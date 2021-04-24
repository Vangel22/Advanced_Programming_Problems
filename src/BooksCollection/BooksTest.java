package BooksCollection;
import java.util.*;
import java.util.stream.Collectors;

public class BooksTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        BookCollection booksCollection = new BookCollection();
        Set<String> categories = fillCollection(scanner, booksCollection);
        System.out.println("=== PRINT BY CATEGORY ===");
        for (String category : categories) {
            System.out.println("CATEGORY: " + category);
            booksCollection.printByCategory(category);
        }
        System.out.println("=== TOP N BY PRICE ===");
        print(booksCollection.getCheapestN(n));
    }

    static void print(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static TreeSet<String> fillCollection(Scanner scanner,
                                          BookCollection collection) {
        TreeSet<String> categories = new TreeSet<String>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
            collection.addBook(book);
            categories.add(parts[1]);
        }
        return categories;
    }
}

// Вашиот код овде

class Book implements Comparable<Book>{
    String title;
    String category;
    float price;

    public Book(String title, String category, float price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    @Override
    public int compareTo(Book o) {
        if(this.title.equalsIgnoreCase(o.title))
            return Float.compare(this.price,o.price);
        return title.compareTo(o.title);

        //Ovde ne koristam komparator bidejki ne mi se baraat konkretni aspekti od klasata t.e mi treba
        //ili samo title da proveram ili samo price a ne i dvete odednas
    }

    @Override
    public String toString() {
        return String.format("%s (%s) %.2f", title, category, price);
    }
}

class BookCollection{
    Set<Book> books;

    BookCollection(){
        books = new TreeSet<>();
        //mi gi ima sortirano podatocite za povicite
    }

    public void addBook(Book book){
        books.add(book);
    }

    public void printByCategory(String category){
        books.stream().filter(book -> book.category.equalsIgnoreCase(category)).forEach(System.out::println);
    }

    public List<Book> getCheapestN(int n){
        return books.stream().sorted(Comparator.comparing(book -> book.price)).collect(Collectors.toList()).subList(0,n);
        //logikata e deka prvo gi sortiram so sorted vo rastecki redosled i pravam podlista koja odi od
        //prviot do posledniot n element koj se bara od nas
    }
}
