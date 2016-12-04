package moves;

import robots.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Playground extends Thread
{
	public static void main(String[] args)
	{
		(new Playground()).start();
	}

	public void play() throws IOException, InterruptedException
	{
		String escena = "escena4x400";
		
		Matrix.getInstance().init(escena);
		
		
		VRobbie r1 = null;
		VRobbie r2 = null;
		
		ArrayList<String> one = new ArrayList<String>();
		ArrayList<String> two = new ArrayList<String>();
		ArrayList<String> steps = new ArrayList<String>();
		
		r1 = new VRobbie(1);
		r2 = new VRobbie(2);
		
		
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		
		
		steps = Matrix.getInstance().steps;
		one = r1.moves;
		two = r2.moves;
		
		for (int i = 0; i != 50; i++)
		{
			Matrix.getInstance().destroy();
			Matrix.getInstance().init(escena);

			r1 = new VRobbie(1);
			r2 = new VRobbie(2);
			
			
			t1 = new Thread(r1);
			t2 = new Thread(r2);
			
			t1.start();
			t2.start();
			t1.join();
			t2.join();
			
			if ((r1.moves.size()+r2.moves.size())< (one.size()+two.size()))
			{
				one = r1.moves;
				two = r2.moves;
			}
			if(Matrix.getInstance().steps.size()<steps.size())
			{
				steps = Matrix.getInstance().steps;
			}
			
		}
		r1.moves = one;
		r2.moves = two;
		
		r1.writeMoves();
		r2.writeMoves();
		
		
		Matrix.getInstance().writeMoves();
		
		System.out.println("r1: "+ one.size()+" moves");
		System.out.println("r2: "+ two.size()+" moves");
		
		
//		Robie robot1 = new Robie("157.253.173.241");
//		Robie robot2 = new Robie("157.253.173.230");
		
		Robie robot1 = new Robie(8101);
		Robie robot2 = new Robie(8102);
		
		robot1.init();
		robot2.init();
				
		String r1_last = "";
		String r2_last = "";
				    
		String r1_current = "right";
	  
		String r2_current = "right";
		
		double or2i = Matrix.getInstance().orient2i;
		double or2f = Matrix.getInstance().orient2f;
		double or1i = Matrix.getInstance().orient1i;
		double or1f = Matrix.getInstance().orient1f;
		
		//posicionar orientación inicial
		if( Matrix.getInstance().orient2i != 0)
		{
			System.out.println("Ajustando orientación inicial R2");
			if(or2i == 90)
				r2_current = "up";
			else if(or2i == 180)
				r2_current = "left";
			else if(or2i == 270)
				r2_current = "down";
			else
				robot2.rotate(0);
		}
		if( Matrix.getInstance().orient1i != 0)
		{
			System.out.println("Ajustando orientación inicial R1");
			if(or1i == 90)
				r1_current = "up";
			else if(or1i == 180)
				r1_current = "left";
			else if(or1i == 270)
				r1_current = "down";
			else
				robot1.rotate(0);
		}
				  

		for (String paso: Matrix.getInstance().steps)
		{
			System.out.println(paso);
			if (paso.contains("R1"))
			{
				r1_last = r1_current;
				r1_current = paso.substring(4).trim();
				System.out.println(r1_current);
				
				if (r1_current != r1_last)
				{
					if (r1_current.contains("right"))
					{
						robot1.rotate(0 - or1i);
					}
					else if (r1_current.contains("left"))
					{
						robot1.rotate(180 - or1i);
					}
					else if (r1_current.contains("up"))
					{
						robot1.rotate(90 - or1i);
					}
					else if (r1_current.contains("down"))
					{
						robot1.rotate(270 - or1i);
					}
				}
				
				
				robot1.move();
				
			}
			else if (paso.contains("R2"))
			{
				r2_last = r2_current;
				r2_current = paso.substring(4).trim();
				System.out.println(r2_current);
				
				if (r2_current != r2_last)
				{
					if (r2_current.contains("right"))
					{
						robot2.rotate( 0- or2i);

					}
					else if (r2_current.contains("left"))
					{
						robot2.rotate(180 - or2i);
					}
					else if (r2_current.contains("up"))
					{
						robot2.rotate(90 - or2i);
					}
					else if (r2_current.contains("down"))
					{
						robot2.rotate( 270 - or2i);
					}
				}

				robot2.move();
				
			}
		}
		
		
		//posicionar orientación final
		System.out.println("Ajustando orientación final");
		robot1.rotate(or2i);
		robot2.rotate(or2f);
		
		robot1.out();
		robot2.out();
			
	}

	public void run() 
	{
		try
		{
			play();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
 catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
