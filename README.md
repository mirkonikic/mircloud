# mircloud
Encrypted file storage and synchronization service, that can be used localy and globaly.

Use:
  Home storage server:
    - [ ] Encrypted       - No Sniffing of data or MITM attacks (When finished)
    - [ ] Privacy         - No one except you will know what files are you sending on server (When finished)
    - [x] Remote Controll - If isAdministrator is set to true, you can use this program for Remote Control
    - [x] > Google Drive  - While Google and other Drives Limit your storage size, with this program you can use your whole Drive
                          You have an old laptop with 256gb storage, why not install linux and run mircloud_server.jar on it?
    - [x] Multi-User      - Mircloud can be used for multiple users to store their data at the same time, 256 users can be logged in 
                          at the same time(256 for the sake of server working properly on older machines, it can be changed tho)
                          while any number of users can create their folder and store their data on the server.
  
  

![alt text](Untitled.png)

________________________________________________________________________________________
Requirements:
  Installed java on OS which is running the program.

How to use:
  Run the mircloud_server.jar in either terminal or cmd with the following command:
    java -jar mircloud_server.jar
  It will request a ip address and a port to run on, if not specified it will run on:
    127.0.0.1 9090
  
  If you are using it on a private network no port forwarding is needed.
  Just open the mircloud_cli.jar on any other computer inside of the network and input
    the ip address and the port of the PC running the server program.
    
  If trying to run it on public network, for example remote connection from school to your
    home server, or storing a file from school PC to your home storage server without USB
    transfering, you need to open a port on your gateway, than connect that port to the PC
    that is running the _server program.
________________________________________________________________________________________
