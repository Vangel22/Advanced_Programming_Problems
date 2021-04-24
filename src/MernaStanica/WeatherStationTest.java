package MernaStanica;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Measurement{
    float temperature;
    float wind;
    float humidity;
    float visibility;
    Date date;

    public Measurement(float temperature, float wind, float humidity, float visibility, Date date) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.visibility = visibility;
        this.date = date;
    }

    public float getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s",
                this.temperature, this.wind, this.humidity, this.visibility, this.date.toString());
    }
}

class WeatherStation{
    private int days;
    private TreeMap<Date,Measurement> measurements;
    private static final long MAX_DIFF = 150000;

    public WeatherStation(int days){
        measurements = new TreeMap<>();
        this.days = days;
    }

    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date){
      Measurement m = new Measurement(temperature, wind, humidity, visibility, date);

      if(this.itCantBeAdded(date))
          return;

      measurements.put(date,m);
      clear();
    }

    public int total(){
        return measurements.size();
    }

    public void status(Date from, Date to) throws RuntimeException{
        Map<Date,Measurement> subMap = this.measurements.subMap(from,true,to,true);
        if(subMap.isEmpty())
            throw new RuntimeException();

        double average = subMap.values().stream().peek(System.out::println)
                        .mapToDouble(Measurement::getTemperature)
                        .average()
                        .orElse(0);

        System.out.printf("Average temperature: %.2f%n", average);
    }

    //ova e proverka dali datumite se razlikuvaat za 2.5 minuti
    public boolean itCantBeAdded(Date date){
        return measurements.values()
                .stream()
                .anyMatch(measurement -> Math.abs(measurement.date.getTime() - date.getTime()) < MAX_DIFF);
    }

    private long daysBetween(Date d1, Date d2){
        return Math.abs(ChronoUnit.DAYS.between(d1.toInstant(),d2.toInstant()));
    }

    //Ova e metod za da gi izbriseme denovite -> gi zemame denovite pomegju dvata datumi
    //i gi sporeduvame so denovite koi se predadeni kako parametar
    public void clear(){
        Date latest = this.measurements.lastKey();
        Set<Date> toRemove = this.measurements.keySet().stream()
                .filter(m -> daysBetween(m,latest) >= this.days)
                .collect(Collectors.toSet());
        for(Date remove : toRemove){
            this.measurements.remove(remove);
        }
    }

}




public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}

// vashiot kod ovde
