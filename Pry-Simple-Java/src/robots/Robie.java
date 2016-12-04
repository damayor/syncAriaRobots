package robots;
import com.mobilerobots.Aria.*;

public class Robie extends Thread
{
	static 
	  {
	    try 
	    {
	        System.loadLibrary("AriaJava");
	    } catch (UnsatisfiedLinkError e) 
	    {
	    	e.printStackTrace();
	      System.err.println("Native code library libAriaJava failed to load. Make sure that its directory is in your library path; See javaExamples/README.txt and the chapter on Dynamic Linking Problems in the SWIG Java documentation (http://www.swig.org) for help.\n" + e);
	      System.exit(1);
	    }
	  }
	private String last;
	private String current;
	private String tcp;
	private String ip;
	
	 ArRobot robot;
	
	public Robie(int TCP_PORT)
	{
		tcp = "-rrtp "+TCP_PORT;
	}
	
	public Robie(int TCP_PORT, String ipX)
	{
		tcp = "-rrtp "+TCP_PORT;
		ip = "-rh "+ipX;
	}
	
	public Robie(String ipX)
	{
		ip = "-rh "+ipX;
	}

	@Override
	public void run()
	{
		System.out.println("Starting Java Test");

	    Aria.init();

	    robot = new ArRobot();
	    
	    ArArgumentBuilder builder = new ArArgumentBuilder();
	    
	    ArArgumentParser parser = new ArArgumentParser(builder);
	    
	    if (ip != null)
	    {
	    	parser.addDefaultArgument(ip);
	    }
	    if (tcp != null)
	    {
	    	parser.addDefaultArgument(tcp);
	    }
	    
	    
	    ArRobotConnector rc = new ArRobotConnector(parser, robot);
	 
	    if(!Aria.parseArgs())
	    {
	      Aria.logOptions();
	      Aria.exit(1);
	    }

	    
	    if (!rc.connectRobot(robot))
	    {
	      System.err.println("Could not connect to robot, exiting.\n");
	      System.exit(1);
	    }
	    robot.runAsync(true);
	    robot.lock();
	    System.out.println("Sending command to move forward 1 meter...");
	    robot.enableMotors();
//	    robot.move(400);
//	    robot.unlock();
//	    System.out.println("Sleeping for 5 seconds...");
//	    ArUtil.sleep(5000);
//	    robot.lock();
//	    System.out.println("Sending command to rotate 90 degrees...");
//	    robot.setHeading(90);
//	    robot.unlock();
//	    System.out.println("Sleeping for 5 seconds...");
//	    ArUtil.sleep(5000);
//	    robot.lock();
//	    System.out.println("Robot coords: robot.getX()=" + robot.getX() + ", robot.getY()=" + robot.getY() + ", robot.getTh()=" + robot.getTh()); 
//	    ArPose p = robot.getPose();
//	    System.out.println("               pose.getX()=" + p.getX() +     ", pose.getY()="  + p.getY() +     ",  pose.getTh()=" + p.getTh());
//	    double[] xout = {0};
//	    double[] yout = {0};
//	    double[] thout = {0};
//	    p.getPose(xout, yout, thout);
//	    System.out.println("              pose.getPose(): x=" + xout[0] + ", y=" + yout[0] + ", th=" + thout[0]);
//	    robot.unlock();
//	    robot.lock();
//	    System.out.println("exiting.");
//	    robot.stopRunning(true);
//	    robot.unlock();
//	    robot.lock();
//	    robot.disconnect();
//	    robot.unlock();
//	    Aria.exit(0);
	}
	
	public void move()
	{
		System.out.println("MOVE");
		
		robot.lock();
	    robot.enableMotors();
		robot.move(400);
		robot.unlock();
		ArUtil.sleep(5000);
	}
	
	public void rotate(double angle)
	{
		System.out.println("ROTATE"+angle);
		 
		robot.lock();
	    robot.enableMotors();
	    robot.setHeading(angle);
		robot.unlock();
		ArUtil.sleep(5000);
	}
	
	public void init()
	{
		System.out.println("Starting Java Test");

	    Aria.init();

	    robot = new ArRobot();
	    
	    ArArgumentBuilder builder = new ArArgumentBuilder();
	    
	    ArArgumentParser parser = new ArArgumentParser(builder);
	    
	    if (ip != null)
	    {
	    	parser.addDefaultArgument(ip);
	    }
	    if (tcp != null)
	    {
	    	parser.addDefaultArgument(tcp);
	    }
	    
	    
	    ArRobotConnector rc = new ArRobotConnector(parser, robot);
	 
	    if(!Aria.parseArgs())
	    {
	      Aria.logOptions();
	      Aria.exit(1);
	    }

	    
	    if (!rc.connectRobot(robot))
	    {
	      System.err.println("Could not connect to robot, exiting.\n");
	      System.exit(1);
	    }
	    robot.runAsync(true);
	    
	   

	}
	public void out()
	{
	    robot.stopRunning(true);
	    robot.unlock();
	    robot.lock();
	    robot.disconnect();
	    robot.unlock();
	    Aria.exit(0);
	}

}
