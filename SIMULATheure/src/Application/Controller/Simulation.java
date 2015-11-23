/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Controller;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Image;
import java.io.*;

import Domain.Node.*;
import Domain.Vehicule.*;
import Domain.Client.*;
import Domain.Trips.*;
import Domain.Generation.*;
import Domain.Positions.*;
import Application.Controller.Tool;
/**
 *
 * @author Raphael
 */
public class Simulation 
{
    private String name;
    private Tool currentTool;
    private Image background;
    private Time currentTime;
    private Time startTime;
    private Time endTime;
    private float speedMultiplier;
    private List<Node> listNode;
    private List<Vehicule> listVehicule;
    private List<Segment> listSegment;
    private List<Trip> listTrip;
    private List<ClientGenerator> listClientGenerator;
    private List<VehiculeGenerator> listVehiculeGenerator;
    private List<ClientProfile> listClientProfile;
    private List<Client> listClient;
    private boolean isSimulationStarted;
    private boolean isSimulationPaused;
    
    private int nodeMargin = 5;
    
    public Simulation()
    {
        this.listNode = new ArrayList<>();
        this.listVehicule = new ArrayList<>();
        this.listSegment = new ArrayList<>();
        this.listTrip = new ArrayList<>();
        this.listClientGenerator = new ArrayList<>();
        this.listVehiculeGenerator = new ArrayList<>();
        this.listClientProfile = new ArrayList<>();
        this.listClient = new ArrayList<>();
        this.speedMultiplier = 1;
        this.currentTime = new Time();
        this.startTime = new Time();
        this.endTime = new Time();
        this.isSimulationStarted = false;
        this.isSimulationPaused = false;
    }
            
    public float getSpeedMultiplier()
    {
        return this.speedMultiplier;
    }
    
    public boolean getIsSimuationStarted()
    {
        return this.isSimulationStarted;
    }
    public void setIsSimulationStarted(boolean _isSimulationStarted)
    {
        this.isSimulationStarted = _isSimulationStarted;
    }
    public boolean getIsSimuationPaused()
    {
        return this.isSimulationPaused;
    }
    public void setIsSimulationPaused(boolean _isSimulationPaused)
    {
        this.isSimulationPaused = _isSimulationPaused;
    }
    public Time getCurrentTime()
    {
        return this.currentTime;
    }
    public void setCurrentTime(Time _currentTime)
    {
        this.currentTime = _currentTime;
    }
    public Time getStartTime()
    {
        return this.startTime;
    }
    public void setStartTime(Time _startTime)
    {
        this.startTime =_startTime;
    }
    public Time getEndTime()
    {
        return this.endTime;
    }
    public void setEndTime(Time _endTime)
    {
        this.endTime =_endTime;
    }
    
    public void play()
    {
        //this.saveInitialState();
        this.setSegmentsDuration();
        //this.speedMultiplier = 1;        
    }
    
    public void pause()
    {
        this.setIsSimulationPaused(true);
    }
    
    public void reset()
    {
        this.setIsSimulationPaused(false);
        this.setIsSimulationStarted(false);
        //this.LoadSimulation(name);
    }
    
    public void updateSimulation()
    {
        this.currentTime.setTime(this.currentTime.getTime() + (2 * this.speedMultiplier));
        /*for(int i = 0; i < this.listClientGenerator.size(); i++)
        {
            this.listClientGenerator.get(i).awakeGenerator(this.currentTime);
        }*/
        
        for(int i = 0; i < this.listVehiculeGenerator.size(); i++)
        {
            int amountVehicule = 0;
            Trip vehiculeTrip = this.listVehiculeGenerator.get(i).getTrip();
            for(int j= 0; j < this.listVehicule.size();j++)
            {
                if(this.listVehicule.get(j).getTrip() == vehiculeTrip)
                {
                    amountVehicule++;
                }
            }
            if(amountVehicule < this.listVehiculeGenerator.get(i).getTrip().getMaxNumberVehicule())
            {
                Vehicule newVehicule = this.listVehiculeGenerator.get(i).awakeGenerator(this.currentTime);
                if(newVehicule != null)
                {
                    this.listVehicule.add(newVehicule);
                }
            }
        }
        this.updateVehiculePositions();
    }
    public void saveInitialState()
    {
        try
        {
            FileOutputStream outFile = new FileOutputStream("saves/" + this.name + "vehicules.ser");
            ObjectOutputStream out = new ObjectOutputStream(outFile);
            for(int i =0; i < this.listVehicule.size(); i++)
            {
                out.writeObject(this.listVehicule.get(i));
            }
            
            outFile = new FileOutputStream("saves/" + this.name + "segments.ser");
            out = new ObjectOutputStream(outFile);
            for(int i =0; i < this.listSegment.size(); i++)
            {
                out.writeObject(this.listSegment.get(i));
            }
                                    
            outFile = new FileOutputStream("saves/" + this.name + "clients.ser");
            out = new ObjectOutputStream(outFile);
            for(int i =0; i < this.listClient.size(); i++)
            {
                out.writeObject(this.listClient.get(i));
            }
                                    
            outFile = new FileOutputStream("saves/" + this.name + "clientGenerators.ser");
            out = new ObjectOutputStream(outFile);
            for(int i =0; i < this.listClientGenerator.size(); i++)
            {
                out.writeObject(this.listClientGenerator.get(i));
            }
                                    
            outFile = new FileOutputStream("saves/" + this.name + "clientProfiles.ser");
            out = new ObjectOutputStream(outFile);
            for(int i =0; i < this.listClientProfile.size(); i++)
            {
                out.writeObject(this.listClientProfile.get(i));
            }
                                    
            outFile = new FileOutputStream("saves/" + this.name + "nodes.ser");
            out = new ObjectOutputStream(outFile);
            for(int i =0; i < this.listNode.size(); i++)
            {
                out.writeObject(this.listNode.get(i));
            }
                                    
            outFile = new FileOutputStream("saves/" + this.name + "trips.ser");
            out = new ObjectOutputStream(outFile);
            for(int i =0; i < this.listTrip.size(); i++)
            {
                out.writeObject(this.listTrip.get(i));
            }
                                    
            outFile = new FileOutputStream("saves/" + this.name + "vehiculeGenerators.ser");
            out = new ObjectOutputStream(outFile);
            for(int i =0; i < this.listVehiculeGenerator.size(); i++)
            {
                out.writeObject(this.listVehiculeGenerator.get(i));
            }
            outFile = new FileOutputStream("saves/" + this.name + "vehiculeGenerators.ser");
            out = new ObjectOutputStream(outFile);
            out.writeObject(this); //À essayer
            
            out.close();
            outFile.close();
        }
        catch(IOException i)
        {
            
        }  
    }
    
    public void LoadSimulation(String _folderPath)
    {
        try
        {
            FileInputStream fileIn = new FileInputStream(_folderPath + "vehicules");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.listVehicule = (List<Vehicule>) in.readObject();
            
            fileIn = new FileInputStream(_folderPath + "segments.ser");
            in = new ObjectInputStream(fileIn);
            this.listSegment = (List<Segment>) in.readObject();
            
            fileIn = new FileInputStream(_folderPath + "clents.ser");
            in = new ObjectInputStream(fileIn);
            this.listClient = (List<Client>) in.readObject();
            
            fileIn = new FileInputStream(_folderPath + "clientGenerators.ser");
            in = new ObjectInputStream(fileIn);
            this.listClientGenerator = (List<ClientGenerator>) in.readObject();                        
            
            fileIn = new FileInputStream(_folderPath + "clientProfiles.ser");
            in = new ObjectInputStream(fileIn);
            this.listClientProfile = (List<ClientProfile>) in.readObject();
            
            fileIn = new FileInputStream(_folderPath + "nodes.ser");
            in = new ObjectInputStream(fileIn);
            this.listNode = (List<Node>) in.readObject();
            
            fileIn = new FileInputStream(_folderPath + "trips.ser");
            in = new ObjectInputStream(fileIn);
            this.listTrip = (List<Trip>) in.readObject();                        
            
            fileIn = new FileInputStream(_folderPath + "vehiculeGenerators.ser");
            in = new ObjectInputStream(fileIn);
            this.listVehiculeGenerator = (List<VehiculeGenerator>) in.readObject();
           
           in.close();
           fileIn.close();
        }
        catch(IOException i)
        {
            i.printStackTrace();
        }
        catch(ClassNotFoundException c)
        {
           System.out.println(c.getClass() + " class not found");
        } 
    }
    
    public Tool getCurrentTool()
    {
        return this.currentTool;
    }
    public void setCurrentTool(Tool _tool)
    {
        this.currentTool = _tool;
    }
    
    public void SetSpeedMultiplier(float _speedMultiplier)
    {
         this.speedMultiplier = _speedMultiplier;
    }
    
    public boolean addNode(float _x, float _y)
    {
        Node node  = new Node(new GeographicPosition(_x, _y));
        node.setName("Arrêt" + (this.listNode.size() + 1));
        return this.listNode.add(node);
    }
    
    public boolean addSegment(Node _origin, Node _destination)
    {
        return this.listSegment.add(new Segment(_origin, _destination));
    }
    
    public boolean addVehicule(Trip _trip, Segment _spawnSegment, String _name)
    {
        return this.listVehicule.add(new Vehicule(_trip, _spawnSegment, _name));
    }
    
    public boolean addTrip(List<Segment> _listSegment, String _name, int _number, boolean _isCircular)
    {
        Trip trip = new Trip();
        List<Segment> list = new LinkedList<>(_listSegment);
        trip.setAllSegments(list);
        trip.setName(_name);
        trip.setMaxNumberVehicule(_number);
        trip.setIsCircular(_isCircular);
        return this.listTrip.add(trip);
    }
    
    public boolean addClientGenerator(ClientProfile _clientProfile)
    {
        return this.listClientGenerator.add(new ClientGenerator(_clientProfile));
    }
    
    public boolean addVehiculeGenerator(Segment _spawnSegment, Trip _trip, double _min, double _max, double _mode, Time _startTime, Time _endTime, String _name)
    {
        return this.listVehiculeGenerator.add(new VehiculeGenerator(_spawnSegment, _trip, _min, _max, _mode, _startTime, _endTime,_name));
    }
    
    /*public boolean addVehiculeGenerator(Segment _spawnSegment, Time _timeBeginGeneration, Time _timeEndGeneration, Trip _trip, String _name, VehiculeKind _vehiculeKind)//VehiculeKind _vehiculeKind, Segment _spawnSegment, Trip _trip)
    {
        return this.listVehiculeGenerator.add(new VehiculeGenerator(_timeBeginGeneration, _timeEndGeneration, _spawnSegment, _trip, _name, _vehiculeKind));
    }*/
    
    public boolean addClientProfile(List<Itinary> _itinary)
    {
        ClientProfile profile = new ClientProfile();
        profile.setItinary(_itinary);
        return this.listClientProfile.add(profile);
    }
    
    public boolean addClient(Client _client)
    {
        return this.listClient.add(_client);
    }
    
    public List<Node> getListNode()
    {
        return this.listNode;
    }
    
    public List<Vehicule> getListVehicule()
    {
        return this.listVehicule;
    }
    public List<Segment> getListSegment()
    {
        return this.listSegment;
    }
    public List<Trip> getListTrip()
    {
        return this.listTrip;
    }
    public List<ClientGenerator> getListClientGenerator()
    {
        return this.listClientGenerator;
    }
    
    public List<VehiculeGenerator> getListVehiculeGenerator()
    {
        return this.listVehiculeGenerator;
    }
    
    public List<ClientProfile> getListClientProfile()
    {
        return this.listClientProfile;
    }
    
    public List<Client> getListClient()
    {
        return this.listClient;
    }
    
    public List<Node> getListNodeByTrip(Trip _trip)
    {
        List<Node> listNode = new ArrayList<Node>();
        for(int i = 0; i < _trip.getAllSegments().size(); i++)
        {
            if(!listNode.contains(_trip.getAllSegments().get(i).getOriginNode()))
                listNode.add(_trip.getAllSegments().get(i).getOriginNode());
            if(!listNode.contains(_trip.getAllSegments().get(i).getDestinationNode()))
                listNode.add(_trip.getAllSegments().get(i).getDestinationNode());
            
        }
        return listNode;
    }
    
    public Node getNodeAtPostion(float _x, float _y)
    {
        for(Node node : this.listNode)
        {
            if((node.getGeographicPosition().getXPosition() <= _x + nodeMargin && node.getGeographicPosition().getXPosition() >= _x - nodeMargin) &&
               (node.getGeographicPosition().getYPosition() <= _y + nodeMargin && node.getGeographicPosition().getYPosition() >= _y - nodeMargin))
                return node;
        }
        return null;
    }
    
    public Vehicule getVehiculeAtPostion(float _x, float _y)
    {
        for(Vehicule vehicule : this.listVehicule)
        {
            if(vehicule.getGeographicPosition() == new GeographicPosition(_x, _y))
                return vehicule;
        }
        return null;
    }
    
    public Segment getSegmentAtPostion(float _x, float _y)
    {
        for(Segment segment : this.listSegment)
        {
            if(new GeographicPosition(_x, _y).isBetween(segment.getOriginNode().getGeographicPosition(), segment.getDestinationNode().getGeographicPosition()))
                return segment;
        }
        return null;
    }
    
    public Trip getTripByName(String _tripName)
    {
        for(Trip trip: this.listTrip)
        {
            if(trip.getName().equals(_tripName))
                return trip;
        }
        return null;
    }
    
    /*public VehiculeGenerator getVehiculeGeneratorAtPosition(float _x, float _y)
    {
        for(VehiculeGenerator generator: this.listVehiculeGenerator)
        {
            if(generator.getSpawnSegment().getOriginNode().getGeographicPosition().getXPosition() == _x &&
               generator.getSpawnSegment().getOriginNode().getGeographicPosition().getYPosition() == _y)
                return generator;
        }
        return null;
    }*/
    
    public void setSegmentsDuration()
    {
        for(int i = 0; i < this.getListSegment().size(); i++)
        {
            Segment segment = this.getListSegment().get(i);
            double triangle = segment.getDurationDistribution().calculate();
            int duration =(int) Math.round(triangle);
            segment.setDurationTime(duration);
        }       
    }
    
    public void updateVehiculePositionsByNode(Node _nodeUpdated)
    {
        for(Vehicule vehicule : this.listVehicule)
        {
            if(vehicule.getCurrentPosition().getCurrentSegment().getOriginNode() == _nodeUpdated)
                vehicule.setCurrentPosition(new VehiculePosition(vehicule.getCurrentPosition().getCurrentSegment(), new Time()));
        }
    }
    
    public void changeNodeNameAndPosition(float _oldXPosition, float _oldYPosition, float _newXPosition, float _newYPosition, String _name)
    {
        for(Node node : this.listNode)
        {
            if (node.getGeographicPosition().getXPosition() == _oldXPosition && node.getGeographicPosition().getYPosition() == _oldYPosition)
            {
                node.setGeographicPosition(new GeographicPosition(_newXPosition, _newYPosition));
                node.setName(_name);
                break;
            }
        }
    }
    
    public boolean changeSegmentInfo(String _oldOriginName, String _oldDestinationName, String _newOriginName, String _newDestinationName,
                                  String _name, float _minTime, float _maxTime, float _modeTime)
    {
        for(Segment segment: this.listSegment)
        {
            if(segment.getOriginNode().getName().equals(_oldOriginName) && segment.getDestinationNode().getName().equals(_oldDestinationName))
            {
                Node nodeOrigin = this.getNodeByName(_newOriginName);
                Node nodeDestination = this.getNodeByName(_newDestinationName);
                segment.setName(_name);
                
                if (nodeOrigin == null || nodeDestination == null)
                    return false;
                
                segment.setOriginNode(this.getNodeByName(_newOriginName));
                segment.setDestinationNode(this.getNodeByName(_newDestinationName));
                segment.setDurationDistribution(_minTime, _maxTime, _modeTime);
                return true;
            }
        }
        return false;
    }
    
    public void changeVehiculeGeneratorInfo(VehiculeGenerator _generator, Segment _spawnSegment, Trip _trip,
                                            double _min, double _max, double _mode, Time _startTime, Time _endTime, String _name)
    {
        for(VehiculeGenerator generator : this.listVehiculeGenerator)
        {
            if(generator == _generator)
            {
                generator.setTimeBeginGeneration(_startTime);
                generator.setTimeEndGeneration(_endTime);
                generator.setTrip(_trip);
                generator.setDistribution(_min, _max, _mode);
                generator.setSpawnSegment(_spawnSegment);
                generator.setName(_name);                
                break;
            }
        }
    }
    
    public void deleteNode(float _x, float _y)
    {
        String name;
        for(Node node : this.listNode)
        {
            if (node.getGeographicPosition().getXPosition() == _x && node.getGeographicPosition().getYPosition() == _y)
            {
                name = node.getName();
                //Delete ce qui est selon les trajets
                deleteTripsWithNode(name);
                deleteSegmentWithNode(name);
                this.listNode.remove(node);
                break;
            }
        }
    }
    
    //Appelez quand on delete un arret
    public void deleteSegmentWithNode(String _nodeName)
    {
        List<Segment> lstSegmentToDelete = new ArrayList<Segment>();
        for(int i = 0; i < this.listSegment.size(); i++)
        {
            Segment segment = listSegment.get(i);
            if(segment.getOriginNode().getName().equalsIgnoreCase(_nodeName) || segment.getDestinationNode().getName().equalsIgnoreCase(_nodeName))
            {
               lstSegmentToDelete.add(segment);
            }
        }
        for(int i = 0; i < lstSegmentToDelete.size(); i++)
        {
            this.listSegment.remove(lstSegmentToDelete.get(i));
        }
    }
    
    public void deleteVehicule(Vehicule _vehicule)
    {
        this.listVehicule.remove(_vehicule);
    }
    
    public void deleteSegmentByNodeName(String _originName, String _destinationName)
    {
        for(Segment segment: this.listSegment)
        {
            if(segment.getOriginNode().getName().equals(_originName) && segment.getDestinationNode().getName().equals(_destinationName))
            {
                deleteTripsWithSegment(segment);
                this.listSegment.remove(segment);
                break;
            }
        }
    }
    
    
    public void deleteVehiculeGenerator(VehiculeGenerator _vehiculeGenerator)
    {
        this.listVehiculeGenerator.remove(_vehiculeGenerator);
    }
    

    public void deleteTrip(Trip _trip)
    {
        String tripName = _trip.getName();
        for(int i = 0; i < this.getListTrip().size(); i++)
        {
            Trip trip = this.getListTrip().get(i);
            if (trip.getName().equalsIgnoreCase(tripName))
            {
                this.listTrip.remove(trip);
                break;
            }
        }
    }
    public void deleteTripsWithSegment(Segment _segment)
    {
        List<Trip> listTripToDelete = new ArrayList<>();
        for(int i = 0; i < this.listTrip.size(); i++)
        {
            Trip trip = listTrip.get(i);
            int j;
            for( j = 0; j < trip.getAllSegments().size(); j++)
            {
                Segment segment = trip.getAllSegments().get(j);
                if(segment.getOriginNode() == _segment.getOriginNode() && segment.getDestinationNode() == _segment.getDestinationNode())
                {
                    if(!listTripToDelete.contains(trip))
                    {
                        listTripToDelete.add(trip);
                        break;
                    }
                }
            }
        }
        for(int i = 0; i < listTripToDelete.size(); i++)
        {
            this.listTrip.remove(listTripToDelete.get(i));
        }
    }
    public void deleteTripsWithNode(String _nodeName)
    {
        List<Trip> listTripToDelete = new ArrayList<>();
        for(int i = 0; i < this.listTrip.size(); i++)
        {
            Trip trip = listTrip.get(i);
            int j;
            for( j = 0; j < trip.getAllSegments().size(); j++)
            {
                Node origin = trip.getAllSegments().get(j).getOriginNode();
                if(origin.getName().equalsIgnoreCase(_nodeName))
                {
                    if(!listTripToDelete.contains(trip))
                    {
                        listTripToDelete.add(trip);
                        break;
                    }
                }
            }
            if(trip.getAllSegments().get(j).getDestinationNode().getName().equalsIgnoreCase(_nodeName))
            {                
                if(!listTripToDelete.contains(trip))
                {
                    listTripToDelete.add(trip);            
                }
            }
        }
        for(int i = 0; i < listTripToDelete.size(); i++)
        {
            deleteVehiculeGeneratorsWithTrip(listTripToDelete.get(i));
            deleteVehiculeWithTrip(listTripToDelete.get(i));
            this.listTrip.remove(listTripToDelete.get(i));
        }
    }
    private void deleteVehiculeGeneratorsWithTrip(Trip _trip)
    {
        for(int i = 0; i < this.getListVehiculeGenerator().size(); i++)
        {
            if(this.getListVehiculeGenerator().get(i).getTrip().getName().equalsIgnoreCase( _trip.getName()))
            {
                this.listVehiculeGenerator.remove(this.getListVehiculeGenerator().get(i));
            }
        }
    }
    private void deleteVehiculeWithTrip(Trip _trip)
    {
        for(int i = 0; i < this.getListVehicule().size(); i++)
        {
            if(this.getListVehicule().get(i).getTrip().getName().equalsIgnoreCase( _trip.getName()))
            {
                this.listVehicule.remove(this.getListVehicule().get(i));
            }
        }
    }
    
    /******************************************
    ****** Private functions below ************
    *******************************************/
    
    private Node getNodeByName(String _name)
    {
        for(Node node : this.listNode)
        {
            if(node.getName().equals(_name))
                return node;
        }
        return null;
    }
    
    private void updateVehiculePositions()
    {
        if(this.getListVehicule().size()>0)
        {
            for(int i = 0; i < this.getListVehicule().size(); i++)
            {
                Vehicule vehicule = this.getListVehicule().get(i);
                if(this.isSegmentCompleted(vehicule))
                {
                    Node destinationNode = vehicule.getCurrentPosition().getCurrentSegment().getDestinationNode();
                    if(destinationNode instanceof Stop)
                    {
                        for(Node stop: this.listNode) 
                        {
                            if(stop.equals(destinationNode))
                            {
                                //((Stop)stop).addClient(vehicule.disembarkClient((Stop)stop));
                                //vehicule.embarkClient(((Stop)stop).requestEmbarkmentClient(vehicule.getTrip(), capacity));
                            }
                        }
                    }
                    if(vehicule.getTrip().getNextSegment(destinationNode) != null)
                    {
                        vehicule.getCurrentPosition().setCurrentSegment(vehicule.getTrip().getNextSegment(destinationNode));
                        vehicule.getCurrentPosition().setTimeStartSegment(new Time(this.currentTime.getHour(), this.currentTime.getMinute(), this.currentTime.getSecond()));
                    }else{
                        if(vehicule.getTrip().getIsCircular())
                        {
                            vehicule.getCurrentPosition().setCurrentSegment(vehicule.getTrip().getAllSegments().get(0));
                            vehicule.getCurrentPosition().setTimeStartSegment(new Time(this.currentTime.getHour(), this.currentTime.getMinute(), this.currentTime.getSecond()));
                        }else{
                            this.listVehicule.remove(vehicule);
                        }
                    }
                }else{
                    this.moveVehicule(vehicule);
                }
            }
        }
    }
    
    private void moveVehicule(Vehicule _vehicule)
    {
        _vehicule.getCurrentPosition().update(currentTime);
        
        /*Segment segment = _vehicule.getCurrentPosition().getCurrentSegment();
        GeographicPosition originPosition = segment.getOriginNode().getGeographicPosition();
        GeographicPosition destinationPosition = segment.getDestinationNode().getGeographicPosition();
        
        float angle = originPosition.getAngle(destinationPosition);
        float xPercentage;
        float yPercentage;
        
        //Initialiser le pourcentage du mouvement horizontale vs verticale
        if(angle <=180)
        {
            xPercentage = ((90 - angle)/90);
            yPercentage = 1;//Le véhicule va vers le haut
        }
        else //if(angle <= 360)
        {
            xPercentage = ((angle - 270)/90);
            yPercentage = -1; //Le véhicule va vers le bas
        }
        yPercentage = (1 - java.lang.Math.abs(xPercentage)) * yPercentage;
        
        float x = _vehicule.getGeographicPosition().getXPosition() + ((_vehicule.getSpeed() * this.speedMultiplier) * xPercentage);
        float y = _vehicule.getGeographicPosition().getYPosition() + ((_vehicule.getSpeed() * this.speedMultiplier) * yPercentage);
        
        VehiculePosition position = _vehicule.getCurrentPosition();
        position.setGeographicPosition(new GeographicPosition(x, y));
        _vehicule.setCurrentPosition(position);*/
    }
    
    private boolean isSegmentCompleted(Vehicule _vehicule)
    {
        double timeSegmentStarted = _vehicule.getCurrentPosition().getTimeSegmentStart().getTime();
        double ellapsedTime = currentTime.getTime() - timeSegmentStarted;
        double totalTime = (_vehicule.getCurrentPosition().getCurrentSegment().getDurationTime())*60;
        double percentageCompleted =  (100 - ((totalTime - ellapsedTime)/totalTime)*100)/100;
        return (percentageCompleted >= 1);
    }
}
