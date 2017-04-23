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
        if(s==null || s.isEmpty()){
            return null;
        }
        List<Integer> l = new ArrayList<>();
        Scanner sc = new Scanner(s);
        while(sc.hasNext())
            l.add(sc.nextInt());
        return (ArrayList) l;
    }

    public static ArrayList parseRecipeList(String s){
        s = removeBrackets(s);
        if(s==null || s.isEmpty()){
            return null;
        }
        List<Integer> l = new ArrayList<>();
        Scanner sc = new Scanner(s);
        sc.useDelimiter(", ");
        while(sc.hasNext()) {
            l.add(Integer.parseInt(sc.next()));
        }
        return (ArrayList) l;
    }

    public static ArrayList parseStockList(String s){
        s = removeBrackets(s);
        if(s==null || s.isEmpty()){
            return null;
        }
        List<Integer> l = new ArrayList<>();
        Scanner sc = new Scanner(s);
        sc.useDelimiter(", ");
        String scint;
        while(sc.hasNext()) {
            scint = removeQuotes(sc.next());
            l.add(Integer.parseInt(scint));
        }
        return (ArrayList) l;
    }

    private static String removeQuotes(String s) {
        if(s == null || s.isEmpty()){
            return null;
        }
        StringBuffer sb = new StringBuffer(s);

        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public static String removeBrackets(String s) {
        if(s == null || s.isEmpty()){
            return null;
        }
        StringBuffer sb = new StringBuffer(s);
        if(sb.charAt(0)=='[')
            sb.deleteCharAt(0);
        if(sb.charAt(sb.length()-1)==']')
            sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
