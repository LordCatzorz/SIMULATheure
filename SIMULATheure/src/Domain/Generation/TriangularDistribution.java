/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Generation;
import java.util.Random;
import static java.lang.Math.sqrt;

/**
 *
 * @author Raphael
 */
public class TriangularDistribution implements java.io.Serializable
{
    private float minimum;
    private float maximum;
    private float mode;
    
    public TriangularDistribution(float _minimum, float _maximum, float _mode)
    {
        this.maximum = _maximum;
        this.minimum = _minimum;
        this.mode = _mode;
    }
    
    public double calculate()
    {
        double proportion = (mode - minimum)/(maximum - minimum);
        Random randomGenerator = new Random();
        double randomInt = randomGenerator.nextInt(100);
        double randomizedProportion;
        if(randomInt <= proportion)
        {
            randomizedProportion = sqrt(randomInt/100 * proportion);
        }else{
            randomizedProportion = 1 - (sqrt((1-(randomInt/100)) * (1-proportion)));
        }
        return (minimum + (maximum - minimum)*randomizedProportion); 
    }
    
    public float getMinimum()
    {
        return this.minimum;
    }
    
    public float getMaximum()
    {
        return this.maximum;
    }
    public float getMode()
    {
        return this.mode;
    }
    
    public void setMinimum(float _minimum)
    {
        this.minimum = _minimum;
    }
    
    public void setMaximum(float _maximum)
    {
        this.maximum = _maximum;
    }
    
    public void setMode(float _mode)
    {
        this.mode = _mode;
    }
}
