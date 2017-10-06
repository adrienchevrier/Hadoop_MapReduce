
    	import java.io.IOException;
        import java.util.*;
        import org.apache.hadoop.fs.Path;
        import org.apache.hadoop.conf.*;
        import org.apache.hadoop.io.*;
        import org.apache.hadoop.mapred.*;
        import org.apache.hadoop.util.*;

        public class pivotMapper {
 	   public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, IntWritable, Text> {
 	     private IntWritable idx = new Text();
         private Text rowValue = new Text();
		   /*Entry is a key corrseponding to the line and a value corresponding to a row in the dataset*/
		   /*Output is a couple with each word of the line and its column index*/
 	     public void map(LongWritable key, Text value, OutputCollector<IntWritable, Text> output, Reporter reporter) throws IOException {
 	       String line = value.toString();
 	       /*Read next line stored in buffer*/
            String[] row =  ligne.split(",");
            
            /*map each value of the row with its column index*/
            for (int i=0; i<row.size();i++) {
                idx.set(i);
                rowValue.set(row[i]);
                context.write(idx, rowValue);
            }
 	     }
 	   }
		
	   public static class Reduce extends MapReduceBase implements Reducer<IntWritable, Text, Text, IntWritable> {
         private Text line = new Text();
		   /*reduce takes as input the couples word/column index*/
		   /*Output is a row with each row at its new line position*/
         public void reduce(IntWritable key, Iterable<Text> values, OutputCollector<IntWritable, Text> output, Reporter reporter) throws IOException {
 	      
            String row = "";
            //add each input cell to new row
            for (Text val : values) {
                row.add(new IntWritable(val));
                row.append(",").append(new Text(val));
            }
            line.set(row);
            //write pair with line number and list of new values
 	       context.write(key, row);
 	     }
 	   }
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        Job job = new Job(conf, "pivotMapper");
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        job.setJarByClass(WordCount.class);
        job.setInputFormatClass(TextInputFormat.class);
        // map output types
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(Text.class);
        // reducer output types
        job.setOutputValueClass(IntWritable.class);
        job.setOutputValueClass(Text.class);
        //Get file in 1stt argument
        FileInputFormat.addInputPath(job, new Path(args[0]));
        //set file in 2nd argument
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

    job.waitForCompletion(true);
 	   }
}
