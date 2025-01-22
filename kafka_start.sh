#!/bin/bash

osascript -e 'tell application "Terminal"
    activate
    do script "cd /Users/vladimirtodoshchak/Downloads/kafka_2.13-3.7.0/bin; ./zookeeper-server-start.sh ../config/zookeeper.properties"
end tell'

sleep 2

osascript -e 'tell application "Terminal"
    activate
    do script "cd /Users/vladimirtodoshchak/Downloads/kafka_2.13-3.7.0/bin; ./kafka-server-start.sh ../config/server.properties"
end tell'

sleep 2

osascript -e 'tell application "Terminal"
    activate
    do script "cd /Users/vladimirtodoshchak/Downloads/kafka_2.13-3.7.0/bin; ./kafka-topics.sh --bootstrap-server kafka:9092 --topic chat_topic --create --partitions 1 --replication-factor 1; exit"
end tell'

osascript -e 'tell application "Terminal"
    activate
    do script "brew services start redis; exit"
end tell'
