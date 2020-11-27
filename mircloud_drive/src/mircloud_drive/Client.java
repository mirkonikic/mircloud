package mircloud_drive;

import java.net.*;

public class Client {
	Boolean isLoggedIn = false;			//If client is logged in
	Boolean isRegistered = false;		//If username exists
	Boolean isAdministrator = false;	//If the user is admin
	Boolean isConsole = false;			//If he is using console
	Boolean isPremium = false;			//If he is premium
	String name = this.toString().substring(15);					//Store users Username
	String password = "";
	String mainFileDir = "home/"+name;	//Store users home dir
	Socket socket = null;				//Store users socket object
	String inetv4 = "undefined";		//Store users current ip address
	String pwd = "";
	ServerThread cliThread = null;		//Storujem thread koji runuje clienta da bih mogao da ga kickujem
	
	String root = "home/"; //ZELIM DA MOGU DA NAPRAVIM I REMOTE PRISTUP A NE SAMO SSH, ADMIN MOZE PO CELOM KOMPU, DOK CLIENT MOZE SAMO NORM
	
	int socketID;
	
	//CLIENT IN FILE: username password premium/basic admin/not
	
	public Client(Socket socket, int n) 
	{
		this.socket = socket;
		inetv4 = "" + socket.getRemoteSocketAddress();
		socketID = n; //Which socket it is in array so i can delete it more easily
	}
}
