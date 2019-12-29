package com.example.ggalgom_1;

import android.app.AppComponentFactory;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtil extends AppCompatActivity {

    // Methods related to Calcuating date
    /* Return each component's cycle by identifying id */
    public int getDday(int id)
    {
        int dday = 0;

        switch(id){
            case 1:
                //dday = 7;
                dday = 0;
                break;
            case 2:
                //dday = 7;
                dday = 0;
                break;
            case 3:
                //dday= 14;
                dday = 0;
                break;
            default:
                finish();
                break;
        }

        return dday;
    }

    /* Return today's date string data */
    public List<String> getCurrentDate()
    {
        List<String> dataList = new ArrayList<String>();

        //Calculating the current date
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        String weekDay = weekdayFormat.format(currentTime);
        String year = yearFormat.format(currentTime);
        String month = monthFormat.format(currentTime);
        String day = dayFormat.format(currentTime);

        Log.v("CurretDating","Brought weekday = " + weekDay + "\n"
                + "Brought year = " + year + "\n"
                + "Brought month = " + month + "\n"
                + "Brought day = " + day + "\n");

        /* Return results(date intofrmation) by list<String> */
        dataList.add(weekDay);
        dataList.add(year);
        dataList.add(month);
        dataList.add(day);

        return dataList;
    }

    /* Return the difference between today's date and input date */
    public int countdday(int myear, int mmonth, int mday)
    {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Calendar todaCal = Calendar.getInstance(); //오늘날자 가져오기
            Calendar ddayCal = Calendar.getInstance(); //오늘날자를 가져와 변경시킴

            mmonth -= 1; // 받아온날자에서 -1을 해줘야함.

            ddayCal.set(myear,mmonth,mday);// D-day의 날짜를 입력
            //Log.e("테스트",simpleDateFormat.format(todaCal.getTime()) + "");
            //Log.e("테스트",simpleDateFormat.format(ddayCal.getTime()) + "");

            long today = todaCal.getTimeInMillis()/86400000; //->(24 * 60 * 60 * 1000) 24시간 60분 60초 * (ms초->초 변환 1000)
            long dday = ddayCal.getTimeInMillis()/86400000;
            long count = today - dday; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
            return (int) count;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }
}
