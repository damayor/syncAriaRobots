package moves;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Matrix 
{
	public ArrayList<String> steps;
	public String map;
	private String [][] m;
	
	public String[][] getM() 
	{
		return m;
	}
	public void setM(String[][] m) 
	{
		this.m = m;
	}

	public int f;
	public int c;
	
	public double orient1i;
	public double orient2i;
	public double orient1f;
	public double orient2f;
	
	public VRobbie r1;
	public VRobbie r2;
	
	//create an object of SingleObject
	private static Matrix instance = null;

   //make the constructor private so that this class cannot be
   //instantiated
	private Matrix(){}

   //Get the only object available
	public static Matrix getInstance()
	{
		if (instance == null)
		{
			instance = new Matrix();
		}
		return instance;
	}
	public void destroy()
	{
		instance = null;
	}
	
	
	public void init(String map) throws IOException
	{
		steps = new ArrayList<String>();
		this.map = map;
		BufferedReader br = new BufferedReader(new FileReader("./maps/"+map+".txt"));
		
		String s = "";
		int k = 0;
		while ((s = br.readLine()) != null)
		{
			if (s.contains("filas"))
			{
				f = Integer.parseInt((s.split(" "))[1]);
			}
			else if (s.contains("columnas"))
			{
				c = Integer.parseInt((s.split(" "))[1]);
				m = new String[f][c];
			}
			if (s.contains("1i:"))
			{
				orient1i = Double.parseDouble((s.split(" "))[3]);
				System.out.println("orientacion inicial "+orient1i);
			}
			else if (s.contains("1f:"))
			{
				orient1f = Double.parseDouble((s.split(" "))[3]);
				System.out.println("orientacion inicial "+orient1f);
			}
			else if (s.contains("2i:"))
			{
				orient2i = Double.parseDouble((s.split(" "))[3]);
				System.out.println("orientacion inicial "+orient2i);
			}
			else if (s.contains("2f:"))
			{
				orient2f = Double.parseDouble((s.split(" "))[3]);
				System.out.println("orientacion final "+orient2f);
			} 
			if ((s.contains("oo") || s.contains("xx")) && !(s.contains("(oo)") || s.contains("(xx)")))
			{
				String [] fila = s.split(" ");
				m[k]= fila;
				k++;
			}
		}
		br.close();
		
//		r1 = new VRobbie(1);
//		r2 = new VRobbie(2);
//		
//		for (int i = 0; i<10;i++)
//		{	
//			for (int j = 0; j<10;j++)
//			{
//				s = m[i][j];
//				if(s.contains("1"))
//				{
//					if(s.contains("i"))
//					{
//						r1.setX(j);
//						r1.setY(i);
//						r1.setX1(j);
//						r1.setY1(i);
//					}
//					else if(s.contains("f"))
//					{
//						r1.setY2(i);
//						r1.setX2(j);
//					}
//				}
//				else if(s.contains("2"))
//				{
//					if(s.contains("i"))
//					{
//						r2.setX(j);
//						r2.setX1(j);
//						r2.setY(i);
//						r2.setY1(i);
//					}
//					else if(s.contains("f"))
//					{
//						r2.setY2(i);
//						r2.setX2(j);
//					}
//				}
//				
//				System.out.print(m[i][j]);
//				System.out.print(" ");
//			}
//			System.out.println();
//		}
//		System.out.println();
//		System.out.println("R1 - i: ("+r1.getX1()+","+r1.getY1()+") , f: ("+r1.getX2()+" , "+r1.getY2()+")");
//		System.out.println("R2 - i: ("+r2.getX1()+","+r2.getY1()+") , f: ("+r2.getX2()+" , "+r2.getY2()+")");
		
		
	}
	public synchronized void printMatrix()
	{
		
		try 
		{
			FileWriter writer = new FileWriter("./results/results_"+map+".txt", true);
			
			writer.append("\n");
			System.out.println();
			for (int i = 0; i<f;i++)
			{	
				String line="";
				for (int j = 0; j<c;j++)
				{	
					line = line + m[i][j] +" ";
					System.out.print(m[i][j]);
					System.out.print(" ");
				}
				writer.append(line+"\n");
				
				System.out.println();
			}
			
			writer.flush();
			writer.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void findPath(VRobbie r) throws InterruptedException
	{
		int x = r.getX();
		int y = r.getY();
		int xf = r.getX2();
		int yf = r.getY2();
		
		System.out.println("x:"+x+" y:"+y+" xf:"+xf+" yf: "+yf);
		
		m[y][x] = r.getId();
		
		System.out.println("FIND PATH");

		boolean finish = false;
		
		while (!finish)
		{

			approachX(r);
			approachY(r);
			
			if (r.getX() == xf && r.getY() == yf)
			{
				finish = true;
			}
			else
			{
				System.out.println("Getting there...");
			}
		}
		
	}
	
	public void approachX(VRobbie r) throws InterruptedException
	{
		System.out.println("Approach X");
		r.printCoordinates();
		int x = r.getX();
		int xf = r.getX2();
		
		
		if (x != xf)
		{
			int q = xf -x;
			q = q / Math.abs(q);
			
			if ( !moveX(r, q))
			{
				dodgeX(r, q);
			}
		}
		Thread.sleep(1000);
		
		
		
		
	}
	
	public void dodgeX(VRobbie r, int q)
	{
		System.out.println("Dodge X");
		while (!moveX(r, q))
		{
			if (!up(r))
			{
				down(r);
			}
		}
	}
	
	public void dodgeY(VRobbie r, int q)
	{
		System.out.println("Dodge Y");
		while (!moveY(r, q))
		{
			if (!right(r))
			{
				left(r);
			}
		}
	}
	
	public void approachY(VRobbie r)
	{
		System.out.println("Approach Y");
		
		int y = r.getY();
		int yf = r.getY2();
		

		if (y !=yf)
		{
			int q = yf -y;
			q = q / Math.abs(q);
			
			if ( !moveY(r, q))
			{
				dodgeY(r, q);
			}
		}
		
		
	}
	
	public boolean up(VRobbie r)
	{
		System.out.println("up");
		String s = m[r.getY()-1][r.getX()];
		if ( s.equals("oo") || s.equals(r.num()))
		{
			
			m[r.getY()][r.getX()] = "oo";
			r.setY(r.getY()-1);
			m[r.getY()][r.getX()] = r.getId();
			printMatrix();
			
			return true;
		}
		return false;
	}
	
	public boolean down(VRobbie r)
	{
		System.out.println("down");
		String s = m[r.getY()+1][r.getX()];
		if ( s.equals("oo") || s.equals(r.num()))
		{
			m[r.getY()][r.getX()] = "oo";
			r.setY(r.getY()+1);
			m[r.getY()][r.getX()] = r.getId();
			printMatrix();
			return true;
		}
		return false;
	}
	
	public boolean left(VRobbie r)
	{
		System.out.println("left");
		String s = m[r.getY()][r.getX()-1];
		if ( s.equals("oo") || s.equals(r.num()))
		{
			m[r.getY()][r.getX()] = "oo";
			r.setX(r.getX()-1);
			m[r.getY()][r.getX()] = r.getId();
			printMatrix();
			return true;
		}
		return false;
	}
	
	public boolean right(VRobbie r)
	{
		System.out.println("right");
		String s = m[r.getY()][r.getX()+1];
		System.out.println("s: "+s);
		if ( s.equals("oo") || s.equals(r.num()))
		{
			m[r.getY()][r.getX()] = "oo";
			r.setX(r.getX()+1);
			m[r.getY()][r.getX()] = r.getId();
			printMatrix();
			return true;
		}
		return false;
	}
	// e: +1 -> right
	// e: -1 -> left
	public boolean moveX(VRobbie r, int e)
	{

		String s = m[r.getY()][r.getX()+e];
		System.out.println("finish: "+r.num());
		if ( s.equals("oo") || s.equals(r.num()))
		{
			System.out.println("Move X "+e);
			m[r.getY()][r.getX()] = "oo";
			r.setX(r.getX()+e);
			m[r.getY()][r.getX()] = r.getId();
			printMatrix();
			return true;
		}
		return false;
	}
	
	// e: +1 -> down
	// e: -1 -> up
	public boolean moveY(VRobbie r, int e)
	{
		
		
		String s = m[r.getY()+e][r.getX()];
		if ( s.equals("oo") || s.equals(r.num()))
		{
			System.out.println("move Y "+e);
			m[r.getY()][r.getX()] = "oo";
			r.setY(r.getY()+e);
			m[r.getY()][r.getX()] = r.getId();
			printMatrix();
			return true;
		}
		return false;
	}
	
	public void writeMoves() throws IOException
	{
		
		FileWriter writer = new FileWriter("./results/steps_"+map+".txt");
		for (String i : steps)
		{
			writer.append(i+"\n");
		}
		writer.flush();
		writer.close();
	}

}
