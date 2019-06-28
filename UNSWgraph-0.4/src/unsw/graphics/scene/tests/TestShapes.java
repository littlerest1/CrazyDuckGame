package unsw.graphics.scene.tests;


import java.awt.Color;

import com.jogamp.opengl.GL3;

import unsw.graphics.Application2D;
import unsw.graphics.geometry.Point2D;
import unsw.graphics.geometry.Polygon2D;
import unsw.graphics.scene.Camera;
import unsw.graphics.scene.CircularSceneObject;
import unsw.graphics.scene.Heart;
import unsw.graphics.scene.LineSceneObject;
import unsw.graphics.scene.PolygonalSceneObject;
import unsw.graphics.scene.Scene;
import unsw.graphics.scene.SceneObject;

import java.util.*;

/**
 * A simple test class for assignment1
 * 
 * @author angf
 * @author Robert Clifton-Everest
 */
public class TestShapes extends Application2D {

    private Scene scene;
    
	public TestShapes() {
        super("Test shapes", 400, 400);
        setBackground(Color.BLACK);
        // Create a scene
        scene = new Scene();
        
        // Create a camera
        Camera camera = new Camera(scene.getRoot());
        scene.setCamera(camera);
        camera.setScale(2); // scale up the camera so we can see more of the world  
              
        addTestShapes(scene);
      //  Point2D point = new Point2D(1f,-1f); // in the right corner circle
        Point2D point = new Point2D(0f,1f); // in the top triangle 
        List<SceneObject> list = scene.collision(point);
        for(SceneObject o : list) {
        	System.out.print(o.getClass().getName() + "	, ");
        }
        System.out.println();
    }

    private static void addTestShapes(Scene scene) {
        // Create a polygon
    	
    	//Polygon2D poly = new Polygon2D();
        Polygon2D poly = new Polygon2D(0,0, 1,1, 0,1);
        PolygonalSceneObject p = new PolygonalSceneObject(scene.getRoot(), poly, Color.BLUE, Color.WHITE); 
     //   PolygonalSceneObject p = new PolygonalSceneObject(scene.getRoot(), poly, Color.WHITE, Color.WHITE);
        p.rotate(45);
      //   p.setScale(0.5f);
       //   p.setPosition(0.5f, -0.5f);  

       
        // Create a circle 
        Color cFillCol = new Color(1,0.5f,0.5f);
        CircularSceneObject c = new CircularSceneObject(scene.getRoot(), cFillCol, Color.WHITE);
        c.setPosition(1, -1);  
        c.setScale(0.5f);  
       
        //Create a line
        Color lineCol = new Color(0.5f,1,0.5f);
        LineSceneObject l = new LineSceneObject(scene.getRoot(),0.5f,0.5f,1,1,lineCol);
       

        //Create a line that is a child of polygon p
        Color lineCol2 = new Color(0.5f, 0.5f, 1);
        LineSceneObject l2 = new LineSceneObject(p, lineCol2);     
        l2.setPosition(-1, 0);
      
    
        //Create a circle that is a child of polygon p       
        CircularSceneObject c2 = new CircularSceneObject(p, 0.25f,null, Color.WHITE);     
        c2.translate(-1,0);
         
       
        
	}
   
    /**
     * A simple example of how to use PolygonalSceneObject, CircularSceneObject and LineObject
     * 
     * and also how to put together a simple scene using the game engine.
     * 
     * @param args
     */
    public static void main(String[] args) {
        TestShapes test = new TestShapes();
        
        test.start();
       
    }
    
    @Override
    public void display(GL3 gl) {
        super.display(gl);
        scene.draw(gl);
        
    }

    @Override
    public void reshape(GL3 gl, int width, int height) {
        scene.getCamera().reshape(width, height);
    }
   

}
