package comp533.mvc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;


public class ViewImpl extends AMapReduceTracer implements PropertyChangeListener, View {
	
	public ViewImpl() {
	}
	
	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		tracePropertyChange(evt);
	}
	
	@Override
	public String toString() {
		return VIEW;
	}
}