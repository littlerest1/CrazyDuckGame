package unsw.graphics.scene;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL3;

import unsw.graphics.CoordFrame2D;
import unsw.graphics.Matrix3;
import unsw.graphics.Vector3;
import unsw.graphics.geometry.Point2D;
import java.lang.Math;

/**
 * A SceneObject is an object that can move around in the world.
 * 
 * SceneObjects form a scene tree.
 * 
 * Each SceneObject is offset from its parent by a translation, a rotation and a scale factor. 
 *
 * TODO: The methods you need to complete are at the bottom of the class
 *
 * @author malcolmr
 * @author Robert Clifton-Everest
 */
public class SceneObject {
    
    // the links in the scene tree
    private SceneObject myParent;
    private List<SceneObject> myChildren;

    // the local transformation
    private Point2D myTranslation;
    private float myRotation; //normalised to the range [-180..180)
    private float myScale;
    
    // Is this part of the tree showing?
    private boolean amShowing;

    /**
     * Special constructor for creating the root node. Do not use otherwise.
     */
    public SceneObject() {
        myParent = null;
        myChildren = new ArrayList<SceneObject>();

        myRotation = 0;
        myScale = 1;
        myTranslation = new Point2D(0,0);

        amShowing = true;
    }

    /**
     * Public constructor for creating SceneObjects, connected to a parent.
     *  
     * New objects are created at the same location, orientation and scale as the parent.
     *
     * @param parent
     */
    public SceneObject(SceneObject parent) {
        myParent = parent;
        myChildren = new ArrayList<SceneObject>();

        parent.myChildren.add(this);

        myRotation = 0;
        myScale = 1;
        myTranslation = new Point2D(0,0);

        // initially showing
        amShowing = true;
    }

    /**
     * Remove an object and all its children from the scene tree.
     */
    public void destroy() {
	    List<SceneObject> childrenList = new ArrayList<SceneObject>(myChildren);
        for (SceneObject child : childrenList) {
            child.destroy();
        }
        if(myParent != null)
                myParent.myChildren.remove(this);
    }

    /**
     * Get the parent of this scene object
     * 
     * @return
     */
    public SceneObject getParent() {
        return myParent;
    }

    /**
     * Get the children of this object
     * 
     * @return
     */
    public List<SceneObject> getChildren() {
        return myChildren;
    }

    /**
     * Get the local rotation (in degrees)
     * 
     * @return
     */
    public float getRotation() {
        return myRotation;
    }

    /**
     * Set the local rotation (in degrees)
     * 
     * @return
     */
    public void setRotation(float rotation) {
        myRotation = MathUtil.normaliseAngle(rotation);
    }

    /**
     * Rotate the object by the given angle (in degrees)
     * 
     * @param angle
     */
    public void rotate(float angle) {
        myRotation += angle;
        myRotation = MathUtil.normaliseAngle(myRotation);
    }

    /**
     * Get the local scale
     * 
     * @return
     */
    public float getScale() {
        return myScale;
    }

    /**
     * Set the local scale
     * 
     * @param scale
     */
    public void setScale(float scale) {
        myScale = scale;
    }

    /**
     * Multiply the scale of the object by the given factor
     * 
     * @param factor
     */
    public void scale(float factor) {
        myScale *= factor;
    }

    /**
     * Get the local position of the object 
     * 
     * @return
     */
    public Point2D getPosition() {
        return myTranslation;
    }

    /**
     * Set the local position of the object
     * 
     * @param x
     * @param y
     */
    public void setPosition(float x, float y) {
        setPosition(new Point2D(x,y));
    }

    /**
     * Set the local position of the object
     * 
     * @param x
     * @param y
     */
    public void setPosition(Point2D p) {
        myTranslation = p;
    }

    /**
     * Move the object by the specified offset in local coordinates
     * 
     * @param dx
     * @param dy
     */
    public void translate(float dx, float dy) {
        myTranslation = myTranslation.translate(dx, dy);
    }

    /**
     * Test if the object is visible
     * 
     * @return
     */
    public boolean isShowing() {
        return amShowing;
    }

    /**
     * Set the showing flag to make the object visible (true) or invisible (false).
     * This flag should also apply to all descendents of this object.
     * 
     * @param showing
     */
    public void show(boolean showing) {
        amShowing = showing;
    }

    /**
     * Update the object and all it's children. This method is called once per frame. 
     * 
     * @param dt The amount of time since the last update (in seconds)
     */
    public void update(float dt) {
        updateSelf(dt);
        
        // Make a copy of all the children to avoid concurrently modification issues if new objects
        // are added to the scene during the update.
        List<SceneObject> children = new ArrayList<SceneObject>(myChildren);
        for (SceneObject so : children) {
            so.update(dt);
        }
    }

    /** 
     * Update the object itself. Does nothing in the default case. Subclasses can override this
     * for animation or interactivity.
     * 
     * @param dt
     */
    public void updateSelf(float dt) {
        // Do nothing by default
    }

    /**
     * Draw the object (but not any descendants)
     * 
     * This does nothing in the base SceneObject class. Override this in subclasses.
     * 
     * @param gl
     */
    public void drawSelf(GL3 gl, CoordFrame2D frame) {
    }

    
    // ===========================================
    // COMPLETE THE METHODS BELOW
    // ===========================================
    
    /**
     * Draw the object and all of its descendants recursively.
     * 
     * TODO: Complete this method
     * 
     * @param gl
     */
    public void draw(GL3 gl, CoordFrame2D frame) {
        
        // don't draw if it is not showing
        if (!amShowing) {
            return;
        }
        CoordFrame2D  thisframe;
        
        if(this.myParent != null) {
        //	System.out.println("Use global");
	      thisframe = frame
	        		.translate(this.getPosition())
	        	    .rotate(this.getRotation())
	        	    .scale(this.getScale(),this.getScale());
	   //   System.out.println("Center (" + this.getPosition().getX() + "," + 
	    	//	  this.getPosition().getY()   );
        // TODO: Compute the coordinate frame for this object
        // draw the object (Call drawSelf() to draw the object itself) 
        // and all its children recursively
        }
        else {
        	 Point2D origin = new Point2D(0f,0f);
        	 thisframe = CoordFrame2D.identity()
 	        		.translate(origin)
 	        	    .rotate(this.myRotation)
 	        	    .scale(this.myScale,this.myScale);
        }
        drawSelf(gl,thisframe);
        if(!this.myChildren.isEmpty()) {
        	for(SceneObject x : this.myChildren) {
        		x.draw(gl, thisframe);
        	}
        }
       
    }

    /**
     * Compute the object's position in world coordinates
     * 
     * @return a point in world coordinats
     */
    public Point2D getGlobalPosition() {
        // TODO: Complete this
    	if(myParent != null) {
    		float angle = myParent.getGlobalRotation();
    		//System.out.println("angle faced " + angle);
    		float scale = myParent.getGlobalScale();
    		//System.out.println("Scale using " + scale);
    		Point2D trans = myParent.getGlobalPosition();
    		//System.out.println("translation point (" + trans.getX()+", " + trans.getY() + ")");
    		//Point2D Local = this.getPosition();
    		
    		Matrix3 translation = Matrix3.translation(trans);
    		Matrix3 rotation = Matrix3.rotation(angle);
    		Matrix3 scaleG = Matrix3.scale(scale, scale);
    		Vector3 local = new Vector3(this.myTranslation.getX(),this.myTranslation.getY(),1);
    	//	System.out.println("(" + local.asPoint2D().getX() + " , " + local.asPoint2D().getY() + ")");
    		Vector3 globalfirst = scaleG.multiply(local);
    		Vector3 globalSecond = rotation.multiply(globalfirst);
    		Vector3 globalFinal = translation.multiply(globalSecond);
    	//	this.set(globalFinal.asPoint2D());
    	//	System.out.println("global position (" + this.getPosition().getX()+ ", " 
    		//		+ this.getPosition().getY() + ")");
    		return globalFinal.asPoint2D();
    		
    	}

        return this.myTranslation;
    }

    /**
     * Compute the object's rotation in the global coordinate frame
     * 
     * @return the global rotation of the object (in degrees) and 
     * normalized to the range (-180, 180) degrees. 
     */
    public float getGlobalRotation() {
        // TODO: Complete this
    	float angle = this.getRotation();
    	if(myParent != null) {
    		angle = myParent.myRotation + this.myRotation;
    		if(angle > 180) {
    			angle = -(360 - angle);
    			
    		}
    		else if(angle < -180) {
    			angle = 360 + angle;
    		}
    	}
    
        return angle;
    }

    /**
     * Compute the object's scale in global terms
     * 
     * @return the global scale of the object 
     */
    public float getGlobalScale() {
    
        // TODO: Complete this
    
    	float scale = this.getScale();
    	if(myParent != null ) {
    		scale = myParent.myScale * this.myScale;
    	}
 
        return scale;
    }

    /**
     * Change the parent of a scene object.
     * 
     * @param parent
     */
    public void setParent(SceneObject parent) {
        // TODO: add code so that the object does not change its global position, rotation or scale
        // when it is reparented. You may need to add code before and/or after 
        // the fragment of code that has been provided - depending on your approach
    
    	//	System.out.println("global scale before " +this.getGlobalScale());
    	//System.out.println("global rotation before " +this.getGlobalRotation());
    	float globalR = this.getGlobalRotation();
    	Point2D globalP = this.getGlobalPosition();
    	
    	
        myParent.myChildren.remove(this);
        myParent = parent;
        myParent.myChildren.add(this);
      //  System.out.println("global scale after " +this.getGlobalScale());
        float backS = this.myScale/parent.getScale();
        
    	this.setScale(backS);
    	//System.out.println("backS " + backS);
    	
    	//System.out.println("new parent rotation " + parent.myRotation);
    	float backR = 0;
    	if(parent.myRotation > 0) 
		{
    	//	System.out.println("new parent rotation greater than 0 ");
			backR = globalR - parent.myRotation;
		}
    	else {
    		backR = globalR + Math.abs(parent.myRotation);
    //		System.out.println("backR " + backR);
    		if(backR > 180) {
    			backR = 360- backR;
    			backR = -backR;
    		}
    	}
    	this.setRotation(backR);
    //	System.out.println("back " + backR);
    	
    	backS = 1/parent.getScale();
    	Vector3 now = new Vector3(globalP.getX(),globalP.getY(),1);
    	Matrix3 scale = Matrix3.scale(backS,backS);
    	Matrix3 rotate = Matrix3.rotation(-parent.getRotation());
    	Matrix3 trans = Matrix3.translation(-parent.getPosition().getX(),-parent.getPosition().getY());
    	
    	Vector3 v1 = trans.multiply(now);
    	Vector3 v2 = rotate.multiply(v1);
    	Vector3 v3 = scale.multiply(v2);
    //	System.out.println("set the position to (" + v3.asPoint2D().getX() + " , " + v3.asPoint2D().getY() + ")");
        this.setPosition(v3.asPoint2D());
  //      System.out.println("global Rotation " +this.getGlobalRotation());
    //    System.out.println("Global Scale " + this.getGlobalScale());
      //  System.out.println("Global position " + this.getGlobalPosition());
     //   System.out.println("("+ v2.asPoint2D().getX() + "," + v3.asPoint2D().getY() + ")");
        
    }
    

}
