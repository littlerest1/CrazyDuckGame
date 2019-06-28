package unsw.graphics.scene;

import java.awt.Color;
import java.util.*;

import com.jogamp.opengl.GL3;

import unsw.graphics.CoordFrame2D;
import unsw.graphics.Matrix3;
import unsw.graphics.Shader;
import unsw.graphics.Vector3;
import unsw.graphics.geometry.Line2D;
import unsw.graphics.geometry.Point2D;
import unsw.graphics.geometry.Polygon2D;

/**
 * Drawing circle in given part,use to draw curve and Semicircle
 * @author z5161462
 *
 */
class HalfCircleSceneObj extends PolygonalSceneObject{
		private Color myFillColor;
		private Color myLineColor;
		private Point2D myCenter;
		private float myRadius;
		private int myCut;
		private ArrayList<Point2D> vertex = new ArrayList<Point2D>();

		public HalfCircleSceneObj(SceneObject parent,int cut, Color fillColor, Color lineColor) {
			
			super(parent,null,fillColor,lineColor);
			myRadius = 1f;
			myCenter = new Point2D(0f,0f);
			myFillColor = fillColor;
			myLineColor = lineColor;
			myCut = cut;

		}
	
		public HalfCircleSceneObj(SceneObject parent,int cut, float radius, Color fillColor, Color lineColor) {
			super(parent,null,fillColor,lineColor);
			myRadius = radius;
			myCenter = new Point2D(0f,0f);
			myFillColor = fillColor;
			myLineColor = lineColor;
			myCut = cut;
		}
	
		//Get local coordinate of the curve/semicircle
		 public void setVertex() {
			 
		    	Point2D start = new Point2D(this.myRadius,this.myCenter.getY());
		  //  	System.out.println("HalfCircle"+ "  (" + this.myCenter.getX() + "," + this.myCenter.getY() + ")");
		    	float angle = (float)360/32;
		    	float incre =  (float)360/32;
		    	float angle2 = (float) (180-angle)/2;
		    	double radians = Math.toRadians(angle);
		    	double radian2 = Math.toRadians(angle2);
		    	float k = (float) Math.sin(radians)*this.myRadius;
	    		float j = this.myRadius - (k/(float) Math.tan(radian2));
	    		
	    		vertex.add(start);
	    		
	    		for(int n = 0;n < myCut;n ++) {
	        		Matrix3 rotation = Matrix3.rotation(angle);
	        		Vector3 now = new Vector3(start.getX(),start.getY(),1);
	        		Vector3 after = rotation.multiply(now);
	    			vertex.add(after.asPoint2D());
	    			angle += incre;
	    		}   	
		 }
		 
		 //Get global coordinate of the semicircle/curve
		 public List<Point2D> getVetexs(){
		    	List<Point2D> temp = this.vertex;
		    	List<Point2D> re = new ArrayList<Point2D>();
		    //	System.out.println("Half Circle " + temp.isEmpty());
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
		        	//	System.out.println("global position (" + this.getPosition().getX()+ ", " 
		        		//		+ this.getPosition().getY() + ")");
		        		Point2D after =  globalFinal.asPoint2D();
		        //		System.out.println("global (" + after.getX() + " , " 
		        	//			+ after.getY() + ")");
		        		
		        		re.add(after);
		        	}
		    		else {
		    			re.add(e);
		    		}
		    	}
		    	return re;
		    }
		
	 	public Color getFillColor() {
	        return myFillColor;
	    }

	    /**
	     * Set the fill color.
	     * 
	     * Setting the color to null means the object should not be filled.
	     * 
	     * @param fillColor The fill color
	     */
	    public void setFillColor(Color fillColor) {
	        myFillColor = fillColor;
	    }
	
	    /**
	     * Get the outline color.
	     * 
	     * @return
	     */
	    public Color getLineColor() {
	        return myLineColor;
	    }
	
	    /**
	     * Set the outline color.
	     * 
	     * Setting the color to null means the outline should not be drawn
	     * 
	     * @param lineColor
	     */
	    public void setLineColor(Color lineColor) {
	        myLineColor = lineColor;
	    }
	    
	    public void setCenter(Point2D c) {
	    	myCenter = c;
	    }
	    
	    public Point2D getCenter() {
	    	return this.myCenter;
	    }
	    
	    public void setCenter(float x,float y) {
	    	myCenter = new Point2D(x,y);
	    }
	    
	    public float getRadius() {
	    	return myRadius;
	    }
	    
	    public void setRadius(float r) {
	    	myRadius = r;
	    }
	    
	    //How many cuts how many triangles are drawing
	    @Override
	    public void drawSelf(GL3 gl, CoordFrame2D frame) {
	    	if(this.myFillColor == null && this.myLineColor == null) {
	    		return ;
	    	}
	   // 	System.out.println("Drawing circle");
	    	float angle = (float)360/32;
	    	float angle2 = (float) (180-angle)/2;
	    //	System.out.println("rotate " + angle);
	    	double radians = Math.toRadians(angle);
	    	double radian2 = Math.toRadians(angle2);
	    	
    		float k = (float) Math.sin(radians)*this.myRadius;
    		float j = this.myRadius - (k/(float) Math.tan(radian2));
	    	if(this.myFillColor != null) {	
    	//		System.out.println("y-axis " + k + "x-axis " + j);
	    		Polygon2D poly = new Polygon2D(0,0 , j,k, this.myRadius,0);

    	
    			Line2D l = null;
    			if(this.myLineColor != null) {
    				l = new Line2D(this.myRadius,this.myCenter.getY(),j,k);
    			}
    			for(int n = 0; n < myCut; n++) {
    				Shader.setPenColor(gl, this.myFillColor);
	    			poly.draw(gl,frame);    			
	    			if(l != null) {
	    				Shader.setPenColor(gl, this.myLineColor);
	    				l.draw(gl, frame);
	    				
	    			}
	    			frame = frame.rotate(angle);
	    		}
		    	
	    	}
	    	else {
	    		Line2D	l = new Line2D(this.myRadius,this.myCenter.getY(),j,k);
	    		Shader.setPenColor(gl, this.myLineColor);
	    		l.draw(gl,frame);
	    		for(int n = 0; n < myCut; n++) {	    		
	    			frame = frame.rotate(11.25f);
	    			Shader.setPenColor(gl, this.myLineColor);
	    			l.draw(gl,frame);
	    			
	    		}
	    	}

	    }


}
