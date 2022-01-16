package lt.niko.utilities;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lt.niko.entity.Flight;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JacksonCsvWriter {
    File outputCsvFile = new File("src/main/resources/extracted_flights_jackson.csv");

    public void writeToCsvFile(List<Flight> flightList) throws IOException {
        CsvMapper mapper = new CsvMapper();

        CsvSchema schema = mapper.schemaFor(Flight.class).withHeader();

        mapper.writer().with(schema).writeValues(outputCsvFile).writeAll(flightList);
    }
}
