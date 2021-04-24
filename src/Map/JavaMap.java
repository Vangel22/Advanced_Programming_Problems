package Map;

import java.util.HashMap;
import java.util.Map;

public class JavaMap {
    public static void main(String[] args) {

        Map<String,String> fruits = new HashMap<>();

        fruits.put("red","apple");
        fruits.put("yellow","banana");
        fruits.put("white","radish");
        fruits.put("green","apple");

        System.out.println(fruits.get("red"));

        for(Map.Entry pairEntry : fruits.entrySet())
            System.out.println(pairEntry.getKey() + " : " + pairEntry.getValue());

        fruits.containsKey("red"); //returns true
        fruits.containsValue("apple"); //returns true
        fruits.size(); //returns size of the MAP
        fruits.remove("red"); //deletes the entry whose key is "red"
        fruits.clear(); //deletes every entry
    }
}

/*Map: Interface
* HashMap: class that implements interface Map
*
* Map properties:
* 1.They contain values based on key
* 2.They are not ordered
* 3."KEY" should be unique
* 4."VALUE" can be duplicate
* */