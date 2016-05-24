package phonesafe.kagura.com.mobiephonesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @version $Rev$
 * @auther kagura
 * @time 2016/5/14.10:55
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateData $Author$
 * @updatedes ${TODO}
 */
public class MD5Util {
    public static String passwordMD5(String password)
    {
        StringBuilder sb = new StringBuilder();

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(password.getBytes());
            for (int i = 0; i < digest.length; i++) {
                int result = digest[i] & 0xff;
                String hexString = Integer.toHexString(result);
                if(hexString.length() < 2)
                {
                    sb.append("0");
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
       return null;
    }
}
