import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.awt.GLCanvas;
import graphicslib3D.Matrix3D;
import graphicslib3D.Vertex3D;
import graphicslib3D.*;

public class Cylinder extends Mesh {
	//protected Vertex3D vertices[];
	//protected int numVertices;
	int size = 100;
	public Cylinder() {	
		
		vertices = new Vertex3D[6*size*6];
		//float[] vertex_positions = new float[18*size*6];
		int count = 0;
		for(int t = 0; t <= 5; t++) {
			double angle = 0.0;
			for(int i = size; i >= 1; i--) {
				angle += (Math.PI * 2 / size);
				double x = (Math.cos(angle));
			    double y =  t;
			    double z = (Math.sin(angle));
			    double x1 = (Math.cos(angle + Math.PI * 2 / size));
			    double y1 = t;
			    double z1 = (Math.sin(angle + Math.PI * 2 / size));
			    double x2 = (Math.cos(angle + Math.PI * 2 / size));
			    double y2 = t + 1;
			    double z2 = (Math.sin(angle + Math.PI * 2 / size));
			    
			    double x_0 = (Math.cos(angle));
			    double y_0 =  t;
			    double z_0 = (Math.sin(angle));
			    double x_1 = (Math.cos(angle));
			    double y_1 = t + 1;
			    double z_1 = (Math.sin(angle));
			    double x_2 = (Math.cos(angle + Math.PI * 2 / size));
			    double y_2 = t + 1;
			    double z_2 = (Math.sin(angle + Math.PI * 2 / size));   
			    

			    Vertex3D a = new Vertex3D(x, y, z);
			    a.set2DTextureCoordinates(0, 0);
				a.setNormal(0, 0, 0);
				Vertex3D b = new Vertex3D(x1, y1, z1);
				b.set2DTextureCoordinates(0, 0);
				b.setNormal(0, 0, 0);
				Vertex3D c = new Vertex3D(x2, y2, z2);
				c.set2DTextureCoordinates(0, 0);
				c.setNormal(0, 0, 0);
				Vertex3D d = new Vertex3D(x_0, y_0, z_0);
				d.set2DTextureCoordinates(0, 0);
				d.setNormal(0, 0, 0);
				Vertex3D e = new Vertex3D(x_1, y_1, z_1);
				e.set2DTextureCoordinates(0, 0);
				e.setNormal(0, 0, 0);
				Vertex3D f = new Vertex3D(x_2, y_2, z_2);
				f.set2DTextureCoordinates(0, 0);
				f.setNormal(0, 0, 0);
				vertices[0 + count] = a;
				vertices[1 + count] = b;
				vertices[2 + count] = c;
				vertices[3 + count] = d;
				vertices[4 + count] = e;
				vertices[5 + count] = f;
				count += 6;
			}
		}
		numVertices = 6*size*6;
	}
}