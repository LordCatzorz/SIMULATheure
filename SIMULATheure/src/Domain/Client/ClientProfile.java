/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Client;

import java.util.List;
import java.util.ArrayList;
import java.sql.Time;
/**
 *
 * @author Raphael
 */
public class ClientProfile implements java.io.Serializable
{
    private List<Itinary> itinary;
    private Time minRecordedPassageDuration;
    private Time maxRecordedPassageDuration;
    private Time averageRecordedPassageDuration;
    private int totalNumberRecords;
    
    public ClientProfile()
    {
        this.itinary = new ArrayList<>();
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
    
    public Time getMinRecordedPassageDuration()
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
    }
    
    
}
