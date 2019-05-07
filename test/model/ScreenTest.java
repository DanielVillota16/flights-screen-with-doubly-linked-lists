package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ScreenTest {
	
	private static final long DATE_REFERENCE = System.currentTimeMillis();

	private Screen screen;
	
	private Flight[] flights;
	
	private void setupScenary1() {
		
	}
	
	private void setupScenary2() throws IOException {
		screen = new Screen(3);
		Flight f1 = new Flight(new Time(5,30,"AM"), new FlightDate(DATE_REFERENCE), 55, "Avianca", "Manizales", 7);
		Flight f2 = new Flight(new Time(4,30,"AM"), new FlightDate(DATE_REFERENCE+50000000L), 30, "Wingo", "Bogotá", 4);
		Flight f3 = new Flight(new Time(6,30,"AM"), new FlightDate(DATE_REFERENCE+6000L), 200, "SATENA", "Medellín", 9);
		f1.setNext(f2);
		f2.setPrevious(f1);
		f2.setNext(f3);
		f3.setPrevious(f2);
		screen.setFirst(f1);
		this.flights = new Flight[3];
		this.flights[0] = f1;
		this.flights[1] = f2;
		this.flights[2] = f3;
	}
	
	/*private void setupScenary2() throws IOException {
		screen = new Screen(3);
		Flight f1 = new Flight(new Time(5,30,"AM"), new FlightDate(DATE_REFERENCE), 55, "Avianca", "Manizales", 7);
		Flight f2 = new Flight(new Time(4,30,"AM"), new FlightDate(DATE_REFERENCE+50000000L), 30, "Wingo", "Bogotá", 4);
		Flight f3 = new Flight(new Time(6,30,"AM"), new FlightDate(DATE_REFERENCE+6000L), 200, "SATENA", "Medellín", 9);
		Flight[] flights = new Flight[3];
		flights[0] = f1;
		flights[1] = f2;
		flights[2] = f3;
		screen.setFlights(flights);
		this.flights = new Flight[3];
		this.flights[0] = f1;
		this.flights[1] = f2;
		this.flights[2] = f3;
	}*/
	
	@Test
	void testScreen() throws IOException {
		setupScenary1();
		Screen s = new Screen(3);
		assertTrue(s.size() == 3, "The list of flights hasn't been created correctly");
	}
	
	@Test
	void testSortByDepartureTime() throws IOException {
		setupScenary2();
		screen.sortByDepartureTime();
		assertTrue(screen.getFirst() == flights[1],"The sort failed");
		assertTrue(screen.getFirst().getNext() == flights[0],"The sort failed");
		assertTrue(screen.getFirst().getNext().getNext() == flights[2],"The sort failed");
	}
	
	@Test
	void testSortByDate() throws IOException {
		setupScenary2();
		screen.sortByDate();
		assertTrue(screen.getFirst() == flights[0],"The sort failed");
		assertTrue(screen.getFirst().getNext() == flights[2],"The sort failed");
		assertTrue(screen.getFirst().getNext().getNext() == flights[1],"The sort failed");
	}
	
	@Test
	void testSortByAirline() throws IOException {
		setupScenary2();
		screen.sortByAirline();
		assertTrue(screen.getFirst() == flights[0],"The sort failed");
		assertTrue(screen.getFirst().getNext() == flights[2],"The sort failed");
		assertTrue(screen.getFirst().getNext().getNext() == flights[1],"The sort failed");
	}
	
	@Test
	void testSortByDestination() throws IOException {
		setupScenary2();
		screen.sortByDestination();
		assertTrue(screen.getFirst() == flights[1],"The sort failed");
		assertTrue(screen.getFirst().getNext() == flights[0],"The sort failed");
		assertTrue(screen.getFirst().getNext().getNext() == flights[2],"The sort failed");
	}
	
	@Test
	void testSortByFlightCode() throws IOException {
		setupScenary2();
		screen.sortByCode();
		assertTrue(screen.getFirst() == flights[1],"The sort failed");
		assertTrue(screen.getFirst().getNext() == flights[0],"The sort failed");
		assertTrue(screen.getFirst().getNext().getNext() == flights[2],"The sort failed");
	}
	
	@Test
	void testSortByGate() throws IOException {
		setupScenary2();
		screen.sortByGate();
		assertTrue(screen.getFirst() == flights[1],"The sort failed");
		assertTrue(screen.getFirst().getNext() == flights[0],"The sort failed");
		assertTrue(screen.getFirst().getNext().getNext() == flights[2],"The sort failed");
	}
	
	@Test
	void testSearchByTime() throws IOException {
		setupScenary2();
		Flight f = screen.searchFlightByTime(new Time(5,30,"AM").toString());
		assertTrue(f == flights[0], "The search went wrong");
	}
	
	@Test
	void testSearchByDate() throws IOException {
		setupScenary2();
		long date = DATE_REFERENCE+50000000L;
		Flight f = screen.searchFlightByDate(new FlightDate(date).toString());
		assertTrue(f == flights[1], "The search went wrong");
	}
	
	@Test
	void testSearchByAirline() throws IOException {
		setupScenary2();
		Flight f = screen.searchFlightByAirline("Avianca");
		assertTrue(f == flights[0], "The search went wrong");
	}
	
	@Test
	void testSearchByDestination() throws IOException {
		setupScenary2();
		Flight f = screen.searchFlightByDestination("Medellín");
		assertTrue(f == flights[2], "The search went wrong");
	}
	
	@Test
	void testSearchByFlightCode() throws IOException {
		setupScenary2();
		Flight f = screen.searchFlightByCode(30);
		assertTrue(f == flights[1], "The search went wrong");
	}
	
	@Test
	void testSearchByFlightByGate() throws IOException {
		setupScenary2();
		Flight f = screen.searchFlightByGate(7);
		assertTrue(f == flights[0], "The search went wrong");
	}

}
