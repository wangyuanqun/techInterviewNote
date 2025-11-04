


## EC2

### What is Amazon EC2?

EC2 instances are virtual servers in the cloud that run your applications and workloads. Each instance is like a virtual computer with its own processing power, memory, and storage.

### How is EC2 used to architect a cloud solution?

![alt text](<attachments/EC2.png>)

1. Internet gateway
    * This is a connection point that allows users to access your public resources and allows these resources to communicate with the internet
2. Application Load Balancer
    * This distributes the incoming traffic across multiple EC2 instances for better performance and avalibility.
3. Public Subnet
    * It is a network segments that contains public-facing resources like NAT gateways and load balancers.
4. NAT gateway
    * It enables private resources to access the internet while preventing unwanted inbound connections (connections from an external network like internet).
5. App subnet
    * This is a private network segment where EC2 instances run your applicaiton code securely.
6. Auto Scaling group
    * It automatically adjusts the number of EC2 instances based on the user demand.
7. Amazon EBS volumes
    * Amazon Elastic Block Store (Amazon EBS) storage volumes are attached to EC2 instances to store application data.
8. Database subnet
    * A highly secured network segment that dedicated to database resources.
9. Amazon Aurora DB (primary and replica)
    * It is a managed databse service that provides both read and write capabilities with automatic replication.

### What AWS services integrate with Amazon EC2?
1. Storage
    * EC2 instances can be paired with Amazon EBS for persistent block storage so you can store and access data independently of the instance lifecycle.
2. Networking
    * With Amazon Virtual Private Cloud (Amazon VPC), you can create isolated network environments for EC2 instances to control access and improve security.
3. Load balancing
    * Elastic Load Balancing (ELB) distributes incoming traffic across multiple EC2 instances to improve availability and fault tolerance.
4. Amazon EC2 Auto scaling
    * Amazon EC2 Auto Scaling automatically adjusts the number of instances based on demand to ensure optimal performance and cost-efficiency.
5. Database service
    * EC2 instances can connect to managed database services, such as Amazon Relational Database Service (Amazon RDS) or Amazon DynamoDB, for structured data storage and retrieval.
6. Monitoring and management
    * Amazon CloudWatch provides monitoring and observability for EC2 instances to help you track performance metrics and set up alarms.
7. Security
    * AWS Identity and Access Management (IAM) integrates with Amazon EC2 to control access to your instances and other AWS resources.
8. Container service
    * With Amazon Elastic Container Service (Amazon ECS) and Amazon Elastic Kubernetes Service (Amazon EKS), you can run containerized applications on EC2 instances to provide flexibility and scalability.


==== **Solutions Architect Associate Udemy** ============================
#### Private vs Public vs Elastic IP
* shouldn't use Elastic IP
    * not good for architectural decisions
    * should use random public IP with DNS name
    * use Load Balancer and don't use public IP
#### Placement group
* **cluster** -clusters instances into a low-latency group in a **single Availability Zone**
    * Great network (10 Gps bandwidth between instances with Enhanced Networking enabled - recommended)
    * **Single point failure**
    * Use case
        * Big Data job that needs to complete fast
        * Application that needs extremely low latency and high nework throughput
* **spread** - spreads instances across underlying hardware (max 7 instances per group per AZ)
    * can span across multiple AZs in the same region
    * reduce the rick of simultaneous failure
    * EC2 are on different physical hardware
    * Use case
        * applciation that needs high availablility
        * critical applications where each instance must be isolated from failure from each other
* **partition** - spreads instances across many different partitions (which rely on different sets of racks) within an AZ. Scales to 100s of EC2 instances per group (Hadoop, Cassandra, Kafka)
    * can span across multiple AZs in the same region
    * A partion failure can affect many EC2s but won't affect other partitions.
    * Use case:
        * HDFS, HBase, Cassandra, Kafka

#### Elastic Network Interface (ENI)
[ENI link](https://aws.amazon.com/blogs/aws/new-elastic-network-interfaces-in-the-virtual-private-cloud/)

* Primary private IPv4, one or more secondary IPv4
* One Elastic IP (IPv4) per private IPv4
* One Public IPv4
* One or more security groups
* A MAC address 

Good for failover -> reattach to a different instance

#### EC2 Hibernate
* stop
    * the data on the disk (EBS) is kept **intact** in the next start
* terminate
    * any EBS volumes (root) also setup to be **destroyed** is lost

<img src="attachments/EC2 Hibernate.jpeg" alt="drawing" width="300"/>

#### To enable EC2 Hiubernate, the EC2 instance Root Volume type must be an EBS volume and must be encrypted to ensure the protection of sensitive content.
Use case:
* Services that takes time to initialize
* Long-running job
* Saving the RAM state

### EC2 Storage

* EBS Volume Types
    * SSD - general - gp2 / gp3
        * good for developemt and test environment
    * High-performance SSD - **Provisioned IOPS SSD volumes** - io1/io2
        * good for database workloads (sensitive to storage perf)
    * low cost HDD - **Throughput Optimized HDD volumes** - stl
        * good for big data, data warehousing, log processing
    * lowest cost HDD - cold HDD volumes - scl
        * good for lowest cost storage is important
    * **Only gp2/gp3 and io1/io2 can be used as boot volumes**

* EBS multi- Attach - only for **io1/io2 family**
    * attach the same EBS to multiple EC2 in the same AZ
    * **Up to 16 EC2 instances at a time**
    * applications must manage **concurrent** write operations

* EBS Encryption
    * encrypt an unencrypted EBS volume
        * Create a snapshot of the volume
        * Encrypt the snapshot **(copy snapshot allows encryption)**
        * create a new EBS volume from the snapshot (the volume will be also be encrypted)
        * attach the new EBS volume to the original instance
    * or create EBS volume from unencrypted snapshot by choosing "Encrypt this volume"

* EFS - Elastic File System
    * managed NFS (network file system) that can be mounted on manyEC2
    * EFS works with EC2 instances in multi-AZ
    * Highly available, scalable, expensive (3x gp2), pay per use
    * **compatible with Linux based AMI (not Windows)**
    * Use case
        * content management, web serving, data sharing, Wordpress
    * Use Lifecycle policy to control **storage tiers**
        * standard
        * infrequent access (EFS - IA)
        * archive
    * Performance settings
        * Bursting - basic performance
        * Elastic - unpredictable I/O, automatically adjust
        * Provisionsed - customaize the throughput and pay for it

* AMI (Amazon Machine Image) - a **customization** of EC2 Instance
    * launch option
        * public AMI: AWS provided
        * your own AMI: you make and maintain them yourself
        * AWS Marketplace AMI: someone else make and sell
    * AMIs are built for a specific AWS region, they are unique for each AWS region. Can't launch EC2 insatnce with the AMI in another region, but can copy the AMI to the target region.

* EC2 Instance Store
    * EBS volumes are network drives with good but "limited" performance
    * If neeed **high-performance** hardware disk, use EC2 Instance Store
        * BetterI/O performance
        * it loses storage if it's stopped
        * Good for buffer/cache/scratch data/temprary content
        * risk of data loss if hardware fails
        * Backups and Replication are your responsibility

#### Scalability & High Availability