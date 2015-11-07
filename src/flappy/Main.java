package flappy;

import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import input.Input;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main implements Runnable {
	private int width = 800;
	private int height = 600;
	
	private Thread thread;
	private boolean running = false;
	
	private long window;
	
	private GLFWErrorCallback errorCallback;
	
	public void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}
	
	private void init() {
		System.out.println("Hello LWJGL " + Sys.getVersion());
		
		// Setup an error callback
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
		
		if (glfwInit() != GL_TRUE) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(width, height, "ludimus", NULL, NULL);
		
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");
		
		// Get resolution of the primary monitor
		GLFWVidMode vidmode=glfwGetVideoMode(glfwGetPrimaryMonitor());
		// Center our window
		glfwSetWindowPos(
			window,
			(vidmode.getWidth() - width) / 2,
			(vidmode.getHeight() - height) / 2
		);
		
		glfwSetKeyCallback(window, new Input());
		
		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);
		
		// Make the window visible
		glfwShowWindow(window);
		
		// Makes OpenGL bindings available for use
		GL.createCapabilities();
		
		// Set the clear color
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		System.out.println("Running OpenGL " + glGetString(GL_VERSION));
	}
	
	public void run() {
		init();
		while (running) {
			update();
			render();
			
			if (glfwWindowShouldClose(window) == GL_TRUE)
				running = false;
		}
		
		// Release window
		glfwDestroyWindow(window);
		// Terminate GLFW and release the GLFWErrorCallback
		glfwTerminate();
		errorCallback.release();
	}
	
	private void update() {
		glfwPollEvents();
		if (Input.keys[GLFW_KEY_ESCAPE])
			glfwSetWindowShouldClose(window, GL_TRUE);
		if (Input.keys[GLFW_KEY_SPACE]) {
			System.out.println("flap");
		}
	}
	
	private void render() {
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		glfwSwapBuffers(window); // swap the color buffers
	}
	
	public static void main(String[] args) {
		new Main().start();
	}
}
