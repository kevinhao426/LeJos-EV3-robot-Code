package OnEV3;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.EventListener;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class EV3Demo {

	static EV3LargeRegulatedMotor Left_Motor = new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor RightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

	public static void main(String[] args) throws Exception {
		String clientSentence;
		String capitalizedSentence;

		// create socket listener
		ServerSocket welcomeSocket = new ServerSocket(1111);

		// init set color sensor
		EV3ColorSensor color;
		Port s1 = LocalEV3.get().getPort("S1");
		color = new EV3ColorSensor(s1);

		// connect to client
		Socket connectionSocket = welcomeSocket.accept();

		while (true) {
			
			

			while (true) {
				switch (clientSentence) {
				case "1":
					break;
				case "2":
					break;
				default:
					break;
				}
			}

		}
	}

	public class RemoteControl implements EventListener {
		public Object getSource() {

		}
	}

	public class robotAct() {
		
		public void robotInit() {
			// init wheels
			Wheel wheel1 = WheeledChassis.modelWheel(Left_Motor, 5.6).offset(-4);
			Wheel wheel2 = WheeledChassis.modelWheel(RightMotor, 5.6).offset(4);
			Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
			MovePilot pilot = new MovePilot(chassis);
//			pilot.setAngularSpeed(360);
			
		}
	}
}