package json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
class Ticket {
    @JsonProperty("origin")
    public String origin;

    @JsonProperty("origin_name")
    public String origin_name;

    @JsonProperty("destination")
    public String destination;

    @JsonProperty("destination_name")
    public String destination_name;

    @JsonProperty("departure_date")
    public String departure_date;

    @JsonProperty("departure_time")
    public String departure_time;

    @JsonProperty("arrival_date")
    public String arrival_date;

    @JsonProperty("arrival_time")
    public String arrival_time;

    @JsonProperty("carrier")
    public String carrier;

    @JsonProperty("stops")
    public Integer stops;

    @JsonProperty("price")
    public Integer price;

}
