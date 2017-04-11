# Chapter 2

### Implement the TODOs of the Random tweet spout
1.  Remove the tuple from the emitted tuples map when it gets acked
```java
emittedTuples.remove(msgId);
```
2.  Re-emit the the tuple when it fails
```java
collector.emit(new Values(emittedTuples.get(msgId)), msgId);
```

### Implement the TODOs of the Sentence splitter bolt
1.  Get the tweet text from the tuple
```java
String tweet = input.getStringByField(Constants.TWEET);
```
2.  Split the tweet to get an array with the words
```java
String[] words = tweet.split("\\s+");
```
3.  Emit the words inside the loop
```java
collector.emit(new Values(word));
```

### Implement the TODOs of the TopologyHelper
1.  Change the RandomWordSpout for the RandomSentenceSpout
```java
topologyBuilder.setSpout(Constants.SPOUT_RANDOM_TWEET, new RandomTweetSpout());
```
2.  Add the sentence splitter bolt to the configure method with a local or shuffle grouping
```java
topologyBuilder.setBolt(Constants.BOLT_SENTENCE_SPLITTER, new SentenceSplitterBolt())
        .localOrShuffleGrouping(Constants.SPOUT_RANDOM_TWEET);
```
3.  Modify the word count bolt so it listens to the sentence splitter bolt stream
```java
topologyBuilder.setBolt(Constants.BOLT_WORD_COUNT, new WordCountBolt())
        .fieldsGrouping(Constants.BOLT_SENTENCE_SPLITTER, new Fields(Constants.WORD));
```