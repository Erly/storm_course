package com.erlantzoniga.storm_course.bolts;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

public class WordCountBolt extends BaseBasicBolt {
  private Map<String, Integer> counts = new HashMap<>();

  @Override
  public void execute(Tuple input, BasicOutputCollector collector) {
    // TODO: Get the text from the tuple
    String word = null;

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
