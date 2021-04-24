package MinMax;

import java.util.Scanner;

public class MinAndMax {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<String>();
        for(int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
        System.out.println(strings);
        MinMax<Integer> ints = new MinMax<Integer>();
        for(int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
    }
}

class MinMax<T extends Comparable<T>>{
    private T min;
    private T max;
    private int countMax;
    private int countMin;
    private int total;

    public MinMax() {
        countMax = 0;
        countMin = 0;
        total = 0;
    }

    //go zema segasniot max i segasniot min
    //compare na min i na max treba da napravam
    void update(T element){
        if(total == 0){
            max = element;
            min = element;
        }

        //analogno na komentarot dole za maksimum ovde so minimum
        if(min.compareTo(element) > 0){
            min = element;
            countMin = 1;
        }else if(min.compareTo(element) == 0){
            countMin++;
        }

        //dokolku imas poveke max elementi tie ke se izbrojat vo countMax
        //ako nemas se izvrsuva samo prviot if uslov i countMax vrakja 1 maksimum element
        if(max.compareTo(element) < 0){
            max = element;
            countMax = 1;
        }else if(max.compareTo(element) == 0){
            countMax++;
        }

        total++;
    }

    //methods are never used --> commented to avoid warnings
    /*public T max(){
        return max;
    }

    public T min(){
        return min;
    }*/

    @Override
    public String toString() {
       return min+" "+max+" "+(total-countMax-countMin)+"\n";
    }
}
