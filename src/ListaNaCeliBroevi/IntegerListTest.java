package ListaNaCeliBroevi;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//Ako indeksot e negativen
class ArrayIndexOutOfBoundsException extends Exception{
    public ArrayIndexOutOfBoundsException() { }
}

class IntegerList{
    List<Integer> list;

    public IntegerList(){
        list = new ArrayList<>();
    }

    //Integer... -> tockite oznacuvaat Variable Arguments t.e koga neznaeme kolku argumenti ke ima konstruktorot
    public IntegerList(Integer... numbers) {
        this(); //so ova go povikuvame prviot konstruktor za da kreirame nova lista
        Collections.addAll(list, numbers);
    }

    //go dodava elementot na soodvetniot indeks
    public void add(int el,int idx){
       if(idx > size()){
           IntStream.range(size(),idx).forEach(i -> list.add(0));
       }
       list.add(idx,el);
    }

    public int remove(int idx){
        return list.remove(idx);
    }

    public void set(int el,int idx){
        list.set(idx,el);
    }

    public int get(int idx){
        return list.get(idx);
    }

    public int size(){
        return list.size();
    }

    public int count(int el){
        return (int) list
                .stream()
                .filter(i -> i == el)
                .count();
    }

    public void removeDuplicates(){
       /* list =  list ------MISLAM DEKA OVA NEMA DA SE IZVRSI DOBRO BIDEJKI NEMA DA GO PROVERI I NAPRED I NAZAD
                .stream()
                .distinct()
                .collect(Collectors.toList());*/
        Collections.reverse(list);
        list =  list
                .stream()
                .distinct()
                .collect(Collectors.toList());
        Collections.reverse(list);
    }

    public int sumFirst(int k){
        if(k > size()) k = size();
        return list
                .subList(0,k)
                .stream()
                .reduce(Integer::sum)//method reference
                .orElse(0);
    }

    public int sumLast(int k){
        if(k > size()) k = 0;

        return list
                .subList(size() - k,size())
                .stream()
                .reduce(Integer::sum)//method reference
                .orElse(0);
    }

    public void shiftRight(int idx,int k){
        int newIndex = (idx+k)%size();
        int el = list.remove(idx); //taka osloboduvam mesto za da se reorganiziraat ostanatite elementi sami
        //so remove go zemam elementot od stariot indeks i go smestuvam na noviot
        //tie sami se podreduvaat
        list.add(newIndex,el); //go smestuvam elementot na noviot indeks
    }

    public void shiftLeft(int idx,int k){
        int newIndex = idx - (k %size());
        if(newIndex < 0)
            newIndex += size();
        int el = list.remove(idx);
        list.add(newIndex,el);
    }

    public IntegerList addValue(int value){
        IntegerList integerList = new IntegerList();
        this.list.forEach(i -> integerList.add(i+value,list.indexOf(i)));
        return integerList;
    }
}




public class IntegerListTest {

    public static void main(String[] args) throws ArrayIndexOutOfBoundsException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) { //test standard methods
            int subtest = jin.nextInt();
            if ( subtest == 0 ) {
                IntegerList list = new IntegerList();
                while ( true ) {
                    int num = jin.nextInt();
                    if ( num == 0 ) {
                        list.add(jin.nextInt(), jin.nextInt());
                    }
                    if ( num == 1 ) {
                        list.remove(jin.nextInt());
                    }
                    if ( num == 2 ) {
                        print(list);
                    }
                    if ( num == 3 ) {
                        break;
                    }
                }
            }
            if ( subtest == 1 ) {
                int n = jin.nextInt();
                Integer a[] = new Integer[n];
                for ( int i = 0 ; i < n ; ++i ) {
                    a[i] = jin.nextInt();
                }
                IntegerList list = new IntegerList(a);
                print(list);
            }
        }
        if ( k == 1 ) { //test count,remove duplicates, addValue
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    System.out.println(list.count(jin.nextInt()));
                }
                if ( num == 1 ) {
                    list.removeDuplicates();
                }
                if ( num == 2 ) {
                    print(list.addValue(jin.nextInt()));
                }
                if ( num == 3 ) {
                    list.add(jin.nextInt(), jin.nextInt());
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
        if ( k == 2 ) { //test shiftRight, shiftLeft, sumFirst , sumLast
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    list.shiftLeft(jin.nextInt(), jin.nextInt());
                }
                if ( num == 1 ) {
                    list.shiftRight(jin.nextInt(), jin.nextInt());
                }
                if ( num == 2 ) {
                    System.out.println(list.sumFirst(jin.nextInt()));
                }
                if ( num == 3 ) {
                    System.out.println(list.sumLast(jin.nextInt()));
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
    }

    public static void print(IntegerList il) {
        if ( il.size() == 0 ) System.out.print("EMPTY");
        for ( int i = 0 ; i < il.size() ; ++i ) {
            if ( i > 0 ) System.out.print(" ");
            System.out.print(il.get(i));
        }
        System.out.println();
    }

}
