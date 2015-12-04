/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Client;

import java.sql.Time;
import java.util.Date;
import java.text.*;
/**
 *
 * @author Raphael
 */
public class Client implements java.io.Serializable
{
    private ClientProfile profile;
    private Itinary itinary;
    private Time creationTime;
    
    
    public Client(ClientProfile _profile)
    {
        this.profile = _profile;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        this.creationTime = Time.valueOf(dateFormat.format(new Date()));
        this.itinary = _profile.getItinary().get(0);
    }
    
    public Client()
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        this.creationTime = Time.valueOf(dateFormat.format(new Date()));
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
    
    public boolean nextItinary()
    {
        for(int i = 0; i < this.profile.getItinary().size(); i++)
        {
            if(this.profile.getItinary().get(i) == this.itinary)
            {
                if(i == this.profile.getItinary().size() - 1)//Last itinary
                {
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
