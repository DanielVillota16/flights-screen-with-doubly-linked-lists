package ui;

import java.io.IOException;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Flight;
import model.Screen;
import model.Time;

public class FlightsScreenController {
	
	private Screen screen;
    private TableView<Flight> flightsList;
    private ObservableList<Flight> data;
    
    public final static int ROWS_PER_PAGE = 10;
	
	@FXML
    private Pagination pagination;
	
	@FXML
    private MenuButton searchBy;

    @FXML
    private TextField flightToSearch;

    @FXML
    private TextField numberOfFlights;

	@FXML
    public void initialize() {
		flightsList = new TableView<Flight>();
		data = FXCollections.observableArrayList();
		setColumns();
		pagination.setMaxWidth(600);
		flightToSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				flightToSearch.setText("");
				flightToSearch.setStyle("-fx-text-inner-color: black;");
			}
		});
		
    }
    
    public void refreshTable() {
    	flightsList.getItems().clear();
    	data.clear();
    	data.addAll(screen.getFlightsArray());
    	flightsList.setItems(data);
    	flightsList.refresh();
		pagination.setPageFactory(this::createPage);    
    }
    
    private Node createPage(int pageIndex) {
       	if(pagination.getCurrentPageIndex() <= data.size()/ROWS_PER_PAGE){
       		int fromIndex = pageIndex * ROWS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, data.size());
            flightsList.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));
            return flightsList;
       	}
        return null;
    }
    
    @SuppressWarnings("unchecked")
	public void setColumns(){
    	TableColumn<Flight, Date> dates = new TableColumn<Flight, Date>("Date");
        dates.setMinWidth(100);
        dates.setCellValueFactory(new PropertyValueFactory<>("date"));
    	
        TableColumn<Flight, Time> departureTimes = new TableColumn<Flight, Time>("Time");
        departureTimes.setMinWidth(100);
        departureTimes.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        
        TableColumn<Flight, String> airlines = new TableColumn<Flight, String>("Airline");
        airlines.setMinWidth(100);
        airlines.setCellValueFactory(new PropertyValueFactory<>("airline"));
        
        TableColumn<Flight, Integer> codes = new TableColumn<Flight, Integer>("Flight");
        codes.setMinWidth(100);
        codes.setCellValueFactory(new PropertyValueFactory<>("code"));
        
        TableColumn<Flight, String> destinations = new TableColumn<Flight, String>("Destination");
        destinations.setMinWidth(100);
        destinations.setCellValueFactory(new PropertyValueFactory<>("destination"));
        
        TableColumn<Flight, Integer> gates = new TableColumn<Flight, Integer>("Gate");
        gates.setMinWidth(100);
        gates.setCellValueFactory(new PropertyValueFactory<>("gate"));
        
        flightsList.getColumns().addAll(dates, departureTimes, airlines, codes, destinations, gates);
    }
    
    
    @FXML
    public void generateRandomFlights(ActionEvent event) {
    	try {
    		int numberOfFlights = Integer.parseInt(this.numberOfFlights.getText());
    		screen = new Screen(numberOfFlights);
    		refreshTable();
    	} catch(NumberFormatException e) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Illegal value, please enter a positive integer.");
    		alert.show();
    		alert.setOnCloseRequest(new EventHandler<DialogEvent>(){
				@Override
				public void handle(DialogEvent arg0) {
					numberOfFlights.setText("");
				}
    		});
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	numberOfFlights.setText("");
    }

	@FXML
    public void search(ActionEvent event) {
		Flight flight = null;
		try {
			if(searchBy.getUserData() != null) {
				if(searchBy.getUserData().equals("airline")) {
		    		flight = screen.searchFlightByAirline(flightToSearch.getText());
		    	} else if(searchBy.getUserData().equals("code")) {	
		    		flight = screen.searchFlightByCode(Integer.parseInt(flightToSearch.getText()));
		    	} else if(searchBy.getUserData().equals("date")) {
		    		flight = screen.searchFlightByDate(flightToSearch.getText());
		    	} else if(searchBy.getUserData().equals("time")) {
		    		flight = screen.searchFlightByTime(flightToSearch.getText());
		    	} else if(searchBy.getUserData().equals("gate")) {
		    		flight = screen.searchFlightByGate(Integer.parseInt(flightToSearch.getText()));
		    	} else if(searchBy.getUserData().equals("destination")) {
		    		flight = screen.searchFlightByDestination(flightToSearch.getText());
		    	}
				if(flight != null) {
					showFoundFlight(flight);
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
		    		alert.setContentText("Flight with the specified info was not found, please select a criteria and provide a correct input");
		    		alert.show();
				}
			} else {
				throw new NullPointerException("Please select a criteria");
			}
		} catch(NumberFormatException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Illegal input value, please select a criteria and provide a correct input");
    		alert.show();
		} catch(NullPointerException e) {
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText(e.getMessage());
    		alert.show();
		}
		flightToSearch.setText("");
		searchBy.setUserData(null);
    }
    
    @SuppressWarnings("unchecked")
	public void showFoundFlight(Flight flight) {
    	Stage stage = new Stage();
    	Scene scene = new Scene(new Group());
    	stage.setTitle("Flight");
        stage.setWidth(620);
        stage.setHeight(150);
        stage.setResizable(false);
        Label label = new Label("Found flight:");
        label.setFont(new Font("Arial", 20));
        TableView<Flight> tv = new TableView<Flight>();
        ObservableList<Flight> data = FXCollections.observableArrayList();
    	data.add(flight);
        
    	TableColumn<Flight, Date> dates = new TableColumn<Flight, Date>("Date");
        dates.setMinWidth(100);
        dates.setCellValueFactory(new PropertyValueFactory<>("date"));
    	
        TableColumn<Flight, Time> departureTimes = new TableColumn<Flight, Time>("Time");
        departureTimes.setMinWidth(100);
        departureTimes.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        
        TableColumn<Flight, String> airlines = new TableColumn<Flight, String>("Airline");
        airlines.setMinWidth(100);
        airlines.setCellValueFactory(new PropertyValueFactory<>("airline"));
        
        TableColumn<Flight, Integer> codes = new TableColumn<Flight, Integer>("Flight");
        codes.setMinWidth(100);
        codes.setCellValueFactory(new PropertyValueFactory<>("code"));
        
        TableColumn<Flight, String> destinations = new TableColumn<Flight, String>("Destination");
        destinations.setMinWidth(100);
        destinations.setCellValueFactory(new PropertyValueFactory<>("destination"));
        
        TableColumn<Flight, Integer> gates = new TableColumn<Flight, Integer>("Gate");
        gates.setMinWidth(100);
        gates.setCellValueFactory(new PropertyValueFactory<>("gate"));
        
        tv.setItems(data);
        tv.getColumns().addAll(dates, departureTimes, airlines, codes, destinations, gates);
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, tv);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void searchByAirline(ActionEvent event) {
    	searchBy.setUserData("airline");
    	flightToSearch.setText("Airline");
    	flightToSearch.setStyle("-fx-text-inner-color: gray;");
    }

    @FXML
    public void searchByDate(ActionEvent event) {
    	searchBy.setUserData("date");
    	flightToSearch.setText("Date");
    	flightToSearch.setStyle("-fx-text-inner-color: gray;");
    }

    @FXML
    public void searchByDestination(ActionEvent event) {
    	searchBy.setUserData("destination");
    	flightToSearch.setText("Destination");
    	flightToSearch.setStyle("-fx-text-inner-color: gray;");
    }

    @FXML
    public void searchByFlightCode(ActionEvent event) {
    	searchBy.setUserData("code");
    	flightToSearch.setText("Code");
    	flightToSearch.setStyle("-fx-text-inner-color: gray;");
    }

    @FXML
    public void searchByGate(ActionEvent event) {
    	searchBy.setUserData("gate");
    	flightToSearch.setText("Gate");
    	flightToSearch.setStyle("-fx-text-inner-color: gray;");
    }

    @FXML
    public void searchByTime(ActionEvent event) {
    	searchBy.setUserData("time");
    	flightToSearch.setText("Time");
    	flightToSearch.setStyle("-fx-text-inner-color: gray;");
    }


    @FXML
    public void sortByDate(ActionEvent event) {
    	long t1 = System.currentTimeMillis();
    	screen.sortByDate();
    	long t2 = System.currentTimeMillis();
    	System.out.println("sorted by date\tused method: insertion sort\ttaken time: " + (t2-t1) + " milliseconds");
    	refreshTable();
    }
    
    @FXML
    public void sortByAirline(ActionEvent event) {
    	long t1 = System.currentTimeMillis();
    	screen.sortByAirline();
    	long t2 = System.currentTimeMillis();
    	System.out.println("sorted by airline\tused method: bubble sort\ttaken time: " + (t2-t1) + " milliseconds");
    	refreshTable();
    }

    @FXML
    public void sortByDestination(ActionEvent event) {
    	long t1 = System.currentTimeMillis();
    	screen.sortByDestination();
    	long t2 = System.currentTimeMillis();
    	System.out.println("sorted by destination\tused method: bubble sort\ttaken time: " + (t2-t1) + " milliseconds");
    	refreshTable();
    }

    @FXML
    public void sortByFlightCode(ActionEvent event) {
    	long t1 = System.currentTimeMillis();
    	screen.sortByCode();
    	long t2 = System.currentTimeMillis();
    	System.out.println("sorted by code\tused method: selection sort\ttaken time: " + (t2-t1) + " milliseconds");
    	refreshTable();
    }

    @FXML
    public void sortByGate(ActionEvent event) {
    	long t1 = System.currentTimeMillis();
    	screen.sortByGate();
    	long t2 = System.currentTimeMillis();
    	System.out.println("sorted by gate\tused method: cocktail sort\ttaken time: " + (t2-t1) + " milliseconds");
    	refreshTable();
    }

    @FXML
    public void sortByTime(ActionEvent event) {
    	long t1 = System.currentTimeMillis();
    	screen.sortByDepartureTime();
    	long t2 = System.currentTimeMillis();
    	System.out.println("sorted by departure time\tused method: selection sort\ttaken time: " + (t2-t1) + " milliseconds");
    	refreshTable();
    }

}
