/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Trips;

import java.util.LinkedList;
import Domain.Node.Node;
import java.util.List;
/**
 *
 * @author Raphael
 */
public class Trip implements java.io.Serializable
{
    private List<Segment> allSegments;
    private String name;
    private int maxNumberVehicule;
    private boolean isCircular;
    
    public Trip()
    {
        this.allSegments = new LinkedList<>();
        this.name = "Trip";
    }
    
    public List<Segment> getAllSegments()
    {
        return this.allSegments;
    }
    
    public void setAllSegments(List<Segment> _allSegments)
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
            if(segment.getOriginNode().getName().equalsIgnoreCase(_node.getName()))
            {
                return segment;
            }
        }
        return null;
    }
    public void setMaxNumberVehicule(int _number)
    {
        this.maxNumberVehicule = _number;
    }
    public int getMaxNumberVehicule()
    {
        return this.maxNumberVehicule;
    }
    public boolean getIsCircular()
    {
        return isCircular;
    }
    public void setIsCircular(boolean _isCircular)
    {
        isCircular = _isCircular;
    }
    public boolean containsNode(String _nodeName)
    {
        for(int i = 0; i < this.getAllSegments().size(); i++)
        {
            Segment segment = this.getAllSegments().get(i);
            if(segment.getOriginNode().getName().equalsIgnoreCase(_nodeName))
            {
                return true;
            }
        }
        return false;
    }
    
}
