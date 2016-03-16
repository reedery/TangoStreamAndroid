package edu.bc.reedelunt;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Consumer implements Runnable {
    private KafkaStream m_stream;
    private int m_threadNumber;

    private Session session;
 
    public Consumer(KafkaStream a_stream, int a_threadNumber, Session session) {
        m_threadNumber = a_threadNumber;
        m_stream = a_stream;

        this.session = session;
    }
 
    public void run() {
        ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
        while (it.hasNext()) {
            try {
                if (session.isOpen()) {
                    session.getRemote().sendString(new String(it.next().message()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Shutting down Thread: " + m_threadNumber);
    }
}