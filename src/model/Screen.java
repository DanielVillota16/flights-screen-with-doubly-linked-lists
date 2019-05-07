package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Screen {

	private Flight first;
	
	public Screen(int numberOfFlights) throws IOException {
		if(numberOfFlights > 0) {
			int counter = 0;
			this.first = generateRandomFlight();
			Flight temp = first;
			while(counter < numberOfFlights-1) {
				temp.setNext(generateRandomFlight());
				temp.getNext().setPrevious(temp);
				temp = temp.getNext();
				counter++;
			}
		}
	}

	public Flight getFirst() {
		return first;
	}

	public void setFirst(Flight first) {
		this.first = first;
	}

	public void sortByDepartureTime() {
		//TODO use of selection sort
		Flight current = first;
		while(current != null) {
			Flight temp = current.getNext();
			Flight min = current;
			while(temp != null) {
				if(temp.compareTo(min) <= 0) {
					min = temp;
				}
				temp = temp.getNext();
			}
			boolean firstIt = false;
			if(min != current) {
				Flight next1 = current.getNext();
				Flight previous1 = current.getPrevious();
				
				Flight next2 = min.getNext();
				Flight previous2 = min.getPrevious();
				
				if(min == current.getNext()	) {
					if(previous1 != null) previous1.setNext(min);
					else {
						first = min;
						firstIt = true;
					}
					current.setNext(next2);
					current.setPrevious(min);
					if(next2 != null) next2.setPrevious(current);
					min.setNext(current);
					min.setPrevious(previous1);
				} else {
					if(next1 != null) next1.setPrevious(min);
					if(previous1 != null) previous1.setNext(min);
					else {
						first = min;
						firstIt = true;
					}
					
					min.setNext(next1);
					min.setPrevious(previous1);
					
					if(next2 != null) next2.setPrevious(current);
					if(previous2 != null) previous2.setNext(current);
					
					current.setNext(next2);
					current.setPrevious(previous2);
				}
				current = min;
			}	
			if(firstIt) {
				current = first.getNext();
			} else {
				current = current.getNext();
			}
		}
	}

	public void sortByCode() {
		// TODO use of selection sort
		Flight current = first;
		while(current != null) {
			Flight temp = current.getNext();
			Flight min = current;
			while(temp != null) {
				if(temp.getCode() <= min.getCode()) {
					min = temp;
				}
				temp = temp.getNext();
			}
			boolean firstIt = false;
			if(min != current) {
				Flight next1 = current.getNext();
				Flight previous1 = current.getPrevious();
				
				Flight next2 = min.getNext();
				Flight previous2 = min.getPrevious();
				
				if(min == current.getNext()) {
					if(previous1 != null) previous1.setNext(min);
					else {
						first = min;
						firstIt = true;
					}
					current.setNext(next2);
					current.setPrevious(min);
					if(next2 != null) next2.setPrevious(current);
					min.setNext(current);
					min.setPrevious(previous1);
				} else {
					if(next1 != null) next1.setPrevious(min);
					if(previous1 != null) previous1.setNext(min);
					else {
						first = min;
						firstIt = true;
					}
					
					min.setNext(next1);
					min.setPrevious(previous1);
					
					if(next2 != null) next2.setPrevious(current);
					if(previous2 != null) previous2.setNext(current);
					
					current.setNext(next2);
					current.setPrevious(previous2);
				}
				current = min;
			}	
			if(firstIt) {
				current = first.getNext();
			} else {
				current = current.getNext();
			}
		}
	}

	public void sortByAirline() {
		// TODO Use of bubble sort
		if(first != null) {
			FlightAirlineComparator fac = new FlightAirlineComparator();
			Flight temp = first;
			int counter = 0;
			int size = size();
			while(temp != null) {
				Flight current = first;
				int counter2 = 0;
				while(current.getNext() != null && counter2 < size-counter) {
					//current.getAirline().compareTo(current.getNext().getAirline())
					if(fac.compare(current, current.getNext()) > 0) {
						if(first == current) first = current.getNext();
						Flight next = current.getNext().getNext();
						Flight prev = current.getPrevious();
						if(next != null) next.setPrevious(current);
						if(prev != null) prev.setNext(current.getNext());
						current.getNext().setNext(current);
						current.getNext().setPrevious(prev);
						current.setPrevious(current.getNext());
						current.setNext(next);
					} else {
						current = current.getNext();
					}
					counter2++;
				}
				counter++;
				temp = temp.getNext();
			}
		}
	}

	public void sortByDestination() {
		// TODO Use of bubble sort
		if(first != null) {
			Flight temp = first;
			int counter = 0;
			int size = size();
			while(temp != null) {
				Flight current = first;
				int counter2 = 0;
				while(current.getNext() != null && counter2 < size-counter) {
					if(current.getDestination().compareTo(current.getNext().getDestination()) > 0) {
						if(first == current) first = current.getNext();
						Flight next = current.getNext().getNext();
						Flight prev = current.getPrevious();
						if(next != null) next.setPrevious(current);
						if(prev != null) prev.setNext(current.getNext());
						current.getNext().setNext(current);
						current.getNext().setPrevious(prev);
						current.setPrevious(current.getNext());
						current.setNext(next);
					} else {
						current = current.getNext();
					}
					counter2++;
				}
				counter++;
				temp = temp.getNext();
			}
		}
	}

	public void sortByGate() {
		// TODO use of cocktail sort
		Flight temp = first;
		int counter = 0;
		int size = size();
		while(temp != null) {
			Flight current = first;
			int counter2 = 0;
			while(current.getNext() != null && counter2 < size - counter) {
				if(current.getGate() > current.getNext().getGate()) {
					if(current == first) first = current.getNext();
					Flight next = current.getNext().getNext();
					Flight prev = current.getPrevious();
					if(next != null) next.setPrevious(current);
					if(prev != null) prev.setNext(current.getNext());
					current.setPrevious(current.getNext());
					current.getNext().setNext(current);
					current.getNext().setPrevious(prev);
					current.setNext(next);
				} else {
					current = current.getNext();
				}
				counter2++;
			}
			while(current.getPrevious() != null && counter2 > counter) {
				if(current.getGate() < current.getPrevious().getGate()) {
					if(current.getPrevious() == first) first = current;
					Flight next = current.getNext();
					Flight prev = current.getPrevious().getPrevious();
					if(next != null) next.setPrevious(current.getPrevious());
					if(prev != null) prev.setNext(current);
					current.setNext(current.getPrevious());
					current.getPrevious().setPrevious(current);
					current.getPrevious().setNext(next);
					current.setPrevious(prev);					
				} else {
					current = current.getPrevious();
				}
				counter2--;
			}
			temp = temp.getNext();
			counter++;
		}
	}
	
	public void sortByDate() {
		//TODO use of insertion sort
		if(first.getNext() != null) {
			FlightDateComparator fdc = new FlightDateComparator();
			Flight current = first.getNext();
			while(current != null) {
				Flight temp = current;
				while(temp.getPrevious() != null) {
					//(temp.getDate().compareTo(temp.getPrevious().getDate()) < 0)
					if(fdc.compare(temp, temp.getPrevious()) < 0) {
						if(temp.getPrevious() == first) first = temp;
						Flight next = temp.getNext();
						Flight prev = temp.getPrevious().getPrevious();
						if(next != null) next.setPrevious(temp.getPrevious());
						if(prev != null) prev.setNext(temp);
						temp.setNext(temp.getPrevious());
						temp.getPrevious().setPrevious(temp);
						temp.getPrevious().setNext(next);
						temp.setPrevious(prev);
					} else {
						temp = temp.getPrevious();
					}
				}
				current = current.getNext();
			}
		}
	}

	public Flight searchFlightByCode(int code) {
		// TODO Use linear search to find the specified flight with the correspondent code
		Flight current = first;
		Flight found = null;
		while(current != null && found == null) {
			if(current.getCode() == code) {
				found = current;
			}
			current = current.getNext();
		}
		return found;
	}

	public Flight searchFlightByAirline(String airline) {
		Flight current = first;
		Flight found = null;
		while(current != null && found == null) {
			if(current.getAirline().equals(airline)) {
				found = current;
			}
			current = current.getNext();
		}
		return found;
	}

	public Flight searchFlightByGate(int gate) {
		Flight current = first;
		Flight found = null;
		while(current != null && found == null) {
			if(current.getGate() == gate) {
				found = current;
			}
			current = current.getNext();
		}
		return found;
	}

	public Flight searchFlightByDestination(String destination) {
		Flight current = first;
		Flight found = null;
		while(current != null && found == null) {
			if(current.getDestination().equals(destination)) {
				found = current;
			}
			current = current.getNext();
		}
		return found;
	}

	public Flight searchFlightByDate(String date) {
		Flight current = first;
		Flight found = null;
		while(current != null && found == null) {
			if(current.getDate().toString().equals(date)) {
				found = current;
			}
			current = current.getNext();
		}
		return found;
	}
	
	public Flight searchFlightByTime(String time) {
		Flight current = first;
		Flight found = null;
		while(current != null && found == null) {
			if(current.getDepartureTime().toString().equals(time)) {
				found = current;
			}
			current = current.getNext();
		}
		return found;
	}

	public int size() {
		Flight current = first;
		int size = 0;
		while (current != null) {
			size++;
			current = current.getNext();
		}
		return size;
	}
	
	public Flight[] getFlightsArray() {
		Flight current = first;
		Flight[] arr = new Flight[size()];
		int index = 0;
		while(current != null) {
			arr[index] = current;
			current = current.getNext();
			index++;
		}
		return arr;
	}
	
	public Flight generateRandomFlight() throws IOException {
		int hour = (int)(Math.random()*11+1), minutes = (int)(Math.random()*60);
		String[] dayTimes = {"AM", "PM"};
		Random r = new Random();
		String dayTime = dayTimes[r.nextInt(2)];
		long dateMillis = (long)(Math.random()*2592000000L) + System.currentTimeMillis();
		int code = (int)(Math.random()*1000), gate = (int)(Math.random()*14+1);
		String airline = readAirlines()[(int)(Math.random()*readAirlines().length-1)];
		String destination = readDestinations()[(int)(Math.random()*readDestinations().length-1)];
		return new Flight(new Time(hour, minutes, dayTime), new FlightDate(dateMillis), code, airline, destination, gate);
	}
	
	public String[] readDestinations() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("data/destinations"));
		String line = br.readLine();
		String destinations = "";
		while(line != null) {
			destinations += line + "~";
			line = br.readLine();
		}
		String[] dest = destinations.split("~");
		br.close();
		return dest;
	}
	
	public String[] readAirlines() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("data/airlines"));
		String line = br.readLine();
		String airlines = "";
		while(line != null) {
			airlines += line + "~";
			line = br.readLine();
		}
		String[] airl = airlines.split("~");
		br.close();
		return airl;
	}
	
}