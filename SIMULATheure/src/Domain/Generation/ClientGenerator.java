/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Generation;

import java.sql.Time;
import java.time.LocalTime;
import Domain.Client.ClientProfile;
import Domain.Client.Client;
/**
 *
 * @author Raphael
 */
public class ClientGenerator implements java.io.Serializable
{
    private Distribution<Time> generationTimeDistribution;
    private Time nextGenerationTime;
    private Time timeBeginGeneration;
    private Time timeEndGeneration;
    private ClientProfile clientProfile;
    
    public ClientGenerator(ClientProfile _clientProfile)
    {
        this.clientProfile = _clientProfile;
        this.generationTimeDistribution = new Distribution();
    }
    
    public Distribution getGenerationTimeDistribution()
    {
        return this.generationTimeDistribution;
    }
    
    public Time getNextGenerationTime()
    {
        return this.nextGenerationTime;
    }
    
    public void setNextGenerationTime(Time _nextGenerationTime)
    {
        this.nextGenerationTime = _nextGenerationTime;
    }
    
    public Time getTimeBeginGeneration()
    {
        return this.timeBeginGeneration;
    }
    
    public void setTimeBeginGeneration(Time _timeBeginGeneration)
    {
        this.timeBeginGeneration = _timeBeginGeneration;
    }
    
    public Time getTimeEndGeneration()
    {
        return this.timeBeginGeneration;
    }
    
    public void setTimeEndGeneration(Time _timeEndGeneration)
    {
        this.timeEndGeneration = _timeEndGeneration;
    }
    
    public Client awakeGenerator(Time _currentTime)
    {
        if(_currentTime.getTime() >= this.nextGenerationTime.getTime())
        {
            this.nextGenerationTime.setTime(_currentTime.getTime() + this.generationTimeDistribution.calculate().getTime());
            return new Client(this.clientProfile);
        }
        else
            return null;
    }
    
    
}
