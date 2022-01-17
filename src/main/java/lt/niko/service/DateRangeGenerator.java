package lt.niko.service;

import lt.niko.entity.Flight;
import lt.niko.utilities.JacksonCsvWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class DateRangeGenerator {

    List<Flight> allFlightsInDateRange = new ArrayList<>();

    int daysFromToday = 10;
    int dateRange = 1;
    int daysToReturnFlight = 7;

    public void generateSearchDateRange() throws IOException {
        FlightExtractor flightExtractor = new FlightExtractor();

        DateTimeFormatter dateTimeFormatterSearch = DateTimeFormatter
                .ofPattern("E, dd MMM yyyy");

        for(int i = daysFromToday; i <= daysFromToday + dateRange - 1; i++){
            LocalDateTime searchDateFrom = LocalDateTime.now().plusDays(i);
            LocalDateTime searchDateTo = searchDateFrom.plusDays(daysToReturnFlight);

            String generatedUrl = "https://www.fly540.com/flights/nairobi-to-mombasa?isoneway=0&depairportcode=NBO&arrvairportcode=" +
                    "MBA&date_from="+
                    searchDateFrom.format(dateTimeFormatterSearch) +
                    "&date_to=" +
                    searchDateTo.format(dateTimeFormatterSearch) +
                    "&adult_no=1&children_no=0&infant_no=0&currency=" +
                    "KES&searchFlight=";

            System.out.println(generatedUrl);

            allFlightsInDateRange.addAll(flightExtractor.extractFlight(generatedUrl, searchDateFrom));
        }

        new JacksonCsvWriter().writeToCsvFile(allFlightsInDateRange, "NBO", "MBA");
    }
}
