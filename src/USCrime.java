import javafx.scene.control.Button;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.layout.StackPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

public class USCrime extends Application 
{
 
    @Override public void start(Stage stage) 
    {
        stage.setTitle("Data Visualization");
        //defining the axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Year");
        //xAxis.setAutoRanging(false);
        yAxis.setLabel("Number of Violent Crimes");
        //creating the chart
        final LineChart<String, Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);
        lineChart.setAnimated(true);
        lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
        lineChart.setTitle("US Violent Crimes 1998-2017");
        
        ArrayList<String> data = new ArrayList<String>();
        ArrayList<String> year = new ArrayList<String>();
        ArrayList<Integer> violent = new ArrayList<Integer>();
        File file = new File("src/res/usviolentcrime1998-2017.txt");
	    try
	    {
	        Scanner sc = new Scanner(file);
	        while(sc.hasNextLine())
	        {
	        	String s = sc.nextLine();
	        	data.add(s);
	        }
	        sc.close();
	    } 
	    catch(FileNotFoundException e)
	    {
	        e.printStackTrace();
	    }
	    for(int i = 0; i < data.size(); i++)
	    {
	    	String[] parsed = data.get(i).split(",");
	    	year.add(parsed[0]);
	    	violent.add(Integer.parseInt(parsed[1]));
	    }
        
	    Button button = new Button("Future Data");
	    
        //defining a series
        XYChart.Series violentcrimes = new XYChart.Series();
        violentcrimes.setName("Violent Crimes");
        //populating the series with data
        for(int i = 0; i < year.size() - 19; i++)
        {
        	violentcrimes.getData().add(new XYChart.Data(year.get(i), violent.get(i)));
        }
        
    	XYChart.Series futureviolentcrimes = new XYChart.Series();

        button.setOnAction(value -> 
        {
            for(int i = 19; i < year.size(); i++)
            {
            	futureviolentcrimes.getData().add(new XYChart.Data(year.get(i),violent.get(i)));
            }
            futureviolentcrimes.setName("Future Violent Crimes");
            lineChart.getData().add(futureviolentcrimes);
            
        });
        
        button.setLayoutX(500);
        button.setLayoutY(500);
        
        lineChart.getData().add(violentcrimes);
        
        VBox vbox = new VBox();
        vbox.setPrefHeight(800);
        vbox.setPrefWidth(1000);
        vbox.getChildren().addAll(lineChart);
        
        Pane root = new Pane();
        root.getChildren().add(vbox);
        root.getChildren().add(button);
        
        Scene scene  = new Scene(root,1000,600);
       
        stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) 
    {
        launch(args);
    }
}