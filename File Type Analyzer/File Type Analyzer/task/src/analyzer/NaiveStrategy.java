package analyzer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class NaiveStrategy implements Strategy{
    @Override
    public String find(String path, String pattern, String result) {
        try(
                InputStream iS = new FileInputStream(path)
        ){
            byte[] buffer = new byte[4096];
            while (iS.read(buffer) != -1) {
                String string = new String(buffer);
                if (string.contains(pattern)) {
                    return result;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "Unknown file type";
    }
}

