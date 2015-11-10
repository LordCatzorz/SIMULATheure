/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Simulation;

import java.util.List;
import java.awt.Image;
import java.sql.Time;
import java.io.*;

import Domain.Node.Node;
import Domain.Vehicule.*;
import Domain.Client.*;
import Domain.Trips.*;
import Domain.Generation.*;
import Domain.Positions.*;
/**
 *
 * @author Raphael
 */
public class Simulation 
{
    private String name;
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
    
    
    public void Simulation()
    {
        
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
        
    }
    
    public void updateSimulation()
    {
        this.updatePositions();
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
    
    public void SetSpeedMultiplier(float _speedMultiplier)
    {
         this.speedMultiplier = _speedMultiplier;
    }
    
    public boolean addNode(GeographicPosition _geographicPosition)
    {
        return false;
    }
    
    public boolean addSegment(Node _origin, Node _destination, Distribution _distributionDuration)
    {
        return false;
    }
    
    public boolean addVehicule(VehiculeKind _vehiculeKind, Trip _trip, Node _spawnNode)
    {
        return false;
    }
    
    public boolean addTrip(List<Segment> _listSegment, String _name, boolean _circular)
    {
        return false;
    }
    
    public boolean addTrip(List<Segment> _listSegment, String _name) // circular = false
    {
        return false;
    }
    
    public boolean addClientGenerator(ClientProfile _clientProfile, Node _spawnNode, Distribution _distribution)
    {
        return false;
    }
    
    public boolean addVehiculeGenerator(VehiculeKind _vehiculeKind, Node _spawnNode, Distribution _distribution)
    {
        return false;
    }
    
    public boolean addClientProfile(List<Itinary> _itinary)
    {
        return false;
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
    
    public Node getNodeAtPostion(GeographicPosition _position)
    {
        return null;
    }
    
    public Vehicule getVehiculeAtPostion(GeographicPosition _position)
    {
        return null;
    }
    
    public Segment getSegmentAtPostion(GeographicPosition _position)
    {
        return null;
    }
    
    private void updatePositions()
    {
        
    }
}
