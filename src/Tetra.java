
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.awt.GLCanvas;
import graphicslib3D.Matrix3D;
import graphicslib3D.Vertex3D;
import graphicslib3D.*;

public class Tetra extends Mesh {
	//protected Vertex3D vertices[];
	//protected int numVertices;
	public Tetra() {	
		Vertex3D a = new Vertex3D(1.0, 0.5, 0.0);
		a.set2DTextureCoordinates(0, 0);
		a.setNormal(0, 0, 0);
		Vertex3D b = new Vertex3D(0.0, -2.0, 0.5);
		b.set2DTextureCoordinates(0, 0);
		b.setNormal(0, 0, 0);
		Vertex3D c = new Vertex3D(-1.0, 0.5, 0.0);
		c.set2DTextureCoordinates(0, 0);
		c.setNormal(0, 0, 0);
		Vertex3D d = new Vertex3D(0.0, -2.0, 0.5);
		d.set2DTextureCoordinates(0, 0);
		d.setNormal(0, 0, 0);
		Vertex3D e = new Vertex3D(-1.0, 0.5, 0.0);
		e.set2DTextureCoordinates(0, 0);
		e.setNormal(0, 0, 0);
		Vertex3D f = new Vertex3D(0.0, 0.0, 3.0);
		f.set2DTextureCoordinates(0, 0);
		f.setNormal(0, 0, 0);
		Vertex3D g = new Vertex3D(1.0, 0.5, 0.0);
		g.set2DTextureCoordinates(0, 0);
		g.setNormal(0, 0, 0);
		Vertex3D h = new Vertex3D(-1.0, 0.5, 0.0);
		h.set2DTextureCoordinates(0, 0);
		h.setNormal(0, 0, 0);
		Vertex3D i = new Vertex3D(0.0, 0.0, 3.0);
		i.set2DTextureCoordinates(0, 0);
		i.setNormal(0, 0, 0);
		Vertex3D j = new Vertex3D(1.0, 0.5, 0.0);
		j.set2DTextureCoordinates(0, 0);
		j.setNormal(0, 0, 0);
		Vertex3D k = new Vertex3D(0.0, -2.0, 0.5);
		k.set2DTextureCoordinates(0, 0);
		k.setNormal(0, 0, 0);
		Vertex3D l = new Vertex3D(0.0, 0.0, 3.0);
		l.set2DTextureCoordinates(0, 0);
		l.setNormal(0, 0, 0);
		//GL4 gl = (GL4) GLContext.getCurrentGL();
		super.vertices = new Vertex3D[]
		{a, b, c, d, e, f, g, h, i, j, k, l};
			//1.0, 0.5, 0.0, 0.0, -2.0f, 0.5f, -1.0f, 0.5f, 0.0f,
			//0.0f, -2.0f, 0.5f, -1.0f, 0.5f, 0.0f, 0.0f, 0.0f, 3.0f,
			//1.0f, 0.5f, 0.0f, -1.0f, 0.5f, 0.0f, 0.0f, 0.0f, 3.0f,
			//1.0f, 0.5f, 0.0f, 0.0f, -2.0f, 0.5f, 0.0f, 0.0f, 3.0f
		super.numVertices = 12;
	}
}
