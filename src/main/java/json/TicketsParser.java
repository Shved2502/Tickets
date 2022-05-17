package json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TicketsParser {

    public static final String DEPARTURE = "Владивосток";
    public static final String ARRIVAL = "Тель-Авив";
    public static final String PATTERN = "dd.MM.yyHH:mm";
    public static final String FILE_NAME = "json/tickets.json";

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        try (Reader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE_NAME))))) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            String jsonString = jsonObject.toJSONString();
            ObjectMapper mapper = new ObjectMapper();
            TicketsArray ticketsArray = mapper.readValue(jsonString, TicketsArray.class);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);

            long duration = 0L;
            int counter = 0;
            List<Long> durationTime = new ArrayList<>();

            for (Ticket t : ticketsArray.tickets) {
                if (t.origin_name.equals(DEPARTURE) && t.destination_name.equals(ARRIVAL)) {
                    long calculatedDuration = getDuration(formatter, t);
                    duration += calculatedDuration;
                    counter++;
                    durationTime.add(calculatedDuration);
                }
            }

            long durationInMinutes = duration / counter;

            System.out.println("Average travel time is " + getHoursAndMinutes(durationInMinutes));
            System.out.println("90th percentile flight time is " + getHoursAndMinutes(getPercentile(durationTime)));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static long getDuration(DateTimeFormatter formatter, Ticket t) {
        if (t.departure_time.length() == 4) {
            t.departure_time = "0" + t.departure_time;
        }
        if (t.arrival_time.length() == 4) {
            t.arrival_time = "0" + t.arrival_time;
        }
        LocalDateTime departureDate = LocalDateTime.parse(t.departure_date + t.departure_time, formatter);
        LocalDateTime arrivalDate = LocalDateTime.parse(t.arrival_date + t.arrival_time, formatter);

        return ChronoUnit.MINUTES.between(departureDate, arrivalDate);
    }

    private static long getPercentile (List<Long> gaps) {
        Collections.sort(gaps);
        int index = (int) Math.ceil(0.9 * gaps.size());
        return gaps.get(index - 1);
    }

    private static String getHoursAndMinutes (long minutes) {
        return minutes / 60 + " hours " + minutes % 60 + " minutes";
    }
}
