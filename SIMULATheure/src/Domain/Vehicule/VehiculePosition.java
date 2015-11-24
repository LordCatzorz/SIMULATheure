/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Vehicule;

import Domain.Trips.Segment;
import Domain.Positions.GeographicPosition;
import Application.Controller.Time;
/**
 *
 * @author Raphael
 */
public class VehiculePosition implements java.io.Serializable
{
    private Segment currentSegment;
    private Time timeSegmentStart;
    private GeographicPosition geographicPosition;
    
    public VehiculePosition(Segment _segment, Time _timeStart)
    {
        this.currentSegment = _segment;
        this.timeSegmentStart = _timeStart;
        this.geographicPosition = new GeographicPosition(this.currentSegment.getOriginNode().getGeographicPosition().getXPosition(), this.currentSegment.getOriginNode().getGeographicPosition().getYPosition() );
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
        double currentTime = _currentTime.getTime();
        double startTime = this.timeSegmentStart.getTime();
        double ellapsedTime = currentTime - startTime;
        double totalTime = (this.getCurrentSegment().getDurationTime())*60;
        double percentageCompleted =  (100 - ((totalTime - ellapsedTime)/totalTime)*100)/100;
        float xDifference = (this.currentSegment.getDestinationNode().getGeographicPosition().getXPosition()) - (this.currentSegment.getOriginNode().getGeographicPosition().getXPosition());
        int newX = (int) ((int) Math.round(this.currentSegment.getOriginNode().getGeographicPosition().getXPosition())) +  (int)(xDifference * (float)percentageCompleted);
        float yDifference = (this.currentSegment.getDestinationNode().getGeographicPosition().getYPosition()) - (this.currentSegment.getOriginNode().getGeographicPosition().getYPosition());
        int newY =  (int) ((int)Math.round(this.currentSegment.getOriginNode().getGeographicPosition().getYPosition())) +  (int) (yDifference * (float)percentageCompleted);
        this.getGeographicPosition().setXPosition(newX);
        this.getGeographicPosition().setYPosition(newY);
    }
    
}
