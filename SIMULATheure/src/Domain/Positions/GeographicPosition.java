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
    
    public float getDistance(GeographicPosition _geographicPosition)
    {
        return 0;
    }
}

