# Frameworks
1. STAR: Description TODO

2. PARSER: Description TODO

3. Springboot Applications:
   
    REST URLs:

        Publisher:
            http://localhost:9090/publisher/get/data1/nitin
            http://localhost:9090/publisher/get/data2?id=1000&name=nitin
            http://localhost:9098/publisher/put/save?id=1000&name=nitin
            http://localhost:9098/publisher/put/query?id=1000&name=nitin

        Consumer:
            http://localhost:8080/consumer/get/data1/nitin
            http://localhost:8080/consumer/get/data2?id=1000&name=nitin
            http://localhost:8080/consumer/put/save?id=1000&name=nitin
            http://localhost:8080/consumer/put/query?id=1000&name=nitin
  
  
    Kafka commands:
  
        cd kafka\bin\windows
        zookeeper-server-start.bat ../../config/zookeeper.properties
        kafka-server-start.bat ../../config/server.properties

        kafka-topics.bat --create --topic single --partitions 5 --replication-factor 1 --zookeeper localhost:2181
        kafka-topics.bat --create --topic multiple --partitions 5 --replication-factor 1 --zookeeper localhost:2181

        kafka-topics.bat --zookeeper localhost:2181 --alter --topic single --partitions 5
        kafka-topics.bat --zookeeper localhost:2181 --alter --topic multiple --partitions 5

        kafka-topics.bat --delete --zookeeper localhost:2181 --topic single
        kafka-topics.bat --delete --zookeeper localhost:2181--topic multiple
