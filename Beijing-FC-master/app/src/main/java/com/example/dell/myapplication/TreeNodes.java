package com.example.dell.myapplication;

/**
 * Created by Dell on 2019/4/25.
 */

public class TreeNodes {

    double data;
    char operation;
    TreeNodes left;
    TreeNodes right;



    public TreeNodes(String ex) {

        char[] arr = Delete(ex);
        arr = checkCorrect(arr);
        int length = arr.length;
        int index = 0;


        if (hasOperation(arr)) {

            int in = 0;

            for (int i = length - 1; i >= 0; i--) {

                if (arr[i] == '(') {
                    in--;
                } else if (arr[i] == ')') {
                    in++;
                }

                if (in > 0) {
                    continue;
                }

                if (arr[i] == '×' || arr[i] == '÷') {
                    index = i;
                }
                else if(arr[i] == '+' || arr[i] == '-') {
                    index = i;
                    break;
                }
            }
            operation = arr[index];

            StringBuilder lefter = new StringBuilder();
            StringBuilder righter = new StringBuilder();

            for (int i = 0; i < index; i++) {
                lefter.append(arr[i]);
            }
            for (int i = index + 1; i < length; i++) {
                righter.append(arr[i]);
            }

            left = new TreeNodes(lefter.toString());
            right = new TreeNodes(righter.toString());

        }
        else {
            StringBuilder number = new StringBuilder();
            number.append(ex);
            data = Double.parseDouble(number.toString());
        }
    }


    public char[] Delete(String str) {

        if(str == null) {
            return null;
        }
        while (str.charAt(0) == '(' && str.charAt(str.length()-1) == ')') {
            int in = 0;
            for(int i = 0; i < str.length()-1; i++){
                if(str.charAt(i) == '('){
                    in--;
                }
                if(str.charAt(i) == ')'){
                    in++;
                }
                if(in == 0){
                    return str.toCharArray();
                }
            }
            str = str.substring(1,str.length()-1);
        }

        return str.toCharArray();
    }


    public char[] checkCorrect(char[] ch) {
        int check = 0;
        int check1 = 0;
        if(isOperation(ch[0])) {
            char[] chars = new char[ch.length+1];
            chars[0] = '0';
            for(int i=1 ; i<chars.length ; i++) {
                chars[i] = ch[i-1];
            }
            ch = chars;
        }
        for(int i = 0; i<ch.length ; i++) {
            if(isOperation(ch[i])) {
                if (check == 1) {
                    System.out.println("enter is wrong");
                }
                check = 1;
            }
            else{
                check = 0;
            }

            if(ch[i] == '(') {
                check1++;
            }
            if (ch[i] == ')') {
                check1--;
            }
        }
        if (check1 != 0) {
            System.out.println("enter is wrong");
        }

        return ch;
    }






    public boolean hasOperation(char[] cArray) {
        for (int i = 0; i < cArray.length; i++) {
            if (isOperation(cArray[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean isOperation(char c) {

        return (c == '+' || c == '-' || c == '÷' || c == '×');
    }



    public double calculate() {
        double result = 0;
        if (left == null && right == null) {
            result = data;
        } else {
            double leftResult = left.calculate();
            double rightResult = right.calculate();
            switch (operation) {
                case '+':
                    result = leftResult + rightResult;
                    break;
                case '-':
                    result = leftResult - rightResult;
                    break;
                case '×':
                    result = leftResult * rightResult;
                    break;
                case '÷':
                    result = leftResult / rightResult;
                    break;
                default:
                    break;
            }
        }
        return result;
    }
}

