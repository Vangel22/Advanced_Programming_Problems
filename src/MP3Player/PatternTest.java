package MP3Player;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PatternTest {
    public static void main(String args[]) {
        List<Song> listSongs = new ArrayList<Song>();
        listSongs.add(new Song("first-title", "first-artist"));
        listSongs.add(new Song("second-title", "second-artist"));
        listSongs.add(new Song("third-title", "third-artist"));
        listSongs.add(new Song("fourth-title", "fourth-artist"));
        listSongs.add(new Song("fifth-title", "fifth-artist"));
        MP3Player player = new MP3Player(listSongs);


        System.out.println(player.toString());
        System.out.println("First test");


        player.pressPlay();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Second test");


        player.pressStop();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Third test");


        player.pressFWD();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
    }
}

class Song{
    String title;
    String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public String toString() {
        return "Song{title=" + title + ", artist=" + artist + "}";
    }
}

class MP3Player{
    List<Song> songList;
    int current;
    boolean stopped;
    boolean playing;

    public MP3Player(List<Song> songList) {
        this.songList = new ArrayList<>();
        this.songList.addAll(songList);
        this.current = 0;
        this.playing = false;
        this.stopped = false;
    }

    public void pressPlay(){
        System.out.printf("Song %s playing\n", playing ? "is already" : current + " is");
        playing = true;
        stopped = false;
    }

    public void pressStop(){
        current = 0;
        if(playing){
            playing = false;
            stopped = true;
            System.out.println("Song " + current + " is paused");
        }else if(stopped){
            stopped = false;
            System.out.println("Songs are stopped");
        }else {
            System.out.println("Songs are already stopped");
        }
    }

    public void pressFWD(){
        playing = false;
        stopped = true;
        if(current == songList.size()-1){
            current = 0;
        }else {
            current++;
        }
        System.out.println("Forward...");
    }

    public void pressREW(){
        playing = false;
        stopped = true;
        if(current == 0){
            current = songList.size()-1;
        }else{
            current--;
        }
        System.out.println("Reward...");
    }

    public void printCurrentSong(){
        System.out.println("Song{title=" + songList.get(current).getTitle() +", artist=" + songList.get(current).getArtist() + "}");
    }

    @Override
    public String toString() {
        String result = "MP3Player{currentSong = " + current + ", songList = [";
        String songs = this.songList.stream()
                .map(Song::toString)
                .collect(Collectors.joining(", "));
        return result + songs + "]}";
    }
}
