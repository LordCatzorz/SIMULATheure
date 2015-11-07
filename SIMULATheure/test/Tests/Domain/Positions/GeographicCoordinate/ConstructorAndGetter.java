package Tests.Domain.Positions.GeographicCoordinate;

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
public class ConstructorAndGetter {
    float deltaFloatAccepted = 0.001f;
    public ConstructorAndGetter() {
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
    public void GIVEN_getDegree_WHEN_0_0_0_THEN_0(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(0, 0, 0);
        
        //Act
        int expected = 0;
        int given = gc.getDegree();
        
        //Assert
        assertEquals(expected, given);
    }
    
    @Test
    public void GIVEN_getDegree_WHEN_179_0_0_THEN_179(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(179, 0, 0);
        
        //Act
        int expected = 179;
        int given = gc.getDegree();
        
        //Assert
        assertEquals(expected, given);
    }
    
    @Test
    public void GIVEN_getDegree_WHEN_neg179_0_0_THEN_neg179(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(-179, 0, 0);
        
        //Act
        int expected = -179;
        int given = gc.getDegree();
        
        //Assert
        assertEquals(expected, given);
    }
    
    @Test
    public void GIVEN_getDegree_WHEN_185_0_0_THEN_neg175(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(185, 0, 0);
        
        //Act
        int expected = -175;
        int given = gc.getDegree();
        
        //Assert
        assertEquals(expected, given);
    }
    
    @Test
    public void GIVEN_getDegree_WHEN_neg185_0_0_THEN_175(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(-185, 0, 0);
        
        //Act
        int expected = 175;
        int given = gc.getDegree();
        
        //Assert
        assertEquals(expected, given);
    }
    
    @Test
    public void GIVEN_getDegree_WHEN_360_0_0_THEN_0(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(360, 0, 0);
        
        //Act
        int expected = 0;
        int given = gc.getDegree();
        
        //Assert
        assertEquals(expected, given);
    }
    
    
    public void GIVEN_getDegree_WHEN_neg360_0_0_THEN_0(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(-360, 0, 0);
        
        //Act
        int expected = 0;
        int given = gc.getDegree();
        
        //Assert
        assertEquals(expected, given);
    }
    
    
    // **************** \\
    
    @Test
    public void GIVEN_getMinute_WHEN_0_59_0_THEN_59(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(0, 59, 0);
        
        //Act
        int expected = 59;
        int given = gc.getMinute();
        
        //Assert
        assertEquals(expected, given);
    }
    
    @Test
    public void GIVEN_getMinute_WHEN_0_0_0_THEN_0(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(0, 0, 0);
        
        //Act
        int expected = 0;
        int given = gc.getMinute();
        
        //Assert
        assertEquals(expected, given);
    }
    
    @Test
    public void GIVEN_getMinute_WHEN_0_60_0_THEN_0(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(0, 60, 0);
        
        //Act
        int expected = 0;
        int given = gc.getMinute();
        
        //Assert
        assertEquals(expected, given);
    }
    
    @Test
    public void GIVEN_getDegree_WHEN_0_60_0_THEN_1(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(0, 60, 0);
        
        //Act
        int expected = 1;
        int given = gc.getDegree();
        
        //Assert
        assertEquals(expected, given);
    }
    
    @Test
    public void GIVEN_getDegree_WHEN_0_120_0_THEN_2(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(0, 120, 0);
        
        //Act
        int expected = 2;
        int given = gc.getDegree();
        
        //Assert
        assertEquals(expected, given);
    }
    
    
    // ***************** \\
    
    @Test
    public void GIVEN_getSecond_WHEN_0_0_59_99f_THEN_59_99f(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(0, 0, 59.99f);
        
        //Act
        float expected = 59.99f;
        float given = gc.getSecond();
        
        //Assert
        assertEquals(expected, given, this.deltaFloatAccepted);
    }
    
    @Test
    public void GIVEN_getSecond_WHEN_0_0_0f_THEN_0f(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(0, 0, 0f);
        
        //Act
        float expected = 0f;
        float given = gc.getSecond();
        
        //Assert
        assertEquals(expected, given, 0f);
    }
    
    @Test
    public void GIVEN_getSecond_WHEN_0_0_60f_THEN_0f(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(0, 0, 60f);
        
        //Act
        float expected = 0f;
        float given = gc.getSecond();
        
        //Assert
        assertEquals(expected, given, this.deltaFloatAccepted);
    }
    
    @Test
    public void GIVEN_getMinute_WHEN_0_0_60f_THEN_1(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(0, 0, 60f);
        
        //Act
        int expected = 1;
        int given = gc.getMinute();
        
        //Assert
        assertEquals(expected, given);
    }
    
    @Test
    public void GIVEN_getMinute_WHEN_0_0_120f_THEN_2(){
        //Assign
        Domain.Positions.GeographicCoordinate gc = new GeographicCoordinate(0, 0, 120f);
        
        //Act
        int expected = 2;
        int given = gc.getMinute();
        
        //Assert
        assertEquals(expected, given);
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
