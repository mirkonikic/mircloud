package mircloud_drive;

import java.net.*;
import java.util.*;
import java.io.*;

public class ServerThread extends Thread{
	Socket socket = null; //Current socket
	Client client = null; //Current client
	ServerCLI storage = null; //Object holding all sockets
	
	String lastCmd = ""; //Stores the most recent command so that we can check errors
	
	String dat_files = "home/admin/user.dat";
	
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
	
	//Stores REPLY CODES that require help argument
	public String replyCode(int code, String help) 
	{
		switch(code) 
		{
			case 110:
				return "110 Restart marker reply.";
			case 120:
				return "120 Service ready in "+help+" minutes.";
			case 125:
				return "125 Data connection already open; transfer starting.";
			case 150:
				return "150 File status okay; about to open data connection.";
			case 200:
				return "200 Okay. "+help;
			case 202:
				return "202 Command not implemented, superfluous at this site.";
			case 211:
				return "211 System status, or system help reply.";
			case 212:
				return "212 Directory status: "+help; //LIST COMMAND
			case 213:
				return "213 File status: "+help;
			case 214:
				return "214 Welcome to MirCloud alpha version.";
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
				return "230 User logged in, welcome "+client.name+", you can proceed.";
			case 250:
				return "250 Requested file action okay, completed.";
			case 257:
				return "257 "+help;	
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
	
	//Stores REPLY CODES that require help argument
	public String replyCode(int code) 
	{
		switch(code) 
		{
			case 110:
				return "110 Restart marker reply.";
			case 125:
				return "125 Data connection already open; transfer starting.";
			case 150:
				return "150 File status okay; about to open data connection.";
			case 200:
				return "200 Okay.";
			case 202:
				return "202 Command not implemented, superfluous at this site.";
			case 211:
				return "211 System status, or system help reply.";
			case 212:
				return "212 Directory status.";
			case 213:
				return "213 File status.";
			case 214:
				return "214 Welcome to MirCloud alpha version.";
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
			case 250:
				return "250 Requested file action okay, completed.";
			case 331:
				return "331 User name okay, need password.";	
			case 332:
				return "332 Need account for that action.";	
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
	
	public void reply(int code, String help) 
	{
		out.println(replyCode(code, help));
	}
	
	/*
	 * COMMANDS:
-Access Control Commands
	USER [Username]						- Send username
	PASS [Password]						- Send password
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
			case "CWD": //cd <path>
				requestCwd(arr);
				lastCmd = "CWD";
				break;
			case "CDUP": //cd ..
				requestCdup();
				lastCmd = "CDUP";
				break;
			case "QUIT": //exit
				removeClient();
				reply(200);
				lastCmd = "QUIT";
				break;
			//U
			case "TYPE": //
				lastCmd = "TYPE";
				break;
			//U
			case "STRU": //
				lastCmd = "STRU";
				break;
			//U
			case "MODE": //
				lastCmd = "MODE";
				break;
			//U
			case "RETR": //Request a file
				lastCmd = "RETR";
				break;
			//U
			case "STOR": //Store a file, if it exists it gets replaced
				lastCmd = "STOR";
				break;
			//U
			case "STOU": //Store a file, if exists create unique name
				lastCmd = "STOU";
				break;
			//U
			case "APPE": //Append to a file
				lastCmd = "APPE";
				break;
			//U
			case "RNFR": //Rename a file, provides curr name of file, which is always followed by RNTO
				lastCmd = "RNFR";
				break;
			//U
			case "RNTO": //Provides to what should file be renamed
				lastCmd = "RNTO";
				break;
			//U
			case "ABOR": //Abort current data sending
				lastCmd = "ABOR";
				break;
			//U
			case "DELE": //Delete a file
				lastCmd = "DELE";
				break;
			//U
			case "MKD": //Make a directory
				lastCmd = "MKD";
				break;
			//U
			case "RMD": //Remove a directory
				lastCmd = "RMD";
				break;
			case "PWD": //Print current directory
				requestPwd();
				lastCmd = "PWD";
				break;
			case "LIST": //ls <file> - returns info about file or ls <dir> - returns all files
				requestList(arr);
				lastCmd = "LIST";
				break;
			case "HELP": //Returns info about server
				reply(214); //HELP reply code, useful only to human user
				lastCmd = "HELP";
				break;
			case "NOOP": //Noop instruction, returns 200 OK always
				reply(200);
				lastCmd = "NOOP";
				break;
			//U
			case "PORT": //Changes data port i think
				lastCmd = "PORT";
				break;
			case "S":
				serverCommand(msg);
				lastCmd = "S";
				break;
			default:	 //Bad instruction
				reply(500);
				lastCmd = "undefined";
				break;
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
				if(client.isRegistered == true)   //USER EXISTS
				{
					if(cmd[1].equals(client.password)) 
					{
						client.mainFileDir = client.root+client.name+"/";
						client.pwd = client.root+client.name+"/";
						
						reply(230, client.name); //User logged in, proceed
						client.isLoggedIn = true;
						storage.srprint("User "+client.name+" is logged in!");
					}
					else 
					{
						reply(530); //User not logged in.
						client.name = "User";
					}
				}
				else   //USER DOES NOT EXIST
				{
					try {
						dat_in = new BufferedWriter(new FileWriter(dat_files, true));
						PrintWriter dat_inp = new PrintWriter(dat_in);
						
						dat_inp.println(client.name + " " + cmd[1] + " " + "b" + " " + "n");
						
						dat_inp.close();
						dat_in.close();
						
						client.password = cmd[1]; //set password to object
						client.isRegistered = true; //let password function that it is registered
						client.isAdministrator = false;
						client.isPremium = false;
						
						client.mainFileDir = client.root+client.name+"/";
						client.pwd = client.root+client.name+"/";
						
						//CREATE HIS HOME DIRECTORY
						File dir = new File(client.mainFileDir);
						if(dir.mkdirs())
						{
							storage.srprint("Created home directory for "+client.name);
						}
						else
						{
							storage.srerror("Couldn't create home directory for "+client.name);
						}
						
						reply(230, client.name); //User logged in, proceed
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
	
	//Change working directory to specified one
	public void requestCwd(String[] cmd) 
	{
		//Check if user is logged in
		if(client.isLoggedIn) 
		{
			//Proveri dal ides u folder u main folderu ili u neki u curr folderu
			if(cmd[1].equals("/")) 
			{
				File f = new File(client.mainFileDir + cmd[1].substring(1));
				if(f.isDirectory()) 
				{
					client.pwd = client.mainFileDir + cmd[1].substring(1);
					reply(200);
				}
				else 
				{
					reply(550);
				}
			}
			else if(cmd[1].charAt(0) == '/') 
			{
				File f = new File(client.mainFileDir + cmd[1].substring(1) + "/");
				if(f.isDirectory() && !f.getName().contains("."))
				{
					client.pwd = client.mainFileDir + cmd[1].substring(1) + "/";
					reply(200);
				}
				else 
				{
					reply(550);
				}
			}
			else 
			{
				File f = new File(client.pwd+cmd[1]);
				if(f.isDirectory() && !f.getName().contains("."))
				{
					client.pwd = client.pwd + cmd[1] + "/";
					reply(200);
				}
				else 
				{
					reply(550);
				}
			}
		}
		else 
		{
			reply(332);
		}
	}
	
	public void requestCdup() 
	{
		if(client.isLoggedIn) 
		{
			if(client.isAdministrator) 
			{
				String[] path = client.pwd.split("/");
				if(path.length <= 1) 
				{
					reply(503);
				}
				else 
				{
					client.pwd = "";
					for(int i = 0; i<(path.length-1); i++) 
					{
						System.out.println(path[i]);
						client.pwd += path[i]+"/";
					}
					reply(250);
				}
			}
			else 
			{
				String[] path = client.pwd.split("/");
				if(path.length <= 2) 
				{
					reply(503);
				}
				else 
				{
					client.pwd = "";
					for(int i = 0; i<(path.length-1); i++) 
					{
						System.out.println(path[i]);
						client.pwd += path[i]+"/";
					}
					reply(250);
				}
			}
		}
		else 
		{
			reply(332);
		}
	}
		
	public void requestPwd() 
	{
		if(client.isLoggedIn) 
		{
			reply(257, client.pwd);
		}
		else 
		{
			reply(332);
		}
	}
	
	//Lists files in current folder or if specified info about file
	public void requestList(String[] cmd) 
	{
		//Proveri dal je ulogovan
		if(client.isLoggedIn) 
		{
			//Checkuj dal je proviedovao file ili samo LIST
			if(cmd.length == 1) 
			{
				File f = new File(client.pwd);
				String[] files = f.list();
				String repl = "";
				for(String pth : files)
				{
					File dir = new File(client.pwd+pth);
					if(dir.isDirectory() && !dir.getName().contains("."))
					{
						repl += "D|" + pth + "   ";
					}
					else 
					{
						repl += "f|" + pth + "   ";
					}
				}
				reply(212, repl);	
			}
			else if(cmd.length == 2)
			{
				File f = new File(client.pwd+cmd[1]);
				if(f.isFile()) 
				{
					reply(213, " " + f.getName() + " " + f.length() + " Bytes");	
				}
				else if(f.isDirectory()) 
				{
					String[] files = f.list();
					String repl = "";
					for(String pth : files)
					{
						File dir = new File(client.pwd+pth);
						if(dir.isDirectory() && !dir.getName().contains("."))
						{
							repl += "D|" + pth + "   ";
						}
						else 
						{
							repl += "f|" + pth + "   ";
						}
					}
					reply(212, repl);	
				}
				else 
				{
					reply(550);
				}
			}
		}
		else 
		{
			reply(332);
		}
	}
	
	public void serverCommand(String msg) 
	{
		if(client.isAdministrator) 
		{
			String[] cmd = msg.split("\\s+");
			if(cmd.length == 2) 
			{
				switch(cmd[1])
				{
					case "EXIT":
						storage.srShutDown();
				}
			}
			else if(cmd.length > 2) 
			{
				switch(cmd[1]) 
				{
					case "LIST":
						out.println(storage.srlist(Arrays.copyOfRange(cmd, 1, cmd.length)));
						break;
					case "PRINT":
						storage.srprint(msg.substring(8));
						break;
					case "MSG":
						storage.messageClient(Arrays.copyOfRange(cmd, 1, cmd.length));
						break;
					case "KICK":
						storage.kickClient(Arrays.copyOfRange(cmd, 1, cmd.length));
						break;
				}
			}
			else 
			{
				reply(500);
			}
		}
		else 
		{
			reply(500);
		}
	}
	
	//Searches for username in dat files
	public void findInDatFiles(String sen) 
	{
		try {
			dat = new BufferedReader(new FileReader(dat_files));

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
					client.mainFileDir = client.root+client.name+"/";
					client.pwd = client.root+client.name+"/";
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
	
	public void run() 
	{
		//INPUT and OUTPUT
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//out.println("poz uspelo!");
			
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
