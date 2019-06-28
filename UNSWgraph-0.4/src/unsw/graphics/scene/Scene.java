package unsw.graphics.scene;




import java.util.*;

import com.jogamp.opengl.GL3;

import unsw.graphics.CoordFrame2D;
import unsw.graphics.geometry.Point2D;


/**
 * A Scene consists of a scene tree and a camera attached to the tree.
 * 
 * Every object in the scene tree is updated on each display call.
 * Then the scene tree is rendered.
 *
 * You shouldn't need to modify this class.
 *
 * @author malcolmr
 * @author Robert Clifton-Everest
 * 
 */
public class Scene {

    private Camera myCamera;

    private SceneObject root;
    private long myTime;
 

    /**
     * Construct a new scene with a camera attached to the root object.
     *
     */
    public Scene() {
        root = new SceneObject();
        myTime = System.currentTimeMillis();
        myCamera = new Camera(root);
    }

    public void reshape(int width, int height) {
        
        // tell the camera that the screen has reshaped
        myCamera.reshape(width, height);
    }

    public void draw(GL3 gl) {

        // set the view matrix based on the camera position
        myCamera.setView(gl); 
        
        // update the objects
        update();

        // draw the scene tree
        root.draw(gl, CoordFrame2D.identity());        
    }

    private void update() {
        
        // compute the time since the last frame
        long time = System.currentTimeMillis();
        float dt = (time - myTime) / 1000f;
        myTime = time;
        
        root.update(dt);      
    }

    public SceneObject getRoot() {
        return root;
    }
   
    public Camera getCamera() {
        return myCamera;
    }

    public void setCamera(Camera camera) {
        myCamera.destroy();
        this.myCamera = camera;
    }
    
    /**
     * Main collision function
     * @param p
     * @return
     */
    public List<SceneObject> collision(Point2D p){
    //	System.out.println("Print collision");
    	List<SceneObject> OS = new ArrayList<SceneObject>();
    	/**
    	 * Get all the scene objects in this scene
    	 */
    	List<SceneObject> fullVer = GetAll(root.getChildren());
    //	PrintList(fullVer);
    //	System.out.println(getall(root.getChildren()));
    	for(SceneObject e : fullVer) {
    		if(e.getClass().getName().contains("Camera")) {
    			continue;
    		}
    	//	System.out.println(e.getClass().getName());
    		if(e.getClass().getName().contains("Polygonal")) {
    			PolygonalSceneObject poly = (PolygonalSceneObject) e;
    		
    		//	List<Point2D> points = polygon.getList();
    			List<Point2D> points = poly.getVetexs();
    			/**
    			 * if is the vertex of the polygon
    			 */
    			if(points.contains(p)) {
    				System.out.println("is vertex");
    				OS.add(e);
    			}
    			else {
    			//	List<Point2D> ori = polygon.getList();
    				List<Point2D> ori = poly.getVetexs();
    				List<Point2D> test = poly.getVetexs();
    			//	PrintPoints(test);
    				/**
    				 * Sort the vertex with x-coordinate hence get the x range
    				 */
	    			Collections.sort(points, XComparator);
	    			//PrintPoints(points);
	    			//float maxX = points.get(0).getX();
	    			
	    			if(p.getX() <= points.get(0).getX() && p.getX() >= points.get(points.size()-1).getX()) {
	    				Collections.sort(points, YComparator);
	    				//PrintPoints(points);
	    				/**
	    				 * if the point is in the range of polygon
	    				 */
	    				if(p.getY() <= points.get(0).getY() && p.getY() >= points.get(points.size()-1).getY()) {
	    				//	System.out.println("Point P(" + p.getX() + " , " + p.getY() + ") ");
	        			//	Line2D temp = new Line2D(p,new Point2D(p.getX()+maxX, p.getY()));
	        				/**
	        				 * check if is in the line but not exactly is the vertex
	        				 */
	    					boolean t = inLine(ori,p);
	        				//System.out.println(t);
	        				if(t) {
	        					OS.add(e);
	        					continue;
	        				}
	        				else {
	        					/**
	        					 * Finally cut the shape if it touch the outline in odd times
	        					 * is in the shape
	        					 * Filter the points of the polygon in the same y-axis as p
	        					 */
		    					List<Point2D> filter = possible(ori, p.getY());
		        	//			PrintPoints(filter);
		        		//		System.out.println(filter.size());
		        				int count = filter.size()/2;
		        				if(count%2 ==1) {
		        					OS.add(e);
		        					continue;
		        				}
	        			
	        				}
	    				}
	    			}
    			}
    		}
    		/**
    		 * if is line object just apply the linear equation
    		 */
    		else if(e.getClass().getName().contains("Line")) {
    			LineSceneObject line = (LineSceneObject) e;
    			List<Point2D> points = line.getVetexs();
    			
    			if(points.contains(p)) {
    				OS.add(e);	
    			}
    			else {
	    			float a = (float) (points.get(1).getY() - points.get(0).getY()) / (points.get(1).getX() - points.get(0).getX());
	    			float b = (float) points.get(0).getY() - (points.get(0).getX()* a);
	    			if(p.getY() == a*p.getX() + b) {
	    				OS.add(e);	
	    			}
    			}
    		}
    		/**
    		 * Circular is made by the polygon rotation hence basically the same
    		 */
    		else if(e.getClass().getName().contains("Circular")) {
    			CircularSceneObject poly = (CircularSceneObject) e;
    		//	List<Point2D> points = polygon.getList();
    			poly.setVertex();
    			List<Point2D> points = poly.getVetexs();
    			
    			if(points.contains(p)) {
    			//	System.out.println("is vertex");
    				OS.add(e);
    			}else {
    				List<Point2D> ori = poly.getVetexs();
    				List<Point2D> test = poly.getVetexs();
    			//	PrintPoints(test);
    				
    				Collections.sort(points, XComparator);
	    		//	PrintPoints(points);
    				
	    			if(p.getX() <= points.get(0).getX() && p.getX() >= points.get(points.size()-1).getX()) {
	    				Collections.sort(points, YComparator);
	    				PrintPoints(points);
	    			
	    				if(p.getY() <= points.get(0).getY() && p.getY() >= points.get(points.size()-1).getY()) {
	    				//	System.out.println("Point P(" + p.getX() + " , " + p.getY() + ") ");
	        			//	Line2D temp = new Line2D(p,new Point2D(p.getX()+maxX, p.getY()));
	        				boolean t = inLine(ori,p);
	        				//System.out.println(t);
	        				if(t) {
	        					OS.add(e);
	        					continue;
	        				}
	        				else {
		    					List<Point2D> filter = possible(ori, p.getY());
		        				//PrintPoints(filter);
		        			//	System.out.println(filter.size());
		        				int count = filter.size()/2;
		        				if(count%2 ==1) {
		        					OS.add(e);
		        					continue;
		        				}
	        			
	        				}
	    				}
	    			}
    				
    			}
    		}
    		
    	}
    	
    	return OS;
    	
    }
    //Decreasing order
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
  //Decreasing order
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
    
    
    private void PrintPoints(List<Point2D> p) {
    	for(Point2D x : p) {
    		System.out.print("(" + x.getX() + " , " + x.getY() + ") ");
    	}
    	System.out.println();
    }
    
    
    private void PrintList(List<SceneObject> l) {
    	System.out.print("Size " + l.size() + " Create list: ");
    	for(SceneObject e : l) {
    		System.out.print(e.getClass().getName() + " ");
    	}
    	
    	System.out.println();
    }
    
    private List<Point2D> possible(List<Point2D>p, float y){
    	ArrayList<Point2D> ps = new ArrayList<Point2D>();
    	
    	for(int n = 0; n < p.size() ; n ++) {
    		if(n == p.size() - 1) {
    			if(p.get(n).getY() < y && p.get(0).getY() > y) {
    				ps.add(p.get(n));
    				ps.add(p.get(0));
    			}
    		}
    		else if(p.get(n).getY() < y && p.get(n+1).getY() > y) {
    			ps.add(p.get(n));
				ps.add(p.get(n+1));
    		}
    	}
    	return ps;
    }
    
    private boolean inLine(List<Point2D>points, Point2D p) {
    	for(int n = 0; n < points.size() ; n ++) {
    		if(n == points.size() - 1) {
    			Point2D start = points.get(n);
        		Point2D end = points.get(0);
        		float a = (float) (end.getY() - start.getY()) / (end.getX() - start.getX());
    			float b = (float) start.getY() - (start.getX()* a);
    			if(p.getY() == a*p.getX() + b) {
    				return true;
    			}
    			
    		}
    		else {
	    
	    		Point2D start = points.get(n);
        		Point2D end = points.get(0);
        		float a = (float) (end.getY() - start.getY()) / (end.getX() - start.getX());
    			float b = (float) start.getY() - (start.getX()* a);
    			if(p.getY() == a*p.getX() + b) {
    				return true;
    			}
    		}
    	}
    	return false;
    }

    /**
     * Get all the scene object in the scene, find all the children under parent object
     * @param fathers
     * @return
     */
    private List<SceneObject> GetAll(List<SceneObject> fathers) {
    	
    	List<SceneObject> sent = new ArrayList<SceneObject>();
    	List<SceneObject> newList = new ArrayList<>(fathers);
   
    	for(int n = 0; n < fathers.size(); n++) {
    		SceneObject temp = fathers.get(n);
    		SceneObject e = temp;
    		sent.add(e);
    		if(!temp.getChildren().isEmpty()) {
	    		while(temp.getChildren().size() > 0) {
	    			ArrayList<SceneObject> k = (ArrayList<SceneObject>) temp.getChildren();
	    			
	    		//	System.out.println(e.getClass().getName() + " has child");
	    		  //  System.out.println(k.size());
	    		    newList.remove(e);
	    		    List<SceneObject> first = GetAll(k);
	    		    List<SceneObject> second = GetAll(newList);
	    		    List<SceneObject> third = Combine(sent,first);
	    		    List<SceneObject> last = Combine(third,second);
	    		    return last;
	    			//return sent + getall(k) + getall(newList);
	    		   
	    		}
    		}
    	

    		
    		
    	}
    	return sent;
    }
    
    /**
     * Helper function, combines 2 arraylists to 1 arraylist
     * @param fathers
     * @param child
     * @return
     */
    private List<SceneObject> Combine(List<SceneObject> fathers,List<SceneObject> child) {
    	List<SceneObject> sent = new ArrayList<SceneObject>();
    	int flag = 0;
    	for(int n = 0; n < fathers.size(); n++) {
    		if(flag == 1 && fathers.get(n).getClass().getName().contains("Camera")) {
    			continue;
    		}
    		else if(fathers.get(n).getClass().getName().contains("Camera")) {
    			flag = 1;
    			sent.add(fathers.get(n));
    		}
    		else{
    			sent.add(fathers.get(n));
    		}
    	}
    	
    	for(int n = 0; n < child.size(); n++) {
    		if(flag == 1 && child.get(n).getClass().getName().contains("Camera")) {
    			continue;
    		}
    		else if(child.get(n).getClass().getName().contains("Camera")) {
    			flag = 1;
    			
    		}
    		else{
    			sent.add(child.get(n));
    		}
    	}
    	
    	return sent;
    }
}
