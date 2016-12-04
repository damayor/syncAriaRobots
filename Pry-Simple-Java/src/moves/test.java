package moves;

import robots.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class test extends Thread
{
	public static void main(String[] args)
	{
		(new test()).start();
	}

	public void play() throws IOException, InterruptedException
	{
	
		
		Robie robot1 = new Robie(8101);
		robot1.init();
		
		//pta vida, la orientación depende de la orientación original
		robot1.rotate(90);
		robot1.rotate(-90);
		
		

			
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

