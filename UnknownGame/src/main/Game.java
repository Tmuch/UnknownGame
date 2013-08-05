package main;

import org.lwjgl.input.Keyboard;

import input.InputManager;

public class Game
{

	public Game()
	{

	}

	public void render()
	{

	}

	public void update()
	{

	}

	public void input()
	{
		if (InputManager.getKeyDown(Keyboard.KEY_UP)) System.out.println("pressed up");
		if (InputManager.getKeyUp(Keyboard.KEY_UP)) System.out.println("released up");

	}
}
