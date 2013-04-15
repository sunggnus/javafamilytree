package tree.model;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class PersonUtil {
	
	static public String calendarToString(GregorianCalendar time) {

		SimpleDateFormat terminlayout = new SimpleDateFormat();

		terminlayout.applyPattern("EEE, dd MMMM yyyy");
		
		return calendarToString(time,terminlayout);

		
	}
	
	static public String calendarToSimpleString(GregorianCalendar time){
		SimpleDateFormat terminlayout = new SimpleDateFormat();

		terminlayout.applyPattern("dd.MM.yyyy");
		
		return calendarToString(time,terminlayout);
	}
	
	static public String calendarToString(GregorianCalendar time, SimpleDateFormat layout){
		if(time==null){
			return "";
		}
		
		return layout.format(time.getTime());

	}

}
