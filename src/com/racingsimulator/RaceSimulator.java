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
	// ����� ����� � ������
	public static int lengthLap = 0;
	public static int currentLap = 1;
	public static int step = 1;
	
	public static void main(String[] args) throws IOException, InterruptedException{
		String filePropertiesName = "resources/config.properties";
		ReaderPropertiesFile rpf = new ReaderPropertiesFile(filePropertiesName);
		
		if (rpf.isEmptyFileProperties()) {
			out.println("���������������� ���� ����!");
			return;
		}
		
		lengthLap = rpf.getPropertyInt("LengthLap");
		countTransports = rpf.getPropertyInt("CountTransports");
		step = rpf.getPropertyInt("Step");
		typeTransports = rpf.getPropertyList("TypeTransports");
		
		if (typeTransports.isEmpty()){
			out.println("���� ������������ ������� �����������!");
			return;
		}
		
		String transportType = "", transportParameter = "";
		int transportSpeed = 0, transportNumber = 0;
		double transportProbabilityWheelPuncture = 0;
		
		for(int i = 1; i <= countTransports; i++){
			transportType = rpf.getProperty("TransportType" + i);
			if (!typeTransports.contains(transportType)){
				out.println(transportType + ": ������������ �������� �����������");
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
					out.println(transportType + ": ������ ������������� �������� �� �������������");
			}
		}
		
		out.println("\n����� ����� = " + lengthLap + " ������");
		out.println("\n���-�� ������������ �������: " + countTransports);
		Scanner scanner = new Scanner(System.in);
		
		tableFinishers.clear();
		do {
			for(Transport itemTransport: listTransports){
				itemTransport.printParameters();
			}
			
			if (currentLap == 1){
				out.println("\n����� ��������!");
			}
			out.println("\n���� " + currentLap);
			
			List<Thread> threads = new LinkedList<Thread>();
			for(int i = 0; i < listTransports.size(); i++){
				Thread thread = new Thread(listTransports.get(i));
				thread.start();
				threads.add(thread);
			}
			for(int i = 0; i < listTransports.size(); i++){
				threads.get(i).join();
			}
			
			printTableFinishers("\n| ������� ����������");
			
			out.println("\n�������� ��� ���� ���� (���������� �����)? (yes : no)");
			String continueRace = scanner.next();
			if (!continueRace.equals("yes")) {
				out.println("\n����� ���������!");
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
			out.println("| " + (i + 1) + " �����. " 
					+ tableFinishers.get(i).getClass().getSimpleName() 
					+ "[num." + tableFinishers.get(i).getNumber() + "]");
		}
	}
	
}
