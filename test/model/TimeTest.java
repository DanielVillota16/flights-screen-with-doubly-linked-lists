package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TimeTest {
	
	private Time time;

	private void setupScenary1() {
		
	}
	
	private void setupScenary2() {
		this.time = new Time(4, 20, "PM");
	}
	
	@Test
	public void testTime() {
		setupScenary1();
		int hour = 4;
		int minutes = 20;
		String dayTime = "PM";
		time = new Time(hour, minutes, dayTime);
		assertTrue(time.getHour() == hour, "The hour attribute hasn't been assigned correctly");
		assertTrue(time.getMinutes() == minutes, "The minutes attribute hasn't been assigned correctly");
		assertTrue(time.getDayTime().equals(dayTime), "The dayTime attribute hasn't been assigned correctly");
	}

	@Test
	public void testCompareTo1() {
		setupScenary2();
		Time time = new Time(5,45,"AM");
		int comparison = this.time.compareTo(time);
		assertTrue(comparison == 1, "The comparison went wrong");
	}

	@Test
	public void testCompareTo2() {
		setupScenary2();
		Time time = new Time(6,30,"PM");
		int comparison = this.time.compareTo(time);
		assertTrue(comparison == -1, "The comparison went wrong");
	}
	
	@Test
	public void testCompareTo3() {
		setupScenary2();
		Time time = new Time(4,20,"PM");
		int comparison = this.time.compareTo(time);
		assertTrue(comparison == 0, "The comparison went wrong");
	}

	@Test
	public void testToString() {
		setupScenary2();
		String result = this.time.toString();
		String expectedResult = "4:20 PM";
		assertTrue(result.equals(expectedResult), "The equals method returns an incorrect String representation for the actual time instance");
	}

}
