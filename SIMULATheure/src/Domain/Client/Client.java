/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Client;

import java.util.Date;
import java.text.*;

import Application.Controller.Time;
/**
 *
 * @author Raphael
 */
public class Client implements java.io.Serializable
{
    private ClientProfile profile;
    private Itinary itinary;
    private Time creationTime;
    
    
    public Client(ClientProfile _profile, Time _currentTime)
    {
        this.profile = _profile;
        this.creationTime = new Time(_currentTime.getDay(), _currentTime.getHour(), _currentTime.getMinute(), _currentTime.getSecond());
        this.itinary = new Itinary(_profile.getItinary().get(0));
    }
    public Client(Client _client)
    {
        this.creationTime = _client.creationTime;
        this.itinary = _client.itinary;
        this.profile = _client.profile;
    }
    
    public ClientProfile getProfile()
    {
        return this.profile;
    }
    
    public void setProfile(ClientProfile _profile)
    {
        this.profile = _profile;
    }
    
    public Itinary getCurrentItinary()
    {
        return this.itinary;
    }
    
    public void setCurrentItinary(Itinary _itinary)
    {
        this.itinary = _itinary;
    }
    
    public boolean nextItinary(Time _currentTime)
    {
        for(int i = 0; i < this.profile.getItinary().size(); i++)
        {
            if(this.profile.getItinary().get(i).getOriginStop() == this.itinary.getOriginStop() &&
                    this.profile.getItinary().get(i).getDestinationStop() == this.itinary.getDestinationStop() &&
                    this.profile.getItinary().get(i).getTrip()== this.itinary.getTrip())
            {
                if(i == this.profile.getItinary().size() - 1)//Last itinary
                {
                    Time time = new Time();
                    time.setTime(_currentTime.getTime() - this.creationTime.getTime());
                    this.profile.addPassageDuration(time, (int)_currentTime.getDay());
                    this.profile.addClientNumber(1, (int)_currentTime.getDay());
                    return false;
                }
                else
                {
                    this.itinary = this.profile.getItinary().get(i+1);
                    return true;
                }
            }
        }
        return false;
    }
    
    public Time getCreationTime()
    {
        return this.creationTime;
    }
    
    public void setCreationTime(Time _time)
    {
        this.creationTime = _time;
    }
    
    
}
