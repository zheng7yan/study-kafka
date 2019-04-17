package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileProcessor {

    private String filepath;
    private char [] buffer = new char[1024];

    public FileProcessor(String filepath) {
        this.filepath = filepath;
    }

    public void process() {
        InputStream stream;
        BufferedReader reader = null;
        try {
            stream = Files.newInputStream(Paths.get(filepath));
            reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (reader == null) {
            return;
        }
        int nRead = 0;

        while (true) {
            try {
                if (!reader.ready()) break;
                nRead = reader.read(buffer, 0, buffer.length);
                if (nRead > 0) {
                    System.out.println(new String(buffer, 0 , nRead));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String [] args) {
        FileProcessor processor = new FileProcessor("/home/zy/test.txt");
        processor.process();
    }
}
