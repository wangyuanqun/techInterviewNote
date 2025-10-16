## SRE preparation link
https://github.com/mxssl/sre-interview-prep-guide


### Linux Command

* tree -d
* cat -N \<file>
* less -N \<file>
* head -n 20 \<file>
* tail -n 15 \<file>
* rmdir \<folder> foler needs to be empty
* rmdir -v \<folder> verbose
* rmdir -p \<foler/foler> remove nested folder if all empty
* rm -rf \<foler> to remove all the content, -f option to force deletion without prompting, even for write-protected files.
* mv
* wildcards
    * ls ba?.out
    * ls \*.out
* du -sch *: s - summary, c - total disk usage, h - human readable
* top
* sar -u 4 5: 5 times with 4 secs each
* ps aux
    * a: Displays information about processes from all users.
    * u: Provides a user-oriented format, showing detailed information about the processes.
    * x: Lists processes without a controlling terminal, typically those started at boot time and running in the background.

### Linux Boot process

* Power supply initialization - at this stage, the components receive stable power to operate correctly, e.g. motherboard, CPU, GPU, cooling fans.
* BIOS/UEFI startup and POST - BIOS/UEFI is responsible for POST, error handling, and hardware initialization. POST: Power-on self test to check hardware components working correctly.
* Boot loader - the job of boot loader is to load the actual operating system kernel into memory. 
    * OS kernel - is responsible for managing the system's resources and facilitating communication between hardware and software components.
* Kernel and Init process - so that it can perform tasks, such as managing CPU, memory, and hardware devices; initializing system drivers; preparing user space.
* Starting system services and daemons - such as networking services, printing services, security services.
* User login and Desktop Environment - this gives the user a graphical interface to interact with applications and the underlying hardware.

### Network

* Web (HTTP): Port 80
* Web (HTTPS - secure): Port 443
* File Transfer Protocol (FTP): Port 21
* Secure Shell (SSH): Port 22
* Simple Mail Transfer Protocol (SMTP): Port 25
* Post Office Protocol (POP3): Port 110
* Interactive Mail Access Protocol (IMAP): Port 143
* Remote Desktop Protocol (RDP): Port 3389
* Database (SQL Server): Port 1433 

When typing a url and hit enter
* ![alt text](<attachments/when typing a url.jpeg>)

#### DNS record explained
* A records - **Domain name -> IP address**
![alt text](<attachments/A record.jpeg>)
* Name Servers - **second level domain -> responsible Authoritative DNS server**
![alt text](<attachments/Name server.jpeg>)
* AAAA Records - **Domain name -> IP address**
![alt text](<attachments/AAAA record.jpeg>)
* Mail Server  - **tells email server where to send your messages for your domain**
![alt text](<attachments/MX Record.jpeg>)
* PTR Record - **security purpose, check the correct ip address with domain name**
![alt text](<attachments/PTR Record.jpeg>)
* CNAME Record - **Alias -> Real**
![alt text](attachments/CNAME.jpeg)

#### Network model

* Application layer
    * DNS - protocol
    * DHCP - protocol
    * HTTP - protocol

=========================================
* Transport layer
    * TCP/UDP - protocol
    * port addresses
        * src/des are used for services replications

* Network layer
    * IP - protocol
        * src/des are used to identify the devices on the network
        * how to get the ip address - linux/macOs: ifconfig; windows: ipconfig
    * NAT (for IPv4 lack) - network address translation, able to use 1 public ip address per home/company, within them, use private ip address for different address

* Data link layer - abstractions that convert bytes to human-read/hear/touch

=========================================
* Physical layer
    * Ethernet - protocol
        * src/des are burned in the interface card that can't be changed


#### Routing protocols

![alt text](<attachments/routing protocol.jpeg>)