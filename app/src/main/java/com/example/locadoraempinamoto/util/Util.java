package util;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public abstract class Util {
    public static String dataFormat(String textData) {
        String value = textData;
        if (textData.length() < 11) {
            value = value.replaceAll("[^0-9]", "");
            value = value.replaceFirst("(\\d{2})(\\d{2})", "$1/$2");
            value = value.replaceFirst("(\\d{2})\\/(\\d{2})(\\d{4})", "$1/$2/$3");
        }else{
            value = value.substring(0,10);
            value = value.replaceFirst("(\\d{2})\\/(\\d{2})(\\d{4})", "$1/$2/$3");
        }
        return value;
    }

    public static String dataFormatSQL(String textData) {
        String value[] = textData.split("/");
        return value[2]+"-"+value[1]+"-"+value[0];
    }

    public static String dataFormatSQLReverse(String textData) {
        String value[] = textData.split("-");
        return value[2]+"/"+value[1]+"/"+value[0];
    }

    public static int calIdade(String dateNasc){
        int age = 0; 
        String da[] = dateNasc.split("/");
        age = (int) ChronoUnit.YEARS.between(LocalDate.of(Integer.parseInt(da[2]), 
        Integer.parseInt(da[1]), Integer.parseInt(da[0])), LocalDate.now());
        return age;
    }

    public static long diffDates(String DT_INICIO, String DT_FIM){
        String d1[] = DT_INICIO.split("/");
        String d2[] = DT_FIM.split("/");
        LocalDate from = LocalDate.of(Integer.parseInt(d1[2]),Integer.parseInt(d1[1]),Integer.parseInt(d1[0]));
        LocalDate to = LocalDate.of(Integer.parseInt(d2[2]),Integer.parseInt(d2[1]),Integer.parseInt(d2[0]));
        
        long result = ChronoUnit.DAYS.between(from, to);
        return result + 1;
    }
}
