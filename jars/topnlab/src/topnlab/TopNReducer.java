package topnlab;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TopNReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private Map<String, Integer> countMap = new HashMap<>();

    @Override
    public void reduce(Text key, Iterable<IntWritable> values,
                       Reducer<Text, IntWritable, Text, IntWritable>.Context context)
            throws IOException, InterruptedException {

        int sum = 0;
        for (IntWritable val : values)
            sum += val.get();

        countMap.put(key.toString(), countMap.getOrDefault(key.toString(), 0) + sum);
    }

    @Override
    protected void cleanup(Reducer<Text, IntWritable, Text, IntWritable>.Context context)
            throws IOException, InterruptedException {

        // Sort the map by values in descending order
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(countMap.entrySet());
        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // Write top 20 entries
        int counter = 0;
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            if (counter++ == 20) break;
            context.write(new Text(entry.getKey()), new IntWritable(entry.getValue()));
        }
    }
}
