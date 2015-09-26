package demo;

import com.google.api.client.util.DateTime;

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

public class GenerateLightHistoricalData {

    static HashMap<String, Boolean> lightStatusByDayMode;

    static {
        lightStatusByDayMode = new HashMap<String, Boolean>();
        lightStatusByDayMode.put("00", false);
        lightStatusByDayMode.put("01", false);
        lightStatusByDayMode.put("02", false);
        lightStatusByDayMode.put("03", false);
        lightStatusByDayMode.put("04", false);
        lightStatusByDayMode.put("05", false);
        lightStatusByDayMode.put("06", false);
        lightStatusByDayMode.put("07", false);
        lightStatusByDayMode.put("08", false);
        lightStatusByDayMode.put("09", false);
        lightStatusByDayMode.put("10", false);
        lightStatusByDayMode.put("11", false);
        lightStatusByDayMode.put("12", false);
        lightStatusByDayMode.put("13", false);
        lightStatusByDayMode.put("14", false);
        lightStatusByDayMode.put("15", false);
        lightStatusByDayMode.put("16", false);
        lightStatusByDayMode.put("17", false);
        lightStatusByDayMode.put("18", false);
        lightStatusByDayMode.put("19", true);
        lightStatusByDayMode.put("20", true);
        lightStatusByDayMode.put("21", true);
        lightStatusByDayMode.put("22", true);
        lightStatusByDayMode.put("23", true);
        lightStatusByDayMode.put("24", true);
    }

    static HashMap<String, String> lightByDayMode;

    static {
        lightByDayMode = new HashMap<String, String>();
        lightByDayMode.put("0", "sleep");
        lightByDayMode.put("1", "sleep");
        lightByDayMode.put("2", "sleep");
        lightByDayMode.put("3", "sleep");
        lightByDayMode.put("4", "sleep");
        lightByDayMode.put("5", "sleep");
        lightByDayMode.put("6", "sleep");
        lightByDayMode.put("7", "light");
        lightByDayMode.put("8", "light");
        lightByDayMode.put("9", "light");
        lightByDayMode.put("10", "light");
        lightByDayMode.put("11", "light");
        lightByDayMode.put("12", "light");
        lightByDayMode.put("13", "light");
        lightByDayMode.put("14", "light");
        lightByDayMode.put("15", "light");
        lightByDayMode.put("16", "light");
        lightByDayMode.put("17", "light");
        lightByDayMode.put("18", "light");
        lightByDayMode.put("19", "dark");
        lightByDayMode.put("20", "dark");
        lightByDayMode.put("21", "dark");
        lightByDayMode.put("22", "read");
        lightByDayMode.put("23", "read");
        lightByDayMode.put("24", "sleep");
    }

    final static int id = 1;
    final static String name = "LivingRoomLight";
    final static String modelid = "LCT001";
    final static String DATE_FORMAT = "yyyy-MM-dd HH-mm";
    static DateFormat df = new SimpleDateFormat(DATE_FORMAT);
    static int currentMonth;
    static String currentTime;
    static boolean on;
    static int bri ;
    static int hue;
    static int sat;
    static String last_use_date;
    static String  created_date = "Wed Sep 24 12:18:00 PDT 2013";
    static String user_mode;
    static boolean reachable = true;

    static String type = "Extended color light";

    static Date currentTS;
    static Date prevDate = null;

    final static String delimiter = ",";

    public static void main(String[] args) throws ParseException, IOException {
        makeLightHistoricalData(600000);
        System.out.println("File is ready");
    }

    public static Date getStartTS() throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.YEAR, 2014);
        cal.set(Calendar.MONTH, 3);
        cal.set(Calendar.DAY_OF_MONTH, 1);
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
        return cal.get(Calendar.MONTH - 1);
    }

    public static int getTime(Date currentTime){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentTime);
        return cal.get(Calendar.HOUR);
    }


    public static void makeLightHistoricalData(int records) throws IOException, ParseException {
        File file = new File("philip_history_data.csv");
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
                //currentTS = getNextTS(new Timestamp(2014, 4, 01, 00, 00, 00, 00));
                currentTS = getStartTS();
                currentMonth = currentTS.getMonth()+1;
                //currentTime = currentTS.getHours();
                currentTime = Integer.toString(currentTS.getHours());
                System.out.println(currentTime);
                System.out.println(lightByDayMode.get(currentTime));

            } else {
                currentTS = getNextTS(prevDate);
                currentMonth = currentTS.getMonth()+1;
                currentTime = Integer.toString(currentTS.getHours());
                System.out.println(currentTime);
                System.out.println(lightByDayMode.get(currentTime));
            }

            // DESIRED RESULTS
            if (lightByDayMode.get(currentTime).equals("sleep")) {
                user_mode = "sleep";
                on = false;
                bri = 0;
                sat = 0;
                hue = 0;
                last_use_date = df.format(currentTS);
            } else if (lightByDayMode.get(currentTime).equals("light")) {
                user_mode = "light";
                on = false;
                bri = 0;
                sat = 0;
                hue = 0;
                last_use_date = df.format(currentTS);
            }else if (lightByDayMode.get(currentTime).equals("dark")) {
                user_mode = "dark";
                on = true;
                bri = 255;
                sat = 254;
                hue = 63136;
                last_use_date = df.format(currentTS);
            } else if (lightByDayMode.get(currentTime).equals("read")) {
                user_mode = "read";
                on = true;
                bri = 154;
                sat = 152;
                hue = 13243;
                last_use_date = df.format(currentTS);
            }


            // WRITE DATA
            sb.append(id);
            sb.append(delimiter);
            sb.append(name);
            sb.append(delimiter);
            sb.append(currentTS);
            sb.append(delimiter);
            sb.append(modelid);
            sb.append(delimiter);
            sb.append(on);
            sb.append(delimiter);
            sb.append(bri);
            sb.append(delimiter);
            sb.append(hue);
            sb.append(delimiter);
            sb.append(sat);
            sb.append(delimiter);
            sb.append(reachable);
            sb.append(delimiter);
            sb.append(type);
            sb.append(delimiter);
            sb.append(last_use_date);
            sb.append(delimiter);
            sb.append(created_date);
            sb.append(delimiter);
            sb.append(user_mode);
            sb.append("\n");
            bw.write(sb.toString());
            i++;
            prevDate = currentTS;
        }
        bw.close();
    }
}
