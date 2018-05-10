// Project 1 initial source code
// CMSC427 fall 2017

// Class Controller handles all GUI events
// 

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import com.jogamp.opengl.awt.GLCanvas;

import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import graphicslib3D.Vertex3D;

import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.lang.*;

// An Adapter is a default implementation of a listener, so you don't have implement all 
// required methods
// An Interface Listener requires that you do
public class Controller extends MouseMotionAdapter implements KeyListener, MouseListener {
	
	public static int angle;
	public static Vector3D normalized_vec;
	Code myView;
	//GLCanvas myCanvas;
	public Controller() {
		
	}
	
	// Need to add reference to ModelView object
	// So as to access redraw() and setAngle()
	public void addCode(Code c) {
		myView = c;
	}
	

	 public void mouseReleased(MouseEvent e) {
	    System.out.println("Mouse released; # of clicks: "
	                    + e.getClickCount());
	  }
	 //Part2
	 public MouseEvent clickEvent = null;
	 //public MouseEvent passedEvent = null;
	 public void mousePressed(MouseEvent e) {
		 clickEvent = e;
     }

	 public void mouseEntered(MouseEvent e) {
		 
	 }

	 public void mouseExited(MouseEvent e) {
		       
	 }
	 // Part2
	 public void mouseClicked(MouseEvent e) {
		 
	 }
		
	 // Only function event at moment
	 public void mouseDragged(MouseEvent e) {
		  double mx = e.getX() * 1/300.0 -1;
		  double my = e.getY() * 1/300.0 -1;	  		  
		  double double_mz = Math.sqrt(Math.abs(1 - (mx*mx) - (my*my)));
		  System.out.println(mx + "mx");
		  System.out.println(my + "my");
		  System.out.println(double_mz + "mz");
		  //System.out.println(double_mz + "asdflkasjdflk");
		  double new_double_mx = clickEvent.getX()/300.0-1;
		  double new_double_my = clickEvent.getY()/300.0-1;
		  double new_double_mz = Math.sqrt(Math.abs(1 - (new_double_mx * new_double_mx) - (new_double_my * new_double_my)));
		  System.out.println(new_double_mx+ "newmx");
		  System.out.println(new_double_my + "newmy");
		  System.out.println(new_double_mz + "newmz");
		  Vector3D u0 = new Vector3D(mx, my, double_mz);
		  Vector3D u1 = new Vector3D(new_double_mx, new_double_my, new_double_mz);
		  u0 = u0.normalize();
		  u1 = u1.normalize();
		  Vector3D normal_vec = u0.cross(u1);
		  double normal_vec_leng = normal_vec.magnitude();
		  Vector3D normalized_vec = normal_vec_leng == 0 ? new Vector3D(1, 0, 0) : normal_vec.normalize();
          double angle = Math.asin(normal_vec_leng);
		  myView.setAngles(angle, normalized_vec);
		  myView.redraw();
		  //clickEvent = e;
	 }
		     
	// Part2
	 public void mouseMoved(MouseEvent e) {
	 }
	 
	 public void keyTyped(KeyEvent e) {
	 }
	 
	 // Part1
	 public void keyPressed(KeyEvent e) {
		 Tetra a = new Tetra();
		 Cylinder b = new Cylinder();
		 if (e.getKeyCode() == KeyEvent.VK_F) { // Move forward
			 myView.setMyObj(a);
			 myView.redraw();
		 }
		   else if (e.getKeyCode() == KeyEvent.VK_D) { // Move back
			 myView.setMyObj(b);
			 myView.redraw();
		 }
	 }

	    /** Handle the key-released event from the text field. */
	 public void keyReleased(KeyEvent e) {
	    //System.out.println("KEY RELEASED: ");
	 }	 
}

