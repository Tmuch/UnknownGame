package net.sauce.game;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ThreadReceive extends Thread {

	ObjectInputStream in;
	World world;
	
	public ThreadReceive(World w, ObjectInputStream i){
		world = w;
		in = i;
		start();
	}
	
	public void run(){
		while(true){
			try {
				World.other.centerX = Float.parseFloat((String)in.readObject());
				World.other.centerY = -Float.parseFloat((String)in.readObject());
				World.other.centerZ = Float.parseFloat((String)in.readObject());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
