package com.example.ggalgom_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GGALGOM_Activity extends AppCompatActivity {

    final int tabmenuimg[] = {R.drawable.aircon, R.drawable.bed, R.drawable.refrigerator,
            R.drawable.teddybear, R.drawable.trashcan};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ggalgom_);

        //tab menu
        /*
        final int tabmenuimg[] = {R.drawable.aircon, R.drawable.bed, R.drawable.refrigerator,
                R.drawable.teddybear, R.drawable.trashcan};
                */

        GalleryAdapter tab_adapter = new GalleryAdapter(
                getApplicationContext(),
                R.layout.tabrow,
                tabmenuimg);

        Gallery g_tab = (Gallery)findViewById(R.id.tabmenu);
        g_tab.setAdapter(tab_adapter);

        //final ImageView iv = (ImageView)findViewById(R.id.imageView1);

        g_tab.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) { // 선택되었을 때 콜백메서드
                //Toast.makeText(GGALGOM_Activity.this, "wow", Toast.LENGTH_SHORT).show();

                LinearLayout new_layout = new LinearLayout(getApplicationContext());

                LinearLayout.LayoutParams llayout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);

                //llayout_params.gravity = Gravity.CENTER;

                new_layout.setLayoutParams(llayout_params);

                final int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());

                final int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());


                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width /* layout_width */,
                        /*LinearLayout.LayoutParams.WRAP_CONTENT*/height /* layout_height */ /*1f*/ /* layout_weight */);

                layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

                ImageView iv = new ImageView(getApplicationContext());  // 새로 추가할 imageView 생성

                iv.setImageResource(tabmenuimg[position]);  // imageView에 내용 추가

                iv.setLayoutParams(layoutParams);  // imageView layout 설정

                ((ConstraintLayout) findViewById(R.id.room)).addView(new_layout); // 기존 linearLayout에 imageView 추가

                new_layout.addView(iv);




                //R.drawable.view


                //iv.setX(iv.getWidth()/2);

                //iv.setY(-iv.getHeight()/2);

                //iv.setg

                return false;
            }

        });

    }



    public void onTabmenuButtonClicked(View v) {
        if (findViewById(R.id.tabmenu).getVisibility() == View.VISIBLE)
            findViewById(R.id.tabmenu).setVisibility(View.INVISIBLE); // or GONE

        else
            findViewById(R.id.tabmenu).setVisibility(View.VISIBLE);
    };

}
