package Prevodi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SubtitlesTest {
    public static void main(String[] args) {
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.println(String.format("SHIFT FOR %d ms", shift));
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}

class Subtitle{
    private int numSub;
    private String startSub;
    private String endSub;
    private List<String> text;


    public Subtitle(int numSub, String startSub, String endSub,List<String> text) {
        this.numSub = numSub;
        this.startSub = startSub;
        this.endSub = endSub;
        this.text = text;
    }

    public int timeInMs(String time){
        String [] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]) * 60 * 60 * 1000;
        int minutes = Integer.parseInt(parts[1]) * 60 * 1000;
        String [] parts2 = parts[2].split(",");
        int seconds = Integer.parseInt(parts2[0]) * 1000;

        return hours+minutes+seconds+Integer.parseInt(parts2[1]);
    }

    public String timeToString(int time){
        long hours = time/ (60*60*1000);
        time %=  (60*60*1000);
        long minutes = time / (60*1000);
        time %= (60*1000);
        long seconds = time / 1000;
        long milis = time % 1000;

        return String.format("%02d:%02d:%02d,%03d",hours,minutes,seconds,milis);
    }

    public void shift(int ms){
        int start = timeInMs(startSub);
        int end = timeInMs(endSub);

        this.startSub = timeToString(start + ms);
        this.endSub = timeToString(end + ms);

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(numSub+"\n");
        sb.append(startSub+" --> "+endSub+"\n");
        text.forEach(text -> sb.append(text+"\n"));
        return sb.toString();
    }
}

class Subtitles{
    List<Subtitle> subtitles;

    public Subtitles() {
        subtitles = new ArrayList<>();
    }

    public int loadSubtitles(InputStream inputStream){
        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNextLine()){
            int num = Integer.parseInt(scanner.nextLine()); //prvo ja zemam brojkata

            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            String startTime = parts[0];
            String endTime = parts[2]; //bidejki ja skokam "-->"


            List<String> text = new ArrayList<>();
            while(scanner.hasNextLine()) {
                String textLine = scanner.nextLine();
                if (textLine.equals(""))
                    break;
                text.add(textLine);

            }
            subtitles.add(new Subtitle(num,startTime,endTime,text));
        }
        return subtitles.size();
    }

    public void print(){
        subtitles.forEach(System.out::println);
    }

    public void shift(int ms){
        subtitles.forEach(subtitle -> subtitle.shift(ms));
    }
}
