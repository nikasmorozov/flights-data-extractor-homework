package lt.niko;

import lt.niko.service.FlightCollertor;
import lt.niko.service.TaxCollector;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException {
//        new FlightCollertor().generateSearchDateRange();
        new TaxCollector().collectTaxes("https://www.fly540.com/flights/nairobi-to-mombasa?isoneway=0&depairportcode=NBO&arrvairportcode=MBA&date_from=Wed,%2026%20Jan%202022&date_to=Wed,%2002%20Feb%202022&adult_no=1&children_no=0&infant_no=0&currency=KES&searchFlight=");
    }
}
