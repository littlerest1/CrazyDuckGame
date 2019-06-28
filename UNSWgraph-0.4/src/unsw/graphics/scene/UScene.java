package unsw.graphics.scene;

import java.awt.Color;

import com.jogamp.opengl.GL3;

import unsw.graphics.CoordFrame2D;
import unsw.graphics.geometry.Polygon2D;

/**
 * Letter U drawn by 2 rectangle and 2 semicircle
 * @author 89198
 *
 */
public class UScene extends SceneObject{
	private Color myFillColor;
	
	public UScene(SceneObject parent) {
	    super(parent);
	    myFillColor = new Color(0.76f,0.12f,0.34f);

    	Polygon2D i = new Polygon2D(-2,3, -1,3, -1,1, -2,1);
    	
      	PolygonalSceneObject row1 = new  PolygonalSceneObject(this,i,myFillColor,null);
     

      	Polygon2D i2 = new Polygon2D(1,3, 2,3, 2,1, 1,1);
    	
      	PolygonalSceneObject row2 = new  PolygonalSceneObject(this,i2,myFillColor,null);
      
    	
      	HalfCircleSceneObj o1 = new HalfCircleSceneObj(this,16,1,myFillColor,null);
    	o1.scale(2);
    	o1.rotate(180);
    	o1.translate(0, 1);
    	
    	HalfCircleSceneObj o2 = new HalfCircleSceneObj(this,16 ,1,Color.white,null);
    	o2.rotate(180);
    	o2.translate(0, 1);
    
	}

    
    @Override
    public void drawSelf(GL3 gl, CoordFrame2D frame) {
    //	System.out.println("Drawing U");
    	
    }

}
