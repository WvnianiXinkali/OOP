package task1;

import java.util.HashMap;

public class AccountManager {
    private HashMap<String, String> map;
    public AccountManager(){
        map = new HashMap<String, String>();
        map.put("Patrick","1234");
        map.put("Molly","FloPup");
    }

    public void addToMap(String mail, String pass){
        map.put(mail, pass);
    }

    public boolean containsMap(String mail) {
        return map.containsKey(mail);
    }
    public boolean checkPass(String mail, String pass){
        return map.get(mail).equals(pass);
    }
}
