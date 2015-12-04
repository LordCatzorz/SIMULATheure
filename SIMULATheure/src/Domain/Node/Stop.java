/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Node;

import java.util.List;
import java.util.ArrayList;

import Domain.Client.Client;
import Domain.Positions.GeographicPosition;
import Domain.Trips.Trip;
/**
 *
 * @author Raphael
 */
public class Stop extends Node implements java.io.Serializable
{
    private List<Client> waitingClients;
    private String name;
    
    public Stop(GeographicPosition _geographicPosition, String _name)
    {
       super(_geographicPosition);
       this.name = _name;
       this.waitingClients = new ArrayList<>();
    }
    
    public List<Client> getClients()
    {
        return this.waitingClients;
    }
    
    public boolean addClient(Client _client)
    {
        return this.waitingClients.add(_client);
    }
    
    public boolean addClient(List<Client> _clients)
    {
        if(this.waitingClients.size() <= 0 )
        {
            return false;
        }
        return this.waitingClients.addAll(this.waitingClients.size() - 1, _clients);
    }
    
    public boolean removeClient(Client _client)
    {
        return this.waitingClients.remove(_client);
    }
    
    public List<Client> requestEmbarkmentClient(Trip _vehiculeTrip, int maxClientAmount)
    {
        List<Client> clients = new ArrayList();
        for(int i = 0; i < this.waitingClients.size(); i++)
        {
            if(this.waitingClients.get(i).getCurrentItinary().getTrip() == _vehiculeTrip && clients.size() <= maxClientAmount)
            {
                clients.add(this.waitingClients.get(i));
                this.waitingClients.remove(i);
                i--;
            }
        }
        return clients;
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
