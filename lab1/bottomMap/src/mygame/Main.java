package mygame;


import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Plane;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;

import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.SkyFactory;
import com.jme3.water.SimpleWaterProcessor;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Integer.max;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.lang.model.element.Element;
import jme3tools.converters.ImageToAwt;


public class Main extends SimpleApplication {

     private TerrainQuad terrain;
    Material mat_terrain;
    int[][] grid;
    
    public static void main(String[] args) {
        
        Main app = new Main();
        
        app.start();
        
    }
   

    
    @Override
    
    public void simpleInitApp() {
        
        
        Node mainScene=new Node();
        fillImg("C:\\Users\\mixon\\Documents\\jMonkeyEngine\\bottomMap\\assets\\Textures\\coords.txt");
        flyCam.setMoveSpeed(200); 
        mainScene.attachChild(SkyFactory.createSky(getAssetManager(), "Textures/sky.jpg", SkyFactory.EnvMapType.SphereMap));
        
        SimpleWaterProcessor waterProcessor = new SimpleWaterProcessor(assetManager);
        waterProcessor.setReflectionScene(mainScene);
        Vector3f waterLocation=new Vector3f(0,0,0);
        Vector3f lightPos =  new Vector3f(100,100,100);
        waterProcessor.setPlane(new Plane(Vector3f.UNIT_Y, waterLocation.dot(Vector3f.UNIT_Y)));
        
       // waterProcessor.setLightPosition(lightPos);
        waterProcessor.setWaterDepth(10);         
        waterProcessor.setDistortionScale(0.05f); 
        waterProcessor.setWaveSpeed(0.05f);   
        waterProcessor.setRefractionClippingOffset(1.0f);
        waterProcessor.setWaterColor(ColorRGBA.Blue);
        waterProcessor.setRenderSize(2048,2048);
     
        viewPort.addProcessor(waterProcessor);
        Quad quad = new Quad(400,400);
        quad.scaleTextureCoordinates(new Vector2f(4f,4f));
        Geometry water=new Geometry("water", quad);
        water.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));
        water.setLocalTranslation(0, -195, 1200);
        water.setLocalScale(10f, 10f, 10f);
       // water.setShadowMode(ShadowMode.Receive);
        water.setMaterial(waterProcessor.getMaterial());
         rootNode.attachChild(water);
        
        //rootNode.attachChild(mainScene);
        
        

    mat_terrain = new Material(assetManager,
            "Common/MatDefs/Terrain/Terrain.j3md");


    mat_terrain.setTexture("Alpha", assetManager.loadTexture(
            "Textures/alpha.png"));


    Texture green = assetManager.loadTexture(
            "Textures/green.jpg");
    green.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("Tex1", green);
    mat_terrain.setFloat("Tex1Scale", 1f);

    

    Texture blue = assetManager.loadTexture(
            "Textures/blue.jpg");
    blue.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("Tex3", blue);
    mat_terrain.setFloat("Tex3Scale", 1f);
    
    Texture red = assetManager.loadTexture(
            "Textures/red.jpg");
    red.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("Tex2", red);
    mat_terrain.setFloat("Tex2Scale", 1f);
   
    AbstractHeightMap heightmap = null;
    Texture heightMapImage = assetManager.loadTexture(
            "Textures/heightmap.jpg");
    heightmap = new ImageBasedHeightMap(heightMapImage.getImage());
    heightmap.load();

    int patchSize = 65;
    terrain = new TerrainQuad("my terrain", patchSize, 513, heightmap.getHeightMap());

    terrain.setMaterial(mat_terrain);
    terrain.setLocalTranslation(550, -300, 550);
    terrain.setLocalScale(2f, 0.4f, 2f);
    mainScene.attachChild(terrain);

    TerrainLodControl control = new TerrainLodControl(terrain, getCamera());
    terrain.addControl(control);
    rootNode.attachChild(mainScene);
       
    
    }

    @Override
    
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    public void fillImg(String fileName) {
            class Params {
               int x;
               int y;
               int time;
               public Params(int _x, int _y, int _time) {
                   x = _x;
                   y = _y;
                   time = _time;
               }
           }
           ArrayList<Params> map = new ArrayList<Params>();


          // String fileName = "C:\\Users\\mixon\\Documents\\jMonkeyEngine\\bottomMap\\assets\\Textures\\coords.txt";

           String line = null;
           String x;
           String y;
           String time;
           int index1;
           int index2;
           try {
               FileReader fileReader = 
                   new FileReader(fileName);

               BufferedReader bufferedReader = 
                   new BufferedReader(fileReader);

               while((line = bufferedReader.readLine()) != null) {
                   index1 = 0;
                   for(; line.charAt(index1) != ' '; index1++);
                   index2 = index1;
                   index2++;
                   for(; line.charAt(index2) != ' '; index2++);
                   x = line.substring(0, index1);
                   y = line.substring(index1 + 1, index2);
                   time = line.substring(index2 + 1, line.length());
                   map.add(new Params(Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(time)));
               }   

               bufferedReader.close();         
           }
           catch(IOException ex) {
           }
           final int HEIGHT = 512;
           final int WIDTH  = 512;
           
           grid = new int[HEIGHT][WIDTH];
           final int SIZEOFHILL = 19;
           int shift;
           int gridDepth;
           for(int i = 0; i < map.size(); i++) {
               shift = 0;
               for(int px = 0; px < SIZEOFHILL; px++) {
                   for(int py = 0; py < SIZEOFHILL; py++) {
                       if((px + py) < 2) {
                         shift = 1; 
                       }
                       else if(px + py < 4) {
                           shift = 2;
                       }
                       else if(px + py < 6) {
                           shift = 3;
                       }
                       else if(px + py < 8) {
                           shift = 4;
                       }
                       else if(px + py < 9) {
                           shift = 5;
                       }
                       else if(px + py < 10) {
                           shift = 6;
                       }
                       else if(px + py < 12) {
                           shift = 7;
                       }
                       else {
                           continue;
                       }
                        gridDepth = map.get(i).time - shift;
                        if(gridDepth < 0) {
                            gridDepth = 0;
                        }
                        if((map.get(i).x + px < WIDTH) &&
                           (map.get(i).y + py < HEIGHT)) {
                            if(grid[map.get(i).x + px][map.get(i).y + py] == 0){
                            grid[map.get(i).x + px][map.get(i).y + py] = gridDepth;
                            }
                            else {
                           grid[map.get(i).x + px][map.get(i).y + py] = 
                                   max(grid[map.get(i).x + px][map.get(i).y + py], gridDepth);
                            }
                        }
                         if((map.get(i).x + px < WIDTH) &&
                           (map.get(i).y - py >= 0)) {
                            if(grid[map.get(i).x + px][map.get(i).y - py] == 0){
                            grid[map.get(i).x + px][map.get(i).y - py] = gridDepth;
                            }
                            else {
                           grid[map.get(i).x + px][map.get(i).y - py] = 
                                   max(grid[map.get(i).x + px][map.get(i).y - py], gridDepth);
                            }
                        }
                          if((map.get(i).x - px >= 0) &&
                           (map.get(i).y + py < HEIGHT)) {
                            if(grid[map.get(i).x - px][map.get(i).y + py] == 0){
                            grid[map.get(i).x - px][map.get(i).y + py] = gridDepth;
                            }
                            else {
                           grid[map.get(i).x - px][map.get(i).y + py] = 
                                   max(grid[map.get(i).x - px][map.get(i).y + py], gridDepth);
                            }
                        }
                           if((map.get(i).x - px >= 0) &&
                           (map.get(i).y - py >= 0)) {
                            if(grid[map.get(i).x - px][map.get(i).y - py] == 0){
                            grid[map.get(i).x - px][map.get(i).y - py] = gridDepth;
                            }
                            else {
                           grid[map.get(i).x - px][map.get(i).y - py] = 
                                   max(grid[map.get(i).x - px][map.get(i).y - py], gridDepth);
                            }
                        }
                        
                   }
               }
           }//end grid filling
           try {
		File file = new File("C:\\Users\\mixon\\Documents\\jMonkeyEngine\\bottomMap\\assets\\Textures\\heightmap.jpg"); 
		File fileAlpha = new File("C:\\Users\\mixon\\Documents\\jMonkeyEngine\\bottomMap\\assets\\Textures\\alpha.png");
		BufferedImage img = ImageIO.read(file);
                    int shade = 0;
                    for(int i = 0; i < HEIGHT; i++)
                    {
                        for(int j = 0; j < WIDTH; j++)
                        {
                            shade = 255 - grid[i][j] * 16;
                            if(shade < 0) {
                                shade = 0;
                            }
                            int color = (
                                    255<<24) | (shade<<16) | (shade<<8) | shade;
                            img.setRGB(i, j, color);
                        }
                    }
                    ImageIO.write(img, "jpg", file);
                img = ImageIO.read(fileAlpha);
                int color = 0;
                  for(int i = 0; i < HEIGHT; i++)
                    {
                        for(int j = 0; j < WIDTH; j++)
                        {
                            /*shade = 255 - grid[i][j] * 16;
                            if(shade < 0) {
                                shade = 0;
                            }
                            else if(shade > 230)
                            {
                                color = (
                                    255<<24)|(shade<<8);
                            }
                            else if(shade > 190) {
                                color = (
                                    255<<24)|(shade*2<<16) | (shade<<8);
                            }
                            else if(shade > 128) {
                                color = (
                                    255<<24) | (shade*2<<16);
                            }
                            else if (shade > 80) {
                                color = (
                                    255<<24) | (shade*2<<16)|shade;
                            }
                            else{
                                color = (
                                    255<<24) | shade * 2;
                            }*/
                            switch(grid[i][j]) {
                                case 0:{
                                    color = (255 << 24) | (255<<8);
                                    break;
                                }
                                case 1: {
                                    color = (255 << 24) | (230<<8);
                                    break;
                                }
                                case 2: {
                                    color = (255 << 24) | (200<<8);
                                    break;
                                }
                                case 3: {
                                    color = (255 << 24) | (30<<16) | (180<<8);
                                    break;
                                }
                                case 4: {
                                    color = (255 << 24) | (64<<16) | (140<<8);
                                    break;
                                }
                                case 5: {
                                    color = (255 << 24) | (90<<16) | (110<<8);
                                    break;
                                }
                                case 6: {
                                    color = (255 << 24) | (120<<16) | (80<<8);
                                    break;
                                }
                                case 7: {
                                    color = (255 << 24) | (160<<16) | (50<<8);
                                    break;
                                }
                                case 8: {
                                    color = (255 << 24) | (190<<16) | (30<<8);
                                    break;
                                } 
                                case 9: {
                                    color = (255 << 24) | (220<<16);
                                    break;
                                }
                                case 10: {
                                    color = (255 << 24) | (255<<16);
                                    break;
                                }
                                case 11: {
                                    color = (255 << 24) | (220<<16)|(20);
                                    break;
                                }
                                case 12: {
                                    color = (255 << 24) | (200<<16)|(40);
                                    break;
                                }
                                case 13: {
                                    color = (255 << 24) | (180<<16)|(50);
                                    break;
                                }
                                case 14: {
                                    color = (255 << 24) | (150<<16)|(80);
                                    break;
                                }
                                case 15: {
                                    color = (255 << 24) | (110<<16)|(110);
                                    break;
                                }
                                case 16: {
                                    color = (255 << 24) | (80<<16)|(140);
                                    break;
                                }
                                case 17: {
                                    color = (255 << 24) | (50<<16)|(190);
                                    break;
                                }
                                case 18: {
                                    color = (255 << 24) | (20<<16)|(200);
                                    break;
                                }
                                case 19: {
                                    color = (255 << 24) | (220);
                                    break;
                                }
                                case 20: {
                                    color = (255 << 24) | (255);
                                    break;
                                }
                            }
                            img.setRGB(i, j, color);
                        }
                    }
                ImageIO.write(img, "jpg", fileAlpha);
           } catch(IOException e) {
               System.out.println(e);
           }
           
           
    }
   
}
