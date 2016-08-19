import java.io.File;
import java.io.IOException;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericData;
import org.apache.avro. generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;

public class OutputRead
{
    public static void main(String[] args) throws IOException
    {
        String filename = args[0] ;
        
        File file=new File(filename) ;
        DatumReader<GenericRecord> reader= new GenericDatumReader<GenericRecord>();
        DataFileReader<GenericRecord> dataFileReader=new DataFileReader<GenericRecord>(file,reader);
        
        while (dataFileReader.hasNext())
        {
            GenericRecord result=dataFileReader.next();
            String output = String.format("%s %d",
                result.get("shape"), result.get("count")) ;
            System.out.println(output) ;
        }
    }
}
