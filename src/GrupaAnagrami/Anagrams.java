package GrupaAnagrami;
import java.io.InputStream;
import java.util.*;

public class Anagrams {

    public static void main(String[] args) {
        findAll(System.in);
    }

    public static void findAll(InputStream inputStream) {
        Map<String,TreeSet<String>> words = new TreeMap<>();

        Scanner input = new Scanner(inputStream);

        while(input.hasNext()){
            String word = input.nextLine();
            String sortedWord = wordSorted(word);
            words.computeIfAbsent(sortedWord, key -> new TreeSet<>());
            //ako postoi sortiran zbor smesti go vo mnozesto koe e sortirano
            words.get(sortedWord).add(word);
        }
        words.values().stream()
                .filter(set -> set.size()>=5)
                .sorted(Comparator.comparing(TreeSet::first))
                .forEach(set -> System.out.println(String.join(" ", set)));

    }

    public static String wordSorted(String s){
        char[] word = s.toCharArray();
        Arrays.sort(word);
        StringBuilder sorted = new StringBuilder();
        for(char c : word)
            sorted.append(c);

        return sorted.toString();
    }
}