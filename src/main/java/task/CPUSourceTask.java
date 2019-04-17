package task;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;

import java.io.*;
import java.util.*;

public class CPUSourceTask extends SourceTask {
    private String filename;
    private String topic;
    private InputStream ins;
    private char [] buffer = new char[1024];

    public CPUSourceTask() {
    }

    @Override
    public String version() {
        return null;
    }

    @Override
    public void start(Map<String, String> props) {
        this.filename = props.get("FILE_CONFIG");
        this.topic = props.get("TOPIC_CONFIG");
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        try {
            this.ins = new FileInputStream(this.filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<SourceRecord> records = new ArrayList<>();
        while (this.ins != null && records.isEmpty()) {
            BufferedReader reader = null;
            int nRead = 0;
            try {
                reader = new BufferedReader(new InputStreamReader(this.ins));
                nRead = reader.read(buffer, 0 , buffer.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nRead > 0) {
                String line = new String(buffer, 0, nRead);
                Map sourcePartition = Collections.singletonMap("filename", filename);
                Map sourceOffset = Collections.singletonMap("position", String.valueOf(new Date().getTime()));
                records.add(new SourceRecord(sourcePartition, sourceOffset, topic, Schema.STRING_SCHEMA, line));
            }

            Thread.sleep(3000);
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return records;
    }

    @Override
    public void stop() {
        if (this.ins != null) {
            try {
                this.ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
