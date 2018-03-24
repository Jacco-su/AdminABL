/*
 *  Copyright (c) 2016-2020  陶乐乐,毛磊版权所有 
 *  Tao Lele, Pierre Morel. All Rights Reserved.
 */

package com.dream.util;

/**
 * @author
 * @ClassName: SortArr.java
 * @Description:
 * @date 2016-04-11 11:06
 */

public class SortArr {
    public static void main(String[] args) {
        String arr[][]=new String[][]{{"5","qq"},{"8","ww"},{"999","xxx"},{"56565","rtr"},{"66","哈哈儿"}};
        for (int i = 0; i <sort(arr).length ; i++) {
            for(int j=0;j<arr[i].length;j++) {
                System.out.print(arr[i][j] + "\t");
            }
            System.out.println("\n");
        }
    }
    public static int compareArray(String a[], String b[]) {
        if(Integer.parseInt(a[0]) > Integer.parseInt(b[0])) return 1;
        else if(Integer.parseInt(a[0]) < Integer.parseInt(b[0])) return -1;
        return 0;
    }
    public static void swap(String[][] arr, int a, int b) {
        String[] str = arr[a].clone();
        for(int i=0; i < arr[b].length; i++) {
            arr[a][i] = arr[b][i];
        }
        for(int i=0; i < str.length; i++) {
            arr[b][i] = str[i];
        }
    }
    public static String[][] sort(String[][] list) {
        int len = list.length;
        int min, comp;
        for(int i=0; i < len; i++) {
            min = i;
            for(int j=i+1; j < len; j++) {
                comp = compareArray(list[min], list[j]);
                if(comp == 1) min = j;
            }
            swap(list, i, min);
        }
        return list;
    }
}
