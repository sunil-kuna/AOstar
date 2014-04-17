import java.io.IOException;


public class AOmain {
	
	
	static int n;
	static boolean solved=false;
	

	public static void main(String args[]) throws IOException
	{
		String data = null;
		ReadFile rf= new ReadFile("D:\\CSE6 Resources\\AI\\AOstar\\Input.txt");
		try {
			data=rf.openfile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		n=rf.no_of_nodes();
		Vertical_Node[] graph = new Vertical_Node[n];
		
		buildgraph(data,graph);
		printgraph(graph);
		int length=0;
		while(!solved)
			graph[0].hvalue=AOStar(graph,0);
		
		System.out.println(graph[0].hvalue);
		printgraph(graph);
		for(int i=0;i<n;i++)
		{
			System.out.println( i +" = "+graph[i].status);
		}
		
	}



	private static  int AOStar(Vertical_Node[] graph,int i) {
		// TODO Auto-generated method stub
		boolean traverse=false;
		boolean tempand=false;
		int min=graph[i].hvalue,minnode=graph[i].node,minnode2 = 0,tempmin,pathmin = 0,min2;
		Horizontal_Node smallNode = null,smallNode2 = null;
		if(graph[i].status==0)
		{
			graph[i].status=1;
			if(graph[i].node==7)
			{
				solved=true;
			}
			System.out.println("node = "+graph[i].node+" min = "+min);
			return graph[i].hvalue;
		}
		else if(graph[i].status==2)
		{
			return graph[i].hvalue;
			
		}
		Horizontal_Node temp = graph[i].hnext;
		Horizontal_Node temp2 = null;
		while(temp!=null&&graph[temp.node].status==2)
			temp=temp.hnext;
		if(temp!=null)
		{
			
			if(!temp.and)
			{		
				min=temp.hvalue+temp.path_length;
				minnode=temp.node;
				pathmin=temp.path_length;
				smallNode=temp;
			}
			else
			{
				tempand=true;
				temp2=temp.hnext;
				if(graph[temp2.node].status!=2)
				{
					min=Math.min(temp.hvalue,temp2.hvalue);
					pathmin=temp.path_length+temp2.path_length;
					if(min==temp.hvalue)
					{
						smallNode=temp;
						minnode=temp.node;
						minnode2=temp2.node;
						smallNode2=temp2;
					}
					else
					{
						smallNode=temp2;
						minnode=temp2.node;
						minnode2=temp.node;
						smallNode2=temp;
					}
					
					min=temp.hvalue+temp.path_length+temp2.hvalue+temp2.path_length;
					
				}
				else
				{
					min=temp.hvalue+temp.path_length;
					minnode=temp.node;
					pathmin=temp.path_length;
					smallNode=temp;
					minnode2=temp2.node;
					smallNode2=temp2;
				}
				temp=temp.hnext;
				temp=temp.hnext;
			}
		}
		else
		{
			System.out.println("Status of "+i+" = 2");
			graph[i].status=2;
			temp = graph[i].hnext;
		}
		while(temp!=null)
		{
			if(!temp.and)
			tempmin=temp.path_length+temp.hvalue;//AOStar(graph,temp.node);
			else
			{
				temp2=temp.hnext;
				tempmin=temp.hvalue+temp.path_length+temp2.hvalue+temp2.path_length;
			}
			
			if(tempmin<min)
			{
				System.out.println("While Tempnode = "+temp.node+" temp--and= "+temp.and);
					
				if(!temp.and)
				{
					min=tempmin;
					minnode=temp.node;
					pathmin=temp.path_length;
					smallNode=temp;
					tempand=false;
				}
				else if(temp.and&&graph[temp2.node].status!=2)
				{
					
					min=Math.min(temp.hvalue,temp2.hvalue);
					pathmin=temp.path_length+temp2.path_length;
					if(min==temp.hvalue)
					{
						smallNode=temp;
						minnode=temp.node;
						minnode2=temp2.node;
						smallNode2=temp2;
					}
					else
					{
						smallNode=temp2;
						minnode=temp2.node;
						minnode2=temp.node;
						smallNode2=temp;
					}
					min=temp.hvalue+temp.path_length+temp2.hvalue+temp2.path_length;
					temp=temp.hnext;
					tempand=true;
				}
				else if(temp.and&&graph[temp2.node].status==2)
				{
					min=temp.hvalue+temp.path_length+temp2.hvalue+temp2.path_length;
					minnode=temp.node;
					pathmin=temp.path_length+temp2.path_length;
					smallNode=temp;
					minnode2=temp2.node;
					smallNode2=temp2;
					tempand=true;
					temp=temp.hnext;
				}
			}
			temp=temp.hnext;
		}
		if(graph[i].status==2)
		{
			graph[i].hvalue=min;
			return min;
		}
		
		System.out.println("graph[minnode] = "+graph[minnode].hvalue+" minnode = "+minnode+" minnode2 = "+minnode2+" graph[minnode2] = "+graph[minnode2].hvalue+" min = "+min+" pathmin= "+pathmin+" tempand = "+tempand);
		
		if(!tempand)
		{	
				min=AOStar(graph,minnode);
				graph[i].hvalue=pathmin+min;
				smallNode.hvalue=min;
		}
		else
		{
			min=AOStar(graph,minnode);
					min2=AOStar(graph,minnode2);
					System.out.println("min2 = "+min2);
			
				graph[i].hvalue=pathmin+min+min2;
				smallNode.hvalue=min;
				smallNode2.hvalue=min2;
			
		}
		
		System.out.println("i = "+i+"graph[i] = "+graph[i].status);
		return graph[i].hvalue;
	}

	private static void printgraph( Vertical_Node graph[]) {
		// TODO Auto-generated method stub
		
	int i=0;
		while(i<n)
		{
			print_Node(graph,i);
			i++;
		}
		
	}

	private static void print_Node(Vertical_Node graph[],int i) {
		// TODO Auto-generated method stub
		
		System.out.print(" "+graph[i].node+"  "+graph[i].hvalue+" ");
		Horizontal_Node temp = graph[i].hnext;
		while(temp!=null)
		{
			System.out.print(" "+temp.node+" "+temp.path_length+" "+temp.hvalue+" ");
			temp=temp.hnext;
		}
		
		for( int[]k :  graph[i].andnode)
		{
			if(k[0]!=0)
			System.out.print(" "+k[0]+" "+k[1]+" "+k[2]);
		}
		System.out.println();
	}

	@SuppressWarnings("null")
	private static void buildgraph(String data,Vertical_Node[] graph) {
		// TODO Auto-generated method stub
		
		data=data.replace("null","");
		Vertical_Node tempvn =null;
		Vertical_Node vn = null ;
		Horizontal_Node temphn =null;
		Horizontal_Node hn = null ;
		
		int j = 0,k=0;
		 for (String temp_1: data.split("\n"))
		 {
			 
			 int i=0;
			 graph[k]=new Vertical_Node();
			 graph[k].andnode=new int[10][3];
	        // System.out.println(temp_1);
	         for (String temp_2: temp_1.split(","))
	         {
	        	 
	        	int num=Integer.parseInt(temp_2);
	        	//System.out.print(num+" "); 
	        	
	        	if(i==0)
	        	{
	        		j=0;
	        		
	        		graph[k].node=num;
	        	}
	        	else if(i==1)
	        	{
	        		graph[k].hvalue=num;
	        	}
	        	else if(num!=0)
	        	{
	        		int mod=i-2;
	        		mod=mod%5;
	        		if(mod==0)
	        		{
	        			
	        			hn=new Horizontal_Node();
	        			hn.node=num;
	        			if(i>2)
		        			temphn.hnext=hn;
		        			else
		        			graph[k].hnext=hn;
	        			if(i>2)
	        			{
	        				if(temphn.and)
	        				{
	        				graph[k].andnode[j][0]=temphn.node;
	        				graph[k].andnode[j][1]=hn.node;
	        				graph[k].andnode[j][2]=temphn.andvalue;
	        				}
	        			}
	        				
	        		}
	        		else if(mod==1)
	        		{
	        			hn.path_length=num;
	        		}
	        		else if(mod==2)
	        		{
	        			hn.hvalue=num;
	        			temphn=hn;
	        		}
	        		else if(mod==3)
	        		{
	        			if(num==0)
	        			hn.and=false;
	        			else
	        			{
	        				hn.and=true;
	        				j++;
	        			}
	        			
	        		}
	        		else if(mod==4)
	        		{
	        			
	        			hn.andvalue=num;
	        		}
	        		
	        	}
	        	//System.out.println(i);
	        	i++;
	         }
	         
	         k++;
		 }
	}

}
