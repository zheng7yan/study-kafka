package connector;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import task.CPUSourceTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CPUConnector extends SourceConnector {

    private String filename;
    private String topic;

    private static final ConfigDef CONFIG_DEF = new ConfigDef()
            .define("FILE_CONFIG", ConfigDef.Type.STRING, null, ConfigDef.Importance.HIGH, "")
            .define("TOPIC_CONFIG", ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, "");
    @Override
    public void start(Map<String, String> props) {
        filename = props.get("FILE_CONFIG");
        topic = props.get("TOPIC_CONFIG");
    }

    @Override
    public Class<? extends Task> taskClass() {
        return CPUSourceTask.class;
    }

    /*
    * Even with multiple tasks, this method implementation is usually pretty simple.
    * It just has to determine the number of input tasks, which may require contacting the remote service
    * it is pulling data from, and then divvy them up.
    * */
    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        ArrayList<Map<String,String>> configs = new ArrayList<>();
        Map<String,String> config = new HashMap<>();
        if (this.filename != null) {
            config.put("FILE_CONFIG", this.filename);
            config.put("TOPIC_CONFIG", this.topic);
        }
        configs.add(config);
        return configs;
    }

    @Override
    public void stop() {

    }

    @Override
    public ConfigDef config() {
        return CONFIG_DEF;
    }

    @Override
    public String version() {
        return null;
    }
}
