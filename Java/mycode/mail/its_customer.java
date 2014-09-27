import java.util.*;
import java.sql.*;

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
 }

 static void customer_request()  throws ClassNotFoundException , SQLException {
	 Class.forName("com.mysql.jdbc.Driver");
	 Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/godown","root","pandit");  
	 Statement stmt = con.createStatement();
	
	 Scanner s = new Scanner (System.in);

     String location, good_name, db_good_name, db_location, customer_name, sql;
     int quantity = 0, db_quantity = 0;
     int choice = 0;
     
     do{
     	System.out.print("Enter your name : ");
        customer_name = s.next();
        System.out.print("Enter your location : ");
        location = s.next();
        System.out.print("Enter the good name : ");
        good_name = s.next();
        
        sql = "select * from goods where name = '" + good_name + "'" ;
		ResultSet result = stmt.executeQuery(sql);        
        while( result.next() ) {
        	if( good_name.equals(result.getString(1))) 
	        	db_quantity += 	db_quantity = result.getInt(2) ;
        }
        System.out.println("There are " + db_quantity + " " + good_name + " in godown ");
        
        System.out.print("Enter the quantity of good you want : ");
        quantity = s.nextInt();
        
        if( db_quantity < quantity ){
        	System.out.println("There are insufficient goods in godown, plz enter within available limit ");
     	}
     	
     	else{
     		System.out.println("In Else");
    		sql = "select * from assigned_goods where customer_name = '" + customer_name + "'";
    		sql = sql + " && location_name = '" + location + "'";
    		result = stmt.executeQuery(sql);
    		
    		if( !result.next()){
    			System.out.println("In Else IF");
 				sql = "insert into assigned_goods values ( " + "'" + good_name + "',";
	    		sql = sql + quantity + ",";
	    		sql = sql + "0" + ",";
	    		sql = sql + "0.0" + ",";
	    	    sql = sql + "0.0" + ",";
	    	    sql = sql + "'" + customer_name + "'" ;
	    	    sql = sql + "'" + location + "'" ;
				sql = sql + ")";
				System.out.println(sql);
//				stmt.executeUpdate(sql);
    		}
    		else{
    			System.out.println("In Else Else");
	    		do{
    				db_good_name = result.getString(1);
    				if( db_good_name.equals( good_name) ) {
    					System.out.println("In Else Else IF");
    					sql = "update assigned_goods set quantity = " + (db_quantity + quantity) ;
    					sql = sql + " where customer_name = '" + customer_name + "'" ;
    					sql = sql + " && location_name = '" + location + "'" ;
    					sql = sql + " && good = '" + good_name + "'";
    					System.out.println(sql);
//    					stmt.executeUpdate(sql);
    					
    					sql = "update assigned_goods set weight = 0"  ;     // need to take weight of goods from goods table
    					sql = sql + " where customer_name = '" + customer_name + "'" ;	// Not yet completed
    					sql = sql + " && location_name = '" + location + "'" ;
    					sql = sql + " && good = '" + good_name + "'";					
						System.out.println(sql);
//    					stmt.executeUpdate(sql);
	    			}	
    				else{
    					System.out.println("In Else Else Else");
	    				sql = "insert into assigned_goods values ( " + "'" + good_name + "',";
	    				sql = sql + quantity + ",";
	    				sql = sql + "0" + ",";
	    				sql = sql + "0.0" + ",";
	    			    sql = sql + "0.0" + ",";
	    			    sql = sql + "'" + customer_name + "'" ;
	    			    sql = sql + "'" + location + "'" ;
	    			    sql = sql + ")" ;
						System.out.println(sql);
//						stmt.executeUpdate(sql);
	     			}
	     		}while( result.next() );
			}     		
     	}
     	System.out.println("Do you want to go again ? yes -> 1, No -> 0 ");
     	choice = s.nextInt();
     }while( choice == 1);
	 
 }
}	
