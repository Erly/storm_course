# Chapter 1

### Implement the TODOs of the Word count bolt
1. Read the word from the tuple
```java
input.getStringByField(Constants.WORD)
```
2. Emit the word and the count from the map to the collector
```java
collector.emit(new Values(word, count))
```
3. Declare WORD and COUNT as the output fields
```java
declarer.declare(new Fields(Constants.WORD, Constants.COUNT));
```

### Implement the TODOs of the TopologyHelper
1. Add the wordcount bolt to the configure method with a fields grouping so all the words that are identical go to the same bolt instance
```java
topologyBuilder.setBolt(Constants.BOLT_WORD_COUNT, new WordCountBolt()).fieldsGrouping(Constants.SPOUT_RANDOM_SENTENCE, new Fields(Constants.WORD))
```
2. Run the topology in local
```java
cluster.submitTopology(Constants.TOPOLOGY_NAME, conf,  topologyBuilder.createTopology());
```
3. Get the number of workers from the properties file and set it on the storm config, use a default value of 1 when the property is not set
```java
conf.setNumWorkers(config.getInt(Config.TOPOLOGY_WORKERS, 1));
```
4. Run the topology on the remote cluster
```java
StormSubmitter.submitTopology(Constants.TOPOLOGY_NAME, conf, topologyBuilder.createTopology());
```