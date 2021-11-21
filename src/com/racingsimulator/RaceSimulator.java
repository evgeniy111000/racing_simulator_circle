package com.racingsimulator;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.*;

import java.io.IOException;


public class RaceSimulator {
	public static int countTransports = 0;
	public static List<String> typeTransports;
	public static List<Transport> listTransports = new LinkedList<Transport>();
	public static List<Transport> tableFinishers = Collections.synchronizedList(
			new LinkedList<Transport>()
	);
	// длина круга в метрах
	public static int lengthLap = 0;
	public static int currentLap = 1;
	public static int step = 1;
	
	public static void main(String[] args) throws IOException, InterruptedException{
		String filePropertiesName = "resources/config.properties";
		ReaderPropertiesFile rpf = new ReaderPropertiesFile(filePropertiesName);
		
		if (rpf.isEmptyFileProperties()) {
			out.println("Конфигурационный файл пуст!");
			return;
		}
		
		lengthLap = rpf.getPropertyInt("LengthLap");
		countTransports = rpf.getPropertyInt("CountTransports");
		step = rpf.getPropertyInt("Step");
		typeTransports = rpf.getPropertyList("TypeTransports");
		
		if (typeTransports.isEmpty()){
			out.println("Типы транспортных средств отсутствуют!");
			return;
		}
		
		String transportType = "", transportParameter = "";
		int transportSpeed = 0, transportNumber = 0;
		double transportProbabilityWheelPuncture = 0;
		
		for(int i = 1; i <= countTransports; i++){
			transportType = rpf.getProperty("TransportType" + i);
			if (!typeTransports.contains(transportType)){
				out.println(transportType + ": Транспортное средство отсутствует");
				continue;
			}
			
			transportSpeed = rpf.getPropertyInt("TransportSpeed" + i);
			transportProbabilityWheelPuncture = 
					rpf.getPropertyDouble("TransportProbabilityWheelPuncture" + i);
			transportNumber = rpf.getPropertyInt("TransportNumber" + i);
			transportParameter = rpf.getProperty("TransportParameter" + i);
			switch(transportType){
				case "Car": 
					listTransports.add(
							new Car(transportSpeed, transportProbabilityWheelPuncture, 
									step, transportNumber, Integer.valueOf(transportParameter)
							)
					);
					break;
				case "Motorcycle": 
					listTransports.add(
							new Motorcycle(transportSpeed, transportProbabilityWheelPuncture, 
									step, transportNumber, Boolean.valueOf(transportParameter)
							)
					);
					break;
				case "Truck": 
					listTransports.add(
							new Truck(transportSpeed, transportProbabilityWheelPuncture, 
									step, transportNumber, Integer.valueOf(transportParameter)
							)
					);
					break;
				default: 
					out.println(transportType + ": Такого транспортного средства не предусмотрено");
			}
		}
		
		out.println("\nДЛИНА КРУГА = " + lengthLap + " метров");
		out.println("\nКол-во транспортных средств: " + countTransports);
		Scanner scanner = new Scanner(System.in);
		
		tableFinishers.clear();
		do {
			for(Transport itemTransport: listTransports){
				itemTransport.printParameters();
			}
			
			if (currentLap == 1){
				out.println("\nГонка началась!");
			}
			out.println("\nКРУГ " + currentLap);
			
			List<Thread> threads = new LinkedList<Thread>();
			for(int i = 0; i < listTransports.size(); i++){
				Thread thread = new Thread(listTransports.get(i));
				thread.start();
				threads.add(thread);
			}
			for(int i = 0; i < listTransports.size(); i++){
				threads.get(i).join();
			}
			
			printTableFinishers("\n| ТАБЛИЦА ПЕРВЕНСТВА");
			
			out.println("\nПроехать еще один круг (продолжить гонку)? (yes : no)");
			String continueRace = scanner.next();
			if (!continueRace.equals("yes")) {
				out.println("\nГонка закончена!");
				break;
			}
			tableFinishers.clear();
			currentLap++;
		}while(true);
		scanner.close();
	}
	
	public static void printTableFinishers(String headTable){
		out.println(headTable);
		for(int i = 0; i < tableFinishers.size(); i++){
			out.println("| " + (i + 1) + " место. " 
					+ tableFinishers.get(i).getClass().getSimpleName() 
					+ "[num." + tableFinishers.get(i).getNumber() + "]");
		}
	}
	
}
