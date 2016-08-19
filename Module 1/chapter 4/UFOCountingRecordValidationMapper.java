import java.io.IOException;

import org.apache.hadoop.io.* ;
import org.apache.hadoop.mapred.* ;
import org.apache.hadoop.mapred.lib.* ;

public class UFOCountingRecordValidationMapper extends MapReduceBase implements Mapper<LongWritable, Text, LongWritable, Text>
{
    
    public enum LineCounters
    {
        BAD_LINES,
        TOO_MANY_TABS,
        TOO_FEW_TABS
    } ;
    
    public void map(LongWritable key, Text value, OutputCollector<LongWritable, Text> output, Reporter reporter) throws IOException
    {
        String line = value.toString();
        
        if (validate(line, reporter))
        output.collect(key, value);
    }
    
    private boolean validate(String str, Reporter reporter)
    {
        String[] parts = str.split("\t") ;
        
        if (parts.length != 6)
        {
            if (parts.length < 6)
            {
                reporter.incrCounter(LineCounters.TOO_FEW_TABS, 1) ;
            }
            else
            {
                reporter.incrCounter(LineCounters.TOO_MANY_TABS, 1) ;
            }
            
            reporter.incrCounter(LineCounters.BAD_LINES, 1) ;
            
            if ((reporter.getCounter(LineCounters.BAD_LINES).getCounter()%10) == 0)
            {
                reporter.setStatus("Got 10 bad lines.") ;
                System.err.println("Read another 10 bad lines.") ;
            }
            
            return false ;
        }
        
        return true ;
    }
}

