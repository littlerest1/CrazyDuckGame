package unsw.graphics.scene;

import java.awt.Color;

import com.jogamp.opengl.GL3;

import unsw.graphics.CoordFrame2D;
import unsw.graphics.geometry.Polygon2D;

/**
 * Draw letter I
 * @author z5161462
 *
 */
public class IScene extends SceneObject{
	private Color myFillColor;
	
	public IScene(SceneObject parent) {
	    super(parent);
	    myFillColor = new Color(0.76f,0.12f,0.34f);
	    Polygon2D i = new Polygon2D(-2,3, 2,3, 2,2, -2,2);
    	
      	PolygonalSceneObject row1 = new  PolygonalSceneObject(this,i,myFillColor,null);

      	Polygon2D i2 = new Polygon2D(-2,-1, 2,-1, 2,-2, -2,-2);
    	
      	PolygonalSceneObject row2 = new  PolygonalSceneObject(this,i2,myFillColor,null);
    	
      	Polygon2D i3 = new Polygon2D(-0.5f,2, 0.5f,2, 0.5f,-1, -0.5f,-1);
    	
      	PolygonalSceneObject row3 = new  PolygonalSceneObject(this,i3,myFillColor,null);

	}

    
    @Override
    public void drawSelf(GL3 gl, CoordFrame2D frame) {
    //	System.out.println("Drawing I");
    	
    	
    }
}
