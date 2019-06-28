package unsw.graphics.scene;

/**
 * A cool scene object
 *
 */

import java.awt.Color;
import java.util.*;

import com.jogamp.opengl.GL3;

import unsw.graphics.CoordFrame2D;
import unsw.graphics.Matrix3;
import unsw.graphics.Shader;
import unsw.graphics.Vector3;
import unsw.graphics.geometry.Point2D;
import unsw.graphics.geometry.Polygon2D;

/**
 * Draw sea wave with 2 curve and 2 semicircle
 * @author z5161462
 *
 */
public class MyCoolSceneObject extends SceneObject {
	private Color myLineColor;
	private ArrayList<Point2D> vertex = new ArrayList<Point2D>();
	
		public MyCoolSceneObject(SceneObject parent) {
		    super(parent);
		    myLineColor = new Color(0,0.41f,0.58f);
		    HalfCircleSceneObj l = new HalfCircleSceneObj(this,3,4f,null,this.myLineColor);
	    	l.translate(0f, 4.6f);
	    	l.rotate(-90f);
	    	
	    	HalfCircleSceneObj l2 = new HalfCircleSceneObj(this,4,4f,null,this.myLineColor);
	    	l2.translate(0.19f, 4.21f);
	    	l2.rotate(-90f);
	    	
	    	//System.out.println("Drawing circle1");
	    	
	    	HalfCircleSceneObj c1 = new HalfCircleSceneObj(this,16,0.5f,null,this.myLineColor);
	    	c1.translate(3, 2);
	    	
	    	HalfCircleSceneObj c2 = new HalfCircleSceneObj(this,16,0.25f,new Color(0.53f,0.81f,0.92f),this.myLineColor);
	    	c2.translate(2.75f, 2);
	    	c2.rotate(180);
		}

		public Color getLineColor() {
		        return myLineColor;
		}

	    /**
	     * Set the fill color.
	     * 
	     * Setting the color to null means the object should not be filled.
	     * 
	     * @param fillColor The fill color
	     */
	    public void setLineColor(Color lineColor) {
	        myLineColor = lineColor;
	    }
	    
	    public List<Point2D> getVetexs(){
	    	List<Point2D> temp = this.vertex;
	    //	System.out.println("My cool obj " + temp.isEmpty());
	    	List<Point2D> re = new ArrayList<Point2D>();
	    	for(Point2D e : temp) {
	    	//	System.out.println("local (" + e.getX() + " , " 
	    		//		+ e.getY() + ")");
	    		
	    		
	    		if(this.getParent() != null) {
	        		float angle =this.getGlobalRotation();
	        		//System.out.println("angle faced " + angle);
	        		float scale = this.getGlobalScale();
	        		//System.out.println("Scale using " + scale);
	        		Point2D trans = this.getGlobalPosition();
	        		//System.out.println("translation point (" + trans.getX()+", " + trans.getY() + ")");
	        		//Point2D Local = this.getPosition();
	        		
	        		
	        		Matrix3 translation = Matrix3.translation(trans);
	        		Matrix3 rotation = Matrix3.rotation(angle);
	        		Matrix3 scaleG = Matrix3.scale(scale, scale);
	        		
	        		
	        		Vector3 local = new Vector3(e.getX(),e.getY(),1);
	        	//	System.out.println("(" + local.asPoint2D().getX() + " , " + local.asPoint2D().getY() + ")");
	        		Vector3 globalfirst = scaleG.multiply(local);
	        		Vector3 globalSecond = rotation.multiply(globalfirst);
	        		Vector3 globalFinal = translation.multiply(globalSecond);
	        	//	this.set(globalFinal.asPoint2D());
	        	//	System.out.println("global position (" + this.getPosition().getX()+ ", " 
	        		//		+ this.getPosition().getY() + ")");
	        		Point2D after =  globalFinal.asPoint2D();
	        	//	System.out.println("global (" + after.getX() + " , " 
	        		//		+ after.getY() + ")");
	        		
	        		re.add(after);
	        	}
	    		else {
	    			re.add(e);
	    		}
	    	}
	    	return re;
	    }
	    
	    
	    @Override
	    public void drawSelf(GL3 gl, CoordFrame2D frame) {
	    	
	    }
	
}
