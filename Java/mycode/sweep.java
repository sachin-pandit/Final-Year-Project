import java.util.*;
import java.sql.*;
import java.io.*;

class customer{
	double lat, longi, total;
	int assign_flag;
	double prev_dist, cur_dist;
	String name, loc;
	customer(){
		lat = 0;
		longi = 0;
		assign_flag = 0;
		prev_dist = 0.0 ;
		cur_dist = 0.0;	
		total = 0;
	}	
}

/*
class truck{
	int num;
	double capacity;
	truck(int number, double truck_capacity){
		num = number;
		capacity = truck_capacity;
	}
}
*/

class s_cust{
	int num;
	int assigned ;
	s_cust( ){
		assigned = 0;
	}	
}

class sweep {
	public static void main(String str[]){
		try{
			double gla = 0, glo = 0;
			float tlla = -180, tllo = 90, trla = 180, trlo = 90;
			float blla = -180 , bllo = -90, brla = 180, brlo = -90;
			call_sweep(gla, glo, tlla, tllo, trla, trlo, blla, bllo, brla, brlo );		 
		}
		catch( NullPointerException e){
			e.printStackTrace();
			System.out.println("Null Pointer Exception");
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
			System.out.println("Class Not found Exception");
		}
		catch( SQLException e){
			e.printStackTrace();
			System.out.println("SQl Exception");
		}
	}

	static void call_sweep(double gla, double glo,double tlla, double tllo, double trla, double trlo, double blla, double bllo, double brla, double brlo ) throws NullPointerException, ClassNotFoundException, SQLException {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/godown","root","pandit");  
	 		Statement stmt = con.createStatement();
			Statement stmt2 = con.createStatement();
			Statement stmt3 = con.createStatement();
     		Scanner s = new Scanner (System.in);


			customer[] c = new customer[100] ;
			truck[] t = new truck[10];
			int j, assigned_count = 1, no_trucks = 0, cust_count = 0, truck_capacity, cur_weight = 0, cur_truck = 0, count;
			double i; 
			String sql, c_name, loc;
			float lat, longi, total_weight;
			boolean flag = true;
			

			cust_count = 0;
			sql = "select * from assigned_goods where near = 1 ";
			ResultSet result2 = stmt.executeQuery(sql);						
			while( result2.next()){
				total_weight = 0;
				cust_count++;
				c[cust_count] = new customer();
				lat = result2.getFloat(4);
				longi = result2.getFloat(5);
				c_name = result2.getString(6);
				loc = result2.getString(7);
//				System.out.println("Lat = " + lat + " longi = " + longi + " c_name = " + c_name );
				sql = "select * from assigned_goods where customer_name = '" ;
				sql = sql + c_name + "'" ;
				sql = sql +  " && abs( destination_lat - " + lat +") < .001  &&  abs( destination_lon - " + longi + ") < 0.001 " ;
//				System.out.println("Sql = " + sql); 
				ResultSet result1 = stmt2.executeQuery(sql);	
				while( result1.next() ){
//					System.out.println("good name = " + result1.getString(1) + " Customer_name = " + result1.getString(6) );
					total_weight += result1.getInt(2) * result1.getFloat(3);
				}
				c[cust_count].lat = lat;
				c[cust_count].longi = longi;
				c[cust_count].name = c_name;
				c[cust_count].total = total_weight;
				c[cust_count].loc = loc;
//				System.out.println("Total = " + total_weight);

/*
				sql = "delete from assigned_goods where abs(destination_lat - " + c[cust_count].lat + ") < 0.001 ";
				sql = sql + " && abs(destination_lon - " + c[cust_count].longi + ") < 0.001 ";
				sql = sql + " && customer_name = '" + c[cust_count].name + "'";  
//			    System.out.println(sql);
				stmt3.executeUpdate(sql);

				sql = "select * from assigned_goods where near = 1 ";
				result2 = stmt.executeQuery(sql);						
*/
			}


			
			for( j = 1 ; j <= cust_count ; j++ ){
				System.out.println(c[j].name + " " + c[j].lat + " " + c[j].longi + " " + c[j].total + " " + c[j].loc);
			}
			
			s_cust[] swept_cust = new s_cust[cust_count+10];
			for( j = 1 ; j <= cust_count ; j++ ){
				swept_cust[j] = new s_cust();
			}	
			
			System.out.print("Enter number of trucks : ");
			no_trucks = s.nextInt();
			
			System.out.print("Enter the capacity of each truck : ");
			truck_capacity = s.nextInt();
			
			for( j = 1 ; j <= no_trucks ; j++ ){
				t[j] = new truck(j, truck_capacity);
//				System.out.println("Truck num = " + t[j].num + " " + " Truck Capacity = " + t[j].capacity );
			}
			
/*
				if( total_weight < 100 ){
					sql = "select * from assigned_goods where customer_name = '" ;
					sql = sql + c_name + "'" ;
					sql = sql +  " && abs( destination_lat - " + lat +") < .001  &&  abs( destination_lon - " + longi + ") < 0.001 " ;
					System.out.println("Sql = " + sql); 
					ResultSet result3 = stmt2.executeQuery(sql);
					while( result3.next() ){
						sql = "update goods set quantity = (quantity - " + result3.getInt(2) + ") where godown_num = 1  && name = '" ;
						sql = sql + result3.getString(1) + "'";
						System.out.println(sql);
						stmt3.executeUpdate(sql);
					}
				}
*/
				System.out.println("Up");
				for( i = trla ; i > tlla && assigned_count <= cust_count; i = i - 0.0025){
					  	 for( j = 1 ; j <= cust_count ; j++ ){
					  	 	if( c[j].assign_flag != 1 ){
					  	 		c[j].prev_dist = c[j].cur_dist;
					  	 		c[j].cur_dist =  (c[j].longi - glo)*(i - gla)-(c[j].lat-gla)*(trlo-glo);	
					  	 		if(((c[j].prev_dist > 0 && c[j].cur_dist < 0 ) || ( c[j].prev_dist < 0 && c[j].cur_dist > 0) && c[j].longi > glo )){ 
										System.out.println("C[" + j + "] name =" + c[j].name + " total = " + c[j].total);
										c[j].assign_flag = 1;
										swept_cust[assigned_count].num = j;
										assigned_count++;
								}
					  	 	}
					  	 }
				}
		
				System.out.println("left");
				for( i = tllo ; i > bllo && assigned_count <= cust_count; i = i - 0.0025){
					  	 for( j = 1 ; j <= cust_count ; j++ ){
					  	 	if( c[j].assign_flag != 1 ){
					  	 		c[j].prev_dist = c[j].cur_dist;
					  	 		c[j].cur_dist =  (c[j].longi - glo)*(tlla-gla)-(c[j].lat-gla)*(i-glo);	
				  	 			if(((c[j].prev_dist > 0 && c[j].cur_dist < 0 ) || ( c[j].prev_dist < 0 && c[j].cur_dist > 0) && c[j].lat < gla )){ 
										System.out.println("C[" + j + "] name =" + c[j].name + " total = " + c[j].total); 
										c[j].assign_flag = 1;
										swept_cust[assigned_count].num = j;
										assigned_count++;
								}
							}
						}
				}
		
				System.out.println("Bottom");
				for( i = blla ; i < brla && assigned_count <= cust_count; i = i + 0.0025){
					  	 for( j = 1 ; j <= cust_count ; j++ ){
					  	 	if( c[j].assign_flag != 1 ){
					 	 	 	c[j].prev_dist = c[j].cur_dist;
					  	 		c[j].cur_dist =  (c[j].longi - glo)*(i - gla)-(c[j].lat-gla)*(bllo-glo);	
					  		 	if(((c[j].prev_dist > 0 && c[j].cur_dist < 0 ) || ( c[j].prev_dist < 0 && c[j].cur_dist > 0) && c[j].longi < glo  )){ 
										System.out.println("C[" + j + "] name =" + c[j].name + " total = " + c[j].total); 
										c[j].assign_flag = 1;
										swept_cust[assigned_count].num = j;
										assigned_count++;
								}

							}
						}
			    }
		
				System.out.println("right");
				for( i = brlo ; i < trlo && assigned_count <= cust_count; i = i + 0.0025){
					  	 for( j = 1 ; j <= cust_count ; j++ ){
					  	 	if( c[j].assign_flag != 1 ){
						  	 	c[j].prev_dist = c[j].cur_dist;
					  	 		c[j].cur_dist =  (c[j].longi - glo)*(brla - gla)-(c[j].lat-gla)*(i-glo);	
						  	 	if(((c[j].prev_dist > 0 && c[j].cur_dist < 0 ) || ( c[j].prev_dist < 0 && c[j].cur_dist > 0) && c[j].lat > gla)){ 
										System.out.println("C[" + j + "] name =" + c[j].name + " total = " + c[j].total); 
										c[j].assign_flag = 1;
										swept_cust[assigned_count].num = j;
										assigned_count++;
								}
							}

						}
				}
				
				for ( j = 1 ; j < assigned_count ; j++ ){
					System.out.print(" " + swept_cust[j].num + " " );
					System.out.println(" " + c[swept_cust[j].num].name);
				}
				System.out.println("");


				count = 1;
				cur_truck = 0;
				cur_weight = 0;
				System.out.println("Truck num " + (cur_truck+1));
				while( count < assigned_count ){
					for( j = 1 ; j < assigned_count ; j++ ){
					  if( swept_cust[j].assigned != 1){
						if( cur_weight + c[swept_cust[j].num].total <= truck_capacity ){
//							System.out.println("customer weight = " + c[swept_cust[j].num].total );
							cur_weight += c[swept_cust[j].num].total;
							swept_cust[j].assigned = 1;
							count++;
							System.out.print(" " + swept_cust[j].num + " " );
							System.out.println(" " + c[swept_cust[j].num].name + " Count = " + count );

							sql = "select * from assigned_goods where customer_name = '" ;
							sql = sql + c[swept_cust[j].num].name + "'" ;
							sql = sql +  " && abs( destination_lat - " + c[swept_cust[j].num].lat +") < .001  &&  abs( destination_lon - " + c[swept_cust[j].num].longi + ") < 0.001 " ;
//							System.out.println("Sql = " + sql); 
							ResultSet result3 = stmt2.executeQuery(sql);
							while( result3.next() ){
								sql = "update goods set quantity = (quantity - " + result3.getInt(2) + ") where godown_num = 1  && name = '" ;
								sql = sql + result3.getString(1) + "'";
//								System.out.println(sql);
								stmt3.executeUpdate(sql);
							}
								
							sql = "delete from assigned_goods where abs(destination_lat - " + c[swept_cust[j].num].lat + ") < 0.001 ";
							sql = sql + " && abs(destination_lon - " + c[swept_cust[j].num].longi + ") < 0.001 ";
							sql = sql + " && customer_name = '" + c[swept_cust[j].num].name + "'";  
//						    System.out.println(sql);
							stmt3.executeUpdate(sql);

							sql = "select * from assigned_goods where near = 1 ";
							result2 = stmt.executeQuery(sql);						
						}
						else{
							cur_truck = ((cur_truck+1) % no_trucks);
							System.out.println("Truck num " + (cur_truck+1));
							cur_weight = 0;
						}
					  }	
//						System.out.println("Current Weight = " + cur_weight + "\n") ;
					}
				}
	}
}	
