import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Pose;

public class RoboServer {
	public static ServerSocket OPSocket;
	public static Socket connSocket;
	public static InputStream inputStream;
	public static DataOutputStream outputStream;
	public static BufferedReader inFromClient;
	public static BufferedWriter outToClient;

	public static boolean running = true;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		EV3LargeRegulatedMotor Left_Motor = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor RightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		String getInput;
		SampleProvider colorID;
		SampleProvider usDist;
		float[] sampleCL = new float[1];
		float[] sampleUS = new float[1];
		EV3ColorSensor cl = null; // color sensor
		EV3UltrasonicSensor us = null; // ultrasonic sensor
		
		Wheel wheel1 = WheeledChassis.modelWheel(Left_Motor, 5.6).offset(-4);
		Wheel wheel2 = WheeledChassis.modelWheel(RightMotor, 5.6).offset(4);
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		pilot.setLinearSpeed(3);
		pilot.setAngularSpeed(45);
		Sound.beep();
		
		OdometryPoseProvider pp = new OdometryPoseProvider(pilot);
		Pose pose;
		
		Port p1= LocalEV3.get().getPort("S1");
		cl = new EV3ColorSensor(p1);
		colorID = cl.getColorIDMode();
		Port p4 = LocalEV3.get().getPort("S4");
		us = new EV3UltrasonicSensor(p4);
		usDist = us.getDistanceMode();
		Sound.twoBeeps();

		connect();
		
		inFromClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));	
		getInput = inFromClient.readLine();
		while (getInput != null) {
			
			switch (getInput) {
			case "Up":
				pilot.forward();
				break;
				
			case "Down":
				pilot.backward();
				break;

			case "Turnleft":
				pilot.rotate(-90);
				break;
				
			case "Turnright":
				pilot.rotate(90);
				break;
				
			case "Stop":
				pilot.stop();
//				pose = pp.getPose();
//				System.out.println("" + pose);
//				String out = pose.toString();
				colorID.fetchSample(sampleCL, 0);
				usDist.fetchSample(sampleUS, 0);
				String out = "" + sampleCL[0] + " " + sampleUS[0];
				sendData(out);
				break;
				
			case "Auto":
				pilot.forward();
				while(pilot.isMoving()) {
					colorID.fetchSample(sampleCL, 0);
					usDist.fetchSample(sampleUS, 0);
					if (sampleCL[0] == 7.0 || sampleUS[0] <= 0.05) {
						pilot.stop();
						out = "" + sampleCL[0] + " " + sampleUS[0];
						sendData(out);
					}
				}
				pilot.stop();
				//Sound.twoBeeps();
				break;
				
			case "Exit":
				close();
				break;
				
			default:
				break;
			}
			
			getInput = inFromClient.readLine();
			Sound.twoBeeps();

		}
		
		close();

	}
	
	public static void connect() throws IOException {
		OPSocket = new ServerSocket(1111);
		connSocket = OPSocket.accept();
		inputStream = connSocket.getInputStream();
		outputStream = new DataOutputStream(connSocket.getOutputStream());
	}
	
	public static void close() throws IOException {
		OPSocket.close();
		connSocket.close();
//		inputStream.close();
////		outputStream.close();
//		if(inFromClient == null) {
//			inFromClient.close();
//		}
//		if(outputStream == null) {
//			outputStream.close();
//		}
	}
	
	public static void sendData (String Data) throws IOException {
		String outString = Data + '\n';
		outputStream.writeBytes(outString);
	}

}
