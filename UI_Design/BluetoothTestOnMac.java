//package myLaptop;

//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//public class BluetoothTestOnMac {
//
//	public static void main(String[] args) throws UnknownHostException, IOException {
//		Socket s = new Socket("10.0.1.1", 1111);
//		DataOutputStream out = new DataOutputStream(s.getOutputStream());
//		out.writeUTF("Hello EV3!");
//	}
//
//}


import java.io.*;
import java.net.*;

public class BluetoothTestOnMac {
   public static void main(String argv[]) throws Exception{
      String sentence;
      String modifiedSentence;

      Socket clientSocket = new Socket("10.0.1.1", 1111);    
//      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      
      do {
      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
      sentence = inFromUser.readLine();
    	  outToServer.writeBytes(sentence + '\n');
      }while(!sentence.equals("exit"));
      
//      modifiedSentence = inFromServer.readLine();
//      System.out.println("FROM SERVER: " + modifiedSentence);
      
      clientSocket.close();
   }
}
