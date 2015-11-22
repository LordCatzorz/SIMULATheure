/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Client;

import Domain.Node.*;
import Domain.Trips.Trip;
/**
 *
 * @author Raphael
 */
public class Itinary implements java.io.Serializable
{
    private Stop originStop;
    private Stop destinationStop;
    private Trip trip;
    
    public Itinary(Stop _originStop, Stop _destinationStop, Trip _trip)
    {
        this.originStop = _originStop;
        this.destinationStop = _destinationStop;
        this.trip = _trip;
    }
    
    public Stop getOriginStop()
    {
        return this.originStop;
    }
    
    public void setOriginStop(Stop _originStop)
    {
        this.originStop = _originStop;
    }
    
    public Stop getDestinationStop()
    {
        return this.destinationStop;
    }
    
    public void setDestinationStop(Stop _destinationStop)
    {
        this.destinationStop = _destinationStop;
    }
    
    public Trip getTrip()
    {
        return this.trip;
    }
    
    public void setTrip(Trip _trip)
    {
        this.trip = _trip;
    }
}
