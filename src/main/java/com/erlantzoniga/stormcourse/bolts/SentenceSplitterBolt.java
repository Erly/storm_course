package com.erlantzoniga.stormcourse.bolts;

import com.erlantzoniga.stormcourse.utils.Constants;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class SentenceSplitterBolt extends BaseBasicBolt {
  @Override
  public void execute(Tuple input, BasicOutputCollector collector) {
    // TODO: Get the tweet and split it into words (hint: the regex for space in java is //s+)
    String tweet;
    String[] words;
    for (int i = 0; i < words.length; i++) {
      String word = words[i];
      // TODO: emit the word
    }
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields(Constants.WORD));
  }
}
