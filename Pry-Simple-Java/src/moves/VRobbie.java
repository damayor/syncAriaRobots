package moves;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class VRobbie implements Runnable
{
	private int x1,y1,x2,y2,x,y;
	private String id;
	private int num;
	public ArrayList<String> moves;
	
//	private String [][] m;
	
	private VRobbie r = this;

	public VRobbie(int x1, int y1, int x2, int y2, int x, int y)
	{
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.x = x;
		this.y = y;
	}
	public VRobbie()
	{
		
	}
	public VRobbie(String id)
	{
		this.id = id;
	}
	public VRobbie(int id)
	{
		this.id = "r"+id;
		setNum(id);
		
		init(Matrix.getInstance().getM());
		
		moves = new ArrayList<String>();
		
	}

	public VRobbie(int id, Matrix martix)
	{
		this.id = "r"+id;
		setNum(id);
		
		init(Matrix.getInstance().getM());
	}
	
	public synchronized void init(String [][]m)
	{
		String s = "";
		for (int i = 0; i<Matrix.getInstance().c;i++)
		{	
			for (int j = 0; j<Matrix.getInstance().f;j++)
			{
				s = m[i][j];
				if(s.contains(""+num))
				{
					if(s.contains("i"))
					{
						setX(j);
						setY(i);
						setX1(j);
						setY1(i);
					}
					else if(s.contains("f"))
					{
						setY2(i);
						setX2(j);
					}
				}
				System.out.print(m[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public void printCoordinates() 
	{
		System.out.println(id+" - x:"+x+" y:"+y);
	}

	public String num() {
		return num+"f";
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
	
	public void findPath() throws InterruptedException
	{
		int x = r.getX();
		int y = r.getY();
		int xf = r.getX2();
		int yf = r.getY2();
		
		System.out.println("x:"+x+" y:"+y+" xf:"+xf+" yf: "+yf);
		
//		m[y][x] = r.getId();
		
		System.out.println("FIND PATH");

		boolean finish = false;
		
		while (!finish)
		{
			if(Math.random()>0.5)
			{
				approachX(Matrix.getInstance().getM());
			}
			else
			{
				approachY(Matrix.getInstance().getM());
			}
			
			
			
			if (r.getX() == xf && r.getY() == yf)
			{
				finish = true;
				System.out.println("Done!");
			}
			else
			{
				System.out.println("Getting there...");
			}
		}
		
	}
	
	public synchronized void approachX(String [][]m) throws InterruptedException
	{
		System.out.println("Approach X");
		r.printCoordinates();
		int x = r.getX();
		int xf = r.getX2();
		
		
		if (x != xf)
		{
			int q = xf -x;
			q = q / Math.abs(q);
			
			if ( !moveX(q, m))
			{
				dodgeX(q, m);
			}
		}
//		Thread.sleep(1000);
	}
	public synchronized void approachY(String [][]m) throws InterruptedException
	{
		System.out.println("Approach Y");
		
		int y = r.getY();
		int yf = r.getY2();
		

		if (y !=yf)
		{
			int q = yf -y;
			q = q / Math.abs(q);
			
			if ( !moveY(q, m))
			{
				dodgeY(q, m);
			}
		}
//		Thread.sleep(1000);
	}
	
	public synchronized void dodgeX(int q, String [][]m)
	{
		System.out.println("Dodge X");
		boolean g = (Math.random()>0.5);
		while (!moveX(q, m))
		{
			if (g)
			{
				if (!up(m))
				{
					down(m);
				}
			}
			else
			{
				if (!down(m))
				{
					up(m);
				}
			}
			
		}
	}
	
	public synchronized void dodgeY(int q, String [][]m)
	{
		System.out.println("Dodge Y");
		boolean g = (Math.random()>0.5);
		while (!moveY(q, m))
		{
			if (g)
			{
				if (!right(m))
				{
					left(m);
				}
			}
			else
			{
				if (!left(m))
				{
					right(m);
				}
			}
			
		}
	}
	
	
	
	public synchronized boolean up(String [][]m)
	{
		System.out.println("up");
		String s = m[r.getY()-1][r.getX()];
		if ( s.equals("oo") || s.equals(r.num()))
		{
			moves.add("up");
			Matrix.getInstance().steps.add("R"+num+" - up");
			
			m[r.getY()][r.getX()] = "oo";
			r.setY(r.getY()-1);
			m[r.getY()][r.getX()] = r.getId();
			Matrix.getInstance().printMatrix();
			
			return true;
		}
		return false;
	}
	
	public synchronized boolean down(String [][]m)
	{
		System.out.println("down");
		String s = m[r.getY()+1][r.getX()];
		if ( s.equals("oo") || s.equals(r.num()))
		{
			moves.add("down");
			Matrix.getInstance().steps.add("R"+num+" - down");
			m[r.getY()][r.getX()] = "oo";
			r.setY(r.getY()+1);
			m[r.getY()][r.getX()] = r.getId();
			Matrix.getInstance().printMatrix();
			return true;
		}
		return false;
	}
	
	public synchronized boolean left(String [][]m)
	{
		System.out.println("left");
		String s = m[r.getY()][r.getX()-1];
		if ( s.equals("oo") || s.equals(r.num()))
		{
			moves.add("left");
			Matrix.getInstance().steps.add("R"+num+" - left");
			m[r.getY()][r.getX()] = "oo";
			r.setX(r.getX()-1);
			m[r.getY()][r.getX()] = r.getId();
			Matrix.getInstance().printMatrix();
			return true;
		}
		return false;
	}
	
	public synchronized boolean right(String [][]m)
	{
		System.out.println("right");
		String s = m[r.getY()][r.getX()+1];
		System.out.println("s: "+s);
		if ( s.equals("oo") || s.equals(r.num()))
		{
			moves.add("right");
			Matrix.getInstance().steps.add("R"+num+" - right");
			m[r.getY()][r.getX()] = "oo";
			r.setX(r.getX()+1);
			m[r.getY()][r.getX()] = r.getId();
			Matrix.getInstance().printMatrix();
			return true;
		}
		return false;
	}
	// e: +1 -> right
	// e: -1 -> left
	public synchronized boolean moveX(int e, String [][]m)
	{

		String s = m[r.getY()][r.getX()+e];
		System.out.println("finish: "+r.num());
		if ( s.equals("oo") || s.equals(r.num()))
		{
			if (e==1)
			{
				moves.add("right");
				Matrix.getInstance().steps.add("R"+num+" - right");
			}
			else if (e==-1)
			{
				moves.add("left");
				Matrix.getInstance().steps.add("R"+num+" - left");
			}
			System.out.println("Move X "+e);
			m[r.getY()][r.getX()] = "oo";
			r.setX(r.getX()+e);
			m[r.getY()][r.getX()] = r.getId();
			Matrix.getInstance().printMatrix();
			return true;
		}
		return false;
	}
	// e: +1 -> down
	// e: -1 -> up
	public synchronized boolean moveY(int e, String [][]m)
	{
		
		String s = m[r.getY()+e][r.getX()];
		if ( s.equals("oo") || s.equals(r.num()))
		{
			if (e==1)
			{
				moves.add("down");
				Matrix.getInstance().steps.add("R"+num+" - down");
			}
			else if (e==-1)
			{
				moves.add("up");
				Matrix.getInstance().steps.add("R"+num+" - up");
			}
			System.out.println("move Y "+e);
			m[r.getY()][r.getX()] = "oo";
			r.setY(r.getY()+e);
			m[r.getY()][r.getX()] = r.getId();
			Matrix.getInstance().printMatrix();
			return true;
		}
		return false;
	}
	public void run() 
	{
		try 
		{
			findPath();
//			writeMoves();
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void writeMoves() throws IOException
	{
		
		FileWriter writer = new FileWriter("./results/results_R"+num+"_"+Matrix.getInstance().map+".txt");
		for (String i : moves)
		{
			writer.append(i+"\n");
		}
		writer.flush();
		writer.close();
	}
}
