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
		Socket server = serverSocket.accept();
		
		DataInputStream in = new DataInputStream(server.getInputStream());

		while(true){
			try{
				String msg = "received: " + in.readInt();
				KeyedMessage<String, String> data = 
					            		   new KeyedMessage<String, String>("data", "localhost", msg);
				producer.send(data);
			}
			catch(EOFException e){
			}
		}	
		//serverSocket.close();
		//server.close();
		//in.close();
	}
}

