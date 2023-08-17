package telran.time;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.UnsupportedTemporalTypeException;

public class NextFriday13 implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		if (!temporal.isSupported(ChronoField.DAY_OF_MONTH) || !temporal.isSupported(ChronoField.DAY_OF_WEEK)) {
	        throw new UnsupportedTemporalTypeException("must support days and weeks");
	    }
		temporal = temporal.plus(1, ChronoUnit.DAYS);
	    
             while (temporal.get(ChronoField.DAY_OF_WEEK) != 5 ||temporal.get(ChronoField.DAY_OF_MONTH) != 13) {
            	 temporal = temporal.get(ChronoField.DAY_OF_WEEK)==5?temporal.plus(1, ChronoUnit.WEEKS):temporal.plus(1, ChronoUnit.DAYS);
	    }
	    
	    return temporal;
	}
			

}

	
