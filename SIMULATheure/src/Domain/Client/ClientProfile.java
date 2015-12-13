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
    private List<List<Integer>> recordedClientNumberGenerated;
    private int numberRecordsPassageDuration;
    private int numberRecordsClient;
    private String name;
    
    public ClientProfile()
    {
        this.itinary = new ArrayList<>();
        this.recordedPassageDurations = new ArrayList<List<Time>>();
        this.recordedClientNumberGenerated = new ArrayList<List<Integer>>();
        this.numberRecordsPassageDuration = 0;
        this.numberRecordsClient = 0;
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
        if(_day >= this.recordedPassageDurations.size())
        {
            this.recordedPassageDurations.add(new ArrayList<Time>());
        }
        this.recordedPassageDurations.get(_day).add(_passageDuration);
        this.numberRecordsPassageDuration++;
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
        for(int i = 0; i < this.numberRecordsPassageDuration; i++)
        {
            sum +=this.recordedPassageDurations.get(_day).get(i).getTime();
        }
        sum = sum/this.numberRecordsPassageDuration;
        
        Time time  = new Time();
        time.setTime(sum);
        return time;
    }
    
    public void addClientNumber(int _number, int _day)
    {
        if(_day >= this.recordedClientNumberGenerated.size())
        {
            this.recordedClientNumberGenerated.add(new ArrayList<Integer>());
        }
        this.recordedClientNumberGenerated.get(_day).add(_number);
        this.numberRecordsClient++;
    }
    
    public List<Integer> getRecordedClientNumberGenerated(int _day)
    {
        return this.recordedClientNumberGenerated.get(_day);
    }
    
    public Integer getMinClientNumber(int _day)
    {
        return Collections.min(this.recordedClientNumberGenerated.get(_day), null);
    }
    
    public Integer getMaxClientNumber(int _day)
    {
        return Collections.max(this.recordedClientNumberGenerated.get(_day), null);
    }
    
    public Integer getAverageClientNumber(int _day)
    {
        int sum = 0;
        for(int i = 0; i < this.numberRecordsClient; i++)
        {
            sum +=this.recordedClientNumberGenerated.get(_day).get(i);
        }
        return (sum/this.numberRecordsClient);
    }
    
    public int getNumberRecordsClient()
    {
        return this.numberRecordsClient;
    }
    
    public int getNumberRecordsPassageDuration()
    {
        return this.numberRecordsPassageDuration;
    }
    
    public String getName(){
        return this.name;
    }
    public void setName(String _name)
    {
        this.name = _name;
    }
    
}
