/**
 * Snowman.java
 * Program 7: make a snowman
 * 
 * @author Yong Deng
 * @since  3/25/2015
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.geometry.*;

public class Snowman extends Application {
	Pane pane = new Pane();
	Scene scene;
	Stage st;
	Circle circle;	//Circles used to make snowman
	
	Text message = new Text(10, 20, "Please choose a color:\n\tY = Yellow green\n\tB = lightBlue\n\tS = Silver");
	
	//background colors----------------------------------------------------------------
	Color yellowGreen   = new Color(0.6, 0.8, 0.2, 1);
	Color lightBlue     = new Color(0.2, 0.6, 0.9, 1);
	Color silver        = Color.SILVER;
	Background bkYellow = new Background(new BackgroundFill(yellowGreen, null, new Insets(2)));
	Background bkBlue   = new Background(new BackgroundFill(lightBlue, null, new Insets(2)));
	Background bkSilver = new Background(new BackgroundFill(silver, null, new Insets(2)));

	private double    topRadius, middleRadius, bottomRadius;	//radius of the three circle making the snowman body
	private double    topY, middleY, bottomY;					//Y-axis position of the center of each circle
	private double    centerX = 0;								//X-axis position of the center of each circle
	private double    x, y;										//coordinates of mouse click
	double 			  startX, startY, endX, endY;				//coordinates used to make arm
	public static int clickCount = 1;							//counter for mouse clicks
	
	@Override
	//===================================================================================
	//Override start function
	//===================================================================================
	public void start(Stage st) throws Exception {
		this.st = st;
		buildGui();
		scene.setOnKeyPressed(e -> setBackground(e));	//change background using keyboard
		pane.setOnMouseClicked(e -> makeSnowman(e));	//make snow man using mouse
		makeArms();
		st.show();
	}
	
	//===================================================================================
	//function to set background
	//Users can change the color of the background by pressing corresponding keys
	//===================================================================================
	public void setBackground (KeyEvent ke) {
		KeyCode code = ke.getCode();
		switch(code) {
			case Y:
				pane.setBackground(bkYellow);
				System.out.println("Background color has been changed to yellow green.");
				break;
			case B:
				pane.setBackground(bkBlue);
				System.out.println("Background color has been changed to light blue.");
				break;
			case S:
				pane.setBackground(bkSilver);
				System.out.println("Background color has been changed to silver.");
				break;
			default:
				System.out.println("We don't process this key.");
		}
		message.setText("Click top of the window to create the head");
	}
	
	//===================================================================================
	//Build initial GUI
	//===================================================================================
	public void buildGui() {
		message.setFont(Font.font("Arial", FontWeight.BOLD, null, 20));
		pane.getChildren().add(message);
		scene = new Scene(pane, 500, 600);
		st.setTitle("Snow Man");
		st.setScene(scene);
	}
	
	//===================================================================================
	//Make snowman
	//===================================================================================
	public void makeSnowman(MouseEvent me) {
        x = me.getX();
        y = me.getY();
        
        switch (clickCount) {
        	case 1: 	//making head
        		centerX = x;
        		topY = y;
        		topRadius = 50;
        		circle = body(centerX, topY, topRadius);
        		message.setText("Click a point below the head to make body");
        		System.out.println("Making head.");
        		break;
        	case 2: 	//making body
        		middleY = y;
        		middleRadius = middleY- topY - topRadius;
        		circle = body(centerX, middleY, middleRadius);
        		message.setText("Click a point below the head to make base");
        		System.out.println("Making body.");
        		break;
        	case 3: 	//making base
        		bottomY = y;
        		bottomRadius = bottomY - middleY - middleRadius;
        		circle = body(centerX, bottomY, bottomRadius);
        		message.setText("Click where to make eyes");
        		System.out.println("Making base.");
        		break;
        	case 4:		//making eyes
        		circle = eye(x, y);
        		message.setText("Click where to make eyes");
        		System.out.println("Making eyes.");
        		break;
        	case 5:
        		circle = eye(x, y);
        		message.setText("Click five times where to make mouth");
        		System.out.println("Making eyes.");
        		break;
        	case 6:		//making mouth
        	case 7:
        	case 8:
        	case 9:
        		circle = mouth(x, y);
        		System.out.println("Making mouth.");
	    		break;
        	case 10:
        		circle = mouth(x, y);
        		message.setText("Click twice to make coat buttons");
        		System.out.println("Making mouth.");
        		break;
        	case 11:	//making coatButtons
        		circle = coatButton(centerX, y);
        		System.out.println("Making coat buttons.");
        		break;
        	case 12:
        		circle = coatButton(centerX, y);
        		message.setText("Drag mouse to make arms.");
        		System.out.println("Making coat buttons.");
        		break;
        	case 13:	//making arms
        	case 14:
        		makeArms();
        		System.out.println("Making arms.");
        		break;
        	default:
        		break;
        }
		clickCount++;
        try {
        	if (circle != null) pane.getChildren().add( circle );
        }
        catch (IllegalArgumentException iae) {
    		if (clickCount == 15) {
    			message.setText("Nice Done! You made it");
    			message.setFill(Color.RED);
    		}
    	}
	}
	
	//===================================================================================
	//Function to make circles of the snowman body
	//===================================================================================
	public Circle body (double x, double y, double radius) {
		Circle bd = new Circle(x, y, radius);
		bd.setFill(Color.WHITE);
		bd.setStroke(Color.BLACK);
		bd.setStrokeWidth(2);
		return bd;
	}
	
	//===================================================================================
	//Function to make circles of the snowman eyes
	//===================================================================================
	public Circle eye (double x, double y) {
		Circle cl = new Circle(x, y, 6);
		cl.setFill(Color.BLUE);
		return cl;
	}
	
	//===================================================================================
	//Function to make circles of the snowman mouth
	//===================================================================================
	public Circle mouth (double x, double y) {
		Circle mt = new Circle(x, y, 4);
		mt.setFill(Color.PINK);
		return mt;
	}
	
	//===================================================================================
	//Function to make circles of the snowman coat button
	//===================================================================================
	public Circle coatButton (double x, double y) {
		Circle cb = new Circle(x, y, 4);
		cb.setFill(Color.GREY);
		cb.setStroke(Color.BLACK);
		return cb;
	}
	
	//===================================================================================
	//Function to make circles of the snowman arms
	//===================================================================================
	public void makeArms () {
		scene.setOnMousePressed(e -> {
			startX = e.getX();
			startY = e.getY();
		});
		scene.setOnMouseReleased( e -> {
			endX = e.getX();
			endY = e.getY();
		});
		Line line = new Line(startX, startY, endX, endY);
		line.setStrokeWidth(5);
		line.setFill(Color.DARKGRAY);
		pane.getChildren().add(line);
	}
	
	/* Main function used to run the program in Eclipse---------------------------------
	public static void main(String[] args){
		launch(args);
	}
	------------------------------------------------------------------------------------*/	
}
