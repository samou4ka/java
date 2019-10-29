package analyzer;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        if(args.length < 2){
            System.out.println("Number of arguments must be more");
            System.exit(0);
        }
        String path = args[0];
        String patternPath = args[1];
        //String path = "C:\\Users\\Dmitry\\Desktop\\new";
        //String patternPath = "C:\\Users\\Dmitry\\Desktop\\patterns.db";

        /*read every string from file patterns*/
        List<String> dbList = new ArrayList<>();
        File patternFile = new File(patternPath);
        try {
            Scanner scanner = new Scanner(patternFile);
            while(scanner.hasNextLine()) {
                dbList.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.exit(0);
        }

        /*compare patterns with file content*/
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<String>> listFuture = new ArrayList<>();

        for (File file : listOfFiles) {
            if (file.isFile()) {

                Callable<String> callable = () -> {
                    FindContext context = new FindContext();
                    context.setStrategy(new KMPStrategy());
                    String fitOfPattern = "";
                    int maxPriority = 0;

                    for (String line : dbList) {
                        int priority = Integer.parseInt(line.trim().split(";")[0]);

                            String tmp = line.trim().split(";")[1];
                            final String pattern = tmp.substring(1, tmp.length() - 1);

                            tmp = line.trim().split(";")[2];
                            final String result = tmp.substring(1, tmp.length() - 1);

                            String fileType = context.find(file.getPath(), pattern, result);
                            if (!"".equals(fileType)) {
                                if(priority > maxPriority) {
                                    maxPriority = priority;
                                    fitOfPattern = file.getName() + ": " + fileType;
                                }
                            }

                    }

                    return "".equals(fitOfPattern) ? file.getName() + ": Unknown file type" : fitOfPattern;
                };
                listFuture.add(executor.submit(callable));

            }
        }

        for(Future future : listFuture){
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException ex){
                ex.printStackTrace();
            }
        }

        executor.shutdown();
    }


}
