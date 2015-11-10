/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Vehicule;

import java.util.List;
import java.sql.Time;

import Domain.Trips.Trip;
import Domain.Trips.Segment;
import Domain.Client.Client;
import Domain.Node.Stop;
import Domain.Positions.GeographicPosition;

/**
 *
 * @author Raphael
 */
public class Vehicule implements java.io.Serializable
{
    private Trip trip;
    private List<Client> inboardClients;
    private VehiculePosition position;
    private VehiculeKind vehiculeKind;
    
    public void Vehicule(Trip _trip, VehiculeKind _vehiculeKind, Segment _segmentToSpawn)
    {
        this.trip = _trip;
        this.vehiculeKind = _vehiculeKind;
    }
    
    public void embarkClient(List<Client> _newPassengers)
    {
        this.inboardClients.addAll(_newPassengers);
    }
    
    public void disembarkClient(Stop _currentStop)
    {
        
    }
    
    public void disembarkClient(Stop _currentStop, Client _client)
    {
        
    }
    
    public GeographicPosition getPosition(Time _time)
    {
        return null;
    }
    
    public VehiculePosition getCurrentPosition()
    {
        return this.position;
    }
    
    public Trip getTrip()
    {
        return this.trip;
    }
    
    public void setTrip(Trip _trip)
    {
        this.trip = _trip;
    }
    
    public VehiculeKind getVehiculeKind()
    {
        return this.vehiculeKind;
    }
    
    public GeographicPosition getGeographicPosition()
    {
        return null;
    }
    
    
}
