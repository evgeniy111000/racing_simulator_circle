package com.racingsimulator;

import static java.lang.System.out;

import java.util.concurrent.TimeUnit;

public abstract class Transport implements Runnable{
	// скорость в км/ч
	private int speed;
	private double probabilityWheelPuncture;
	// пройденный путь в метрах
	private int distanceTraveled = 0;
	private int step;
	private int number;
	
	public Transport(int speed, double probabilityWheelPuncture, int step, int number){
		this.speed = speed;
		this.probabilityWheelPuncture = probabilityWheelPuncture;
		this.step = step;
		this.number = number;
	}
	
	@Override
	public void run() {
		long delay = getDelay();
		int distanceRace = RaceSimulator.lengthLap * RaceSimulator.currentLap;
		for(int i = getDistanceTraveled(); i < distanceRace; i = i + getStep()){
			try {
				long resultDelay = checkDistanceTraveled(distanceRace); 
				if (resultDelay != 0) delay = resultDelay;
				TimeUnit.MILLISECONDS.sleep(calculateDelayForParameter());
				goTheWay(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		finish();
	}
	
	public abstract long calculateDelayForParameter();
	
	public void finish(){
		RaceSimulator.tableFinishers.add(this);
		out.println(getClass().getSimpleName() 
				+ "[num." + getNumber() + "] Finished!");
	}
	
	public long checkDistanceTraveled(int distanceRace){
		if ((getDistanceTraveled() + getStep()) > distanceRace){
			setStep(getDistanceTraveled() + getStep() - distanceRace);
			return getDelay();
		}
		return 0;
	}
	
	public void goTheWay(long delay) throws InterruptedException{
		TimeUnit.MILLISECONDS.sleep(delay);
		setDistanceTraveled(getDistanceTraveled() + getStep());
		out.println(toString() + "Пройдено: " + getDistanceTraveled() + " метров.");
		checkWheelPuncture();
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public double getProbabilityWheelPuncture() {
		return probabilityWheelPuncture;
	}

	public void setProbabilityWheelPuncture(double probabilityWheelPuncture) {
		this.probabilityWheelPuncture = probabilityWheelPuncture;
	}

	public int getDistanceTraveled() {
		return distanceTraveled;
	}

	public void setDistanceTraveled(int distanceTraveled) {
		int distanceRace = RaceSimulator.lengthLap * RaceSimulator.currentLap;
		if (distanceTraveled > distanceRace){
			this.distanceTraveled = distanceRace;
		} else {
			this.distanceTraveled = distanceTraveled;
		}
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public long getDelay(){
		return getMillisecondsForDistance(getStep());
	}
	
	public String toString(){
		String transportName = getClass().getSimpleName();
		String stringTransport = "(" + transportName + ")";
		StringBuilder pathTransport = new StringBuilder(
				transportName + "[num." + getNumber() + "] ->"
		);
		//transportName.substring(0, 1) + 
		if (this.getDistanceTraveled() == 0){
			pathTransport.append(stringTransport);
		}
		if (this.getDistanceTraveled() > 0){
			for(int i = 0; i < getDistanceTraveled(); i = i + getStep()){
				pathTransport.append("*");
			}
			pathTransport.append(stringTransport);
		}
		return pathTransport.toString();
	}
	
	public void printParameters(){
		out.println("|          Параметры " + getClass().getSimpleName());
		out.println("| Скорость: " + getSpeed() + " км/ч");
		out.println("| Вероятность прокола колеса: "
				+ getProbabilityWheelPuncture());
	}
	
	private long getMillisecondsForDistance(int distance){
		if (distance >= 1 && distance <= 1000){
			return Math.round((3600000 * distance/(speed * 1000)));
		}
		return Math.round((3600000/(speed * 1000)));
	}
	
	public void checkWheelPuncture(){
		int distanceRace = RaceSimulator.lengthLap * RaceSimulator.currentLap;
		if (getDistanceTraveled() < distanceRace && isWheelPuncture()){
			try {
				String transportName = getClass().getSimpleName()
						+ "[num." + getNumber() + "]: ";
				out.println(transportName + "Колесо прокололось. Идет замена колеса...");
				TimeUnit.SECONDS.sleep(15);
				out.println(transportName + "Колесо починено! Продолжаю движение.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean isWheelPuncture(){
		return Math.random() < this.getProbabilityWheelPuncture();
	}
}