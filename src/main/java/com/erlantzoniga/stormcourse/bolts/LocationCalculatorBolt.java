package com.erlantzoniga.stormcourse.bolts;


import com.erlantzoniga.stormcourse.core.ILocationCalculator;
import com.erlantzoniga.stormcourse.utils.Constants;

import java.util.UUID;

import javax.inject.Inject;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class LocationCalculatorBolt extends BaseBasicBolt {

  private final ILocationCalculator locationCalculator;

  @Inject
  public LocationCalculatorBolt(ILocationCalculator locationCalculator) {
    this.locationCalculator = locationCalculator;
  }

  @Override
  public void execute(Tuple input, BasicOutputCollector collector) {
    UUID id = (UUID) input.getValueByField(Constants.ID);
    float latitude = input.getFloatByField(Constants.LATITUDE);
    float longitude = input.getFloatByField(Constants.LONGITUDE);

    String countryCode = locationCalculator.getLocation(latitude, longitude);

    collector.emit(new Values(id, countryCode));
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields(Constants.ID, Constants.COUNTRY_CODE));
  }
}
