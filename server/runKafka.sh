kafka_2.10-0.9.0.1/bin/kafka-server-start.sh kafka_2.10-0.9.0.1/config/server.properties > logs/kafka.out &

kafka_2.10-0.9.0.1/bin/zookeeper-server-start.sh kafka_2.10-0.9.0.1/config/zookeeper.properties > logs/zookeeper.out &

gradle run 
