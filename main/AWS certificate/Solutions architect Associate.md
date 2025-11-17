


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

* Elastic Load Balancer is a managed load balancer
    * AWS guarantees it will be working
    * AWS takes care of upgrades, maintance, high availablity
    * AWS provides only a few configuration options
    * 4 kinds of managed load balancers
        * Application LB: HTTP HTTPS, WebSocket
        * Network LB: TCP, TLS(secure TCP), UDP - ultra high performance
        * Gateway LB: Operates at network layer - IP protocol
        * not used - Classic LB

* Application LB
    * is great for **micro servicews & container-based application** - Docker & Amazon ECS
    * Can use Security Groups to control the inbound and outbound connections
        * for example, for the instances under ALB, edit the inbound rules to only allow the security group that the ALB is in.
    * The instances can only see the private ip address of the ALB, to get the client's ip addresses, ALB adds a header called **X-Forwarded-For**

* Network LB - ultra high performance
    * it has **one static IP per AZ**, and supports assigning Elastic IP
    * target groups
        * EC2 instances
        * **private** IP address
        * Application Load balancer
    * **Health check support TCP, HTTP and HTTPS Protocols**

* Gateway Load Balancer
    * **GENEVE protocol on port 6081**
    * target groups
        * EC2 instances
        * **private** IP address
    * <img src="attachments/Gateway Load Balancer.jpeg" width=200/>

* ELB - sticky Sessions
    * This works with **ALB and NLB**
    * Use case:
        * make sure the user does not lose his session data
    * Cookie name
        * Application-based cookie
            * custom cookie - generated by the target
            * application cookie - generated by the load balancer **AWS ALB APP**
        * duration-based cookie
            * generated by load balancer **AWS ALB**

* ELB - Cross-Zone LB
    * <img src="attachments/Cross zone LB.jpeg" width=1200/>
    * by default, enabled for ALB
    * by default, disabled for NLB and Gateway LB

* ELB - SSL/TLS
    * An SSL cetificate allows traffic between your clients and your load balancer to be encrypted in transit (in-flight encryption)
    * SSL - Secure Sockets Layer
    * TLS - Transport Layer Security, newer version
    * **SNI Sserver Name Indication**
        * solves the problem of loading multiple SSL certificates onto one web server

* **Deregistration Delay**
    * it give some time for your instances to complete the "in-flight requests" while the instance is de-registered or unhealthy
    * <img src="attachments/Deregisteration delay.jpeg" width=200/>

* Auto Scaling Group
    * Scale out (add EC2 instances) to match increased load
    * Scale in (remove EC2 instances) to match decreased load
    * Ensure having a minimum and max number of EC2 instace
    * Automatically register new instances to a load balancer 
    * Re-create an EC2 instance in case the previous one is terminated (unhealthy)
    * Free
    * **Launch Template**
        * AMI + instance type
        * EC2 User data
        * EBS Volume
        * Security Groups
        * SSH Key pair
        * IAM roles for the EC2 instances
        * Network + subnet information
        * Load balancer information
        * min/max size/initial capacity

* ASG policies
    * Dynamix Scaling
        * Target Tracking Scaling
            * example: want the average ASG CPU to stay around 40%
        * Simple/Step Scaling
            * example: when a cloudwatch alarm is trigger (CPU > 70%) add 2 new units
    * Scheduled Scaling
        * example: increase the min capacity to 10 at 5 pm on Fri
    * Predictive Scaling
        * continusly forcast load and schedule scaling ahead

#### RDS - Relational Database Service

* Advantage versus deploying DB on EC2
    * Automated provision, OS patching
    * Continuous backups and Point in time restore
    * monitoring dashboards
    * Read replicas for improved read performance
    * Multi AZ setup for Disaster Recovery
    * Maintenance windows for upgrades
    * Scaling capability vertical and horizontal
    * Storage backed by EBS
    * **BUT you cant ssh into your instance**

* RDS - Storage Auto Scaling
    * automatically increase storage
    * have to set **Max storage threshold**
    * useful for upredicatable workloads
    * supports all database engine

* RDS Read replica vs RDS Multi-AZ(standby)
    * up to 15 RRs
    * within AZ, Cross AZ or Cross region
    * Replication is **ASYNC**, so reads are eventually consistent
    * RR can be promoted to their own DB
    * **update SQL connection String**
    * Network cost
        * for RDS RR in the same region different AZ, free
        * for cross region, cost money
    * RDS Multi AZ - standby (comparison with RR)
        * **SYNC** replication, different from RR
        * One DNS name - failover to standby
        * no manual work
        * RR can be setup as Multi AZ for Disaster Recovery
        * From Single-AZ to Multi-AZ
            * Zero Down time
            * just click "modify" for the database

* RDS Backups
    * Automated backups
        * daily full backup
        * transaction logs are backed-up every 5 mins
        * 1-35 days of retention set 0 to disable sutomated backups
    * Manual DB snapshots
        * manually triggered
        * retention of backup for as long as you want
    * trick: in a stopped RDS databse, you will still pay for the storage. If you plan on stopping it for long time, you should snapshot & restore instead.
* Aurora Backups
    * only difference
        * for automated backups
            * 1 to 35 days (**can't** be disabled)

* RDS & Aurora Restore ptions
    * Restoring a RDS / Aurora backup or a snapshot
    * Restoreing MySQL RDS databse from s3
        * create a backup of your on-premises database
        * store it on S3
        * restore the backup file onto a new RDS instance running  MYSQL
    * Restoring MySQL Aurora cluster from S3
        * create a backup of your on-premises using **Percona XtraBackup**
        * store the backup file on S3
        * restore the backup file onto a new Aurora cluster running  MYSQL

* Aurora Database cloing
    * faster then snapshot & restore
    * use copy-on-write protocol
    * without impact on production database

* **Amazon Aurora**
    * can support both MySQL and Postgres
    * Aurora storage automatically grows 10GB to 256TB
    * support for cross region replication
    * Aurora Replicas - Auto Scaling
        * if the average CPU usage increase, then auto add more RR and Reader Endpoint auto extended to cover the newly added RRs
    * Custom Endpoint replcace for Reader Endpoint
    * Aurora Serverless
        * good for infrequent unpredictable
        * no capacity planning needed
    * **Global Aurora Database**
        * **Typical cross-region replication takes less than 1 second**
    * Babefish
        * allows Aurora postgreSQL to understand commands targeted for MS SQL server

* Amazon RDS Proxy
    * Improving database effiency by reducing the stress on database resources (CPU & RAM) and minimize open connections and timeouts
    * Reducing RDS & Aurora failover time by up 66%
    * RDS Proxy is never publicly accessible (must be accessed from VPC)
    * helpful for lambda functions
    * Enforce IAM Authentication for DB, and securely store crendentials in AWS Secrets Manager

* ElastiCache
    * get managed Redis or Memcached
    * in-memory
    * helpful for **read intensive workloads**
    * **involves heavy application code changes**
    * <img src="attachments/ElastiCache.jpeg" width=400/>
    * <img src="attachments/RedisVSMem.jpeg" width=400/>
    * supports **IAM Authentitation for Redis**
    * **Redis AUTH**
    * Redis use case
        * **Redis Sorted sets** for gaming leaderboards

* **important ports VS RDS databases ports**
    * important
        * FTP: 21
        * SSH: 22
        * SFTP: 22 (same as SSH)
        * HTTP: 80
        * HTTPS: 443
    * RDS
        * PostgreSQL: 5432
        * MySQL: 3306
        * Oracle RDS: 1521
        * MSSQL Server: 1433
        * MariaDB: 3306 (same as MySQL)
        * Aurora: 5432 (if PostgreSQL compatible) or 3306 (if MySQL compatible)


### Route 53
* Hosted Zones
    * pay for each zone per month

* CNAME vs Alias
    * CNAME
        * points a hostname to any other hostname
        * **only for non root domain**
    * Alias
        * points a hostname to an AWS Resources
        * **works both root domain and non root domain**
        * **always** a type of AAAA/A
        * can't saet TTL
        * cannot set an Alias record for an EC2 DNS name

* Route 53 policies
    * Simple
        * If multiple values returned, choose a random one
        * **can't** be associated with Health Check
    * Weighted
        * control the % of the requests that go to each specific resources
        * if a record set weight as 0, stop sending traffic to this
        * If all records set weight as 0, all resources returned equally
    * Failover (Active-Passive)
    * Latency based
        * Latency is based on **traffic** between users and AWS Regions
        * **can** be associated with Health check
    * Geolocation
        * **can** be associated with Health Check
        * is based on user **location**
        * should create a "default" record
        * use case:
            * website localization
            * restrict content distribution
            * load balancing
    * Multi-Value
        * **can** be associated with Health Check
        * **not** a subsitiude for an ELB, as it's at client side
        * return up to 8 healthy records
    * Geoproximity (Traffic flow feature)
        * shift more traffic to resources based on defined **bias**
    * IP-based
        * provide a list of CIDRs for your clients
        * use case:
            * optimize performance
            * reduce network cost
        * example:
            * route end users from a particular ISP to a specific endpoint

* If buy your domain on 3rd party registrar, can still use Route 53 as DNS Service provider
    1. create a Hosted Zone in Route 53
    2. Update NS records on 3rd party website to use Route 53 Name Servers

* Route 53 Health Check
    * HTTP Health Checks are only for public resources
    * Calculated Health Checks
        * performaing maintenance to your website without causing all health checks to fail
    * for **private hosted zones**
        * Route 53 health checkers are outside the VPC, they **cannot** access the **private** endpoints
        * Use **CouldWatch Metric** and associate a **CloudWatch Alarm**

* Route 53 & Hybrid DNS
    * <img src="attachments/Inbound endpoint.jpeg" width=400/>
    * <img src="attachments/Outbound endpoint.jpeg" width=400/>

### S3

* Buckets
    * must have **globally unique name**
    * created in a region
* Objects
    * the key is the FULL path
    * the key is the compose prefix and object name
    * max objedct size is 5T, otherwise need multi-part upload
* Version
    * enable at bucket level
    * after versioning, the existed files are versioned as **null**
    * suspend versioning does not delete previous versions

* S3 replication
    * CRR (cross region)
        * compliance, lower latency access, replication across accounts
    * SRR (same region)
        * log aggregation, live replication between production and test accounts
    * after enabled
        * only **new objects** will be replicated
        * replicate **existed** objects, using **S3 batch replication**
    * for **delete** operations
        * delete markers can be replicated
        * delete with version ID is replicated
    * **no chaining of replication**

* **S3 storage classes**
    * Standard General purpose
        * 99.99 availability
        * for frequently accessed data
        * low lantency and high throughput
        * sustain 2 concurrent facility failures
        * use case
            * big data analytics
            * mobile & gaming application
            * content distribution
    * Infrequent Access
        * less frequently access, but requires rapid access when needed
        * S3 standard-IA
            * 99.9 ava
            * for disaster recovery, backups
        * S3 One Zone-IA
            * 99.5 ava
            * for secondary backup copies of on-premise data, or data you can recreate
    * Glacier Storage
        * low cost for archiving/backup
        * Glacier instant retrieval
            * millisecond retrieval, great for data accessed once a quater
            * minimum storage duration 90 days
        * Glacier flexible retrieval
            * expedite 1-5 mins, standard 3-5 hous, bulk 5-12 hours
            * minimum storage duration 90 days
        * Glacier deep archive
            * standard 12 hours, bulk 48 hours
            * minimum storage duration 180 days
    * S3 intelligent Tiering
        * small monthly monitoring and auto-tiering fee
        * move objects automatically between Access Tiers based on usage
        * no retrieval charges
    * **S3 Express One Zone**
        * **single availablity Zone storage class**
        * data stored in **Directory Bucket (bucket in a single AZ)**
        * 10x better than s3 standard 50% lower cost
        * use case
            * latency-sensitive apps, data intensive apps. AI&ML training, HPC (high performace computing)
    * comparison
        * <img src="attachments/s3 storage comparison.jpeg" width=500/>

    * price example
        * <img src="attachments/price example.jpeg" width=500/>

* S3 replication rules (with S3 Analytics)
    * Transaction Actions - move objects to another storage class
        * move objects to Standard IA class 60 days after creation
        * move to Glacier for achiving after 6 months
    * Expiration Actions - configure objects to expire (delete) after some time
        * Access log file can be set to delete after 365 days
        * **Can be used to delete old verions of files (if version is enabled)**
        * Can be used to delete incomplete Multi-Part uploads
    * Rules can be created for a **certain prefix e.g. s3://mybucket/mp3/\***
    * Rules can be created for **certain objects Tags e.g. Department: Finance**
    * **S3 analytics does not work for One-Zone IA or Glacier, only work for standard and Standard IA**

* **S3 Requester Pays**
    * In general, bucket owner pays for all S3 storage and **data transfer**
    * **With Requester Pays Bucket**, the requester pays the cost of the request
        * The requester must be authenticated in AWS (cannot be anonymous)

* S3 Event Notifications
    * IAM Permissions
        * for SNS, SQS, Lambda Function, use **resource access policies**
        * EventBridge is the fourth, advanced filtering, multiple destination.

* S3 Performance improvement
    * Multi-Part upload
        * recommand for files > 100MB, must use for files > 5GB
        * it parallelize uploads to speed up
    * S3 Transfer Acceleration
        * Increase transfer speed by transfering file to an **AWS edge location which will forward the data to S3 bucket**
        * **compatible with multi-part upload**
    * S3 Byte-Range Fetches
        * can be used to speed up downloads
        * can be used to retrieve only partial data
    
* S3 Batch Operations
    * **Encrypt un-encrypted objects**
    * You can use **S3 inventory** to get object list and use **Athena** to query and filter your objects, then pas it to **Batch Operation** to do the work.

* S3 Storage Lens
    * <img src="attachments/storage lens.jpeg" width=500/>
    * **default dashboard has data across multiple accounts and multiple regions**
    * **Free Metrics**
        * available for all users
        * data available for queries for **14 days**
    * **Advanced Metrics**
        * Activity, Advanced Cost Optimization, Advanced Data Protection, Status Code
        * CloudWatch Publishing
        * Prefis Aggregation
        * data available for queries for **15 months**

### S3 Security
* S3 Object Encryptyion
    * Server-Side Encryption (SSE)
        * Server-Side Encryption with Amazon S3-Managed Keys (**SSE-S3**) - Enabled by Default
            * <img src="attachments/SSE-S3.jpeg" width=500/>
        * Server-Side Encryption with KMS Keys stored in AWS KMS (**SSE-KMS**)
            * <img src="attachments/SSE-KMS.jpeg" width=500/>
            * It has limitations for speed - KMS quota per sec
        * Server-Side Encryption with Customer-Provided Keys (**SSE-C**)
            * <img src="attachments/SSE-C.jpeg" width=500/>
    * Client-Side Encryption
        * <img src="attachment" width=500/>
    * Encryption in transit (SSL/TLS)
        * S3 exposes **HTTP and HTTPS**
        * **HTTPS is mandatory for SSE-C**
        * Use **bucket policy** to force encryption in transit and evaluated before **default encryption**

* **CORS Cross-Origin Resource Sharing**
    * web browser security that allows you to enable files being retrieved from one S3 bucket in case the request is originating from another origin
    * Origin = scheme (protocol) + host (domain) + port
    * If a client makes a cross-origin request on S3 bucket, need to enable the correct CORS headers
        * you can allow for a specific origin or for *(all origins)

* **S3 - MFA Delete**
    * **only the bucket owner (root account) can enable/disable MFA Delete**
    * To use it, **Versioning must be enabled** on the bucket
    * MFA **will be required** to
        * Permanently delete an object version
        * suspend versioning on the bucket
    * MFA **won't be required** to
        * enable versioning
        * list deleted versions

* S3 Access Logs
    * Warning
        * **Dont set your logging bucket to be the monitored bucket**, otherwise pay a lot

* S3 - Presigned URLs
    * allow temporary access of GET/PUT on S3 bucket for an external user without breaking your security
        * S3 Console - 1 min to 12 hours expiration
        * AWS CLI - default 3600 sec, max 168 hours expiration

* **S3 Glacier Vault Locck & S3 Object Lock**
    * WORM (Write once read many)
    * glacier lock
        * create a Vault Lock Policy
    * Object lock
        * **versioning must be enabled**
        * retention mode - **compliance**
            * Object versions **can't be overwritten or deleted by any user, including the root user**
            * Objects retention modes **can't be changed, and retention periods can't be shortened**
        * rentention mode - **governance**
            * Some users have special permissions to change the retention or delete the object. change lock settings
        * **Legal Hold**
            * protect the object **indefinitely**
            * can be modified by IAM permission

* S3 - Access Points
    * <img src="attachments/access point.jpeg" width=500/>
* S3 Object Lambda use case
    * Redacting personally identifiable information for analytics or non production environments.
    * Converting across data formats, such as converting XML to JSON.
    * Resizing and watermarking images on the fly using caller-specific details, such as the user who requested the object.

* CloudFront
    * **CDN**
    * **Improves read performance, content is cached at the edge**
    * DDoS protection, integration with Shield, AWS Web Application Firewall
    * **Cloud Front vs S3 Cross Region Replication**
        * CloudFront:
            * Global Edge network
            * Files are cached for a TTL (maybe a day)
            * **Great for static content that must be available everywhere**
        * S3 CRR
            * Must be setup for each region you want replication to happen
            * Files are updated in near real-time
            * Read only
            * **Great for dynamic content that needs to be available at low-latency in few regions**
    * **Pricing class**
        1. Price Class All: all regions – best performance 
        2. Price Class 200: most regions, but excludes the most expensive regions
        3. Price Class 100: only the least expensive regions
        * <img src="attachments/cloudfront pricing.jpeg" width=600/>
    * **Global Accelerator vs CloudFront**
        * same
            * They both use the AWS global network and its edge locations around the world
            * Both services integrate with AWS Shield for DDoS protection
        * different
            * CloudFront
                * Improves performance for both cacheable content
                * Dynamic content (such as API acceleration and dynamic site delivery)
                * Content is served at the edge
            * Global Accelerator
                * Improves performance for a wide range of applications over TCP or UDP
                * Proxying packets at the edge to applications running in one or more AWS Regions
                * Good fit for non-HTTP use cases, such as gaming (UDP), IoT (MQTT), or Voice over IP
                * Good for HTTP use cases that require static IP addresses
                * Good for HTTP use cases that required deterministic, fast regional failover

* AWS Snowball
    * Highly-secure, portable **devices** to collect and process data at the edge, and migrate data into and out of AWS
        * Storage optimized device has 210 TB SSD
        * Compute optimized device has 28 TB SSD
        * **Both have the same cpus and memory**
        * **If it takes more than a week to transfer over the network, use Snowball devices**
        * **Snowball cannot import to Glacier directly**
            * import to S3 first, then use S3 lifecycle policy

* **Amazon FSx**
    * FSx for Windows
        * Can be mounted on **Linux EC2 Instance**
        * support **Microsoft's Distributed File System (DFS) Namespaces**
        * Can be **accessed** from your on-premises infrastructure (VPN or Direct Connect)
        * **Multi-AZ (high availability)**
        * Data is backed-up daily to S3
    * FSx for Lustre
        * **High Performance Computing (HPC)**
        * **Seamless integration with S3**
            * can read S3
            * can write output of the computations back to S3
        * Can be **used** from on-premises servers (VPN or Direct Connect)
        * File System Deployment Options
            * **Scratch File System**
                * Temporary storage
                * Data is not replicated
                * High burst
                * used for optimized cost
            * **Persistent File System**
                * Long-term storage
                * Data is replicated within **same AZ**
                * used for sensitive data
    * FSx for NetApp ONTAP
        * **compatible with NFS, SMB, iSCSI protocol**
        * Point-in-time instantaneous cloning
    * FSx for OpenZFS
        * **compatible with NFS**
        * Point-in-time instantaneous cloning

* AWS Storage Gateway
    * used for long-term migration from on-premis to cloud
    * <img src="attachments/storage gateway.jpeg" width=600/>

* AWS Transfer Family
    * used when dont want to use S3 or EFS APIs
    * <img src="attachments/transfer family.jpeg" width=600/>

* AWS DataSync
    * **File permissions and metadata are preserved**
    * can synchronize between anything, but it's not continuous. It's a scheduled task that runs hourly, daily, weekly. **need to run the DataSync agents if connecting to an NFS or SMB server**
    * <img src="attachments/datasync.jpeg" width=600/>
    * snowcone is a device in the AWS Anow Family