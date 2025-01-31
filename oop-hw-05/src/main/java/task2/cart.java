package task2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class cart {
    HashMap<String, Integer> idList;
    public cart(){
        idList = new HashMap<>();
    }

    public void makeItem(String item, int cnt){
        idList.put(item, cnt);
    }

    public int getCount(String item){
        return idList.get(item);
    }

    public void incItem(String item, int inc){
        if(idList.containsKey(item))
            idList.put(item, idList.get(item) + 1);
        else
            idList.put(item, 1);
    }

    public void removeItem(String item){
        idList.remove(item);
    }

    public ArrayList<String> getList(){
        ArrayList<String> items = new ArrayList<>();
        for (String item : idList.keySet()) {
            items.add(item);
        }
        return items;
    }
}
