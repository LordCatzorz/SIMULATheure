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
    private int minimum;
    private int maximum;
    private int mode;
    
    public TriangularDistribution(int _minimum, int _maximum, int _mode)
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
    
    public int getMinimum()
    {
        return this.minimum;
    }
    
    public int getMaximum()
    {
        return this.maximum;
    }
    public int getMode()
    {
        return this.mode;
    }
    
    public void setMinimum(int _minimum)
    {
        this.minimum = _minimum;
    }
    
    public void setMaximum(int _maximum)
    {
        this.maximum = _maximum;
    }
    
    public void setMode(int _mode)
    {
        this.mode = _mode;
    }
}
