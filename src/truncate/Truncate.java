package truncate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Truncate {
    public static double truncateM1(double value, int decimalPlaces) {
        String s = String.format("%." + decimalPlaces + "f", value);
        return Double.parseDouble(s);
    }

    public static double truncateM2(double value, int decimalPlaces) {
        String s = String.format("%0" + decimalPlaces + "d", 0).replace('0', '#');
        DecimalFormat df = new DecimalFormat("#." + s);
        return Double.parseDouble(df.format(value));
    }

    public static double truncateM3(double value, int decimalPlaces) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(decimalPlaces);
        nf.setRoundingMode(RoundingMode.DOWN);
        return Double.parseDouble(nf.format(value));
    }

    public static double truncateM4(double value, int decimalPlaces) {
        double mValue = Math.pow(10, decimalPlaces);
        return (value > 0 ? Math.floor(value * mValue) : Math.ceil(value * mValue)) / mValue;
    }

    public static double truncateM5(double value, int decimalPlaces) {
        BigDecimal bd = new BigDecimal(value).setScale(decimalPlaces, RoundingMode.DOWN);
        return bd.doubleValue();
    }

    public static void main(String[] args) {
        int decimalPlaces = 2;
        double value = 1.123456789;

        System.out.println(truncateM1(value, decimalPlaces));
        System.out.println(truncateM2(value, decimalPlaces));
        System.out.println(truncateM3(value, decimalPlaces));
        System.out.println(truncateM4(value, decimalPlaces));
        System.out.println(truncateM5(value, decimalPlaces));
    }
}