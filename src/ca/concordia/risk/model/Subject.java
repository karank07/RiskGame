package ca.concordia.risk.model;

public interface Subject {
	
	public void notify_observer();
	
	public void attach(Observer o);
	
	public void detach(Observer o);
	
}
