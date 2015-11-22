/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Generation;


import Domain.Simulation.Time;
import Domain.Trips.Segment;
import Domain.Trips.Trip;
import Domain.Vehicule.Vehicule;
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
    private Trip trip;
    private String name;
    
    public VehiculeGenerator()
    {
    }
    
    public VehiculeGenerator(Segment _spawnSegment, Trip _trip, double _min, double _max, double _mode, Time _startTime, Time _endTime, String _name)
    {
        this.spawnSegment = _spawnSegment;
        this.timeBeginGeneration = _startTime;
        this.timeEndGeneration = _endTime;
        this.trip = _trip;
        this.name = _name;
        distribution = new TriangularDistribution(_min,_max,_mode);
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
    
    public void setDistribution(double _min, double _max, double _mode)
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
    public void setSpawnSegment(Segment _spawnSegment)
    {
        this.spawnSegment = _spawnSegment;
    }
    public Trip getTrip()
    {
        return this.trip;
    }
    
    public void setTrip(Trip _trip)
    {
        this.trip = _trip;
    }
    public Vehicule awakeGenerator(Time _currentTime)
    {
        return null;
    }
    
}
