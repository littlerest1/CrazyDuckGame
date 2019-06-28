package unsw.graphics.scene;

import java.awt.Color;

import com.jogamp.opengl.GL3;

import unsw.graphics.CoordFrame2D;
import unsw.graphics.geometry.Polygon2D;

/**
 * Drawing heart
 * @author z5161462
 *
 */
public class Heart extends SceneObject{
	private Color myFillColor;
	
	public Heart(SceneObject parent,Color c) {
	    super(parent);
	    myFillColor = c;
	    HalfCircleSceneObj o1 = new HalfCircleSceneObj(this,16,1,myFillColor,null);
    		
    	HalfCircleSceneObj o2 = new HalfCircleSceneObj(this,16,1,myFillColor,null);
    	o2.translate(2, 0);
    
    	Polygon2D tri = new Polygon2D(-1f,0, 3f,0, 1,-2);
    	PolygonalSceneObject tail = new PolygonalSceneObject(this,tri,this.myFillColor,null);

	}

    
    @Override
    public void drawSelf(GL3 gl, CoordFrame2D frame) {
    	
    }

}
