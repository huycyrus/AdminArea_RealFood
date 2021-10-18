package com.example.adminarea_realfood;

import android.widget.EditText;

import java.util.regex.Pattern;

public class Validate {

    Pattern pattern_email = Pattern.compile("^.+@.+\\..+$");
    Pattern pattern_phone = Pattern.compile("(84|0[3|5|7|8|9])+([0-9]{8})\\b");
    Pattern pattern_number = Pattern.compile("^(0|[1-9][0-9]*)$");

    public boolean isBlank(EditText editText){
        editText.setError(null);
        if (editText.getText().toString().isEmpty())
        {
            editText.setError("Vui lòng không để trống!");
            return true;
        }
        return false;
    }

    public boolean lessThan6Char(EditText editText){
        editText.setError(null);
        if (editText.getText().toString().length()<6)
        {
            editText.setError("Vui lòng nhập nhiều hơn 6 kí tự!");
            return true;
        }
        return false;
    }

    public boolean isEmail(EditText editText){
        editText.setError(null);
        if (!pattern_email.matcher(editText.getText().toString()).find())
        {
            editText.setError("Vui lòng nhập Email!");
            return false;
        }
        return true;
    }
    public boolean isPhone(EditText editText){
        editText.setError(null);
        if (!pattern_phone.matcher(editText.getText().toString()).find())
        {
            editText.setError("Vui lòng nhập Số điện thoại!");
            return false;
        }
        return true;
    }
    public boolean isNumber(EditText editText){
        editText.setError(null);
        if (!pattern_number.matcher(editText.getText().toString()).find())
        {
            editText.setError("Vui lòng nhập Số");
            return false;
        }
        return true;
    }
}
