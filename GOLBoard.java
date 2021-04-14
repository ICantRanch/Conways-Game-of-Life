import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class GOLBoard {

	public int[][] board;
	
	public int[][] nextBoard;

	Random rng = new Random();
	
	public GOLBoard(int BOARD_HEIGHT,int BOARD_WIDTH) {
		
		board = new int[BOARD_HEIGHT][BOARD_WIDTH];
		
		for(int i = 0;i < board.length;i++) {
			
			for(int o = 0;o < board[0].length;o++) {
				
				board[i][o] = rng.nextInt(2);
			}
		}
	}
	
	public GOLBoard(String filePath) {
		
		try {

            String content = Files.readString(Paths.get(filePath));
            
            StringtoBoard(content);
              
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public GOLBoard(File file) {

		try {
			String content = Files.readString(file.toPath());

			StringtoBoard(content);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void StringtoBoard(String content) {
		
		String[] strArr = content.split("\n");
		board = new int[strArr.length][strArr[0].length()-1];

		for(int i = 0;i < board.length; i++) {
			for(int o = 0;o < board[0].length; o++) {
				board[i][o] = Character.digit(strArr[i].charAt(o), 10);
			}
		}
	}
	
	public void calcNext() {
		
		nextBoard = new int[board.length][board[0].length];
		
		int liveNeighbors;
		
		for(int i = 0;i < nextBoard.length;i++) {
			
			for(int o = 0;o < nextBoard[0].length;o++) {
				
				liveNeighbors = 0;
				
				for(int ni = i-1;ni <= i+1;ni++) {
					
					for(int no = o-1;no <= o+1;no++) {
						
						if(no < 0 || no >= board[0].length )continue;
						if(ni < 0 || ni >= board.length )break;
						
						if(board[ni][no] == 1) {
							if(ni != i || no != o) {	
								liveNeighbors++;
							}	
						}	
					}
				}
				
				
				if(board[i][o] == 1) {
					
					if(liveNeighbors <= 1) {
						nextBoard[i][o] = 0;
					}else if(liveNeighbors == 2 || liveNeighbors == 3) {
						nextBoard[i][o] = 1;
					}else{
						nextBoard[i][o] = 0;
					}
				}else if(liveNeighbors == 3){
					nextBoard[i][o] = 1;		
				}
			}	
		}
	}
	
	public void nextState() {
		
		board = nextBoard;
		calcNext();
		
	}
	
	public int getState(int x, int y) {
		
		
		return board[x][y];
		
	}
	
	public void printArr() {
		
		for(int i = 0;i < board.length;i++) {
			
			System.out.print("|");
			
			for(int o = 0;o < board[0].length;o++) {
				
				if(board[i][o] == 1) {
					System.out.print("#");
				}else {
					System.out.print(" ");
				}
			}
			System.out.print("|\n");
		}
		System.out.print("\n\n");
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public int[][] getNextBoard() {
		return nextBoard;
	}

	public void setNextBoard(int[][] nextBoard) {
		this.nextBoard = nextBoard;
	}
	
	public int getBoardHeight() {
		return board.length;
	}
	public int getBoardWidth() {
		return board[0].length;
	}
}
