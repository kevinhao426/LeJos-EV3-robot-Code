package rbserver;


import lejos.hardware.Audio;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.ev3.EV3;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.ModulusFilter;
import lejos.utility.Delay;

public class gyroTest {
   
   EV3 ev3 = null;
   private EV3GyroSensor gyro = null;
   private SampleProvider gyroSamples = null;
   private UnregulatedMotor leftMotor = null;
   private UnregulatedMotor rightMotor = null;
   private Audio audio = null;
   float[] angle = { 0.0f };
   float gyroTacho = 0;
   
   public gyroTest() {

      // register custom key listener
      Button.ESCAPE.addKeyListener(new KeyListener() {
         @Override
         public void keyPressed(Key k) {
            System.out.println("Bye...");
            if (k.getId() == Button.ID_ESCAPE) {
               gyroTest.this.exit();
            }
         }

         @Override
         public void keyReleased(Key k) {
         }
      });

      // write current angle on demand
      Button.DOWN.addKeyListener(new KeyListener() {
         @Override
         public void keyPressed(Key k) {
            if (k.getId() == Button.ID_DOWN) {
               System.out.println("Current angle:" + getGyroAngle());
            }
         }

         @Override
         public void keyReleased(Key k) {
         }
      });

      // reset gyro
      Button.UP.addKeyListener(new KeyListener() {
         @Override
         public void keyPressed(Key k) {
            if (k.getId() == Button.ID_UP) {
               resetGyro();
            }
         }

         @Override
         public void keyReleased(Key k) {
         }
      });

   }
   
   public void resetGyro() {
      if (gyro != null) {
         Delay.msDelay(1000); //wait until the hands are off the robot
         audio.systemSound(0);
         
         gyro.reset();
         gyroSamples = gyro.getAngleMode();
         gyroTacho = 0;
         System.out.println("Gyro is reset");
      }
   }
   
   public void resetGyroTacho() {
      gyroTacho += getGyroAngle();
   }

   public void init() {
      ev3 = LocalEV3.get();
      audio = ev3.getAudio();
      audio.systemSound(0);
      gyro = new EV3GyroSensor(SensorPort.S3);
      System.out.println("Gyro init");
      leftMotor = new UnregulatedMotor(MotorPort.A);
      System.out.println("Left motor init");
      rightMotor = new UnregulatedMotor(MotorPort.B);
      System.out.println("Right motor init");
      gyroSamples = gyro.getAngleMode();
      audio.systemSound(Audio.DOUBLE_BEEP);
   }
   
   public float getGyroAngleRaw() {
      gyroSamples.fetchSample(angle, 0);
      return angle[0];
   }

   public float getGyroAngle() {
      float rawAngle = getGyroAngleRaw();
      return rawAngle - gyroTacho;
   }

   public void exit() {
      if (audio != null) {
         audio.systemSound(2);
      }
      if (leftMotor != null) {
         leftMotor.stop();
         leftMotor.close();
      }
      if (rightMotor != null) {
         rightMotor.stop();
         rightMotor.close();
      }
      if (gyro != null) {
         gyro.close();
      }
      System.exit(0);
   }
   
   public void run() {
      Delay.msDelay(500);      
      LCD.clear();
      
      while (true) {
      
         LCD.drawString("Rotate?", 0, 0);
         Button.ENTER.waitForPress();
         LCD.clearDisplay();
         
         leftMotor.setPower(100);
         rightMotor.setPower(100);
         //start turning right
         leftMotor.forward();
         rightMotor.backward();
         while (getGyroAngle() < 60) {
            Thread.yield();
         }
         leftMotor.stop();
         rightMotor.stop();

         //wait until the robot is really stopped
         Delay.msDelay(100);
         
         if (getGyroAngle() > 90f) {
            System.out.println("Angle is " + angle[0]);
            leftMotor.setPower(30);
            rightMotor.setPower(30);
            //turn left slowly to correct the rotation angle
            rightMotor.forward();
            leftMotor.backward();
            while (getGyroAngle() > 93) {
               Thread.yield();
            }
            leftMotor.stop();
            rightMotor.stop();
         }
         audio.systemSound(0);

         resetGyroTacho();
      }
   }
   
   public static void main(String[] args) {
      gyroTest test = new gyroTest();
      test.init();
      test.run();
      test.exit();
   }

}
