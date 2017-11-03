package org.ow2.proactive.data.streaming.bolt;

import org.apache.storm.task.ShellBolt;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;

import java.util.Map;

public class RawRateBolt extends ShellBolt implements IRichBolt {


        public RawRateBolt() throws Exception{
            super("python",Thread.currentThread().getContextClassLoader().getResource("").getPath()+ "visdomRawRate.py");

        }

        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("rawRate"));
        }

        public Map<String, Object> getComponentConfiguration() {
            return null;
        }
}
