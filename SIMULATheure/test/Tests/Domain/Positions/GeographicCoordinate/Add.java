/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests.Domain.Positions.GeographicCoordinate;

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
public class Add {

    public Add() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void GIVEN_add_WHEN_0_0_0_AND_0_0_0_THEN_0_0_0() {
        //Assign

        GeographicCoordinate gc1 = new GeographicCoordinate(0, 0, 0f);
        GeographicCoordinate gc2 = new GeographicCoordinate(0, 0, 0f);

        //Act
        GeographicCoordinate expected = new GeographicCoordinate(0, 0, 0f);
        GeographicCoordinate given = gc1.add(gc2);

        //Assert
        assertEquals(expected, given);
    }
    @Test
    public void GIVEN_add_WHEN_0_0_0_AND_90_30_30f_THEN_90_30_30f() {
        //Assign

        GeographicCoordinate gc1 = new GeographicCoordinate(0, 0, 0f);
        GeographicCoordinate gc2 = new GeographicCoordinate(90, 30, 30f);

        //Act
        GeographicCoordinate expected = new GeographicCoordinate(90, 30, 30f);
        GeographicCoordinate given = gc1.add(gc2);

        //Assert
        assertEquals(expected, given);
    }
    @Test
    public void GIVEN_add_WHEN_90_30_30f_AND_0_0_0_THEN_90_30_30f() {
        //Assign

        GeographicCoordinate gc1 = new GeographicCoordinate(90, 30, 30f);
        GeographicCoordinate gc2 = new GeographicCoordinate(0, 0, 0f);

        //Act
        GeographicCoordinate expected = new GeographicCoordinate(90, 30, 30f);
        GeographicCoordinate given = gc1.add(gc2);

        //Assert
        assertEquals(expected, given);
    }
    @Test
    public void GIVEN_add_WHEN_90_30_30f_AND_0_0_40f_THEN_90_31_10f() {
        //Assign

        GeographicCoordinate gc1 = new GeographicCoordinate(90, 30, 30f);
        GeographicCoordinate gc2 = new GeographicCoordinate(0, 0, 40f);

        //Act
        GeographicCoordinate expected = new GeographicCoordinate(90, 31, 10f);
        GeographicCoordinate given = gc1.add(gc2);

        //Assert
        assertEquals(expected, given);
    }
    @Test
    public void GIVEN_add_WHEN_0_0_40f_AND_90_30_30f_THEN_90_31_10f() {
        //Assign

        GeographicCoordinate gc1 = new GeographicCoordinate(0, 0, 40f);
        GeographicCoordinate gc2 = new GeographicCoordinate(90, 30, 30f);

        //Act
        GeographicCoordinate expected = new GeographicCoordinate(90, 31, 10f);
        GeographicCoordinate given = gc1.add(gc2);

        //Assert
        assertEquals(expected, given);
    }
    @Test
    public void GIVEN_add_WHEN_90_30_30f_AND_0_40_0f_THEN_91_10_30f() {
        //Assign

        GeographicCoordinate gc2 = new GeographicCoordinate(90, 30, 30f);
        GeographicCoordinate gc1 = new GeographicCoordinate(0, 40, 0f);

        //Act
        GeographicCoordinate expected = new GeographicCoordinate(91, 10, 30f);
        GeographicCoordinate given = gc1.add(gc2);

        //Assert
        assertEquals(expected, given);
    }
    @Test
    public void GIVEN_add_WHEN_0_40_0f_AND_90_30_30f_THEN_91_10_30f() {
        //Assign

        GeographicCoordinate gc1 = new GeographicCoordinate(0, 40, 0f);
        GeographicCoordinate gc2 = new GeographicCoordinate(90, 30, 30f);

        //Act
        GeographicCoordinate expected = new GeographicCoordinate(91, 10, 30f);
        GeographicCoordinate given = gc1.add(gc2);

        //Assert
        assertEquals(expected, given);
    }
    @Test
    public void GIVEN_add_WHEN_90_30_30f_AND_100_0_0f_THEN_neg170_30_30f() {
        //Assign

        GeographicCoordinate gc2 = new GeographicCoordinate(90, 30, 30f);
        GeographicCoordinate gc1 = new GeographicCoordinate(100, 0, 0f);

        //Act
        GeographicCoordinate expected = new GeographicCoordinate(-170, 30, 30f);
        GeographicCoordinate given = gc1.add(gc2);

        //Assert
        assertEquals(expected, given);
    }
    @Test
    public void GIVEN_add_WHEN_100_0_0f_AND_90_30_30f_THEN_neg170_30_30f() {
        //Assign

        GeographicCoordinate gc1 = new GeographicCoordinate(100, 0, 0f);
        GeographicCoordinate gc2 = new GeographicCoordinate(90, 30, 30f);

        //Act
        GeographicCoordinate expected = new GeographicCoordinate(-170, 30, 30f);
        GeographicCoordinate given = gc1.add(gc2);

        //Assert
        assertEquals(expected, given);
    }
    @Test
    public void GIVEN_add_WHEN_180_0_0f_AND_180_0_0f_THEN_0_0_0f() {
        //Assign

        GeographicCoordinate gc1 = new GeographicCoordinate(180, 0, 0f);
        GeographicCoordinate gc2 = new GeographicCoordinate(180, 0, 0f);

        //Act
        GeographicCoordinate expected = new GeographicCoordinate(0, 0, 0f);
        GeographicCoordinate given = gc1.add(gc2);

        //Assert
        assertEquals(expected, given);
    }
    @Test
    public void GIVEN_add_WHEN_180_0_0f_AND_neg180_0_0f_THEN_0_0_0f() {
        //Assign

        GeographicCoordinate gc1 = new GeographicCoordinate(180, 0, 0f);
        GeographicCoordinate gc2 = new GeographicCoordinate(-180, 0, 0f);

        //Act
        GeographicCoordinate expected = new GeographicCoordinate(0, 0, 0f);
        GeographicCoordinate given = gc1.add(gc2);

        //Assert
        assertEquals(expected, given);
    }
    @Test
    public void GIVEN_add_WHEN_neg180_0_0f_AND_180_0_0f_THEN_0_0_0f() {
        //Assign

        GeographicCoordinate gc1 = new GeographicCoordinate(-180, 0, 0f);
        GeographicCoordinate gc2 = new GeographicCoordinate(180, 0, 0f);

        //Act
        GeographicCoordinate expected = new GeographicCoordinate(0, 0, 0f);
        GeographicCoordinate given = gc1.add(gc2);

        //Assert
        
        assertEquals(expected, given);
    }
    @Test
    public void GIVEN_add_WHEN_neg180_0_0f_AND_neg180_0_0f_THEN_0_0_0f() {
        //Assign

        GeographicCoordinate gc1 = new GeographicCoordinate(-180, 0, 0f);
        GeographicCoordinate gc2 = new GeographicCoordinate(-180, 0, 0f);

        //Act
        GeographicCoordinate expected = new GeographicCoordinate(0, 0, 0f);
        GeographicCoordinate given = gc1.add(gc2);

        //Assert
        assertEquals(expected, given);
    }
    @Test
    public void GIVEN_add_WHEN_91_46_10_25f_AND_neg42_31_13_15f_THEN_50_17_23_40f() {
        //Assign

        GeographicCoordinate gc1 = new GeographicCoordinate(91, 46, 10.25f);
        GeographicCoordinate gc2 = new GeographicCoordinate(-42, 31, 13.15f);

        //Act
        GeographicCoordinate expected = new GeographicCoordinate(50, 17, 23f);
        GeographicCoordinate given = gc1.add(gc2);

        //Assert
        assertEquals(expected, given);
    }
    @Test
    public void GIVEN_add_WHEN_neg42_31_13_15f_AND_91_46_10_25f_THEN_50_17_23_40f() {
        //Assign

        GeographicCoordinate gc1 = new GeographicCoordinate(-42, 31, 13.15f);
        GeographicCoordinate gc2 = new GeographicCoordinate(91, 46, 10.25f);

        //Act
        GeographicCoordinate expected = new GeographicCoordinate(50, 17, 23f);
        GeographicCoordinate given = gc1.add(gc2);

        //Assert
        assertEquals(expected, given);
    }
    @Test
    public void GIVEN_add_WHEN_neg40_0_0f_AND_neg40_0_0f_THEN_neg80_0_0f() {
        //Assign

        GeographicCoordinate gc1 = new GeographicCoordinate(-40, 0, 0f);
        GeographicCoordinate gc2 = new GeographicCoordinate(-40, 0, 0f);

        //Act
        GeographicCoordinate expected = new GeographicCoordinate(-80, 0, 0f);
        GeographicCoordinate given = gc1.add(gc2);

        //Assert
        assertEquals(expected, given);
    }
    @Test
    public void GIVEN_add_WHEN_neg40_0_0f_AND_neg160_0_0f_THEN_160_0_0f() {
        //Assign

        GeographicCoordinate gc1 = new GeographicCoordinate(-40, 0, 0f);
        GeographicCoordinate gc2 = new GeographicCoordinate(-160, 0, 0f);

        //Act
        GeographicCoordinate expected = new GeographicCoordinate(160, 0, 0f);
        GeographicCoordinate given = gc1.add(gc2);

        //Assert
        assertEquals(expected, given);
    }
    @Test
    public void GIVEN_add_WHEN_neg160_0_0f_AND_neg40_0_0f_THEN_160_0_0f() {
        //Assign

        GeographicCoordinate gc1 = new GeographicCoordinate(-160, 0, 0f);
        GeographicCoordinate gc2 = new GeographicCoordinate(-40, 0, 0f);

        //Act
        GeographicCoordinate expected = new GeographicCoordinate(160, 0, 0f);
        GeographicCoordinate given = gc1.add(gc2);

        //Assert
        assertEquals(expected, given);
    }
}
