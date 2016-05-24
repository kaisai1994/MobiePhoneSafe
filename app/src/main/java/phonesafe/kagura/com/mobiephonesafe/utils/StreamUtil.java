package phonesafe.kagura.com.mobiephonesafe.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

/**
 * @author Administrator
 * @time 2016/5/8 21:17
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class StreamUtil {
    /**
     * 将流信息转化成字符串
     * @return
     * @throws IOException
     */
    public static String parserStreamUtil(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringWriter sw = new StringWriter();
        String str = null;
        while((str = br.readLine()) != null)
        {
             sw.write(str);
        }
        sw.close();
        br.close();
        return sw.toString();
    }
}
