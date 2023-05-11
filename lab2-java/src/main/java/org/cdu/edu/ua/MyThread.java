package org.cdu.edu.ua;

public class MyThread implements Runnable {
    private final int id;
    private final int firstBound;
    private final int secondBound;
    private final ThreadController threadController;

    public MyThread(int id, int firstBound, int secondBound, ThreadController threadController) {
        this.id = id;
        this.firstBound = firstBound;
        this.secondBound = secondBound;
        this.threadController = threadController;
    }

    @Override
    public void run() {
        double min = threadController.partMin(firstBound, secondBound);
        threadController.collectMin(id, min);
    }
}
