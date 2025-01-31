package Quiz.src.main.java.HelperMethods;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PassHasher {
    public static String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i=0; i<bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;  // remove higher bits, sign
            if (val<16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }
    public static String hashPassword(String pass){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            byte[] hashed = md.digest(pass.getBytes());
            pass = hexToString(hashed);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(pass);
        return pass;
    }
}
