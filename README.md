
## start kafka
    docker-compose up

## create topic
    ./bin/kafka-topics.sh --bootstrap-server localhost:29092 --create --topic devs4j-topic --partitions 5 --replication-factor 1

## update topics

    change partitions
 
    ./bin/kafka-topics.sh --bootstrap-server localhost:29092 --update --topic devs4j-topic --partitions 6

## delete topics

    ./bin/kafka-topics.sh --bootstrap-server localhost:29092 --delete --topic devs4j-topic


## list topics
    ./bin/kafka-topics.sh --list --bootstrap-server localhost:29092

## describe topic
    ./bin/kafka-topics.sh --describe --topic devs4j-topic --bootstrap-server localhost:29092


# publisher and subscriber


## create consumer
    ./bin/kafka-console-consumer.sh --topic devs4j-topic --bootstrap-server localhost:29092

    ./bin/kafka-console-consumer.sh --topic devs4j-topic --from-beginning --bootstrap-server localhost:29092

    ./bin/kafka-console-consumer.sh --topic devs4j-topic --from-beginning --property print.key=true --property key.separator=" - " --bootstrap-server localhost:29092


## create producer
    ./bin/kafka-console-producer.sh --topic devs4j-topic --bootstrap-server localhost:29092
