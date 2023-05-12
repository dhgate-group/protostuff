package io.protostuff.runtime;

import java.util.*;

public class StringUtil {

    public static int hashCode(String value) {
        int h = 0;
        if (value.length() > 0) {
            for (int i = 0; i < value.length(); i++) {
                h = 5 * h + chaToInt(value.charAt(i));
            }
        }
        return h;
    }

    public static int stringToInt(String value) {
        int intTo = 0;
        if(value.length()<=4){
            //System.out.println(value);
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < value.length(); i++) {
                char ch = value.charAt(i);
                buffer.append(chaToInt(ch));
            }
            intTo = Integer.parseInt(buffer.toString()+value.length());
        }else {
            int hash = hashCode(value);
            //int hash = value.hashCode();
            if(hash<0){
                intTo = -hash;
            }else {
                intTo = hash;
            }
        }

        while (intTo > 2147483){
            intTo = intTo>>1;
        }

        return intTo;
        //return value.hashCode();

    }




    public static int chaToInt(char ch) {
        switch (ch) {
            case 'a':
                return 1;
            case 'b':
                return 2;
            case 'c':
                return 3;
            case 'd':
                return 4;
            case 'e':
                return 5;
            case 'f':
                return 6;
            case 'g':
                return 7;
            case 'h':
                return 8;
            case 'i':
                return 9;
            case 'j':
                return 10;
            case 'k':
                return 11;
            case 'l':
                return 12;
            case 'm':
                return 13;
            case 'n':
                return 14;
            case 'o':
                return 15;
            case 'p':
                return 16;
            case 'q':
                return 17;
            case 'r':
                return 18;
            case 's':
                return 19;
            case 't':
                return 20;
            case 'u':
                return 21;
            case 'v':
                return 22;
            case 'w':
                return 23;
            case 'x':
                return 24;
            case 'y':
                return 25;
            case 'z':
                return 26;
            case 'A':
                return 27;
            case 'B':
                return 28;
            case 'C':
                return 29;
            case 'D':
                return 30;
            case 'E':
                return 31;
            case 'F':
                return 32;
            case 'G':
                return 33;
            case 'H':
                return 34;
            case 'I':
                return 35;
            case 'J':
                return 36;
            case 'K':
                return 37;
            case 'L':
                return 38;
            case 'M':
                return 39;
            case 'N':
                return 40;
            case 'O':
                return 41;
            case 'P':
                return 42;
            case 'Q':
                return 43;
            case 'R':
                return 44;
            case 'S':
                return 45;
            case 'T':
                return 46;
            case 'U':
                return 47;
            case 'V':
                return 48;
            case 'W':
                return 49;
            case 'X':
                return 50;
            case 'Y':
                return 51;
            case 'Z':
                return 52;
            case '_':
                return 53;
            case '0':
                return 54;
            case '1':
                return 55;
            case '2':
                return 56;
            case '3':
                return 57;
            case '4':
                return 58;
            case '5':
                return 59;
            case '6':
                return 60;
            case '7':
                return 61;
            case '8':
                return 65;
            case '9':
                return 63;
            case '$':
                return 64;
            default:
                return 65;

        }
    }

    public static String getRandomString(int length){
        if(length==0){
            return "";
        }
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789$_";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(64);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {

        stringToInt("buyerId");

        List<String> list = new ArrayList<String>();
        Map<Integer,String> map = new HashMap<Integer,String>();
        int i = 0;
        int j = 0;
        Random random=new Random();
        while (i<100000){
            int a = random.nextInt(20);
            if(a==0){
                continue;
            }
            String str = getRandomString(a);
            if(Character.isDigit(str.charAt(0))){
                continue;
            }
            if(str.startsWith("$")){
                continue;
            }
            if(list.contains(str)){
                continue;
            }
            list.add(str);
            i++;
            int nameInt = stringToInt(str);
//            if(nameInt<0){
//                System.out.println(nameInt+"------"+str);
//            }

            if(map.get(nameInt)!=null){
                //System.out.println("nameInt="+nameInt+" str1="+map.get(nameInt)+" str2="+str+" i="+i);
                j ++;
                //System.out.println(str+"-----"+map.get(nameInt));
                //break;
            }
            map.put(nameInt,str);
        }
        System.out.println("一共随机字符串数="+i+" 其中有多少次碰撞="+j);
    }
}
