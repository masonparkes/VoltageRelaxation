package relaxation;

import java.util.Random;
import java.io.PrintWriter;
import java.lang.Math;
public class Main{
	public static void main(String[] args) throws Exception{
		long startTime = System.nanoTime();
		int size=Integer.parseInt(args[0]);
		double tol=Double.parseDouble(args[1]);
		Voltage v=new Voltage(size);
		v.setColumnBoundary(0,0);v.setColumnBoundary(size-1,0);
		v.setRowBoundary(size-1,0);v.setRowBoundary(0,100);
		double er=1.0;//dialectric constant
		for(int i=size*3/10;i<size*7/10;i++)
		{
			for(int j=size*3/10;j<size*7/10;j++)
			{
				v.setDialectric(i,j,er);
			}
		}
		v.relax(tol);
		PrintWriter pw=new PrintWriter("er1.csv");
		pw.println(v.toCSV2());
		pw.close();
		pw=new PrintWriter("Efield1.csv");
		pw.println(v.getField());
		pw.close();
		
		v=new Voltage(size);
		v.setColumnBoundary(0,0);v.setColumnBoundary(size-1,0);
		v.setRowBoundary(size-1,0);v.setRowBoundary(0,100);
		er=4.0;//dialectric constant
		for(int i=size*3/10;i<size*7/10;i++)
		{
			for(int j=size*3/10;j<size*7/10;j++)
			{
				v.setDialectric(i,j,er);
			}
		}
		v.relax(tol);
		pw=new PrintWriter("er4.csv");
		pw.println(v.toCSV2());
		pw.close();
		pw=new PrintWriter("Efield4.csv");
		pw.println(v.getField());
		pw.close();
		
		v=new Voltage(size);
		v.setColumnBoundary(0,0);v.setColumnBoundary(size-1,0);
		v.setRowBoundary(size-1,0);v.setRowBoundary(0,100);
		er=1000.0;//dialectric constant
		for(int i=size*3/10;i<size*7/10;i++)
		{
			for(int j=size*3/10;j<size*7/10;j++)
			{
				v.setDialectric(i,j,er);
			}
		}
		v.relax(tol);
		pw=new PrintWriter("er1000.csv");
		pw.println(v.toCSV2());
		pw.close();
		pw=new PrintWriter("Efield1000.csv");
		pw.println(v.getField());
		pw.close();
		
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		totalTime=totalTime/(1000000);
		System.out.println("Time: "+totalTime+" millliSeconds");
	}
}
/*
public class Main{
	public static void main(String[] args) throws Exception{
		long startTime = System.nanoTime();

		int size=Integer.parseInt(args[0]);
		double tol=Double.parseDouble(args[1]);
		//System.out.println("Double is: "+tol);
		Voltage v=new Voltage(size);
		//System.out.println(v.toString());
		//
		//Make a random assortment of fixed potential points
		//
		Voltage rando=new Voltage(size);
		Random r=new Random();
		int charge_num=r.nextInt(size);
		while(charge_num==0)
		{
			charge_num=r.nextInt(size);
		}
		for(int i=0;i<charge_num;i++)//creates random charges
		{
			int x=r.nextInt(size);
			int y=r.nextInt(size);
			int val=r.nextInt(100);
			int power=r.nextInt(100);
			double factor=Math.pow(-1,power);
			val=(int)factor*val;
			rando.setBoundaryPoint(x,y,val);
		}
		rando.relax(tol);
		PrintWriter pw=new PrintWriter("random.csv");
		pw.println(rando.toCSV2());
		pw.close();

		//The actual Assignment
		v=new Voltage(size);
		v.setRowBoundary(size-1,0);
		v.setRowBoundary(0,0);
		v.setColumnBoundary(0,0);
		v.setColumnBoundary(size-1,0);
		int y1=size*1/8;
		int y2=size*7/8;
		for(int i=size/4;i<size*3/4;i++)//creates capacitor plates
		{
			v.setBoundaryPoint(i,y1,100);
			v.setBoundaryPoint(i,y2,-100);
		}
		pw=new PrintWriter("assignment.csv");
		pw.println(v.toCSV2());
		pw.close();
		v.relax(tol);
		pw=new PrintWriter("assignment_done.csv");
		pw.println(v.toCSV2());
		pw.close();
		

		//Capacitor Plates with a circle in the middle
		v= new Voltage(size);
		int rcenter=50;//creates equipotential circle
		int rwidth=10;
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				double rSquared=Math.pow(i-size/2,2)+Math.pow(j-size/2,2);
				if(rSquared>rcenter-rwidth&&rSquared<rcenter+rwidth)
				{
					v.setBoundaryPoint(i,j,0);
				}
			}
		}
		y1=size*1/8;
		y2=size*7/8;
		for(int i=size/4;i<size*3/4;i++)//creates capacitor plates
		{
			v.setBoundaryPoint(i,y1,100);
			v.setBoundaryPoint(i,y2,-100);
		}
		v.relax(tol);
		pw=new PrintWriter("circleinplates.csv");
		pw.println(v.toCSV2());
		pw.close();


		//A thing they do in Phscs 108 lab
		Voltage lab=new Voltage(size);
		lab.setBoundaryPoint(size*1/5,size/2,-15);
		lab.setBoundaryPoint(size*4/5,size/2,15);
		lab.setBoundaryPoint(size*1/2,0,0);
		lab.relax(tol);
		pw=new PrintWriter("108lab.csv");
		pw.println(lab.toCSV2());
		pw.close();
		

		//a circle with charge in the middle
		v= new Voltage(size);
		rcenter=size*4/10;//creates equipotential circle
		rwidth=size/3;
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				double rSquared=Math.pow(i-size/2,2)+Math.pow(j-rcenter,2);
				if(rSquared>rcenter-rwidth&&rSquared<rcenter+rwidth)
				{
					v.setBoundaryPoint(i,j,0);
				}
			}
		}
		rcenter=size*6/10;//creates equipotential circle
		rwidth=size/3;
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				double rSquared=Math.pow(i-size/2,2)+Math.pow(j-rcenter,2);
				if(rSquared>rcenter-rwidth&&rSquared<rcenter+rwidth)
				{
					v.setBoundaryPoint(i,j,0);
				}
			}
		}
		rcenter=size*1/10;//creates equipotential circle
		rwidth=size/3;
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				double rSquared=Math.pow(i-size/2,2)+Math.pow(j-rcenter,2);
				if(rSquared>rcenter-rwidth&&rSquared<rcenter+rwidth)
				{
					v.setBoundaryPoint(i,j,0);
				}
			}
		}
		rcenter=size*9/10;//creates equipotential circle
		rwidth=size/3;
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				double rSquared=Math.pow(i-size/2,2)+Math.pow(j-rcenter,2);
				if(rSquared>rcenter-rwidth&&rSquared<rcenter+rwidth)
				{
					v.setBoundaryPoint(i,j,0);
				}
			}
		}
		v.setBoundaryPoint(size/2,size*4/10,100);
		v.setBoundaryPoint(size/2,size*6/10,-100);
		v.setBoundaryPoint(size/2,size*9/10,100);
		v.setBoundaryPoint(size/2,size*1/10,-100);
		v.relax(tol);
		pw=new PrintWriter("circlewithcharges.csv");
		pw.println(v.toCSV2());
		pw.close();
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		totalTime=totalTime/(1000000);
		System.out.println("Time: "+totalTime+" millliSeconds");
	}
}
*/