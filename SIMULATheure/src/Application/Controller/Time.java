/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Controller;

import java.math.BigDecimal;

/**
 *
 * @author Pierre
 */
public class Time {
    private double hour;
    private double minute;
    private double second;
    public Time()
    {
        hour = 0;
        minute = 0;
        second = 0;
    }
    public Time(double _hour, double _minute, double _second)
    {
        hour = _hour;
        minute =_minute;
        second = _second;
    }
    public double getTime()
    {
        return second+(60*minute)+(3600*hour);
    }
    public void setTime(double _time)
    {
        hour =  _time / 3600;
        double remainder = _time - hour * 3600;
        minute = remainder / 60;
        second = remainder - minute * 60;
    }
    public double getHour()
    {
        return hour;
    }
    public void setHour(Double _hour)
    {
        this.hour = _hour;
    }
    public double getMinute()
    {
        return minute;
    }
    public void setMinute(Double _minute)
    {
        this.minute = _minute;
    }public double getSecond()
    {
        return second;
    }
    public void setSecond(Double _second)
    {
        this.hour = _second;
    }
    public String getTimeStringNoSecond()
    {
        String timeString="";
        System.out.println(minute);
        System.out.println(Math.round(minute));
        System.out.println((int) Math.round(minute));
        System.out.println(hour);
        System.out.println(Math.round(hour));
        System.out.println((int) Math.round(hour));
        
        int hourAmount = (int) Math.round(hour);
        if(hourAmount >= 24)
        {
            hourAmount = hourAmount - 24;
        }
        if(!(hour < 10))
        {
            timeString+= hourAmount+ ":";
        }else{
            timeString+="0" +hourAmount+ ":";
        }
        if(minute <10)
        {
             timeString+="0" +(int) Math.round(minute);
        }else{
             timeString+=(int) Math.round(minute);
        }        
        return timeString;
    }
    
}
