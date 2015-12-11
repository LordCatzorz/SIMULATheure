/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Generation;

import Domain.Client.*;
import Application.Controller.Time;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Raphael
 */
public class ClientGenerator implements java.io.Serializable
{
    private TriangularDistribution generationTimeDistribution;
    private TriangularDistribution generationClientNumberDistribution;
    private Time nextGenerationTime;
    private Time timeBeginGeneration;
    private Time timeEndGeneration;
    private ClientProfile clientProfile;
    private String name;
    
    public ClientGenerator(ClientProfile _clientProfile, double _minTime, double _maxTime, double _modeTime,
                           double _minNb, double _maxNb, double _modeNb, Time _startTime, Time _endTime, String _name)
    {
        this.clientProfile = _clientProfile;
        this.generationTimeDistribution = new TriangularDistribution(_minTime, _maxTime, _modeTime);
        this.generationClientNumberDistribution = new TriangularDistribution(_minNb, _maxNb, _modeNb);
        this.timeBeginGeneration = new Time(_startTime.getDay(), _startTime.getHour(), _startTime.getMinute(), _startTime.getSecond());
        this.timeEndGeneration = new Time(_endTime.getDay(), _endTime.getHour(), _endTime.getMinute(), _endTime.getSecond());
        this.name = _name;
        double triangle = this.generationTimeDistribution.calculate();
        double nextTime = _startTime.getTime();
        this.nextGenerationTime = new Time();
        this.nextGenerationTime.setTime(nextTime);
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setName(String _name)
    {
        this.name = _name;
    }
    
    public TriangularDistribution getGenerationTimeDistribution()
    {
        return this.generationTimeDistribution;
    }
    
    public void setGenerationTimeDistribution(double _minTime, double _maxTime, double _modeTime)
    {
        this.generationTimeDistribution = new TriangularDistribution(_minTime, _maxTime, _modeTime);
    }
    
    public TriangularDistribution getClientNumberDistribution()
    {
        return this.generationClientNumberDistribution;
    }
    
    public void setClientNumberDistribution(double _minNb, double _maxNb, double _modeNb)
    {
        this.generationClientNumberDistribution = new TriangularDistribution(_minNb, _maxNb, _modeNb);
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
    public void setClientProfile(ClientProfile _profile)
    {
        this.clientProfile = _profile;
    }
    
   /* public List<Client> awakeGenerator(Time _currentTime)
    {
        if(_currentTime.getTime() >= this.nextGenerationTime.getTime())
        {
            double timeTriangular = this.generationTimeDistribution.calculate();
            //double numberTriangular = this.generationClientNumberDistribution.calculate();
            
            long time = Math.round(timeTriangular);
            double nextTime = _currentTime.getTime() + Math.round(time)*60;
            //long number = Math.round(numberTriangular);
            
            List<Client> clients = new ArrayList();
           // for (int i = 0; i < number; i++) 
           // {
                clients.add(new Client(this.clientProfile));
           // }
            this.nextGenerationTime.setTime(nextTime);
            return clients;
        }
        else
            return null;
    }*/

    
    public List<Client> awakeGenerator(Time _currentTime)
    {
        Time nextChecker = new Time(0, nextGenerationTime.getHour(), nextGenerationTime.getMinute(), nextGenerationTime.getSecond());
        Time endChecker = new Time(0, timeEndGeneration.getHour(),timeEndGeneration.getMinute(), timeEndGeneration.getSecond());
        Time startChecker = new Time(0, timeBeginGeneration.getHour(),timeBeginGeneration.getMinute(), timeBeginGeneration.getSecond());
        Time currentChecker = new Time(0, _currentTime.getHour(), _currentTime.getMinute(), _currentTime.getSecond());
        boolean isSecondDay = false;
        if(currentChecker.getTime() < startChecker.getTime())
        {
            currentChecker.setDay(1);
            isSecondDay = true;
        }
        if(nextChecker.getTime() < startChecker.getTime())
        {
            nextChecker.setDay(1);
        }    
        if(endChecker.getTime() < startChecker.getTime())
        {
            endChecker.setDay(1);
        }
        if(nextChecker.getTime() <= currentChecker.getTime())
        {
            double triangle = this.generationTimeDistribution.calculate();
            double nextTime = currentChecker.getTime() + Math.round(triangle)*60;
            
            if(currentChecker.getTime() < endChecker.getTime())
            {
                this.nextGenerationTime = new Time();
                this.nextGenerationTime.setTime(nextTime);
                if(nextGenerationTime.getDay() > 1)
                {
                    nextGenerationTime.setDay(0);
                }
                Client newClient = new Client(this.clientProfile, _currentTime);
                Time newTime = new Time();
                newTime.setTime(currentChecker.getTime());
                newTime.setDay(0);
                List<Client> clients = new ArrayList();
                clients.add(newClient);
                return clients;

            }else{
                if(isSecondDay)
                {
                    startChecker.setDay(1);
                    if(currentChecker.getTime() < startChecker.getTime() && currentChecker.getTime() > endChecker.getTime())
                    {
                        this.nextGenerationTime = new Time(0, startChecker.getHour(), startChecker.getMinute(), startChecker.getSecond());
                    }
                }else{
                     if(currentChecker.getTime() > endChecker.getTime())
                     {
                         this.nextGenerationTime = new Time(0, startChecker.getHour(), startChecker.getMinute(), startChecker.getSecond());
                     }
                }
            }
        }
        return null;
    }
    
    
}
