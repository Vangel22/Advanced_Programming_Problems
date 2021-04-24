package sorrs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PasswordsPolicy {

    public static List<String> readInput() {
        final Scanner scan = new Scanner(System.in);
        final List<String> items = new ArrayList<>();
        while(scan.hasNextLine()){
            items.add(scan.nextLine());
        }
        return items;
    }

    public static void main(String[] args) {
        final List<String> lines = readInput();
        for(int i=0; i<lines.size(); i++){
            String line = lines.get(i);
            String[] l = line.split(" ");
            String[] parts = l;
            int prvBroj = Integer.parseInt(parts[0]);
            int vtorBroj = Integer.parseInt(parts[2]);
            //char
            String letter = parts[4];
            for(i=4; i<lines.size(); i++){
                //ovde mi zavrsi vremeto.
            }
        }
        System.out.println("The output should go here...");
    }
}
