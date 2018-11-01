package relaxation;

import java.lang.StringBuilder;
import java.lang.Math;

public class Voltage{
	private double[][] values;
	private int size;
	private boolean[][] boundary;
	private double[][] eps;

	Voltage(){
		
	}

	Voltage(int s)
	{
		size=s;
		values=new double[size][size];
		boundary=new boolean[size][size];
		eps=new double[size][size];
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				boundary[i][j]=false;
				eps[i][j]=1;
			}
		}
	}
	
	public String toString(){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				sb.append(values[i][j]);
				sb.append(" ");
			}
			sb.append('\n');
		}
		return sb.toString();
	}
	public void guess(){//creates an initial guess. 
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if(!boundary[i][j]){
					values[i][j]=100/(.05*i+1);
				}
			}
		}
				
	}
	public void relax(double tol)
	{
		guess();
		//double[][] newvals=new double[size][size];
		//newvals=copyBoundary();
		//newvals=values;
		double change=100;
		int k=0;
		while(change>tol)
		{
			change=0;
			for(int i=0;i<size;i++)
			{
				for(int j=0;j<size;j++)
				{
					if(!boundary[i][j])
					{
						double newpoint=0;
						
						if(eps[i][j]>eps[i+1][j]){//right edge
							newpoint=(values[i+2][j]-values[i+1][j])/eps[i][j]+values[i-1][j];
						}
						else if(eps[i][j]>eps[i-1][j]){//left edge
							newpoint=values[i+1][j]-(values[i-1][j]-values[i-2][j])/eps[i][j];
						}						
						else if(eps[i][j]>eps[i][j+1]){//bottom edge
							newpoint=(values[i][j+2]-values[i][j+1])/eps[i][j]+values[i][j-1];
						}					
						else if(eps[i][j]>eps[i][j-1]){//top edge
							newpoint=values[i][j+1]-(values[i][j-1]-values[i][j-2])/eps[i][j];
						}
						else{
							newpoint=avg(i,j);
						}
						if(Math.abs(newpoint-values[i][j])>change)
						{
							change=Math.abs(newpoint-values[i][j]);
						}
						values[i][j]=newpoint;
					}
				}
			}
			//values=newvals;
			//System.out.println("Change is: "+change);	
			//System.out.println(toString());
			k++;
			
		}
		System.out.println("done after: "+k+" loops");
	}
	public void setRowBoundary(int row, int value)
	{
		for(int i=0;i<size;i++)
		{
			values[row][i]=value;
			boundary[row][i]=true;
			
		}
	}
	public void setColumnBoundary(int column, int value)
	{
		for(int i=0;i<size;i++)
		{			
			values[i][column]=value;
			boundary[i][column]=true;
		}
	}
	public void setBoundaryPoint(int x, int y, int value)
	{
		values[x][y]=value;
		boundary[x][y]=true;
	}
	public void setDialectric(int x, int y, double value)
	{
		eps[x][y]=value;
	}
	public String toStringBound()
	{
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				sb.append(boundary[i][j]);
				
			}
			sb.append('\n');
		}
		return sb.toString();
	}
	private double avg(int row, int column)
	{
		double num=0;
		int by=0;
		if(row>0)
		{
			num+=values[row-1][column];
			by++;
		}
		if(row<size-1)
		{
			num+=values[row+1][column];
			by++;
		}
		if(column>0)
		{
			num+=values[row][column-1];
			by++;
		}
		if(column<size-1)
		{
			num+=values[row][column+1];
			by++;
		}
		
		
		return num/by;
	}
	public String toCSV(){
		StringBuilder sb=new StringBuilder();
		sb.append(size);
		sb.append(',');
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				sb.append(values[i][j]);
				sb.append(',');
			}
		}
		return sb.toString();
	}
	public String toCSV2(){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				sb.append(i);
				sb.append(',');
				sb.append(j);
				sb.append(',');
				sb.append(values[i][j]);
				sb.append('\r');
			}
			
		}
		return sb.toString();
	}
	public String getField(){
		//E=-delV
		StringBuilder sb=new StringBuilder();
		//sb.append('{');
		double dely=0;
		double delx=0;
		for(int i=0;i<size-1;i++){
			//if(i>0){sb.append('{');}
			for(int j=0; j<size-1; j++){
				delx=values[i][j]-values[i+1][j];
				dely=values[i][j]-values[i][j+1];
				sb.append('{');
				sb.append(delx);
				sb.append(',');
				sb.append(dely);
				sb.append('}');
				if(j+1<size-1){
					sb.append(',');
				}
			}
			//sb.append('}');
			sb.append('\r');
		}
		//sb.append('}');
		return sb.toString();		
	}
}