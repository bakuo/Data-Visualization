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
        yAxis.setLabel("Number of Violent Crimes");
        //creating the chart
        final LineChart<String, Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);
        lineChart.setAnimated(true);
        lineChart.setTitle("US Violent Crimes 1998-2017");
        
        final CategoryAxis xAxis2 = new CategoryAxis();
        final NumberAxis yAxis2 = new NumberAxis();
        xAxis2.setLabel("Year");
        yAxis2.setLabel("Number of Violent Crimes");
        //creating the chart
        final LineChart<String, Number> lineChart2 = new LineChart<String,Number>(xAxis2,yAxis2);
        lineChart2.setAnimated(true);
        lineChart2.setTitle("US Violent Crimes 1998-2017");
        lineChart2.setVisible(false);
        
        ArrayList<String> data = new ArrayList<String>();
        ArrayList<String> year = new ArrayList<String>();
        ArrayList<Integer> violent = new ArrayList<Integer>();
        ArrayList<Double> column1array = new ArrayList<Double>();
        ArrayList<Double> column2array = new ArrayList<Double>();
        ArrayList<Double> column3array = new ArrayList<Double>();
        ArrayList<Double> column4array = new ArrayList<Double>();
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
	    
	    int xsum = 0;
    	int ysum = 0;
    	double xysum = 0;
    	int xsquare = 0;
    	int ysquare = 0;
        double column1avg = 0;
        double column2avg = 0;
        double column1 = 0;
        double column2 = 0;
        double m1 = 0;
    	double m2 = 0;
        for(int i = 0; i < year.size(); i++)
	    {
	    	xsum += Integer.parseInt(year.get(i));
	    	ysum += violent.get(i);
	    	column1 = Integer.parseInt(year.get(i)) - ((xsum)/20);
	    	column2 = violent.get(i) - ((ysum)/20);
	    	column1array.add(column1);
	    	column2array.add(column2);
	    	column3array.add(column1array.get(i) * column2array.get(i));
	    	column4array.add(column1array.get(i) * column1array.get(i));
	    	m1 += column3array.get(i);
	    	m2 += column4array.get(i);
	    }
        final double m = m1/m2;
        column1avg = xsum / 20;
        column2avg = ysum / 20;
        final double b = column2avg - (m * column1avg);

	    Button button = new Button("Future Data");
	    
        //defining a series
        XYChart.Series violentcrimes = new XYChart.Series();
        XYChart.Series futureviolentcrimes = new XYChart.Series();
        violentcrimes.setName("Violent Crimes");
        futureviolentcrimes.setName("Future Violent Crimes");
        
        //populating the series with data
        for(int i = 0; i < year.size(); i++)
        {
        	violentcrimes.getData().add(new XYChart.Data(year.get(i), violent.get(i)));
    		futureviolentcrimes.getData().add(new XYChart.Data(year.get(i), m * violent.get(i) + b));
        }
        button.setOnAction(value -> 
        {   
            button.setVisible(false);
            lineChart2.setVisible(true);
        });
        
        button.setLayoutX(500);
        button.setLayoutY(500);
        
        lineChart.getData().add(violentcrimes);
        lineChart2.getData().add(futureviolentcrimes);
        
        VBox vbox = new VBox();
        vbox.setPrefHeight(800);
        vbox.setPrefWidth(1000);
        vbox.getChildren().addAll(lineChart, lineChart2);
        
        Pane root = new Pane();
        root.getChildren().add(vbox);
        root.getChildren().add(button);
        
        Scene scene  = new Scene(root,1000,800);
       
        stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) 
    {
        launch(args);
    }
}