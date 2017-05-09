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
    float latitude;
    float longitude;
    String[] words = tweet.getText().split("\\s+");
    for (int i = 0; i < words.length; i++) {
      String word = words[i];
      // TODO: The tweet id along the words and add a stream id (Constants.STREAM_WORD)
      collector.emit(new Values(word));
    }

    // TODO: Emit the latitude and longitude along the tweet id with the stream id Constants.STREAM_COORDINATES. Do it in the following order: id, latitude, longitude
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    // TODO: Change declare by declareStream and add Constants.ID to the fields
    declarer.declare(new Fields(Constants.WORD));
    // TODO: Declare the output fields for the Constants.STREAM_COORDINATES
  }
}
