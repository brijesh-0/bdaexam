package topnlab;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TopNMapper extends Mapper<Object, Text, Text, IntWritable> {

    private static final IntWritable one = new IntWritable(1);
    private Text word = new Text();

    // Regex to clean out punctuation and special symbols
    private String tokens = "[_|$#<>\\^=\\[\\]*/\\\\,;,.\\-:()?!\"']";

    @Override
    public void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context)
            throws IOException, InterruptedException {

        // Clean line and convert to lowercase
        String cleanLine = value.toString().toLowerCase().replaceAll(this.tokens, " ");
        
        // Tokenize by whitespace
        StringTokenizer itr = new StringTokenizer(cleanLine);
        
        // Emit each word with a count of 1
        while (itr.hasMoreTokens()) {
            this.word.set(itr.nextToken().trim());
            context.write(this.word, one);
        }
    }
}
