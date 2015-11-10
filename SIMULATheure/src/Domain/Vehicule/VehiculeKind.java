/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Vehicule;

/**
 *
 * @author Raphael
 */
public class VehiculeKind implements java.io.Serializable
{
    protected int capacity;
    
    public void VehiculeKind()
    {
        
    }
    
    public int getCapacity()
    {
        return this.capacity;
    }
    
    protected void setCapacity(int _capacity)
    {
        this.capacity = _capacity;
    }
}
