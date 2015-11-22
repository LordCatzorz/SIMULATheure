/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Node;

import Domain.Positions.GeographicPosition;

/**
 *
 * @author Raphael
 */
public class Node implements java.io.Serializable
{
    private GeographicPosition geographicPosition;
    private String name;
    private int diameter = 16;

    public Node(GeographicPosition _geographicPosition)
    {
        this.geographicPosition = _geographicPosition;
    }
    
    public GeographicPosition getGeographicPosition()
    {
        return this.geographicPosition;
    }
    
    public void setGeographicPosition(GeographicPosition _geographicPosition)
    {
        this.geographicPosition = _geographicPosition;
    }

    public String getName()
    {
        return this.name;
    }
    
    public void setName(String _name)
    {
        this.name = _name;
    }
    
    public int getDiameter()
    {
        return this.diameter;
    }
}
