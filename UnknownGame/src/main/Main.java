package main;

import render.Window;
import utils.Time;

public class Main
{

	public static final int		WIDTH	= 800;
	public static final int		HEIGHT	= 600;
	public static final String	TITLE	= "GAME";
	public static final int		TPS		= 150;
	private boolean				isRunning;
	public long					ticks;

	public static void main(String[] args)
	{
		Window.createWindow(WIDTH, HEIGHT, TITLE);

		Main game = new Main();
		game.start();
	}

	public Main()
	{
		isRunning = false;
		ticks = 0;
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

		long lastTime = Time.getTime();
		final double nsPerUpdate = Time.SECOND / TPS; //nanoseconds between each update
		double delta = 0;

		while (isRunning)
		{
			if (Window.isCloseRequested()) stop();

			long now = Time.getTime();
			delta += (now - lastTime) / nsPerUpdate;
			lastTime = now;

			while (delta >= 1)
			{
				update();
				delta -= 1;
			}

			render();
		}

		cleanup();
	}

	private void update()
	{
		ticks++;
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
