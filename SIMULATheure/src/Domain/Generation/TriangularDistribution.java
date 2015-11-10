/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Generation;

/**
 *
 * @author Raphael
 */
public class TriangularDistribution<T> extends Distribution implements java.io.Serializable
{
    private T minimum;
    private T maximum;
    private T mode;
    
    public void TriangularDistribution(T _minimum, T _maximum, T _mode)
    {
        this.maximum = _maximum;
        this.minimum = _minimum;
        this.mode = _mode;
    }
    
    @Override public T calculate()
    {
        return null;
    }
    
    public T getMinimum()
    {
        return this.minimum;
    }
    
    public T getMaximum()
    {
        return this.maximum;
    }
    public T getMode()
    {
        return this.mode;
    }
    
    public void setMinimum(T _minimum)
    {
        this.minimum = _minimum;
    }
    
    public void setMaximum(T _maximum)
    {
        this.maximum = _maximum;
    }
    
    public void setMode(T _mode)
    {
        this.mode = _mode;
    }
}
