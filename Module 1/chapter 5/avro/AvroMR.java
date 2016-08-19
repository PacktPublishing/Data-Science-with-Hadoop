import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData ;
import org.apache.avro.generic.GenericRecord ;

import org.apache.avro.Schema.Type;

import org.apache.avro.mapred.*;

import org.apache.avro.reflect.ReflectData;


import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.conf.Configured;

import org.apache.hadoop.fs.Path;

import org.apache.hadoop.mapred.*;

import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.io.* ;
//import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

//import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import org.apache.hadoop.util.*;
import org.apache.avro.util.Utf8;

class UFORecord
{
    UFORecord()
    {
    }
    
    public String shape ;
    public long count ;
}

public class AvroMR extends Configured  implements Tool
{
    public static final Schema PAIR_SCHEMA =         Pair.getPairSchema(Schema.create(Schema.Type.STRING), Schema.create(Schema.Type.LONG));
    public final static Schema OUTPUT_SCHEMA = ReflectData.get().getSchema(UFORecord.class);
    
    @Override
    public int run(String[] args) throws Exception
    {
        JobConf conf = new JobConf(getConf(), getClass());
        conf.setJobName("UFO count");
        
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 2)
        {
            System.err.println("Usage: avro UFO counter <in> <out>");
            System.exit(2);
            
        }

        FileInputFormat.addInputPath(conf, new Path(otherArgs[0]));
        Path outputPath = new Path(otherArgs[1]);
        FileOutputFormat.setOutputPath(conf, outputPath);
        outputPath.getFileSystem(conf).delete(outputPath);
        Schema input_schema = Schema.parse(getClass().getResourceAsStream("ufo.avsc")) ;
        AvroJob.setInputSchema(conf, input_schema);
        AvroJob.setMapOutputSchema(conf,
            Pair.getPairSchema(Schema.create(Schema.Type.STRING), Schema.create(Schema.Type.LONG)));
        
        AvroJob.setOutputSchema(conf, OUTPUT_SCHEMA);
        AvroJob.setMapperClass(conf, AvroRecordMapper.class);
        AvroJob.setReducerClass(conf, AvroRecordReducer.class);
        conf.setInputFormat(AvroInputFormat.class) ;
        JobClient.runJob(conf);
        
        return 0 ;
    }
    
    public static class AvroRecordMapper extends AvroMapper<GenericRecord, Pair<Utf8, Long>>
    {
        @Override
        public void map(GenericRecord in, AvroCollector<Pair<Utf8, Long>> collector, Reporter reporter) throws IOException
        {
            Pair<Utf8,Long> p = new Pair<Utf8,Long>(PAIR_SCHEMA) ;
            Utf8 shape = (Utf8)in.get("shape") ;
            if (shape != null)
            {
                p.set(shape, 1L) ;
                collector.collect(p);
            }
        }
    }
    
    public static class AvroRecordReducer extends AvroReducer<Utf8, Long, GenericRecord>
    {
        public void reduce(Utf8 key, Iterable<Long> values, AvroCollector<GenericRecord> collector, Reporter reporter) throws IOException
                {
                    long sum = 0;
                for (Long val : values)
                {
                    sum += val;
                }
                
                GenericRecord value = new GenericData.Record(OUTPUT_SCHEMA);
                value.put("shape", key);
                value.put("count", sum);
                
                collector.collect(value);
            }
        }
        
        public static void main(String[] args) throws Exception
        {
            int res = ToolRunner.run(new Configuration(), new AvroMR(), args);
            System.exit(res);
        }
    }
    
    
    
    
    
    
    
