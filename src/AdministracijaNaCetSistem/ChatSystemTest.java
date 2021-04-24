package AdministracijaNaCetSistem;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ChatSystemTest {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt();
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr.addUser(jin.next());
                if ( k == 1 ) cr.removeUser(jin.next());
                if ( k == 2 ) System.out.println(cr.hasUser(jin.next()));
            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if ( n == 0 ) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr2.addUser(jin.next());
                if ( k == 1 ) cr2.removeUser(jin.next());
                if ( k == 2 ) cr2.hasUser(jin.next());
            }
            System.out.println(cr2.toString());
        }
        if ( k == 1 ) {
            ChatSystem cs = new ChatSystem();
            Method mts[] = cs.getClass().getMethods();
            while ( true ) {
                String cmd = jin.next();
                if ( cmd.equals("stop") ) break;
                if ( cmd.equals("print") ) {
                    System.out.println(cs.getRoom(jin.next())+"\n");continue;
                }
                for ( Method m : mts ) {
                    if ( m.getName().equals(cmd) ) {
                        String params[] = new String[m.getParameterTypes().length];
                        for ( int i = 0 ; i < params.length ; ++i ) params[i] = jin.next();
                        m.invoke(cs,params);
                    }
                }
            }
        }
    }

}

class ChatRoom{

    String roomName;
    Set<String> users;

    public ChatRoom(String name) {
        roomName = name;
        users = new TreeSet<>(); //podredeno
    }

    public void addUser(String username){
        users.add(username);
    }

    public void removeUser(String username){
        users.remove(username);
    }

    public boolean hasUser(String username){
        return users.contains(username);
    }

    public int numberOfUsers(){
        return users.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(roomName).append("\n");
        if(users.isEmpty()){
            sb.append("EMPTY");
        }else{
            sb.append(String.join("\n", users));
        }
        sb.append("\n");
        return sb.toString();
    }
}

class ChatSystem{

    Map<String,ChatRoom> rooms;
    TreeSet<String> users;

    public ChatSystem() {
        rooms = new TreeMap<>();
        users = new TreeSet<>();
    }

    public void addRoom(String roomName){
        rooms.put(roomName,new ChatRoom(roomName));
    }

    public void removeRoom(String roomName){
        rooms.remove(roomName);
    }

    public ChatRoom getRoom(String roomName) throws NoSuchRoomException{
        if(rooms.containsKey(roomName))
            return rooms.get(roomName);
        else
            throw new NoSuchRoomException(roomName);

    }

    public void register(String userName) throws NoSuchRoomException, NoSuchUserException {
        users.add(userName);
        if (!rooms.isEmpty()) {
            String min = rooms.values().stream()
                    .min(Comparator.comparing(ChatRoom::numberOfUsers).thenComparing(room -> room.roomName))
                    .get().roomName;
            //min mi ja vrakja sobata sto mi e pogodna so baranjeto sto e dadeno
            registerAndJoin(userName, min);
            //soba so najmalku korisnici i leksikografski podredeno ako ima nekoja minimalna so isto korisnici
        }

    }

    public void registerAndJoin(String username,String roomName) throws NoSuchRoomException,NoSuchUserException{
        users.add(username); //sekogas imame kontrola koj korisnik e, se cuva vo Set
        joinRoom(username,roomName);
    }

    public void joinRoom(String username,String roomName) throws NoSuchRoomException,NoSuchUserException{
        if(!rooms.containsKey(roomName)){
            throw new NoSuchRoomException(roomName);
        }
        if(!users.contains(username)){
            throw new NoSuchUserException(username);
        }
        rooms.get(roomName).addUser(username);
    }

    public void leaveRoom(String username,String roomName) throws NoSuchRoomException,NoSuchUserException{
        if(!rooms.containsKey(roomName)){
            throw new NoSuchRoomException(roomName);
        }
        if(!users.contains(username)){
            throw new NoSuchUserException(username);
        }
        rooms.get(roomName).removeUser(username);
    }

    public void followFriend(String username,String friend_username) throws  NoSuchUserException{
        if(!users.contains(friend_username)){
            throw new NoSuchUserException(friend_username);
        }

        rooms.values()
                .stream()
                .filter(room ->room.hasUser(friend_username))
                .forEach(room -> room.addUser(username));
    }

}

class NoSuchRoomException extends Exception{
    public NoSuchRoomException(String roomName) { }
}
class NoSuchUserException extends Exception{
    public NoSuchUserException(String username) { }
}

