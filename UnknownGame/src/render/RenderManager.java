package render;

import static org.lwjgl.opengl.GL11.*;

import java.util.HashMap;
import java.util.Map;

import entities.Entity;

import main.Game;

import world.World;

public class RenderManager
{

	/*
	 * This maps every renderable entity to its corresponding render class 
	 */
	private Map						entityRenderMap	= new HashMap();

	/* Singleton locked instance of this class. 
	 * There should only be one instance of this class
	 * in this game environment. */
	private static RenderManager	instance		= null;

	/* Instance of the Camera class. 
	 * TODO: This should probably be moved to be a field in the playerentity class
	 */
	public Camera					camInstance;

	/*
	 * This will remain null until getInstance() is called 
	 */
	private World					theWorld		= null;



	/**
	 * Render the world and all of the blocks within it.
	 */
	private void renderWorld()
	{
		/* Make sure that there is actually a world to render. */
		if (theWorld == null)
		{
			System.out.println("There is no world to render!");
			return;
		}

		/* Render the world */
		for (Entity e : theWorld.getEntities())
		{

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


	/* Render all of the Entities. This should probably just offload
	 * the work to a class RenderEntities */
	private void renderEntities()
	{
		return;
	}


	/**
	 * Private constructor. Only called by getInstance()
	 */
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


	public void renderEntity()
	{

	}
}
