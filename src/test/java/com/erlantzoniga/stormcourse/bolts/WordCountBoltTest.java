package com.erlantzoniga.stormcourse.bolts;

import com.erlantzoniga.stormcourse.testutils.CustomArgumentMatchers;
import com.erlantzoniga.stormcourse.utils.Constants;

import java.util.UUID;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WordCountBoltTest {

  WordCountBolt wordCountBolt = new WordCountBolt();

  @Mock
  Tuple mockTuple;
  @Mock
  BasicOutputCollector mockCollector;
  @Mock
  OutputFieldsDeclarer mockDeclarer;

  UUID id = UUID.fromString("123e4567-e89b-12d3-a456-426655440000");

  @Test
  public void test_execute_once() {
    // prepare
    doReturn(id).when(mockTuple).getValueByField(Constants.ID);
    doReturn("B0ss").when(mockTuple).getStringByField(Constants.WORD);

    // act
    wordCountBolt.execute(mockTuple, mockCollector);

    // assert
    verify(mockCollector, times(1)).emit(new Values(id,"B0ss", 1));
  }

  @Test
  public void test_execute_twice() {
    // prepare
    doReturn(id).when(mockTuple).getValueByField(Constants.ID);
    doReturn("B0ss").when(mockTuple).getStringByField(Constants.WORD);

    // act
    wordCountBolt.execute(mockTuple, mockCollector);
    wordCountBolt.execute(mockTuple, mockCollector);

    // assert
    verify(mockCollector, times(1)).emit(new Values(id, "B0ss", 1));
    verify(mockCollector, times(1)).emit(new Values(id, "B0ss", 2));
  }

  @Test
  public void test_execute_multiple_words() {
    // prepare
    doReturn(id).when(mockTuple).getValueByField(Constants.ID);
    doReturn("B0ss", "St0rm", "B0ss").when(mockTuple).getStringByField(Constants.WORD);

    // act
    wordCountBolt.execute(mockTuple, mockCollector);
    wordCountBolt.execute(mockTuple, mockCollector);
    wordCountBolt.execute(mockTuple, mockCollector);
    wordCountBolt.execute(mockTuple, mockCollector);

    // assert
    verify(mockCollector, times(1)).emit(new Values(id, "B0ss", 1));
    verify(mockCollector, times(1)).emit(new Values(id, "St0rm", 1));
    verify(mockCollector, times(1)).emit(new Values(id, "B0ss", 2));
    verify(mockCollector, times(1)).emit(new Values(id, "B0ss", 3));
  }

  @Test
  public void test_declare_output_fields() {
    // act
    wordCountBolt.declareOutputFields(mockDeclarer);

    // assert
    mockDeclarer.declare(CustomArgumentMatchers.fieldsEq(new Fields(Constants.ID, Constants.WORD, Constants.COUNT)));
  }
}
