package generic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCDemo 
{
	public static void main(String[] args) throws SQLException 
	{
		String url="jdbc:mysql://localhost:3306/logic";
		//open the database
		Connection c=DriverManager.getConnection(url, "root", "");
		
		String sql="Select * from xfcuj_customer";
		ResultSet res = c.createStatement().executeQuery(sql);
		
		int cc=res.getMetaData().getColumnCount();
		while(res.next())
		{
			for(int i=1;i<=cc;i++)
			{
				String v=res.getString(i);
				System.out.println(v);
			}
			System.out.println("----------------------");
		}
		
		//to close the database
		c.close();
		System.out.println("Done");
	}
	
}
