package com.erlantzoniga.storm_course.bolts;

import static com.erlantzoniga.storm_course.utils.Constants.TWEET_TEXT;
import static com.erlantzoniga.storm_course.utils.Constants.WORD;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Arrays;

public class SplitSentenceBolt extends BaseBasicBolt {
  @Override
  public void execute(Tuple input, BasicOutputCollector collector) {
    String tweetText = input.getStringByField(TWEET_TEXT);

    Arrays.stream(tweetText.split("\\s+")).forEach(word -> collector.emit(new Values(word)));
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields(WORD));
  }
}
