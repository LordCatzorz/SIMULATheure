/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Positions;

/**
 *
 * @author Raphael
 */
public class GeographicCoordinate {
    private int degree;
    private int minute;
    private float second;
    
    public GeographicCoordinate(int _degree, int _minute, float _second){
        this.setDegree(_degree);
        this.setMinute(_minute);
        this.setSecond(_second);
    }
    
    public int getDegree(){
        return this.degree;
    }
    
    public int getMinute(){
        return this.minute;
    }
    
    public float getSecond(){
        return this.second;
    }
    
    public void setDegree(int _degree){
        this.degree = _degree;
    }
    
    public void setMinute(int _minute){
        this.minute = _minute;
    }
    
    public void setSecond(float _second){
        this.second = _second;
    }
    
    public GeographicCoordinate add(GeographicCoordinate _geographicCoordinate){
        int tempDegree = 0;
        int tempMinute = 0;
        float tempSecond = 0;
        
        tempSecond = Float.sum(this.getSecond(), _geographicCoordinate.getSecond());
        
        int resultCompareSecond = Float.compare(Float.valueOf(60.0f), Float.valueOf(tempSecond));
        
        if (resultCompareSecond < 0)
        {
            tempSecond = Float.sum(tempSecond, Float.valueOf(-60.0f));
            tempMinute++;
        }
        else if (resultCompareSecond > 0)
        {
            tempSecond = Float.sum(tempSecond, Float.valueOf(60.0f));
            tempMinute--;
        }
        
        tempMinute +=  this.getMinute() + _geographicCoordinate.getMinute();
        
        if (tempMinute > 60)
        {
            tempMinute -= 60;
            tempDegree++;
        }
        else if (tempMinute < 0)
        {
            tempMinute += 60;
            tempDegree--;
        }
        
        
        tempDegree += this.getDegree() + _geographicCoordinate.getDegree();
        
        if (tempDegree > 180)
        {
            tempDegree -= 360;
        }
        else if (tempDegree < 180)
        {
            tempDegree += 360;
        }
        
        return new GeographicCoordinate(tempDegree, tempMinute, tempSecond);
    }
    
    public GeographicCoordinate substract(GeographicCoordinate _geographicCoordinate){
        throw new UnsupportedOperationException();
    }
    
    public GeographicCoordinate multiplyByScalar(float _scalar){
        throw new UnsupportedOperationException();
    }
    
    public boolean isEqual(GeographicCoordinate _geographicCoordinate){
        return (this.getDegree() == _geographicCoordinate.getDegree()) &&
               (this.getMinute() == _geographicCoordinate.getMinute()) &&
               (this.getSecond() == _geographicCoordinate.getSecond());
    }
}
