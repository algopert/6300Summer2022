package edu.gatech.seclass;

public class MyString implements MyStringInterface {
    private String _str;

    @Override
    public String getString() {
        return _str;
    }

    @Override
    public void setString(String string) throws IllegalArgumentException{
        if (string == MyStringInterface.easterEgg) {
        throw new IllegalArgumentException();
        }
        _str = string;

    }

    @Override
    public int countAlphabeticWords() throws NullPointerException{
        if (_str == null)
            throw new NullPointerException();
        if (_str.isEmpty())
            return 0;
        boolean prev_char_flag = false;
        int cnt = 0;
        for (int i = 0; i < _str.length(); i++) {
            if ((_str.charAt(i) >= 'A' && _str.charAt(i) <= 'Z') || (_str.charAt(i) >= 'a' && _str.charAt(i) <= 'z')) {
                if (prev_char_flag)
                    continue;
                prev_char_flag = true;

                cnt++;
            } else {
                prev_char_flag = false;
            }
        }
        return cnt;
    }

    private int reverse(int number) {
        String reverse = ""; // Holds reversed number
        String n = number + ""; // Convert number to string
        // Reverse string
        for (int i = n.length() - 1; i >= 0; i--) {
            reverse += n.charAt(i);
        }
        return Integer.parseInt(reverse); // Return reversed integer
    }


    @Override
    public String addNumber(int n, boolean flip) throws NullPointerException, IllegalArgumentException {
        if (_str == null)
            throw new NullPointerException();
        if (n < 0)
            throw new IllegalArgumentException();

        String res_str = "";
        String tmp_dig_str = "";
        boolean flag_dig = false;
        for (int i = 0; i < _str.length(); i++) {
            if (_str.charAt(i) >= '0' && _str.charAt(i) <= '9') {
                tmp_dig_str += _str.charAt(i);
                flag_dig = true;
            } else {
                if (flag_dig) {
                    int xx = Integer.parseInt(tmp_dig_str);
                    if (flip) {
                        xx = reverse(xx);
                    }
                    res_str += String.valueOf(xx + n);
                    flag_dig = false;
                    tmp_dig_str = "";
                }

                res_str += _str.charAt(i);
            }
        }
        if (flag_dig) {
            int xx = Integer.parseInt(tmp_dig_str);
            if (flip) {
                xx = reverse(xx);
            }
            res_str += String.valueOf(xx + n);
        }

        return res_str;
    }

    @Override
    public void convertDigitsToNamesInSubstring(int firstPosition, int finalPosition) throws MyIndexOutOfBoundsException, IllegalArgumentException, NullPointerException {
        if (_str == null) {
            throw new NullPointerException();
        }
        if (firstPosition > finalPosition || firstPosition < 1) {
            throw new IllegalArgumentException();
        }
        if (firstPosition < 1 || finalPosition > this._str.length()) {
            throw new MyIndexOutOfBoundsException();
        }

        String myString = _str.substring(firstPosition - 1, finalPosition);
        System.out.println(_str);
        System.out.println(myString);
        myString = myString.replaceAll("10", "Ten");
        myString = myString.replaceAll("0", "Zero");
        myString = myString.replaceAll("1", "One");
        myString = myString.replaceAll("2", "Two");
        myString = myString.replaceAll("3", "Three");
        myString = myString.replaceAll("4", "Four");
        myString = myString.replaceAll("5", "Five");
        myString = myString.replaceAll("6", "Six");
        myString = myString.replaceAll("7", "Seven");
        myString = myString.replaceAll("8", "Eight");
        myString = myString.replaceAll("9", "Nine");

        System.out.println(_str);
        System.out.println(_str.substring(0, firstPosition - 1));

        _str = _str.substring(0, firstPosition - 1) + myString
                + _str.substring(finalPosition, _str.length());
    }
}
