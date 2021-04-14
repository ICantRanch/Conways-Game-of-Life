
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class GOLMain extends Application {
	public int boardHeight = 10;
	public int boardWidth = 20;
	
	
	int WIN_WIDTH = 700;
	int WIN_HEIGHT = 700;
	GraphicsContext gc;
	Canvas c;
	Timer t;
	TimerTask tt;
	
	int borderSize = 1;;
	int cellSize = 10;
	int iterSize = borderSize + cellSize;
	
	GOLBoard board;
	
	
	public void start(Stage stage) throws Exception {
		
		FileChooser fc = new FileChooser();
		
		board = new GOLBoard(fc.showOpenDialog(stage));
		
		
		boardHeight = board.getBoardHeight();
		boardWidth = board.getBoardWidth();
		
		System.out.printf("Board Height: %d, Board Width: %d\n",boardHeight, boardWidth);
		
		WIN_HEIGHT = (iterSize)*boardHeight + borderSize;
		WIN_WIDTH =	(iterSize)*boardWidth + borderSize;	
		
		board.calcNext();
		
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		c = new Canvas(WIN_WIDTH,WIN_HEIGHT);
		gc = c.getGraphicsContext2D();
		root.setCenter(c);
		
		HBox hb = new HBox();
		hb.setSpacing(5);
		hb.setPadding(new Insets(5,5,5,5));
		root.setTop(hb);
		hb.setAlignment(Pos.CENTER);
		
		ToggleButton tb = new ToggleButton("Step-By-Step");
		
		myTimer();
		
		tb.setOnAction(e -> {
			if(tb.isSelected()) {
				System.out.println("Step-By-Step Engaged");
				t.cancel();
			} else {
				System.out.println("Step-By-Step Disengaged");
				update();
			}
		});
		
		hb.getChildren().addAll(tb);
		
		stage.setWidth(c.getWidth() + hb.getWidth() + 20);
		stage.setHeight(c.getHeight() + hb.getHeight() + 100);
		stage.setTitle("Conway's Game of Life");
		stage.setScene(scene);
		stage.show();
		
		//EventHandlers for Step-by-Step
		
		EventHandler<MouseEvent> eh1 = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent me) {
					if(tb.isSelected()) {
						
						render();
						
					}
					
					}
				};
				
			root.addEventHandler(MouseEvent.MOUSE_CLICKED, eh1);
			
		EventHandler<KeyEvent> eh = new EventHandler<KeyEvent>() {
				
				public void handle(KeyEvent ke) {
					if(tb.isSelected()) {	
						render();	
						System.out.println("Step");
					}
				}
			};
			root.addEventHandler(KeyEvent.KEY_PRESSED, eh);		
		
		//******************************************	
			
		stage.setOnCloseRequest(event ->{
			t.cancel();
		});
		
		
		
	}
	
	public void myTimer() {
        TimerTask tt = new TimerTask() {

            @Override
            public void run() {
                System.out.println("Life of a MAC");
                update();
            }
        };
        t = new Timer();
        t.schedule(tt, 0, 60);

    }
    public void update() {
        TimerTask tt = new TimerTask() {

            @Override
            public void run() {
                //System.out.println("Updated timer");
                render();
            }
        };
        t.cancel();
        t = new Timer();
        t.schedule(tt, 0, 90);
    }
	
    
    
	
	public void render() {
		
		gc.setFill(Color.GRAY);
		gc.fillRect(0,0,c.getWidth(),c.getHeight());
		
		int xPos = borderSize, yPos = borderSize;
		
		
		for(int i = 0; i < boardHeight; i++) {
			
			for(int o = 0;o < boardWidth; o++) {
				
				if(board.getState(i,o) == 1) {
					gc.setFill(Color.BLACK);
				}else {
					gc.setFill(Color.WHITE);
				}
				
				gc.fillRect(xPos, yPos, cellSize, cellSize);
				
				xPos += iterSize;
			}
			xPos = borderSize;
			yPos += iterSize;
		}
		
		board.nextState();	

	}
	
	
	
	public static void main(String[] args) {
		
		launch(args);

	}	
}
