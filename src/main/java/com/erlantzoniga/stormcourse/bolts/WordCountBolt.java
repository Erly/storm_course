package com.erlantzoniga.stormcourse.bolts;

import com.erlantzoniga.stormcourse.utils.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class WordCountBolt extends BaseBasicBolt {
  private Map<String, Integer> counts = new HashMap<>();

  @Override
  public void execute(Tuple input, BasicOutputCollector collector) {
    UUID id = (UUID) input.getValueByField(Constants.ID);
    String word = input.getStringByField(Constants.WORD);

    Integer count = counts.get(word);
    if (count == null) {
      count = 0;
    }
    counts.put(word, ++count);

    collector.emit(new Values(id, word, count));
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields(Constants.ID, Constants.WORD, Constants.COUNT));
  }
}
