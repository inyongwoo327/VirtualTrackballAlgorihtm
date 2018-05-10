// Project 1 initial source code
// CMSC427 fall 2017

// Class Mesh now handles both setting up vertices
// and rendering a mesh
// As superclass it handles functions common to all
// mesh objects
// Expects meshes to be a single array of Vertice3D 
// objects, not indexed mesh data structure

// But the class is not efficient
// It sets up vertices and VBOs for each display call
// Should set up vertices and VBOs only once at creation

import graphicslib3D.Matrix3D;
import graphicslib3D.Vertex3D;

import com.jogamp.opengl.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.io.*;
import java.nio.*;
import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.*;
import com.jogamp.common.nio.Buffers;


public class Mesh
{
    // Model vertices
	protected Vertex3D [] vertices;
	protected int numVertices;
	
	
	
	public Vertex3D[] getVertices() { return vertices; }	
	public int getNumVertices() { return numVertices; }
	
	public void render( GL4 gl, int vbo[], int rendering_program, int texture ) {
	
	    setupVertices( gl, vbo );
	
	    gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
		gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[2]);
		gl.glBindTexture(GL_TEXTURE_2D, texture);
		gl.glUniform1i(gl.glGetUniformLocation(rendering_program, "samp"), 0);

		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, texture);
		// added for Macs
		gl.glUniform1i(gl.glGetUniformLocation(rendering_program, "samp"), 0);

		gl.glEnable(GL_CULL_FACE);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glFrontFace(GL_CCW);
		int numVerts = getVertices().length;
		gl.glDrawArrays(GL_TRIANGLES, 0, numVerts);
		
		}
		
		
	private void setupVertices(GL4 gl, int vbo[])
	{
		Vertex3D[] vertices = getVertices();
		int numObjVertices =  getNumVertices();
		
		float[] pvalues = new float[numObjVertices*3];
		float[] tvalues = new float[numObjVertices*2];
		float[] nvalues = new float[numObjVertices*3];
		
		for (int i=0; i<numObjVertices; i++)
		{	pvalues[i*3]   = (float) (vertices[i]).getX();
			pvalues[i*3+1] = (float) (vertices[i]).getY();
			pvalues[i*3+2] = (float) (vertices[i]).getZ();
			tvalues[i*2]   = (float) (vertices[i]).getS();
			tvalues[i*2+1] = (float) (vertices[i]).getT();
			nvalues[i*3]   = (float) (vertices[i]).getNormalX();
			nvalues[i*3+1] = (float) (vertices[i]).getNormalY();
			nvalues[i*3+2] = (float) (vertices[i]).getNormalZ();
		}
		
		gl.glGenBuffers(vbo.length, vbo, 0);
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
		FloatBuffer vertBuf = Buffers.newDirectFloatBuffer(pvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, vertBuf.limit()*4, vertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
		FloatBuffer texBuf = Buffers.newDirectFloatBuffer(tvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, texBuf.limit()*4, texBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[2]);
		FloatBuffer norBuf = Buffers.newDirectFloatBuffer(nvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, norBuf.limit()*4,norBuf, GL_STATIC_DRAW);
	}

}
