package com.company;

public class Main {

    public static void main(String[] args) {
        int dim = 10000000;
        int threadNum = 2;
        ArrClass arrClass = new ArrClass(dim, threadNum);

        //MinELem min_part=arrClass.partMin(0,dim);

        MinELem min_threads=arrClass.threadMin();

        //System.out.println("value:"+min_part.value+" index:"+min_part.index);

        System.out.println("value:"+min_threads.value+" index:"+min_threads.index);
    }
}
