/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Client;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import Application.Controller.Time;
/**
 *
 * @author Raphael
 */
public class ClientProfile implements java.io.Serializable
{
    private List<Itinary> itinary;
    //private Time minRecordedPassageDuration;
    //private Time maxRecordedPassageDuration;
    //private Time averageRecordedPassageDuration;
    private List<Time> recordedPassageDurations;
    private int totalNumberRecords;
    private String name;
    
    public ClientProfile()
    {
        this.itinary = new ArrayList<>();
        this.recordedPassageDurations = new ArrayList<>();
        this.totalNumberRecords = 0;
    }
    
    public List<Itinary> getItinary()
    {
        return this.itinary;
    }
    
    public void setItinary(List<Itinary> _itinary)
    {
        this.itinary = _itinary;
    }
    
    public boolean addItinary(Itinary _itinary)
    {
        return this.itinary.add(_itinary);
    }
    
    public boolean removeFirstItinary()
    {
        return this.itinary.remove(0) != null;
    }
    
    public boolean removeLastItinary()
    {
        return this.itinary.remove(this.itinary.size() - 1) != null;
    }
    
    public void addPassageDuration(Time _passageDuration)
    {
       this.recordedPassageDurations.add(_passageDuration);
       this.totalNumberRecords++;
    }
    
    public List<Time> getRecordedPassageDuration()
    {
        return this.recordedPassageDurations;
    }
    
    public Time getMinPassageDuration()
    {
        return Collections.min(this.recordedPassageDurations, null);
    }
    
    public Time getMaxPassageDuration()
    {
        return Collections.max(this.recordedPassageDurations, null);
    }
    
    public Time getAveragePassageDuration()
    {
        double sum = 0;
        for(int i = 0; i < this.totalNumberRecords; i++)
        {
            sum +=this.recordedPassageDurations.get(i).getTime();
        }
        sum = sum/this.totalNumberRecords;
        
        Time time  = new Time();
        time.setTime(sum);
        return time;
    }
    
    /*public Time getMinRecordedPassageDuration()
    {
        return this.minRecordedPassageDuration;
    }
    
    public void setMinRecordedPassageDuration(Time _time)
    {
        this.minRecordedPassageDuration = _time;
    }
    
    public Time getMaxRecordedPassageDuration()
    {
        return this.maxRecordedPassageDuration;
    }
    
    public void setMaxRecordedPassageDuration(Time _time)
    {
        this.maxRecordedPassageDuration = _time;
    }
    
    public Time setAverageRecordedPassageDuration()
    {
        return this.averageRecordedPassageDuration;
    }
    
    public void setAverageRecordedPassageDuration(Time _time)
    {
        this.averageRecordedPassageDuration = _time;
    }*/
    
    public String getName(){
        return this.name;
    }
    public void setName(String _name)
    {
        this.name = _name;
    }
    
}
