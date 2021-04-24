package SuperString;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;


class SuperString{
    LinkedList<String> list;
    Stack<String> record;

    public SuperString() {
        list = new LinkedList<>();
        record = new Stack<>();
    }

    public void append(String s){
        list.addLast(s);
        record.push(s);
    }

    public void insert(String s){
        list.addFirst(s);
        record.push(s);
    }

    //treba da gi vidam site bukvi sto postojat vo listata
    //i da gi sporedam so String s
    public boolean contains(String s){
        return this.toString().contains(s);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(String s : list){
            sb.append(s);
        }

        return sb.toString();
    }

    //gi prevrtuva elementite vo poleto(indeksot)
    //pa go prevrtuva rasporedot na indeksite
    public void reverse(){
        LinkedList<String> reversed = new LinkedList<>();
        String s;
        while(!list.isEmpty()){
            s = list.getLast();
            s = reverseString(s);
            reversed.addLast(s);
            list.removeLast(); //go brisam za da mi se namali size() i da odi na sledniot element
        }
        for(String str : reversed){
            list.addLast(str);
        }
    }

    public String reverseString(String s){
        StringBuilder sb = new StringBuilder(s);
        return sb.reverse().toString();
    }

    public void removeLast(int k){
        while(k > 0){
            String toRemove = record.pop(); //go zemam posledniot dodaden string
            list.remove(toRemove); //go brisam
            String toRemoveReversed = reverseString(toRemove); //go zemam posledniot reversedString
            list.remove(toRemoveReversed); //go brisam
            k--;
        }
    }
}

public class SuperStringTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (  k == 0 ) {
            SuperString s = new SuperString();
            while ( true ) {
                int command = jin.nextInt();
                if ( command == 0 ) {//append(String s)
                    s.append(jin.next());
                }
                if ( command == 1 ) {//insert(String s)
                    s.insert(jin.next());
                }
                if ( command == 2 ) {//contains(String s)
                    System.out.println(s.contains(jin.next()));
                }
                if ( command == 3 ) {//reverse()
                    s.reverse();
                }
                if ( command == 4 ) {//toString()
                    System.out.println(s);
                }
                if ( command == 5 ) {//removeLast(int k)
                    s.removeLast(jin.nextInt());
                }
                if ( command == 6 ) {//end
                    break;
                }
            }
        }
    }
}
