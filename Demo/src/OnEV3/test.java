package OnEV3;

import lejos.hardware.*;
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
import lejos.utility.Delay;

public class test {
	static EV3LargeRegulatedMotor Left_Motor = new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor RightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
 public static void main(String[] args) {
     
//     Sound.twoBeeps();
//     
//     Delay.msDelay(1000);
//     
//     Sound.beep();
//     
//     Motor.A.forward();
//     Motor.B.forward();
//     Delay.msDelay(1500);
//	 System.out.println("Hello World");
//	 Button.waitForAnyPress();

		Wheel wheel1 = WheeledChassis.modelWheel(Left_Motor, 5.6).offset(-4);
		Wheel wheel2 = WheeledChassis.modelWheel(RightMotor, 5.6).offset(4);

		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 },WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		pilot.setAngularSpeed(360);
		EV3ColorSensor color;
		Port s1 = LocalEV3.get().getPort("S1");
		color = new EV3ColorSensor(s1);
		int t = color.getColorID();
		while(t!=7) {
			pilot.forward();
			t = color.getColorID();
		}

 }

}