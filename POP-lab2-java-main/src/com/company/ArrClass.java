package com.company;

import java.util.concurrent.ThreadLocalRandom;
public class ArrClass {
    private final int dim;
    private final int threadNum;
    public final int[] arr;

    public ArrClass(int dim, int threadNum) {
        this.dim = dim;
        arr = new int[dim];
        this.threadNum = threadNum;
        for(int i = 0; i < dim; i++){
            arr[i] = i;
        }
        arr[ThreadLocalRandom.current().nextInt(0, dim)]=ThreadLocalRandom.current().nextInt(0-dim, -1);

    }

    public MinELem partMin(int startIndex, int finishIndex){
        MinELem minELem = new MinELem(arr[0],0);
        for(int i = startIndex+1; i < finishIndex; i++){
            if(arr[i]<minELem.value) {
                minELem.value=arr[i];
                minELem.index=i;

            }
        }
        return minELem;
    }

    private MinELem min = new MinELem(100000000,0);

    synchronized private MinELem getMin() {
        while (getThreadCount()<threadNum){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return min;
    }

    synchronized public void collectMin(MinELem min){
        if(min.value<this.min.value) {
            this.min.value = min.value;
            this.min.index = min.index;
        }
    }

    private int threadCount = 0;
    synchronized public void incThreadCount(){
        threadCount++;
        notify();
    }

    private int getThreadCount() {
        return threadCount;
    }

    public MinELem threadMin(){
        ThreadMin[] threadMins = new ThreadMin[threadNum];

        int partLength = arr.length / threadNum;
        for (int i = 0; i < threadNum; i++) {
            threadMins[i] = new ThreadMin(partLength * i, partLength*(i+1),this);
        }
        for (int i = 0; i < threadNum; i++) {
            threadMins[i].start();
        }
        return getMin();
    }
}
