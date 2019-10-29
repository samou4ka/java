package analyzer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class KMPStrategy implements Strategy{
    @Override
    public String find(String path, String pattern, String result){
        try(
                InputStream iS = new FileInputStream(path)
        ){
            byte[] buffer = new byte[4096];
            while (iS.read(buffer) != -1) {
                String string = new String(buffer);
                if (searchKMP(string, pattern)) {
                    return result;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private boolean searchKMP(String text, String pattern) {
        int[] prefixFunc = prefixFunction(pattern);
        int j = 0;
        for (int i = 0; i < text.length(); i++) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = prefixFunc[j - 1];
            }
            if (text.charAt(i) == pattern.charAt(j)) {
                j += 1;
            }
            if (j == pattern.length()) {
                return true;
            }
        }
        return false;
    }

    private static int[] prefixFunction(String str) {
        int[] prefixFunc = new int[str.length()];

        for (int i = 1; i < str.length(); i++) {
            int j = prefixFunc[i - 1];

            while (j > 0 && str.charAt(i) != str.charAt(j)) {
                j = prefixFunc[j - 1];
            }

            if (str.charAt(i) == str.charAt(j)) {
                j += 1;
            }
            prefixFunc[i] = j;
        }
        return prefixFunc;
    }
}


