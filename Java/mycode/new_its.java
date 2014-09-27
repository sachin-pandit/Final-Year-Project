import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;


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


public class its implements ActionListener {
	
 public static void main(String str[] ){
	try{
		get_goods( );	// we have to make this a thread
	}
	catch( SQLException e ){
		System.out.println(" Connection could not be established ");
	}
	catch( ClassNotFoundException e ){
		System.out.println(" Class not found ");
	}
}
	
 static void get_goods( ) throws ClassNotFoundException, SQLException {
	String location, good_name;
	int quantity = 0;

//Interface
	JTextField JName, JQuantity, JLocation;
	JButton button;

	Frame frame = new Frame("Database for godown");
	
	frame.setLayout(new Layout());
	frame.setSize(500,500);
	frame.setVisible(true);
	
	JName 	  = new JTextField(30);
	JQuantity = new JTextField(30);
	JLocation = new JTextField(30);
	
	button = new JButton("Sumbit");
	button.addActionListener(this);
	
	Class.forName("com.mysql.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/godown","root","pandit");  
	Statement stmt = con.createStatement();

	Scanner s = new Scanner (System.in);
	String sql;
			
	sql = "delete from goods";
	stmt.executeUpdate(sql);
	// let us take location in terms of name for now, later we can think about lattitude and longitude
	System.out.print("Enter your location : ");
	location = s.next();
	System.out.print("Enter Number of types of goods you want to purchase : ");
	int n = s.nextInt();
			
	for(int i = 0 ; i < n ; i++ ){
		frame.add(JName);
		frame.add(JQuantity);
		frame.add(JLocation);
		frame.add(button);
		
		public void ActionPerformed( ActionEvent e){
			good_name = textField.getText();
			quantity = textField.getText();
		}
			
/*
		System.out.println("Enter the information for good " + (i+1) );
		System.out.print("Enter the good name : ");
		good_name = s.next();
		System.out.print("Enter the Quantity : " );
		quantity = s.nextInt();
				
	    	sql = "insert into goods values ( '" + good_name + "','" + quantity + "','" + location + "')"  ;
		stmt.executeUpdate(sql); 
*/					
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
	
	sql = "update goods set quantity=" + (quantity+100) + " where name='pen'";
	stmt.executeUpdate(sql);
	 
 }		
			
}
