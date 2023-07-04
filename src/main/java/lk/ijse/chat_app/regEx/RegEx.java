package lk.ijse.chat_app.regEx;

public class RegEx {

    public static String nameRegEx(){
        return "^[A-Z][a-z]{2,}(\\\\s+[A-Z][a-z]{2,})*$";
    }

    public static String contactRegEx(){
        return "^0\\d{9}$";
    }

    public static String addressRegEx(){
        return "^([A-Z][a-zA-Z0-9]*)(,\\s*[A-Z][a-zA-Z]+)+$";
    }

}
