package net.sauce.game;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ThreadSend extends Thread {

	ObjectOutputStream out;
	World world;
	
	public ThreadSend(World w, ObjectOutputStream o){
		world = w;
		out = o;
		start();
	}
	
	public void run(){
		while(true){
			try {
				out.writeObject(""+world.camera.x);
				out.writeObject(""+world.camera.y);
				out.writeObject(""+world.camera.z);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
