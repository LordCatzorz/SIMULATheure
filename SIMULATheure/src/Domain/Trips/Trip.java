/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Trips;

import java.util.Queue;
import java.util.LinkedList;
import java.sql.Time;

import Domain.Node.Node;
/**
 *
 * @author Raphael
 */
public class Trip implements java.io.Serializable
{
    private Queue<Segment> allSegments;
    private Time nextDepartureTime;
    private String name;
    
    public Trip()
    {
        this.allSegments = new LinkedList<>();
        this.name = "Trip";
    }
    
    public Queue<Segment> getAllSegments()
    {
        return this.allSegments;
    }
    
    public void setAllSegments(Queue<Segment> _allSegments)
    {
        this.allSegments = _allSegments;
    }
    
    public void addSegment(Segment _segmentToAdd)
    {
        this.allSegments.add(_segmentToAdd);
    }
    
    public void removeSegment(Segment _segmentToRemove)
    {
        this.allSegments.remove(_segmentToRemove);
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setName(String _name)
    {
        this.name = _name;
    }
    
    public Segment getNextSegment(Node _node)
    {
        for(Segment segment: this.allSegments)
        {
            if(segment.getOriginNode() == _node)
            {
                return segment;
            }
        }
        return null;
    }
}
