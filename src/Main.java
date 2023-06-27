import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    private static TreeMap<Date, Double> getDateDoubleTreeMap(double[] transfers, String[] transfers_dates) {
        TreeMap<Date, Double> transfersDatesMap = new TreeMap<>(Date::compareTo);

        for (int i = 0; i < transfers_dates.length; i++) {

            try {
                Date key = new SimpleDateFormat("MMM-yy").parse(transfers_dates[i].substring(3));
                double value = transfers[i];
                if (transfersDatesMap.containsKey(key)) {
                    value += transfersDatesMap.get(key);
                    transfersDatesMap.put(key, value);
                } else {
                    transfersDatesMap.put(key, value);
                }
            } catch (ParseException e) {
                System.out.println(e.getStackTrace());
            }
        }
        return transfersDatesMap;
    }

    private static long returnLastSixMonthAvg(double[] transfers, String[] transfers_dates) {
        TreeMap<Date, Double> transfersDatesMap = getDateDoubleTreeMap(transfers, transfers_dates);

        for (Map.Entry<Date, Double> entry : transfersDatesMap.entrySet()) {
            Date key = entry.getKey();
            double value = entry.getValue();
            System.out.println("Key: " + key + " value: " + value);
        }

        int counter = 0;
        double sum = 0;
        int lastMonth = 6;

        NavigableSet<Date> descendingKeys = transfersDatesMap.descendingKeySet();

        for (Date key : descendingKeys) {

            if (transfersDatesMap.get(key) >= 300) {
                sum += transfersDatesMap.get(key);
            }

            counter++;


            if (counter == lastMonth) {
                break;
            }

        }

        long result = Math.round(sum / lastMonth);

        return result;
    }

    private static void getMaxNumberAndMonth(double[] transfers, String[] transfers_dates) {
        TreeMap<Date, Double> transfersDatesMap = getDateDoubleTreeMap(transfers, transfers_dates);

        double maxTransfer = 0;
        Date maxDate = new Date();
        Calendar cal = Calendar.getInstance();

        for (Map.Entry<Date, Double> entry : transfersDatesMap.entrySet()) {
            Date key = entry.getKey();
            double value = entry.getValue();

            if (maxTransfer < value) {
                maxTransfer = value;
                maxDate = key;
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
        String month = dateFormat.format(maxDate);
        dateFormat = new SimpleDateFormat("YYYY");
        String year = dateFormat.format(maxDate);

        System.out.println("Maximum amount of transfers in one month is : " + maxTransfer);
        System.out.println("All the transfers were done in : " + month + " " + year);
    }

    public static void main(String[] args) {

        double[] transfers = {6057.40, 8836.62, 9764.25, 7497.91, 4357.27, 720.01, 6172.99, 3955.23, 6139.59,
                6789.37, 3784.11, 8038.22, 5890.01, 6968.98, 5482.94, 262.01, 4106.93, 9971.85,
                7207.67, 4488.62};
        String[] transfers_dates = {"13-Jul-05", "15-Oct-22", "15-Apr-22", "15-Jan-22", "13-Jul-22", "13-Jul-22",
                "15-Mar-23", "15-Feb-23", "15-Jan-23", "15-Jul-21", "15-Apr-23", "15-Dec-20",
                "15-May-22", "13-Jul-22", "15-Jun-23", "13-Jul-22", "15-Mar-22", "03-May-23",
                "13-Jul-22", "13-Jul-22"};

        System.out.println("Last six month's average transfers from client's card is: " + returnLastSixMonthAvg(transfers, transfers_dates));
        getMaxNumberAndMonth(transfers, transfers_dates);

    }
}

