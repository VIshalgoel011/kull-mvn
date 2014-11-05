package com.kull.orm.dialect;

import com.kull.ObjectHelper;
import com.kull.StringHelper;
import com.kull.able.Ormable;
import com.kull.orm.Database;
import com.kull.orm.annotation.OrmTable;
import com.kull.orm.mybatis.MapperFactory;
import com.kull.orm.mybatis.MapperTemplate;

import com.kull.able.Resultable;
import com.kull.orm.Session;
import static com.kull.orm.Session.COLTYPE_REF_CLASS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.dbutils.QueryRunner;

public abstract class Dialect {

    protected QueryRunner qr = new QueryRunner();

    public Database database = Database.unknow;
    public String dateRegexp = "";

    final public static String DATE_REGEXP_ORACLE = "YYYY-MM-DD";
    final public static String DATE_REGEXP_MYSQL = "%Y-%m-%d";

    final public static String METHOD_STR_TO_DATE_ORACLE = "to_date";
    final public static String METHOD_STR_TO_DATE_MYSQL = "str_to_date";

    public boolean supportsLimit() {
        return false;
    }

    public boolean supportsLimitOffset() {
        return supportsLimit();
    }

    public String getLimitString(String sql, int offset, int limit) {
        return getLimitString(sql, offset, Integer.toString(offset), limit, Integer.toString(limit));
    }

    public abstract String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder);

    public abstract Set<String> showTables(Connection conn) throws SQLException;

    public abstract Set<String> showViews(Connection conn) throws SQLException;

    public List<Column> showColumns(Connection conn, String table) throws SQLException {
        List<Column> cols = new ArrayList<Column>();
        String pattern = "select * from {0} where 1=2";
        String querySql = MessageFormat.format(pattern, table);
        PreparedStatement ps = conn.prepareStatement(querySql);
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData mtdata = rs.getMetaData();
        for (int i = 1; i <= mtdata.getColumnCount(); i++) {
            Column col=new Column();
            col.from(mtdata, i);
            cols.add(col);
        }
        Session.close(null, ps, rs);
        return cols;
    }

    public String dbmlScript(Connection conn, String className) throws SQLException, Exception {
       
        String packg = className.substring(0, className.lastIndexOf(".")),
                sname = className.substring(packg.length() + 1, className.length() - 1);

        StringBuffer context = new StringBuffer("");

        Class[] importClss = {List.class, Date.class, Timestamp.class, Ormable.class, Resultable.class, Set.class, ObjectHelper.class, OrmTable.class};
        context
                .append("package " + packg + ";").append(StringHelper.ln(2));
        for (Class importCls : importClss) {
            context.append("import " + importCls.getName() + ";").append(StringHelper.ln());
        }

        context.append(" public class ").append(sname).append(" {  \n");

        for (String table : showTables(conn)) {
            List<Column> cols=showColumns(conn, table);
            
            context.append("\n\n\n\t").append("public class "+table+" { \n\n");
            for(Column col :cols){
              context.append("\n\n\t\t").append(col.protectedScript());
            }
            for(Column col :cols){
              context.append("\n\n\t\t").append(col.getterScript())
                      .append("\n\t\t").append(col.setterScript());
            }
            context.append("\n\n \t } // end "+table );
            
        }
        context.append("\n\n } //end "+sname);
        return context.toString();
    }

    ;
    
    
    public abstract String methodStrToDate(String regexp);

    
}
