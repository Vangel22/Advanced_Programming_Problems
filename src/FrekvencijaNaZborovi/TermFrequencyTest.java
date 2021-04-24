package FrekvencijaNaZborovi;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class TermFrequencyTest {
    public static void main(String[] args) throws FileNotFoundException {
        String[] stop = new String[] { "во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја" };
        TermFrequency tf = new TermFrequency(System.in,
                stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}

class TermFrequency{

    TreeMap<String,Word> words;
    int total;
    List<String> stopWords;

    public TermFrequency(InputStream input, String[] stop){
        total = 0;
        words = new TreeMap<>();
        stopWords = Arrays.asList(stop);
        Scanner scan = new Scanner(input);

        while(scan.hasNext()){
            String word = scan.next();
            word = word.toLowerCase();
            //word = word.replaceAll("[,.]*","");
            word = word.replace(".","");
            word = word.replace(",","");

            if(!word.equals("\\s+") && !stopWords.contains(word) && !word.equals("")){
                total++;

                if(words.containsKey(word)){
                    words.get(word).upCount();
                }
                else{
                    words.put(word, new Word(word));
                }

            }
        }
    }

    public int countTotal(){
        return total;
    }

    public int countDistinct(){
        return words.size();
    }

    public List<String> mostOften(int k){
        List<Word> list = words.values()
                .stream().sorted(Comparator.comparing(Word::getCount).reversed().thenComparing(Word::getWord))
                .limit(k)
                .collect(Collectors.toList());

        List<String> listout = new ArrayList<>(k);
        for(int i=0; i<list.size(); i++){
            listout.add(list.get(i).getWord());
        }
        return listout;
    }
}

class Word{

    String word;
    int count;

    public Word(String word) {
        this.word = word;
        this.count = 1;
    }

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }

    public int upCount(){
        return count++;
    }

    @Override
    public String toString() {
        return word + " ";
    }
}