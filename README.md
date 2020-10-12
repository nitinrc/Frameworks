# Frameworks
1. STAR: Description TODO

2. PARSER: Description TODO

3.Springboot Applications:
  REST URLs:
  http://localhost:8080/publisher/get/data1/nitin
  http://localhost:8080/publisher/get/data2?id=10&name=nitin
  
  Kafka commands:
  cd E:\Nitin\prep\kafka\bin\windows
  zookeeper-server-start.bat ../../config/zookeeper.properties
  kafka-server-start.bat ../../config/server.properties
  kafka-topics.bat --create --topic kafka-test --partitions 4 --replication-factor 1 --bootstrap-server localhost:2181
  kafka-topics.bat --create --topic kafka-test --partitions 4 --replication-factor 1 --zookeeper localhost:2181
