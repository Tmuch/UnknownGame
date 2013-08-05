package render;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.util.HashMap;
import java.util.Map;

import main.Game;

import world.World;

public class RenderManager
{

	private Map						entityRenderMap	= new HashMap();

	private static RenderManager	instance		= null;
	public Camera					camInstance;
	private World					theWorld		= null;

	/**
	 * Render the world and all of the blocks within it.
	 */
	private void renderWorld()
	{
		if (theWorld == null)
		{
			System.out.println("There is no world to render!");
			return;
		}

		glPushMatrix();
		{
			glColor3f(1f, 1f, 1f);
			glBegin(GL_QUADS);
			{
				float w = (float) World.WIDTH / (float) 2;
				glVertex3f(-w, -5f, w);
				glVertex3f(-w, -5f, -w);
				glVertex3f(w, -5f, -w);
				glVertex3f(w, -5f, w);
			}
			glEnd();
		}
		glPopMatrix();


		/*
				glPushMatrix();
				{
					//glColor3f(1.0f, 0.5f, 0f);
					glTranslatef(0, 0, -10);
					//glRotatef(x, 1, 1.5f, 0.75f);
					glBegin(GL_QUADS);
					{
						glColor3f(1f, 0f, 0f);
						//Left face
						glVertex3f(-1, 1, 1);
						glVertex3f(-1, -1, 1);
						glVertex3f(-1, -1, -1);
						glVertex3f(-1, 1, -1);

						glColor3f(0f, 1f, 0f);
						//Front face
						glVertex3f(-1, 1, 1);
						glVertex3f(-1, -1, 1);
						glVertex3f(1, -1, 1);
						glVertex3f(1, 1, 1);

						glColor3f(1f, 0f, 0f);
						//right face
						glVertex3f(1, 1, 1);
						glVertex3f(1, 1, -1);
						glVertex3f(1, -1, -1);
						glVertex3f(1, -1, 1);


						glColor3f(0f, 1f, 0f);
						//back face
						glVertex3f(-1, -1, -1);
						glVertex3f(-1, 1, -1);
						glVertex3f(1, 1, -1);
						glVertex3f(1, -1, -1);

						glColor3f(0f, 0f, 1f);
						//top face
						glVertex3f(-1, 1, 1);
						glVertex3f(-1, 1, -1);
						glVertex3f(1, 1, -1);
						glVertex3f(1, 1, 1);

						glColor3f(0f, 0f, 1f);
						//bottom face
						glVertex3f(-1, -1, 1);
						glVertex3f(-1, -1, -1);
						glVertex3f(1, -1, -1);
						glVertex3f(1, -1, 1);
					}
					glEnd();

				}
				glPopMatrix();*/


	}

	private void renderEntities()
	{
		return;
	}

	private RenderManager()
	{
		this.camInstance = new Camera(70, (float) Window.getWidth() / (float) Window.getHeight(), 0.3f, 1000);
		theWorld = Game.getGameInstance().getWorld();
		//add entities to hashmap
		//and set their RenderManager field to 'this'
	}

	public void render()
	{
		/* Clear the screen before each frame */
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();
		camInstance.useView(); //update camera position and orientation


		/* REMEMBER that each render class for each entity must have glbegin and glend surrounding it's drawings, 
		 * as well as push and pop operations */

		/* Render world */
		renderWorld();
		/* Render entities */
		renderEntities();


		/*
				glPushMatrix();
				{
					//glColor3f(1.0f, 0.5f, 0f);
					glTranslatef(0, 0, -10);
					//glRotatef(x, 1, 1.5f, 0.75f);
					glBegin(GL_QUADS);
					{
						glColor3f(1f, 0f, 0f);
						//Left face
						glVertex3f(-1, 1, 1);
						glVertex3f(-1, -1, 1);
						glVertex3f(-1, -1, -1);
						glVertex3f(-1, 1, -1);

						glColor3f(0f, 1f, 0f);
						//Front face
						glVertex3f(-1, 1, 1);
						glVertex3f(-1, -1, 1);
						glVertex3f(1, -1, 1);
						glVertex3f(1, 1, 1);

						glColor3f(1f, 0f, 0f);
						//right face
						glVertex3f(1, 1, 1);
						glVertex3f(1, 1, -1);
						glVertex3f(1, -1, -1);
						glVertex3f(1, -1, 1);


						glColor3f(0f, 1f, 0f);
						//back face
						glVertex3f(-1, -1, -1);
						glVertex3f(-1, 1, -1);
						glVertex3f(1, 1, -1);
						glVertex3f(1, -1, -1);

						glColor3f(0f, 0f, 1f);
						//top face
						glVertex3f(-1, 1, 1);
						glVertex3f(-1, 1, -1);
						glVertex3f(1, 1, -1);
						glVertex3f(1, 1, 1);

						glColor3f(0f, 0f, 1f);
						//bottom face
						glVertex3f(-1, -1, 1);
						glVertex3f(-1, -1, -1);
						glVertex3f(1, -1, -1);
						glVertex3f(1, -1, 1);
					}
					glEnd();
				}
				glPopMatrix();*/
	}

	/**
	 * This method guarantees that only one instance of RenderManager can exist
	 * at once
	 * 
	 * @return live instance of RenderManager
	 */
	public static RenderManager getInstance()
	{
		if (instance == null) instance = new RenderManager();
		return instance;
	}
}
