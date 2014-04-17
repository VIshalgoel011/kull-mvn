import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Scanner;

import org.javaclub.jorm.Jorm;
import org.javaclub.jorm.Session;

import com.Console;





public class CreateTableApp {

	
	public static void main(String[] args) throws SQLException{
		
		 Session session=Jorm.getSession("ps-simple");
		Console.println(session.getConnection().getMetaData().getURL().toString());
		   Scanner scanner=new Scanner(System.in);
		   Console.println("请输入表名(对个表以,分隔):");
		  String tables=scanner.next();
		    String sqlPattern="CREATE TABLE {0}(_id serial NOT NULL,_code character varying(10),_type character varying(10)" +
		    		",_text character varying(200),_remark character varying(1000),_create_date date,_create_user_code character varying(10),_level integer," +
		    		"_string_0 character varying(500),_string_1 character varying(500),_string_2 character varying(500)" +
		    		",_int_0 integer,_int_1 integer,_int_2 integer" +
		    		",CONSTRAINT {0}_pkey PRIMARY KEY (_id))";
	        for(String table : tables.split(",")){
	    	String sql=MessageFormat.format(sqlPattern, table);
	    	Console.println(sql);
	    	session.executeUpdate(sql);
	        }
	    	session.close();
	   
	}
}
