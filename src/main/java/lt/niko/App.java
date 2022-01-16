package lt.niko;

import lt.niko.service.FlightCollertor;
import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException {
        new FlightCollertor().generateSearchDateRange();
    }
}
