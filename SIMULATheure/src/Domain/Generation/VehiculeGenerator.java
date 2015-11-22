/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Generation;

import java.sql.Time;

import Domain.Trips.Segment;
import Domain.Trips.Trip;
import Domain.Vehicule.Vehicule;
import Domain.Vehicule.VehiculeKind;
/**
 *
 * @author Raphael
 */
public class VehiculeGenerator implements java.io.Serializable
{
    private Time nextDepartureTime;
    private TriangularDistribution distribution;
    private Time timeBeginGeneration;
    private Time timeEndGeneration;
    private Segment spawnSegment;
    private VehiculeKind vehiculeKind;
    private Trip trip;
    private String name;
    
    public VehiculeGenerator()
    {
    }
    
    public VehiculeGenerator(Time _timeBeginGeneration, Time _timeEndGeneration, Segment _spawnSegment, Trip trip, String name)
    {
        this.spawnSegment = _spawnSegment;
        this.timeBeginGeneration = _timeBeginGeneration;
        this.timeEndGeneration = _timeEndGeneration;
        this.trip = trip;
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setName(String _name)
    {
        this.name = _name;
    }
    
    public Time getNextDepartureTime()
    {
        return this.nextDepartureTime;
    }
    
    public void setNextDepartureTime(Time _nextDepartureTime)
    {
        this.nextDepartureTime = _nextDepartureTime;
    }
    
    public TriangularDistribution getDistribution()
    {
        return this.distribution;
    }
    
    public void setDistribution(float _min, float _max, float _mode)
    {
        this.distribution = new TriangularDistribution(_min, _max, _mode);
    }
    
    public Time getTimeBeginGeneration()
    {
        return this.timeBeginGeneration;
    }
    
    public void setTimeBeginGeneration(Time _timeBeginGeneration)
    {
        this.timeBeginGeneration = _timeBeginGeneration;
    }
    
    public Time getTimeEndGeneration()
    {
        return this.timeEndGeneration;
    }
    
    public void setTimeEndGeneration(Time _timeEndGeneration)
    {
        this.timeEndGeneration = _timeEndGeneration;
    }
    
    public Segment getSpawnSegment()
    {
        return this.spawnSegment;
    }
    
    public VehiculeKind getVehiculeKind()
    {
        return this.vehiculeKind;
    }
    
    public Trip getTrip()
    {
        return this.trip;
    }
    
    public void setTrip(Trip _trip)
    {
        this.trip = _trip;
    }
    
    public void setVehiculeKind(VehiculeKind _kind)
    {
        this.vehiculeKind = _kind;
    }
    
    public Vehicule awakeGenerator(Time _currentTime)
    {
        return null;
    }
    
}
