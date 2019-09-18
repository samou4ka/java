import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

interface Searching{
    Set<Integer> find(HashMap<String, Set<Integer>> map, String query);
}

class SearchingContext{
    private Searching strategy;

    SearchingContext(Searching strategy){
        this.strategy = strategy;
    }

    Set<Integer> find(HashMap<String, Set<Integer>> map, String query){
        return this.strategy.find(map, query);
    }
}

class AllSearching implements Searching{

    public Set<Integer> find(HashMap<String, Set<Integer>> map, String query)
    {
        Set<Integer> set = new TreeSet<>();
        Map<Integer, Integer> storageMap = new TreeMap<>();

        String[] str = query.trim().split("\\s+");

        for(String string : str){
            if(map.containsKey(string)){
                for(Integer i : map.get(string)){
                    if(storageMap.containsKey(i)){
                        storageMap.put(i, storageMap.get(i) + 1);
                    }
                    else{
                        storageMap.put(i, 1);
                    }
                }
            }
        }

        for(Map.Entry<Integer,Integer> pair : storageMap.entrySet()){
            if(pair.getValue() == str.length){
                set.add(pair.getKey());
            }
        }
        return set;
    }

}

class AnySearching implements Searching{
    public Set<Integer> find(HashMap<String, Set<Integer>> map, String query)
    {
        Set<Integer> set = new TreeSet<>();
        String[] str = query.trim().split("\\s+");
        for(String string : str){
            if(map.containsKey(string)){
                for(Integer i : map.get(string)){
                    set.add(i);
                }
            }
        }
        return set;
    }
}

class NoneSearching implements Searching{
    public Set<Integer> find(HashMap<String, Set<Integer>> map, String query)
    {
        Set<Integer> set = new TreeSet<>();

        String[] str = query.trim().split("\\s+");
        for (Map.Entry<String, Set<Integer>> pair : map.entrySet()) {
            for(Integer i : pair.getValue()){
                set.add(i);
            }
        }


        for(String string : str){
            if(map.containsKey(string)){
                for(Integer i : map.get(string)){
                    set.remove(i);
                }
            }
        }
        return set;
    }
}

public class SimpleSearchEngine {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = "";
        //read from file to txt array
        try {
            str = readFileAsString("C:\\Users\\Dmitry\\Desktop\\search.txt");
        }
        catch(Exception e){
            System.out.println("Something went wrong");
        }
        String[] txt = str.trim().split("\n");
        HashMap<String, Set<Integer>> map = getMap(txt);

        while(true){
            //Menu
            int choiceMenu = menu(scanner);
            System.out.println();
            if(choiceMenu == 1){
                System.out.println("Select a matching strategy: ALL, ANY, NONE");

                String strategy = scanner.nextLine();
                System.out.println();

                System.out.println("Enter a name or email to search all suitable people.");
                String query = scanner.nextLine().toLowerCase();

                try {
                    SearchingContext finder = null;
                    switch(strategy){
                        case "ALL":
                            finder = new SearchingContext(new AllSearching());
                            break;
                        case "ANY":
                            finder = new SearchingContext(new AnySearching());
                            break;
                        case "NONE":
                            finder = new SearchingContext(new NoneSearching());
                            break;
                        default:
                            System.out.println("No such strategy");
                            break;
                    }

                    Set<Integer> set = finder.find(map, query);
                    System.out.println();
                    if(set.size() > 0){
                        System.out.println(set.size() + " people found:");
                        for(Integer i : set){
                            System.out.println(txt[i]);
                        }
                        System.out.println();
                    }
                    else {
                        System.out.println("No matching people found");
                    }

                }
                catch(NullPointerException e){
                    System.out.println("No matching people found");
                    //System.out.println();
                }

            }
            else if(choiceMenu == 2){
                System.out.println("=== List of people ===");
                for(int i = 0; i < txt.length; i++){
                    System.out.println(txt[i]);
                }
                System.out.println();
            }
            else{
                System.out.println("Bye!");
                break;
            }
        }
    }

    public static String readFileAsString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static HashMap<String, Set<Integer>>  getMap(String[] txt){
        HashMap<String, Set<Integer>> map = new HashMap<>();

        for(int i = 0; i < txt.length; i++){
            String[] str = txt[i].trim().split("\\s+");
            for(String s : str){
                if(map.containsKey(s.toLowerCase())){
                    Set<Integer> set = map.get(s.toLowerCase());
                    set.add(i);
                    map.put(s.toLowerCase(), set);
                }
                else{
                    Set<Integer> set = new TreeSet<>();
                    set.add(i);
                    map.put(s.toLowerCase(), set);
                }
            }
        }
        return map;
    }

    public static int menu(Scanner scanner){
        while(true){
            System.out.println("=== Menu ===");
            System.out.println("1. Find a person\n2. Print all people\n0. Exit");
            String choice = scanner.nextLine();

            switch(choice){
                case "1":
                    return 1;
                case "2":
                    return 2;
                case "0":
                    return 0;
                default:
                    System.out.println();
                    System.out.println("Incorrect option! Try again.");
                    System.out.println();
                    break;
            }
        }
    }

}