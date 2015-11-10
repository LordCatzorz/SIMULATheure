/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Node;

import java.util.List;

import Domain.Client.Client;
import Domain.Positions.GeographicPosition;
import Domain.Trips.Trip;
/**
 *
 * @author Raphael
 */
public class Stop implements java.io.Serializable
{
    private List<Client> waitingClients;
    private String name;
    
    public void Stop(GeographicPosition _geographic)
    {
       
    }
    
    public List<Client> getClients()//ou private?? (Diagramme de classe)
    {
        return this.waitingClients;
    }
    
    public boolean addClient(Client _client)
    {
        return this.waitingClients.add(_client);
    }
    
    public boolean addClient(List<Client> _clients)
    {
        return this.waitingClients.addAll(this.waitingClients.size() - 1, _clients);
    }
    
    public boolean removeClient(Client _client)
    {
        return this.waitingClients.remove(_client);
    }
    
    public List<Client> requestEmbarkmentClient(Trip _vehiculeTrip, int maxClientAmount)
    {
        return null;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setName(String _name)
    {
        this.name = _name;
    }
}
