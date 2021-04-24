package Audition;

import java.util.*;

class Participant{
    private String code;
    private String name;
    private int age;

    public Participant(String code, String name, int age) {
        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return code+" "+name+" "+age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return code.equals(that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}

class Audition{
    Map<String,HashSet<Participant>> participants;

    public Audition() {
        this.participants = new HashMap<>();
    }

    public void addParticpant(String city, String code, String name, int age){
        //dokolku gradot ne postoi vo mojata mapa togas dodadi go vo novo mnozestvo
        participants.computeIfAbsent(city, (k) -> participants.put(city,new HashSet<>()));

        Participant par = new Participant(code, name, age);

        //vo odreden grad dodavam ucesnici
        participants.get(city).add(par);
        //Ne se zamaram da gi brisam ucesnicite so ist kod bidejki HashSet ne prima duplikat vrednosti
    }

    public void listByCity(String city){
        participants.get(city).stream()
                .sorted(Comparator.comparing(Participant::getName)
                .thenComparing(Participant::getAge)
                .thenComparing(Participant::getCode))
                .forEach(System.out::println);
    }
}

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}