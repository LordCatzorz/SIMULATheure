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
    
    public void Client()
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        this.creationTime = Time.valueOf(dateFormat.format(new Date()));
    }
    
    public void Client(ClientProfile _profile)
    {
        this.profile = _profile;
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
    
    public Time getCreationTime()
    {
        return this.creationTime;
    }
    
    public void setCreationTime(Time _time)
    {
        this.creationTime = _time;
    }
    
    
}
