package mircloud_drive;

import java.net.*;
import java.util.*;
import java.io.*;

public class ServerThread extends Thread{
	Socket socket = null; //Current socket
	Client client = null; //Current client
	ServerCLI storage = null; //Object holding all sockets
	
	String lastCmd = ""; //Stores the most recent command so that we can check errors
	
	PrintWriter out; //Client output 
	BufferedReader in; //Client input
	BufferedReader dat; //Dat files reader
	BufferedWriter dat_in; //Dat files writer
	
	//Ends connection to current client, which will close this thread
	public void removeClient() 
	{
		try {
			in.close();
			out.close();
			socket.close();
			storage.clients[client.socketID] = null;
			storage.updateMin();
		} catch (IOException e) {
			storage.srerror("Couldn't remove client..");
		}		
	}
	
	//Initiates variables
	public ServerThread(ServerCLI serverCLI, Client client) 
	{
		this.storage = serverCLI;
		this.client = client;
		this.socket = client.socket;
	}
	
	//OPENS a new thread and a random port and sends requested data to user
	public void data(String path) 
	{
		
	}
	
	//Stores REPLY CODES
	public String replyCode(int code) 
	{
		switch(code) 
		{
			case 110:
				return "110 Restart marker reply.";
			case 120:
				return "120 Service ready in nnn minutes.";
			case 125:
				return "125 Data connection already open; transfer starting.";
			case 150:
				return "150 File status okay; about to open data connection.";
			case 200:
				return "200 Command okay.";
			case 202:
				return "202 Command not implemented, superfluous at this site.";
			case 211:
				return "211 System status, or system help reply.";
			case 212:
				return "212 Directory status.";
			case 213:
				return "213 File status.";
			case 214:
				return "214 Help message.";
			case 215:
				return "215 NAME system type.";
			case 220:
				return "220 Service ready for new user";
			case 221:
				return "221 Service closing control connection(Logged out).";
			case 225:
				return "225 Data connection open; no transfer in progress.";
			case 226:
				return "226 Closing data connection.";
			case 227:
				return "227 Entering Passive Mode (h1,h2,h3,h4,p1,p2).";
			case 230:
				return "230 User logged in, proceed.";
			case 250:
				return "250 Requested file action okay, completed.";	
			case 257:
				return "257 \"PATHNAME\" created.";	
			case 331:
				return "331 User name okay, need password.";	
			case 332:
				return "332 Need account for login.";	
			case 350:
				return "350 Requested file action pending further information.";	
			case 421:
				return "421 Service not available, closing control connection.";	
			case 425:
				return "425 Can't open data connection.";	
			case 426:
				return "426 Connection closed; transfer aborted.";	
			case 450:
				return "450 Requested file action not taken.(File unavailable/busy)";	
			case 451:
				return "451 Requested action aborted: local error in processing.";	
			case 452:
				return "452 Requested action not taken. Insufficient storage.";	
			case 500:
				return "500 Syntax error, command unrecognized.";	
			case 501:
				return "501 Syntax error in parameters or arguments.";	
			case 502:
				return "502 Command not implemented.";	
			case 503:
				return "503 Bad sequence of commands.";	
			case 504:
				return "504 Command not implemented for that parameter.";	
			case 530:
				return "530 Not logged in.";	
			case 532:
				return "532 Need account for storing files.";	
			case 550:
				return "550 Requested action not taken(file not found, no access).";	
			case 551:
				return "551 Requested action aborted: page type unknown.";	
			case 552:
				return "552 Requested file action aborted. Exceeded storage allocation.";	
			case 553:
				return "553 Requested action not taken. File name not allowed.";	
			default:
				return "500 Syntax error, command unrecognized.";
		}
	}
	
	//SEND A REPLY
	public void reply(int code) 
	{
		out.println(replyCode(code));
	}
	
	/*
	 * COMMANDS:
-Access Control Commands
	USER [Username]						- Send username
	PASS [Password]						- Send password
	ACCT [Account] 						- Request account info
	CWD  [ChangeWorkingDirectory]		- cd <path>
	CDUP [ChangeToParentDirectory]		- cd ..
	QUIT [LOGOUT]						- exit

-Transfer Parameter Commands
	TYPE [RepresentationType]			- 
	STRU [FileStructure]				- 
	MODE [TransferMode]					- 

-Ftp Service Commands
	RETR [Retrieve]						- Request a file
	STOR [Store]						- Store a file, if it exists it gets replaced
	STOU [StoreUnique]					- Store a file, if exists create unique name
	APPE [Append]						- Append to a file
	RNFR [RenameFrom]					- Request renaming a file
	RNTO [RenameTo]						- Follow RNFR with a name to which it should rename
	ABOR [Abort]						- Abort current transfer of data
	DELE [Delete]						- Delete a file
	RMD  [DemoveDirectory]				- Remove directory
	MKD  [MakeDirectory]				- Create directory
	PWD  [PrintWorkingDirectory]		- Request path of current directory
	LIST [List]							- List out filenames and dirnames in cwd 
											or info about file
	NLST [NameList]						- List only filenames in cwd
	HELP [Help]							- Request help
	NOOP [Noop]							- NOP instruction reply with OK
	 */
	//Parses user input/ request and acts acordingly
	public void parse(String msg) 
	{
		String[] arr = msg.split("\\s+");
		switch(arr[0]) 
		{
			case "USER": //Sends username
				requestUser(arr);
				lastCmd = "USER";
				break;
			case "PASS": //Sends password
				requestPass(arr);
				lastCmd = "PASS";
				break;
			case "ACC": //Requests for info about account
				lastCmd = "ACC";
				break;
			case "CWD": //cd <path>
				lastCmd = "CWD";
				break;
			case "CDUP": //cd ..
				lastCmd = "CDUP";
				break;
			case "QUIT": //exit
				removeClient();
				lastCmd = "QUIT";
				break;
			case "TYPE": //
				lastCmd = "TYPE";
				break;
			case "STRU": //
				lastCmd = "STRU";
				break;
			case "MODE": //
				lastCmd = "MODE";
				break;
			case "RETR": //Request a file
				lastCmd = "RETR";
				break;
			case "STOR": //Store a file, if it exists it gets replaced
				lastCmd = "STOR";
				break;
			case "STOU": //Store a file, if exists create unique name
				lastCmd = "STOU";
				break;
			case "APPE": //Append to a file
				lastCmd = "APPE";
				break;
			case "RNFR": //Rename a file, provides curr name of file, which is always followed by RNTO
				lastCmd = "RNFR";
				break;
			case "RNTO": //Provides to what should file be renamed
				lastCmd = "RNTO";
				break;
			case "ABOR": //Abort current data sending
				lastCmd = "ABOR";
				break;
			case "DELE": //Delete a file
				lastCmd = "DELE";
				break;
			case "MKD": //Make a directory
				lastCmd = "MKD";
				break;
			case "RMD": //Remove a directory
				lastCmd = "RMD";
				break;
			case "PWD": //Print current directory
				lastCmd = "PWD";
				break;
			case "LIST": //ls <file> - returns info about file or ls <dir> - returns all files
				lastCmd = "LIST";
				break;
			case "NLST": //returns only filenames like ls
				lastCmd = "NLST";
				break;
			case "HELP": //Returns info about server
				lastCmd = "HELP";
				break;
			case "NOOP": //Noop instruction, returns 200 OK always
				reply(200);
				lastCmd = "NOOP";
				break;
			case "PORT": //Changes data port i think
				lastCmd = "PORT";
				break;
			default:	 //Bad instruction
				reply(500);
				lastCmd = "undefined";
				break;
		}
	}
	
	//Searches for username in dat files
	public void findInDatFiles(String sen) 
	{
		try {
			dat = new BufferedReader(new FileReader("user.dat"));

			String line = dat.readLine(); //Reads a line from user.dat
			String[] parse; //Parses the line as bash does
			while(line != null) 
			{
				parse = line.split("\\s+"); //parses it
				if(parse[0].equals(sen))  //if first or username is equal to searched one
				{
					client.name = sen; //set objects username to this
					client.password = parse[1]; //set password to object
					client.isRegistered = true; //let password function that it is registered
					if(parse[2].equals("p")) //check if he is premium user or not
					{
						client.isPremium = true;
					}
					else 
					{
						client.isPremium = false;
					}
					if(parse[3].equals("a")) //check if he is premium user or not
					{
						client.isAdministrator = true;
					}
					else 
					{
						client.isAdministrator = false;
					}
					client.mainFileDir = "/home/"+client.name;
					client.pwd = "/home/"+client.name;
					dat.close();
					return;
				}
				line = dat.readLine(); //reads line into variable
			}
			
			//STVARI KOJE RADI AKO NIJE REGISTROVAN
			client.name = sen;
			
			try {
				dat.close();
			} catch (IOException e) {
				storage.srerror("Couldn't close dat files.");
			}
		} catch (FileNotFoundException e) {
			storage.srerror("user.dat not found!");
		} catch (IOException e) {
			storage.srerror("IOException kod citanja dat files-a.");
		}
	}
	
	//Primi zahtev za usera, postavi name na tu vrednost, proveri dal postoji i cekaj na password
	public void requestUser(String[] cmd) 
	{
		if(cmd.length != 2) 
		{
			reply(501);
		}
		else 
		{
			storage.srprint("Someone from "+client.inetv4+"is requesting for user "+cmd[1]);
			if(client.isLoggedIn != true) 
			{
				findInDatFiles(cmd[1]);
				reply(331); //User name okay, need password.
			}
			else 
			{
				reply(503); //Bad sequence of commands
			}
		}
	}
	
	//Proveri isRegistered, ako da proveri password, ako ne onda upisi samo
	public void requestPass(String[] cmd) 
	{
		if(cmd.length != 2) 
		{
			reply(501);
		}
		else 
		{
			if(lastCmd.equals("USER")) 
			{
				if(client.isRegistered == true) 
				{
					if(cmd[1].equals(client.password)) 
					{
						client.mainFileDir = "/home/"+client.name;
						client.pwd = "/home/"+client.name;
						
						out.println("230 " + client.name + " welcome."); //User logged in, proceed
						client.isLoggedIn = true;
						storage.srprint("User "+client.name+" is logged in!");
					}
					else 
					{
						reply(530); //User not logged in.
						client.name = "User";
					}
				}
				else 
				{
					try {
						dat_in = new BufferedWriter(new FileWriter("user.dat", true));
						PrintWriter dat_inp = new PrintWriter(dat_in);
						
						dat_inp.println(client.name + " " + cmd[1] + " " + "b" + " " + "n");
						
						dat_inp.close();
						dat_in.close();
						
						client.password = cmd[1]; //set password to object
						client.isRegistered = true; //let password function that it is registered
						client.isAdministrator = false;
						client.isPremium = false;
						
						client.mainFileDir = "/home/"+client.name;
						client.pwd = "/home/"+client.name;
						
						out.println("230 " + client.name + " welcome."); //User logged in, proceed
						client.isLoggedIn = true;
						storage.srprint("User "+client.name+" is logged in!");
					} catch (FileNotFoundException e) {
						storage.srerror("Couldn't register user.");
					} catch (IOException e) {
						storage.srerror("Couldn't register user 2.");
					}
				}
			}
			else 
			{
				reply(503); //Bad sequence of commands.
				client.name = "User";
			}
		}
	}
	
	public void run() 
	{
		//INPUT and OUTPUT
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			out.println("poz uspelo!");
			
			//3 THREADA SEND i RECV i po potrebi DATA
			
			String msg = ""; //Stores input
			while(msg != null) {
				msg = in.readLine();
				if(msg == null) 
				{
					storage.srerror("User disconnected!");
					break;
				}
				storage.srprint("Recieved: "+ msg);
				parse(msg);
			}
			
			removeClient();
			
		} catch (IOException e) {
			//GRESKA
			removeClient();			
		}
	}
}
