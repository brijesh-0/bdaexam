package wordcountlab;


//Importing libraries

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapred.MapReduceBase;

import org.apache.hadoop.mapred.Mapper;

import org.apache.hadoop.mapred.OutputCollector;

import org.apache.hadoop.mapred.Reporter;


//Word Count Mapper Class

public class WCMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {


 // Map function

 public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter)

         throws IOException {


     String line = value.toString(); // Convert input line to string


     // Splitting the line into words on spaces

     for (String word : line.split(" ")) {

         if (word.length() > 0) {

             // Emit (word, 1)

             output.collect(new Text(word), new IntWritable(1));

         }

     }

 }

}