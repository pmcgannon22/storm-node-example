storm-node-example
==================
An example project that uses Storm, Node.js, and Redis in order to display tweet locations on a Google Map in real-time. To gather Twitter data, the Twitter Streaming API is used. 

##Dependencies
- Node.js
- Maven build tool for Java
- Redis

Maven should handle all other Java related dependencies using the m2-pom.xml file included in the project. 

##Running
Start a Redis server. The location of the server is specified in the RedisBolt.java file. Currently, it is configured to look for one running on the local machine. 
```shell
redis-server
```
Run the Node.js application. Navigate to nodejs folder and run the command below. It is currently configured to look for a redis server on the localhost as well. The node server will run on localhost:3000.
```shell
node app.js
```
Lastly, start the Storm topology with the following command. On the first run, Maven will attempt to download all of the required Java libraries. 
```shell
mvn -f m2-pom.xml compile exec:java -Dexec.classpathScope=compile -Dexec.mainClass=storm.starter.TwitterStream
```

Navigate to localhost:3000/ in a web browser and the application should be running. 
