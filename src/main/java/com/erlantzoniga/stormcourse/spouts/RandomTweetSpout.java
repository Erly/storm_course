package com.erlantzoniga.stormcourse.spouts;

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
  HashMap<UUID, String> emittedTuples;

  @Override
  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    this.collector = collector;
    rand = new Random();
    emittedTuples = new HashMap<>();
  }

  @Override
  public void nextTuple() {
    Utils.sleep(100);
    String[] sentences =
        new String[]{"the cow jumped over the moon", "an apple a day keeps the doctor away",
            "four score and seven years ago", "snow white and the seven dwarfs",
            "i am at two with nature"};
    final String sentence = sentences[rand.nextInt(sentences.length)];

    UUID msgId = UUID.randomUUID();
    emittedTuples.put(msgId, sentence);

    collector.emit(new Values(sentence), msgId);
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields(Constants.TWEET));
  }

  @Override
  public void ack(Object msgId) {
    System.out.println(msgId + " acked");
    // TODO: remove the tuple from the emitted tuples map
  }

  @Override
  public void fail(Object msgId) {
    System.out.println(msgId + " failed");
    // TODO: re-emit the failed tuple by getting the text from the emitted tuples map
  }
}
