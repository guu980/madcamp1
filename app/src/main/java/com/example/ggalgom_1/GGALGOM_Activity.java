package com.example.ggalgom_1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class GGALGOM_Activity extends AppCompatActivity {

    /* ---------------------------------Alarm System-------------------------------------*/
    Context context_for_popup = this;

    static SharedPreferences sharePref = null;
    static SharedPreferences.Editor editor = null;

    /*----------------------------------Item Adding System-------------------------------------*/
    HashMap<String, Integer> added_item_idmap = new HashMap<String, Integer>();

    private class DdayThread extends Thread{
        private static final String TAG = "DdayThread";
        public DdayThread(){
            //Initializing
        }

        public void run() {
            //work to do
            while(true) {
                try{
                    Thread.sleep(10000 ); // Check by 1 hour

                    /* Notice Ddaythread started */
                    //createNotification(R.mipmap.ic_launcher ,"Test", "Sub thread is started", 1);

                    int[] notification_id = {0};

                    /* Iterate all components (basic component = 1:floor, 2:window, 3:restroom) */
                    for (int i = 1; i < 4; i++) {
                        String notification_title = null;
                        String notification_context = null;

                        /* Set notification texts */
                        switch (i){
                            case 1:
                                notification_title = "Floor";
                                notification_context = "It's time to Clean Floor!";
                                break;
                            case 2:
                                notification_title = "Window";
                                notification_context = "It's time to Clean Window!";
                                break;
                            case 3:
                                notification_title = "Bathroom";
                                notification_context = "It's time to Clean Bathroom!";
                                break;
                            default:
                                finish();
                                break;
                        }

                        /* Get SharedPreferences handler, bring each component's date data */
                        SharedPreferences sharePref = getSharedPreferences("SHARE_PREF", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharePref.edit();

                        List<String> each_day_datalist = load_data(sharePref, i);
                        if(each_day_datalist.get(0) == "")
                        {
                            continue;
                        }

                        /* For debugging */
                        /*
                        Log.v("Thread_running", "myear = " + each_day_datalist.get(1) + "\n"
                                + "mmonth = "+ each_day_datalist.get(2) + "\n"
                                + "mday = "+ each_day_datalist.get(3) + "\n");
                         */

                        int myear = Integer.parseInt(each_day_datalist.get(1));
                        int mmonth = Integer.parseInt(each_day_datalist.get(2));;
                        int mday = Integer.parseInt(each_day_datalist.get(3));;

                        /* For debugging */
                        /*
                        Log.v("Comparing_date", "Difference value is : " + countdday(myear, mmonth, mday) + "\n"
                                + "Set Cycle is : " + getDday(i) + "\n");
                         */

                        if(countdday(myear, mmonth, mday) == getDday(i))
                        {
                            /* Manipulate the notification id not to be overlapped and be overflowed */
                            if(notification_id[0] > 100)
                            {
                                notification_id[0] = 0;
                            }
                            notification_id[0]++;

                            /* Create push notification */
                            createNotification(R.mipmap.ic_launcher ,notification_title, notification_context, notification_id[0]);

                            /* Change the component's next schedule(changing recently cleaned date data) */
                            List<String> dateData =  getCurrentDate();
                            saveData(editor,dateData.get(0), dateData.get(1), dateData.get(2), dateData.get(3), i);
                        }
                    }
                } catch (Exception e) { //왜 여기 interrupted exception 으로 처리가 안되는가 나중에 체크
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* Alarm System */
        /* Run Dday thread on the background */
        DdayThread thread = new DdayThread();
        thread.start();

        /* Visualize the screen */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ggalgom_);

        /* ------------------------------------ Item Adding System -------------------------------------------- */

        final int tabmenuimg[] = {R.drawable.aircon, R.drawable.bed, R.drawable.refrigerator,
                R.drawable.teddybear, R.drawable.trashcan};

        GalleryAdapter tab_adapter = new GalleryAdapter(
                getApplicationContext(),
                R.layout.tabrow,
                tabmenuimg);

        Gallery g_tab = (Gallery)findViewById(R.id.tabmenu);
        g_tab.setAdapter(tab_adapter);

        g_tab.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            { // 선택되었을 때 콜백메서드
                String selected_item = null;

                switch(position)
                {
                    case 0:
                        selected_item = "Aircon";
                        break;
                    case 1:
                        selected_item = "Bed";
                        break;
                    case 2:
                        selected_item = "Refrigerator";
                        break;
                    case 3:
                        selected_item = "Teddybear";
                        break;
                    case 4:
                        selected_item = "Trashcan";
                        break;
                    default:
                        finish();
                        break;
                }

                //Toast messaging to notice
                Toast.makeText(GGALGOM_Activity.this, selected_item + " is added!", Toast.LENGTH_SHORT).show();

                // Make new Image View which should be included in created LinearLayout
                ImageView iv = new ImageView(getApplicationContext());

                // Set Image View's parameters. Image View will have fixed size and should be placed in the center of LinearLayout(same location with room) by gravity property in above
                final int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
                final int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width ,height);

                iv.setImageResource(tabmenuimg[position]);  // imageView에 내용 추가
                iv.setLayoutParams(layoutParams);  // imageView layout 설정
                int new_item_id = View.generateViewId();
                iv.setId(new_item_id); // set imageView's id
                added_item_idmap.put(selected_item, new_item_id); //add id information

                ((ConstraintLayout) findViewById(R.id.room)).addView(iv);

                // Set ConstraintSet to place in center
                ConstraintSet constraintSet = new ConstraintSet();
                ConstraintLayout room_constraintLayout = (ConstraintLayout)findViewById(R.id.room);
                constraintSet.clone(room_constraintLayout);
                constraintSet.connect(iv.getId(), ConstraintSet.END, R.id.room, ConstraintSet.END,0);
                constraintSet.connect(iv.getId(), ConstraintSet.START, R.id.room, ConstraintSet.START,0);
                constraintSet.connect(iv.getId(), ConstraintSet.TOP, R.id.room, ConstraintSet.TOP,0);
                constraintSet.connect(iv.getId(), ConstraintSet.BOTTOM, R.id.room, ConstraintSet.BOTTOM,0);
                constraintSet.applyTo(room_constraintLayout);

                return false;
            }

        });

        /* ------------------------------------ Alarm System -------------------------------------------- */

        /* Set SharedPreference */
        sharePref = getSharedPreferences("SHARE_PREF", MODE_PRIVATE);
        editor = sharePref.edit();

        /* Floor onClick listener (id = 1) */
        ConstraintLayout layout_floor = (ConstraintLayout) findViewById(R.id.floorLayout);
        layout_floor.setOnClickListener(new ConstraintLayout.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                /* Save Current Date into SharedPreference */
                List<String> dateData =  getCurrentDate();
                saveData(editor,dateData.get(0), dateData.get(1), dateData.get(2), dateData.get(3), 1);

                /* Show popup to notice alarm is set */
                new AlertDialog.Builder(context_for_popup).setTitle("Notice").setMessage("Floor Cleaning Alarm is set").setNeutralButton("Close", new DialogInterface.OnClickListener()
                {public void onClick(DialogInterface dlg, int sumthin) {} }).show();
            }
        });

        /* Window onClick listener (id = 2) */
        ImageView imageView_window = (ImageView) findViewById(R.id.window) ;
        imageView_window.setOnClickListener(new ImageView.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                /* Save Current Date into SharedPreference */
                List<String> dateData =  getCurrentDate();
                saveData(editor,dateData.get(0), dateData.get(1), dateData.get(2), dateData.get(3), 2);

                /* Save Current Date into SharedPreference */
                new AlertDialog.Builder(context_for_popup).setTitle("Notice").setMessage("Window Cleaning Alarm is set").setNeutralButton("Close", new DialogInterface.OnClickListener()
                {public void onClick(DialogInterface dlg, int sumthin) {} }).show();
            }
        });

        /* Window onClick listener (id = 3) */
        ImageView imageView_bathroom = (ImageView) findViewById(R.id.bathroom) ;
        imageView_bathroom.setOnClickListener(new ImageView.OnClickListener()
        {
            @Override
            public void onClick(View view){
                /* Save Current Date into SharedPreference */
                List<String> dateData =  getCurrentDate();
                saveData(editor,dateData.get(0), dateData.get(1), dateData.get(2), dateData.get(3), 3);

                /* Save Current Date into SharedPreference */
                new AlertDialog.Builder(context_for_popup).setTitle("Notice").setMessage("Bathroom Cleaning Alarm is set").setNeutralButton("Close", new DialogInterface.OnClickListener()
                {public void onClick(DialogInterface dlg, int sumthin) {} }).show();
            }
        });

        /* Reset button onClick listner */
        Button resetButton = (Button) findViewById(R.id.resetbutton);
        resetButton.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor.clear();
                editor.commit();
            }
        });

        /* for debugging */
        /*
        Button debugging_button = (Button) findViewById(R.id.debugButton) ;
        debugging_button.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                List<String> window_datalist = load_data(sharePref, 2);
                //List<String> window_datalist = sharedPrefer_util.load_data(2);
            }
        });
        */
    }


    /* ------------------------------------------------ Item Adding System --------------------------------------------------- */

    /* Item list on/off button onClick handelr */
    public void onTabmenuButtonClicked(View v) {
        if (findViewById(R.id.tabmenu).getVisibility() == View.VISIBLE)
            findViewById(R.id.tabmenu).setVisibility(View.INVISIBLE); // or GONE

        else
            findViewById(R.id.tabmenu).setVisibility(View.VISIBLE);
    };

    /* ------------------------------------------------ Alarm System -------------------------------------------------- */
    // Methods related to Calcuating date
    /* Return each component's cycle by identifying id */
    private int getDday(int id)
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
    private List<String> getCurrentDate()
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


    // Methods realted to SharedPreferences
    /* Save each component's date data in SharedPreferences */
    public void saveData(SharedPreferences.Editor editor, String weekday, String year, String month, String day, int id)
    {
        String first = null;

        /* Identify the component by id */
        switch(id){
            case 1:
                first = "floor";
                break;
            case 2:
                first = "window";
                break;
            case 3:
                first = "bathroom";
                break;
            default:
                finish();
                break;
        }

        /* for debugging
        Log.v("Saving","Brought weekday = " + weekday + "\n"
                +"Weekday key = " + first + "_wd" + "\n"
                + "Brought year = " + year + "\n"
                + "Year key = " + first + "_y" + "\n"
                + "Brought month = " + month + "\n"
                + "Month key = " + first + "_m" + "\n"
                + "Brought day = " + day + "\n"
                + "Day key = " + first + "_d" + "\n");
         */

        //put weekday
        editor.putString(first + "_wd", weekday);
        
        //put year
        editor.putString(first + "_y", year);

        //put month
        editor.putString(first + "_m", month);

        //put day
        editor.putString(first + "_d", day);

        //apply modified data
        editor.apply();
    }

    /* Remove each component's date data in SharedPreferences */
    public void deleteData(SharedPreferences.Editor editor, int id)
    {
        String first = null;

        /* Identify the component by id */
        switch(id){
            case 1:
                first = "floor";
                break;
            case 2:
                first = "window";
                break;
            case 3:
                first = "bathroom";
                break;
            default:
                finish();
                break;
        }

        //put weekday
        editor.remove(first + "_wd");

        //put year
        editor.remove(first + "_y");

        //put month
        editor.remove(first + "_m");

        //put day
        editor.remove(first + "_d");

        //apply modified data
        editor.commit();
    }

    /* Load component's date data from SharedPreferences */
    public List<String> load_data(SharedPreferences sharePref, int id)
    {
        String first = null;

        /* Identify the component by id */
        switch(id){
            case 1:
                first = "floor";
                break;
            case 2:
                first = "window";
                break;
            case 3:
                first = "bathroom";
                break;
            default:
                finish();
                break;
        }

        /* for debugging
        Log.v("Loading","Brought weekday = " + sharePref.getString(first+"_wd","" ) + "\n"
                +"Weekday key = " + first+"_wd" + "\n"
                +"Is weekday key exists? = " + sharePref.contains(first+"_wd") + "\n"
                + "Brought year = " + sharePref.getString(first+"_y","" ) + "\n"
                + "Year key = " + first+"_y" + "\n"
                +"Is year key exists? = " + sharePref.contains(first+"_y") + "\n"
                + "Brought month = " + sharePref.getString(first+"_m","" ) + "\n"
                + "Month key = " + first+"_m" + "\n"
                +"Is month key exists? = " + sharePref.contains(first+"_m") + "\n"
                + "Brought day = " + sharePref.getString(first+"_d","" ) + "\n"
                + "Day key = " + first+"_d"+ "\n"
                +"Is month key exists? = " + sharePref.contains(first+"_d") + "\n");
                */

        List<String> dataList= new ArrayList<String>();
        dataList.add(sharePref.getString(first+"_wd","" ));
        dataList.add(sharePref.getString(first+"_y","" ));
        dataList.add(sharePref.getString(first+"_m","" ));
        dataList.add(sharePref.getString(first+"_d","" ));

        return dataList;
    }

    // Methods related to notification
    /* Make notification */
    private void createNotification(int alarm_icon, String alarm_title, String alarm_context, int id)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        //R.mipmap.ic_launcher
        builder.setSmallIcon(alarm_icon);
        builder.setContentTitle(alarm_title);
        builder.setContentText(alarm_context);

        //builder.setColor(Color.RED);
        // 사용자가 탭을 클릭하면 자동 제거
        builder.setAutoCancel(true);

        // 알림 표시
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

        // id값은
        // 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(/*1*/id, builder.build());
    }

    /* Remvoe notification */
    private void removeNotification()
    {
        // Notification 제거
        NotificationManagerCompat.from(this).cancel(1);
    }
}
