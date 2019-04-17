package kafka;

import org.junit.Assert;
import org.junit.Test;
import utils.FileProcessor;

import java.util.logging.Logger;

public class FileProcessorTest {
    @Test
    public void processTest() {
        Logger logger = Logger.getLogger("test");
//        logger.setLevel(Level.ALL);
        logger.info("let's begin");
        FileProcessor processor = new FileProcessor("/home/zy/test.txt");
        processor.process();
        Assert.assertEquals(1, 1);
    }
}
