import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

import java.io.*;
import java.net.*;

public class Server{
	public static void main(String[] args) throws Exception{
		Properties props = new Properties();
		props.put("metadata.broker.list", "localhost:9092");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
	  	props.put("producer.type", "sync");
		props.put("retry.backoff.ms", "1000");

		props.put("request.required.acks", "1");
																						 
		ProducerConfig config = new ProducerConfig(props);
		Producer<String, String> producer = new Producer<String, String>(config);
		
		ServerSocket serverSocket = new ServerSocket(5000);
		System.out.println(
				"Listening on: " + 
				serverSocket.getInetAddress().getLocalHost().getHostAddress()+
				":"+5000);
		System.out.println("Awaiting connection");
		Socket server = serverSocket.accept();		
		System.out.println("Connection accepted");

		BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		while(true){
				System.out.println("reading messages");
				String msg = in.readLine();
				System.out.println(msg);
				KeyedMessage<String, String> data = 
					            		   new KeyedMessage<String, String>("data", "localhost", msg);
				producer.send(data);
		}	
		//serverSocket.close();
		//server.close();
		//in.close();
	}
}

