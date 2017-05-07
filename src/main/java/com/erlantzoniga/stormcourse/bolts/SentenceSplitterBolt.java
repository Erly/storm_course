package com.erlantzoniga.stormcourse.bolts;

import com.erlantzoniga.stormcourse.model.Tweet;
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
    Tweet tweet = (Tweet) input.getValueByField(Constants.TWEET);
    String[] words = tweet.getText().split("\\s+");
    for (int i = 0; i < words.length; i++) {
      String word = words[i];
      collector.emit(new Values(word));
    }
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields(Constants.WORD));
  }
}
