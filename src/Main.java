// Main.java — Students version
import java.io.*;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};
    static int[][][]profitData=new int[MONTHS][DAYS][COMMS];
    static int getCommIndex(String comm){
        for(int i=0;i<COMMS;i++){
            if(commodities[i].equals(comm)){
                return i;
            }
        }
        return -1;
    }
    

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        for(int m=0;m<MONTHS;m++){
            for(int d=0;d<DAYS;d++){
                for(int c=0;c<COMMS;c++){
                    profitData[m][d][c]=0;

                }
            }

        }
        for(int m=0;m<MONTHS;m++){
            Scanner sc=null;
            try {
                sc = new Scanner(new File("src/Data_Files/" + months[m] + ".txt"));
            } catch (Exception e) {
                continue;

            }
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(",");

                if (parts.length != 3) continue;

                int day;
                int profit;

                try {
                    day = Integer.parseInt(parts[0].trim());
                    profit = Integer.parseInt(parts[2].trim());
                } catch (Exception e) {
                    continue;
                }

                if (day < 1 || day > DAYS) continue;

                String comm = parts[1].trim();
                int cIndex = getCommIndex(comm);

                if (cIndex == -1) continue;

                profitData[m][day - 1][cIndex] = profit;
            }

            if(sc!=null) sc.close();
        }
    }








    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if(month<0 || month>=MONTHS) return "INVALID_MONTH";
        int[] list = new int[COMMS];
        for (int d = 0; d < DAYS; d++)
            for (int c = 0; c < COMMS; c++)
                list[c] += profitData[month][d][c];
        int most=0;
        for(int c=1;c<COMMS;c++){
            if(list[c]>list[most]){
                most=c;
            }
        }
        return commodities[most] + " " + list[most];
    }

    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month >= MONTHS || day < 1 || day > 28) return -99999;
        int sum = 0;
        for (int c = 0; c < COMMS; c++) {
            sum += profitData[month][day - 1][c];
        }
        return sum;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        int ci = getCommIndex(commodity);
        if (ci == -1 || from < 1 || to > 28 || from > to) return -99999;
        int total = 0;
        for (int m = 0; m < MONTHS; m++)
            for (int d = from-1; d <= to-1; d++)
                total += profitData[m][d][ci];
        return total;
    }
    public static int bestDayOfMonth(int month) {
        if (month < 0 || month >= MONTHS) return -1;
        int bestDay = 1;
        int bestProfit = -999999;
        for (int d = 0; d < DAYS; d++) {
            int sum = 0;
            for (int c = 0; c < COMMS; c++)
                sum += profitData[month][d][c];
            if (sum > bestProfit) {
                bestProfit = sum;
                bestDay = d + 1;
            }
        }
        return bestDay;
    }






    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}