package unsw.graphics.scene.tests;


import java.awt.Color;

import com.jogamp.opengl.GL3;

import unsw.graphics.Application2D;
import unsw.graphics.scene.Camera;
import unsw.graphics.scene.Duck;
import unsw.graphics.scene.Heart;
import unsw.graphics.scene.IScene;
import unsw.graphics.scene.MyCoolSceneObject;
import unsw.graphics.scene.Scene;
import unsw.graphics.scene.SeaScene;
import unsw.graphics.scene.UScene;

/**
 * A simple class to view MyCoolSceneObject
 *
 * @author angf
 * @author Robert Clifton-Everest
 */
public class TestMyCoolSceneObject extends Application2D {
    
    private Scene scene;
    
    public TestMyCoolSceneObject() {
        super("Test MyCoolSceneObject", 600, 600);
      
        setBackground(Color.WHITE);
        
        // Create a scene
        scene = new Scene();
        
        // Create a camera
        Camera camera = new Camera(scene.getRoot());
        scene.setCamera(camera);
        camera.setScale(4);
              
        createTestObjects(scene);
    }

	public void createTestObjects(Scene scene){
		
		//Should look good when we create your MyCoolSceneObject using the default constructor
		//By default we test on a black background
		//Write a comment to your tutor in your MyCoolSceneObject file if your object does not look ok on a 
        //black background and it looks better with a white or a red background
		
		MyCoolSceneObject cso = new MyCoolSceneObject(scene.getRoot());
		
		// Should not break if we apply transformations for example
		// If we uncommented these lines (or wrote other transformations) 
		// it should not break your object
		
		cso.translate(0f,-1f);
		cso.scale(0.5f);
		
/*		cso.translate(-0.2f,0.2f);
		cso.rotate(45);
		cso.scale(0.25f);
		 */
		
	}
   
    /**
     * A simple test for MyCoolSceneObject
     * 
     * @param args
     */
    public static void main(String[] args) {
        TestMyCoolSceneObject test = new TestMyCoolSceneObject();
        
        test.start();
    }
    
    @Override
    public void display(GL3 gl) {
        super.display(gl);
        scene.draw(gl);
    }

    @Override
    public void reshape(GL3 gl, int width, int height) {
        scene.reshape(width, height);
    }
    
    
}