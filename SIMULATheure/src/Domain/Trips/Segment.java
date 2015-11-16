/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Trips;

import Domain.Node.Node;
import Domain.Generation.Distribution;
import Domain.Positions.GeographicPosition;

import java.sql.Time;
/**
 *
 * @author Raphael
 */
public class Segment implements java.io.Serializable
{
    private Node originNode;
    private Node destinationNode;
    private Distribution durationDistribution;
    private Time durationTime;
    
    public Segment(Node _originNode, Node _destinationNode)
    {
        this.originNode = _originNode;
        this.destinationNode = _destinationNode;
        this.durationDistribution = new Distribution();
    }
    
    public Node getOriginNode()
    {
        return this.originNode;
    }
    
    public void setOriginNode(Node _originNode)
    {
        this.originNode = _originNode;
    }
    
    public Node getDestinationNode()
    {
        return this.destinationNode;
    }
    
    public void setDestinationNode(Node _destinationNode)
    {
        this.destinationNode = _destinationNode;
    }
    
    public Time getDurationTime()
    {
        return this.durationTime;
    }
    
    public void setDurationTime(Time _durationTime)
    {
        this.durationTime = _durationTime;
    }
    
    public Distribution getDurationDistribution()
    {
        return this.durationDistribution;
    }
   
    public boolean isPartOfSegment(GeographicPosition _position)
    {
        return false;
    }
  
}
