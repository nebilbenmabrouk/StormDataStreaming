package org.ow2.proactive.data.streaming.bolt;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import org.apache.storm.tuple.Values;
import org.ow2.proactive.data.streaming.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class BitcoinExchangeProcessing extends BaseBasicBolt {

    private static final Logger LOG = LoggerFactory.getLogger(BitcoinExchangeProcessing.class);

    static double previous_rate;
    static double threshold;
    static double max_gap;
    static String data;
    static double rate;
    static double diff;


    @SuppressWarnings("rawtypes")
    public void prepare(Map stormConf, TopologyContext context) {
        previous_rate=-1;
        threshold=40300.00;
        max_gap=50;
    }


    public void execute(Tuple input, BasicOutputCollector collector) {
        try {
            data = (String) input.getValues().get(0);
            rate = JsonUtil.getRate(data);

            collector.emit("RawRateStream",new Values(rate));
            LOG.info("RawRateStream: "+rate);

            if (previous_rate!=-1) {

                //raw value
                collector.emit("RawRateStream",new Values(rate));
                LOG.info("RawRateStream: "+rate);

                //check for gap
                diff = Math.abs(previous_rate - rate);
                if (diff > max_gap){
                    String alert = "{ \"previous rate\": \""+previous_rate+"\"," +
                            "\"current rate\": \""+rate+"\"," +
                            "\"diff\": \""+diff+"\"," +
                            "\"message\": \"The Bitcoin value changed more than "+max_gap+"\""+
                            "}";
                    collector.emit(new Values(alert));
                    LOG.info("Gap Alert: "+alert);
                }

                //check for thresh
                Double thresh = ((rate >= threshold) ? rate : threshold);
                collector.emit("ThresholdStream",new Values(thresh));
                LOG.info("Threshold stream: "+thresh);

            }

            //prepare next iteration
            previous_rate = rate;

        }catch (Exception e){
            LOG.error("BitcoinExchangeProcessing error", e);
        }

    }

    public void cleanup() {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream("RawRateStream", new Fields("rawRate"));
        declarer.declareStream("ThresholdStream", new Fields("thresholdRate"));
        declarer.declare(new Fields("message"));
    }
}
