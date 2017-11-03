package org.ow2.proactive.data.streaming.bolt;

import org.apache.storm.task.ShellBolt;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ThresholdBolt extends ShellBolt implements IRichBolt {

    public ThresholdBolt() throws Exception{
        super("python",Thread.currentThread().getContextClassLoader().getResource("").getPath()+ "visdomThresholdRate.py");

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("thresholdRate"));
    }

    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

        /*private static final Logger LOG = LoggerFactory.getLogger(ThresholdBolt.class);

        @SuppressWarnings("rawtypes")
        public void prepare(Map stormConf, TopologyContext context) {

        }

        public void execute(Tuple input, BasicOutputCollector collector) {
            try {
                Double gapAlert = (Double) input.getValues().get(0);

                LOG.info("GapAlertStream: "+gapAlert);

            }catch (Exception e){
                LOG.error("ThresholdBolt error", e);
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

        }*/
}
