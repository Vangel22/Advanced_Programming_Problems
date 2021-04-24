package GenerickaTrojka;

import java.util.*;

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.average());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.average());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.average());
        tDouble.sort();
        System.out.println(tDouble);
    }
}
// vasiot kod ovde
// class Triple

class Triple<E extends Number>{
    List<E> list;

    public Triple(E a,E b,E c) {
        list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
    }

    public double max(){
        return Double.max(list.get(0).doubleValue(),Double.max(list.get(1).doubleValue(), list.get(2).doubleValue()));
    }

    public double average(){
        return (double) (list.get(0).doubleValue() + list.get(1).doubleValue() + list.get(2).doubleValue())/3;
    }

    //Buble sort so swap
    public void sort(){
        for (int i = 0; i <3; i++){
            for(int j = 1; j < (3-i); j++){
                if(list.get(j-1).doubleValue() > list.get(j).doubleValue()){
                    E temp = list.get(j-1);
                    list.set(j-1, list.get(j));
                    list.set(j, temp);
                }
            }
        }
    }

    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f", list.get(0).doubleValue(),list.get(1).doubleValue(),list.get(2).doubleValue());
    }
}


