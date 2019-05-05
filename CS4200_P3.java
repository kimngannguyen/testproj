import java.util.ArrayList;
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

	public static void main(String args[]) 
	{
		isoBoard = createBoard();
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

	public static boolean isValidMove (String potentialMove) 
	{
		System.out.println("Used square: " + usedSquares);

		for (String path : usedSquares)
		{
			// if you're trying to move to a square on the same horizontal as a #
			if (path.charAt(0) == potentialMove.charAt(0)) 
			{
				//WORKS
				// check to see if you're jumping west over a #
				System.out.println("path:" + path.charAt(0));
				if (potentialMove.charAt(1) - currentComputerPosition.charAt(1) < 0)
				{
					// potential is less than curr
					int curr=Integer.parseInt(String.valueOf(currentComputerPosition.charAt(1))); 
					int potential=Integer.parseInt(String.valueOf(potentialMove.charAt(1))); 
					int row = getNumericValue(path.charAt(0)); 
					for(int i = potential; i <= curr; i++) {
						if (isoBoard[row][i] == '#')
							return false;
					}

				} else if (potentialMove.charAt(1) - currentComputerPosition.charAt(1) > 0){ 
					// check to see if you're jumping east over a #

					//potential is greater than curr
					int curr=Integer.parseInt(String.valueOf(currentComputerPosition.charAt(1))); 
					int potential=Integer.parseInt(String.valueOf(potentialMove.charAt(1))); 
					int row = getNumericValue(path.charAt(0)); 
					for(int i = curr; i <= potential; i++) {
						if (isoBoard[row][i] == '#')
							return false;
					}
				}

			}

			// if you're trying to move to a square in the same vertical as a #
			// if (path.charAt(1) == potentialMove.charAt(1)) 
			// {
			// 	if (potentialMove.charAt(0) - currentComputerPosition.charAt(0) < 0)
			// 	{
			// 		// check to see if you're jumping north over a #
			// 		if ((potentialMove.charAt(0) < currentComputerPosition.charAt(0) && potentialMove.charAt(0) < path.charAt(0)) || (potentialMove.charAt(0) < currentOpponentPosition.charAt(0) && potentialMove.charAt(0) < path.charAt(0))) 
			// 		{
			// 			return false;
			// 		}
			// 	}
			// 	// check to see if you're jumping south over a #
			// 	else if ((potentialMove.charAt(0) > currentComputerPosition.charAt(0) && potentialMove.charAt(0) > path.charAt(0)) || (potentialMove.charAt(0) > currentOpponentPosition.charAt(0) && potentialMove.charAt(0) > path.charAt(0))) 
			// 	{
			// 		return false;
			// 	}
			// }
		}

		return true;
	}

	public static void inputOpponent() 
	{
		opponentTurn = true;
		System.out.println("\nEnter opponent's move: ");
		sc = new Scanner(System.in);
		String oMove = sc.nextLine();

		while (searchInArray(oMove)) 
		{
			System.out.println("Error, that move has already been made. Choose again.");
			System.out.println("\nEnter opponent's move: ");
			sc = new Scanner(System.in);
			oMove = sc.nextLine();
		}

		while (isValidMove(oMove) == false) 
		{
			System.out.println("Error, that is not a valid Queen move. Try again.");
			System.out.println(usedSquares);
			System.out.println("Error, that move has already been made. Choose again.");
			System.out.println("\nEnter opponent's move: ");
			sc = new Scanner(System.in);
			oMove = sc.nextLine();
		}

		opponentMoves.add(oMove);
		currentOpponentPosition = oMove;
		updatePrevBoard(opponentMoves.get(opponentMoves.size()-2).charAt(0), opponentMoves.get(opponentMoves.size()-2).charAt(1));
		updateCurrentOpponentBoard(oMove.charAt(0),oMove.charAt(1));
		printBoard();
	}

	public static void inputComputer() 
	{
		computerTurn = true;
		System.out.println("\nEnter computer's move: ");
		sc = new Scanner(System.in);
		String cMove = sc.next();
		boolean valid = isValidMove(cMove);
		
		while (searchInArray(cMove)) 
		{
			System.out.println("Error, that move has already been made. Choose again.");
			inputComputer();
		}

		while (valid == false) 
		{
			System.out.println("Error, that is not a valid Queen move. Try again.");
			System.out.println(usedSquares);
			inputComputer();
		}

		computerMoves.add(cMove);
		currentComputerPosition = cMove;
		updatePrevBoard(computerMoves.get(computerMoves.size()-2).charAt(0), computerMoves.get(computerMoves.size()-2).charAt(1));
		updateCurrentComputerBoard(cMove.charAt(0),cMove.charAt(1));
		printBoard();
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



}