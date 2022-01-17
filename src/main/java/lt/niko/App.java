package lt.niko;

import lt.niko.service.DateRangeGenerator;
import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException {
        new DateRangeGenerator().generateSearchDateRange();
    }
}
