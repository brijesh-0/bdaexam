package wordcountlab;

// Importing libraries

import java.io.IOException;

import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapred.MapReduceBase;

import org.apache.hadoop.mapred.OutputCollector;

import org.apache.hadoop.mapred.Reducer;

import org.apache.hadoop.mapred.Reporter;


// Word Count Reducer Class

public class WCReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {


    // Reduce function

    public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output,

                       Reporter reporter) throws IOException {


        int count = 0;


        // Counting the frequency of each word

        while (values.hasNext()) {

            count += values.next().get();

        }


        // Emit (word, total_count)

        output.collect(key, new IntWritable(count));

    }

}


