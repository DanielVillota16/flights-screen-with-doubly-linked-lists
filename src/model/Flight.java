package model;

public class Flight implements Comparable<Flight>{

	private Time departureTime;
	private FlightDate date;
	private int code;
	private String airline;
	private String destination;
	private int gate;
	private Flight next;
	private Flight previous;

	public Flight(Time departureTime, FlightDate date, int code, String airline, String destination, int gate) {
		this.departureTime = departureTime;
		this.date = date;
		this.code = code;
		this.airline = airline;
		this.destination = destination;
		this.gate = gate;
		this.next = null;
		this.previous = null;
	}

	public Time getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Time departureTime) {
		this.departureTime = departureTime;
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getAirline() {
		return this.airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getDestination() {
		return this.destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getGate() {
		return this.gate;
	}

	public void setGate(int gate) {
		this.gate = gate;
	}
	
	public FlightDate getDate() {
		return date;
	}

	public void setDate(FlightDate date) {
		this.date = date;
	}


	@Override
	public int compareTo(Flight flight) {
		// TODO Compare the actual instance with the one given as parameter by the default criteria: the hour 
		// of departure time
		return departureTime.compareTo(flight.departureTime);
	}

	public Flight getNext() {
		return next;
	}

	public void setNext(Flight next) {
		this.next = next;
	}

	public Flight getPrevious() {
		return previous;
	}

	public void setPrevious(Flight previous) {
		this.previous = previous;
	}

}