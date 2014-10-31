/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common.console;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.MessageFormat;
import com.kull.orm.Database;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
/**
 *
 * @author lin
 */
public class JormConsole extends Console{
    
    protected Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void customConnection() throws IOException{
        println("custom the connection!");
        String driver = prompt("dirver class:")
                ,url=prompt("url:")
                ,user=prompt("user:")
                ,pwd=prompt("pwd");
                ;
        try{
            Class.forName(driver);
            this.connection=DriverManager.getConnection(url,user,pwd);
            println(MessageFormat.format("{0}",this.connection.getClientInfo()));
        }catch(Exception ex){
            println(ex.getMessage());
        }
    }
    
    public static void main(String[] args) throws IOException, Exception {
        
        
        
         JormConsole console=new JormConsole();
         console.welcome();
         Options options=new Options();
         options.addOption("p",false,"");
         
         CommandLineParser parser=new BasicParser();
         CommandLine cmd=parser.parse(options, args);
         
         //console.customConnection();
         console.setConnection(Database.createSqllteConnection("I:\\ws-nb\\kull-mvn\\kull-common-webapp\\src\\main\\resources\\kull.db3"));
    
    }

    @Override
    public void welcome() {
        println("jorm 实体类生生成器");
    }
    
    
    
}
