package org.ow2.proactive.data.streaming.util;

import javax.json.*;
import java.io.StringReader;

public class JsonUtil {

    static StringReader sr ;
    static JsonReader reader ;

    public static void main(String [] args){
        String data="{ \"Realtime Currency Exchange Rate\": { \"1. From_Currency Code\": \"BTC\", \"2. From_Currency Name\": \"Bitcoin\", \"3. To_Currency Code\": \"CNY\", \"4. To_Currency Name\": \"Chinese Yuan\", \"5. Exchange Rate\": \"40307.73159950\", \"6. Last Refreshed\": \"2017-10-30 16:40:38\", \"7. Time Zone\": \"UTC\" } }";
        getRate(data);
    }

    public static Double getRate(String data){
        sr = new StringReader(data);
        reader = Json.createReader(sr);
        JsonObject obj = reader.readObject();
        Double rate = new Double(obj.getJsonObject("Realtime Currency Exchange Rate").getString("5. Exchange Rate"));
        return rate;
    }

}
