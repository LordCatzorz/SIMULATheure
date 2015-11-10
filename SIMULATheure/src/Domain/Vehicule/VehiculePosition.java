/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Vehicule;

import java.sql.Time;

import Domain.Trips.Segment;
import Domain.Positions.GeographicPosition;
/**
 *
 * @author Raphael
 */
public class VehiculePosition implements java.io.Serializable
{
    private Segment currentSegment;
    private Time timeSegmentStart;
    private GeographicPosition geographicPosition;
    
    public void VehiculePosition(Segment _segment, Time _timeStart)
    {
        this.currentSegment = _segment;
        this.timeSegmentStart = _timeStart;
    }
    
    public float getCompletionPercentage()
    {
        return 0;
    }
    
    public Segment getCurrentSegment()
    {
        return this.currentSegment;
    }
    
    public void setCurrentSegment(Segment _segment)
    {
        this.currentSegment = _segment;
    }
    
    public Time getTimeSegmentStart()
    {
        return this.timeSegmentStart;
    }
    
    public void setTimeStartSegment(Time _time)
    {
        this.timeSegmentStart = _time;
    }
    
    public GeographicPosition getGeographicPosition()
    {
        return this.geographicPosition;
    }
    
    public void setGeographicPosition(GeographicPosition _geographicPosition)
    {
        this.geographicPosition = _geographicPosition;
    }
    
    public void update(Time _currentTime)
    {
        
    }
    
}
