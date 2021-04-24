package KalendarNaNastani;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}

// vashiot kod ovde
class Event implements Comparable<Event>{
    String name;
    String location;
    Date date;

    public Event(String name, String location, Date date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    @Override
    public int compareTo(Event o) {
        if(this.date.compareTo(o.date) != 0) //proveruvam dali e isti datumot
            return this.date.compareTo(o.date);
        //ako ne e isti sporedi gi iminjata da vidis dali e isti nastan
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, YYY HH:mm");
        return sdf.format(date) +" at "+ location +", "+ name;
    }
}

class WrongDateException extends Throwable{
    Date date;
    public WrongDateException(Date date) {
        this.date = date;
    }

    @Override
    public String getMessage() {
        String dateString = date.toString();
        String fk = dateString.replace("GMT","UTC");
        return "Wrong date: "+fk;
    }
}

class EventCalendar{
    int year;
    HashMap<Integer, TreeSet<Event>> events;
    int[] eventsByMonth;

    public EventCalendar(int year) {
        this.year = year;
        events = new HashMap<>();
        eventsByMonth = new int[12];
        //sekoj mesec velam deka ima 0 nastani na pocetok
        for(int i = 0; i < 12; i++){
            eventsByMonth[i] = 0;
        }
    }

    public void addEvent(String name,String location,Date date) throws WrongDateException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentYear = calendar.get(Calendar.YEAR);

        if(year != currentYear)
            throw new WrongDateException(date);

        int month = calendar.get(Calendar.MONTH);

        eventsByMonth[month]++; //mesecite gi cuvam kako indeksi i koga ke povikam nekoj indeks so inkrementacijata
        //znam kolku nastani ima vo odreden mesec

        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

        if(!events.containsKey(dayOfYear)){
            events.put(dayOfYear,new TreeSet<Event>());
            //mi treba da za sekoj den gi zemam site nastani i zatoa pravam TreeSet od Event-i
        }

        Event e = new Event(name, location, date);
        events.get(dayOfYear).add(e);
    }

    public void listEvents(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int dayInYear = calendar.get(Calendar.DAY_OF_YEAR);

        //ako postoi klucot podredi gi rastecki ili leksikografski ako se isti
        if(!events.containsKey(dayInYear)){
            System.out.println("No events on this day!");
            return;
        }

        TreeSet<Event> treeSet = events.get(dayInYear);

        for(Event e : treeSet){
            System.out.println(e);
        }
    }

    public void listByMonth(){
        for(int i = 0 ; i < 12; i++){
            System.out.println((i+1) +" : "+ eventsByMonth[i]);
        }
    }
}