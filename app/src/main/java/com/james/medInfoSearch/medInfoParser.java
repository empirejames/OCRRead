package com.james.medInfoSearch;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class medInfoParser {
    String TAG = medInfoParser.class.getSimpleName();
    String url = "http://goodinfo.tw/StockInfo/StockDividendScheduleList.asp?MARKET_CAT=%E5%85%A8%E9%83%A8&YEAR=%E5%8D%B3%E5%B0%87%E9%99%A4%E6%AC%8A%E6%81%AF&INDUSTRY_CAT=%E5%85%A8%E9%83%A8";
    String urlForGuHi = "http://stock.wespai.com/p/5625";
    String urlForEPS = "http://stock.wespai.com/p/7733";
    String urlForPresent = "http://stock.wespai.com/stock";
    String urlForGuli = "http://stock.wespai.com/tenrate#";

    private HandlerThread mThread;
    private Handler mThreadHandler;
    ArrayList<String> stockNumberList = new ArrayList<String>();
    ArrayList<String> historyGuHi = new ArrayList<String>();
    ArrayList<String> historyEPS = new ArrayList<String>();
    ArrayList<String> historyPresent = new ArrayList<String>();
    ArrayList<String> historyGuli = new ArrayList<String>();
    String[] name = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    int total ;
    public void start() {
        mThread = new HandlerThread("jsoup");
        mThread.start();
        mThreadHandler = new Handler(mThread.getLooper());
        mThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                getMedInfo(name[0]);
//                for(int i=0; i<name.length;i++){
//                    getMedInfo(name[i]);
//                }
                Log.e(TAG,"total : " + total);

            }
        });

    }

    public ArrayList<String> getMedInfo(String num) {
        String url = "http://web-reg-server.803.org.tw:8090/med_query_easy.asp?idx=" +num;
        try {
            Document doc = Jsoup.connect(url).get();
            Element table = doc.select("table").get(0);
            Elements rows = table.select("tr");
            Pattern pattern = Pattern.compile("^(\\d+.*)");
            for(int i =6; i<7;i++){
                Element row = rows.get(i);
                Elements cols = row.select("td");
                Elements links = cols.select("a[href]");
                Matcher matcher = pattern.matcher(cols.text());
                if (matcher.matches()) {
                    Log.e(TAG, cols.text() + "   " + links.attr("href"));
                    getMedDetail(links.attr("href"));
                    total +=1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stockNumberList;
    }


    public void getMedDetail(String url){
        String [] infoSplit =  url.split("\\?");
        //Log.e(TAG,infoSplit[1]);
        //String detailURL = "http://web-reg-server.803.org.tw:8090/med_info_detail.asp?" +infoSplit[1];
        String detailURL = "http://web-reg-server.803.org.tw:8090/med_info_detail.asp?code=RO0A312";
        //Log.e(TAG,detailURL);
        try {
            Document doc = Jsoup.connect(detailURL).get();
            Element table = doc.select("table[id=tblInfo1 ]").first();
            Elements rows = table.select("tr");
            for(int i =2; i<rows.size();i++){
                Element row = rows.get(i);
                Elements cols_th = row.select("th");
                Elements cols_td = row.select("td");
                Log.e(TAG,  i + "  -  " + cols_th.text() + "  " + cols_td.text() );
            }


        }catch(Exception e){

        }





    }

    public String transferDate(String date) {
        String result = "";
        int y = date.indexOf("年");
        int m = date.indexOf("月");
        int d = date.indexOf("日");
        if (y == 3 && m == 6 && d == 9) {
            String year = date.substring(0, y);
            int a = Integer.parseInt(year) + 1911;
            String mouth = date.substring(y + 1, m);
            String day = date.substring(m + 1, d);
            result = a + mouth + day;
        }
        return result;
    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public ArrayList<String> getUrlInfo(String url) {
        ArrayList<String> temp = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Element table = doc.select("table[id=example]").first();
            Elements rows = table.select("tr");
            Elements td;
            for (int i = 0; i < rows.size(); i++) {
                td = rows.get(i).children();

                for (int j = 0; j < td.size(); j++) {
                    if (j % 22 == 0) {
                        if (isNumeric(td.get(j).text())) {
                            Log.e(TAG, td.text() + " :: " + td.size());
                            temp.add(td.text());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
