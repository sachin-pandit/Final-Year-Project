import java.util.*;
import java.sql.*;
import java.io.*;
import java.util.regex.*;

class godown_info{
	float lat, longi;
	godown_info(){
		lat = 0;
		longi = 0;
	}
}

public class its_customer {
 public static void main(String str[] ){
	try {
		customer_request(); 
 	}
 	catch( ClassNotFoundException e ){
 		System.out.println("Class not found Exception");
 	}
 	catch( SQLException e){
 		System.out.println("SQL Exception");
 	}
 	catch( IOException e){
 		System.out.println("IO Exception");
 	}
 }

 static void customer_request()  throws ClassNotFoundException , SQLException, IOException {
	 Class.forName("com.mysql.jdbc.Driver");
	 Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/godown","root","pandit");  
	 Statement stmt = con.createStatement();
	
	 Scanner s = new Scanner (System.in);

     String location, good_name, db_good_name, db_location, customer_name, sql, cmd, line, op = null ;
     int quantity = 0, db1_quantity = 0, db2_quantity = 0, avail_quantity = 0, list = 0;
     int choice = 0, i, temp, near = 0;
     float db_weight;
     boolean found = false;
     double cla, clo, time = 0, nearest = 0;
	 godown_info g_info[];
	 g_info = new godown_info[10];		

     
	 for ( i = 0 ; i <= 9 ; i++ )
		 g_info[i] = new godown_info();
		 
	 i = 1;
	 sql = "select * from godown_info";
	 ResultSet result = stmt.executeQuery(sql);
	 while( result.next() ){
	 	g_info[i].lat = result.getFloat(2);
	 	g_info[i].longi = result.getFloat(3)	;
	 	i++;	
	 }
	 i = 0;
	 Runtime run = Runtime.getRuntime() ;
	 Process pr;

	 
     do{
     	System.out.print("Enter your name : ");
        customer_name = s.next();
        System.out.print("Enter your location : ");
        location = s.next();
		System.out.print("Enter the latitude of your location : ");
		cla = s.nextFloat();
		System.out.print("Enter the longitude of your location : ");
		clo = s.nextFloat();
        System.out.print("Enter the good name : ");
        good_name = s.next();
        
        sql = "select * from goods where name = '" + good_name + "'";
		ResultSet result1 = stmt.executeQuery(sql);        
        db1_quantity = 0;
        list = 0;
        while( result1.next() ) {										//To fetch the total number of goods in godown( requested good)
        	if( good_name.equalsIgnoreCase(result1.getString(1))) 
	        	db1_quantity += result1.getInt(2) ;
				list += result1.getInt(4);
				list = list * 10;				
        }
		System.out.println("List = " + list );
		sql = "select * from assigned_goods where name = '" + good_name + "'";
		ResultSet result2 = stmt.executeQuery(sql);						//To fetch the number of goods already allocated
		db2_quantity = 0;
		while( result2.next() ){
			if( good_name.equalsIgnoreCase(result2.getString(1)))
				 db2_quantity += result2.getInt(2);						// so available = total - allocated
		}
		
		avail_quantity = 0;
		avail_quantity = db1_quantity - db2_quantity;
		System.out.println("There are " + avail_quantity + " " + good_name + " in godown ");

        System.out.print("Enter the quantity of good you want : ");
        quantity = s.nextInt();
        
        if( avail_quantity < quantity ){
        	System.out.println("There are insufficient goods in godown, plz enter within available limit ");
     	}
     	
     	else if( quantity == 0 ){
     		System.out.println("No order taken");
     	}
		
     	else{
    		sql = "select * from assigned_goods where customer_name = '" + customer_name + "'";
    		sql = sql + " && location_name = '" + location + "'";
			sql = sql + " && name = '" + good_name + "'";
			
			result2 = stmt.executeQuery(sql);
			
			if( !result2.next() ){
    			sql = "select weight from goods where name = '" + good_name + "' group by weight";
				System.out.println(sql);    			
    			result1 = stmt.executeQuery(sql);
    			result1.next();
    			db_weight = result1.getFloat(1);

 				sql = "insert into assigned_goods values ( " + "'" + good_name + "',";
	    		sql = sql + quantity + ",";
	    		sql = sql + db_weight + ",";
	    		sql = sql + cla + ",";
	    	    sql = sql + clo + ",";
	    	    sql = sql + "'" + customer_name + "',"  ;
	    	    sql = sql + "'" + location + "'" + "," ;
		    	sql = sql + list/10 + ",";
		    	sql = sql + "0";
				sql = sql + ")";
//				System.out.println(sql);
				stmt.executeUpdate(sql);
    		}
			else{
				db2_quantity = result2.getInt(2);
//   				System.out.println("db2_quantity = " + db2_quantity );
    			sql = "update assigned_goods set quantity = " + (db2_quantity + quantity) ;
    			sql = sql + " where customer_name = '" + customer_name + "'" ;
    			sql = sql + " && location_name = '" + location + "'" ;
    			sql = sql + " && name = '" + good_name + "'";
//    			System.out.println(sql);
    			stmt.executeUpdate(sql);
    			
    			sql = "select weight from goods where name = '" + good_name + "' group by weight";
				System.out.println(sql);    			
    			result1 = stmt.executeQuery(sql);
    			result1.next();
    			db_weight = result1.getFloat(1);
//    			System.out.println("weight = " + db_weight);
    			
    					
    			sql = "update assigned_goods set weight = " + db_weight ;     
    			sql = sql + " where customer_name = '" + customer_name + "'" ;	
    			sql = sql + " && location_name = '" + location + "'" ;
    			sql = sql + " && name = '" + good_name + "'";					
//				System.out.println(sql);
    			stmt.executeUpdate(sql);
			}	
			// calculate the nearest godown from customer's location
			temp = list ;
			temp = temp/10;
			nearest = 1000000;
			while( temp != 0){
				cmd = "./time.sh " + g_info[temp%10].lat + " " + g_info[temp%10].longi + " ";
				cmd = cmd + cla + " " + clo + " ";
			    System.out.println(cmd);
				pr = run.exec(cmd) ;
				BufferedReader buf = new BufferedReader( new InputStreamReader( pr.getInputStream() ) ) ;
				op = "";
				while ( (line = buf.readLine() ) != null ){
					 op += line ;
				}
				time = Float.parseFloat(op);
				System.out.println("Godown " + temp%10 + " Time = " + time);
				
				if( time < nearest ){
					nearest = time ;
					near = temp%10;
				}

				temp = temp/10;
			}
			
			// Add to the database the godown number which is nearer the customer location
			System.out.print("Nearest godown is " + near);
			sql = "update assigned_goods set near = " + near ;     
    		sql = sql + " where customer_name = '" + customer_name + "'" ;	
    		sql = sql + " && location_name = '" + location + "'" ;
    		sql = sql + " && name = '" + good_name + "'";	
				
//			System.out.println(sql);
    		stmt.executeUpdate(sql);

		}						

     	System.out.println("Do you want to go again ? yes -> 1, No -> 0 ");
     	choice = s.nextInt();
     	System.out.print("\n\n");

     }while( choice == 1);
	 
 }
}	
