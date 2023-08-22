package telran.time.applications;

import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class PrintCalendar {
	private static final int TITLE_OFFSET =10;
	private static final int WEEK_DAYS_OFFSET = 2;
	private static final int COLUMN_WIDTH = 4;
    private static Locale LOCALE = Locale.ENGLISH;
	public static void main(String[] args) {
		try {
			RecordArguments recordArguments = getRecordArguments(args);
			printCalendar(recordArguments);

		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	private static void printCalendar(RecordArguments recordArguments) {
		printTitle(recordArguments.month(), recordArguments.year());
		printWeekDays(recordArguments.firstDay());
		printDays(recordArguments.month(), recordArguments.year(),recordArguments.firstDay());
		
	}

	private static void printDays(int month, int year, DayOfWeek firstDayOfWeek) {
	    int nDays = getMonthDays(month, year);
	    LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
	    int dayOfWeekValue = firstDayOfMonth.getDayOfWeek().getValue();
	    int offset = (dayOfWeekValue-firstDayOfWeek.getValue()   + 7) % 7;
	    System.out.printf("%s", " ".repeat(offset * COLUMN_WIDTH));
	    for (int day = 1; day <= nDays; day++) {
	        System.out.printf("%4d", day);

	        if ((offset + day ) % 7 == 0) {
	            System.out.println();
	        }
	    }
	}


	private static int getFirstColumnOffset(int currentWeekDay) {
		
		return COLUMN_WIDTH * (currentWeekDay - 1);
	}

	private static int getFirstMonthWeekDay(int month, int year) {
		LocalDate ld = LocalDate.of(year, month, 1);
		return ld.get(ChronoField.DAY_OF_WEEK) ;
	}

	private static int getMonthDays(int month, int year) {
		YearMonth ym = YearMonth.of(year, month);
		return ym.lengthOfMonth();
	}

	private static void printWeekDays(DayOfWeek firstDayOfWeek) {
		System.out.printf("%s", " ".repeat(WEEK_DAYS_OFFSET));
		DayOfWeek[] orderedWeekDays=getOrderedWeekdays(firstDayOfWeek);
		for(DayOfWeek dayWeek: orderedWeekDays) {
			System.out.printf("%s ",dayWeek.getDisplayName(TextStyle.SHORT, LOCALE));
		}
		System.out.println();
		
	}

	private static DayOfWeek[] getOrderedWeekdays(DayOfWeek firstDayOfWeek) {
		DayOfWeek[] orderedWeekdays=new DayOfWeek[7];
		int firstDayValue=firstDayOfWeek.getValue();
		for(int i=0;i<7;i++) {
			orderedWeekdays[i]=DayOfWeek.of((firstDayValue+i-1)%7+1);
		}
		return orderedWeekdays;
	}

	private static void printTitle(int month, int year) {
		Month monthEn = Month.of(month);
		System.out.printf("%s%s %d\n", " ".repeat(TITLE_OFFSET),
				monthEn.getDisplayName(TextStyle.FULL, LOCALE), year);
		
	}

	private static RecordArguments getRecordArguments(String[] args) throws Exception{
		
		int month = getMonthArg(args);
		int year = getYearArg(args);
		DayOfWeek dayOfWeek = getFirstDayOfWeek(args);
		return new RecordArguments(month, year, dayOfWeek); 
	}

	private static DayOfWeek getFirstDayOfWeek(String[] args) {
		if(args.length>2) {
				String firstDayArgs=args[2];
				try {
					return DayOfWeek.valueOf(firstDayArgs.toUpperCase());
				}catch(IllegalArgumentException e) {
					System.out.println("Invalid day name,using default day:Monday");
				}
			
		}
		
		return DayOfWeek.MONDAY;
	}

	private static int getYearArg(String[] args) throws Exception {
		int yearRes = LocalDate.now().getYear();
		if(args.length > 1) {
			try {
				yearRes = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				throw new Exception("year must be a number");
			}
		}
		return yearRes;
	}

	private static int getMonthArg(String[] args) throws Exception{
		int monthRes = LocalDate.now().getMonthValue();
		if (args.length > 0)  {
			try {
				monthRes = Integer.parseInt(args[0]);
				if(monthRes < 1) {
					throw new Exception("Month value must not be less than 1");
				}
				if (monthRes > 12) {
					throw new Exception("Month value must not be greater than 12");
				}
			} catch (NumberFormatException e) {
				throw new Exception("Month value must be a number");
			}
		}
		return monthRes;
	}

}