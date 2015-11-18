/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Node;

import java.util.List;

import Domain.Positions.GeographicPosition;
import Domain.Client.Client;
import Domain.Trips.Trip;
/**
 *
 * @author Raphael
 */
public class Node implements java.io.Serializable
{
    private GeographicPosition geographicPosition;
    private int diameter = 8;
    
    //abstract public boolean addClient(List<Client> _clients);
    //abstract public List<Client> requestEmbarkmentClient(Trip _vehiculeTrip, int maxClientAmount);
    
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
    
    public int getDiameter()
    {
        return this.diameter;
    }
}
