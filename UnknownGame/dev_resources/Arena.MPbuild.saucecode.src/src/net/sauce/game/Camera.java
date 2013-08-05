package net.sauce.game;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


public class Camera {

	float x, y, z;
	float rotationX=0, rotationY=0, rotationZ=0;
	float xPrevious, yPrevious, zPrevious;
	float vspeed = 0;
	float yGround = -0.8f;
	static float speed = 0.05f;
	World world;
	
	public Camera(World w, float a, float b, float c){
		world = w;
		x = a;
		y = b;
		z = c;
	}
	
	public void update(){
		collisions();
		xPrevious = x;
		yPrevious = y;
		zPrevious = z;
		if(Mouse.isGrabbed()){
			float mouseDX = Mouse.getDX() * 0.8f * 0.16f;
			float mouseDY = Mouse.getDY() * 0.8f * 0.16f;
			if (rotationY + mouseDX >= 360) {
				rotationY = rotationY + mouseDX - 360;
			} else if (rotationY + mouseDX < 0) {
				rotationY = 360 - rotationY + mouseDX;
			} else {
				rotationY += mouseDX;
			}
			if (rotationX - mouseDY >= -89 && rotationX - mouseDY <= 89) {
				rotationX += -mouseDY;
			} else if (rotationX - mouseDY < -89) {
				rotationX = -89;
			} else if (rotationX - mouseDY > 89) {
				rotationX = 89;
			}
		}
		y -= vspeed;
		if(y > yGround){
			y = yGround;
		}
		if(y < yGround)
			vspeed -= World.gravity;
	}
	
	public void collisions(){
		for(Box box: world.boxes){
			if(Math.abs(box.centerX - x) < box.size+0.2f
			&& Math.abs(box.centerZ - z) < box.size+0.2f){
				x = xPrevious;
				z = zPrevious;
				System.out.println("Collision");
			}
		}
	}
	
	public void input(){
		if(world.keys[Keyboard.KEY_W]){
			x += -(Camera.speed * Math.sin(Math.toRadians(rotationY)));
			z -= -(Camera.speed * Math.cos(Math.toRadians(rotationY)));
		}
		if(world.keys[Keyboard.KEY_S]){
			x += Camera.speed * Math.sin(Math.toRadians(rotationY));
			z -= Camera.speed * Math.cos(Math.toRadians(rotationY));
		}
		if(world.keys[Keyboard.KEY_A]){
			x += -(Camera.speed * Math.sin(Math.toRadians(rotationY-90)));
			z -= -(Camera.speed * Math.cos(Math.toRadians(rotationY-90)));
		}
		if(world.keys[Keyboard.KEY_D]){
			x += -(Camera.speed * Math.sin(Math.toRadians(rotationY+90)));
			z -= -(Camera.speed * Math.cos(Math.toRadians(rotationY+90)));
		}
		if(world.keys[Keyboard.KEY_SPACE]){
			if(y == yGround)
				vspeed = 0.115f;
		}
		if(world.keys[Keyboard.KEY_LSHIFT]){
			Camera.speed = 0.1f;
		}else{
			Camera.speed = 0.05f;
		}
	}
}
