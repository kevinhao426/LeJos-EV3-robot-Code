package rbserver;
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
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.GyroscopeAdapter;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.CompassPoseProvider;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Pose;
import lejos.utility.Delay;
import lejos.utility.GyroDirectionFinder;
public class RoboServer2 {
	public static ServerSocket OPSocket;
	public static Socket connSocket;
	public static InputStream inputStream;
	public static DataOutputStream outputStream;
	public static BufferedReader inFromClient;
	public static BufferedWriter outToClient;
	public String getInput;
	public static boolean running = true;
//	public static EV3LargeRegulatedMotor Left_Motor = new EV3LargeRegulatedMotor(MotorPort.A);
//	public static EV3LargeRegulatedMotor RightMotor = new EV3LargeRegulatedMotor(MotorPort.B);	
	public SampleProvider colorID;
	public SampleProvider usDist;
	public static SampleProvider gyroSP;
	public float[] sampleCL;
	public float[] sampleUS;
	public static float[] sampleGR;
	public EV3ColorSensor cl; // color sensor
	public EV3UltrasonicSensor us; // ultrasonic sensor
	public static EV3GyroSensor gr;
	public GyroscopeAdapter gyroSC;
	public GyroDirectionFinder gyro;	
	public Wheel wheel1;
	public Wheel wheel2;
	public Chassis chassis;
	public static DifferentialPilot pilot;
	public OdometryPoseProvider pp;
	public Pose pose;
	public int direction = 90;// 90-left,270-right,0-up,180-down
	RoboServer2() {
		sampleCL = new float[1];
		sampleUS = new float[1];
		sampleGR = new float[1];
		cl = null; // color sensor
		us = null; // ultrasonic sensor

//		wheel1 = WheeledChassis.modelWheel(Left_Motor, 5.6).offset(-3.75);
//		wheel2 = WheeledChassis.modelWheel(RightMotor, 5.6).offset(3.75);
//		chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new DifferentialPilot(55, 75, Motor.A, Motor.B, false);
//		pilot.setLinearAcceleration(10);
		pilot.setLinearSpeed(20);
		pilot.setAngularSpeed(15);

		Port p1 = LocalEV3.get().getPort("S1");
		cl = new EV3ColorSensor(p1);
		colorID = cl.getColorIDMode();

		Port p3 = LocalEV3.get().getPort("S3");
		gr = new EV3GyroSensor(p3);
		gyroSP = gr.getAngleMode();
		gr.reset();
		gyroSC = new GyroscopeAdapter(gyroSP, 1.0f);
		gyro = new GyroDirectionFinder(gyroSC);

		Port p4 = LocalEV3.get().getPort("S4");
		us = new EV3UltrasonicSensor(p4);
		usDist = us.getDistanceMode();

		pp = new CompassPoseProvider(pilot, gyro);
//		pp = new OdometryPoseProvider(pilot);

		// Sound.twoBeeps();

		// try{
		// connect();
		// inFromClient = new BufferedReader(new
		// InputStreamReader(connSocket.getInputStream()));
		// getInput = inFromClient.readLine();
		// }catch (IOException e) {
		// System.out.println("connect failded!");
		// }
	}

	public static void turnLeft(DifferentialPilot pilot) {
		pilot.setAngularSpeed(15);
		gyroSP.fetchSample(sampleGR, 0);
		float temp = sampleGR[0];
		pilot.rotateLeft();
		while (sampleGR[0]<temp+100) {
			Thread.yield();
			gyroSP.fetchSample(sampleGR, 0);
		}
		pilot.stop();
		Delay.msDelay(1000);
		if (sampleGR[0]>temp+90) {
			pilot.setAngularSpeed(5);
			gyroSP.fetchSample(sampleGR, 0);
			pilot.rotateRight();
			while (sampleGR[0]>temp+90) {
				Thread.yield();
				gyroSP.fetchSample(sampleGR, 0);
			}
			pilot.stop();
			Delay.msDelay(1000);
		}
	}

	public static void turnRight(DifferentialPilot pilot) {
		pilot.setAngularSpeed(15);
		float temp = sampleGR[0];
		gyroSP.fetchSample(sampleGR, 0);
		pilot.rotateRight();
		while (sampleGR[0]>temp-100) {
			Thread.yield();
			gyroSP.fetchSample(sampleGR, 0);
		}
		pilot.stop();
		Delay.msDelay(1000);
		gyroSP.fetchSample(sampleGR, 0);
		if (sampleGR[0]<temp-90) {
			pilot.setAngularSpeed(5);
			gyroSP.fetchSample(sampleGR, 0);
			pilot.rotateLeft();
			while (sampleGR[0]<temp-90) {
				Thread.yield();
				gyroSP.fetchSample(sampleGR, 0);
			}
			pilot.stop();
		}
	}

	@SuppressWarnings({ "resource", "deprecation" })
	public static void main(String[] args) throws IOException {
		RoboServer2 robot = new RoboServer2();
		sendDataThread sendThread = robot.new sendDataThread();
		System.out.println("connect....");
		Sound.beep();
		try {
			// System.out.println("connect!");
			connect();
			inFromClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
			System.out.println("connect!");
		} catch (IOException e) {
			System.out.println("connect failded!");
		}

		if (inFromClient.ready())
			robot.getInput = inFromClient.readLine();
		else
			robot.getInput = "";

		while (true) {
			if (robot.getInput.equals("Up")) {
				if (sendThread.t == null)
					sendThread.start();
				sendThread.resume();
				robot.pilot.forward();

				while (robot.pilot.isMoving()) {
					robot.colorID.fetchSample(robot.sampleCL, 0);
					robot.usDist.fetchSample(robot.sampleUS, 0);

					if (inFromClient.ready())
						robot.getInput = inFromClient.readLine();
					else
						robot.getInput = "";

					if (robot.sampleCL[0] == 2.0 || robot.sampleCL[0] == 5.0 || robot.sampleUS[0] <= 0.04
							|| robot.getInput.equals("Stop")) {
						robot.pilot.stop();
					}
					// pilot.travelArc(radius, distance, immediateReturn);

				}
			}

			if (robot.getInput.equals("Down")) {
				if (sendThread.t == null)
					sendThread.start();
				sendThread.resume();
				robot.pilot.backward();
				while (robot.pilot.isMoving()) {
					robot.colorID.fetchSample(robot.sampleCL, 0);
					robot.usDist.fetchSample(robot.sampleUS, 0);

					if (inFromClient.ready())
						robot.getInput = inFromClient.readLine();
					else
						robot.getInput = "";

					if (robot.sampleCL[0] == 2.0 || robot.sampleCL[0] == 5.0 || robot.sampleUS[0] <= 0.05
							|| robot.getInput.equals("Stop")) {
						robot.pilot.stop();
					}
				}
			}
			if (robot.getInput.equals("Turnleft")) {
				turnLeft(robot.pilot);
				// robot.pilot.rotate(-90);
				robot.direction = robot.direction - 90;
				if (robot.direction < 0)
					robot.direction = robot.direction + 360;
			}
			if (robot.getInput.equals("Turnright")) {
				turnRight(robot.pilot);
				// robot.pilot.rotate(90);
				robot.direction = robot.direction + 90;
				if (robot.direction >= 360)
					robot.direction = robot.direction - 360;
			}
			if (robot.getInput.equals("Stop")) {
				if (sendThread.t.isAlive())
					sendThread.suspend();

				robot.pilot.stop();
				// ***** pose = pp.getPose();
				// ***** System.out.println("" + pose);
				// ***** String out = pose.toString();
				// ***** robot.colorID.fetchSample(robot.sampleCL, 0);
				// ***** robot.usDist.fetchSample(robot.sampleUS, 0);
				// ***** String out = "" + robot.sampleCL[0] + " " + robot.sampleUS[0];
				// ***** sendData(out);
			}

			if (robot.getInput.equals("Auto")) {
				sendThread.start();

				System.out.println("Auto Run!");
				robot.autoTraveled(robot.pilot);

				// Sound.twoBeeps();
			}

			if (robot.getInput.equals("Exit")) {
				sendThread.stop();
				close();
				break;
			}

			if (inFromClient.ready())
				robot.getInput = inFromClient.readLine();
			else
				robot.getInput = "";
			// Sound.twoBeeps();

		}

	}

	public static void connect() throws IOException {
		// OPSocket = new ServerSocket(5678);
		OPSocket = new ServerSocket(1111);
		connSocket = OPSocket.accept();
		inputStream = connSocket.getInputStream();
		outputStream = new DataOutputStream(connSocket.getOutputStream());
		System.out.println("connect!");

	}

	public static void close() throws IOException {

		OPSocket.close();
		connSocket.close();
	}

	public static void sendData(String Data) {
		String outString = Data + '\n';
		try {
			outputStream.writeBytes(outString);
		} catch (IOException e) {
			System.out.println("writeBytes failded!");
		}
	}

	public void autoTraveled(DifferentialPilot pilot2) throws IOException {
		String inputString;
		pilot2.forward();
		pose = pp.getPose();
		while (pilot2.isMoving()) {
//			Motor.D.rotate(90);
//			while(Motor.D.isMoving()) {
//				colorID.fetchSample(sampleCL, 0);
//			}
//			usDist.fetchSample(sampleUS, 0);
//			Motor.D.rotate(-90);
//			while(Motor.D.isMoving()) {
				
//			}
			colorID.fetchSample(sampleCL, 0);
			usDist.fetchSample(sampleUS, 0);
			
			if(sampleCL[0]==2.0) {
				pilot2.stop();
				Delay.msDelay(500);
				colorID.fetchSample(sampleCL, 0);
			}
			// boundary 
			if (sampleCL[0] == 2.0||sampleCL[0]==7.0) {
				pilot2.stop();
				if (direction == 90) {
//					pilot.rotate(90);
					turnRight(pilot2);
					colorID.fetchSample(sampleCL, 0);
					usDist.fetchSample(sampleUS, 0);
					if (!(((sampleCL[0] == 2.0)) && (sampleUS[0] <= 0.04))) {
						pilot2.travel(120);
						if(pose.getY()<=-550)
						pilot2.stop();
					}
//					pilot.rotate(90);
					turnRight(pilot2);
					pilot2.forward();
					direction = 270;
				} else if (direction == 270) {
//					pilot.rotate(-90);
					turnLeft(pilot2);
					colorID.fetchSample(sampleCL, 0);
					usDist.fetchSample(sampleUS, 0);

					if (!(((sampleCL[0] == 2.0)) && (sampleUS[0] <= 0.04))) {
						pilot2.travel(120);
						if(pose.getY()<=-550)
						pilot2.stop();
					}
//					pilot.rotate(-90);
					turnLeft(pilot2);
					pilot2.forward();
					direction = 90;
				}
			}

			// obstacle
			if (sampleUS[0] <= 0.20) {
				pilot2.stop();
				pilot2.travel(120);
				
				if (direction == 90) {
					turnRight(pilot2);
					direction = 180;
//					pilot.rotate(90);
					colorID.fetchSample(sampleCL, 0);
					usDist.fetchSample(sampleUS, 0);

					if (!(((sampleCL[0] == 2.0)) && (sampleUS[0] <= 0.08))) {
						pilot2.travel(150);
						pilot2.stop();
					}
					turnLeft(pilot2);
					direction = 90;
//					pilot.rotate(-90);
					colorID.fetchSample(sampleCL, 0);
					usDist.fetchSample(sampleUS, 0);
					if (!(((sampleCL[0] == 2.0)) && (sampleUS[0] <= 0.08))) {
						pilot2.travel(350);
						pilot2.stop();
					}
					turnLeft(pilot2);
					direction = 0;
//					pilot.rotate(-90);
					colorID.fetchSample(sampleCL, 0);
					usDist.fetchSample(sampleUS, 0);

					if (!(((sampleCL[0] == 2.0)) && (sampleUS[0] <= 0.08))) {
						pilot2.travel(150);
						pilot2.stop();
					}
					turnRight(pilot2);
					direction = 90;
//					pilot.rotate(90);
					pilot2.forward();

				} else if (direction == 270) {
					turnLeft(pilot2);
					direction = 180;
//					pilot.rotate(-90);
					colorID.fetchSample(sampleCL, 0);
					usDist.fetchSample(sampleUS, 0);

					if (!(((sampleCL[0] == 2.0)) && (sampleUS[0] <= 0.08))) {
						pilot2.travel(150);
						pilot2.stop();
					}
					turnRight(pilot2);
					direction = 270;
//					pilot.rotate(90);
					colorID.fetchSample(sampleCL, 0);
					usDist.fetchSample(sampleUS, 0);

					if (!(((sampleCL[0] == 2.0)) && (sampleUS[0] <= 0.08))) {
						pilot2.travel(350);
						pilot2.stop();
					}
					turnRight(pilot2);
					direction = 0; 
//					pilot.rotate(90);
					colorID.fetchSample(sampleCL, 0);
					usDist.fetchSample(sampleUS, 0);

					if (!(((sampleCL[0] == 2.0)) && (sampleUS[0] <= 0.08))) {
//					if (!(((sampleCL[0] == 2.0) || (sampleCL[0] == 5.0)) && (sampleUS[0] <= 0.04))) {
						pilot2.travel(150);
						pilot2.stop();
					}
					turnLeft(pilot2);
					direction = 270;
//					pilot.rotate(-90);
					pilot2.forward();
				}
			}

			if (inFromClient.ready())
				inputString = inFromClient.readLine();
			else
				inputString = "";
			
			if (inputString.equals("Auto")||pose.getY()<=-550) {
				pilot2.stop();
			}
		}

	}

	class sendDataThread implements Runnable {
		public Thread t;
		boolean suspended = false;

		public void run() {
			int i = 0;
			int j = 0;
			int x = 40;
			int y = 60;
			int heading = 90;
			while (true && (!Thread.currentThread().isInterrupted())) {
				try {

					pose = pp.getPose();
					String out = (pose.getX()/10) + "," + -(pose.getY()/10) + "," + direction;
					colorID.fetchSample(sampleCL, 0);
					usDist.fetchSample(sampleUS, 0);
					out = sampleCL[0] + " " + sampleUS[0] + " " + out;
					sendData(out);
					/*
					 * sendData("4 5 " + x + "," + y + "," + heading); if(heading==90) x=x+40; else
					 * x=x-40; y=60+j*40;
					 * 
					 * if((x>=860)&&(heading==90)) { j++; x=x-40; y=60+j*40;
					 * 
					 * heading=270; }
					 * 
					 * if((x<=20)&&(heading==270)) { j++; x=x+40; y=60+j*40; heading=90; }
					 * 
					 * i++; if(i>300) break;
					 */
					Thread.sleep(1000);
					synchronized (this) {
						while (suspended) {
							wait();
						}
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		}

		public void start() {
			if (t == null) {
				t = new Thread(this, "sendThread");
				t.start();
			}
		}

		public void stop() {
			if (t != null) {
				t.interrupt();
			}
		}

		void suspend() {
			suspended = true;
		}

		synchronized void resume() {
			suspended = false;
			notify();
		}

	}

}
