#### Network troubleshoot

* ping

* traceroute

#### default port

* Web (HTTP): Port 80
* Web (HTTPS - secure): Port 443
* File Transfer Protocol (FTP): Port 21
* Secure Shell (SSH): Port 22
* Simple Mail Transfer Protocol (SMTP): Port 25
* Post Office Protocol (POP3): Port 110
* Interactive Mail Access Protocol (IMAP): Port 143
* Remote Desktop Protocol (RDP): Port 3389
* Database (SQL Server): Port 1433 

#### When typing a url and hit enter
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

#### IP Addressing


#### Routing protocols

![alt text](<attachments/routing protocol.jpeg>)