package net.sauce.game;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class Game {
	
	boolean ended = false;
	World world = new World();
	static int width = 800, height = 600;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	static ServerSocket server;	//Server side's server
	static Socket client;		//Server side client's socket
	static Socket socket;		//Client side socket
	static String serverIP = "CHANGE ME"; 	//Server socket will bind to this address
	static String connectIP = "CHANGE ME"; //Client will connect to this address
	static int port = 25566;				//Client and server will connect/host on this port
	
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		System.out.println("Are you host? Y/N");
		boolean isHost = s.nextLine().toLowerCase().startsWith("y");
		if(isHost){
			System.out.println("Will host on "+serverIP+":"+port);
		}else{
			System.out.println("Will join to "+connectIP+":"+port);
		}
		new Game(isHost);
	}
	
	/*
	 * Inside the game constructor, a few things happen.
	 * First, a connection is established between the client and server. There can only be
	 * one client per server.
	 * Second, a ping/latency test is performed. The client will send a randomly generated
	 * (see fig. A) byte array of 256 bytes to the server (see fig. B). The server will receive
	 * this and time how long it takes to perform this test. It will then print out the time, and
	 * send the time to the client, so they can print it out too!
	 * After all this, two threads are created. A thread for sending data, and a thread for
	 * receiving and parsing data. So far, the only data being received is coordinate data,
	 * for a box in the world.
	 * The rest of the code should be self explanatory. Maybe.
	 */
	
	public Game(boolean host){
		try{
			if(host){
				System.out.println("Hosting...");
				server = new ServerSocket(port, 4, InetAddress.getByName(serverIP));
				System.out.println("Ready!\nAwaiting client...");
				client = server.accept();
				System.out.println("Client connected!\nBuffering...");
				out = new ObjectOutputStream(client.getOutputStream());
				in = new ObjectInputStream(client.getInputStream());
				System.out.println("Buffered!\nPinging for 256 bytes...");
				long start = System.currentTimeMillis();
				byte[] ping = new byte[256];
				in.read(ping);
				System.out.println("Latency: "+(System.currentTimeMillis()-start));
				out.writeLong(start);
				out.flush();
				System.out.println("Starting threads...");
				new ThreadSend(world, out);
				new ThreadReceive(world, in);
				System.out.println("Started!\nCreating game world...");
			}else{
				System.out.println("Connecting...");
				socket = new Socket(connectIP, port);
				System.out.println("Connected!\nBuffering...");
				in = new ObjectInputStream(socket.getInputStream());
				out = new ObjectOutputStream(socket.getOutputStream());
				byte[] ping = new byte[256];
				new Random().nextBytes(ping);
				System.out.println("Buffered\nPinging for 256 bytes...");
				out.write(ping);
				out.flush();
				long latency = in.readLong();
				System.out.println("Latency: "+(System.currentTimeMillis()-latency));
				System.out.println("Starting threads...");
				new ThreadReceive(world, in);
				new ThreadSend(world, out);
				System.out.println("Started!\nCreating game world...");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			Display.setDisplayMode(new DisplayMode(width,height));
			Display.create();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		world.init();
		
		while(!Display.isCloseRequested()){
			if(ended)
				break;
			world.update();
		}
		
		Display.destroy();
	}
}
