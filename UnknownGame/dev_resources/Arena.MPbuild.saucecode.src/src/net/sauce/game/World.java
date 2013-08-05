package net.sauce.game;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class World {

	Camera camera = new Camera(this, 0f, -1f, -8f);
	Flat floor = new Flat(8, 8);
	List<Box> boxes = new ArrayList<Box>();
	boolean[] keys = new boolean[256];
	TextureManager textures = new TextureManager();
	static float gravity = 0.005988f;
	static Box other = new Box(0f, 0f, -10f, 0.5f, 1f, 0f, 0f);

	public void init() {
		textures.init();
		boxes.add(new Box(0, 0, 0, 1f, 0, 0, 1));
		boxes.add(new Box(2f, 0, 0, 1f, 0, 0, 1));
		boxes.add(new Box(2f, 2f, 0, 1f, 0, 1, 1));

		Mouse.setGrabbed(true);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		gluPerspective((float) 100, Game.width / Game.height, 0.001f, 1000);
		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_TEXTURE_2D);
		glShadeModel(GL_SMOOTH);
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		glClearDepth(1.0f);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
	}

	public void update() {
		remapKeys();

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();
		glRotatef(camera.rotationX, 1, 0, 0);
		glRotatef(camera.rotationY, 0, 1, 0);
		glRotatef(camera.rotationZ, 0, 0, 1);
		glTranslatef(camera.x, camera.y, camera.z);

		camera.update();
		camera.input();

		// RENDER
		render();

		otherInput();

		Display.update();
		Display.sync(90);
	}

	public void render() {
		floor.render();
		for (Box box : boxes) {
			box.render();
		}
		other.render();
	}

	public void otherInput() {
		if (keys[Keyboard.KEY_F1]) {
			Mouse.setGrabbed(false);
		}
		if (keys[Keyboard.KEY_F2]) {
			Mouse.setGrabbed(true);
		}
		if (keys[Keyboard.KEY_ESCAPE]) {
			System.exit(0);
		}
	}

	public void remapKeys() {
		for (int i = 0; i < keys.length; i++) {
			keys[i] = Keyboard.isKeyDown(i);
		}
	}
}
