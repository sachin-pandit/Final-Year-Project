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
 		
 		sql = "select weight from goods where name = '" + good_name + "' group by weight";
 		ResultSet result = stmt.executeQuery(sql);
 		if( !result.next () ){									//If weight of good is already stored in database then  fetch it
	 		System.out.print("Enter the weight of good : ");
 			weight = s.nextFloat();
		}
		else{
			weight = result.getFloat(1);
			System.out.println("The weight of the good is " + weight );
		}
			
		System.out.print("Enter the go-down number : ");
		godown_num = s.nextInt(); 
 		
 		sql = "select * from goods where name = '" + good_name + "'" ;		// To avoid redundancy , and also to meet the requirement
 		sql = sql + " && godown_num = " + godown_num  ;
 		result = stmt.executeQuery(sql);
 		
 		if( !result.next() ) {
		    sql = "insert into goods values ( '" + good_name + "','" + quantity + "','" + weight + "','" + godown_num + "')"  ;
			stmt.executeUpdate(sql); 
		}
		else{
			db_weight = result.getFloat(3);
			db_quantity = result.getInt(2);
			db_godown_num = result.getInt(4);
			db_good_name = result.getString(1);
			
			sql = "update goods set quantity=" + (quantity + db_quantity) + " where name = '" + good_name + "'";
			sql = sql + " && godown_num = " + godown_num ;
//			System.out.println(sql);
			stmt.executeUpdate(sql);
		
		}

		 System.out.print("Do you want to go again ? ( yes -> 1 , no -> 0 ) ");
		 choice = s.nextInt();
 		
		 System.out.print("\n\n");
 		
 	}while(choice == 1);		
 	
 }
			
}
