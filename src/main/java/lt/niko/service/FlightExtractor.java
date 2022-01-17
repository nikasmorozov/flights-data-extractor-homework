package lt.niko.service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lt.niko.entity.Flight;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

public class FlightExtractor {

    private String extractIataCode(String fullAirportName) {
        return fullAirportName.substring(fullAirportName.indexOf("(")+1, fullAirportName.indexOf(")"));
    }

    public List<Flight> extractFlight(String baseUrl, LocalDateTime searchDateFrom) throws IOException {

        List<Flight> singleDateFlights = new ArrayList<>();

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);

        HtmlPage htmlPage = webClient.getPage(baseUrl);


        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().appendPattern("E dd MMM h:mma yyyy")
                .parseCaseInsensitive()
                .toFormatter();

        DateTimeFormatter dateTimeFormatterOutput = DateTimeFormatter
                .ofPattern("E MMM dd HH:mm:ss z yyyy")
                //TODO grab time zone according to airports IATA code?
                .withZone(ZoneId.of("GMT+3"));


            List<HtmlElement> outboundDepartureAirports = htmlPage
                    .getByXPath("//div[@class='fly5-flights fly5-depart th']" +
                            "//td[@data-title='Departs']//span[@class='flfrom']");

            List<HtmlElement> outboundArrivalAirports = htmlPage
                    .getByXPath("//div[@class='fly5-flights fly5-depart th']" +
                            "//td[@data-title='Arrives']//span[@class='flfrom']");

            List<HtmlElement> outboundDepartureTimes = htmlPage
                    .getByXPath("//div[@class='fly5-flights fly5-depart th']" +
                            "//td[@data-title='Departs']//span[@class='fltime ftop']");

            List<HtmlElement> outboundDepartureDates = htmlPage
                    .getByXPath("//div[@class='fly5-flights fly5-depart th']" +
                            "//td[@data-title='Departs']//span[@class='fldate']");

            List<HtmlElement> outboundArrivalTimes = htmlPage
                    .getByXPath("//div[@class='fly5-flights fly5-depart th']" +
                            "//td[@data-title='Arrives']//span[@class='fltime ftop']");

            List<HtmlElement> outboundArrivalDates = htmlPage
                    .getByXPath("//div[@class='fly5-flights fly5-depart th']" +
                            "//td[@data-title='Arrives']//span[@class='fldate']");

            //Inbound:
            List<HtmlElement> inboundDepartureAirports = htmlPage
                    .getByXPath("//div[@class='fly5-flights fly5-return th']" +
                            "//td[@data-title='Departs']//span[@class='flfrom']");

            List<HtmlElement> inboundArrivalAirports = htmlPage
                    .getByXPath("//div[@class='fly5-flights fly5-return th']" +
                            "//td[@data-title='Arrives']//span[@class='flfrom']");

            List<HtmlElement> inboundDepartureTimes = htmlPage
                    .getByXPath("//div[@class='fly5-flights fly5-return th']" +
                            "//td[@data-title='Departs']//span[@class='fltime ftop']");

            List<HtmlElement> inboundDepartureDates = htmlPage
                    .getByXPath("//div[@class='fly5-flights fly5-return th']" +
                            "//td[@data-title='Departs']//span[@class='fldate']");

            List<HtmlElement> inboundArrivalTimes = htmlPage
                    .getByXPath("//div[@class='fly5-flights fly5-return th']" +
                            "//td[@data-title='Arrives']//span[@class='fltime ftop']");

            List<HtmlElement> inboundArrivalDates = htmlPage
                    .getByXPath("//div[@class='fly5-flights fly5-return th']" +
                            "//td[@data-title='Arrives']//span[@class='fldate']");

            //fares
            List<HtmlElement> outboundPrices = htmlPage
                    .getByXPath("//div[@class='fly5-flights fly5-depart th']" +
                            "//span[@class='flprice']");

            List<HtmlElement> inboundPrices = htmlPage
                    .getByXPath("//div[@class='fly5-flights fly5-return th']" +
                            "//span[@class='flprice']");

            for (int i = 0; i < outboundDepartureAirports.size(); i++) {

                for(int j = 0; j < inboundDepartureAirports.size(); j++) {

                    BigDecimal tax = new TaxExtractor().collectTaxes(baseUrl, i, j);

                    LocalDateTime outboundDepartureDateTime = LocalDateTime.parse(outboundDepartureDates.get(i)
                            .asNormalizedText().replace(",", "") + " " + outboundDepartureTimes.get(i)
                            .asNormalizedText().replace("am", "AM").replace("pm", "PM")
                            + " " + searchDateFrom.getYear(), dateTimeFormatter);

                    LocalDateTime outboundArrivalDateTime = LocalDateTime.parse(outboundArrivalDates.get(i)
                            .asNormalizedText().replace(",", "") + " " + outboundArrivalTimes.get(i)
                            .asNormalizedText().replace("am", "AM").replace("pm", "PM")
                            + " " + searchDateFrom.getYear(), dateTimeFormatter);

                    LocalDateTime inboundDepartureDateTime = LocalDateTime.parse(inboundDepartureDates.get(j)
                            .asNormalizedText().replace(",", "") + " " + inboundDepartureTimes.get(j)
                            .asNormalizedText().replace("am", "AM").replace("pm", "PM")
                            + " " + searchDateFrom.getYear(), dateTimeFormatter);

                    LocalDateTime inboundArrivalDateTime = LocalDateTime.parse(inboundArrivalDates.get(j)
                            .asNormalizedText().replace(",", "") + " " + inboundArrivalTimes.get(j)
                            .asNormalizedText().replace("am", "AM").replace("pm", "PM")
                            + " " + searchDateFrom.getYear(), dateTimeFormatter);

                    Flight flight = Flight.builder()
                            .outboundDepartureAirport(extractIataCode(outboundDepartureAirports.get(i).asNormalizedText()))
                            .outboundArrivalAirport(extractIataCode(outboundArrivalAirports.get(i).asNormalizedText()))
                            .outboundDepartureTime(outboundDepartureDateTime.format(dateTimeFormatterOutput))
                            .outboundArrivalTime(outboundArrivalDateTime.format(dateTimeFormatterOutput))
                            .inboundDepartureAirport(extractIataCode(inboundDepartureAirports.get(j).asNormalizedText()))
                            .inboundArrivalAirport(extractIataCode(inboundArrivalAirports.get(j).asNormalizedText()))
                            .inboundDepartureTime(inboundDepartureDateTime.format(dateTimeFormatterOutput))
                            .inboundArrivalTime(inboundArrivalDateTime.format(dateTimeFormatterOutput))
                            .totalPrice(BigDecimal.valueOf(Double.parseDouble(outboundPrices.get(i)
                                    .asNormalizedText().replace(",", "")))
                                    .add(BigDecimal.valueOf(Double.parseDouble(inboundPrices.get(j)
                                            .asNormalizedText().replace(",", "")))
                            ))
                            .taxes(tax.toString())
                            .build();

                    singleDateFlights.add(flight);
                }
            }
        webClient.close();
            return singleDateFlights;
    }
}
