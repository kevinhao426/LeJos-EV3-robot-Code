//package robot;

//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class BluetoothTest {
//
//	public static void main(String[] args) throws IOException {
//		ServerSocket serv = new ServerSocket(1111);
//		Socket s = serv.accept(); //Wait for Laptop to connect
//		DataInputStream in = new DataInputStream(s.getInputStream());
//		DataOutputStream out = new DataOutputStream(s.getOutputStream());
//
//		System.out.println(in.readUTF());
//
//	}
//
//}



import java.io.*;
import java.net.*;

import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;

public class BluetoothTest {
   public static void main(String argv[]) throws Exception {
      String clientSentence;
      String capitalizedSentence;
      ServerSocket welcomeSocket = new ServerSocket(1111);

      while(true) {
         Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = inFromClient.readLine();
            System.out.println("Received: " + clientSentence);
            capitalizedSentence = clientSentence.toUpperCase() + '\n';
            outToClient.writeBytes(capitalizedSentence);
//            if(clientSentence.equals("forward")) {
//            	Motor.A.forward();
//            	Motor.B.forward();
//            	Delay.msDelay(1500);
//            }
//            if(clientSentence.equals("scan")) {
//            	for(int i=0; i<5; i++) {
//            		Motor.D.rotate(120);
//        			Motor.D.rotate(-120);
//            	}
//            }
//            if(clientSentence.equals("exit")) {
//            	System.exit(1);
//            }
      }
   }
}
