package Borne_Informatise;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class test {
    public static void main (String [] arg){
        Borne b = new Borne("A111");
        CarteCredit c = new CarteCredit(20,"1234567890123456",2021,12);
        GregorianCalendar p = new GregorianCalendar();
        System.out.println(p.get(GregorianCalendar.MONTH));
        if (11  > p.get(Calendar.MONTH)) {
            System.out.println("Y");
        }


    }
}
