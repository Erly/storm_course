package com.erlantzoniga.stormcourse.bolts;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

public class WordCountBolt extends BaseBasicBolt {
  Map<String, Integer> counts = new HashMap<String, Integer>();

  @Override
  public void execute(Tuple input, BasicOutputCollector collector) {
    String word;
    // TODO: Get the text from the tuple


    Integer count = counts.get(word);
    if (count == null) {
      count = 0;
    }
    counts.put(word, ++count);

    // TODO: Emit the word and the count
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    // TODO: Declare the map as output fields
  }
}
