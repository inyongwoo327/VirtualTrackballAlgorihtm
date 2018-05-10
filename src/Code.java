// Project 1 initial source code
// CMSC427 fall 2017

// ModelView class
// From Gordon program 6_1

import java.awt.event.*;

import graphicslib3D.*;
import java.io.*;
import java.nio.*;
import javax.swing.*;

import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.texture.*;
import com.jogamp.common.nio.Buffers;

public class Code extends JFrame implements GLEventListener
{	GLCanvas myCanvas;
	private int rendering_program;
	private int vao[] = new int[1];
	private int vbo[] = new int[3];
	private float cameraX, cameraY, cameraZ;
	private float objLocX, objLocY, objLocZ;
	private GLSLUtils util = new GLSLUtils();
	
	private int shuttleTexture;
	private Texture joglShuttleTexture;
	
	private int numObjVertices;
	private Mesh myObj;
	
	int mx, my;
    float xAngle = 0.0f;
    float yAngle = 0.0f;

    Matrix3D rotationMatrix = new Matrix3D ();
    
    Vector3D position = new Vector3D(1.0,1.0,1.0);
    //Vector4D color = new Vector3D(1.0,1.0,1.0);
    
	public Code(Controller myController)
	{	setTitle("Chapter6 - program3");
		setSize(800, 800);
		// ADDED FOR MACS
		GLProfile glp = GLProfile.getMaxProgrammableCore(true);
		GLCapabilities caps = new GLCapabilities(glp);
		myCanvas = new GLCanvas(caps);
		//myCanvas = new GLCanvas();
		myCanvas.addGLEventListener(this);
		myCanvas.addMouseListener(myController);
		myCanvas.addMouseMotionListener(myController);
		myCanvas.addKeyListener(myController);
		myCanvas.setFocusable(true);
		myCanvas.requestFocus();
		getContentPane().add(myCanvas);
		this.setVisible(true);
	}

	public void display(GLAutoDrawable drawable)
	{	GL4 gl = (GL4) GLContext.getCurrentGL();
		gl.glClear(GL_DEPTH_BUFFER_BIT);
		gl.glClear(GL_COLOR_BUFFER_BIT);
		
		gl.glUseProgram(rendering_program);

		int mv_loc = gl.glGetUniformLocation(rendering_program, "mv_matrix");
		int proj_loc = gl.glGetUniformLocation(rendering_program, "proj_matrix");

		float aspect = (float) myCanvas.getWidth() / (float) myCanvas.getHeight();
		Matrix3D pMat = perspective(50.0f, aspect, 0.1f, 1000.0f);

		Matrix3D vMat = new Matrix3D();
		vMat.translate(-cameraX, -cameraY, -cameraZ);

		Matrix3D mMat = new Matrix3D();
		mMat.translate(objLocX, objLocY, objLocZ);
		//mMat.rotateY(135.0f);
		// For rotation
		//mMat.rotateY(yAngle);
		//mMat.rotateX(xAngle);
		//setAngles(Controller.angle, Controller.normalized_vec);
		
		//current_orientation = mMat;
		mMat.concatenate(rotationMatrix);
		
		
		mMat.scale(1.5f,1.5f,1.5f);
		//System.out.println("event "+yAngle+" "+xAngle);

		Matrix3D mvMat = new Matrix3D();
		mvMat.concatenate(vMat);
		mvMat.concatenate(mMat);

		gl.glUniformMatrix4fv(mv_loc, 1, false, mvMat.getFloatValues(), 0);
		gl.glUniformMatrix4fv(proj_loc, 1, false, pMat.getFloatValues(), 0);
 
		// Object renders itself
		// Needs gl for OpenGL context
		// Could own its shader program and texture
		getMyObj().render(gl, vbo, rendering_program, shuttleTexture);

	}

	public void init(GLAutoDrawable drawable)
	{	GL4 gl = (GL4) GLContext.getCurrentGL();
		//setMyObj(new Tetra());
		myObj = new ImportedModel("shuttle.obj");
		rendering_program = createShaderProgram();
		//setupVertices();
		cameraX = 0.9f; cameraY = 0.9f; cameraZ = 5.0f;
		objLocX = 0.0f; objLocY = 0.0f; objLocZ = 0.0f;

		joglShuttleTexture = loadTexture("spstob_1.jpg");
		shuttleTexture = joglShuttleTexture.getTextureObject();
	}



	private Matrix3D perspective(float fovy, float aspect, float n, float f)
	{	float q = 1.0f / ((float) Math.tan(Math.toRadians(0.5f * fovy)));
		float A = q / aspect;
		float B = (n + f) / (n - f);
		float C = (2.0f * n * f) / (n - f);
		Matrix3D r = new Matrix3D();
		r.setElementAt(0,0,A);
		r.setElementAt(1,1,q);
		r.setElementAt(2,2,B);
		r.setElementAt(3,2,-1.0f);
		r.setElementAt(2,3,C);
		r.setElementAt(3,3,0.0f);
		return r;
	}
	
	
	// Added for MVC
	// Called by controller when model changes
	void redraw() {
		myCanvas.repaint();
	}

	// Added for MVC
	// Called by controller on MouseDragged
	void setAngles(double angle, Vector3D normalized_vec) {
		if(normalized_vec != null) {
			rotationMatrix.rotate(angle, normalized_vec);
			System.out.println(angle);
			System.out.println(normalized_vec);
		}
	}
	

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
	public void dispose(GLAutoDrawable drawable) {}

	private int createShaderProgram()
	{	GL4 gl = (GL4) GLContext.getCurrentGL();

		String vshaderSource[] = util.readShaderSource("code/vert2.shader");
		String fshaderSource[] = util.readShaderSource("code/frag2.shader");

		int vShader = gl.glCreateShader(GL_VERTEX_SHADER);
		int fShader = gl.glCreateShader(GL_FRAGMENT_SHADER);

		gl.glShaderSource(vShader, vshaderSource.length, vshaderSource, null, 0);
		gl.glShaderSource(fShader, fshaderSource.length, fshaderSource, null, 0);

		gl.glCompileShader(vShader);
		gl.glCompileShader(fShader);

		int vfprogram = gl.glCreateProgram();
		gl.glAttachShader(vfprogram, vShader);
		gl.glAttachShader(vfprogram, fShader);
		gl.glLinkProgram(vfprogram);
		return vfprogram;
	}
	
	public Texture loadTexture(String textureFileName)
	{	Texture tex = null;
		try { tex = TextureIO.newTexture(new File(textureFileName), false); }
		catch (Exception e) { e.printStackTrace(); }
		return tex;
	}

	public Mesh getMyObj() {
		return myObj;
	}

	public void setMyObj(Mesh myObj) {
		this.myObj = myObj;
	}
}