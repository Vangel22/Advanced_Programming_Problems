package BlokovaStruktura;

import com.sun.source.tree.Tree;

import java.util.*;
import java.util.stream.Collectors;

class BlockContainerTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int size = scanner.nextInt();
        BlockContainer<Integer> integerBC = new BlockContainer<Integer>(size);
        scanner.nextLine();
        Integer lastInteger = null;
        for(int i = 0; i < n; ++i) {
            int element = scanner.nextInt();
            lastInteger = element;
            integerBC.add(element);
        }
        System.out.println("+++++ Integer Block Container +++++");
        System.out.println(integerBC);
        System.out.println("+++++ Removing element +++++");
        integerBC.remove(lastInteger);
        System.out.println("+++++ Sorting container +++++");
        integerBC.sort();
        System.out.println(integerBC);
        BlockContainer<String> stringBC = new BlockContainer<String>(size);
        String lastString = null;
        for(int i = 0; i < n; ++i) {
            String element = scanner.next();
            lastString = element;
            stringBC.add(element);
        }
        System.out.println("+++++ String Block Container +++++");
        System.out.println(stringBC);
        System.out.println("+++++ Removing element +++++");
        stringBC.remove(lastString);
        System.out.println("+++++ Sorting container +++++");
        stringBC.sort();
        System.out.println(stringBC);
    }
}

class Block<T extends Comparable<T>>{
    int n;
    TreeSet<T> elements;

    public Block(TreeSet<T> elements, int n) {
        this.n = n;
        this.elements = elements;
    }

    public TreeSet<T> getElements() {
        return elements;
    }
}

class BlockContainer<T extends Comparable<T>>{
    int n;
    Block<T> last;
    HashMap<Block<T>,TreeSet<T>> blockchain;

    public BlockContainer(int n) { //n blokovi
        this.n = n;
        this.last = new Block<T>(new TreeSet<T>(),n);
        this.blockchain = new HashMap<Block<T>,TreeSet<T>>();
        this.blockchain.put(this.last,this.last.getElements());
    }

    public void add(T a){
        if(this.last.getElements().size() > n){ //poln blok
            Block<T> newBlock = new Block<T>(new TreeSet<>(), this.n);
            this.last = newBlock;
            this.last.getElements().add(a);
            this.blockchain.put(newBlock, newBlock.getElements());
        }else{
            this.blockchain.get(this.last).add(a);
        }
    }

    public boolean remove(T a){
        if(this.last.getElements().size() <= 0){
            this.blockchain.get(this.last).remove(a); //go brisam elementot
            this.blockchain.remove(this.last); //go brisam blokot
            return true;
        }else{
            this.blockchain.get(this.last).remove(a);
            return true;
        }
    }

    public void sort(){
        this.blockchain = (HashMap<Block<T>, TreeSet<T>>) this.blockchain.keySet().stream().sorted().collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return this.blockchain.keySet().toString();

    }
}

//11 2
//89 12 54 11 5 1 7 8 2 4 14
//abc ccc bcxs abcde fdsr aerdd fdsa fdf etie lidj trdf