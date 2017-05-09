# Chapter 3

### Implement TODOs of SentenceSplitterBolt
1. Add tweet id and stream id when emitting a word.
```java
collector.emit(Constants.STREAM_WORD, new Values(tweet.getId(), word));
```
2. Emit the latitude and longitude along with the tweet id
```java
collector.emit(Constants.STREAM_COORDINATES, new Values(tweet.getId(), tweet.getLatitude(), tweet.getLongitude()));
```
3. Update the old declare line to indicate a stream and add the tweet id as an output field
```java
declarer.declareStream(Constants.STREAM_WORD, new Fields(Constants.ID, Constants.WORD));
```
4. Declare the second output indicating it sends the id, latitude and longitude
```java
declarer.declareStream(Constants.STREAM_COORDINATES, new Fields(Constants.ID, Constants.LATITUDE, Constants.LONGITUDE));
```