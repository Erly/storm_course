package com.erlantzoniga.stormcourse.spouts;

import com.erlantzoniga.stormcourse.utils.Constants;

import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class RandomSentenceSpout extends BaseRichSpout {
  private SpoutOutputCollector collector;
  private Random rand;


  @Override
  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    this.collector = collector;
    rand = new Random();
  }

  @Override
  public void nextTuple() {
    Utils.sleep(100);
    String[] sentences = new String[]{"the cow jumped over the moon",
        "an apple a day keeps the doctor away", "four score and seven years ago",
        "snow white and the seven dwarfs", "i am at two with nature"};
    String sentence = sentences[rand.nextInt(sentences.length)];
    collector.emit(new Values(sentence));
  }

  @Override
  public void ack(Object id) {
  }

  @Override
  public void fail(Object id) {
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields(Constants.TWEET_TEXT));
  }
}
