package mircloud_klijent;

import java.net.*;
import java.io.*;
import java.util.*;

public class ClientMain {	
	public static void main(String[] args) {
			Socket socket;
			try {
				socket = new Socket("localhost", 9090);
			
				
				
				ClientRead read = new ClientRead(socket);
				read.start();
				
			} catch (IOException e) {
				System.out.println("Server is down.");
			}
	}
}
