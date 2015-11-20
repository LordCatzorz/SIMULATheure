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
    private TriangularDistribution<Time> distribution;
    private Time timeBeginGeneration;
    private Time timeEndGeneration;
    private Segment spawnSegment;
    private VehiculeKind vehiculeKind;
    private Trip trip;
    
    public VehiculeGenerator(Trip _trip, Segment _spawnSegment, VehiculeKind _vehiculeKind)
    {
        //this.distribution = new TriangularDistribution();
        this.trip = _trip;
        this.spawnSegment = _spawnSegment;
        this.vehiculeKind = _vehiculeKind;
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
    
    public Vehicule awakeGenerator(Time _currentTime)
    {
        return null;
    }
    
}
