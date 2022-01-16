package lt.niko.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
@Data
@JsonPropertyOrder({"outbound_departure_airport",
        "outbound_arrival_airport",
        "outbound_departure_time",
        "outbound_arrival_time",
        "inbound_departure_airport",
        "inbound_arrival_airport",
        "inbound_departure_time",
        "inbound_arrival_time",
        "total_price",
        "taxes"
})
public class Flight {
    @JsonProperty("outbound_departure_airport")
    private String outboundDepartureAirport;

    @JsonProperty("outbound_arrival_airport")
    private String outboundArrivalAirport;

    @JsonProperty("outbound_departure_time")
    private String outboundDepartureTime;

    @JsonProperty("outbound_arrival_time")
    private String outboundArrivalTime;

    @JsonProperty("inbound_departure_airport")
    private String inboundDepartureAirport;

    @JsonProperty("inbound_arrival_airport")
    private String inboundArrivalAirport;

    @JsonProperty("inbound_departure_time")
    private String inboundDepartureTime;

    @JsonProperty("inbound_arrival_time")
    private String inboundArrivalTime;

    @JsonProperty("total_price")
    private BigDecimal totalPrice;

    @JsonProperty("taxes")
    private String taxes;

}
