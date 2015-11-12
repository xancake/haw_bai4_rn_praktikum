package de.xancake.pattern.listener;

import java.util.ArrayList;
import java.util.List;

public class EventExecutor<L> {
	private List<L> myListeners;
	
	public EventExecutor() {
		this(new ArrayList<L>());
	}
	
	protected EventExecutor(List<L> listenerList) {
		myListeners = listenerList;
	}
	
	public void addListener(L l) {
		myListeners.add(l);
	}
	
	public void removeListener(L l) {
		myListeners.remove(l);
	}
	
	public List<L> getListeners() {
		return new ArrayList<L>(myListeners);
	}
	
	public void fireEvent(Event_A<? super L> event) {
		for(L l : myListeners) {
			event.fireEvent(l);
		}
	}
}
