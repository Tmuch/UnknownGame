package render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class Camera
{
	private float	x, y, z;
	private float	rotx, roty, rotz;
	private float	fov;
	private float	aspect;
	private float	nearclip;
	private float	farclip;

	public Camera(float fov, float aspect, float near, float far)
	{
		x = y = z = 0;
		rotx = roty = rotz = 0;
		this.fov = fov;
		this.aspect = aspect;
		this.nearclip = near;
		this.farclip = far;
		initProjection();
	}

	/* Initialize the projection matrix */
	private void initProjection()
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); //clear the projection matrix
		gluPerspective(fov, aspect, nearclip, farclip);
		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_DEPTH_TEST);

		//glFrontFace(GL_CW); //clockwise
		//glCullFace(GL_BACK);
		//glEnable(GL_CULL_FACE);

	}

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getZ()
	{
		return z;
	}

	public void setZ(float z)
	{
		this.z = z;
	}

	public float getRotx()
	{
		return rotx;
	}

	public void setRotx(float rotx)
	{
		this.rotx = rotx;
	}

	public float getRoty()
	{
		return roty;
	}

	public void setRoty(float roty)
	{
		this.roty = roty;
	}

	public float getRotz()
	{
		return rotz;
	}

	public void setRotz(float rotz)
	{
		this.rotz = rotz;
	}

	public void useView()
	{
		glRotatef(rotx, 1, 0, 0);
		glRotatef(roty, 0, 1, 0);
		glRotatef(rotz, 0, 0, 1);
		glTranslatef(x, y, z);
	}

	public void move(float amt, float dir)
	{
		z += amt * Math.sin(Math.toRadians(roty + 90 * dir));
		x += amt * Math.cos(Math.toRadians(roty + 90 * dir));
	}

	public void rotateY(float r)
	{
		roty += r;
	}

}
