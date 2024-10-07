#!/bin/bash

osascript -e 'tell application "Terminal"
    activate
    do script "cd /Users/vladimirtodoshchak/Downloads/kafka_2.13-3.7.0/bin; ./zookeeper-server-start.sh ../config/zookeeper.properties"
end tell'

osascript -e 'tell application "Terminal"
    activate
    do script "cd /Users/vladimirtodoshchak/Downloads/kafka_2.13-3.7.0/bin; ./kafka-server-start.sh ../config/server.properties"
end tell'

osascript -e 'tell application "Terminal"
    activate
    do script "cd /Users/vladimirtodoshchak/Downloads/kafka_2.13-3.7.0/bin; ./kafka-topics.sh --bootstrap-server kafka:9092 --topic chat_topic --create --partitions 1 --replication-factor 1"
end tell'
