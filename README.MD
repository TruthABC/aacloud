# AACloudDisk Back-end Server

## 0. Online Repository
* Back-end Server: https://github.com/TruthABC/aacloud
* Android Client: https://github.com/TruthABC/AACloudDisk/

## 1. Deploy
* Deployed: "http://123.207.6.234:8080/aacloud"
* Check-alive: HTTP-GET http://123.207.6.234:8080/aacloud/greeting (0 param, or 1 param: "name")
* Check-alive Response: {"id":#{Auto Increase Value},"content":"Hello, #{name}!"}
* How-To-Deploy: Build & Copy "aacloud.war" to "Tomcat/webapps", then start Tomcat :D

## 2. Deployment Environment
* Platform (Recommended): Windows Server 2012 R2
* Container (Recommended): Tomcat 9.0
* Database (Recommended): Mysql 5.7
* File System: Windows File System (Eg. non-case-sensitive file names)

## 3. Develop
* Language: Java, html+css+js
* IDE: IntelliJ IDEA
* Maven: Resolving dependencies (including following)
* Spring Boot: Mapping services(RESTful) and files to Internet URL
* MyBatis: Database Connector
* Json: Jsonlib (a set of "net.sf.json" jars)
