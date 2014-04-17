package com.kull.test;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import org.junit.Test;

import com.Console;
import com.google.gson.Gson;


public class JmsTest implements MessageListener,ExceptionListener {

	ConnectionFactory connectionFactory;
	Connection connection;
	Session session;
	Destination destination;
	Topic topic;
	public JmsTest() throws JMSException{
		
		connectionFactory=new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,ActiveMQConnection.DEFAULT_BROKER_URL);
	    connection=connectionFactory.createConnection();
	    connection.setExceptionListener(this);
	    session=connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
	    destination=session.createQueue("hello");
	    topic=session.createTopic("hello-topic");
	}
	
	//@Test
	public void producer() throws Exception{
       MessageProducer producer=session.createProducer(destination);	
       
       producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
       
       //producer.setTimeToLive(1000);
       for (int i = 0; i < 1000; i++) {

           TextMessage message = session.createTextMessage("Message: " + i + " sent at: " + new Date());
           
           if (true) {
               String msg = message.getText();
               if (msg.length() > 50) {
                   msg = msg.substring(0, 50) + "...";
               }
               System.out.println("[" + this.getClass().getName() + "] Sending message: '" + msg + "'");
           }

           producer.send(message);

           if (true) {
               System.out.println("[" + this.getClass().getName() + "] Committing " + 1000 + " messages");
               session.commit();
           }
           //Thread.sleep(10);
       }
      
	}
	
	//@Test
	public void consumer() throws Exception{
		connection.start();
		MessageConsumer consumer=session.createConsumer(destination);
		consumer.setMessageListener(this);
	    
		while(true){}
		
	}

	//@Override
	public void onMessage(Message msg) {
		// TODO Auto-generated method stub
		
		Console.println(new Gson().toJson(msg));
		try {
			msg.acknowledge();
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//@Override
	public void onException(JMSException arg0) {
		// TODO Auto-generated method stub
		Console.println(new Gson().toJson(arg0));
	}
	
	
}
