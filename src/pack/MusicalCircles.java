package pack;

import java.util.Random;

/* PROJECT 2: Musical Circles
 * 
 * Ernest Curioso
 * Sandra Dheming Lemus
 * Priya Malavia;
 *  
 * Comp 598EA: Embedded Applications
 * Section #15400
 * Professor Wiegley
 * Spring 2016
 */

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lejos.hardware.sensor.*;
import lejos.robotics.RangeFinderAdapter;

public class MusicalCircles {
	
	private enum DIRECTION_FLAG{
		FORWARD,
		BACKWARD
	};
	
	private static DIRECTION_FLAG directionFlag;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EV3TouchSensor bumperRight = new EV3TouchSensor(SensorPort.S3);
		SensorMode touchRight = bumperRight.getTouchMode();
		float[] sampleRight = new float[touchRight.sampleSize()];
		// Touch sensor to end program
		
		EV3TouchSensor bumperLeft = new EV3TouchSensor(SensorPort.S4);
		SensorMode touchLeft = bumperLeft.getTouchMode();
		float[] sampleLeft = new float[touchLeft.sampleSize()];
		
		EV3ColorSensor clrSensor = new EV3ColorSensor(SensorPort.S1);
		SensorMode clrMode = clrSensor.getColorIDMode();
		float[] clrSample = new float[clrMode.sampleSize()];
		// Color sensor
		
		EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(SensorPort.S2);
		SensorMode usMode = usSensor.getDistanceMode();
		//SampleProvider distance= usMode.getModeName("Distance"); //get the distance
		//SampleProvider average = new MeanFilter(distance, 5); // optional samples per running
		float[] usSample = new float[usMode.sampleSize()];
		RangeFinder range = new RangeFinder(usSensor);
		//ultrasonic sensor
		
		
		//initialize the motor
		//EV3LargeRegulatedMotor right = new EV3LargeRegulatedMotor(MotorPort.A);
		//EV3LargeRegulatedMotor left = new EV3LargeRegulatedMotor(MotorPort.B);

		touchRight.fetchSample(sampleRight,0); // Fetches touch mode (1 is pressed, 0 is not pressed).
	
		do {
			usMode.fetchSample(usSample, 0);    // keeps fetching to check for distance
			touchRight.fetchSample(sampleRight,0);
			touchLeft.fetchSample(sampleLeft,0);// Constant fetching (checking) of touch modes are needed.
			clrMode.fetchSample(clrSample,0); // Fetches color mode (Numbers 1 through 7 correspond to a certain color).
			
			while (clrSample[0] == 7){ //black 
				clrMode.fetchSample(clrSample,0);
				
				//right.stop();			//we've hit the black target, stop
				//left.stop();
			}
			
			if(sampleRight[0] == 1 || sampleLeft[0] == 1) { //if the bumper hit something
				Random random = new Random();
				int rotTime = random.nextInt(1500); //random rotation time
				
				Random ranDirection = new Random();
				int direction = ranDirection.nextInt(2);
				if(direction == 0){
					turnRight();
				}
				else{
					turnLeft();
				}
				//right.rotate(rotDegree, true);
				//left.rotate((rotDegree*-1),true);
				Delay.msDelay(rotTime);
				pauseMotors();
				directionFlag = DIRECTION_FLAG.FORWARD;
				
			}
			
			else if(/*we find we are too close to an object*/ takeControl){
				/*we back up and rotate*/
				Random random = new Random();
				int rotTime = random.nextInt(1500); //random rotation time
				
				Random ranDirection = new Random();
				int direction = ranDirection.nextInt(2);
				if(direction == 0){
					turnRight();
				}
				else{
					turnLeft();
				}
				//right.rotate(rotDegree, true);
				//left.rotate((rotDegree*-1),true);
				Delay.msDelay(rotTime);
				pauseMotors();
				directionFlag = DIRECTION_FLAG.BACKWARD;
				
			}
			
			
			Motor.A.setSpeed(500);
			Motor.B.setSpeed(500);
			
			if(directionFlag == DIRECTION_FLAG.FORWARD){
				Motor.A.forward();
				Motor.B.forward();
			}
			else{
				Motor.A.backward();
				Motor.B.backward();
			}
			//right.setSpeed(500);
			//left.setSpeed(500);
			//right.backward();
			//left.backward();
			
		} while (true); // Run program forever until stopped by key press.

		//right.close();
		//left.close();
		//bumperLeft.close();
		//bumperRight.close();
		//clrSensor.close();
	}
	
	//function to turn right
	private static void turnRight(){
		Motor.A.startSynchronization();
		Motor.B.forward();
		Motor.A.backward();
		Motor.A.endSynchronization();
	}
	
	//function to turn left
	private static void turnLeft(){
		Motor.A.startSynchronization();
		Motor.B.backward();
		Motor.A.forward();
		Motor.A.endSynchronization();
	}
	
	//function to stop/pause motors
	private static void pauseMotors(){
		Motor.A.startSynchronization();
		Motor.B.stop();
		Motor.B.stop();
		Motor.A.endSynchronization();
	}
	
	// boolean that keeps fetching for when we are too close to a wall 0.3?
	public boolean takeControl() {
	    return usSensor.getRange() < 0.3;
	}

	
}



	
	

