package edu.brunel.accessibalt.graphics;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class OGLRenderer
{
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;	
	private List<Box> shapes;

	
	public OGLRenderer()
	{	
		shapes = new ArrayList<Box>(16);
		shapes.add(new Box(15, 15));
		shapes.add(new Box(150, 150));		
		
		try
		{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("Hello, LWJGL!");
			Display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		
		// Initialise OGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,WIDTH,HEIGHT,0,1,-1);
		glMatrixMode(GL_MODELVIEW);
		
		
		
		while (!Display.isCloseRequested())
		{	
			// Render
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			glBegin(GL_TRIANGLES);
				glColor3f(0.1f, 0.2f, 0.3f);
				glVertex2i(50, 70);
				glVertex2i(45, 100);
				glVertex2i(55, 100);
			glEnd();	
			
			while (Keyboard.next())
			{
				if (Keyboard.getEventKey() == Keyboard.KEY_C
					&& Keyboard.getEventKeyState() == true)
				{
					shapes.add(new Box(50, 50));
				}
			}
			

			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			{
				Display.destroy();
				System.exit(0);
			}
			
			int mouseY = HEIGHT - Mouse.getY();
			int mouseX = Mouse.getX();
			
			System.out.println("Mouse: (" + mouseX + ", " + mouseY + ")");
			
			int mouseDX = Mouse.getDX();
			int mouseDY = Mouse.getDY();
			
			for (Box box : shapes)
			{
				if (Mouse.isButtonDown(0)
					&& box.inBounds(mouseX, mouseY))
				{
					box.isSelected = true;
					System.out.println("You selected me!");
					
				}
				else if (Mouse.isButtonDown(1)
					&& box.inBounds(mouseX, mouseY))
				{
					box.randomiseColors();
					
				}
				else
				{
					box.isSelected = false;
				}
				if (box.isSelected)
				{
					box.update(mouseDX, mouseDY);
				}
				
				box.draw();
			}
			
			
			
			
			Display.update();
			Display.sync(60);
			
		}
		Display.destroy();
		
	}
	
	static class Box
	{
		public int x, y;
		public boolean isSelected;
		private float colorRed, colorBlue, colorGreen;
		
		Box(int x, int y)
		{
			this.x = x;
			this.y = y;
			this.isSelected = false;
			
			randomiseColors();
		}
		
		void randomiseColors()
		{
			Random random = new Random();
			colorRed = random.nextFloat();
			colorBlue = random.nextFloat();
			colorGreen = random.nextFloat();
		}
		
		boolean inBounds(int mouseX, int mouseY)
		{
			if ( (mouseX > x) && (mouseX < (x + 50)) 
				&& (mouseY > y) && (mouseY < (y + 50)) )
			{
				System.out.println("In bounds!");
				return true;
			}
			System.out.println("Outside bounds!");
			return false;
		}
		
		void update(int dx, int dy)
		{
			x += dx;
			y -= dy;
		}
		
		void draw()
		{
			glColor3f(colorRed, colorGreen, colorBlue);
			
			glBegin(GL_QUADS);
				glVertex2i(x, y);
				glVertex2i(x + 50, y);
				glVertex2i(x + 50, y + 50);
				glVertex2i(x, y + 50);
			glEnd();
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		new OGLRenderer();
	}

}
