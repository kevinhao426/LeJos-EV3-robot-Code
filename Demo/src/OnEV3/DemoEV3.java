package OnEV3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Iterator;
import java.util.Vector;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class DemoEV3 {

	static EV3LargeRegulatedMotor LeftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor RightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

	public class robotAct {

		public void forward(MovePilot pilot) {
			pilot.forward();
		}

		public void backward(MovePilot pilot) {
			pilot.backward();
		}

		public void turnLeft(MovePilot pilot) {
			pilot.rotateLeft();
		}

		public void turnRight(MovePilot pilot) {
			pilot.rotateRight();
		}

		public void pause(MovePilot pilot) {
			pilot.stop();
		}

		public void auto() {
			
		}
	}

	public class clientEvent extends EventObject {
		private Object obj;
		private String sName;

		public clientEvent(Object source, String sName) {
			super(source);
			this.obj = source;
			this.sName = sName;
		}

		public Object getSource() {
			return obj;
		}

		public String getsName() {
			return sName;
		}
		
		public void doEvent() {
			//Object temp = this.getSource();
			String MSG = this.getsName();
			
			switch (MSG) {
			case "Forward":
				
			}
		}
	}

	public interface REventListener extends EventListener {

		public void handleEvent(clientEvent event);
		
	}

//	public class REventObject extends EventObject {
//		private static final long serialVersionUID = 1L;
//
//		public REventObject(Object source) {
//			super(source);
//		}
//
//		public void doEvent() {
//			System.out.println("通知一个事件源 source :" + this.getSource());
//		}
//
//	}

	public class clientEventSource {
		private Vector<REventListener> ListenerList = new Vector<REventListener>();

		public void addListener(REventListener eventListener) {
			ListenerList.add(eventListener);
		}

		public void notifyListenerEvents(clientEvent event) {
			for (REventListener eventListener : ListenerList) {
				eventListener.handleEvent(event);
			}
		}
	}

	public void main(String[] args) throws IOException {
		String clientMSG = "";

		ServerSocket RMTControl = new ServerSocket(1111);
		Socket connectSocket = RMTControl.accept();

		robotAct robot = new robotAct();
		
		//robot init
		Wheel wheel1 = WheeledChassis.modelWheel(LeftMotor, 5.6).offset(-4);
		Wheel wheel2 = WheeledChassis.modelWheel(RightMotor, 5.6).offset(4);
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		
		EV3ColorSensor color;
		Port s1 = LocalEV3.get().getPort("S1");
		color = new EV3ColorSensor(s1);

		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectSocket.getInputStream()));
		clientMSG = inFromClient.readLine();
		
		
		switch (clientMSG) {
		case "forward":
			robot.forward(pilot);
			break;
			
		case "pause":
			robot.pause(pilot);
			break;
		
		default:
			break;
		}
	}
}
