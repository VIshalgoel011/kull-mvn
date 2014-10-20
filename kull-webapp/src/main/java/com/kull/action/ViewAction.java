package com.kull.action;



import com.kull.bean.JdbcBean;
import com.kull.web.struts.DSActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.javaclub.jorm.Jorm;
import org.javaclub.jorm.jdbc.sql.Dialect;
import org.javaclub.jorm.jdbc.sql.impl.MySQLDialect;





public abstract class ViewAction extends DSActionSupport implements Preparable{

    public void prepare() throws Exception {
       this.connection=Jorm.getConnection();
    }

    @Override
    public String execute() throws Exception {
        
        String re= super.execute(); //To change body of generated methods, choose Tools | Templates.
        JdbcBean.close(connection, null, null);
        return re;
    }

    
	
   protected static Dialect dialect=new MySQLDialect();


    @Override
    protected String createPageSql(String dataSql, int start, int limit) {
        return dialect.pageable(dataSql, start, limit);
    }
	
	
    
	
	

	

	


	

	
}
