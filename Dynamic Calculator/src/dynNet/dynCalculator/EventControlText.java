package dynNet.dynCalculator;

import javax.swing.event.*;



/**
 * Class [EventControlText]
 * <p>
 * Eventprocessing for the DynCalculator
 *
 * @author Prof. Dr.-Ing. Wolf-Dieter Otte
 * @version Feb. 2000
 */
class EventControlText implements DocumentListener{

	// This field will contain a reference to a DynCalculator
	DynCalculator calculator;

	// The Constructor
	EventControlText(DynCalculator theCalculator){
		calculator = theCalculator;
	}

	// The methods of the interface DocumentListener
	public void insertUpdate(DocumentEvent e) {
 		calculator.getResult();
 	}

	public void removeUpdate(DocumentEvent e) {
		calculator.getResult();
	}
	
	public void changedUpdate(DocumentEvent e){
	}
}