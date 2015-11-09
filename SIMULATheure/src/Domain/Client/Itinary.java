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
    
    public void Itinary(Stop _originStop, Stop _destinationStop, Trip _trip)
    {
        this.originStop = _originStop;
        this.destinationStop = _destinationStop;
        this.trip = _trip;
    }
    
    public Stop getOriginStop()
    {
        return this.originStop;
    }
    
    public void setOriginStop(Stop _stop)
    {
        this.originStop = _stop;
    }
    
    public Stop getDestinationStop()
    {
        return this.destinationStop;
    }
    
    public void setDestinationStop(Stop _stop)
    {
        this.destinationStop = _stop;
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
