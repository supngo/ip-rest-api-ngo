# IP Address Management REST API
 
Create a simple IP Address Management REST API using Spring Framework on top of any data store. It will include the ability to add IP Addresses by CIDR block and then either acquire or release IP addresses individually. Each IP address will have a status associated with it that is either “available” or “acquired”. 
 
The REST API must support four endpoint:
  * **Create IP addresses** - take in a CIDR block (e.g. 10.0.0.1/24) and add all IP addresses within that block to the data store with status “available”
  * **List IP addresses** - return all IP addresses in the system with their current status
  * **Acquire an IP** - set the status of a certain IP to “acquired”
  * **Release an IP** - set the status of a certain IP to “available”


  # Solution:
  ## 1. Requirements:
  - Docker https://www.docker.com/
  - Postman (https://www.postman.com/) or any other Rest client tools
  - Java8 (Don't need to install if using docker)
  - Maven (Don't need to install if using docker)

  ## 2. How to build and run
  - Checkout the repo
  - Run locally:
  ```
  mvn clean package
  java -jar target/trillion-0.0.1-SNAPSHOT.jar
  ```
  - Run in Docker:
  ```
  Install docker
  docker build -t trillion:latest .
  docker run -p 8080:8080 trillion:latest
  ```

  ## 3. Test Rest Endpoints
  ### A. Create IP addresses
  **POST** ***/ip_management/create***

  ***Payload Example***
  ```
  {
    "ip": "10.10.0.0/24"
  }
  ```

  ***Validations:***
  - Valid/invalid CIDR Block
  - Overlapped CIDR Block

  ### B. List IP addresses:
  **GET** ***/ip_management/get***

  ### C. Acquire an IP:
  **PUT** ***/ip_management/acquire***

  ***Payload Example***
  ```
  {
    "ip": "10.10.0.1"
  }
  ```

  ***Validations:***
  - Valid/invalid IP
  - IP not found

  ### D. Release an IP:
  **PUT** ***/ip_management/release***
  
  ***Payload Example***
  ```
  {
    "ip": "10.10.0.1"
  }
  ```
  ***Validations:***
  - Valid/invalid IP
  - IP not found

Question? thongo5430@gmail.com