package pack;

/* PROJECT 2: Musical Circles
 * 
 * Ernest Curioso
 * Sandra Dheming Lemus
 * Priya Malavia
 *  
 * Comp 598EA: Embedded Applications
 * Section #15400
 * Professor Wiegley
 * Spring 2016
 */

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.MotorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.*;

public class MusicalCircles {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EV3TouchSensor sensorRed = new EV3TouchSensor(SensorPort.S3); 
		SensorMode touchRed = sensorRed.getTouchMode();  
		float[] sampleRed = new float[touchRed.sampleSize()];
		// Touch sensor to end program
		
		EV3ColorSensor clrSensor = new EV3ColorSensor(SensorPort.S1); //color sensor
		SensorMode clrMode = clrSensor.getColorIDMode();
		float[] clrSample = new float[clrMode.sampleSize()];
		// Color sensor
		
		EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(Sensor.S2);
		SensorModle usMode = usSensor.getDistanceMode();
		//SampleProvider distance= usMode.getModeName("Distance"); //get the distance
		//SampleProvider average = new MeanFilter(distance, 5); // optional samples per running
		float[] utsSample = new float[average.sampleSize()];
		RangeFinder range = new RangeFinder(usSensor);
		//ultrasonic sensor
		
		
		//initialize the motor
		EV3LargeRegulatedMotor right = new EV3LargeRegulatedMotor(MotorPort.A);   
		EV3LargeRegulatedMotor left = new EV3LargeRegulatedMotor(MotorPort.B);

		touchRed.fetchSample(sampleRed,0); // Fetches touch mode (1 is pressed, 0 is not pressed).
	
		do {
		
			usMode.fetchSample(sample, 0);    // keeps fetching to check for distance
			touchRed.fetchSample(sampleRed,0); // Constant fetching (checking) of touch modes are needed.
			clrMode.fetchSample(clrSample,0); // Fetches color mode (Numbers 1 through 7 correspond to a certain color).
			
				
			
			while (clrSample[0] == 7 && sampleRed[0] == 0) || takeControl() { //black
				touchRed.fetchSample(sampleRed,0); // Constant fetching (checking) of touch modes are needed.
				clrMode.fetchSample(clrSample,0);
				
				if(/*we find we are too close to an object*/ takeControl){
					/*we back up and rotate*/
					
					
				}
				
				/*if not we stop because we find a circle*/
				
				right.stop();
				left.stop();
			}
			right.setSpeed(500);
			left.setSpeed(500);
			right.forward();
			left.forward();
			
		} while (sampleRed[0] == 0 ); // Run program while red touch sensor is not pressed.
		
		right.close();
		left.close();
		sensorRed.close();
		clrSensor.close();
	}
	
	

	
}


// boolean that keeps fetching for when we are too close to a wall 0.3?
public boolean takeControl() {
    return usSensor.getRange() < 0.3;
}

	
	

