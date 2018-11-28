/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.renderer.RenderManager;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MainTest {
    
    public MainTest() {
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

    /**
     * Test of main method, of class Main.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Main.main(args);

    }

    /**
     * Test of simpleInitApp method, of class Main.
     */
    @Test
    public void testSimpleInitApp() {
        System.out.println("simpleInitApp");
        Main instance = new Main();
        instance.simpleInitApp();

    }

    /**
     * Test of simpleUpdate method, of class Main.
     */
    @Test
    public void testSimpleUpdate() {
        System.out.println("simpleUpdate");
        float tpf = 0.0F;
        Main instance = new Main();
        instance.simpleUpdate(tpf);

    }

    /**
     * Test of simpleRender method, of class Main.
     */
    @Test
    public void testSimpleRender() {
        System.out.println("simpleRender");
        RenderManager rm = null;
        Main instance = new Main();
        instance.simpleRender(rm);
    }

    /**
     * Test of fillImg method, of class Main.
     */
    @Test
    public void testFillImg() {
        System.out.println("fillImg");
        Main instance = new Main();
        instance.fillImg("C:\\Users\\mixon\\Documents\\jMonkeyEngine\\bottomMap\\assets\\Textures\\coords1.txt");
        assertEquals(7,instance.grid[0][0]);
        assertEquals(6,instance.grid[1][1]);
        assertEquals(5,instance.grid[2][2]);
        assertEquals(4,instance.grid[3][3]);
        assertEquals(3,instance.grid[4][4]);
        assertEquals(1,instance.grid[5][5]);
        assertEquals(0,instance.grid[6][6]);
        assertEquals(9,instance.grid[100][100]);
        assertEquals(8,instance.grid[101][101]);
        assertEquals(7,instance.grid[102][102]);
        assertEquals(6,instance.grid[103][103]);
        assertEquals(5,instance.grid[104][104]);


    }
    
}
