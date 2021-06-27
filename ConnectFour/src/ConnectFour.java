/*
 Writer: Cobey Kromer
 Date: 2-11-21
 Program: Connect Four(Text Based)
 */
import java.util.Scanner; 
public class ConnectFour
{
	public static void main(String[] args) {
		boolean continuePlay = true; 
		Scanner scnr = new Scanner(System.in); 
	
		while(continuePlay)
		{
			char currentColor = ' '; // color of current players chip
			int turnCount = 0; 
			char[][] board = new char[6][7]; 
			int column = 0;  //what column the player is dropping there disk
			boolean validMove = true; 
			
			// initialize board to have all spaces(clear board)
			for(int i = board.length - 1; i >= 0; i--) 
			{
				for(int j = 0; j <board[i].length; j++)
					board[i][j] = ' '; 
			}
			
			while(1 == 1)
			{
				
				//increment turn count
				turnCount++; 
				
				//print the board
				printBoard(board); 
				
				//check for a tie
				if(turnCount == 43)
				{
					System.out.println("I declare a draw");
					break; 
				}
				
				//yellow player turn
				if(turnCount % 2 == 1)
				{
					currentColor = 'Y'; 
					
					//check if move is valid then place disk
					do
					{
						column = scnr.nextInt(); 
						if(column > 6 || column < 0 || columnIsFull(board, column))
						{	
							validMove = false;
							System.out.println("YOU ENTERED AN INVALID INTEGER OR THE COLUMN IS FULL."); 
						}
						else
							validMove = true; 
					}
					while(!validMove);
					
					board = dropDisk(board, column, currentColor); 
					
				}
				
				//red player turn
				if(turnCount % 2 == 0)
				{
					currentColor = 'R'; 
					
					//check if move is valid then place disk
					do
					{
						column = scnr.nextInt(); 
						if(column > 6 || column < 0 || columnIsFull(board, column))
						{	
							validMove = false;
							System.out.println("YOU ENTERED AN INVALID INTEGER OR THE COLUMN IS FULL."); 
						}
						else
							validMove = true; 
					}
					while(!validMove);
					board = dropDisk(board, column, currentColor); 
					
				}
				
				//check for win, if won print board and exit
				if(isWinner(board))
				{
					printBoard(board); 
					System.out.println(currentColor + " has WON the game!"); 
					break; 
				}
				
			}
			//ask if they want to play again
			//to decide whether to continue play;  
			boolean keepGoing; 
			System.out.println("DO YOU WANT TO PLAY A NEW GAME? (type 1 for yes)"); 
			if(scnr.nextInt() == 1)
				keepGoing = true;
			else
				keepGoing = false; 
			 
			continuePlay = keepGoing;   
		}

	}

	 
	
	private static void printBoard(char[][] board) 
	{
		for(int i = board.length - 1; i >= 0; i--)
		{
			System.out.print("| ");
			for(int j = 0; j <board[i].length; j++)
			{
				System.out.print(board[i][j] + " | ");
			}
			System.out.println();//new line
			boolean even = true;
			
		}
		
	}
	
	private static char[][] dropDisk(char[][] board, int column, char color)
	{
		char[][] retBoard = board; 
		for(int i = 0; i < board.length; i++)
		{
			if((board[i][column] != 'R') && (board[i][column] != 'Y'))
			{
				retBoard[i][column] = color; 
				break; 
			}
			
		}
		return retBoard; 
	}

	private static boolean isWinner(char[][] board)
	{
		boolean isWinner = false; 
		int count = 0; 
		char tempChar = ' '; //used to check if chips are matching
		//check horizontal
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[i].length; j++)
			{
				if(board[i][j] == tempChar && tempChar != ' ')
					count++;
				else
					count = 0; 
				
				tempChar = board[i][j]; 
				if(count == 3)
				{
					isWinner = true; 
					break; 
				}
			}
			count = 0; // reset count for next row
			tempChar = ' '; //reset tempChar
			if(isWinner)
				break; 
		}
		
		
		//check vertical
		tempChar = ' '; //reset tempChar
		for(int i = 0; i < 7; i++)
		{
			for(int j = 0; j < board.length; j++)
			{
				if(board[j][i] == tempChar && tempChar != ' ')
					count++;
				else
					count = 0; 
				
				tempChar = board[j][i]; 
				if(count == 3)
				{
					isWinner = true; 
					break; 
				}
			}
			count = 0; // reset count for next column
			tempChar = ' '; //reset tempChar
			if(isWinner) //break out of loop if a win is found
				break; 
		}
		
		
		//check diagonal
		tempChar = ' '; //reset tempChar
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[i].length; j++)
			{
				tempChar = board[i][j]; 
				if(((i+1) < 6) && ((j+1) < 7) && (board[i+1][j+1] == tempChar) && (board[i+1][j+1] != ' '))
					if(((i+2) < 6) && ((j+2) < 7) && (board[i+2][j+2] == tempChar) && (board[i+2][j+2] != ' '))
						if(((i+3) < 6) && ((j+3) < 7) && (board[i+3][j+3] == tempChar) && (board[i+3][j+3] != ' '))
							isWinner = true; 
				if(((i-1) >= 0) && ((j+1) < 7) && (board[i-1][j+1] == tempChar) && (board[i-1][j+1] != ' '))
					if(((i-2) >= 0) && ((j+2) < 7) && (board[i-2][j+2] == tempChar) && (board[i-2][j+2] != ' '))
						if(((i-3) >= 0) && ((j+3)  < 7) && (board[i-3][j+3] == tempChar) && (board[i-3][j+3] != ' '))
							isWinner = true; 
			}
			if(isWinner) //break out of loop if a win is found
				break;
		}
		return isWinner; 
        
	}
	
	private static boolean columnIsFull(char[][] board, int col) 
	{
		boolean isFull = false; 
		if(board[5][col] == 'Y' || board[5][col] == 'R')
			isFull = true; 
		return isFull; 
	}
	
	//method unused due to it not working with the autograder so I coded the procedure straight into main
	private static boolean keepGoing()
	{
		Scanner scnr = new Scanner(System.in); 
		boolean keepGoing; 
		System.out.println("DO YOU WANT TO PLAY A NEW GAME? (type 1 for yes)"); 
		if(scnr.nextInt() == 1)
			keepGoing = true;
		else
			keepGoing = false; 
		return keepGoing; 
	}
	

	
}


