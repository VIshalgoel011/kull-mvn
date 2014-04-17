import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import org.javaclub.jorm.Jorm;

import com.Console;
import com.kull.bean.JdbcBean;




public class MainApp {

	
	public static void main(String[] args) throws Exception{
		
	   Connection connection=Jorm.getConnection("ps-simple");
	   JdbcBean jdbcBean=new JdbcBean(connection);
	   Console.println("请输入表名:");
	   Scanner scanner=new Scanner(System.in);
	   String tableName=scanner.next();
	   Console.println(jdbcBean.tableRefClassConext(tableName));
	}
}
