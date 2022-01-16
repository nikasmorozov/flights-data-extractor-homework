package lt.niko.utilities;

import lt.niko.entity.Flight;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BasicCsvWriter {
    public void writeCSVToFile(List<Flight> flightList) throws IOException {
        FileWriter fw = new FileWriter("src/main/resources/extracted_flights.csv");

        fw.append(String.join(";",
                "outbound_departure_airport",
                "outbound_arrival_airport",
                "outbound_departure_time",
                "outbound_arrival_time",
                "inbound_departure_airport",
                "inbound_arrival_airport",
                "inbound_departure_time",
                "inbound_arrival_time",
                "total_price",
                "taxes"));
        fw.append("\n");

        for (Flight flight : flightList) {
            fw.append(String.join(";",
                    flight.getOutboundDepartureAirport(),
                    flight.getOutboundArrivalAirport(),
                    flight.getOutboundDepartureTime(),
                    flight.getOutboundArrivalTime(),
                    flight.getInboundDepartureAirport(),
                    flight.getInboundArrivalAirport(),
                    flight.getInboundDepartureTime(),
                    flight.getInboundArrivalTime()
//                    ,flight.getTotalPrice()
            ));
            fw.append("\n");
        }
        fw.flush();
        fw.close();
        System.out.println("CSV written successfully");
    }
}
