package Imenik;
import java.util.*;

public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }
}

class PhoneBook{
    Map<String,ArrayList<String>> phoneBook;
    final Comparator<String> comparator = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    };

    public PhoneBook() {
        phoneBook = new TreeMap<>(comparator);
    }

    public void addContact(String name, String number) throws DuplicateNumberException {
        if(phoneBook.values().stream().anyMatch(k -> k.equals(number))){
            throw new DuplicateNumberException(number);
        }
        phoneBook.putIfAbsent(name,new ArrayList<String>());
        phoneBook.computeIfPresent(name, (key,value) -> {
            value.add(number);
            return value;
        });
    }

    public void contactsByNumber(String number){
        //dokolku imame kontakti so poveke broevi gi naogjame, ili dokolku postoi takov kontakt so takov broj
        if(phoneBook.keySet().stream().anyMatch(k -> phoneBook.get(k).stream().anyMatch(t -> t.contains(number))))
            phoneBook.keySet().stream().forEach(k -> {
                phoneBook.get(k).stream().sorted(comparator).forEach(a -> { //ova mi treba za da iteriram ni arraylistata od broevi
                    if(a.contains(number)){
                        System.out.println(k + " " + a);
                    }
                });
            });
        else
            System.out.println("NOT FOUND");
    }

    public void contactsByName(String name){
        if(phoneBook.keySet().stream().anyMatch(k -> k.equals(name)))
        phoneBook.keySet().stream().filter(k -> k.equals(name)).forEach(a -> {
            phoneBook.get(a).stream().sorted(comparator).forEach(b -> {
                System.out.println(a+ " "+b);
            });
        });
        else
            System.out.println("NOT FOUND");

    }

}

class DuplicateNumberException extends Exception{
    String number;
    public DuplicateNumberException(String number) {
        this.number = number;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}


