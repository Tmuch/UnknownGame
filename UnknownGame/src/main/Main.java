package main;

import render.Window;

public class Main
{

	public static final int		WIDTH	= 800;
	public static final int		HEIGHT	= 600;
	public static final String	TITLE	= "GAME";

	public static void main(String[] args)
	{
		Window.createWindow(WIDTH, HEIGHT, TITLE);
		Game.getGameInstance().start();
	}
}
