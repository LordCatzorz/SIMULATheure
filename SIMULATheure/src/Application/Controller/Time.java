/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Controller;
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
        return (int)second+(60*(int)minute)+(3600*(int)hour);
    }
    public void setTime(double _time)
    {
        hour =  _time / 3600;
        double remainder = _time - ((int)hour * 3600);
        minute = (int)remainder / 60;
        second = remainder - ((int)minute * 60);
    }
    public double getHour()
    {
        return hour;
    }
    public void setHour(double _hour)
    {
        this.hour = _hour;
    }
    public double getMinute()
    {
        return minute;
    }
    public void setMinute(double _minute)
    {
        this.minute = _minute;
    }public double getSecond()
    {
        return second;
    }
    public void setSecond(double _second)
    {
        this.hour = _second;
    }
    public String getTimeStringNoSecond()
    {
        String timeString="";       
        int hourAmount = (int) Math.floor(hour);
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
             timeString+="0" +(int) Math.floor(minute);
        }else{
             timeString+=(int) Math.floor(minute);
        }
        return timeString;
    }
    
}
