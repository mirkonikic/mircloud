package mircloud_klijent;

import java.net.*;
import java.io.*;

public class ClientRead extends Thread{
	Socket client;
	PrintWriter out;
	BufferedReader in;
	BufferedReader tast;
	
	String line;
	String tast_in;
	int wait_time = 100;
	
	Boolean isLoggedIn = false;
	Boolean isAdmin = false;
	String username;
	String password;
	
	public ClientRead(Socket socket)
	{
		client = socket;
	}
	
	public void parse_request(String sen) 
	{
		String[] cmd = sen.split("\\s+");
		
		switch(cmd[0]) 
		{
			case "ls":
				if(cmd.length > 1) {request("LIST", cmd[1]);}
				else {request("LIST");}
				break;
			case "echo":
				srprintln(sen.substring(5));
				request("NOOP");
				break;
			case "pwd":
				request("PWD");
				break;
			case "file":
				request("LIST", cmd[1]);
				break;
			case "cd":
				if(cmd[1].equals("..")) {request("CDUP");}
				else{request("CWD", cmd[1]);}
				break;
			case "whoami":
				srprintln(username);
				request("NOOP");
				break;
			case "exit":
				request("QUIT");
				break;
			default:
				srprintln("mircloud: Unrecognized command \'"+cmd[0]+"\' .");
				break;
		}
	}
	
	public void request(String sen) 
	{
		out.println(sen);
	}
	
	public void request(String sen, String help) 
	{
		out.println(sen+" "+help);
	}
	
	public void parse_reply(String sen) 
	{
		if(sen == null) 
		{
			return;
		}
		String[] cmd = sen.split("\\s+");
		
		switch(cmd[0]) 
		{
			//SUCCESS
			case "212": //LIST
				srprintln(sen.substring(21));
				break;
			case "213": //FILE
				srprintln(sen.substring(16));
				break;
			case "257": //PWD
				srprintln(sen.substring(14));
				break;
			case "200": 
				break;
			case "214": //HELP
				srprintln(sen.substring(4));
				break;
			case "250":
				break;
			//ERRORS
			case "500":
				srprintln(sen.substring(4));
				break;
			case "503":
				srprintln("Bad sequence of commands!");
				break;
			case "550":
				srprintln(sen.substring(4));
				break;
			case "553":
				srprintln("Not allowed!");
				break;
			default:
				clerror("mircloud: Unrecognized command \'"+cmd[0]+"\' .");
				break;
		}
	}
	
	public void clprint(String sen) 
	{
		System.out.print("[*]:"+sen);
	}
	
	public void clprintln(String sen) 
	{
		System.out.println("[*]:"+sen+":[*]");
	}
	
	public void srprint(String sen) 
	{
		System.out.print(">> "+sen);
	}
	
	public void srprintln(String sen) 
	{
		System.out.println(">> "+sen);
	}
	
	public void usprint() 
	{
		if(isAdmin == true) 
		{
			System.out.print(username+":# ");
		}
		else 
		{
			System.out.print(username+":~$ ");
		}
	}
	
	public void clerror(String sen) 
	{
		System.out.println("!!]:"+sen+":[!!");
	}
	
	public void banner() throws InterruptedException 
	{
		System.out.println("                            `:oDFo:`                          ");
		sleep(wait_time);
		System.out.println("                         ./ymM0dayMmy/.                       ");
		sleep(wait_time);
		System.out.println("                      -+dHJ5aGFyZGVyIQ==+-                    ");
		sleep(wait_time);
		System.out.println("                   `:sm⏣~~Destroy.No.Data~~s:`                ");
		sleep(wait_time);
		System.out.println("              -+h2~~Maintain.No.Persistence~~h+-              ");
		sleep(wait_time);
		System.out.println("           `:odNo2~~Above.All.Else.Do.No.Harm~~Ndo:`          ");
		sleep(wait_time);
		System.out.println("        ./etc/shadow.0days-Data'%20OR%201=1--.No.0MN8'/.      ");
		sleep(wait_time);
		System.out.println("    -++SecKCoin++e.AMd`       `.-://///+hbove.913.ElsMNOPg+-  ");
		sleep(wait_time);
		System.out.println("  -~/.ssh/id_rsa.Des-                  `htN01UserWroteMEsece!-");
		sleep(wait_time);
		System.out.println("  :dopeAW.No<nano>o                     :is:TЯiKC.sudo-.ANON]:");
		sleep(wait_time);
		System.out.println("  :we're.all.alike'`                     The.PFYroy.No.Dbash7:");
		sleep(wait_time);
		System.out.println("  :PLACEDRINKHERE!:                      yxp_cmdshell.Ab0bomb:");
		sleep(wait_time);
		System.out.println("  :msf>exploit -j.                       :Ns.BOB&ALICEes7AVdn:");
		sleep(wait_time);
		System.out.println("  :---srwxrwx:-.`                        `MS146.52.No.PerDAAt:");
		sleep(wait_time);
		System.out.println("  :<script>.Ac816/                        sENbove3101.404TLSs:");
		sleep(wait_time);
		System.out.println("  :NT_AUTHORITY.Do                        `T:/shSYSTEM-.NTLS1:");
		sleep(wait_time);
		System.out.println("  :09.14.2011.raid                       /STFU|wall.No.PriAAh:");
		sleep(wait_time);
		System.out.println("  :hevnsntSurb025N.                      dNVRGOING2GIVUUPWOOP:");
		sleep(wait_time);
		System.out.println("  :#OUTHOUSE-  -s:                       /corykennedyDataOoPs:");
		sleep(wait_time);
		System.out.println("  :$nmap -oS                              SSo.6178306EnceMirC:");
		sleep(wait_time);
		System.out.println("  :Awsm.da:                            /shMTl#beats3o.No.Ya.c:");
		sleep(wait_time);
		System.out.println("  :Ring0:                             `dDestRoyREXKC3ta2346/M:");    
		sleep(wait_time);
		System.out.println("  :23d:                               sSETEC.ASTRONOMYSECRETt:");    
		sleep(wait_time);
		System.out.println("  /-                        /yo-    .ence.N:(){ :|: & };:;;{]:");    
		sleep(wait_time);
		System.out.println("                                  `:Shall.We.Play.A.Game?tron/");    
		sleep(wait_time);
		System.out.println("                                    ```-ooy.if1ghtf0r+ehUser5`");    
		sleep(wait_time);
		System.out.println("                                    ..th3.H1V3.U2VjRFNN.jMh+.`");          
		sleep(wait_time);
		System.out.println("                                        `MjM~~WE.ARE.se~~MMjMs");              
		sleep(wait_time);
		System.out.println("                                            +~KANSAS.CITY's~-`");                  
		sleep(wait_time);
		System.out.println("                                                J~HAKCERS~./.`");                    
		sleep(wait_time);
		System.out.println("                                                    .esc:wq!:`");                        
		sleep(wait_time);
		System.out.println("                                                       +++ATH`");                            
		sleep(wait_time);
		System.out.println("                                                             `");
		sleep(wait_time);
		System.out.println("  MirCloud v1.3 Welcome.                                      ");
		sleep(wait_time);
		System.out.println("  admin@mircloud: ~$ echo \'You can feel safe here\'          ");
		sleep(wait_time);
		System.out.println("\n");
		sleep(wait_time);
	}
	
	public void tryLogin() 
	{
		try {
			out.println("USER"+" "+username);
			line = in.readLine();
			
			if(line.split("\\s+")[0].equals("331")) 
			{
				out.println("PASS"+" "+password);
				line = in.readLine();
				if(line.split("\\s+")[0].equals("230")) 
				{
					clprintln("Uspesno logovanje, "+username+" mozete nastaviti sa koriscenjem mircloud aplikacije.");
					isLoggedIn = true;
					if(username.equals("admin")) 
					{
						isAdmin = true;
					}
				}
				else 
				{
					username = "";
					password = "";
					isLoggedIn = false;
					clerror("Wrong credentials.");
				}
			}
			else 
			{
				username = "";
				password = "";
				isLoggedIn = false;
				clerror("Wrong credentials.");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() 
	{
		try {
			//LOGIN
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			tast = new BufferedReader(new InputStreamReader(System.in));
			
			banner();
			
			while(isLoggedIn == false) 
			{
				clprint("Username:");
				username = tast.readLine();
				clprint("Password:");
				password = tast.readLine();
				
				tryLogin();
			}
			//request
			//READ takes over..
			
			//USTVARI NE TREBA TI DVA THREADA, SVEJEDNO SALJES PA PRIMAS!
			
			while(line != null) 
			{				
				//sendline	
				usprint();
				parse_request(tast.readLine());
				
				//parseline
				line = in.readLine();
				//System.out.println(line);
				parse_reply(line);
			}
			
			in.close();	
			
			
		} catch (IOException e) {
			clerror("IOException :/");
		} catch (InterruptedException e) {
			clerror("Banner failed :/");
		}
	}
	
}
