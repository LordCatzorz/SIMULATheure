/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Simulation;

import java.util.List;
import java.lang.String;
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
            FileOutputStream outFile = new FileOutputStream("saves/vehicules.ser");
            ObjectOutputStream out = new ObjectOutputStream(outFile);
            for(int i =0; i < this.listVehicule.size(); i++)
            {
                out.writeObject(this.listVehicule.get(i));
            }
            
            outFile = new FileOutputStream("saves/segments.ser");
            out = new ObjectOutputStream(outFile);
            for(int i =0; i < this.listSegment.size(); i++)
            {
                out.writeObject(this.listSegment.get(i));
            }
                                    
            outFile = new FileOutputStream("saves/clients.ser");
            out = new ObjectOutputStream(outFile);
            for(int i =0; i < this.listClient.size(); i++)
            {
                out.writeObject(this.listClient.get(i));
            }
                                    
            outFile = new FileOutputStream("saves/clientGenerators.ser");
            out = new ObjectOutputStream(outFile);
            for(int i =0; i < this.listClientGenerator.size(); i++)
            {
                out.writeObject(this.listClientGenerator.get(i));
            }
                                    
            outFile = new FileOutputStream("saves/clientProfiles.ser");
            out = new ObjectOutputStream(outFile);
            for(int i =0; i < this.listClientProfile.size(); i++)
            {
                out.writeObject(this.listClientProfile.get(i));
            }
                                    
            outFile = new FileOutputStream("saves/nodes.ser");
            out = new ObjectOutputStream(outFile);
            for(int i =0; i < this.listNode.size(); i++)
            {
                out.writeObject(this.listNode.get(i));
            }
                                    
            outFile = new FileOutputStream("saves/trips.ser");
            out = new ObjectOutputStream(outFile);
            for(int i =0; i < this.listTrip.size(); i++)
            {
                out.writeObject(this.listTrip.get(i));
            }
                                    
            outFile = new FileOutputStream("saves/vehiculeGenerators.ser");
            out = new ObjectOutputStream(outFile);
            for(int i =0; i < this.listVehiculeGenerator.size(); i++)
            {
                out.writeObject(this.listVehiculeGenerator.get(i));
            }
            
            out.close();
            outFile.close();
        }
        catch(IOException i)
        {
            
        }  
    }
    
    public void LoadSimulation()
    {
//        try
//        {
//           Vehicule vehicule = new Vehicule();
//           FileInputStream fileIn = new FileInputStream("saves/test.ser");
//           ObjectInputStream in = new ObjectInputStream(fileIn);
//           vehicule = (Vehicule) in.readObject();
//           in.close();
//           fileIn.close();
//        }
//        catch(IOException i)
//        {
//        }
//        catch(ClassNotFoundException c)
//        {
//           System.out.println("Employee class not found");
//           c.printStackTrace();
//           return;
//        } 
    }
    
    public void SetSpeedMultiplier(float _speedMultiplier)
    {
         
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
        return false;
    }
    
    public List<Node> getListNode()
    {
        return null;
    }
    
    public List<Vehicule> getListVehicule()
    {
        return null;
    }
    public List<Segment> getListSegment()
    {
        return null;
    }
    public List<Trip> getListTrip()
    {
        return null;
    }
    public List<ClientGenerator> getListClientGenerator()
    {
        return null;
    }
    
    public List<VehiculeGenerator> getListVehiculeGenerator()
    {
        return null;
    }
    
    public List<ClientProfile> getListClientProfile()
    {
        return null;
    }
    
    public List<Client> getListClient()
    {
        return null;
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
