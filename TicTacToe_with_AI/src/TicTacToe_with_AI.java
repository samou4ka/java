import java.util.*;

class Player{
    String name;
}
class Move{
    int index;
    int score;
}

public class TicTacToe_with_AI {
    public static char aiPlayer;
    public static char huPlayer;

    public static int fc = 0; ////keeps count of function calls

    public static boolean wins(String s, char ch){
        if( s.charAt(1) == ch && s.charAt(2) == ch && s.charAt(3)==ch ||
                s.charAt(4) == ch && s.charAt(5) == ch && s.charAt(6)==ch ||
                s.charAt(7) == ch && s.charAt(8) == ch && s.charAt(9)==ch ||
                s.charAt(1) == ch && s.charAt(4) == ch && s.charAt(7)==ch ||
                s.charAt(2) == ch && s.charAt(5) == ch && s.charAt(8)==ch ||
                s.charAt(3) == ch && s.charAt(6) == ch && s.charAt(9)==ch ||
                s.charAt(1) == ch && s.charAt(5) == ch && s.charAt(9)==ch ||
                s.charAt(3) == ch && s.charAt(5) == ch && s.charAt(7)==ch)
        {
            return true;
        }
        return false;
    }

    public static boolean impossible(String s){
        boolean state = false;
        int cntX = 0;
        int cntO = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == 'X'){cntX++;}
            if(s.charAt(i) == 'O'){cntO++;}
        }

        if(wins(s, 'X') && wins(s, 'O')){state = true;}
        if(cntO > cntX+1 || cntX > cntO+1){state = true;}
        return state;
    }

    public static boolean draw(String s){
        boolean state = false;
        int cntX = 0;
        int cntO = 0;
        int cntSpace = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == 'X'){cntX++;}
            if(s.charAt(i) == 'O'){cntO++;}
            if(s.charAt(i) == ' '){cntSpace++;}
        }
        if(cntX+cntO == 9) {
            state = true;
        }

        return state;
    }

    public static boolean gameNotFinished(String s){
        boolean state = false;
        int cntX = 0;
        int cntO = 0;
        int cntSpace = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == 'X'){cntX++;}
            if(s.charAt(i) == 'O'){cntO++;}
            if(s.charAt(i) == ' '){cntSpace++;}
        }
        if(cntX+cntO < 9){
            state = true;
        }
        return state;
    }

    public static void printField(String s)
    {
        if(s.length() == 11){
            System.out.println("---------");
            System.out.println("| " +s.charAt(1) + " " + s.charAt(2) + " " + s.charAt(3) + " |");
            System.out.println("| " +s.charAt(4) + " " + s.charAt(5) + " " + s.charAt(6) + " |");
            System.out.println("| " +s.charAt(7) + " " + s.charAt(8) + " " + s.charAt(9) + " |");
            System.out.println("---------");
        }
    }

    public static boolean errorUser(String s, String x, String y){
        boolean err = false;
        if(Character.isDigit(x.charAt(0)) == false || Character.isDigit(y.charAt(0)) == false){
            System.out.println("You should enter numbers!");
            err = true;
        }
        else if(Integer.parseInt(x) < 1 || Integer.parseInt(y) < 1 || Integer.parseInt(x) > 3 || Integer.parseInt(y) > 3){
            System.out.println("Coordinates should be from 1 to 3!");
            err = true;
        }
        else if(s.charAt(9 - Integer.parseInt(y)*3 + Integer.parseInt(x)) != ' '){
            System.out.println("This cell is occupied! Choose another one!");
            err = true;
        }
        return err;
    }

    public static String userMove(String s, Scanner scanner, char symbol){
        System.out.print("Enter the coordinates: ");
        int X;
        int Y;
        while(true){
            String strX = scanner.next();
            String strY = scanner.next();
            if(!errorUser(s, strX, strY)){
                X = Integer.parseInt(strX);
                Y = Integer.parseInt(strY);
                break;}
        }
        char[] chars = s.toCharArray();
        chars[9 - Y*3 + X] = symbol;
        s = String.valueOf(chars);
        return s;
    }
    public static int easy(String s){
        int move;
        Random random = new Random();
        while (true) {
            move = random.nextInt(9) + 1;
            if (s.charAt(move) == ' ') {
                return move;
            }
        }
    }

    public static int nextMedium(String s, char symbol){
        int move = 0;

        //rows
        for(int i = 0; i < 3; i++){
            if(s.charAt(3*i+1)==symbol && s.charAt(3*i+2) == symbol && s.charAt(3*i+3) == ' '){
                move = 3*i+3;
            }
            else if(s.charAt(3*i+1)==symbol && s.charAt(3*i+2) == ' ' && s.charAt(3*i+3) == symbol){
                move = 3*i+2;
            }
            else if(s.charAt(3*i+1)==' ' && s.charAt(3*i+2) == symbol && s.charAt(3*i+3) == symbol){
                move = 3*i+1;
            }
        }
        //colums
        for(int i = 0; i < 3; i++){
            if(s.charAt(i+1)==symbol && s.charAt(i+4) == symbol && s.charAt(i+7) == ' '){
                move = i+7;
            }
            else if(s.charAt(i+1)==symbol && s.charAt(i+4) == ' ' && s.charAt(i+7) == symbol){
                move = i+4;
            }
            else if(s.charAt(i+1)==' ' && s.charAt(i+4) == symbol && s.charAt(i+7) == symbol){
                move = i+1;
            }
        }
        //diagonals
        for(int i = 1; i < 3; i++){
            if(s.charAt(5-2*i)==symbol && s.charAt(5) == symbol && s.charAt(5+2*i) == ' '){
                move = 5+2*i;
            }
            else if(s.charAt(5-2*i)==symbol && s.charAt(5) == ' ' && s.charAt(5+2*i) == symbol){
                move = 5;
            }
            else if(s.charAt(5-2*i)==' ' && s.charAt(5) == symbol && s.charAt(5+2*i) == symbol){
                move = 5-2*i;
            }
        }

        return move;
    }

    public static int medium(String s, char symbol){
        int move = 0;
        char antiSymbol = 'X';
        if (symbol == 'X') {
            antiSymbol = 'O';
        }

        if((move = nextMedium(s, symbol)) == 0){//we can end game in one move
            if((move = nextMedium(s, antiSymbol)) == 0){//opponents can end game in one move
                move = easy(s);//easy move
            }
        }

        return move;
    }

    public static int hard(String s, char symbol){
        char antiSymbol = 'X';
        if (symbol == 'X') {
            antiSymbol = 'O';
        }

        aiPlayer = symbol;
        huPlayer = antiSymbol;
        Move move = minimax(s, symbol);

        return move.index;
    }


    public static String computerMove(String s, String level, char symbol){
        System.out.println(String.format("Making move level \"%s\"", level));
        //easy level
        int move = 0;
        if(level.equals("easy")) {
            move = easy(s);
        }
        //medium level
        if(level.equals("medium")){
            move = medium(s, symbol);
        }
        if(level.equals("hard")){
            move = hard(s, symbol);
        }

        char[] charsComp = s.toCharArray();
        charsComp[move] = symbol;
        s = String.valueOf(charsComp);
        return s;
    }

    public static boolean analyzeMoving(String s){
        if (impossible(s)) {
            System.out.println("Impossible");
        } else if (wins(s, 'X')) {
            System.out.println("X wins");
            return true;
        } else if (wins(s, 'O')) {
            System.out.println("O wins");
            return true;
        } else if (draw(s)) {
            System.out.println("Draw");
            return true;
        }
        /*else{
                System.out.println("Game not finished");
            }*/
        return false;
    }

    public static boolean menu(Player player1, Player player2, Scanner scanner){
        while (true) {
            System.out.print("Input command: ");
            String[] cmd = scanner.nextLine().trim().split(" ");

            switch (cmd[0]) {
                case "exit":
                    return true;
                case "start":
                    if (cmd.length == 3) {
                        if (cmd[1].equals("user")) {
                            player1.name = "user";
                        }
                        else if (cmd[1].equals("easy")) {
                            player1.name = "easy";
                        }
                        else if(cmd[1].equals("medium")){
                            player1.name = "medium";
                        }
                        else if(cmd[1].equals("hard")){
                            player1.name = "hard";
                        }

                        if (cmd[2].equals("user")) {
                            player2.name = "user";
                        }
                        else if (cmd[2].equals("easy")) {
                            player2.name = "easy";
                        }
                        else if(cmd[2].equals("medium")){
                            player2.name = "medium";
                        }
                        else if(cmd[2].equals("hard")){
                            player2.name = "hard";
                        }

                        return false;
                    } else {
                        System.out.println("Bad parameters!");
                    }
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Player player1 = new Player();
        Player player2 = new Player();

        while(true) {
            //---------------MENU---------------------
            if (menu(player1, player2, scanner)) {
                break;
            }

            //------------GAME------------------
            String str = "\"         \"";
            printField(str);

            while (true) {
                //player1 moving
                if(player1.name == "user"){
                    //User move
                    str = userMove(str, scanner, 'X');
                }
                else{
                    //Computers move level easy
                    str = computerMove(str, player1.name, 'X');
                }
                printField(str);
                //Analyze moving
                if(analyzeMoving(str)){
                    break;
                }

                //player2 moving
                if(player2.name == "user"){
                    //User move
                    str = userMove(str, scanner, 'O');
                }
                else{
                    //Computers move level easy
                    str = computerMove(str, player2.name, 'O');
                }
                printField(str);
                //Analyze moving
                if(analyzeMoving(str)){
                    break;
                }
            }
        }
    }

    // the main minimax function
    public static Move minimax(String newBoard, char player){
        //add one to function calls
        fc++;

        //available spots
        String availSpots = emptyIndexies(newBoard);

        Move tempMove = new Move();
        tempMove.index = 0;
        // checks for the terminal states such as win, lose, and tie and returning a value accordingly
        if (wins(newBoard, huPlayer)){
            tempMove.score = -10;
            return tempMove;
        }
        else if (wins(newBoard, aiPlayer)){
            tempMove.score = 10;
            return tempMove;
        }
        else if (availSpots.length() == 0){
            tempMove.score = 0;
            return tempMove;
        }

        // an array to collect all the objects
        ArrayList<Move> moves = new ArrayList<>();

        // loop through available spots
        for (int i = 0; i < availSpots.length(); i++){
            //create an object for each and store the index of that spot that was stored as a number in the object's index key
            Move move = new Move();

            move.index = Character.getNumericValue(availSpots.charAt(i));

            // set the empty spot to the current player
            char[] myNameChars = newBoard.toCharArray();
            myNameChars[Character.getNumericValue(availSpots.charAt(i))] = player;
            newBoard = String.valueOf(myNameChars);

            //if collect the score resulted from calling minimax on the opponent of the current player
            if (player == aiPlayer){
                Move result = minimax(newBoard, huPlayer);
                move.score = result.score;
            }
            else{
                Move result = minimax(newBoard, aiPlayer);
                move.score = result.score;
            }

            //reset the spot to empty
            char[] NameChars = newBoard.toCharArray();
            NameChars[Character.getNumericValue(availSpots.charAt(i))] = ' ';
            newBoard = String.valueOf(NameChars);

            // push the object to the array

            moves.add(move);
        }


        // if it is the computer's turn loop over the moves and choose the move with the highest score
        int bestMove=0;
        if(player == aiPlayer){
            int bestScore = -10000;
            for(int i = 0; i < moves.size(); i++){
                if(moves.get(i).score > bestScore){
                    bestScore = moves.get(i).score;
                    bestMove = i;
                }
            }
        }else{
            // else loop over the moves and choose the move with the lowest score
            int bestScore = 10000;
            for(int i = 0; i < moves.size(); i++){
                if(moves.get(i).score < bestScore){
                    bestScore = moves.get(i).score;
                    bestMove = i;
                }
            }
        }

        // return the chosen move (object) from the array to the higher depth
        return moves.get(bestMove);
    }

    // returns the available spots on the board in string (Example "1459")
    public static String  emptyIndexies(String s){
        String indexes = "";
        for(int i = 1; i < 10; i++){
            if(s.charAt(i) == ' '){
                indexes += Integer.toString(i);
            }
        }
        return  indexes;
    }
}

