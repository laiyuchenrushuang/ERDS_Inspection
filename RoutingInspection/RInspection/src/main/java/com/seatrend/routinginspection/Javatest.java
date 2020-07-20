package com.seatrend.routinginspection;

import com.seatrend.routinginspection.db.table.PictureTable;

/**
 * Created by ly on 2020/7/8 9:55
 */
public class Javatest {

    public static void main(String[] args) {

        MyThread t = new MyThread();
        t.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MyThread t2 = new MyThread();
        t2.setflag(t.getflag());
        t2.start();
    }
}

class MyThread extends Thread {
    public static int inflag = 0;
    @Override
    public void run() {
        for (int i = inflag; i < 50000; i++) {

            System.out.println(i);
            if (isInterrupted()) {
                inflag= i;
                System.out.println("线程停止"
                        );
                return;
            }
        }
    }

    public void setflag(int inflag){
        this.inflag = inflag;
    }
    public int getflag(){
       return inflag;
    }
}
