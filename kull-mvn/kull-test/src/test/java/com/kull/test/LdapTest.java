package com.kull.test;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import net.sourceforge.jtds.jdbc.Driver;

import org.junit.Test;

import com.kull.bean.JdbcBean;

public class LdapTest {
     
	@Test
	public void decodePwd(){
		Connection conn = null;
		PreparedStatement ps = null;
	    ResultSet rs = null;
	    String givenname = ""; String cn = ""; String x = ""; String m = "";
	    JdbcBean jdbcBean=null;
	    String result = "";
	    FileWriter resultFile = null;
	    try
	    {
	      System.out.println("准备连接数据库<br>");

	      Runtime exe = Runtime.getRuntime();
	      String cmd = "";

	      Class.forName(Driver.class.getName());
	      //conn = DriverManager.getConnection("jdbc:mysql://172.19.7.87:5188/asdb?characterEncoding=GBK", "xoops_root", "654321");
	      conn = DriverManager.getConnection("jdbc:jtds:sqlserver://172.19.7.90/asdb_87", "sa", "7ujm8ik,");
          jdbcBean=new JdbcBean(conn);
	      System.out.println("连接数据库成功<br>");

	      String filePath = "c:/1.bat";
	      filePath = filePath.toString();
	      File myFilePath = new File(filePath);
	      if (!myFilePath.exists()) {
	        myFilePath.createNewFile();
	      }
	      resultFile = new FileWriter(myFilePath);
	      PrintWriter myFile = new PrintWriter(resultFile);

	      //System.out.println("正在准备同步脚本" + asf.getCurrentDate() + " " + asf.getCurrentTime() + "<br>");

	      String sql = "SELECT id,NAME,loginid,clientDogSysUi,identitycard FROM k_user ";
	      ps = conn.prepareStatement(sql);
	      rs = ps.executeQuery();

	      String passwd = "";
	      EnCodeUtils des = new EnCodeUtils();
	      String strhint = "";
	      int i = 0;
	      while (rs.next())
	      {
	        i++;
            int eid=rs.getInt("id");
	        givenname = rs.getString("NAME");
	        cn = rs.getString("loginid");
	        if ((!"".equals(givenname)) && (givenname != null)) {
	          x = givenname.substring(0, 1);
	          m = givenname.substring(1);
	        }

	        passwd = rs.getString("clientDogSysUi");
	        if ((passwd == null) || ("".equals(passwd))) {
	          passwd = rs.getString("identitycard").toUpperCase();
	          if (passwd.length() > 6) {
	            passwd = passwd.substring(passwd.length() - 6);
	            strhint = "身份证后6位";
	          } else {
	            strhint = "没修改密码或无法解密，且身份证位数小于6，用缺省1234567";
	            passwd = "1234567";
	          }
	        } else {
	          passwd = des.decodePWS(passwd);
	          if ((passwd == null) || ("".equals(passwd)) || ("-1".equals(passwd))) {
	            passwd = rs.getString("identitycard").toUpperCase();
	            if (passwd.length() > 6) {
	              passwd = passwd.substring(passwd.length() - 6);
	              strhint = "无法解密，身份证后6位";
	            } else {
	              passwd = "1234567";
	              strhint = "无法解密，没修改密码或无法解密，且身份证位数小于6，用缺省1234567";
	            }
	          } else {
	            strhint = "用设置的新密码";
	          }
	        }
	        System.out.println("第" + i + "人：" + givenname + "|" + cn + "|" + rs.getString("identitycard") + "|" + strhint + "    "+passwd);
            jdbcBean.executeUpdate("update k_user set real_pwd=? where id=?", passwd,eid);
	     
	      }

	      if (resultFile != null) {
	        resultFile.close();
	      }

	      //System.out.println("同步结束" + asf.getCurrentDate() + " " + asf.getCurrentTime() + ",一共同步" + i + "人");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      try {
	        if (resultFile != null)
	          resultFile.close();
	      }
	      catch (Exception localException2) {
	      }
	      //DbUtil.close(rs);
	      //DbUtil.close(ps);
	    }
	}
	
	public class EnCodeUtils
	{
	  public String uCode(String s)
	  {
	    int i = s.length();
	    String s2 = "";
	    if (i == 0)
	      return "";
	    for (int k = 0; k < i; k++)
	    {
	      String s1 = s.substring(k, k + 1);
	      int j = s1.hashCode();
	      if (j > 255)
	        s2 = s2 + String.valueOf((char)(j >> 8)) + String.valueOf((char)(j & 0xFF));
	      else {
	        s2 = s2 + String.valueOf('\000') + s1;
	      }
	    }
	    return s2;
	  }

	  public String uDCode(String s)
	  {
	    int i = s.length();
	    if (i % 2 != 0)
	      return "-1";
	    String s1 = "";
	    for (int j = 0; j < i / 2; j++) {
	      s1 = s1 + String.valueOf((char)((s.charAt(j * 2) << '\b') + s.charAt(j * 2 + 1)));
	    }
	    return s1;
	  }

	  public String encodePWS(String s)
	  {
	    if (s == null)
	      return null;
	    int i = s.length();
	    if (i == 0)
	      return "";
	    s = uCode(s);
	    i = s.length();
	    String s4 = "";
	    String s5 = "";
	    Random random = new Random();
	    boolean flag = false;
	    for (int j = 0; j < i; j++)
	    {
	      String s1 = Integer.toHexString(s.substring(j, j + 1).hashCode() >> 4);
	      String s2 = Integer.toHexString(s.substring(j, j + 1).hashCode() & 0xF);
	      String s3 = Integer.toString(random.nextInt());
	      s3 = s3.substring(s3.length() - 1, s3.length());
	      if (j % 3 == 0) {
	        s4 = s4 + s3 + s1 + s2;
	      }
	      else if (j % 3 == 1)
	        s4 = s4 + s1 + s3 + s2;
	      else {
	        s4 = s4 + s1 + s2 + s3;
	      }
	    }
	    return s4;
	  }

	  public String decodePWS(String s)
	  {
	    if (s == null)
	      return null;
	    int i = s.length();
	    if (i == 0)
	      return "";
	    String s1 = "";
	    String s2 = "";
	    String s3 = "";
	    s2 = s;
	    if (s2.length() % 3 != 0)
	      return "-1";
	    for (int j = 0; j < s2.length() / 3; j++) {
	      if (j % 3 == 0) {
	        s1 = s1 + s2.substring(j * 3 + 1, (j + 1) * 3);
	      }
	      else if (j % 3 == 1)
	        s1 = s1 + s2.substring(j * 3, j * 3 + 1) + s2.substring(j * 3 + 2, (j + 1) * 3);
	      else
	        s1 = s1 + s2.substring(j * 3, j * 3 + 2);
	    }
	    for (int k = 0; k < s1.length() / 2; k++) {
	      s3 = s3 + String.valueOf((char)((Char2int(s1.charAt(k * 2)) << 4) + Char2int(s1.charAt(k * 2 + 1))));
	    }
	    s3 = uDCode(s3);
	    return s3;
	  }

	  public int Char2int(char c)
	  {
	    if ((c >= '0') && (c <= '9'))
	      return c - '0';
	    if ((c >= 'a') && (c <= 'f')) {
	      return c - 'a' + 10;
	    }
	    return -1;
	  }
	}
}
