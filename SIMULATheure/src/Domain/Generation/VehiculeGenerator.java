/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Generation;


import Application.Controller.Time;
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
    private static int generatedCounter = 0;
    
    public VehiculeGenerator()
    {
    }
    
    public VehiculeGenerator(Segment _spawnSegment, Trip _trip, double _min, double _max, double _mode, Time _startTime, Time _endTime, String _name)
    {
        this.spawnSegment = _spawnSegment;
        this.timeBeginGeneration = new Time(_startTime.getDay(), _startTime.getHour(), _startTime.getMinute(), _startTime.getSecond());
        this.timeEndGeneration =new Time(_endTime.getDay(), _endTime.getHour(), _endTime.getMinute(), _endTime.getSecond());
        this.trip = _trip;
        this.name = _name;
        this.distribution = new TriangularDistribution(_min,_max,_mode);
        double nextTime = _startTime.getTime();
        this.nextDepartureTime = new Time();
        this.nextDepartureTime.setTime(nextTime);

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
        /*if(_currentTime.getTime() == new Time().getTime())
        {
            if(timeEndGeneration.getHour() >= 24)
            {
                this.timeEndGeneration.setHour(this.timeEndGeneration.getHour() - 24);
            }
            if(nextDepartureTime.getHour() >= 24)
            {
                this.nextDepartureTime.setHour(this.nextDepartureTime.getHour() - 24);
            }
        }*/
        Time nextChecker = new Time(0, nextDepartureTime.getHour(), nextDepartureTime.getMinute(), nextDepartureTime.getSecond());
        Time endChecker = new Time(0, timeEndGeneration.getHour(),timeEndGeneration.getMinute(), timeEndGeneration.getSecond());
        Time startChecker = new Time(0, timeBeginGeneration.getHour(),timeBeginGeneration.getMinute(), timeBeginGeneration.getSecond());
        Time currentChecker = new Time(0, _currentTime.getHour(), _currentTime.getMinute(), _currentTime.getSecond());
        if(nextChecker.getTime() <= currentChecker.getTime())
        {
            double triangle = this.distribution.calculate();
            double nextTime = currentChecker.getTime() + Math.round(triangle)*60;
            
            boolean isSecondDay = false;
            if(currentChecker.getTime() < startChecker.getTime())
            {
                currentChecker.setDay(1);
                isSecondDay = true;
            }
            if(endChecker.getTime() < startChecker.getTime())
            {
                endChecker.setDay(1);
            }
            
            if(currentChecker.getTime() < endChecker.getTime())
            {
                this.nextDepartureTime = new Time();
                this.nextDepartureTime.setTime(nextTime);
                if(nextDepartureTime.getDay() > 1)
                {
                    nextDepartureTime.setDay(0);
                }
                generatedCounter++;
                Vehicule newVehicule = new Vehicule(this.trip, this.spawnSegment, this.name + " " + generatedCounter);
                Time newTime = new Time();
                newTime.setTime(currentChecker.getTime());
                newTime.setDay(0);
                newVehicule.getCurrentPosition().setTimeStartSegment(newTime);
                return newVehicule;

            }else{
                if(isSecondDay)
                {
                    startChecker.setDay(1);
                    if(currentChecker.getTime() < startChecker.getTime() && currentChecker.getTime() > endChecker.getTime())
                    {
                        this.nextDepartureTime = new Time(0, startChecker.getHour(), startChecker.getMinute(), startChecker.getSecond());
                    }
                }else{
                     if(currentChecker.getTime() > endChecker.getTime())
                     {
                         this.nextDepartureTime = new Time(0, startChecker.getHour(), startChecker.getMinute(), startChecker.getSecond());
                     }
                }
            }
        }
        return null;
    }
    
}
