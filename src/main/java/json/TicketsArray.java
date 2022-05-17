package json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties
class TicketsArray {
    @JsonProperty("tickets")
    public List<Ticket> tickets;

}
