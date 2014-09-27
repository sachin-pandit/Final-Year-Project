import java.util.*;
class good{
		String name;
		float[][] destination;
		int weight;

		boolean loaded=false;

}

class truck {
		float[][] destination;
		int max_load;
		boolean Multiple_dest=false;
		boolean full = false;
		LinkedList<good> truck_goods;
}


public class its {
		
	static	LinkedList<float[][]> dest = new LinkedList<float[][]>();  //List of destinations

/********************************************************************************************************************/
	public static void main(String args[])

	{


			LinkedList<good> goods = new LinkedList<good>();
			good g1 = new good();
			g1.name = "Wallet";

			
			good g2 = new good();
			g2.name = "Shirt";


			good g3 = new good();
			g3.name = "Wallet";

			goods.add(g1);
			goods.add(g2);
			goods.add(g3);
			sort(goods);

			LinkedList<truck> t = new LinkedList<truck>();
			t=Initialize_Truck(t);

	}


/********************************************************************************************************************/

	 static ArrayList<LinkedList<good>> sort(LinkedList<good> goods)  //Sorting goods based on destination -- Algo1
	{
			
				
		ArrayList<LinkedList<good>> dest_goods = new ArrayList<LinkedList<good>>(); //ArrayList containing linkedList of goods as nodes
		for(int i=0;i<goods.size();i++)
		{
			if(!dest.contains(goods.get(i).destination))
			{
							
   				dest.add(goods.get(i).destination);	
				LinkedList<good> temp = new LinkedList<good>();
				temp.add(goods.get(i));				
				dest_goods.add(goods.get(i));
				temp.clear();
			} 
			else
			{
				int loc = dest.indexOf(goods.get(i).destination);
				LinkedList<good> temp = new LinkedList<good>();
				temp=dest_goods.get(loc);
				temp.add(goods.get(i));
				dest_goods.add(loc,temp);
				temp.clear();
						
			}
		}	
				

		return dest_goods;
	}
		
/********************************************************************************************************************/

	static LinkedList<truck> Initialize_Truck(LinkedList<truck> t)	//Algorithm 2
	{
		System.out.println("Enter the number of trucks");
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		System.out.println("Enter the capacity of the trucks");
		for(int i=0;i<n;i++)
		{
			truck t1 = new truck();
			t1.max_load = s.nextInt();
			t.add(t1);
		}
		return t;
	}


/********************************************************************************************************************/

	static truck Optimal_goods(ArrayList<LinkedList<good>> dest_goods, truck t, LinkedList<good> temp) //Algorithm 3
	{
		int tot_wt=0;
		boolean c=false;
			

		LinkedList<good> truck_temp = new LinkedList<good>();
			
		while( t.full == false && temp != null)
		{
			good g = new good();
			g=temp.removeFirst();
			if(c == false)
			{
				t.destination[0][0] = g.destination[0][0];
				t.destination[0][1] = g.destination[0][1];	
				c=true;
			}

			if(g != null && tot_wt + g.weight <= t.max_load )	
			{
				truck_temp.add(g);
				tot_wt += g.weight;
			}

			if( tot_wt + g.weight > t.max_load )
					t.full = true;
		}


			t.truck_goods = truck_temp;
			return t;									
	}
	

/********************************************************************************************************************/
	
	static truck Final_Optimize(ArrayList<LinkedList<good>> dest_goods, truck t)
	{
		int loc=0,t1,t2;
		LinkedList<good> temp = new LinkedList<good>();

		LinkedList<good> truck_temp = new LinkedList<good>();
		truck_temp=t.truck_goods;

		float[][] temp_dest = new float[1][2];
	
	/*		ref[0][0] = t.destination[0][0];
			ref[0][1] = t.destination[0][1];		*/
			
		temp_dest = dest.getFirst();

		float[][] small = new float[1][2];		
		small[0][0] = temp_dest[0][0] - t.destination[0][0];
		small[0][1] = temp_dest[0][1] - t.destination[0][1];

					 
			 
		for(int i=1;i<dest.size();i++)
		{
			temp_dest = dest.getFirst();
		
			t1 = temp_dest[i][0] - t.destination[0][0] ;
			t2 = temp_dest[i][1] - t.destination[0][1] ;
			
			if( t1 < small[0][0] && t2 <small[0][1]) 				{
				small[0][0]=t1;
				small[0][1]=t2;
				loc=i;
			}			
				
		}
		temp = dest_goods.get(loc);
		t = Optimal_goods(dest_goods,t,temp);
					
		if(t.full = true)
		{
			t.Multiple_dest = true;			
			return t;
		}

		else
			Final_Optimize(dest_goods,t);
		}
}
