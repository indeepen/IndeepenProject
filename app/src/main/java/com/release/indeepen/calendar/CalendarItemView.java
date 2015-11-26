package com.release.indeepen.calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.release.indeepen.R;
import com.release.indeepen.management.dateManager.DateManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

public class CalendarItemView extends LinearLayout {

    TextView numberView;
    CalendarItem mItem;
    public final static int NUMBER_COLOR = Color.BLACK;
    public final static int SAT_COLOR = Color.BLACK;
    public final static int SUN_COLOR = Color.BLACK;
    public final static float IN_MONTH_TEXT_SIZE_SP = 12.0f;

    public final static int OUT_MONTH_TEXT_COLOR = Color.WHITE;
    public final static float OUT_MONTH_TEXT_SIZE_SP = 11.0f;

    public CalendarItemView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        LayoutInflater.from(context).inflate(R.layout.calendar_item, this);
        numberView = (TextView) findViewById(R.id.number);
    }

    public void setData(CalendarItem item) {
        mItem = item;
        float textsize = IN_MONTH_TEXT_SIZE_SP;
        int textColor = NUMBER_COLOR;
        if (!item.inMonth) {
            textsize = OUT_MONTH_TEXT_SIZE_SP;
            textColor = OUT_MONTH_TEXT_COLOR;
        } else {
            textsize = IN_MONTH_TEXT_SIZE_SP;
            switch (item.dayOfWeek) {
                case Calendar.SUNDAY:
                    textColor = SUN_COLOR;
                    break;
                case Calendar.SATURDAY:
                    textColor = SAT_COLOR;
                    break;
                default:
                    textColor = NUMBER_COLOR;
                    break;
            }
        }
        numberView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textsize);
        numberView.setTextColor(textColor);
        numberView.setText("" + item.dayOfMonth);

        ///

        String date = "" + item.year + "-" + (item.month + 1) + "-" + item.dayOfMonth;
        Set set = DateManager.hDate.keySet();
        Iterator<String> it = set.iterator();

        setBackgroundColor(Color.TRANSPARENT);
        while (it.hasNext()) {
            String key = it.next();
            if (key.equals(date)) {
                if (DateManager.hDate.get(key) == true) {
                    setBackground(getResources().getDrawable(R.drawable.calendar_selected));
                    numberView.setTextColor(getResources().getColor(R.color.colorIndeepen));
                } else {
                    setBackgroundColor(Color.TRANSPARENT);
                }
                break;
            }
        }



        ///

        // contentView setting

        ArrayList items = item.items;/*
        int size = items.size();
		StringBuilder sb = new StringBuilder();
		sb.append(size + "개");
		contentView.setText(sb.toString());*/

    }

/*
    public void onClick(CalendarItem item) {
        mItem = item;
        int textColor = NUMBER_COLOR;
        if (item.inMonth) {
            textColor = Color.argb(255, 106, 27, 154);
            numberView.setTextColor(textColor);
        }else if(!item.inMonth){
            textColor = OUT_MONTH_TEXT_COLOR;
            numberView.setTextColor(textColor);
        }else {
            numberView.setTextColor(Color.BLACK);
        }
    }*/


}
