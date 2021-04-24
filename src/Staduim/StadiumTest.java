package Staduim;
import java.util.*;

class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}

class Sector{
    String code; //koj sektor e
    int capacity; //sedista vo toj sektor
    boolean[] taken;
    int type;
    int count;

    public Sector(String code, int capacity) {
        this.code = code;
        this.capacity = capacity;
        this.taken = new boolean[capacity];
        for(int i=0; i<capacity; i++){
            taken[i] = false;
        }
        this.type = 0;
        this.count = 0;
    }

    public String getCode() {
        return code;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getType() {
        return type;
    }

    public boolean[] getTaken() {
        return taken;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isTaken(int index){
        return taken[index];
    }

    public void takeSeat(int index){
        taken[index] = true;
        count++;
    }

    public int freeSeats(){
        return capacity - count;
    }

    @Override
    public String toString() {
        return String.format("%s\t%d/%d\t%.1f%%",code,capacity-count,capacity,((double)count/capacity)*100);
    }
}

class Stadium{
    String name;
    Map<String,Sector> sectors;

    public Stadium(String name) {
        this.name = name;
        this.sectors = new HashMap<>();
    }

    public void createSectors(String[] sectorNames, int[] sizes){
        for(int i=0; i<sizes.length; i++){
            sectors.put(sectorNames[i],new Sector(sectorNames[i],sizes[i]));
        }
    }

    public void buyTicket(String sectorName, int seat, int type)throws SeatTakenException,SeatNotAllowedException{
        Sector sector = sectors.get(sectorName);
        if(sector.getType() == 0){ //proveruvam dali postoi takov tip vo sekotorot
            sector.setType(type);
        }

        if(sector.isTaken(seat-1))
                throw new SeatTakenException("The seat "+seat+" is taken");

        if((sector.getType()==1 && type == 2)||(sector.getType() == 2 && type == 1))
            throw new SeatNotAllowedException("");

        sector.takeSeat(seat-1);
    }

    public void showSectors(){
        sectors.values()
                .stream().sorted(Comparator.comparing(Sector::freeSeats).reversed().thenComparing(Sector::getCode))
                .forEach(System.out::println);
    }
}

class SeatTakenException extends Throwable{
    public SeatTakenException(String message) {
        super(message);
    }
}

class SeatNotAllowedException extends Throwable{
    public SeatNotAllowedException(String message) {
        super(message);
    }
}
