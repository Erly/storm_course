package com.erlantzoniga.stormcourse.bolts;

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
    Tuple mockTuple = Mockito.mock(Tuple.class);
    BasicOutputCollector mockBasicOutputCollector = Mockito.mock(BasicOutputCollector.class);
    Mockito.doReturn("Test tweet").when(mockTuple).getStringByField(Constants.TWEET);

    // act
    sentenceSplitterBolt.execute(mockTuple, mockBasicOutputCollector);

    // assert
    Mockito.verify(mockBasicOutputCollector, Mockito.times(1)).emit(new Values("Test"));
    Mockito.verify(mockBasicOutputCollector, Mockito.times(1)).emit(new Values("tweet"));
  }
}
