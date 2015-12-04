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
 * @author N-Team
 */
public class Simulation implements java.io.Serializable
{
    private String name;
    private Tool currentTool;
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
    private boolean dayChanged;
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
        this.dayChanged = false;
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
        this.saveInitialState();
        this.setSegmentsDuration();
        //this.speedMultiplier = 1;        
    }
    
    public void pause()
    {
        this.setIsSimulationPaused(true);
    }
    
    public void updateSimulation()
    {
        this.currentTime.setTime(this.currentTime.getTime() + (2 * this.speedMultiplier));
        if(this.currentTime.getTime() < this.endTime.getTime())
        {
            //Resets the time segment duration when the day changes
            if(!this.dayChanged)
            {
                Time endChecker = new Time(0, endTime.getHour(),endTime.getMinute(), endTime.getSecond());
                Time startChecker = new Time(0, startTime.getHour(), startTime.getMinute(), startTime.getSecond());
                Time dayChecker = new Time(0, currentTime.getHour(), currentTime.getMinute(), currentTime.getSecond());
                if(dayChecker.getTime() < startChecker.getTime())
                {
                    dayChecker.setDay(1);
                    if(endChecker.getTime() < startChecker.getTime())
                    {
                        endChecker.setDay(1);
                        startChecker.setDay(1);
                        if(dayChecker.getTime() < startChecker.getTime() && dayChecker.getTime() > endChecker.getTime())
                        {
                            this.dayChanged = true;
                        }
                    }
                }else{
                    if(endChecker.getTime() > startChecker.getTime())
                    {
                        if(dayChecker.getTime() > endChecker.getTime())
                        {
                            this.dayChanged = true;
                        }
                    }
                }
            }else
            {
                Time startChecker = new Time(0, startTime.getHour(), startTime.getMinute(), startTime.getSecond());
                Time dayChecker = new Time(0, currentTime.getHour(), currentTime.getMinute(), currentTime.getSecond());
                if(dayChecker.getTime() >= startChecker.getTime())
                {
                    dayChanged = false;
                    this.setSegmentsDuration();
                }
            }
            //End of reset
            
            for(int i = 0; i < this.listClientGenerator.size(); i++)
            {
                List<Client> clients = this.listClientGenerator.get(i).awakeGenerator(this.currentTime);
                if(clients !=null && clients.size() > 0)
                {
                    this.listClient.addAll(clients);
                    this.SpawnClients(clients);
                }
            }
            
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
                        
                        for(Node node : this.listNode)
                        {
                            if (node.getGeographicPosition() == this.listVehicule.get(this.listVehicule.size() - 1).getCurrentPosition().getCurrentSegment().getOriginNode().getGeographicPosition())
                            {
                                if(((Stop)node).getClients().size() != 0)
                                {
                                    ((Stop)node).addClient(this.listVehicule.get(this.listVehicule.size() - 1).disembarkClient((Stop)node));
                                    this.listVehicule.get(this.listVehicule.size() - 1).embarkClient(((Stop)node).requestEmbarkmentClient(this.listVehicule.get(this.listVehicule.size() - 1).getTrip(), 
                                                            this.listVehicule.get(this.listVehicule.size() - 1).getCapacity() - 
                                                            this.listVehicule.get(this.listVehicule.size() - 1).getInboardClients().size()));
                                }
                                break;
                            }
                        }
                        
                    }
                }
            }
            this.updateVehiculePositions();
        }else{
            this.setIsSimulationStarted(false);
            this.setIsSimulationPaused(true);
        }
    }
    public void saveInitialState()
    {
        this.isSimulationStarted = false;
        try
        {
            File directory = new File("N-Team_Simulatheure_Saves");

            if (!directory.exists())
            {
                directory.mkdir();
            }
            FileOutputStream outFile = new FileOutputStream("N-Team_Simulatheure_Saves/InitialState.ser");
            ObjectOutputStream out = new ObjectOutputStream(outFile);
            out.writeObject(this);
            
            out.close();
            outFile.close();
        }
        catch(IOException i)
        {
            i.printStackTrace();
        }  
        this.isSimulationStarted = true;
    }
    
    public void LoadSimulation(String _folderPath)
    {
        /*try
        {
            FileInputStream fileIn = new FileInputStream("saves/InitialState.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this = (Simulation)in.readObject(); CANNOT DO THIS
            
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
        } */
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
        Node node  = new Stop(new GeographicPosition(_x, _y), "Arrêt" + (this.listNode.size() + 1));
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
        convertNodeToStop(list);
        trip.setAllSegments(list);
        trip.setName(_name);
        trip.setMaxNumberVehicule(_number);
        trip.setIsCircular(_isCircular);
        return this.listTrip.add(trip);
    }
    
    public boolean addClientGenerator(ClientProfile _profile, double _minTime, double _maxTime, double _modeTime,
                                      double _minNb, double _maxNb, double _modeNb, Time _startTime, Time _endTime, String _name)
    {

        return this.listClientGenerator.add(new ClientGenerator(_profile, _minTime, _maxTime, _modeTime,
                                            _minNb, _maxNb, _modeNb, _startTime, _endTime, _name));
    }
    
    public boolean addVehiculeGenerator(Segment _spawnSegment, Trip _trip, double _min, double _max, double _mode, Time _startTime, Time _endTime, String _name)
    {
        return this.listVehiculeGenerator.add(new VehiculeGenerator(_spawnSegment, _trip, _min, _max, _mode, _startTime, _endTime,_name));
    }
    
    /*public boolean addVehiculeGenerator(Segment _spawnSegment, Time _timeBeginGeneration, Time _timeEndGeneration, Trip _trip, String _name, VehiculeKind _vehiculeKind)//VehiculeKind _vehiculeKind, Segment _spawnSegment, Trip _trip)
    {
        return this.listVehiculeGenerator.add(new VehiculeGenerator(_timeBeginGeneration, _timeEndGeneration, _spawnSegment, _trip, _name, _vehiculeKind));
    }*/
    
    public boolean addClientProfile(ClientProfile _profile)
    {
        return this.listClientProfile.add(_profile);
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
    
    public List<Node> getListOriginNodeByTrip(Trip _trip)
    {
        List<Node> listNode = new ArrayList<Node>();
        for(int i = 0; i < _trip.getAllSegments().size(); i++)
        {
            if(!listNode.contains(_trip.getAllSegments().get(i).getOriginNode()))
                listNode.add(_trip.getAllSegments().get(i).getOriginNode());
            
        }
        return listNode;
    }
    
    public List<Node> getListDestinationNodeByTrip(Trip _trip)
    {
        List<Node> listNode = new ArrayList<Node>();
        for(int i = 0; i < _trip.getAllSegments().size(); i++)
        {
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
    
    public Node getNodeByName(String _name)
    {
        for(Node node : this.listNode)
        {
            if(node.getName().equals(_name))
                return node;
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
    
    public ClientProfile getClientProfileByName(String _name)
    {  
       for(ClientProfile profile : this.listClientProfile)
       {
           if(profile.getName().equals(_name))
               return profile;
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
    
    public void changeClientGeneratorInfo(ClientGenerator _generator, ClientProfile _profile, double _minTime, double _maxTime, double _modeTime,
                                          double _minNb, double _maxNb, double _modeNb, Time _startTime, Time _endTime, String _name)
    {
        for(ClientGenerator generator : this.listClientGenerator)
        {
            if(generator == _generator)
            {
                generator.setTimeBeginGeneration(_startTime);
                generator.setTimeEndGeneration(_endTime);
                generator.setClientProfile(_profile);
                generator.setGenerationTimeDistribution(_minTime, _maxTime, _modeTime);
                generator.setClientNumberDistribution(_minNb, _maxNb, _modeNb);
                break;
            }
        }
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
    
    public void deleteClientGenerator(ClientGenerator _clientGenerator)
    {
        this.listClientGenerator.remove(_clientGenerator);
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
            deleteVehiculeGeneratorsWithTrip(listTripToDelete.get(i));
            deleteVehiculeWithTrip(listTripToDelete.get(i));
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
    
    public void deleteClientProfile(ClientProfile _profile)
    {
        deleteClientGeneratorByProfile(_profile);
        this.listClientProfile.remove(_profile);
    }
    private void deleteClientGeneratorByProfile(ClientProfile _profile)
    {
        List<ClientGenerator> toRemove = new ArrayList<ClientGenerator>();
        for(int i = 0; i < this.listClientGenerator.size(); i++)
        {
            if(this.listClientGenerator.get(i).getClientProfile() == _profile)
            {
                toRemove.add(this.listClientGenerator.get(i));
            }
        }
        for(int i = 0; i < toRemove.size(); i++)
        {
            this.listClientGenerator.remove(toRemove.get(i));
        }
    }
    
    /******************************************
    ****** Private functions below ************
    *******************************************/
    
    private void updateVehiculePositions()
    {
        if(this.getListVehicule().size()>0)
        {
            List<Vehicule> toRemove = new ArrayList<Vehicule>();
            for(int i = 0; i < this.getListVehicule().size(); i++)
            {
                Vehicule vehicule = this.getListVehicule().get(i);
                if(this.isSegmentCompleted(vehicule))
                {
                    Node destinationNode = vehicule.getCurrentPosition().getCurrentSegment().getDestinationNode();
                    Node originNode = vehicule.getCurrentPosition().getCurrentSegment().getOriginNode();
                    if(destinationNode instanceof Stop)
                    {
                        for(Node stop: this.listNode) 
                        {
                            if(stop.getGeographicPosition() == destinationNode.getGeographicPosition())
                            {
                                ((Stop)stop).addClient(vehicule.disembarkClient((Stop)stop));
                                vehicule.embarkClient(((Stop)stop).requestEmbarkmentClient(vehicule.getTrip(), 
                                                                                           vehicule.getCapacity() - vehicule.getInboardClients().size()));
                            }
                        }
                    }
                    if(vehicule.getTrip().getNextSegment(destinationNode) != null)
                    {
                        Time endChecker = new Time(0, endTime.getHour(), endTime.getMinute(), endTime.getSecond());
                        Time currentChecker = new Time(0, currentTime.getHour(), currentTime.getMinute(), currentTime.getSecond());
                        Time startChecker = new Time(0, startTime.getHour(), startTime.getMinute(), startTime.getSecond());

                        if(startChecker.getTime() > currentChecker.getTime())
                        {
                            currentChecker.setDay(1);
                        }
                        if(endChecker.getTime() < startChecker.getTime())
                        {
                            endChecker.setDay(1);
                        }
                        if(currentChecker.getTime() > endChecker.getTime())
                        {
                            toRemove.add(vehicule);
                        }else{
                            vehicule.getCurrentPosition().setCurrentSegment(vehicule.getTrip().getNextSegment(destinationNode));
                            vehicule.getCurrentPosition().setTimeStartSegment(new Time(this.currentTime.getDay(), this.currentTime.getHour(), this.currentTime.getMinute(), this.currentTime.getSecond()));
                        }
                    }else{
                        if(vehicule.getTrip().getIsCircular())
                        {
                            vehicule.getCurrentPosition().setCurrentSegment(vehicule.getTrip().getAllSegments().get(0));
                            vehicule.getCurrentPosition().setTimeStartSegment(new Time(this.currentTime.getDay(), this.currentTime.getHour(), this.currentTime.getMinute(), this.currentTime.getSecond()));  
                        }else{
                            toRemove.add(vehicule);
                        }
                    }
                }else{
                    
                    this.moveVehicule(vehicule);
                }
            }
            for(int i = 0; i < toRemove.size(); i++)
            {
                this.listVehicule.remove(toRemove.get(i));
            }
        }
    }
    
    private void moveVehicule(Vehicule _vehicule)
    {
        _vehicule.getCurrentPosition().update(currentTime);
    }
    
    private boolean isSegmentCompleted(Vehicule _vehicule)
    {
        Time timeSegmentStarted = new Time();
        timeSegmentStarted.setTime(_vehicule.getCurrentPosition().getTimeSegmentStart().getTime());
        timeSegmentStarted.setDay(0);
        Time currentChecker = new Time(0, currentTime.getHour(), currentTime.getMinute(), currentTime.getSecond());
        if(currentChecker.getTime() < timeSegmentStarted.getTime())
        {
            currentChecker.setDay(1);
        }
        double ellapsedTime = currentChecker.getTime() - timeSegmentStarted.getTime();
        double totalTime = (_vehicule.getCurrentPosition().getCurrentSegment().getDurationTime())*60;
        double percentageCompleted =  (100 - ((totalTime - ellapsedTime)/totalTime)*100)/100;
        return (percentageCompleted >= 1);
    }
    
    private void convertNodeToStop(List<Segment> _segments)
    {
        List<Node> nodes = new ArrayList();
        for(Segment segment : _segments)
        {
            if(!nodes.contains(segment.getOriginNode()))
            {
                nodes.add(segment.getOriginNode());
                segment.setOriginNode(new Stop(segment.getOriginNode().getGeographicPosition(), segment.getOriginNode().getName()));
            }
            
            if(!nodes.contains(segment.getDestinationNode()))
            {
                nodes.add(segment.getDestinationNode());
                segment.setDestinationNode(new Stop(segment.getDestinationNode().getGeographicPosition(), segment.getDestinationNode().getName()));
            }
        }
        for(int i = 0; i< nodes.size(); i++)
        {
            int j = 0;
            for(Node node : this.listNode)
            {
                if(node == nodes.get(i))
                {
                    this.listNode.set(j, new Stop(node.getGeographicPosition(), node.getName()));
                    break;
                }
                j++;
            }
        }
    }
    
    private void SpawnClients(List<Client> _clients)
    {
        for(int i = 0 ; i < _clients.size(); i++)
        {
            Node clientNode = _clients.get(i).getProfile().getItinary().get(0).getOriginStop();
            for(Node node: this.listNode)
            {
                if(node == clientNode)
                {
                    ((Stop)node).addClient(_clients.get(i));
                }
            }
        }
    }
    
}
