package lt.niko;

import lt.niko.service.FlightsScraper;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException {
        new FlightsScraper().generateSearchDateRange();
    }
}
