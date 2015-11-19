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
    private float xPosition;
    private float yPosition;
    
    public GeographicPosition(float _x, float _y)
    {
        this.xPosition = _x;
        this.yPosition = _y;
    }
    
    public float getXPosition()
    {
        return this.xPosition;
    }
    
    public void setXPosition(float _x)
    {
        this.xPosition = _x;
    }
    
    public float getYPosition()
    {
        return this.yPosition;
    }
    
    public void setYPosition(float _y)
    {
        this.yPosition = _y;
    }
    
    public float getDistance(GeographicPosition _destinationGeographicPosition)
    {
        float xDistance = _destinationGeographicPosition.getXPosition() - this.xPosition;
        float yDistance = _destinationGeographicPosition.getYPosition() - this.yPosition;
        
        return (float)Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance,2)); //Pythagore
    }
    
    public boolean isBetween(GeographicPosition _firstPosition, GeographicPosition _secondPosition)
    {
        return this.getDistance(_firstPosition) + this.getDistance(_secondPosition) == _firstPosition.getDistance(_secondPosition);
    }
    
    public float getAngle(GeographicPosition _destinationGeographicPosition)
    {
        float xDistance = _destinationGeographicPosition.getXPosition() - this.xPosition;
        float yDistance = _destinationGeographicPosition.getYPosition() - this.yPosition;
        
        double angle = Math.toDegrees(Math.atan(yDistance/ xDistance));
        
        if (xDistance < 0)
        {
            if (yDistance < 0)// 3e quadrant
            {
                angle = 270 - angle;
            }
            else // 2e quandrant
            {
                angle = 180 + angle;
            }
        }
        else if(yDistance < 0) //4e quadrant
        {
            angle = 360 + angle;
        }
        
        return (float)angle;
    }
}

