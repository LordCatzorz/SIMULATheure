/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Generation;

import Domain.Client.ClientProfile;
import Domain.Client.Client;
import Application.Controller.Time;
/**
 *
 * @author Raphael
 */
public class ClientGenerator implements java.io.Serializable
{
    private TriangularDistribution generationTimeDistribution;
    private Time nextGenerationTime;
    private Time timeBeginGeneration;
    private Time timeEndGeneration;
    private ClientProfile clientProfile;
    
    public ClientGenerator(ClientProfile _clientProfile)
    {
        this.clientProfile = _clientProfile;
        //this.generationTimeDistribution = new TriangularDistribution();
    }
    
    public TriangularDistribution getTimeDistribution()
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
    
    public ClientProfile getClientProfile()
    {
        return this.clientProfile;
    }
    
    public Client awakeGenerator(Time _currentTime)
    {
        if(_currentTime.getTime() >= this.nextGenerationTime.getTime())
        {
            double triangular = this.generationTimeDistribution.calculate();
            long time = Math.round(triangular);
            this.nextGenerationTime.setTime(_currentTime.getTime() + time);
            return new Client(this.clientProfile);
        }
        else
            return null;
    }
    
    
}
