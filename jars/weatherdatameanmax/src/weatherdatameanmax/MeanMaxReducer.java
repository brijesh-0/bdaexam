package weatherdatameanmax;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MeanMaxReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int maxTemp = Integer.MIN_VALUE;
        int totalTemp = 0;
        int count = 0;
        int days = 0;

        for (IntWritable value : values) {
            int temp = value.get();
            if (temp > maxTemp) {
                maxTemp = temp;
            }

            count++;

            // After every 3 temperatures, assume a day-group
            if (count == 3) {
                totalTemp += maxTemp;
                maxTemp = Integer.MIN_VALUE;
                count = 0;
                days++;
            }
        }

        // Avoid division by 0
        if (days > 0) {
            context.write(key, new IntWritable(totalTemp / days));
        } else {
            context.write(key, new IntWritable(0)); // or handle differently
        }
    }
}
