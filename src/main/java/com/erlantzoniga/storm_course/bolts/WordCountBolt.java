package com.erlantzoniga.storm_course.bolts;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

public class WordCountBolt extends BaseBasicBolt {
  @Override
  public void execute(Tuple input, BasicOutputCollector collector) {
    // TODO: Get the text from the tuple

    // TODO: Emit a map with the word as key and count as value
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    // TODO: Declare the map as output fields
  }
}
