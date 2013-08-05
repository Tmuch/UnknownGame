package main;

import render.Window;

public class Main
{

	public static final int		WIDTH	= 800;
	public static final int		HEIGHT	= 600;
	public static final String	TITLE	= "GAME";

	private boolean				isRunning;

	public static void main(String[] args)
	{
		Window.createWindow(WIDTH, HEIGHT, TITLE);

		Main game = new Main();
		game.start();
	}

	public Main()
	{
		isRunning = false;
	}

	public void start()
	{
		if (isRunning) return;
		run();
	}

	public void stop()
	{
		if (!isRunning) return;
		isRunning = false;
	}

	private void run()
	{
		isRunning = true;
		while (isRunning)
		{
			if (Window.isCloseRequested()) stop();
			render();
		}

		cleanup();
	}

	private void render()
	{
		Window.render();
	}

	private void cleanup()
	{
		Window.dispose();
	}

}
