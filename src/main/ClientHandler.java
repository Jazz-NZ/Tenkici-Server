package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread {
	
	Socket clientSocket = null;
	String player = null;
	
	BufferedReader inputFromClient = null;
	PrintStream outputToClient = null;
	
	public String clientUsername = null;
	Scanner sc= new Scanner(System.in);
	
	private String init1X = "200";
	private String init1Y = "550";
	
	private String init2X = "400";
	private String init2Y = "550";	
	
	boolean wantToSuspendThread = false;
	
	public ClientHandler(Socket clientSocket, String playerNum) {
		this.clientSocket = clientSocket;
		this.player = playerNum;
	}
	
	public void send(String data) {
		
		outputToClient.println(data);
		
	}
	
	@Override
	public void run() {
		
		System.out.println("ClientHandler zapocet");
		
		try {
			
			inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));//init inputStream
			outputToClient = new PrintStream(clientSocket.getOutputStream());//init outputStream
			
			
			//Server.sendToP1(this, 50);
			String x = "50";
			//voditi racuna mora printLN zbog readline() na klijentskoj strani
			if(player.contains("1")) {
				
				Server.initializePlayer1();
				
				outputToClient.println(player); //player+","+init1X+","+init1Y
				System.out.println("Igrac 1 inicijalizovan");
				System.out.println();
				
				
			
			}else {
				
				Server.initializePlayer2();
				
				outputToClient.println(player);
				System.out.println("Igrac 2 inicijalizovan");
				System.out.println();
				
				
			}
			
			
			while(true) {
				  
				String data = inputFromClient.readLine();
				System.out.println(player + "," + data); 
				
				if(player.contains("1")) {
					Server.sendToP2(player + "," + data);
					  
				}else {
					Server.sendToP1(player + "," + data);
					  
				}	
				
			}
			
			/*
			 * while(true){ //endless loop until client notifies us he wants to quit String
			 * text =sc.nextLine();//reading line from client in loop
			 * if(text.startsWith("a")){ System.out.println(player);
			 * outputToClient.print("RADII"); } //method from ChatServer(look there for }
			 */
			 
			 
			//clientSocket.close(); //closing socket with client
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	

}
