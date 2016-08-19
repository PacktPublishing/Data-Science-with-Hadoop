import java.io.IOException;

import org.apache.hadoop.io.* ;
import org.apache.hadoop.mapred.* ;
import org.apache.hadoop.mapred.lib.* ;

public class UFORecordValidationMapper extends MapReduceBase
implements Mapper<LongWritable, Text, LongWritable, Text>
{
    
    public void map(LongWritable key, Text value,
        OutputCollector<LongWritable, Text> output,
        Reporter reporter) throws IOException
    {
       String line = value.toString();
            
        if (validate(line))
            output.collect(key, value);
    }        

        private boolean validate(String str)
        {
            String[] parts = str.split("\t") ;
            
            if (parts.length != 6)
            return false ;
            
            return true ;
        }
    }
    
