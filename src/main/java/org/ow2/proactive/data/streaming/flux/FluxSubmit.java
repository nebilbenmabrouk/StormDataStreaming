package org.ow2.proactive.data.streaming.flux;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.storm.flux.FluxBuilder;
import org.apache.storm.flux.model.ExecutionContext;
import org.apache.storm.flux.model.TopologyDef;
import org.apache.storm.flux.parser.FluxParser;
import org.apache.storm.thrift.*;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.utils.Utils;


public class FluxSubmit {

    public static void main(String[] args) throws TException, IOException, IllegalAccessException,
            InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {

        String propPath = Thread.currentThread().getContextClassLoader().getResource("BitcoinExchangeDataflow.properties").getPath();

        TopologyDef topologyDef = FluxParser.parseResource("/BitcoinExchangeDataflow.yaml", false, true, propPath, false);

        Config conf = FluxBuilder.buildConfig(topologyDef);
        ExecutionContext context = new ExecutionContext(topologyDef, conf);

        org.apache.storm.generated.StormTopology topology = FluxBuilder.buildTopology(context);
        topology.validate();

        System.out.println("'BitcoinExchangeDataflow' created and validated");

        LocalCluster cluster = new LocalCluster();

        System.out.println("Running the topology 'BitcoinExchangeDataflow' ..");

        cluster.submitTopology("BitcoinExchangeDataflow", conf, topology);

        Utils.sleep(120000);

        cluster.killTopology("BitcoinExchangeDataflow");

        cluster.shutdown();

        System.out.println("topology 'BitcoinExchangeDataflow' stopped");

        System.exit(0);
    }
}
