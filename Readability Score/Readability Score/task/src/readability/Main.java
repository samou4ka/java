package readability;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*if(args.length < 1) {
            System.out.println("Add parameter");
            System.exit(0);
        }*/

        //String path = "C:\\Users\\Dmitry\\Desktop\\new\\in.txt";//args[0];
        String path = args[0];
        String text = "";
        try {
            text = readAllFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] sentences = text.trim().split("(!|\\?|\\.)");
        String[] words = text.split("\\s+");

        double cntSentences = sentences.length;
        double cntWords = words.length;
        double cntCharacters = countCharacters(words);
        double cntSyllables = countSyllables(words);
        double cntPolysyllables = countPolysyllables(words);

        printData(text, cntSentences, cntWords, cntCharacters, cntSyllables, cntPolysyllables);

        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        String choice = new Scanner(System.in).nextLine();
        System.out.println();

        ContextIndex readabilityIndex = new ContextIndex();
        double age = 0;

        if("ARI".equals(choice) || "all".equals(choice)) {
            readabilityIndex.setStrategy(new ARIndex());
            age += readabilityIndex.calculateIndex(cntSentences, cntWords, cntCharacters);
        }

        if("FK".equals(choice) || "all".equals(choice)) {
            readabilityIndex.setStrategy(new FKIndex());
            age += readabilityIndex.calculateIndex(cntSentences, cntWords, cntSyllables);
        }

        if("SMOG".equals(choice) || "all".equals(choice)) {
            readabilityIndex.setStrategy(new SMOGIndex());
            age += readabilityIndex.calculateIndex(cntSentences, cntPolysyllables);
        }

        if("CL".equals(choice) || "all".equals(choice)) {
            readabilityIndex.setStrategy(new CLIndex());
            age += readabilityIndex.calculateIndex(cntSentences, cntWords, cntCharacters);
        }
        if("all".equals(choice)) {
            System.out.println();
            System.out.println(String.format("This text should be understood in average by %.2f year olds.", age/4));
        }
    }

    public static double countCharacters(String[] words) {
        double cntCharacters = 0;
        for(String word : words) {
            cntCharacters += word.trim().length();
        }
        return cntCharacters;
    }

    public static double countSyllables(String[] words) {
        double cntSyllables = 0;
        boolean prevConsonant = true;
        for(String word : words) {
            prevConsonant = true;
            int currentWord = 0;
            for(int i = 0; i < word.length(); i++) {
                if(     word.charAt(i) == 'a' ||
                        word.charAt(i) == 'e' ||
                        word.charAt(i) == 'i' ||
                        word.charAt(i) == 'o' ||
                        word.charAt(i) == 'u' ||
                        word.charAt(i) == 'y') {
                    if(prevConsonant) {
                        currentWord += 1;
                    }
                    prevConsonant = false;
                } else {
                    prevConsonant = true;
                }
            }
            if(word.charAt(word.length()-1) == 'e') {
                    currentWord -= 1;
            }
            if(currentWord == 0) {
                currentWord += 1;
            }
            cntSyllables += currentWord;
        }
        return cntSyllables;
    }

    public static double countPolysyllables(String[] words) {
        double cntPoly = 0;
        boolean prevConsonant = true;
        int cntSyllables = 0;
        for(String word : words) {
            for(int i = 0; i < word.length(); i++) {
                if(     word.charAt(i) == 'a' ||
                        word.charAt(i) == 'e' ||
                        word.charAt(i) == 'i' ||
                        word.charAt(i) == 'o' ||
                        word.charAt(i) == 'u' ||
                        word.charAt(i) == 'y') {
                    if(prevConsonant) {
                        cntSyllables += 1;
                    }
                    prevConsonant = false;
                } else {
                    prevConsonant = true;
                }
            }
            if(word.charAt(word.length()-1) == 'e') {
                cntSyllables -= 1;
            }

            if(cntSyllables > 2) {
                cntPoly+=1;
            }
            cntSyllables = 0;
        }
        return cntPoly;
    }

    public static String readAllFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    private static void printData(String text,
                                  double sentences,
                                  double words,
                                  double characters,
                                  double syllables,
                                  double polysyllables) {
        System.out.println("The text is: ");
        System.out.println(text);
        System.out.println();
        System.out.println("Words: " + (int)words);
        System.out.println("Sentences: " + (int)sentences);
        System.out.println("Characters: " + (int)characters);
        System.out.println("Syllables: " + (int)syllables);
        System.out.println("Polysyllables: " + (int)polysyllables);
    }

}
