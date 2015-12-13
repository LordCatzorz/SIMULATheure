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
    private List<List<Time>> recordedPassageDurations;
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
    
    public void addPassageDuration(Time _passageDuration, int _day)
    {
       this.recordedPassageDurations.get(_day).add(_passageDuration);
       this.totalNumberRecords++;
    }
    
    public List<Time> getRecordedPassageDuration(int _day)
    {
        return this.recordedPassageDurations.get(_day);
    }
    
    public Time getMinPassageDuration(int _day)
    {
        return Collections.min(this.recordedPassageDurations.get(_day), null);
    }
    
    public Time getMaxPassageDuration(int _day)
    {
        return Collections.max(this.recordedPassageDurations.get(_day), null);
    }
    
    public Time getAveragePassageDuration(int _day)
    {
        double sum = 0;
        for(int i = 0; i < this.totalNumberRecords; i++)
        {
            sum +=this.recordedPassageDurations.get(_day).get(i).getTime();
        }
        sum = sum/this.totalNumberRecords;
        
        Time time  = new Time();
        time.setTime(sum);
        return time;
    }
    
    public String getName(){
        return this.name;
    }
    public void setName(String _name)
    {
        this.name = _name;
    }
    
}
