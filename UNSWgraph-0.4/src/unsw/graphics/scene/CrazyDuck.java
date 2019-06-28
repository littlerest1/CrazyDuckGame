package unsw.graphics.scene;


import java.awt.Color;

import com.jogamp.opengl.GL3;

import unsw.graphics.Application2D;
import unsw.graphics.geometry.Point2D;
import unsw.graphics.scene.Camera;
import unsw.graphics.scene.Heart;
import unsw.graphics.scene.Scene;
import com.jogamp.newt.event.*;
import java.util.*;

/**
 * Controller: 
 * ENTER ----- Start Game
 * UP ---- Rotate upper
 * DOWN ---- Rotate lower
 * SPACE ----- Jump up and down
 */
//Crazy Duck game starter
public class CrazyDuck extends Application2D implements KeyListener{

    private Scene scene;
    private Duck duck;
    private static SeaScene sea;
    private Heart h;
    private IScene i; 
    private UScene u;
    private Camera camera;

    
	public CrazyDuck() {
        super("Quack", 400, 400);
        setBackground(Color.BLACK);
        // Create a scene
        scene = new Scene();
        
        setBackground(Color.WHITE);
        
        // Create a camera
        camera = new Camera(scene.getRoot());
        scene.setCamera(camera);
        //Scale to 4
        camera.setScale(4);

        createTestObjects(scene);
   
       
    }
//Creates background, duck and text
	public void createTestObjects(Scene scene){	
		sea = new SeaScene(scene.getRoot());
		
		
        duck = new Duck(scene.getRoot());
		duck.translate(-3.79f,-0.6f);
		duck.rotate(11.25f);
		duck.scale(0.2f);
	
		i = new IScene(scene.getRoot());
		i.scale(0.3f);
		i.translate(0, 2);
		i.show(false);
		
		u = new UScene(scene.getRoot());
		u.scale(0.5f);
		u.translate(0, 2);
		u.show(false);
		h = new Heart(scene.getRoot(), new Color(0.76f,0.12f,0.34f));
		h.scale(0.5f);
		h.translate(-0.5f, 2);
		h.show(false);
	
	}
   
    public static void main(String[] args) {
        CrazyDuck test = new CrazyDuck();
        
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
   
    @Override
    public void init(GL3 gl) {
        super.init(gl);
        getWindow().addKeyListener(duck);
        getWindow().addKeyListener(this);
    }
//Continuous monitoring status of the duck
    @Override
    public void keyPressed(KeyEvent e) {
        	 if(duck != null) {
     	        int status = duck.getStatus();
     	  //      System.out.println(status);
     	        if(status == 1) {
     	        	//in the second wave
     	        	if(i != null) {
     	        		i.show(true);
     	        	}
     	        }
     	        else if(status == 2) {
     	        	i.show(false);
     	        	// in the third wave
     	        	if(h != null) {
     	        		h.show(true);
     	        	}	
     	        }
     	        else if(status == 3) {
     	        	h.show(false);
     	        	//in the fourth wave
     	        	if(u != null) {
     	        		u.show(true);
     	        	}
     	        }
     	        //Die status
     	        else if(status == -1) {
     	        	System.out.println("DIE");
     	        
     	        }
             }
 
           
    }

    @Override
    public void keyReleased(KeyEvent arg0) {}

}
