/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Vehicule;

import Domain.Trips.Segment;
import Domain.Trips.Trip;
import Domain.Client.Client;
import Domain.Node.Stop;

import Domain.Positions.GeographicPosition;
import Application.Controller.Time;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Raphael
 */
public class Vehicule implements java.io.Serializable
{
    private Trip trip;
    private List<Client> inboardClients;
    private VehiculePosition position;
    private String name;
    private int width = 16;
    private int capacity = 30;
    
    public Vehicule(Trip _trip, Segment _segmentToSpawn, String _name)
    {
        this.position = new VehiculePosition(_segmentToSpawn, new Time());
        this.inboardClients = new ArrayList<>();
        this.trip = _trip;
        this.name = _name;
    }
    
    public void embarkClient(List<Client> _newPassengers)
    {
        this.inboardClients.addAll(_newPassengers);
    }
    
    public List<Client> disembarkClient(Stop _currentStop, Time _currentTime)
    {
        List<Client> clients = new ArrayList();
        List<Client> toRemove = new ArrayList();
        for(int i = 0; i < this.inboardClients.size(); i++)
        {
            if(this.inboardClients.get(i).getCurrentItinary().getDestinationStop() == _currentStop)
            {
                if(this.inboardClients.get(i).nextItinary(_currentTime))
                {
                    clients.add(this.inboardClients.get(i));
                }
                    
                toRemove.add(this.inboardClients.get(i));
            }
        }
        for(int i = 0; i < toRemove.size(); i++)
        {
            this.inboardClients.remove(toRemove.get(i));
        }
        return clients;
    }
    
    public List<Client>  disembarkClient(Stop _currentStop, Client _client)
    {
        return null;
    }
    
    public List<Client> getInboardClients()
    {
        return this.inboardClients;
    }
    
    public int getCapacity()
    {
        return this.capacity;
    }
    
    public VehiculePosition getCurrentPosition()
    {
        return this.position;
    }
    
    public void setCurrentPosition(VehiculePosition _position)
    {
        this.position = _position;
    }
    
    public Trip getTrip()
    {
        return this.trip;
    }
    
    public void setTrip(Trip _trip)
    {
        this.trip = _trip;
    }
    
    public GeographicPosition getGeographicPosition()
    {
        return position.getGeographicPosition();
    }
    
    public void setPosition(Time _time)
    {
        this.position.update(_time);
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setName(String _name)
    {
        this.name = _name;
    }
    
    public int getWidth()
    {
        return this.width;
    }
    
    public void setWidth(int _width)
    {
        this.width = _width;
    }
}
