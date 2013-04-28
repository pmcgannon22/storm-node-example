package storm.starter.bolt;

import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.Status;
import org.json.simple.JSONObject;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class TwitterFilterBolt extends BaseRichBolt {

    OutputCollector collector;


    public void execute(Tuple tuple) {
	Status status = (Status)tuple.getValue(0);

	GeoLocation g = status.getGeoLocation();
	if(g != null) {
	    JSONObject jrep = new JSONObject();
	    jrep.put("user", status.getUser().getScreenName());
	    jrep.put("text", status.getText());
	    jrep.put("t_id", status.getId());
	    jrep.put("latitude",g.getLatitude());
	    jrep.put("longitude",g.getLongitude());
	    List<HashtagEntity> hashtags = Arrays.asList(status.getHashtagEntities());
	    //	    jrep.put("mentions",hashtags);
	    collector.emit(tuple, new Values(jrep.toString().replace("\\","")));
	}
	collector.ack(tuple);
    }


    public void declareOutputFields(OutputFieldsDeclarer declarer) {
	declarer.declare(new Fields("hashtag"));
    }



    public void prepare(Map stormConf, TopologyContext context,
			    OutputCollector collector) {
	this.collector = collector;
	
    }

}