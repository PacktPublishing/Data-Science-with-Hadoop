import java.io.IOException;

import org.apache.hadoop.conf.* ;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.* ;
import org.apache.hadoop.mapred.* ;
import org.apache.hadoop.mapred.lib.* ;

public class SkipData
{
    
    public static class MapClass extends MapReduceBase
    implements Mapper<LongWritable, Text, Text, LongWritable>
    {
        
        private final static LongWritable one = new LongWritable(1);
        private Text word = new Text("totalcount");
        
        public void map(LongWritable key, Text value,
            OutputCollector<Text, LongWritable> output,
                Reporter reporter) throws IOException
                {
                    String line = value.toString();
                
                if (line.equals("skiptext"))
                throw new RuntimeException("Found skiptext") ;
                output.collect(word, one);
            }
        }
        
        public static void main(String[] args) throws Exception
        {
            Configuration config = new Configuration() ;
            JobConf conf = new JobConf(config, SkipData.class);
            conf.setJobName("SkipData");
            
            conf.setOutputKeyClass(Text.class);
            conf.setOutputValueClass(LongWritable.class);
            
            conf.setMapperClass(MapClass.class);
            conf.setCombinerClass(LongSumReducer.class);
            conf.setReducerClass(LongSumReducer.class);
            
            FileInputFormat.setInputPaths(conf,args[0]) ;
            FileOutputFormat.setOutputPath(conf, new Path(args[1])) ;
            
            JobClient.runJob(conf);
        }
    }
