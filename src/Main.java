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
        int bestProfit=-99999;
        int bestCommodity=0;
        for(int c=0;c<COMMS;c++){
            int sum=0;
            for(int d=0;d<DAYS;d++){
                sum+=profitData[month][d][c];
            }
            if(sum>bestProfit){
                bestProfit=sum;
                bestCommodity=c;
            }
        }
        return commodities[bestCommodity] +" " +bestProfit;
    }

    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month >= MONTHS || day < 1 || day > 28) return -99999;
        int sum = 0;
        for (int c = 0; c < COMMS; c++) {
            int profit=profitData[month][day-1][c];
            sum+=profit;
        }
        return sum;
    }

    public static int commodityProfitInRange(String commodity, int fromDay, int toDay) {
        int ci = getCommIndex(commodity);
        if (ci ==-1){
            return -99999;
        }
        if(fromDay<1 || toDay>DAYS){
            return -99999;
        }
        if(fromDay>toDay){
            return -99999;
        }
        int total = 0;
        for (int m = 0; m < MONTHS; m++)
            for (int d = fromDay-1; d <= toDay-1; d++)
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
    public static String bestMonthForCommodity(String commodity) {
        int ci = getCommIndex(commodity);
        if (ci == -1) return "INVALID_COMMODITY";
        int [] monthTotals=new int[MONTHS];
        for(int m=0;m<MONTHS;m++){
            for(int d=0;d<DAYS;d++){
                monthTotals[m]+=profitData[m][d][ci];
            }
        }
        int highest=0;
        for(int m=1;m<MONTHS;m++){
            if(monthTotals[m]>monthTotals[highest]){
                highest=m;

            }
        }
        return months[highest];
    }
    public static int consecutiveLossDays(String commodity) {
        int ci = getCommIndex(commodity);
        if (ci == -1) return -1;
        int longest = 0;
        int current = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (profitData[m][d][ci] < 0) {
                    current++;
                    if (current > longest){
                        longest = current;
                    }
                } else current = 0;
            }
        }
        return longest;

    }
    public static int daysAboveThreshold(String commodity, int threshold) {
        int ci = getCommIndex(commodity);
        if (ci == -1) return -1;
        int count = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                int profit=profitData[m][d][ci];
                if(profit>threshold){
                    count++;
                }
            }
        }
        return count;
    }
    public static int biggestDailySwing(int month) {
        if (month < 0 || month >= MONTHS) return -99999;
        int max = 0;
        for (int d = 0; d < DAYS-1; d++) {
            int sum1 = 0;
            int sum2=0;
            for (int c = 0; c < COMMS; c++) {
                sum1 += profitData[month][d][c];
                sum2 += profitData[month][d+1][c];
            }
            int different = sum1 - sum2;
            if (different < 0) {
                different = -different;
            }
            if (different > max) {
                max = different;
            }
        }
        return max;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        int comm1=getCommIndex(c1);
        int comm2=getCommIndex(c2);
        if(comm1==-1 || comm2==-1) return "INVALID_COMMODITY";
        int total1=0;
        int total2=0;
        for(int m=0;m<MONTHS;m++){
            for(int d=0;d<DAYS;d++){
                total1+=profitData[m][d][comm1];
                total2+=profitData[m][d][comm2];
            }
        }
        if(total1==total2)return "Equal";
        if(total1>total2)return c1 +"is better by"+(total1-total2);
        return c2 + "is better by" +(total2-total1);

    }









    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}