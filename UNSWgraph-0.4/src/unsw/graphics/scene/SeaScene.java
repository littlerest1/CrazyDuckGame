package unsw.graphics.scene;

import java.awt.Color;
import java.util.*;


import com.jogamp.opengl.GL3;

import unsw.graphics.CoordFrame2D;
import unsw.graphics.Matrix3;
import unsw.graphics.Vector3;
import unsw.graphics.geometry.Point2D;
import unsw.graphics.geometry.Polygon2D;

/**
 * Sea scene drawn by 4 sea wave and 1 rectangle sea
 * @author z5161462
 *
 */
public class SeaScene extends SceneObject{
	private Color myLineColor;
	private ArrayList<Point2D> vertex = new ArrayList<Point2D>();
	
	public SeaScene(SceneObject parent) {
	    super(parent);
	    myLineColor = new Color(0,0.41f,0.58f);

    	MyCoolSceneObject cso = new MyCoolSceneObject(this);

		cso.translate(0f,-1f);
		cso.scale(0.5f);

		
		MyCoolSceneObject cs1 = new MyCoolSceneObject(this);
		cs1.translate(2f, -1f);
		cs1.scale(0.5f);
		
		MyCoolSceneObject cs2 = new MyCoolSceneObject(this);
		cs2.translate(-2f, -1f);
		cs2.scale(0.5f);
		
		MyCoolSceneObject cs3 = new MyCoolSceneObject(this);
		cs3.translate(-4f, -1f);
		cs3.scale(0.5f);
	//	vertex = (ArrayList<Point2D>) cs3.getVetexs();
		
    	Polygon2D p = new Polygon2D( -50f,0.6f, 50f,0.6f,50f,-50f,-50f,-50f );
    	Color seaColor = new Color(0,0.41f,0.58f);
    	PolygonalSceneObject sea = new PolygonalSceneObject(this,p,seaColor,new Color(0.53f,0.81f,0.92f));
    	sea.translate(0f, -1f);
    	sea.scale(0.5f);
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
    
    public ArrayList<Point2D> pado1(){
    	return this.vertex;
    }
    
    
    public List<Point2D> getVetexs(){
    	List<Point2D> temp = this.pado1();
    //	System.out.println("Sea " + temp.isEmpty());
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
    
    @Override
    public void drawSelf(GL3 gl, CoordFrame2D frame) {
    	
    	
    }
}
