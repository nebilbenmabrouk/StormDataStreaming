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

            if (previous_rate!=-1) {

                collector.emit("RateStream",new Values(String.valueOf(rate)));
                LOG.info("RateStream: "+rate);

                //check for gap
                diff = Math.abs(previous_rate - rate);

                if (diff >= max_gap){
                    String alert = "{ \"previous rate\": \""+previous_rate+"\"," +
                            "\"current rate\": \""+rate+"\"," +
                            "\"diff\": \""+diff+"\"," +
                            "\"message\": \"The Bitcoin value changed more than "+max_gap+"\""+
                            "}";

                    collector.emit("AlertStream", new Values(alert));
                    LOG.info("AlertStream: "+alert);

                    collector.emit("GapStream", new Values(String.valueOf(diff)));
                    LOG.info("GapStream: "+diff);

                } else{
                    collector.emit("GapStream", new Values(String.valueOf(0)));
                    LOG.info("GapStream: "+0);
                }

                //check for thresh
                double thresh = ((rate >= threshold) ? rate : threshold);
                collector.emit("ThresholdStream",new Values(String.valueOf(thresh)));
                LOG.info("ThresholdStream: "+thresh);

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
        declarer.declareStream("RateStream", new Fields("message"));
        declarer.declareStream("GapStream", new Fields("message"));
        declarer.declareStream("AlertStream", new Fields("message"));
        declarer.declareStream("ThresholdStream", new Fields("message"));
    }
}
