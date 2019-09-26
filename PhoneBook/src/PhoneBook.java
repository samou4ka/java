
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class PhoneBook {

    private static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static void main(String[] args) {

        //File filePhoneBook = new File("C:\\Users\\Dmitry\\Desktop\\directory.txt");

        try  {
            String names = readFileAsString("C:\\Users\\Dmitry\\Desktop\\find.txt");
            String[] listNames = names.trim().split("\\n+");

            String directory = readFileAsString("C:\\Users\\Dmitry\\Desktop\\directory.txt");
            String[] directoryArray = directory.trim().split("\\n+");

            //Linear search
            System.out.println("Start searching (linear search)...");
            long timeStart = System.currentTimeMillis();
            int foundLinear = linearSearch(directoryArray, listNames);
            long elapseLinearTime = System.currentTimeMillis() - timeStart;
            System.out.println(String.format("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.", foundLinear, listNames.length, elapseLinearTime/60000, (elapseLinearTime%60000)/1000, (elapseLinearTime%60000)%1000));
            System.out.println();
            System.out.println();

            //Bubble Sort
            System.out.println("Start searching (bubble sort + jump search)...");
            long bubbleSortTime = bubbleSort(directoryArray, elapseLinearTime);
            if(bubbleSortTime > (elapseLinearTime*10)){
                timeStart = System.currentTimeMillis();
                foundLinear = linearSearch(directoryArray, listNames);
                elapseLinearTime = System.currentTimeMillis() - timeStart;
                System.out.println(String.format("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.", foundLinear, listNames.length, (elapseLinearTime+bubbleSortTime)/60000,
                        ((elapseLinearTime+bubbleSortTime)%60000)/1000, ((elapseLinearTime+bubbleSortTime)%60000)%1000));
                System.out.println(String.format("Sorting time: %d min. %d sec. %d ms. - STOPPED, moved to linear search", bubbleSortTime/60000, (bubbleSortTime%60000)/1000, (bubbleSortTime%60000)%1000));
                System.out.println(String.format("Searching time: %d min. %d sec. %d ms.", elapseLinearTime/60000, (elapseLinearTime%60000)/1000, (elapseLinearTime%60000)%1000));
                System.out.println();
                System.out.println();
            }
            else{
                timeStart = System.currentTimeMillis();
                int foundJump = jumpSearch(directoryArray, listNames);
                long elapseJumpTime = System.currentTimeMillis() - timeStart;
                //show messages
                System.out.println(String.format("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.", foundJump, listNames.length, (elapseJumpTime+bubbleSortTime)/60000,
                        ((elapseJumpTime+bubbleSortTime)%60000)/1000, ((elapseJumpTime+bubbleSortTime)%60000)%1000));
                System.out.println(String.format("Sorting time: %d min. %d sec. %d ms.", bubbleSortTime/60000, (bubbleSortTime%60000)/1000, (bubbleSortTime%60000)%1000));
                System.out.println(String.format("Searching time: %d min. %d sec. %d ms.", elapseJumpTime/60000, (elapseJumpTime%60000)/1000, (elapseJumpTime%60000)%1000));
                System.out.println();
                System.out.println();
            }

            //Quick sort + binarySearch
            directoryArray = directory.trim().split("\\n+");
            System.out.println("Start searching (quick sort + binary search)...");
            timeStart = System.currentTimeMillis();
            quickSort(directoryArray, 0, directoryArray.length-1);
            long quickSortTime = System.currentTimeMillis() - timeStart;

            timeStart = System.currentTimeMillis();
            int foundBinary = binary(directoryArray, listNames);
            long elapseBinaryTime = System.currentTimeMillis() - timeStart;

            //show messages
            System.out.println(String.format("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.", foundBinary, listNames.length, (elapseBinaryTime+quickSortTime)/60000,
                    ((elapseBinaryTime+quickSortTime)%60000)/1000, ((elapseBinaryTime+quickSortTime)%60000)%1000));
            System.out.println(String.format("Sorting time: %d min. %d sec. %d ms.", quickSortTime/60000, (quickSortTime%60000)/1000, (quickSortTime%60000)%1000));
            System.out.println(String.format("Searching time: %d min. %d sec. %d ms.", elapseBinaryTime/60000, (elapseBinaryTime%60000)/1000, (elapseBinaryTime%60000)%1000));
            System.out.println();
            System.out.println();

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void quickSort(String[] list, int left, int right){
        if(left < right){
            int pivotIndex = partition(list, left, right);
            quickSort(list, left, pivotIndex-1);
            quickSort(list, pivotIndex+1, right);
        }
    }

    public static int partition(String[] list, int left, int right){
        String pivot = list[right];
        int partitionIndex = left;

        for(int i = left; i < right; i++){
            String strFirst = pivot.trim().substring(pivot.indexOf(" ") + 1);
            String strSecond = list[i].trim().substring(list[i].indexOf(" ") + 1);
            if(strFirst.compareTo(strSecond) > 0){
                swap(list, i, partitionIndex);
                partitionIndex++;
            }
        }
        swap(list, partitionIndex, right);
        return partitionIndex;
    }

    public static void swap(String[] list, int i, int j){
        String temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

    public static int binarySearch(String[] dir, String elem){
        int left = 0;
        int right = dir.length-1;

            while(left <= right){
                int mid = left + (right - left)/2;
                String strFirst = dir[mid].trim().substring(dir[mid].indexOf(" ") + 1);
                if(dir[mid].contains(elem.trim())){
                    return mid;
                }
                else if(strFirst.compareTo(elem) > 0){
                    right = mid - 1;
                }
                else{
                    left = mid + 1;
                }
            }

        return -1;
    }

    public static int binary(String[] dir, String[] list){
        int found = 0;
        for(String name : list){
            if(binarySearch(dir, name) != -1){
                found++;
            }
        }
        return found;
    }

    public static long bubbleSort (String[] list, long time){

        long timeStart = System.currentTimeMillis();
        long timeStop = 0;
        String temp  = "";
        for(int i = 0; i < list.length-1; i++){
            for(int j = 0; j < list.length - i - 1; j++){
                String strFirst = list[j].trim().substring(list[j].indexOf(" ") + 1);
                String strSecond = list[j+1].trim().substring(list[j+1].indexOf(" ") + 1);
                if(strFirst.compareTo(strSecond) > 0){
                    temp = list[j];
                    list[j] = list[j+1];
                    list[j+1] = temp;
                }
            }
            timeStop = System.currentTimeMillis();
            if(timeStop - timeStart > (time*10)){
                return timeStop - timeStart;
            }
        }
        timeStop = System.currentTimeMillis();
        long elapsedTime = timeStop - timeStart;

        return elapsedTime;
    }

    public static int jumpSearch(String[] dir, String[] list){
        int jumpBlock = (int)Math.sqrt(dir.length);
        int found = 0;
        int left = 0;
        int right = 0;
        for(String string : list) {
            while (right < dir.length - 1) {
                right = Math.max(dir.length - 1, right + jumpBlock);

                if (dir[right].substring(dir[right].indexOf(" ") + 1).compareTo(string) > 0){
                    break;
                }
                left = right;
            }

            for(int i = right; i > left; i--){
                if(dir[i].contains(string)){
                    found++;
                    break;
                }
            }

        }
        return found;
    }

    public static int linearSearch(String[] dir, String[] list){
        int found = 0;

        for(String name : list) {
            for(int i = 0; i < dir.length; i++){
                if(dir[i].trim().contains(name.trim())){
                    found++;
                    break;
                }
            }
        }
        return found;
    }
}

