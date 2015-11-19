/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Simulation;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.awt.Image;
import java.sql.Time;
import java.io.*;
import java.util.Date;

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
    private Time simulationTime;
    private float speedMultiplier;
    private List<Node> listNode;
    private List<Vehicule> listVehicule;
    private List<Segment> listSegment;
    private List<Trip> listTrip;
    private List<ClientGenerator> listClientGenerator;
    private List<VehiculeGenerator> listVehiculeGenerator;
    private List<ClientProfile> listClientProfile;
    private List<Client> listClient;
    
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
        this.speedMultiplier = 0;
    }
            
    public void Play()
    {
        this.saveInitialState();
        //this.setSegmentsDuration //dans le DS démarrer la simulation 
        //this.speedMultiplier = 1;
        /*while(this.speedMultiplier != 0)
        {
            this.updateSimulation();
        }*/
    }
    
    public void Pause()
    {
        this.speedMultiplier = 0;
    }
    
    public void Reset()
    {
        this.Pause();
        this.LoadSimulation(name);
    }
    
    public void updateSimulation()
    {
        for(int i = 0; i < this.listClientGenerator.size(); i++)
        {
            this.listClientGenerator.get(i).awakeGenerator(this.simulationTime);
        }
        
        for(int i = 0; i < this.listVehiculeGenerator.size(); i++)
        {
            this.listVehiculeGenerator.get(i).awakeGenerator(this.simulationTime);
        }
        this.updateVehiculePositions();
    }
    
    public GeographicPosition convertPixelToGeographicPosition(int _pixelX, int _pixelY)
    {
        return null;
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
    
    public void LoadSimulation(String folderPath)
    {
        try
        {
            FileInputStream fileIn = new FileInputStream(folderPath + "vehicules");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.listVehicule = (List<Vehicule>) in.readObject();
            
            fileIn = new FileInputStream(folderPath + "segments.ser");
            in = new ObjectInputStream(fileIn);
            this.listSegment = (List<Segment>) in.readObject();
            
            fileIn = new FileInputStream(folderPath + "clents.ser");
            in = new ObjectInputStream(fileIn);
            this.listClient = (List<Client>) in.readObject();
            
            fileIn = new FileInputStream(folderPath + "clientGenerators.ser");
            in = new ObjectInputStream(fileIn);
            this.listClientGenerator = (List<ClientGenerator>) in.readObject();                        
            
            fileIn = new FileInputStream(folderPath + "clientProfiles.ser");
            in = new ObjectInputStream(fileIn);
            this.listClientProfile = (List<ClientProfile>) in.readObject();
            
            fileIn = new FileInputStream(folderPath + "nodes.ser");
            in = new ObjectInputStream(fileIn);
            this.listNode = (List<Node>) in.readObject();
            
            fileIn = new FileInputStream(folderPath + "trips.ser");
            in = new ObjectInputStream(fileIn);
            this.listTrip = (List<Trip>) in.readObject();                        
            
            fileIn = new FileInputStream(folderPath + "vehiculeGenerators.ser");
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
        return this.listNode.add(new Node(new GeographicPosition(_x, _y)));
    }
    
    public boolean addSegment(Node _origin, Node _destination)
    {
        return this.listSegment.add(new Segment(_origin, _destination));
    }
    
    public boolean addVehicule(VehiculeKind _vehiculeKind, Trip _trip, Segment _spawnSegment)
    {
        return this.listVehicule.add(new Vehicule(_trip, _vehiculeKind, _spawnSegment));
    }
    
    public boolean addTrip(List<Segment> _listSegment, String _name, boolean _circular)
    {
        Trip trip;
        if(_circular)
            trip = new CircularTrip();
        else
            trip = new LinearTrip();
        
        Queue<Segment> queue = new LinkedList<>(_listSegment);
        trip.setAllSegments(queue);
        trip.setName(_name);
        return this.listTrip.add(trip);
    }
    
    public boolean addClientGenerator(ClientProfile _clientProfile)
    {
        return this.listClientGenerator.add(new ClientGenerator(_clientProfile));
    }
    
    public boolean addVehiculeGenerator(VehiculeKind _vehiculeKind, Segment _spawnSegment, Trip _trip)
    {
        return this.listVehiculeGenerator.add(new VehiculeGenerator(_trip, _spawnSegment, _vehiculeKind));
    }
    
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
    
    public void deleteNode(float _x, float _y)
    {
        for(Node node : this.listNode)
        {
            if (node.getGeographicPosition().getXPosition() == _x && node.getGeographicPosition().getYPosition() == _y)
            {
                this.listNode.remove(node);
                break;
            }
        }
    }
    
    /*
    * Private functions below
    */
    
    private void updateVehiculePositions()
    {
        for(Vehicule vehicule : this.listVehicule)
        {
            this.moveVehicule(vehicule);
            if(this.isSegmentCompleted(vehicule))
            {
                Node destinationNode = vehicule.getCurrentPosition().getCurrentSegment().getDestinationNode();
                if(destinationNode instanceof Stop)
                {
                    for(Node stop: this.listNode) 
                    {
                        if(stop.equals(destinationNode))
                        {
                            int capacity = vehicule.getVehiculeKind().getCapacity();
                            ((Stop)stop).addClient(vehicule.disembarkClient((Stop)stop));
                            vehicule.embarkClient(((Stop)stop).requestEmbarkmentClient(vehicule.getTrip(), capacity));
                        }
                    }
                }
                vehicule.getCurrentPosition().setCurrentSegment(vehicule.getTrip().getNextSegment(destinationNode));
            }
        }    
    }
    
    private void moveVehicule(Vehicule _vehicule)
    {
        Segment segment = _vehicule.getCurrentPosition().getCurrentSegment();
        
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
        _vehicule.setCurrentPosition(position);
    }
    
    private boolean isSegmentCompleted(Vehicule _vehicule)
    {
        Segment segment = _vehicule.getCurrentPosition().getCurrentSegment();
        Time timeSegmentStart = _vehicule.getCurrentPosition().getTimeSegmentStart();
        
        float durationTime = segment.getDurationTime().getTime()/1000; //In seconds
        float timeSpent = (this.simulationTime.getTime() - timeSegmentStart.getTime()) /1000; //In seconds
        float completionPercentage = (timeSpent/durationTime)*100;
        return (completionPercentage >= 100);
    }
}
