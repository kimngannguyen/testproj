import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class CS4200_P3 
{
    public static char[][] isoBoard;
    public static ArrayList<String> computerMoves = new ArrayList<>();
    public static ArrayList<String> opponentMoves = new ArrayList<>();
    public static ArrayList<String> usedSquares = new ArrayList<>();
    public static boolean computerTurn;
    public static boolean opponentTurn;
    public static Scanner sc;
    public static int moveCounter = 1;
    public static String currentComputerPosition = "";
    public static String currentOpponentPosition = "";
    public static List<place> emptyCells;
    public static void main(String args[]) 
    {
        isoBoard = createBoard();
        //System.out.print(alphaBeta(isoBoard));
        
        System.out.println("\nIsolation: Begin Game\n Computer: X\n Opponent: O");
        printBoard();
        sc = new Scanner(System.in);
        System.out.println("Who goes first? Type C for computer or O for opponent: ");
        char firstPlayer = sc.next().charAt(0);
        if (firstPlayer == 'C') 
        {
            inputComputer();
        }
        else 
        {
            inputOpponent();
        }

        while (true) // while players and computer can still move
        {
            if (computerTurn) 
            {
                inputOpponent();
                computerTurn = false;

            }

            if (opponentTurn)
            {
                inputComputer();
                opponentTurn = false;
            }

            System.out.println("Computer vs. Opponent \n " + moveCounter + ". " + computerMoves.get(computerMoves.size()-1) + "     " + opponentMoves.get(opponentMoves.size()-1) + "\n");
            moveCounter++;
        }
        
    }

    public static boolean isValidComputerMove (String potentialMove) 
    {
        for (String path : usedSquares)
        {

            // COMPUTER HORIZONTAL
            if (path.charAt(0) == potentialMove.charAt(0)) 
            {
                // check to see if you're jumping west over a #
                if (potentialMove.charAt(1) - currentComputerPosition.charAt(1) < 0)
                {
                    int curr=Integer.parseInt(String.valueOf(currentComputerPosition.charAt(1))); 
                    int potential=Integer.parseInt(String.valueOf(potentialMove.charAt(1))); 
                    int row = getNumericValue(path.charAt(0)); 
                    for(int i = potential; i < curr; i++) {
                        if (isoBoard[row][i] != '-') {
                            System.out.println("Computer west not valid");
                            return false;
                        }
                    }
                // check to see if you're jumping east over a #
                } else if (potentialMove.charAt(1) - currentComputerPosition.charAt(1) > 0){ 
                    int curr=Integer.parseInt(String.valueOf(currentComputerPosition.charAt(1))); 
                    int potential=Integer.parseInt(String.valueOf(potentialMove.charAt(1))); 
                    int row = getNumericValue(path.charAt(0)); 
                    for(int i = curr; i < potential; i++) {
                        if (isoBoard[row][i] != '-') {
                            System.out.println("Computer east not valid");
                            return false;
                        }
                    }
                    
                }
            }

            // COMPUTER VERTICAL
            if (path.charAt(1) == potentialMove.charAt(1)) 
            {
                // check to see if you're jumping north over a #
                if (potentialMove.charAt(0) - currentComputerPosition.charAt(0) < 0)
                {
                    int curr = getNumericValue(currentComputerPosition.charAt(0)); 
                    int potential = getNumericValue(potentialMove.charAt(0)); 
                    int col = Integer.parseInt(String.valueOf(currentComputerPosition.charAt(1))); 
                    for(int i = potential; i < curr; i++) {
                        if (isoBoard[i][col] != '-') {
                            System.out.println("Computer north not valid");
                            return false;
                        }
                    }
                }
                // check to see if you're jumping south over a #
                if (potentialMove.charAt(0) - currentComputerPosition.charAt(0) > 0)
                {
                    int curr = getNumericValue(currentComputerPosition.charAt(0)); 
                    int potential = getNumericValue(potentialMove.charAt(0)); 
                    int col = Integer.parseInt(String.valueOf(currentComputerPosition.charAt(1))); 
                    for(int i = curr; i < potential; i++) {
                        if (isoBoard[i][col] != '-') {
                            System.out.println("Computer south not valid");
                            return false;
                        }
                    }
                    
                }
            }
        }
        return true;
    }

    public static boolean isValidOpponentMove (String potentialMove) 
    {
        for (String path : usedSquares)
        {
            // OPPONENT HORIZONTAL 
            if (path.charAt(0) == potentialMove.charAt(0)) 
            {
                // check to see if you're jumping west over a #
                if (potentialMove.charAt(1) - currentOpponentPosition.charAt(1) < 0)
                {
                    int curr=Integer.parseInt(String.valueOf(currentOpponentPosition.charAt(1))); 
                    int potential=Integer.parseInt(String.valueOf(potentialMove.charAt(1))); 
                    int row = getNumericValue(path.charAt(0)); 
                    for(int i = potential; i < curr; i++) {
                        if (isoBoard[row][i] != '-') {
                            System.out.println("Opponent west not valid");
                            return false;
                        }
                    }

                } else if (potentialMove.charAt(1) - currentOpponentPosition.charAt(1) > 0){ 
                    // check to see if you're jumping east over a #
                    int curr=Integer.parseInt(String.valueOf(currentOpponentPosition.charAt(1))); 
                    int potential=Integer.parseInt(String.valueOf(potentialMove.charAt(1))); 
                    int row = getNumericValue(path.charAt(0)); 
                    for(int i = curr; i < potential; i++) {
                        if (isoBoard[row][i] != '-') {
                            System.out.println("Opponent east not valid");
                            return false;
                        }
                    }
                    
                }
            }

            // OPPONENT VERTICAL
            if (path.charAt(1) == potentialMove.charAt(1)) 
            {
                // check to see if you're jumping north over a #
                if (potentialMove.charAt(0) - currentOpponentPosition.charAt(0) < 0)
                {
                    int curr = getNumericValue(currentOpponentPosition.charAt(0)); 
                    int potential = getNumericValue(potentialMove.charAt(0)); 
                    int col = Integer.parseInt(String.valueOf(currentOpponentPosition.charAt(1))); 
                    for(int i = potential; i < curr; i++) {
                        if (isoBoard[i][col] != '-') {
                            System.out.println("Opponent north not valid");
                            return false;
                        }
                    }
                    
                }
                // check to see if you're jumping south over a #
                if (potentialMove.charAt(0) - currentOpponentPosition.charAt(0) > 0)
                {
                    int curr = getNumericValue(currentOpponentPosition.charAt(0)); 
                    int potential = getNumericValue(potentialMove.charAt(0)); 
                    int col = Integer.parseInt(String.valueOf(currentOpponentPosition.charAt(1))); 
                    for(int i = curr; i < potential; i++) {
                        if (isoBoard[i][col] != '-') {
                            System.out.println("Opponent south not valid");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public static void inputOpponent() 
    {
        opponentTurn = true;
        System.out.println("\nEnter opponent's move: ");
        sc = new Scanner(System.in);
        String oMove = sc.nextLine();
        boolean valid = isValidOpponentMove(oMove);

        while (searchInArray(oMove)) 
        {
            System.out.println("\nError, that move has already been made. Choose again.");
            inputOpponent();
        }

        if (valid == false) 
        {
            System.out.println("\nError, that is not a valid Queen move. Try again.");
            //System.out.println(usedSquares);
            inputOpponent();
        } else {
            opponentMoves.add(oMove);
            currentOpponentPosition = oMove;
            updatePrevBoard(opponentMoves.get(opponentMoves.size()-2).charAt(0), opponentMoves.get(opponentMoves.size()-2).charAt(1));
            updateCurrentOpponentBoard(currentOpponentPosition.charAt(0),currentOpponentPosition.charAt(1));
            printBoard();
        }

        
    }

    public static void inputComputer() 
    {
        computerTurn = true;
        System.out.println("\nEnter computer's move: ");
        sc = new Scanner(System.in);
        String cMove = sc.next();
        boolean valid = isValidComputerMove(cMove);
        
        while (searchInArray(cMove)) 
        {
            System.out.println("\nError, that move has already been made. Choose again.");
            inputComputer();
        }

        if (valid == false) 
        {
            System.out.println("\nError, that is not a valid Queen move. Try again.");
            //System.out.println(usedSquares);
            inputComputer();
        } else {
            computerMoves.add(cMove);
            currentComputerPosition = cMove;
            updatePrevBoard(computerMoves.get(computerMoves.size()-2).charAt(0), computerMoves.get(computerMoves.size()-2).charAt(1));
            updateCurrentComputerBoard(currentComputerPosition.charAt(0),currentComputerPosition.charAt(1));
            printBoard();
        }

        
    }

    public static char[][] createBoard() 
    {
        computerMoves.add("A1");
        opponentMoves.add("H8");

        char[][] board = {{' ', '1','2','3','4','5','6','7','8'}, 
                          {'A','X','-','-','-','-','-','-','-'},
                          {'B','-','-','-','-','-','-','-','-'},
                          {'C','-','-','-','-','-','-','-','-'},
                          {'D','-','-','-','-','-','-','-','-'},
                          {'E','-','-','-','-','-','-','-','-'},
                          {'F','-','-','-','-','-','-','-','-'},
                          {'G','-','-','-','-','-','-','-','-'},
                          {'H','-','-','-','-','-','-','-','O'}};
        return board;
    }

    public static void printBoard() 
    {
        System.out.println();
        for (int i = 0; i < isoBoard.length; i++) { 
            for (int j = 0; j < isoBoard[i].length; j++) { 
                System.out.print(isoBoard[i][j] + " "); 
            } 
            System.out.println(); 
        } 
        System.out.println();
    }

    public static boolean searchInArray(String move) 
    {
        if (computerMoves.contains(move) || opponentMoves.contains(move)) {
            return true;
        }

        return false;
    }

    public static void updatePrevBoard(char row, char col)
    {
        switch(row) 
        {
            case 'A':
                isoBoard[1][Character.getNumericValue(col)] = '#';
                usedSquares.add("A" + Character.toString(col));
                break;
            case 'B':
                isoBoard[2][Character.getNumericValue(col)] = '#';
                usedSquares.add("B" + Character.toString(col));
                break;
            case 'C':
                isoBoard[3][Character.getNumericValue(col)] = '#';
                usedSquares.add("C" + Character.toString(col));
                break;
            case 'D':
                isoBoard[4][Character.getNumericValue(col)] = '#';
                usedSquares.add("D" + Character.toString(col));
                break;
            case 'E':
                isoBoard[5][Character.getNumericValue(col)] = '#';
                usedSquares.add("E" + Character.toString(col));
                break;
            case 'F':
                isoBoard[6][Character.getNumericValue(col)] = '#';
                usedSquares.add("F" + Character.toString(col));
                break;
            case 'G':
                isoBoard[7][Character.getNumericValue(col)] = '#';
                usedSquares.add("G" + Character.toString(col));
                break;
            case 'H':
                isoBoard[8][Character.getNumericValue(col)] = '#';
                usedSquares.add("H" + Character.toString(col));
                break;
        }
    }

    public static void updateCurrentComputerBoard(char row, char col)
    {
        switch(row) 
        {
            case 'A':
                isoBoard[1][Character.getNumericValue(col)] = 'X';
                break;
            case 'B':
                isoBoard[2][Character.getNumericValue(col)] = 'X';
                break;
            case 'C':
                isoBoard[3][Character.getNumericValue(col)] = 'X';
                break;
            case 'D':
                isoBoard[4][Character.getNumericValue(col)] = 'X';
                break;
            case 'E':
                isoBoard[5][Character.getNumericValue(col)] = 'X';
                break;
            case 'F':
                isoBoard[6][Character.getNumericValue(col)] = 'X';
                break;
            case 'G':
                isoBoard[7][Character.getNumericValue(col)] = 'X';
                break;
            case 'H':
                isoBoard[8][Character.getNumericValue(col)] = 'X';
                break;
        }
    }

    public static void updateCurrentOpponentBoard(char row, char col)
    {
        switch(row) 
        {
            case 'A':
                isoBoard[1][Character.getNumericValue(col)] = 'O';
                break;
            case 'B':
                isoBoard[2][Character.getNumericValue(col)] = 'O';
                break;
            case 'C':
                isoBoard[3][Character.getNumericValue(col)] = 'O';
                break;
            case 'D':
                isoBoard[4][Character.getNumericValue(col)] = 'O';
                break;
            case 'E':
                isoBoard[5][Character.getNumericValue(col)] = 'O';
                break;
            case 'F':
                isoBoard[6][Character.getNumericValue(col)] = 'O';
                break;
            case 'G':
                isoBoard[7][Character.getNumericValue(col)] = 'O';
                break;
            case 'H':
                isoBoard[8][Character.getNumericValue(col)] = 'O';
                break;
        }
    }

    public static int getNumericValue(char row)
    {
        switch(row) 
        {
            case 'A':
                return 1;
            case 'B':
                return 2;
            case 'C':
                return 3;
            case 'D':
                return 4;
            case 'E':
                return 5;
            case 'F':
                return 6;
            case 'G':
                return 7;
            case 'H':
                return 8;
            default:
                return 0;
        }
    }

       
    // function MinMax-Decision() return an action
    // v is the Max value in state
    // function to check if this stage X Won
    // function to check if this stage C won
    //Min Nodes - computer move , X
    //Max nodes - player move, O

    public static int checkWinner(char[][] isoBoard) 
    { 
        for (int row = 0; row<7; row++) 
        { 
            if (isoBoard[row][0]==isoBoard[row][1] &&
                isoBoard[row][1]==isoBoard[row][2] &&
                isoBoard[row][2]==isoBoard[row][3] &&
                isoBoard[row][3]==isoBoard[row][4] &&
                isoBoard[row][4]==isoBoard[row][5] &&
                isoBoard[row][5]==isoBoard[row][6] &&
                isoBoard[row][6]==isoBoard[row][7] )
            { 
                if (isoBoard[row][0]=='X') 
                   return +1; 
                else if (isoBoard[row][0]=='O') 
                   return -1; 
            } 
        } 
        
        for (int col = 0; col<7; col++) 
        { 
            if (isoBoard[0][col]==isoBoard[1][col] && 
                isoBoard[1][col]==isoBoard[2][col] &&
                isoBoard[2][col]==isoBoard[3][col] &&
                isoBoard[3][col]==isoBoard[4][col] &&
                isoBoard[4][col]==isoBoard[5][col] &&
                isoBoard[5][col]==isoBoard[6][col] &&
                isoBoard[6][col]==isoBoard[7][col] ) 
            { 
                if (isoBoard[0][col]=='X') 
                    return +1; 
                else if (isoBoard[0][col]=='O') 
                    return -1; 
            } 
        } 
        
        int check=0, dcheck = 0;
        for(int i=0; i < 7 ; i++){
            if (isoBoard[0][0]==isoBoard[i][i])
            { 
                 check++;
            } 
        }
            if (isoBoard[0][7]==isoBoard[1][6] && 
                isoBoard[0][7]==isoBoard[2][5] &&
                isoBoard[0][7]==isoBoard[3][4] &&
                isoBoard[0][7]==isoBoard[4][3] &&
                isoBoard[0][7]==isoBoard[5][2] &&
                isoBoard[0][7]==isoBoard[6][1] &&
                isoBoard[0][7]==isoBoard[7][0] )
            { 
                dcheck = 7;
              }
         
        
        if ((check==7 && isoBoard[0][0]=='X')|| isoBoard[0][7] == 'X' && dcheck == 7) 
            return +1; 
        else if (check == 7 && isoBoard[0][0]=='O' || isoBoard[0][7] == 'O' && dcheck == 7) 
            return -1;
        
        return 0; 
    }

    
    
    
   
    
    public List<place> emptyCell(char[][] isoBoard){
        emptyCells = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            for( int j = 0;j<7;j++){
                if(isoBoard[i][j] == '-')
                    emptyCells.add(new place(i,j));
            }
        }
        return emptyCells;
    }
    
    
    // ALpha Beta Prunning
    public static int alphaBeta(char[][] state){
       int action =0;
       int negaInfi = Integer.MIN_VALUE;
       int posiInfi = Integer.MAX_VALUE;
       int v = Max_Value(state,negaInfi,posiInfi);
       return action; // Sucessors(state) with value v
    }
    
    public static int Max_Value (char[][] isoBoard, int alpha, int beta) {
        try{
            if(emptyCells.isEmpty()) 
                 return Utility(isoBoard);
        }catch(Exception e){
        }
         int v = Integer.MIN_VALUE; // negative infinity
         for (int a = 0; a < successors(isoBoard); a++){
             v = Math.max(v, Min_Value(isoBoard,alpha,beta));
             if(v >= beta)  return v;
             beta = Math.max(beta, v)  ;             
         }
         return v;
    }
    
    // function Min_value(state,alpha, beta) return Utility
    // if termina_test(state) return utillity;
     public static int Min_Value (char[][] isoBoard, int alpha, int beta) {
         
         if(emptyCells.isEmpty())
             return Utility(isoBoard);
         int v = Integer.MAX_VALUE; // positive infinity
         for (int a = 0; a < successors(isoBoard); a++){
             v = Math.min(v, Max_Value(isoBoard,alpha,beta));
             if(v <= alpha) return v;
             beta = Math.min(beta, v)   ;            
         }
         return v;
     }
     
     public static int Utility(char[][] isoBoard){
         int u = 0;
         u = checkWinner(isoBoard);
         return u;
     }
     
     public static int successors(char[][] isoBoard){
         // QUEEN MOVE 
         //list
         return 0;
     }
}

class place{
    int row, col;
    public place(int row, int col) {
        this.row = row;
        this.col = col;
    }
}




