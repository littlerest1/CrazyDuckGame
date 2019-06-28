package unsw.graphics.scene;

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
 * A game object that has a polygonal shape.
 * 
 * This class extend SceneObject to draw polygonal shapes.
 *
 * TODO: The methods you need to complete are at the bottom of the class
 *
 * @author malcolmr
 * @author Robert Clifton-Everest
 * 
 */
public class PolygonalSceneObject extends SceneObject {

    private Polygon2D myPolygon;
    private Color myFillColor;
    private Color myLineColor;

    /**
     * Create a polygonal scene object and add it to the scene tree
     * 
     * The line and fill colors can possibly be null, in which case that part of the object
     * should not be drawn.
     *
     * @param parent The parent in the scene tree
     * @param points A list of points defining the polygon
     * @param fillColor The fill color
     * @param lineColor The outline color
    */
    public PolygonalSceneObject(SceneObject parent, Polygon2D polygon,
            Color fillColor, Color lineColor) {
        super(parent);

        myPolygon = polygon;
        myFillColor = fillColor;
        myLineColor = lineColor;
    }

    /**
     * Get the fill color
     * 
     * @return
     */
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

    // ===========================================
    // COMPLETE THE METHODS BELOW
    // ===========================================
    
    //Get the local coordinate of polygon
    public Polygon2D getPoly() {
    	return this.myPolygon;
    }

    //Get global coordinate of the polygon
    public List<Point2D> getVetexs(){
    	List<Point2D> temp = this.myPolygon.getList();
    	List<Point2D> re = new ArrayList<Point2D>();
    	for(Point2D e : temp) {
    	//	System.out.println("local (" + e.getX() + " , " 
    	//			+ e.getY() + ")");
    		
    		
    		if(this.getParent() != null) {
        		float angle =this.getGlobalRotation();
        		//System.out.println("angle faced " + angle);
        		float scale = this.getGlobalScale();
        		//System.out.println("Scale using " + scale);
        		Point2D trans = this.getGlobalPosition();
        		//System.out.println("translation point (" + trans.getX()+", " + trans.getY() + ")");
        	
        		
        		
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
     * Draw the polygon
     * 
     * if the fill color is non-null, draw the polygon filled with this color
     * if the line color is non-null, draw the outline with this color
     * 
     */
    @Override
    public void drawSelf(GL3 gl, CoordFrame2D frame) {
    	if(this.myFillColor == null && this.myLineColor == null) {
    		return ;
    	}
    //	System.out.println("Drawing ");
    	if(this.myFillColor == null) {
    		Shader.setPenColor(gl, this.myLineColor);
    		this.myPolygon.drawOutline(gl, frame);
    	}else {
    	//	System.out.println("drawing both fill and outline");
    		Shader.setPenColor(gl, this.myFillColor);
    		this.myPolygon.draw(gl,frame);
    		if(this.myLineColor != null) {
    			Shader.setPenColor(gl, this.myLineColor);
    			this.myPolygon.drawOutline(gl, frame);
    		}
    	}
    

    }


}
