package com.erlantzoniga.stormcourse.bolts;

import com.erlantzoniga.stormcourse.model.Tweet;
import com.erlantzoniga.stormcourse.utils.Constants;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SentenceSplitterBoltTest {

  SentenceSplitterBolt sentenceSplitterBolt;

  @Before
  public void setup() {
    sentenceSplitterBolt = new SentenceSplitterBolt();
  }

  @Test
  public void execute_ok() {
    // prepare
    Tweet tweet = new Tweet("Test tweet", 1.1234f, 9.9876f);
    Tuple mockTuple = Mockito.mock(Tuple.class);
    BasicOutputCollector mockBasicOutputCollector = Mockito.mock(BasicOutputCollector.class);
    Mockito.doReturn(tweet).when(mockTuple).getValueByField(Constants.TWEET);

    // act
    sentenceSplitterBolt.execute(mockTuple, mockBasicOutputCollector);

    // assert
    Mockito.verify(mockBasicOutputCollector, Mockito.times(1)).emit(new Values("Test"));
    Mockito.verify(mockBasicOutputCollector, Mockito.times(1)).emit(new Values("tweet"));
  }
}
