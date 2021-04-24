package NajdobriFilmovi;
import java.util.*;
import java.util.stream.Collectors;

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}

class Movie{
    String title;
    int[] ratings;
    int allRatings = 0;

    public Movie(String title, int[] ratings) {
        this.title = title;
        this.ratings = ratings;
        this.allRatings++;
    }

    public String getTitle() {
        return title;
    }

    public int[] getRatings() {
        return ratings;
    }

    public double averageRating(){
        return Arrays.stream(ratings).average().getAsDouble();
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings",title,averageRating(),ratings.length);
    }
}

class MoviesList{
    Set<Movie> movies;

    public MoviesList() {
        movies = new TreeSet<>(Comparator.comparing(m -> m.title)); //bidejki koristam treeSet mi se podredeni elementite
    }

    public void addMovie(String title, int[] ratings){
        Movie newMovie = new Movie(title,ratings);
        movies.add(newMovie);
    }

    public List<Movie> top10ByAvgRating(){
        return movies.stream()
                            .sorted(Comparator.comparing(Movie::averageRating)
                            .reversed()
                            .thenComparing(Movie::getTitle))
                            .limit(10)
                            .collect(Collectors.toList());
    }

    //просечен ретјтинг на филмот x вкупно број на рејтинзи на филмот / максимален број на рејтинзи
    // (од сите филмови во листата)
    public List<Movie> top10ByRatingCoef(){
        return movies.stream()
               .sorted(Comparator.comparing(movie -> -(movie.averageRating() * movie.ratings.length / movie.allRatings)))
               .collect(Collectors.toList()).subList(0,10);

    }

}