package com.hansung.android.project2;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.Calendar;

public class MonthAdapter extends BaseAdapter {
    Calendar cal;

    Context mContext;

    CalendarItem[] items; // 년도와 월을 입력받기 위해 멤버로 가진다.
    int curYear;
    int curMonth;

    MonthAdapter(Context context) {
        super();
        mContext = context;
        init();
    }

    MonthAdapter(Context context, AttributeSet attrs) {
        super();
        mContext = context;
        init();
    }

    public void init() { // 초기화 해주기
        cal = Calendar.getInstance(); // Calendar 객체 가져오기
        items = new CalendarItem[7 * 6]; // 배열은 7행에 6열로 받아옴

        calculate();// 객체를 가져와서 날짜등을 계산하기 위해 사용
    }

    public void calculate() { //
        for (int i = 0; i < items.length; i++) { // 배열 초기화
            items[i] = new CalendarItem(0);
        }

        cal.set(Calendar.DAY_OF_MONTH, 1); // 월의 시작일을 1로

        int startDay = cal.get(Calendar.DAY_OF_WEEK); // 현재 달 1일의 요일 (1: 일요일, . . . 7: 토요일)
        int lastDay = cal.getActualMaximum(Calendar.DATE); // 달의 마지막 날짜를 구함

        int cnt = 1;
        for (int i = startDay - 1; i < startDay - 1 + lastDay; i++) { // 각 시작위치와 마지막 날을 계산하여 지정
            items[i] = new CalendarItem(cnt);
            cnt++;
        }

        curYear = cal.get(Calendar.YEAR); // 현재 년도를 받음
        curMonth = cal.get(Calendar.MONTH); // 현재 달을 받음
    }

    public void setPreviousMonth() { // 이전 달을 계산하는 함수
        cal.add(Calendar.MONTH, -1); // 현재 달에 -1을 함
        calculate();
    }

    public void setNextMonth() {               // 다음 달을 계산하는 함수
        cal.add(Calendar.MONTH, 1);  // 현재 달에 +1을 함
        calculate();
    }

    //해당 월의 마지막 주 구하기
    public int getWeeks(){
        return cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CalendarItemView view = new CalendarItemView(mContext);
        CalendarItem item = items[position]; // 포지션은 요일에 몇번째인지 알려줌.
        view.setItem(item);

        if (position % 7 == 0) { // 일요일이면
            view.setTextColor(Color.RED);  // 텍스트 색을 빨간색으로
        }

        if (position % 7 == 6) { // 토요일이면
            view.setTextColor(Color.BLUE);  // 텍스트 색을 파란색으로
        }

        float dp = mContext.getResources().getDisplayMetrics().density;
        GridView.LayoutParams params = new GridView.LayoutParams(parent.getWidth()/7 - (int)dp, (parent.getHeight() - (int)dp * getWeeks() ) / getWeeks());
        view.setLayoutParams(params);

        return view;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public int getCurYear() {
        return curYear;
    }

    public int getCurMonth() {
        return curMonth;
    }

    // 년도를 토스트 메세지로 나타내기 위한 메소드
    public int toast_year() {
        return curYear;
    }

    // 월을 토스트 메세지로 나타내기 위한 메소드
    public int toast_month() {
        return curMonth + 1;
    }

    // 일을 토스트 메세지로 나타내기 위한 메소드
    public int toast_date() {
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) // 만약 현재 달 1일의 요일이 일요일이라면
            return 1;
        else if (cal.get(Calendar.DAY_OF_WEEK) == 2) // 현재 달 1일의 요일이 월요일이라면
            return 0;
        else if (cal.get(Calendar.DAY_OF_WEEK) == 3) // 현재 달 1일의 요일이 화요일이라면
            return -1;
        else if (cal.get(Calendar.DAY_OF_WEEK) == 4) // 현재 달 1일의 요일이 수요일이라면
            return -2;
        else if (cal.get(Calendar.DAY_OF_WEEK) == 5) // 현재 달 1일의 요일이 목요일이라면
            return -3;
        else if (cal.get(Calendar.DAY_OF_WEEK) == 6) // 현재 달 1일의 요일이 금요일이라면
            return -4;
        else if (cal.get(Calendar.DAY_OF_WEEK) == 7) // 현재 달 1일의 요일이 일요일이라면
            return -5;
        return 0;
    }
}
