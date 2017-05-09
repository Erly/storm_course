package com.erlantzoniga.stormcourse.bolts;

import com.erlantzoniga.stormcourse.model.Tweet;
import com.erlantzoniga.stormcourse.testutils.CustomArgumentMatchers;
import com.erlantzoniga.stormcourse.utils.Constants;

import java.util.UUID;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class SentenceSplitterBoltTest {

  SentenceSplitterBolt sentenceSplitterBolt;

  @Mock
  OutputFieldsDeclarer mockDeclarer;

  @Before
  public void setup() {
    sentenceSplitterBolt = new SentenceSplitterBolt();
  }

  @Test
  public void execute_ok() {
    // prepare
    UUID id = UUID.fromString("123e4567-e89b-12d3-a456-426655440000");
    Tweet tweet = new Tweet(id, "Test tweet", 1.1234f, 9.9876f);
    Tuple mockTuple = Mockito.mock(Tuple.class);
    BasicOutputCollector mockBasicOutputCollector = Mockito.mock(BasicOutputCollector.class);
    Mockito.doReturn(tweet).when(mockTuple).getValueByField(Constants.TWEET);

    // act
    sentenceSplitterBolt.execute(mockTuple, mockBasicOutputCollector);

    // assert
    Mockito.verify(mockBasicOutputCollector, Mockito.times(1)).emit(new Values(id, "Test"));
    Mockito.verify(mockBasicOutputCollector, Mockito.times(1)).emit(new Values(id, "tweet"));
    Mockito.verify(mockBasicOutputCollector, Mockito.times(1))
        .emit(new Values(id, 1.1234f, 9.9876f));
  }

  @Test
  public void output_fields_declare_ok() {
    // act
    sentenceSplitterBolt.declareOutputFields(mockDeclarer);

    // assert
    mockDeclarer.declareStream(Constants.STREAM_WORD, CustomArgumentMatchers.fieldsEq(new Fields(Constants.ID, Constants.WORD)));
    mockDeclarer.declareStream(Constants.STREAM_COORDINATES, CustomArgumentMatchers.fieldsEq(new Fields(Constants.ID, Constants.LATITUDE, Constants.LONGITUDE)));
  }
}
