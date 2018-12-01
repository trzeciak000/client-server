public class Termin {
	public volatile int hour;
	public volatile boolean reserved = false;
	public volatile String user = null;
	
	public Termin(int hour) {
		this.hour = hour;
	}
	
	public void setHour(int value) {
		this.hour = value;
	}
	public void setReserved(boolean x) {
		this.reserved = x;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void resetUser() {
		this.user = null;
	}
	public int getHour() {
		return this.hour;
	}
	public boolean getReserved() {
		return this.reserved;
	}
	public String getUser() {
		return this.user;
	}
	
}
