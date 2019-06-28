package unsw.graphics.scene;

import java.awt.Color;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.opengl.GL3;

import unsw.graphics.CoordFrame2D;
import unsw.graphics.geometry.Point2D;
import unsw.graphics.geometry.Polygon2D;
import com.jogamp.newt.event.*;

//Main object of the game
public class Duck extends SceneObject implements KeyListener{
	private Color myLineColor;
	private Color myFillColor;
	private Point2D head = new Point2D(-4,0.2f);
	private Point2D Left = new Point2D(-4,-0.6f);
	private boolean up = true;
	private int die = -2;
	private int count =0;
	private HalfCircleSceneObj o;
	private CircularSceneObject o2;
	
	public Duck(SceneObject parent) {
	    super(parent);
	    myLineColor = new Color(0.55f,0.27f,0.07f);
	    myFillColor = new Color(1f,0.83f,0.36f);
    	//tail
    	o = new HalfCircleSceneObj(this,16,1,myFillColor,myLineColor);
    	o.rotate(90);
    	
    	//body
    	Polygon2D p = new Polygon2D(0,1, 2,1, 2,-1, 0,-1);
    	PolygonalSceneObject body = new PolygonalSceneObject(this,p,myFillColor,null);
    	
    	//breast
    	HalfCircleSceneObj o1 = new HalfCircleSceneObj(this,16,1,myFillColor,myLineColor);
    	
    	
    	o1.rotate(-90);
    	o1.translate(2, 0);
    	
    	
    	//mouth
    	Color orange = new Color(1f,0.27f,0);
    	HalfCircleSceneObj o4 = new HalfCircleSceneObj(this,15,1,orange,myLineColor);
    	o4.translate(2.25f, 1);
    	o4.scale(0.5f);

    	
    	//head
    	o2 = new CircularSceneObject(this,1,myFillColor,myLineColor);
    	o2.translate(1.5f, 1.5f);
    	o2.scale(0.75f);
 

    	
    	//eye
    	CircularSceneObject o3 = new CircularSceneObject(this,1,Color.BLACK,null);
    	o3.translate(1.75f, 1.75f);
    	o3.scale(0.2f);
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
    
    
    @Override
    public void updateSelf(float dt) { 
    	//if not start or already die/win, the duck should not move
    	 if(this.die == -1 || this.die == -2 || this.die == 3) {
    		 return;
    	 }
    	 //if in the first wave speed in 0.01
    	 if(this.die == 0) {
    		 this.setPosition(this.getPosition().getX()+0.01f, this.getPosition().getY());
    	 }
    	 //if in the second wave speed in 0.03
    	 else if(this.die == 1) {
    		 this.setPosition(this.getPosition().getX()+0.03f, this.getPosition().getY());
    	 } 
    	 //if in the second wave speed in 0.05
    	 else if(this.die == 2) {
    		 this.setPosition(this.getPosition().getX()+0.05f, this.getPosition().getY());
    	 }
    	 // if the duck is flying is no limit for going right however if it
    	 //get out of the scene will die
    	 /** 
    	  * THIS MEANS SOMETIMES WHEN YOU CHOOSE A SHORTCUT,
    	  *  YOU WILL MISS ALL THE VIEWS IN THE ROAD.
    	 **/
    	 if(head.getY() > 1 && head.getX() < 4) {
           	return;
         }
    	 //if it not landing in the wave it will die
         if(!checkStatus()) {
         	this.setPosition(this.getPosition().getX(), this.getPosition().getY()-2.4f);
 			this.die = -1;
 			System.out.println("Game over");
         }
    }
    
    @Override
    public void drawSelf(GL3 gl, CoordFrame2D frame) {
    	// getting current position of the duck and update its position
    	o.setVertex();
    	List<Point2D> tail  = o.getVetexs();
    	Collections.sort(tail,XComparator);
    	Left = tail.get(tail.size()-1);
    	
        o2.setVertex();
    	List<Point2D> all =  o2.getVetexs();
    	Collections.sort(all,YComparator);
    	head = all.get(0);
    	updateSelf(0.1f);
    	
    }
    
    public int getStatus() {
    	return this.die;
    }
    
    Comparator<Point2D> XComparator = new Comparator<Point2D>() {
        @Override
        public int compare(Point2D e1, Point2D e2) {
            if(e1.getX() < e2.getX()) {
                return 1;
            } else if (e1.getX() > e2.getX()) {
                return -1;
            } else {
                return 0;
            }
        }
    };
   
    Comparator<Point2D> YComparator = new Comparator<Point2D>() {
        @Override
        public int compare(Point2D e1, Point2D e2) {
            if(e1.getY() < e2.getY()) {
                return 1;
            } else if (e1.getY() > e2.getY()) {
                return -1;
            } else {
                return 0;
            }
        }
    };
    
    /**
     * Controller: 
     * ENTER ----- Start Game
     * UP ---- Rotate upper
     * DOWN ---- Rotate lower
     * SPACE ----- Jump up and down
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
        case KeyEvent.VK_UP:
            System.out.println("Go up");
            this.setRotation(this.getRotation() + 3);
            break;
        case KeyEvent.VK_DOWN:
            System.out.println("Go down");
            this.setRotation(this.getRotation() - 3);
            break;

        case KeyEvent.VK_SPACE:
        	if(up) {
        		this.setPosition(this.getPosition().getX(), this.getPosition().getY()+1.6f);
        		up = false;
        	}
        	else {
        		
        	//	this.die ++;
        		if(checkStatus()) {
        			this.setPosition(this.getPosition().getX(), this.getPosition().getY()-1.6f);
        			up = true;
        		}
        		else {
        			this.setPosition(this.getPosition().getX(), this.getPosition().getY()-3.5f);
        			this.die = -1;
        			System.out.println("Game over");
        		}
        	}
            break;
        case KeyEvent.VK_ENTER:
        	if(die == -2) {
        		die = 0;
        		break;
        	}
        }

    }

    /**
     * Checking and update status
     * @return true if its in any wave's range, false for die
     */
    private boolean checkStatus() {
    //	System.out.println("Left " + Left.getX() + "," + Left.getY());
    	//System.out.println("Head " + head.getX() + "," + head.getY());
    	boolean result = false;
    	if(Left.getX() >= -4 && Left.getX() <= -3.5f) {
    		
    		result  = true;
    	}
    	if(Left.getX() >= -2 && Left.getX() <= -0.5f) {
    		this.die = 1;
    		result  = true;
    	}
    	if(Left.getX() >= 0 && Left.getX() <= 1.5f) {
    		this.die = 2;
    		result  = true;
    	}
    	if(Left.getX() >= 2 && Left.getX() <= 3.5f) {
    		this.die = 3;
    		result  = true;
    	}
    	return result;
    }

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}
}
