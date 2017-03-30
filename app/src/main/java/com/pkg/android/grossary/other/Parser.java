package com.pkg.android.grossary.other;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by GAURAV on 22-03-2017.
 */

public class Parser {
    public static ArrayList parseShoppingList(String s){
        s = removeBrackets(s);
        List<Integer> l = new ArrayList<>();
        Scanner sc = new Scanner(s);
        while(sc.hasNext())
            l.add(sc.nextInt());
        return (ArrayList) l;
    }

    public static String removeBrackets(String s) {
        StringBuffer sb = new StringBuffer(s);
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
