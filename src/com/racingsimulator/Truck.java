package com.racingsimulator;

import static java.lang.System.out;

public class Truck extends Transport{
	// вес в кг
	private int cargoWeight;
	
	public Truck(int speed, double probabilityWheelPuncture, 
			int step, int number, int cargoWeight) {
		super(speed, probabilityWheelPuncture, step, number);
		this.cargoWeight = cargoWeight;
	}
	
	public Truck(int speed, double probabilityWheelPuncture, 
			int step, int number) {
		this(speed, probabilityWheelPuncture, step, number, 0);
	}

	public int getCargoWeight() {
		return cargoWeight;
	}

	public void setCargoWeight(int cargoWeight) {
		this.cargoWeight = cargoWeight;
	}
	
	public void printParameters(){
		super.printParameters();
		out.println("| Вес груза: " + getCargoWeight() + " кг.");
		out.println("| -------------------------------- ");
	}

	@Override
	public long calculateDelayForParameter() {
		return getCargoWeight();
	}
}
