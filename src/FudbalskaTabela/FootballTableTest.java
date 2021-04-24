package FudbalskaTabela;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

class Team implements Comparable<Team>{
    private String name;
    private int numWins;
    private int numLoses;
    private int numDraws;
    private int givenGoals;
    private int takenGoals;

    public Team(String name) {
        this.name = name;
        numWins = numDraws = numLoses = givenGoals = takenGoals = 0;
    }

    public void takeGoals(int goals){
        takenGoals+=goals;
    }

    public void giveGoals(int goals){
        givenGoals+=goals;
    }

    public void win(){
        numWins++;
    }

    public void lose(){
        numLoses++;
    }

    public void draw(){
        numDraws++;
    }

    public int getTotalPoints(){
        return numDraws+numWins*3;
    }

    public int goalsDiff(){
        return givenGoals - takenGoals;
    }

    @Override
    public String toString() {
        return String.format("%-15s%5d%5d%5d%5d%5d", name, numDraws+numLoses+numWins, numWins, numDraws, numLoses, numDraws+numWins*3);
    }

    @Override
    public int compareTo(Team o) {
        if (getTotalPoints() == o.getTotalPoints()){
            if (goalsDiff() == o.goalsDiff())
                return name.compareTo(o.name);
            else return -Integer.compare(goalsDiff(), o.goalsDiff());
        }
        return -Integer.compare(getTotalPoints(), o.getTotalPoints());
    }
}

class FootballTable{
    Map<String, Team> map;

    public FootballTable() {
        this.map = new HashMap<>();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals){
        map.computeIfAbsent(homeTeam, (k) -> map.put(homeTeam, new Team(homeTeam)));
        map.computeIfAbsent(awayTeam, (k) -> map.put(awayTeam, new Team(awayTeam)));

        Team home = map.get(homeTeam);
        Team away = map.get(awayTeam);

        home.giveGoals(homeGoals);
        home.takeGoals(awayGoals);
        away.giveGoals(awayGoals);
        away.takeGoals(homeGoals);

        if(homeGoals > awayGoals){
            home.win();
            away.lose();
        }else if (homeGoals < awayGoals){
            home.lose();
            away.win();
        }else{
            home.draw();
            away.draw();
        }
    }

    public void printTable(){
        int k = 1;
        List<Team> teamList = map.values().stream().sorted(Team::compareTo).collect(Collectors.toList());
        for (Team t:teamList){
            System.out.println(String.format("%2d. %s", k, t));
            k++;
        }
    }
}
public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}