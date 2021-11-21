package com.racingsimulator;

import static java.lang.System.out;

public class Motorcycle extends Transport {
	private boolean isCarriage;
	
	public Motorcycle(int speed, double probabilityWheelPuncture, 
			int step, int number, boolean isCarriage) {
		super(speed, probabilityWheelPuncture, step, number);
		this.isCarriage = isCarriage;
	}
	
	public Motorcycle(int speed, double probabilityWheelPuncture, 
			int step, int number) {
		this(speed, probabilityWheelPuncture, step, number, false);
	}

	public boolean isCarriage() {
		return isCarriage;
	}

	public void setCarriage(boolean isCarriage) {
		this.isCarriage = isCarriage;
	}
	
	public void printParameters(){
		super.printParameters();
		out.println("| Коляска: " + isCarriage());
		out.println("| -------------------------------- ");
	}

	@Override
	public long calculateDelayForParameter() {
		if (isCarriage()){
			return 1000;
		}
		return 0;
	}
}
