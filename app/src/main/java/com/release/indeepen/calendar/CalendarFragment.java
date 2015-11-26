package com.release.indeepen.calendar;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.release.indeepen.R;
import com.release.indeepen.management.dateManager.DateManager;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {
    TextView titleView;
    GridView gridView;
    CalendarAdapter mAdapter;
    Boolean nColor = false;
    Context mContext;

    int count, countNum;

    private static final boolean isWeekCalendar = false;

    ArrayList<ItemData> mItemdata = new ArrayList<ItemData>();


    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);


        mItemdata.add(new ItemData(2015, 9, 10, "A"));
        mItemdata.add(new ItemData(2015, 9, 11, "B"));
        mItemdata.add(new ItemData(2015, 9, 12, "C"));
        mItemdata.add(new ItemData(2015, 9, 15, "D"));
        mItemdata.add(new ItemData(2015, 9, 21, "E"));
        mItemdata.add(new ItemData(2015, 9, 21, "F"));

        titleView = (TextView) view.findViewById(R.id.title);
        ImageButton btn = (ImageButton) view.findViewById(R.id.nextMonth);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (isWeekCalendar) {
                    CalendarData data = CalendarManager.getInstance().getNextWeekCalendarData();
                    titleView.setText("" + data.year + " Year " + (data.weekOfYear) + " Week");
                    mAdapter.setCalendarData(data);
                } else {
                    CalendarData data = CalendarManager.getInstance().getNextMonthCalendarData();
                    titleView.setText("" + data.year + "." + (data.month + 1));
                    mAdapter.setCalendarData(data);
                }
            }
        });

        btn = (ImageButton) view.findViewById(R.id.lastMonth);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (isWeekCalendar) {
                    CalendarData data = CalendarManager.getInstance().getPrevWeekCalendarData();
                    titleView.setText("" + data.year + " Year " + (data.weekOfYear) + " Week");
                    mAdapter.setCalendarData(data);
                } else {
                    CalendarData data = CalendarManager.getInstance().getLastMonthCalendarData();
                    titleView.setText("" + data.year + "." + (data.month + 1));
                    mAdapter.setCalendarData(data);
                }
            }
        });
        gridView = (GridView) view.findViewById(R.id.gridView1);
        try {
            CalendarManager.getInstance().setDataObject(mItemdata);
        } catch (CalendarManager.NoComparableObjectException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (isWeekCalendar) {
            CalendarData data = CalendarManager.getInstance().getWeekCalendarData();
            titleView.setText("" + data.year + " Year " + (data.weekOfYear) + " Week");
            mAdapter = new CalendarAdapter(getActivity().getApplicationContext(), data);
        } else {
            CalendarData data = CalendarManager.getInstance().getCalendarData();
            titleView.setText("" + data.year + "." + (data.month + 1));
            mAdapter = new CalendarAdapter(getActivity().getApplicationContext(), data);
        }

        count=0;
        countNum=2;

        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                // TODO Auto-generated method stub
                CalendarItem item = (CalendarItem) mAdapter.getItem(position);
                String date = "" + item.year + "-" + (item.month + 1) + "-" + item.dayOfMonth;
                if (item.inMonth) {
                    if (item.isChecked) {
                        item.isChecked = false;
                        DateManager.hDate.put(date, false);
                        count--;
                    } else {
                        if (count >= countNum) {
                            Toast.makeText(getActivity(), countNum + "회 이상 예약 불가능", Toast.LENGTH_SHORT).show();
                        } else {
                            item.isChecked = true;
                            DateManager.hDate.put(date, true);
                            count++;
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        });


/*



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView cNum = (TextView) view.findViewById(R.id.number);

                // ImageView select = (ImageView)view.findViewById(R.id.selected);
                // gridView.setSelected(!gridView.isSelected());
               *//* if (!gridView.isSelected()){
                  //  gridView.setSelection(position);
                    gridView.setSelected(!gridView.isSelected());
                    cNum.setTextColor(getResources().getColor(R.color.colorIndeepen));
                }*//*
                if (gridView.isPressed()) {
                    gridView.setSelected(true);
                    cNum.setTextColor(getResources().getColor(R.color.colorIndeepen));
                } else {
                    cNum.setTextColor(Color.BLACK);
                }



                *//*mAdapter.notifyDataSetChanged();
                View savedView = new View(getActivity().getApplicationContext());

                view.setBackgroundResource(R.drawable.selector_day);
                if(savedView==null)
                {
                    savedView = view;
                }
                else
                {
                    savedView.setBackgroundResource(R.color.transparent);
                    savedView = view;
                }*//*

               *//* view.setSelected(!view.isSelected());
                view.setBackgroundResource(R.drawable.selector_day);
                if(view.isSelected()) {
                    view.setSelected(true);
                }*//*

                // view.getResources().getDrawable(R.drawable.day_selected);
                // view.getResources().getColor(R.color.colorIndeepen)
                Toast.makeText(getActivity().getApplicationContext(), (position + 1) + " ", Toast.LENGTH_SHORT).show();

            }
        });*/
        return view;
    }



}