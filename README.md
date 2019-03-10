# FileDownnloder

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

File Downloaded is a simple spring boot app, which supports  download of large files in http, ftp and sftp requests.

# Features!

  - Resumable Http Requests.
  - FTP and SFTP channels to write directly to the disk instead of keeping in memory.
  - Automatic Retryable downloads on fail (Currently after fixed interval).
  - Downloads the file asynchronously.

### Future Development
  - Junits and coverage
  - Adding Schedulers to download files by FCFS, server speed , file size.
  - Downloading files by multiple parts, instead as single file.
  - Fine Tune the application.

### Tech

FileDownnloder uses a number of open source projects to work properly:

* [Spring Boot](https://spring.io/projects/spring-boot) -  runs stand-alone, production-grade Spring based Applications
* [FTP Client](https://commons.apache.org/net/apidocs/org/apache/commons/net/ftp/FTPClient.html) - Client to download the ftp files
* [JSch](http://www.jcraft.com/jsch/) - JSch allows you to connect to an sshd server and use port forwarding, X11 forwarding, file transfer, etc.

### Installation

FileDownnloder requires [Java](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 8+ to run.

Install the dependencies and devDependencies and start the server.

```sh
$ cd filedownloader
$ mvn clean install
$ cd target/
$ java -jar *.jar
```

### API documentation
The Server is configured to start at 8080 port and contextPath fileDownloader
[Documentation](https://documenter.getpostman.com/view/97091/S11RKFZm#1f61ad0d-d25d-fbd1-b512-100a142543f3)

```
http://{server:8080}/fileDownloader
```

