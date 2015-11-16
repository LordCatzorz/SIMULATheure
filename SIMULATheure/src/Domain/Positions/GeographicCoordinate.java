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
public class GeographicCoordinate implements java.io.Serializable
{
    private int degree;
    private int minute;
    private float second;

    public GeographicCoordinate(int _degree, int _minute, float _second) 
    {
        this.resetCoordinate();
        this.addToDegree(_degree);
        this.addToMinute(_minute);
        this.addToSecond(_second);
    }

    public int getDegree() 
    {
        return this.degree;
    }

    public int getMinute() 
    {
        return this.minute;
    }

    public float getSecond() 
    {

        return this.second;
    }

    private void addToDegree(int _degree) 
    {
        int tempDegree = this.degree + _degree;

        tempDegree = adjustDegree(tempDegree);

        this.degree = tempDegree;
    }

    private void addToMinute(int _minute) 
    {
        int tempMinute = this.minute + _minute;

        tempMinute = adjustDegreeWithMinutes(tempMinute);
        
        this.minute = tempMinute;
    }

    private void addToSecond(float _second) 
    {
        float tempSecond = Float.sum(this.second, _second);

        tempSecond = adjustMinuteWithSeconds(tempSecond);

        this.second = tempSecond;
    }

    private int adjustDegree(int _degree) 
    {
        if (_degree > 180) {
            _degree -= 360;
        } else if (_degree < -180) {
            _degree += 360;
        }
        return _degree;
    }

    private int adjustDegreeWithMinutes(int _minute) 
    {
        boolean madeModification;
        do {
            madeModification = false;
            if (_minute >= 60) {
                _minute -= 60;
                addToDegree(1);
                madeModification = true;
            } else if (_minute < 0) {
                _minute += 60;
                addToDegree(-1);
                madeModification = true;
            }
        } while (madeModification == true);
        return _minute;
    }

    private float adjustMinuteWithSeconds(float _second) 
    {

        boolean madeModification;
        do {
            madeModification = false;
            if (_second >= 60.0f) {
                _second = Float.sum(_second, -60.0f);
                this.addToMinute(1);
                madeModification = true;
            }
            if (_second < 0f) {
                _second = Float.sum(_second, 60.0f);
                this.addToMinute(-1);
                madeModification = true;
            }
        } while (madeModification == true);

        return _second;
    }

    public float getFloatConvertion()
    {
        return this.degree + (this.minute/60) + (this.second/ 3600);
    }
    
    public GeographicCoordinate add(GeographicCoordinate _geographicCoordinate) 
    {
        GeographicCoordinate addedCoordinate = new GeographicCoordinate(this.getDegree(),
                this.getMinute(),
                this.getSecond());

        addedCoordinate.addToSecond(_geographicCoordinate.getSecond());
        addedCoordinate.addToMinute(_geographicCoordinate.getMinute());
        addedCoordinate.addToDegree(_geographicCoordinate.getDegree());

        return addedCoordinate;
    }

    public GeographicCoordinate substract(GeographicCoordinate _geographicCoordinate) 
    {
        throw new UnsupportedOperationException();
    }

    public GeographicCoordinate multiplyByScalar(float _scalar) 
    {
        throw new UnsupportedOperationException();
    }

    public boolean isEqual(GeographicCoordinate _geographicCoordinate) 
    {
        return (this.getDegree() == _geographicCoordinate.getDegree())
                && (this.getMinute() == _geographicCoordinate.getMinute())
                && (this.getSecond() == _geographicCoordinate.getSecond());
    }

    private void resetCoordinate() 
    {
        this.degree = 0;
        this.minute = 0;
        this.second = 0;
    }

    @Override
    public boolean equals(Object other) 
    {
        if (!(other instanceof GeographicCoordinate)) 
        {
            return false;
        }
        GeographicCoordinate otherGeographicCoordinate = (GeographicCoordinate) other;
        return this.isEqual(otherGeographicCoordinate);
    }
}
