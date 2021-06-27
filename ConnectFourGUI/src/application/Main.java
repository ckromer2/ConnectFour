//Cobey Kromer
//Connect Four GUI
//Date: 4/16/21

package application;
	

import java.awt.Label;
import java.io.File;
import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class Main extends Application {
	
	//Set up fade animation objects
	private FadeTransition f1 = new FadeTransition();
	private FadeTransition f2 = new FadeTransition();
	private FadeTransition f3 = new FadeTransition();
	private FadeTransition f4 = new FadeTransition();

	
	//Set up the Circles array that are to be in the grid pane(chips)
	private Checker[][] checkers = new Checker[6][7]; 
	
	//set up text for if there is a tie  
	private Text resultText = new Text();
	
	//set turnCount
	private int turnCount = 0; 
	
	//set winner string
	private String winnerString = ""; 
	
	//string for the media (Does Not work without internet)
    private final String path = "https://drive.google.com/uc?export=download&id=0BwF6QbkAr7fxQXRMc0Q2c1ZObGs"; 
	
    //media object to hold that path
    private Media media = new Media(path);
	    
    //object to play media
	private MediaPlayer mediaPlayer = new MediaPlayer(media);
	
	@Override
	public void start(Stage primaryStage) 
	{
		
	    //set font of tie text and the color
		resultText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		resultText.setFill(Color.BLUE);
		
		//Set up a pane to hold all of the chips of the board
		GridPane checkerHolder = new GridPane(); 
		//Align the grid in the gridpane to be in the center
		checkerHolder.setAlignment(Pos.CENTER);
		checkerHolder.setVgap(5);
		checkerHolder.setHgap(5);
		
		//set up a vbox to hold tie text and board
		VBox pane = new VBox(10);
		pane.setStyle("-fx-background-color: green;");
		//align the gridpane and text to be in the center of the vbox
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().add(resultText); 
		
		
		//Set up the Circles array that are to be in the grid pane(chips)
		//Checker[][] checkers = new Checker[6][7]; 
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 7; j++)
			{
			    checkers[i][j] = new Checker(); 
			    checkers[i][j].getProperties().put("row", i); //add properties to the checker so to know where it is placed
			    checkers[i][j].getProperties().put("col", j);
				checkerHolder.add(checkers[i][j], j, i); // because grid panes do there indexing reverse for some reason
			}
		}
		
		//put checkerholder in vbox
		pane.getChildren().add(checkerHolder);
		//Set up the scene and the stage and show the stage then play the game
		Scene scene = new Scene(pane,800,700);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
		
		//play the game
		
	}
	
	//check for tie(not in Checker class as its just a checker)
	private boolean isTie()
	{
		if(turnCount == 42 && !isWinner())
			return true; 
		return false; 
	}
	
	//reset game, for when the player decides to start again
	private void playAgain()
	{
		//reset the fades
		f1.stop(); 
		f2.stop(); 
		f3.stop(); 
		f4.stop(); 
		//reset resultText
		resultText.setText("");
		//reset turn count
		turnCount = 0; 
		//reset board
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				checkers[i][j].setFill(Color.WHITE);
				checkers[i][j].setOpacity(1);
			}
		}
		
		//reset winnerString
		winnerString = ""; 
		
		//stop the music
		mediaPlayer.stop();
		

		
			
	}
	
	
	//highlights the winning line of checkers
	private void highlightWinningLine()
	{
		boolean isWinner = false; 
		int count = 0; 
		char tempChar = ' '; //used to check if chips are matching
		char[][] board = new char[6][7]; 
		//create two arrays of coordinates, then we will find the final four and those are the coordinates of the winning checkers
		ArrayList<String> rCoords = new ArrayList<String>(); 
		ArrayList<String> cCoords = new ArrayList<String>(); 
		//take the GUI board and make it into an array of R and Y so I do not have to redo the method
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[i].length; j++)
			{
				//they are not indexed the same, checkers starts from the top left while board starts from bottom left
				if(checkers[checkers.length-1-i][j].getFill().equals(Color.RED))
					board[i][j] = 'R'; 
				if(checkers[checkers.length-1-i][j].getFill().equals(Color.YELLOW))
					board[i][j] = 'Y'; 
				if(checkers[checkers.length-1-i][j].getFill().equals(Color.WHITE))
					board[i][j] = ' '; 
			}
		}
		
		//check horizontal
		while(1 == 1)
		{
			for(int i = 0; i < board.length; i++)
			{
				for(int j = 0; j < board[i].length; j++)
				{
					if(board[i][j] == tempChar && tempChar != ' ')
						count++;
					else
						count = 0; 
					
					rCoords.add(i + ""); 
					cCoords.add(j + "");
					
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
			
			//a fake goto line so that the ArrayList keeps the winning coords
			if(isWinner)
				break; 
			
			
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
					
					rCoords.add(j + ""); 
					cCoords.add(i + ""); 
					
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
			
			//a fake goto line so that the ArrayList keeps the winning coords
			if(isWinner)
				break; 
			
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
					//add coords
					rCoords.add(i + ""); 
					cCoords.add(j + ""); 
					rCoords.add((i+1) + ""); 
					cCoords.add((j+1) + ""); 
					rCoords.add((i+2) + ""); 
					cCoords.add((j+2) + ""); 
					rCoords.add((i+3) + ""); 
					cCoords.add((j+3) + ""); 
					//break out of loop if winner
					if(isWinner)
						break; 
					if(((i-1) >= 0) && ((j+1) < 7) && (board[i-1][j+1] == tempChar) && (board[i-1][j+1] != ' '))
						if(((i-2) >= 0) && ((j+2) < 7) && (board[i-2][j+2] == tempChar) && (board[i-2][j+2] != ' '))
							if(((i-3) >= 0) && ((j+3)  < 7) && (board[i-3][j+3] == tempChar) && (board[i-3][j+3] != ' '))
								isWinner = true; 
					//add coords
					rCoords.add(i + ""); 
					cCoords.add(j + ""); 
					rCoords.add((i-1) + ""); 
					cCoords.add((j+1) + ""); 
					rCoords.add((i-2) + ""); 
					cCoords.add((j+2) + ""); 
					rCoords.add((i-3) + ""); 
					cCoords.add((j+3) + ""); 
					if(isWinner)//break out of the loop if a winner is found
						break; 
				}
				if(isWinner) //break out of loop if a win is found
					break;
			}
			//a fake goto line so that the ArrayList keeps the winning coords
			break; 
		}
		//now is when we actually highlight those areas
		int r1 = (checkers.length-1) - Integer.parseInt(rCoords.get(rCoords.size() - 4)) ; 
		int c1 = Integer.parseInt(cCoords.get(cCoords.size() - 4)); 
		int r2 = (checkers.length-1) - Integer.parseInt(rCoords.get(rCoords.size() - 3)) ; 
		int c2 = Integer.parseInt(cCoords.get(cCoords.size() - 3)); 
		int r3 = (checkers.length-1) - Integer.parseInt(rCoords.get(rCoords.size() - 2)) ; 
		int c3 = Integer.parseInt(cCoords.get(cCoords.size() - 2)); 
		int r4 = (checkers.length-1) - Integer.parseInt(rCoords.get(rCoords.size() - 1)) ; 
		int c4 = Integer.parseInt(cCoords.get(cCoords.size() - 1));
		
		//set the winning String
		if(checkers[r4][c4].getFill().equals(Color.RED))
			winnerString = "RED"; 
		else 
			winnerString = "YELLOW"; 
		
		//set the winning combo to grey
		checkers[r1][c1].setFill(Color.BLUE);
		checkers[r2][c2].setFill(Color.BLUE);
		checkers[r3][c3].setFill(Color.BLUE);
		checkers[r4][c4].setFill(Color.BLUE);
		
		//set winning combo into a fade animation
		setCheckerFade(checkers[r1][c1], f1); 
		setCheckerFade(checkers[r2][c2], f2); 
		setCheckerFade(checkers[r3][c3], f3);
		setCheckerFade(checkers[r4][c4], f4); 
	}
	
	//set a checker to fade
	private void setCheckerFade(Checker c, FadeTransition ft)
	{
		//set winning combo to fade
		ft.setDuration(Duration.millis(1000)); 
		ft.setNode(c);
		ft.setFromValue(1.0);
		ft.setToValue(0.1);
		ft.setCycleCount(Timeline.INDEFINITE);
		ft.setAutoReverse(true);
		ft.play();
	}
	//send an alert to the player after game is won
	private void sendAlert()
	{
		Alert alert = new Alert(AlertType.NONE);
		alert.setContentText("Would you like to play again?");
		alert.getButtonTypes().add(ButtonType.YES);
		alert.getButtonTypes().add(ButtonType.NO);
		alert.showAndWait().ifPresent(response ->
		{
			if(response == ButtonType.YES)
			{
				playAgain(); 
			}
			if(response == ButtonType.NO)
				System.exit(0); 
		
		}); 
				
		

	}
	
	//check for winner(not in Checker class as its just a checker)
	private boolean isWinner()
	{
		boolean isWinner = false; 
		int count = 0; 
		char tempChar = ' '; //used to check if chips are matching
		char[][] board = new char[6][7]; 
		//take the GUI board and make it into an array of R and Y so I do not have to redo the method
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[i].length; j++)
			{
				//they are not indexed the same, checkers starts from the top left while board starts from bottom left
				if(checkers[checkers.length-1-i][j].getFill().equals(Color.RED))
					board[i][j] = 'R'; 
				if(checkers[checkers.length-1-i][j].getFill().equals(Color.YELLOW))
					board[i][j] = 'Y'; 
				if(checkers[checkers.length-1-i][j].getFill().equals(Color.WHITE))
					board[i][j] = ' '; 
			}
		}
		
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
		
	//class for power checkers
	public class Checker extends Circle
	{
		public Checker()
		{
			this.setOnMouseClicked(e -> handleMouseClick());
			this.setRadius(50);
			this.setStroke(Color.BLACK); // Set circle stroke color
		    this.setFill(Color.WHITE); // set checker color
		}
		
		//how mouse clicked event is handled
		private void handleMouseClick()
		{
			//if there is no winner, this is how you handle a mouse click
			if(!isWinner() && !isTie())
			{
				//make sure that the spot is viable 
				if(spaceAvailable())
				{	
				
					//set the color and increment global var turnCount as there was a turn
					if(turnCount % 2 == 0)
					{
						this.setFill(Color.YELLOW);
						turnCount++; 
					}
					else
					{
						this.setFill(Color.RED);
						turnCount++; 
					}
				}
			}
			
			//if there is a winner, highlight those checkers
			if(isWinner())
			{
				highlightWinningLine(); 
				resultText.setText(winnerString + " HAS WON");
				mediaPlayer.play();
				sendAlert(); 

			}
			
			//if there is a tie
			if(isTie())
			{
				resultText.setText("THERE HAS BEEN A TIE");
				mediaPlayer.play();
				sendAlert(); 
			}
				
		
			
		}
		
		
		//check if space is available
		private boolean spaceAvailable()
		{
			int row = (int)this.getProperties().get("row"); 
			int col = (int)this.getProperties().get("col"); 
			
			//if space is full
			if(checkers[row][col].spaceIsFull())
				return false; 
			//if space is legal (top row) prevent out of bounds exception from being thrown
			if(row == 0)
			{
				if(checkers[row + 1][col].spaceIsFull())
					return true; 
				else
					return false; 
			}
			//if space is legal
			if(row == 5 || (!checkers[row - 1][col].spaceIsFull() && checkers[row + 1][col].spaceIsFull()))
				return true; 
			return false; 
		}
		
		//check if space is full
		private boolean spaceIsFull()
		{
			if(this.getFill().equals(Color.RED) || this.getFill().equals(Color.YELLOW))
				return true; 
			return false; 
		}

		
	}
	
	public static void main(String[] args) {
		launch(args);
		
		
	}
}
