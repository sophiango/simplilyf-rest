import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class Main {

	static HashMap<Integer, Integer> tempByMonth;

	static {
		tempByMonth = new HashMap<Integer, Integer>();
		tempByMonth.put(1, 70);
		tempByMonth.put(2, 73);
		tempByMonth.put(3, 75);
		tempByMonth.put(4, 65);
		tempByMonth.put(5, 60);
		tempByMonth.put(6, 57);
		tempByMonth.put(7, 56);
		tempByMonth.put(8, 55);
		tempByMonth.put(9, 60);
		tempByMonth.put(10, 65);
		tempByMonth.put(11, 70);
		tempByMonth.put(12, 75);
	}

	static HashMap<Integer, String> tempModeByMonth;

	static {
		tempModeByMonth = new HashMap<Integer, String>();
		tempModeByMonth.put(1, "heat");
		tempModeByMonth.put(2, "heat");
		tempModeByMonth.put(3, "heat");
		tempModeByMonth.put(4, "cool");
		tempModeByMonth.put(5, "cool");
		tempModeByMonth.put(6, "cool");
		tempModeByMonth.put(7, "cool");
		tempModeByMonth.put(8, "cool");
		tempModeByMonth.put(9, "cool");
		tempModeByMonth.put(10, "heat");
		tempModeByMonth.put(11, "heat");
		tempModeByMonth.put(12, "heat");
	}

	// static ArrayList<Integer> heat_months;
	// static {
	// heat_months = new ArrayList<Integer>();
	// heat_months.add(10);
	// heat_months.add(11);
	// heat_months.add(12);
	// heat_months.add(1);
	// heat_months.add(2);
	// heat_months.add(3);
	// }
	//
	// static ArrayList<Integer> cool_months;
	// static {
	// cool_months = new ArrayList<Integer>();
	// cool_months.add(4);
	// cool_months.add(5);
	// cool_months.add(6);
	// cool_months.add(7);
	// cool_months.add(8);
	// cool_months.add(9);
	// }

	final static String where_id = "I1_Ud68K2g2AgWGstgG39Xo3kBPCzfXk2XeH3UAHIPUz_o_dzMOqPA";
	final static String device_id = "TB36giw8Mpqza4S-9dphVWRJTWVz7JnV";
	final static String name = "LivingRoomThermo";
	static String hvac_mode = "";
	final static boolean can_cool = true;
	final static boolean can_heat = true;
	final static boolean has_leaf = true;
	final static boolean has_fan = true;
	final static boolean is_using_emergency_heat = false;
	final static int extraDegree = 5;
	final static int avg_humidity = 74;
	final static int morning_humidity = 85;
	final static int afternoon_humidity = 61;
	final static String DATE_FORMAT = "yyyy-MM-dd HH-mm";
	static DateFormat df = new SimpleDateFormat(DATE_FORMAT);
//	final static String startingDate = "2015-09-25 11:49:35.721";
	static int currentMonth;

	static int away_temperature_high_f = 55;
	static double away_temperature_high_c = 12.5;
	static int away_temperature_low_f = 55;
	static double away_temperature_low_c = 12.5;

	static int target_temperature_f;
	static double target_temperature_c;
	static int target_temperature_high_f;
	static double target_temperature_high_c;
	static int target_temperature_low_f;
	static double target_temperature_low_c;

	static int ambient_temperature_f;
	static double ambient_temperature_c;
	static int humidity;
	static Date currentTS;
	static Date prevDate = null;

	final static String delimiter = ",";

	public static void main(String[] args) throws ParseException, IOException {
		//System.out.println(new Timestamp(new Date().getTime()));
		makeHistoricalData(600000);
		System.out.println("File is ready");
	}

	public static double convertFtoCdegree(int fDegree) {
		return ((fDegree - 32) * 5 / 9);
	}

	// public static String generateTS() {
	// Calendar cal = Calendar.getInstance();
	// int minute = cal.get(Calendar.MINUTE);
	// int flooredToQuadrileMinute = (minute / 15) * 15;
	// cal.add(Calendar.MINUTE, 15);
	// cal.set(Calendar.SECOND, 0);
	// cal.set(Calendar.MILLISECOND, 0);
	// return cal.getTime();
	// }

	public static Date getStartTS() throws ParseException {
		 Calendar cal = Calendar.getInstance();
		 cal.add(Calendar.MINUTE, 0);
		 cal.set(Calendar.SECOND, 0);
		 cal.set(Calendar.MILLISECOND, 0);
		 cal.set(Calendar.YEAR, 2014);
		 cal.set(Calendar.MONTH, 8);
		 cal.set(Calendar.DAY_OF_MONTH,24);
		 return cal.getTime();
	}

	public static Date getNextTS(Date prevDate) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(prevDate);
		cal.add(Calendar.MINUTE, 5);
		return cal.getTime();
	}
	
	public static int getMonth(Date currentDate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		return cal.get(Calendar.MONTH -1);
	}

	public static void makeHistoricalData(int records) throws IOException, ParseException {
		File file = new File("nest_history_data.csv");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		int i = 0;
		while (i < records) {
			StringBuffer sb = new StringBuffer();
			// MAKE 1 LINE OF RECORD
			if (prevDate == null){
				// no prevDate - use starting date
				//currentTS = getNextTS(new Timestamp(2014, 9, 24, 00, 00, 00, 00));
				currentTS = getStartTS();
				currentMonth = currentTS.getMonth()+1;
				//System.out.println(currentTS + "No previous date");
				
			} else {
				currentTS = getNextTS(prevDate);
				currentMonth = currentTS.getMonth()+1;
			}
			
			// TEMPERATURE MEASURE AT DEVICE
			//System.out.println(currentTS + " month: " + currentMonth);
			ambient_temperature_f = tempByMonth.get(currentMonth).intValue();
			ambient_temperature_c = convertFtoCdegree(ambient_temperature_f);
			target_temperature_f = ambient_temperature_f;
			target_temperature_c = ambient_temperature_c;

			// DESIRED TEMPERATURE
			if (tempModeByMonth.get(currentMonth).equals("heat")) {
				hvac_mode = "heat";
				target_temperature_high_f = ambient_temperature_f;
				target_temperature_high_c = ambient_temperature_c;
				target_temperature_low_f = target_temperature_high_f - extraDegree;
				target_temperature_low_c = target_temperature_high_c - extraDegree;

			} else {
				hvac_mode = "cool";
				target_temperature_low_f = ambient_temperature_f;
				target_temperature_low_c = ambient_temperature_c;
				target_temperature_high_f = target_temperature_low_f + extraDegree;
				target_temperature_high_c = target_temperature_low_c + extraDegree;
			}

			Random rand = new Random();
			if (currentTS.getHours() < 12) { // morning
				humidity = rand.nextInt((morning_humidity - avg_humidity) + 1) + avg_humidity;
			} else { // afternoon
				humidity = rand.nextInt((avg_humidity - afternoon_humidity) + 1) + afternoon_humidity;
			}
			// device_id, name, where_id, timestamp,ambient_temperature_f,ambient_temperature_c,target_temperature_f,target_temperature_c,target_temperature_high_f,target_temperature_high_c,target_temperature_low_f,target_temperature_low_c,
			// away_temperature_high_f, away_temperature_high_c, away_temperature_low_f, away_temperature_low_c,
			// humidity,can_cool,can_heat,has_leaf,hvac_mode,is_using_emergency_heat,has_fan
			// WRITE DATA
			sb.append(device_id);
			sb.append(delimiter);
			sb.append(name);
			sb.append(delimiter);
			sb.append(where_id);
			sb.append(delimiter);
			sb.append(currentTS);
			sb.append(delimiter);
			sb.append(ambient_temperature_f); // ambient_temperature_f
			sb.append(delimiter);
			sb.append(ambient_temperature_c); // ambient_temperature_c
			sb.append(delimiter);
			sb.append(target_temperature_f);
			sb.append(delimiter);
			sb.append(target_temperature_c);
			sb.append(delimiter);
			sb.append(target_temperature_high_f);
			sb.append(delimiter);
			sb.append(target_temperature_high_c);
			sb.append(delimiter);
			sb.append(target_temperature_low_f);
			sb.append(delimiter);
			sb.append(target_temperature_low_c);
			sb.append(delimiter);
			sb.append(away_temperature_high_f);
			sb.append(delimiter);
			sb.append(away_temperature_high_c);
			sb.append(delimiter);
			sb.append(away_temperature_low_f);
			sb.append(delimiter);
			sb.append(away_temperature_low_c);
			sb.append(humidity);
			sb.append(delimiter);
			sb.append(can_cool);
			sb.append(delimiter);
			sb.append(can_heat);
			sb.append(delimiter);
			sb.append(has_leaf);
			sb.append(delimiter);
			sb.append(hvac_mode); // heat/cool
			sb.append(delimiter);
			sb.append(is_using_emergency_heat);
			sb.append(delimiter);
			sb.append(has_fan);
			sb.append("\n");
			bw.write(sb.toString());
			i++;
			prevDate = currentTS;
		}
		bw.close();
	}
}

