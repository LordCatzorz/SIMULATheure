/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Domain.Positions.GeographicCoordinate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Raphael
 */
public class GeographicCoordinateTest {
    
    public GeographicCoordinateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void GIVEN_getDegree_WHEN_0_0_0_GeographicCoordinate_THEN_0(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(0, 0, 0);
        
        //Act
        int expected = 0;
        int given = gc.getDegree();
        
        //Assert
        assertEquals(expected, given);
    }
    
    @Test
    public void GIVEN_getDegree_WHEN_179_0_0_GeographicCoordinate_THEN_179(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(179, 0, 0);
        
        //Act
        int expected = 179;
        int given = gc.getDegree();
        
        //Assert
        assertEquals(expected, given);
    }
    
    @Test
    public void GIVEN_getDegree_WHEN_neg179_0_0_GeographicCoordinate_THEN_neg179(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(-179, 0, 0);
        
        //Act
        int expected = -179;
        int given = gc.getDegree();
        
        //Assert
        assertEquals(expected, given);
    }
    
    @Test
    public void GIVEN_getDegree_WHEN_185_0_0_GeographicCoordinate_THEN_neg175(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(185, 0, 0);
        
        //Act
        int expected = -175;
        int given = gc.getDegree();
        
        //Assert
        assertEquals(expected, given);
    }
    
    @Test
    public void GIVEN_getDegree_WHEN_neg185_0_0_GeographicCoordinate_THEN_175(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(-185, 0, 0);
        
        //Act
        int expected = 175;
        int given = gc.getDegree();
        
        //Assert
        assertEquals(expected, given);
    }
    
    @Test
    public void GIVEN_getDegree_WHEN_360_0_0_GeographicCoordinate_THEN_0(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(360, 0, 0);
        
        //Act
        int expected = 0;
        int given = gc.getDegree();
        
        //Assert
        assertEquals(expected, given);
    }
    
    
    public void GIVEN_getDegree_WHEN_neg360_0_0_GeographicCoordinate_THEN_0(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(-360, 0, 0);
        
        //Act
        int expected = 0;
        int given = gc.getDegree();
        
        //Assert
        assertEquals(expected, given);
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
