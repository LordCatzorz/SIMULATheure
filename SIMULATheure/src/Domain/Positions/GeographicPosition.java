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
public class GeographicPosition implements java.io.Serializable
{
    private GeographicCoordinate longitude;
    private GeographicCoordinate latitude;
    
    public GeographicPosition(GeographicCoordinate _longitude, GeographicCoordinate _latitude)
    {
        this.longitude = _longitude;
        this.latitude = _latitude;
    }
    
    public GeographicCoordinate getLongitude()
    {
        return this.longitude;
    }
    
    public void setLongitude(GeographicCoordinate _longitude)
    {
        this.longitude = _longitude;
    }
    
    public GeographicCoordinate getLatitude()
    {
        return this.latitude;
    }
    
    public void setLatitude(GeographicCoordinate _latitude)
    {
        this.latitude = _latitude;
    }
    
    public float getDistance(GeographicPosition _destinationGeographicPosition)
    {
        float longitudeDistance = _destinationGeographicPosition.getLongitude().getFloatConvertion() - this.longitude.getFloatConvertion();
        float latitudeDistance = _destinationGeographicPosition.getLatitude().getFloatConvertion() - this.latitude.getFloatConvertion();
        
        return (float)Math.sqrt(Math.pow(latitudeDistance, 2) + Math.pow(longitudeDistance,2)); //Pythagore
    }
    
    public boolean isBetween(GeographicPosition _firstPosition, GeographicPosition _secondPosition)
    {
        return this.getDistance(_firstPosition) + this.getDistance(_secondPosition) == _firstPosition.getDistance(_secondPosition);
    }
    
    public float getAngle(GeographicPosition _destinationGeographicPosition)
    {
        float longitudeDistance = _destinationGeographicPosition.getLongitude().getFloatConvertion() - this.longitude.getFloatConvertion();
        float latitudeDistance = _destinationGeographicPosition.getLatitude().getFloatConvertion() - this.latitude.getFloatConvertion();
        
        double angle = Math.toDegrees(Math.atan(latitudeDistance/ longitudeDistance));
        
        if (longitudeDistance < 0)
        {
            if (latitudeDistance < 0)// 3e quadrant
            {
                angle = 270 - angle;
            }
            else // 2e quandrant
            {
                angle = 180 + angle;
            }
        }
        else if(latitudeDistance < 0) //4e quadrant
        {
            angle = 360 + angle;
        }
        
        return (float)angle;
    }
}

