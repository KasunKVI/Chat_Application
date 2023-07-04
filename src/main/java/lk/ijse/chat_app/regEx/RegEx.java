package lk.ijse.chat_app.regEx;

public class RegEx {

    public static String nameRegEx(){
        return "^[A-Za-z][a-z]{3,}$";
    }

    public static String contactRegEx(){
        return "^0\\d{9}$";
    }

    public static String addressRegEx(){
        return "^([A-Z][a-zA-Z0-9]*)(,\\s*[A-Z][a-zA-Z]+)+$";
    }

}
