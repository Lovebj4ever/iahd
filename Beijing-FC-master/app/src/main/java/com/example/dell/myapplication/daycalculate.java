package com.example.dell.myapplication;

/**
 * Created by Dell on 2019/4/13.
 */

    public class daycalculate {

        int year;
        int year1;
        int month;
        int month1;
        int day;
        int day1;

        public daycalculate(int year,int year1,int month,int month1,int day,int day1) {
            this.year = year;
            this.year1 = year1;
            this.month = month;
            this.month1 = month1;
            this.day = day;
            this.day1 = day1;
        }

        public boolean leap(int year) {
            if ((year%4==0 && year%100!=0) || year%400==0 ) {
                return true;
            }
            else {
                return false;
            }
        }

        public int yearDays() {
            if(year == year1) {
                return 0;
            }
            int days = 0;
            for(int i=year; i<year1; i++) {
                if(i==year) {
                    continue;
                }
                if(leap(i)) {
                    days++;
                }
            }
            days = days+365*(year1-year-1);
            return days;
        }

        public int monthDays() {
            int[] mon = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
            int monthdays = 0;

            if (year1 != year) {
                for (int i = 0; i < (month1 - 1); i++) {
                    monthdays += mon[i];
                }
                if (leap(year1) && month1 > 2) {
                    monthdays += 1;
                }
                for (int i = 11; i > (month - 1); i--) {
                    monthdays += mon[i];
                }
                if (leap(year) && month < 2) {
                    monthdays += 1;
                }
            }
            else if(year == year1 && month!=month1){
                for (int i = month; i < (month1 - 1); i++) {
                    monthdays += mon[i];
                }
                if (leap(year1) && month1 > 2) {
                    monthdays += 1;
                }
            }
            else {
                monthdays = 0;
            }
            return monthdays;
        }


        public int days() {
            int days = 0;
            int[] mon = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
            if (year == year1 && month == month1) {
                days = day1 - day;
            }
            else {
                days = (mon[month - 1] - day) + day1;
                if (month == 2) {
                    days += 1;
                }
            }

            return days;
        }

        public int sumdays() {
            int sumday = days() + monthDays() + yearDays();
            return sumday;
        }
    }


