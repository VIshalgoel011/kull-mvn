/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.kull.bean.JdbcBean;
import com.kull.common.Utils;
import com.kull.common.model.JormTable;
import java.sql.Connection;
import java.sql.SQLException;
import org.javaclub.jorm.Session;
import org.javaclub.jorm.jdbc.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author lin
 */
public class DBTest {
    
    Connection connDbmeta;
    Session session;
    JdbcBean jdbcBean;
    
    public DBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        connDbmeta=Utils.dbmeta();
        
        session=SessionFactory.newSession();
        jdbcBean=new JdbcBean(Utils.dbmeta());
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void dbmeta() throws SQLException, Exception {
         String sql="select * from dbmeta_conn ";
          //connDbmeta.createStatement().executeUpdate("create table people (name, occupation);");
           // connDbmeta.createStatement().executeQuery(sql);
          //JdbcBean.close(connDbmeta, null, null);
         
         //session.list(JormTable.dbmeta_conn.class, sql);
         jdbcBean.select(JormTable.dbmeta_conn.class, sql);
         JormTable.dbmeta_conn m=new JormTable().new dbmeta_conn();
         m.setName("test");
         m.setDescp("test");
         jdbcBean.insert(m);
         
     }
     
     
}
