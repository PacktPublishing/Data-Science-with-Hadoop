import java.io.IOException;
import java.util.concurrent.TimeUnit ;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem ;
import org.apache.hadoop.fs.FSDataOutputStream ;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCountTimeout {
    
    public static class WordCountMapper
    extends Mapper<Object, Text, Text, IntWritable>{
        
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();
        
        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            String lockfile = "/user/hadoop/hdfs.lock" ;
            Configuration config = new Configuration() ;
            FileSystem hdfs = FileSystem.get(config) ;
            Path path = new Path(lockfile) ;
            if (!hdfs.exists(path))
            {
                byte[] bytes = "A lockfile".getBytes() ;
                FSDataOutputStream out = hdfs.create(path) ;
                out.write(bytes, 0, bytes.length);
                out.close() ;
                TimeUnit.SECONDS.sleep(100) ;
            }
            
            String[] words = value.toString().split(" ") ;
            
            for (String str: words)
            {
                word.set(str);
                context.write(word, one);
            }
        }
    }
    
    public static class WordCountReducer
    extends Reducer<Text,IntWritable,Text,IntWritable> {
        public void reduce(Text key, Iterable<IntWritable> values,
            Context context
            ) throws IOException, InterruptedException {
                int total = 0;
            for (IntWritable val : values) {
                total+= val.get() ;
            }
            context.write(key, new IntWritable(total));
        }
    }
    
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = new Job(conf, "word count");
        job.setJarByClass(WordCountTimeout.class);
        job.setMapperClass(WordCountMapper.class);
        job.setCombinerClass(WordCountReducer.class);
        job.setReducerClass(WordCountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
