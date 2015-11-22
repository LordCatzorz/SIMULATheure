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
    private double minimum;
    private double maximum;
    private double mode;
    
    public TriangularDistribution(double _minimum, double _maximum, double _mode)
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
    
    public double getMinimum()
    {
        return this.minimum;
    }
    
    public double getMaximum()
    {
        return this.maximum;
    }
    public double getMode()
    {
        return this.mode;
    }
    
    public void setMinimum(double _minimum)
    {
        this.minimum = _minimum;
    }
    
    public void setMaximum(double _maximum)
    {
        this.maximum = _maximum;
    }
    
    public void setMode(double _mode)
    {
        this.mode = _mode;
    }
}
