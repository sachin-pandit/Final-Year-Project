import java.util.*;
import java.sql.*;

public class its_goods {
	
	
 public static void main(String str[] ){
	try {
		get_goods(); 
 	}
 	catch( ClassNotFoundException e ){
 		System.out.println("Class not found Exception");
 	}
 	catch( SQLException e){
 		System.out.println("SQL Exception");
 	}
 }

 static void get_goods() throws ClassNotFoundException , SQLException {
	
	 Class.forName("com.mysql.jdbc.Driver");
	 Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/godown","root","pandit");  
	 Statement stmt = con.createStatement();
	
	 Scanner s = new Scanner (System.in);

 	String good_name , db_good_name ;
 	int quantity , db_quantity, choice = 0, db_godown_num, godown_num;
 	float db_weight, weight;
 	
 	do{
 		String sql ;
 		
 		System.out.print("Enter the good name : ");
 		good_name = s.next();
 		System.out.print("Enter the quantity : ");
 		quantity = s.nextInt();
 		System.out.print("Enter the weight of goods : ");
 		weight = s.nextFloat();
		System.out.print("Enter the go-down number : ");
		godown_num = s.nextInt(); 
 		
 		sql = "select * from goods" ; //  where name = '" + good_name + "' && godown_num = " + godown_num ;
 		ResultSet result = stmt.executeQuery(sql);
 		
 		if( !result.next() ) {
			System.out.println("In If");
		    sql = "insert into goods values ( '" + good_name + "','" + quantity + "','" + weight + "','" + godown_num + "')"  ;
			stmt.executeUpdate(sql); 
		}
		else{
			System.out.println("In else");
			db_weight = result.getFloat(3);
			db_quantity = result.getInt(2);
			db_godown_num = result.getInt(4);
			db_good_name = result.getString(1);
			
			if( db_godown_num == godown_num && db_good_name.equals(good_name) ) {
				sql = "update goods set quantity=" + (quantity + db_quantity) + " where name = '" + good_name + "'";
				sql = sql + " && godown_num = " + godown_num ;
//				System.out.println(sql);
				stmt.executeUpdate(sql);
		
			}
			else{
			    sql = "insert into goods values ( '" + good_name + "','" + quantity + "','" + weight + "','" + godown_num + "')" ;
				stmt.executeUpdate(sql); 
			}			
		}	

		 System.out.print("Do you want to go again ? ( yes -> 1 , no -> 0 ) ");
		 choice = s.nextInt();
 		
		 System.out.print("\n\n");
 		
 	}while(choice == 1);		
 	
 }
			
}
