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
 * Draw line and calculate the global coordinate and local coordinate of the line
 * @author 89198
 *
 */
public class LineSceneObject extends SceneObject{
	private Line2D myLine;
    private Color myLineColor;
	    
	public LineSceneObject(SceneObject parent, Color lineColor) {
		super(parent);
		this.myLineColor = lineColor;
		Point2D x = new Point2D(0,0);
		Point2D y = new Point2D(1,0);
		myLine = new Line2D(x,y);
	}
	
	public LineSceneObject(SceneObject parent, float x0, float y0, float x1, float y1, Color lineColor) {
		super(parent);
		Point2D x = new Point2D(x0,y0);
		Point2D y = new Point2D(x1,y1);
		myLine = new Line2D(x,y);
		myLineColor = lineColor;
	}
	
	public Color getLineColor() {
	        return myLineColor;
	}

	public List<Point2D> points(){
		List<Point2D> points = new ArrayList<Point2D>();
		
		points.add(myLine.getStart());
		points.add(myLine.getEnd());
		return points;
		
 	}
	
	 public List<Point2D> getVetexs(){
	    	List<Point2D> temp = this.points();
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
    
    @Override
    public void drawSelf(GL3 gl, CoordFrame2D frame) {
    	if(this.myLineColor == null) {
    		return ;
    	}
    	Shader.setPenColor(gl, this.myLineColor);
    	myLine.draw(gl, frame);
    }

    
}
