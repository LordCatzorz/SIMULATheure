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
public class Time implements java.io.Serializable{
    private double hour;
    private double minute;
    private double second;
    private double day;
    public Time()
    {
        day = 0;
        hour = 0;
        minute = 0;
        second = 0;
    }
    public Time(double _day, double _hour, double _minute, double _second)
    {
        day = _day;
        hour = _hour;
        minute =_minute;
        second = _second;
    }
    public double getTime()
    {
        return (int)second+(60*(int)minute)+(3600*(int)hour) + (86400*(int)day);
    }
    public void setTime(double _time)
    {
        day = _time / 86400;
        double remainder = _time - ((int)day * 86400);
        hour =  (int) remainder / 3600;
        remainder = remainder - ((int)hour * 3600);
        minute = (int)remainder / 60;
        second = remainder - ((int)minute * 60);
    }
    public double getDay()
    {
        return day;
    }
    public void setDay(double _day)
    {
        this.day = _day;
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
    }
    public double getSecond()
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
