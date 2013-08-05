package world;

import java.util.ArrayList;

import entities.Entity;

public class World
{

	public static final int		WIDTH		= 80;
	public static final int		HEIGHT		= 80;


	private ArrayList<Entity>	entities	= new ArrayList<Entity>();

	/* This should have a list of all the entities in the world */
	/* Can get the rendering class by calling RenderManager.getRenderObject()
	 * and get actually render the object by calling RenderManager.renderEntity().
	 */

	public void addEntity(Entity e)
	{
		entities.add(e);
	}

	public void removeEntity(long id)
	{
		for (int i = 0; i < entities.size(); i++)
		{
			if (entities.get(i).getID() == id)
			{
				entities.remove(i);
				break;
			}
		}
	}

	public ArrayList<Entity> getEntities()
	{
		return entities;
	}

}
