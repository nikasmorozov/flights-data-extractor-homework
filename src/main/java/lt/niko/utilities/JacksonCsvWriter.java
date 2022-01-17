package lt.niko.utilities;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lt.niko.entity.Flight;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JacksonCsvWriter {

    public void writeToCsvFile(List<Flight> flightList, String from, String to) throws IOException {

        File outputCsvFile = new File("src/main/resources/extracted_flights_" + from + "_" + to + ".csv");

        CsvMapper mapper = new CsvMapper();

        CsvSchema schema = mapper.schemaFor(Flight.class).withHeader();

        mapper.writer().with(schema).writeValues(outputCsvFile).writeAll(flightList);
    }
}
