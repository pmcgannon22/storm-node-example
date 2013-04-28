// to use this example, uncomment the twitter4j dependency information in the project.clj,
// uncomment storm.starter.spout.TwitterSampleSpout, and uncomment this class

package storm.starter;

import storm.starter.spout.TwitterSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import storm.starter.bolt.TwitterFilterBolt;
import storm.starter.bolt.PrinterBolt;
import storm.starter.bolt.RedisBolt;


public class TwitterStream {        
    public static void main(String[] args) {
        String username = "";
        String pwd = "";
        TopologyBuilder builder = new TopologyBuilder();
        
        builder.setSpout("spout", new TwitterSpout(username, pwd));
        builder.setBolt("hashtags", new TwitterFilterBolt())
                .shuffleGrouping("spout");
        builder.setBolt("redis", new RedisBolt()).shuffleGrouping("hashtags");
        
        Config conf = new Config();
        
        
        LocalCluster cluster = new LocalCluster();
        
        cluster.submitTopology("test", conf, builder.createTopology());
        
    }
}
