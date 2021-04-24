package NaslovnaStranica;
import java.util.*;
import java.util.stream.Collectors;

public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for(Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch(CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}

class CategoryNotFoundException extends Exception{
    public CategoryNotFoundException(String msg) {
        super(String.format("Category %s was not found",msg));
    }
}

class Category{
    private final String ime_kategorija;

    public Category(String ime_kategorija) {
        this.ime_kategorija = ime_kategorija;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if(getClass() != o.getClass())
            return false;
        Category category = (Category) o;
        return Objects.equals(ime_kategorija, category.ime_kategorija);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ime_kategorija);
    }

    public String getIme_kategorija() {
        return ime_kategorija;
    }
}

abstract class NewsItem{
    protected String naslov;
    protected Date datum_objava;
    protected Category kategorija;

    public NewsItem(String naslov, Date datum_objava, Category kategorija) {
        this.naslov = naslov;
        this.datum_objava = datum_objava;
        this.kategorija = kategorija;
    }

    public Category getKategorija() {
        return kategorija;
    }

    public String getImeKategorija(){
        return kategorija.getIme_kategorija();
    }

    public String getNaslov() {
        return naslov;
    }

    public Date getDatum_objava() {
        return datum_objava;
    }

    public abstract String getTeaser();
}

class TextNewsItem extends NewsItem{
    private String tekst;

    public TextNewsItem(String naslov, Date datum_objava, Category kategorija, String tekst) {
        super(naslov, datum_objava, kategorija);
        this.tekst = tekst;
    }

    @Override
    public String getTeaser() {
        Date now = new Date();
        int minutes = (int) ((now.getTime() - datum_objava.getTime()) / 60 / 1000);
        return String.format("%s\n%d\n%s\n",naslov,minutes,tekst.length() <= 80 ? tekst : tekst.substring(0,80));
    }

    @Override
    public String toString() {
        return getTeaser();
    }
}

class MediaNewsItem extends NewsItem{
    private String url;
    private int br_pogledi;

    public MediaNewsItem(String naslov, Date datum_objava, Category kategorija, String url, int br_pogledi) {
        super(naslov, datum_objava, kategorija);
        this.url = url;
        this.br_pogledi = br_pogledi;
    }

    @Override
    public String getTeaser() {
        Date now = new Date();
        int minutes = (int) ((now.getTime() - datum_objava.getTime()) / 60 / 1000);
        return String.format("%s\n%d\n%s\n%d\n",naslov,minutes,url,br_pogledi);
    }

    @Override
    public String toString() {
        return getTeaser();
    }
}

class FrontPage{
    private List<NewsItem> vesti;
    private Category[] vestiKategorii;

    public FrontPage(Category[] vestiKategorii) {
        this.vesti = new ArrayList<>();
        this.vestiKategorii = vestiKategorii;
    }

    public void addNewsItem(NewsItem newsItem){
        vesti.add(newsItem);
    }

    /*public List<NewsItem> listByCategory(Category category){
        List<NewsItem> pomosna = new ArrayList<NewsItem>();
        for(NewsItem item : vesti){
            if(item.getKategorija().equals(category)){
                pomosna.add(item);
            }
        }
        return pomosna;
    }*/

    public List<NewsItem> listByCategory(Category category){
        return vesti
                .stream()
                .filter(i -> i.getKategorija().equals(category))
                .collect(Collectors.toList());
    }

    public List<NewsItem> listByCategoryName(String category)throws CategoryNotFoundException{

        if(Arrays.stream(vestiKategorii).noneMatch(i -> i.getIme_kategorija().equals(category)))
            throw new CategoryNotFoundException(category);

        List<NewsItem> result = new ArrayList<>();
        Arrays
                .stream(vestiKategorii)
                .filter(i -> i.getIme_kategorija().equals(category))
                .forEach(i -> result.addAll(listByCategory(i)));

        return result;
    }

    //dali postoi kategorija so ime -> argumentot sto se predava
    /*public List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException{
        List<NewsItem> pomosna = new ArrayList<NewsItem>();
        for(NewsItem item : vesti){
            if(item.getImeKategorija().equals(category)){
                pomosna.add(item);
            }
        }
        if(pomosna.isEmpty()) throw new CategoryNotFoundException(category);

        return pomosna;
    }*/

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        vesti.forEach(item -> sb.append(item.toString()));
        return sb.toString();
    }
}

