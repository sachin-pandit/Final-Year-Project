import java.util.*;
import java.sql.*;
import java.io.*;

class godown_info{
	float lat, longi;
	godown_info(){
		lat = 0;
		longi = 0;
	}
}

public class time_matrix{
	
	public static void main( String str[] ){
		try{
			get_matrix();
		}
		catch( ClassNotFoundException e){
			System.out.println("Class not found exception");
		}
		catch( SQLException e){
			System.out.println("SQL Exception");
		}
	}
	
	static void get_matrix() throws ClassNotFoundException, SQLException{
		 Class.forName("com.mysql.jdbc.Driver");
		 Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/godown","root","pandit");  
		 Statement stmt = con.createStatement();
		 int i = 1, j = 1;
		 String sql;
		 Scanner s = new Scanner (System.in);
		 
		 godown_info g_info[];
		 g_info = new godown_info[10];		

		 
//		 Runtime r = Runtime.getRuntime();	
		
		 for ( i = 0 ; i <= 9 ; i++ )
		 	g_info[i] = new godown_info();
		 
		 i = 1;
		 sql = "select * from godown_info";
		 ResultSet result = stmt.executeQuery(sql);
		 while( result.next() ){
		 	g_info[i].lat = result.getFloat(2);
		 	g_info[i].longi = result.getFloat(3)	;
		 	System.out.println( "i = " + i + " Latitude = " + g_info[i].lat + " Longitude = " + g_info[i].longi );
		 	i++;	
		 }
		 
		 try{
		 	String cmd = "cp temp.txt temp1.txt" ;
			Runtime run = Runtime.getRuntime() ;
			Process pr = run.exec(cmd) ;
			pr.waitFor() ;

/*
			BufferedReader buf = new BufferedReader( new InputStreamReader( pr.getInputStream() ) ) ;
			while ( (line = buf.readLine() ) != null ){
				System.out.println(line) ;
			}
*/
			String line;

		    System.out.println("Before script");
			Process p = Runtime.getRuntime().exec("cat temp.txt > temp1.txt"); 		 	
			for( i = 1 ; i < 8 ; i++ ){
		 		for( j = i ; j < 8 ; j++ ){
		 		     if( i != j ){
					 	cmd = "./extract.sh " + g_info[i].lat + " " + g_info[i].longi + " ";
					 	cmd = cmd + g_info[j].lat + " " + g_info[j].longi + " ";
//						System.out.println(cmd);
						pr = run.exec(cmd) ;
						System.out.println("Time and Distance from godown " + i + " to " + j );
						BufferedReader buf = new BufferedReader( new InputStreamReader( pr.getInputStream() ) ) ;
						while ( (line = buf.readLine() ) != null ){
							System.out.println(line) ;
						}
					 }
				}
			}	
		}
		catch( Exception e){
			System.out.println("IO Exception");
		}
	}
}

