import java.util.*;
import java.sql.*;

/*
class good{
	String name;
	int quantity;
	float weight;
	class destination{
		float lat;
		float lon;
	}
	boolean loaded=false;
}

class truck{
	int truck_num;
	int max_load;
	boolean multi_dest = false;
	boolean full = false;
	LinkedList<good> truck_goods;
}
*/

public class its {
	
	
 public static void main(String str[] ){
 

	// Just for testing
	int choice ;
	
	System.out.println("1) To Enter New goods ");
	System.out.println("2) Customer Request ");
	System.out.println("Enter you choice : ");
	
	Scanner s = new Scanner(System.in);	
	choice = s.nextInt();
	switch ( choice ){
		case 1 : 
				  try{	
					 get_goods() ; break;
				  }
				  catch ( ClassNotFoundException e){
				  	System.out.println("Class not found in get_goods");
				  }
				  catch ( SQLException e){
				  	System.out.println(" SQL Exception in get_goods");
				  }
		case 2 : 
				  try{	
					customer_request(); break;
				  }
				  catch ( ClassNotFoundException e){
				  	System.out.println("Class not found in customer request");
				  }
				  catch ( SQLException e){
				  	System.out.println(" SQL Exception in customer request");
				  }

	}
	
 }

 static void get_goods() throws ClassNotFoundException , SQLException {
	 Class.forName("com.mysql.jdbc.Driver");
	 Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/godown","root","pandit");  
	 Statement stmt = con.createStatement();
	
	 Scanner s = new Scanner (System.in);

 	String good_name , db_good_name ;
 	int quantity , db_quantity, choice = 0;
 	
 	do{
 		String sql ;
 		
 		System.out.print("Enter the good name : ");
 		good_name = s.next();
 		System.out.print("Enter the quantity : ");
 		quantity = s.nextInt();
 		
 		sql = "select * from goods where name = " + good_name ;
 		ResultSet result = stmt.executeUpdate(sql);
 		
 		if( !result.next() ) {
		    sql = "insert into goods values ( '" + good_name + "','" + quantity + "')"  ;
			stmt.executeUpdate(sql); 
		}
		else{
		   int k = 0;
		   do {
		   		System.out.println("k =" + k);
				db_good_name = result.getString(1);
				System.out.println("db_good name =" + db_good_name + "," + "good_name = " + good_name);
				db_quantity = result.getInt(2);		
	
				if( db_good_name.equals(good_name) ){
					System.out.println("Inside test");
					found = true;				
				}
				k++;
		   }while( result.next() && found != true);
		   
		  
		   System.out.println("found = " + found );
		   
		   if( found == true ){
		   		sql="update goods set quantity="+(quantity+db_quantity)+" where name='"+db_good_name+"'";
				stmt.executeUpdate(sql);
		   }
		   else{
		   		sql = "insert into goods values ( '" + good_name + "','" + quantity + "')" ;
		   		stmt.executeUpdate(sql);
		   }		   		
		 } 
		 
		 System.out.print("Do you want to go again ? ( yes -> 1 , no -> 0 ) ");
		 choice = s.nextInt();
 
 	
 	}while(choice);		
 	
 }
 
	
 static void customer_request()  throws ClassNotFoundException , SQLException {
	 Class.forName("com.mysql.jdbc.Driver");
	 Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/godown","root","pandit");  
	 Statement stmt = con.createStatement();
	
	 Scanner s = new Scanner (System.in);

     String location, good_name, db_good_name, db_location;
     int quantity = 0, db_quantity;
     int loop = 0;

	
    do{
		String sql;
			
    
		System.out.print("Enter your location : ");
		location = s.next();
		System.out.print("Enter Number of types of goods you want to purchase : ");
		int n = s.nextInt();
		boolean found = false;
			
	  for(int i = 0 ; i < n ; i++ ){
		System.out.println("Enter the information for good " + (i+1) );
		System.out.print("Enter the good name : ");
		good_name = s.next();
		System.out.print("The quantity present is : ");
		sql = "select * from goods where name = '" + good_name + "'";
		ResultSet result = stmt.executeQuery(sql);
		db_quantity = result.getInt( );
		

		System.out.print("Enter the Quantity : " );
		quantity = s.nextInt();
// Done till here
		
		sql = "select * from goods where name = '" + good_name + "'";
		ResultSet result = stmt.executeQuery(sql);
		
		if( !result.next() ) {
		    sql = "insert into goods values ( '" + good_name + "','" + quantity + "','" + location + "')"  ;
			stmt.executeUpdate(sql); 
		}
		else{
		   int k = 0;
		   do {
		   		System.out.println("k =" + k);
				db_good_name = result.getString(1);
				System.out.println("db_good name =" + db_good_name + "," + "good_name = " + good_name);
	
				db_location = result.getString(3);
				System.out.println("db_location =" + db_location + "," + "location =" + location );
				db_quantity = result.getInt(2);		
					
				if( db_location.equals(location) ){
					System.out.println("Inside test");
					found = true;				
				}
				k++;
		   }while( result.next() && found != true);
		   
		  
		   System.out.println("found = " + found );
		   
		   if( found == true ){
		   		sql="update goods set quantity="+(quantity+db_quantity)+" where name='"+db_good_name+"' && location='"+location + "'" ;
				stmt.executeUpdate(sql);
		   }
		   else{
		   		sql = "insert into goods values ( '" + good_name + "','" + quantity + "','" + location + "')" ;
		   		stmt.executeUpdate(sql);
		   }		   		
		 } 
					
		  System.out.println("Information : " + location + "," + good_name + "," +  quantity + "." );
	   }	
	
		sql = "select * from goods";
		ResultSet result = stmt.executeQuery( sql );
		if( !result.next() ){
			System.out.println("Empty");
		}
		else{
			do{
				System.out.println( result.getString(1) + "\t" +  result.getInt(2) + "\t" +  result.getString(3 ));
			}while( result.next() );
		}  
	
	
		System.out.println("Another customer ? ( yes -> 1 , no -> 0 ) ");
		loop = s.nextInt();
    }while( loop == 1 );	 
 }		
			
}
