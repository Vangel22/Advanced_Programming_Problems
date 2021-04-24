package Arhiva;

import java.util.*;

class NonExistingItemException extends Exception{
    public NonExistingItemException(String message) {
        super(message);
    }
}

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        Date date = new Date(113, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();
            Date dateToOpen = new Date(date.getTime() + (days * 24 * 60
                    * 60 * 1000));
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while(scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch(NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}

abstract class Archive{
    //Cuvam lista od arhivi (elementi)
    protected int id;
    protected Date dateArchived;

    public Archive(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Date getDateArchived() {
        return dateArchived;
    }

    public void setDateArchived(Date dateArchived) {
        this.dateArchived = dateArchived;
    }
    //getType
    public abstract String getType();
}

class ArchiveStore{
    List<Archive> archives;
    StringBuilder log;

    public ArchiveStore() {
        archives = new ArrayList<>();
        this.log = new StringBuilder();
    }

    public void archiveItem(Archive item, Date date){
        item.setDateArchived(date);
        archives.add(item);
        log.append("Item "+item.getId()+" archived at "+date+"\n");
    }

    public void openItem(int id, Date date) throws NonExistingItemException {
        if (archives.stream().noneMatch(archive -> archive.getId()==id))
            throw new NonExistingItemException("Item with id "+id+" doesn't exist");

        for (Archive a:archives){
            if (a.getId() == id){
                if (a.getType().equals("LockedArchive")){
                    LockedArchive la = (LockedArchive) a;
                    if (la.getDateArchived().before(la.getDateToOpen()))
                        log.append("Item "+id+" cannot be opened before "+la.getDateToOpen()+"\n");
                    else
                        log.append("Item "+id+" opened at "+date+"\n");
                }else{
                    SpecialArchive sa = (SpecialArchive) a;
                    if (sa.getMaxOpen() == 0)
                        log.append("Item "+id+" cannot be opened more than "+sa.getCopyOfMaxOpen()+" times\n");
                    else{
                        log.append("Item "+id+" opened at "+date+"\n");
                        sa.open();
                    }
                }
            }
        }
    }

    public String getLog() {
        return log.toString().replaceAll("GMT","UTC");
    }
}


class LockedArchive extends Archive{

    Date dateToOpen;

    public LockedArchive(int id, Date dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }

    public Date getDateToOpen() {
        return dateToOpen;
    }

    @Override
    public String getType() {
        return "LockedArchive";
    }
}

class SpecialArchive extends Archive{

    int maxOpen;
    int copyOfMaxOpen;

    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
        copyOfMaxOpen = maxOpen;
    }

    public int getCopyOfMaxOpen(){
        return copyOfMaxOpen;
    }

    public void open(){
        maxOpen--;
    }

    public int getMaxOpen() {
        return maxOpen;
    }

    @Override
    public String getType() {
        return "SpecialArchive";
    }
}
