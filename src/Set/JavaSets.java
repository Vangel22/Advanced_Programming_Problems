package Set;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class JavaSets {
    public static void main(String[] args) {

        Set<Integer> hashSet = new HashSet<>();

        hashSet.add(23);
        hashSet.add(4);
        hashSet.add(4); //ignored
        hashSet.add(4); //ignored
        hashSet.add(10);

        //some methods
        hashSet.isEmpty(); //true if empty
        hashSet.contains(10); //true
        hashSet.remove(23); //true if deleted
        hashSet.clear(); //deletes all elements


        for(int element : hashSet)
            System.out.println(element);

        Set<Integer> treeSet = new TreeSet<>();

        treeSet.add(23);
        treeSet.add(4);
        treeSet.add(4);
        treeSet.add(4);
        treeSet.add(10);
        treeSet.add(1);

        for (int element : treeSet)
            System.out.println(element); //sorted
    }
}

/*Set: Interface
* HashSet: Implementation of Set
* TreeSet: Implementation of Set [sorted]
*
* Properties:
* 1. Unordered Collection
* 2.Cannot store duplicate elements
* 3. It has more implementation such as HashSet, TreeHashSet and TreeSet
*
* TreeSet
* */