package com.erlantzoniga.stormcourse.spouts;

import com.erlantzoniga.stormcourse.model.Tweet;
import com.erlantzoniga.stormcourse.utils.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class RandomTweetSpout extends BaseRichSpout {
  private SpoutOutputCollector collector;
  private Random rand;
  HashMap<UUID, Tweet> emittedTuples;

  private final String[] sentences =
      new String[]{"the cow jumped over the moon", "an apple a day keeps the doctor away",
          "four score and seven years ago", "snow white and the seven dwarfs",
          "i am at two with nature"};
  private final float[] coordinates =
      new float[]{20.5483f, 82.1638f, -38.3762f, 17.4701f, 52.0512f, -68.9535f};

  @Override
  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    this.collector = collector;
    rand = new Random();
    emittedTuples = new HashMap<>();
  }

  @Override
  public void nextTuple() {
    Utils.sleep(100);
    final String sentence = sentences[rand.nextInt(sentences.length)];
    final float latitude = coordinates[rand.nextInt(coordinates.length)];
    final float longitude = coordinates[rand.nextInt(coordinates.length)];

    UUID msgId = UUID.randomUUID();
    emittedTuples.put(msgId, new Tweet(sentence, latitude, longitude));

    collector.emit(new Values(sentence), msgId);
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields(Constants.TWEET));
  }

  @Override
  public void ack(Object msgId) {
    System.out.println(msgId + " acked");
    emittedTuples.remove(msgId);
  }

  @Override
  public void fail(Object msgId) {
    System.out.println(msgId + " failed");
    collector.emit(new Values(emittedTuples.get(msgId)), msgId);
  }

  @Override
  public void close() {
    super.close();
  }

  @Override
  public void activate() {
    super.activate();
  }

  @Override
  public void deactivate() {
    super.deactivate();
  }
}
