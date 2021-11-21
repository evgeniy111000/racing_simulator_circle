package com.racingsimulator;
import static java.lang.System.*;

public class Car extends Transport {
	private int countPassengers;
	
	public Car(int speed, double probabilityWheelPuncture, 
			int step, int number, int countPassengers){
		super(speed, probabilityWheelPuncture, step, number);
		this.countPassengers = countPassengers;
	}
	
	public Car(int speed, double probabilityWheelPuncture, int step, int number){
		this(speed, probabilityWheelPuncture, step, number, 0);
	}

	public int getCountPassengers() {
		return countPassengers;
	}

	public void setCountPassengers(int countPassengers) {
		this.countPassengers = countPassengers;
	}
	
	public void printParameters(){
		super.printParameters();
		out.println("| Кол-во пассажиров: " + getCountPassengers());
		out.println("| -------------------------------- ");
	}

	@Override
	public long calculateDelayForParameter() {
		return getCountPassengers() * 500;
	}
}
