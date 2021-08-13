package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.plaf.synth.SynthTableHeaderUI;



public class Server {

	static ClientHandler player1 = null;
	static ClientHandler player2 = null;
	
	public static void main(String[] args) {
		
		int portNumber = 8090; //port which server will use for listening!
		
		/*
		 * If the main method has a port number as a parameter, it will use that number instead of 8090
		 */
		if(args.length > 0){
			portNumber = Integer.parseInt(args[0]);
		}
		
		try {
			ServerSocket serverSocketForChatApp =  new ServerSocket(portNumber); //server relying on TCP starts
			while(true){//infinite loop because server must always be in state of listening
				
				System.out.println("Waiting for connection");
				System.out.println();
				
				Socket clientSocket = serverSocketForChatApp.accept(); //the method on right returns socket when client makes contact with server
				
				if(player1 == null) {
					System.out.println("Player 1 connected");
					player1 = new ClientHandler(clientSocket, "1");
					player1.start();
					
				}
				else{
					System.out.println("Player 2 connected");
					player2 = new ClientHandler(clientSocket,"2");
					player2.start();
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void initializePlayer1 () {
		
		try {
			synchronized (player1) {
				player1.wait();
			}
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void initializePlayer2 () {
		
		synchronized (player1) {
			player1.notifyAll();
		}
		
	}
	
	public synchronized static void sendToP1(String data) {
		
		player1.send(data);
		
	}
	public synchronized static void sendToP2(String data) {
		
		player2.send(data);
		
	}
	
}
