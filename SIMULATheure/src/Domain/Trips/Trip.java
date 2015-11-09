/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Trips;

import java.util.Queue;
import java.sql.Time;
/**
 *
 * @author Raphael
 */
public class Trip implements java.io.Serializable
{
    private Queue<Segment> allSegments;
    private Time nextDepartureTime;
    private String name;
    
    public void Trip()
    {
        
    }
    
    public Queue<Segment> getAllSegments()
    {
        return this.allSegments;
    }
}
