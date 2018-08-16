//package test;
import lejos.hardware.*;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;

public class testLejos {

 public static void main(String[] args) {
     
//     Sound.twoBeeps();
//     
//     Delay.msDelay(1000);
//     
//     Sound.beep();
//     
     Motor.C.forward();
     Motor.B.forward();
     Delay.msDelay(1500);
//	 System.out.println("Hello World");
	 Button.waitForAnyPress();
	 
	 
 }

}