# mircLoud - A heavily encrypted File Storage 
Encrypted file storage and synchronization service, that can be used localy and globaly.

 [![Tweet](https://img.shields.io/twitter/url/http/shields.io.svg?style=social)](https://twitter.com/intent/tweet?text=Do%20you%20plan%20to%20make%20a%20home%20server%3F%20Look%20no%20further.%20Mircloud%20is%20the%20right%20tool%20for%20the%20job.&url=https://github.com/mirkonikic/mircloud&hashtags=tech,homelab)

![alt text](Untitled.png)
<!--<img src="https://cdn.discordapp.com/attachments/205245036084985857/481213000540225550/full_example.gif"  alt="Full Example"/>-->

**Usage**
---

```
Usage: stronghold.py [OPTIONS]

  Securely configure your Mac.
  Developed by Aaron Lichtman -> (Github: alichtman)


Options:
  -lockdown  Set secure configuration without user interaction.
  -v         Display version and author information and exit.
  -help, -h  Show this message and exit.
```

**Installation Options**
---

1. Install with [`pip`](https://pypi.org/project/stronghold/)
    + `$ pip install stronghold`
    + `$ stronghold`

2. Download the `stronghold` binary from Releases tab.


**Configuration Options**
---

1. Firewall

    + Turn on Firewall?
        - This helps protect your Mac from being attacked over the internet.
    + Turn on logging?
        - If there IS an infection, logs are useful for determining the source.
    + Turn on stealth mode?
        - Your Mac will not respond to ICMP ping requests or connection attempts from closed TCP and UDP networks.

2. General System Protection

    + Enable Gatekeeper?
    	- Defend against malware by enforcing code signing and verifying downloaded applications before allowing them to run.
    + Prevent automatic software whitelisting?
        - Both built-in and downloaded software will require user approval for whitelisting.
    + Disable Captive Portal Assistant and force login through browser on untrusted networks?
        - Captive Portal Assistant could be triggered and direct you to a malicious site WITHOUT any user interaction.

3. User Metadata Storage

    + Clear language modeling metadata?
        - This includes user spelling, typing and suggestion data.
    + Disable language modeling data collection?
    + Clear QuickLook metadata?
    + Clear Downloads metadata?
    + Disable metadata collection from Downloads?
    + Clear SiriAnalytics database?

4. User Safety

    + Lock Mac as soon as screen saver starts?
    + Display all file extensions?
    	- This prevents malware from disguising itself as another file type.
    + Disable saving documents to the cloud by default?
        - This prevents sensitive documents from being unintentionally stored on the cloud.
    + Show hidden files in Finder?
    	- This lets you see all files on the system without having to use the terminal.
    + Disable printer sharing?
        - Offers redundancy in case the Firewall was not configured.

**How to Contribute**
---

1. Clone repo and create a new branch: `$ git checkout https://github.com/alichtman/stronghold -b name_for_new_branch`.
2. Make changes and test
3. Submit Pull Request with comprehensive description of changes

**Acknowledgements**
---

+ [@shobrook](https://www.github.com/shobrook) for logo and UI design assistance.
+ Base logo vector made by [Freepik](https://www.freepik.com/) from [Flaticon](www.flaticon.com).
+ [drduh's macOS-Security-and-Privacy-Guide](https://github.com/drduh/macOS-Security-and-Privacy-Guide) and [Jonathan Levin's MacOS Security Guide](http://newosxbook.com/files/moxii3/AppendixA.pdf) were incredibly helpful while I was building `stronghold`.

**Donations**
---

This is free, open-source software. If you'd like to support the development of future projects, or say thanks for this one, you can donate BTC at `1FnJ8hRRNUtUavngswUD21dsFNezYLX5y9`.

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
