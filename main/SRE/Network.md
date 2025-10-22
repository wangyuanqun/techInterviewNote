### Network troubleshoot

* ping
    * checks whether a remote device is available or not

* traceroute (right after if ping isn't ok)
    * check each hop to find the problematic step
    * ![alt text](<attachments/traceroute.png>)

### default port

* Web (HTTP): Port 80
* Web (HTTPS - secure): Port 443
* File Transfer Protocol (FTP): Port 21
* Secure Shell (SSH): Port 22
* Simple Mail Transfer Protocol (SMTP): Port 25
* Post Office Protocol (POP3): Port 110
* Interactive Mail Access Protocol (IMAP): Port 143
* Remote Desktop Protocol (RDP): Port 3389
* Database (SQL Server): Port 1433 

### When typing a url and hit enter
* reference: https://syedali.net/2013/08/18/what-happens-when-you-type-in-www-cnn-com-in-your-browser/
* DNS resolution: when a domain use is used for the url
    * ![alt text](<attachments/when typing a url.jpeg>)
* netwwork communication
    1. 3 way TCP handshake happens to establish the connection between the client and the server; syn -> sync and ack -> ack
        * the process is the client check its routing table to find the server's entry, if not found, send it to default gateway, the default gateway check its own routing table, if not found, send it to default gateway util reached to **internet gateway which is running BGP**. The BGP routing table contains a list of public ip address that assigned to the ISP.
    2. Once it reaches to the server, the server will acknowledge the sync packet and send its own; the second part of 3 way handshake. And it sends back the packet in the same way of the client does.
    3. When the client receives the packet, it sends back an ACK then the connection is established.
    * How does the client know if the host is in its network or not?
        * **netmask** 255.255.255.0

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

### Network model

* Application layer: 
    * DNS - protocol
        * when a domain name is used, then a DNS request is created
    * HTTP - port 80
    * HTTPS - port 443

=========================================
* Transport layer
    * port addresses
        * src/des are used for services replications
    * TCP
        * three-way handshake
    * UDP
        * connectionless 

* Network layer
    * routing protocol
    * IP - protocol
        * src/des are used to identify the devices on the network
        * how to get the ip address - linux/macOs: ifconfig; windows: ipconfig
    * NAT (for IPv4 lack) - network address translation, able to use 1 public ip address per home/company, within them, use private ip address for different address
    * ip address

* Data link layer - abstractions that convert bytes to human-read/hear/touch
    * ARP - address resolution protocol
        * used to configure which MAC address will be sent to at this layer
        * broadcasting to the local network with ip address, and the target will send back the MAC address once receive the request

=========================================
* Physical layer
    * Ethernet - protocol
        * src/des are burned in the interface card that can't be changed

### IP Addressing

* subnet mask
    * 255.255.255.0 is the same as /24, also tells the network portion and the rest are host portion e.g. 192.168.1.8/24
    * is used to seperate the IP address into network portion and host portion to define the boundaries

* convert binary to decimal
    * $2^7 = 128$ ... $2^0 = 1$

* IP address to binary
    * 192.168.1.8 -> 11000000.10101000.00000001.00001000

### Routing protocols

**What is Routing?**
* Definition: Routing is the process of moving data across networks.
* How Routers Work:
    * Routing table: Stores informtaion on network paths
    * Path Selection: Routers choose the best route
* Analogy: Like using GPS to choose the route.

**Why is Routing important?**
* Networks grow, and efficient routing helps manage more users
* Reliability: Ensure the data reaches its destination even it fails due to congestion/maintenance.
* Scalability: Supports growth as network expands

**Static vs. Dynamic Routing**

* static routing: manually configured paths
    * Advan: Simple setup, useful for samll networks
    * Disadvan: Doesn't adapt to networks changes
* dynamic routing: automatically updates paths based on network changes
    * Advan: Adaptable and scalable
    * Disadvan: More complex to configure

* Analogy: Dynamic routing adapts like a GPS that reroutes you based on traffic

#### Dynamic routing protocols

![alt text](<attachments/routing protocol.jpeg>)

#### RIP (Routing Information Protocol)
* A distance-vector protocol
    * **Hop Count**: Measures the number of routers a packet passes through
* Simple but limited: Best for small networks
    * limit
        * max 15 hops, beyond that considered unreachable to avoid routing loop
        * only consider hop counts, doesn't consider congestion/link speed

* **How RIP works**
    * Routing table updates: Routers share their information every 30 secs
        * Distance vector: Each router only knows its immediate neighbour's routes
    * Tip: RIP is like asking your neighbours about nearby places rather than getting directions for distant locations

* RIP versions
    * RIP v1: Classful routing (**does not** support subnetting, lacks flexibility)
    * RIP v2: Classless routing (support subnetting)
        * support authentication
    * RIP v2 is more morden, allowing better flexity and security

* RIP in Real life
    * Use case: small office or home networks
    * Example: A small business with fewer than 15 routers
    * Tip: RIP works when your network does not grow much

#### OSPF (Open Shortest Path First)
* Link-State Protocol: Consider the state of network links
    * Cost metric: Based on link speed, not just the number of hops
* Hierarchical Design: Divides networks into areas ofr efficiency
    * Area 0 (Backbone): The core area where all other areas connect

* **How OSPF works**
    * LSAs (Link-State Advertisements): Routers share information about their links
        * Link-State Database: All routers in an area build the same map of the network
    * Shortest Path Calculation: Use Dijkstra's algorithm
        * Cost Metric: Path selection based on the speed and reliability of links
    * Tip: OSPF updates quickly, meaning it can reroute data in case of outages

* OSPF Areas
    * Area 0 (Backbone): Central to all OSPF networks
        * Purpose: Ensure efficient routing across multiple areas
    * Other Areas: Divides large networks for better management
        * Area Border Routers (ABR): Connects different areas to the backbone
    * Advan: Minimize the routing table and improve the efficiency
    * Tip: Areas in OSPF work like neighbourhoods connecting to a city center

* OSPF in Real life
    * Use case: Large enterprise networks or ISPs
        * Why use?: Scalable, fast convergence, handles complex network topologies
    * Example: A large company with multiple regional offices
    * Advan: Reliable and fast adapation to network changes
    * Tip: OSPF is ideal when company's network grows and needs fast response to changes

#### BGP (Border Gateway Protocol)

* Path-Vector Protocol: Routes between autonomous systems (AS)
    * What is AS?: A large network or collection of networks under a common policy
    * Tip: BGP is like a set of rules that ISPs use to direct traffic across the world

* **How BGP works**
    * AS-Path Attribute: Tracks the ASes a route has passed through
        * Shortest AS-Path: The route with fewer ASes is preferred
    * Next-Hop Attribute: The next router that packets should be sent to
    * **Policy-Based Routing**: BGP allows organizations to control how traffic flows based on business policies
    * Tip: BGP uses different rules to ensure traffic flows the way ISPs want it

    * MED (Multi-Exit Discriminator): Helps influnce which path to choose when there are multiple exit points from an AS

* BGP in Real life
    * Use case: ISPs, large enterprises, or global networks
        * Why use?: Flexibility in managing global traffic and control over routing policies
    * Example: Internet traffic routing between two ISPs
    * Tip: BGP is the protocol used to make the internet work across the globe