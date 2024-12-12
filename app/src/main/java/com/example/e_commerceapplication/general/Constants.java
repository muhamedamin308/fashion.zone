package com.example.e_commerceapplication.general;

import android.annotation.SuppressLint;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.ui.dialogs.LoadingDialogBar;

import java.util.HashMap;

public class Constants {
    public static final int[] imageArray = {R.drawable.supermarker_card_empty, R.drawable.delivery2, R.drawable.shopping_bags1};

    public static final int[] headingArray = {R.string.first_slide, R.string.second_slide, R.string.third_slide};

    public static final int[] descriptionArray = {R.string.description, R.string.description, R.string.description};

    public static final HashMap<String, String> categories = new HashMap<String, String>() {{
        put("watch", "Watches ⌚");
        put("socks", "Socks \uD83E\uDDE6");
        put("shirts", "Shirts \uD83D\uDC54");
        put("glass", "Glasses \uD83D\uDC53");
        put("pants", "Pants \uD83D\uDC56");
        put("tshirt", "T-Shirts \uD83D\uDC54");
        put("shoes", "Shoes \uD83D\uDC5E");
    }};
    public final static int REQUEST_CODE = 111;
    public final static String USERS = "Users";
    public final static String CATEGORY = "Category";
    public final static String NEW_PRODUCTS = "NewProducts";
    public final static String POPULAR_PRODUCTS = "AllProducts";
    public static final String SHOW_ALL = "ShowAll";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public final static String ADD_TO_CART = "AddToCart";
    public final static String USER = "User";
    public final static String PRODUCT_IMAGE = "productImage";
    public static final String USER_PAYMENTS = "UserPayments";
    public static final String PAYMENT_SUCCESSFUL = "Payment Done Successfully";
    public static final String ADD_TO_CART_SUCCESSFUL = "Product Added To Card Successfully";
    public static final String ADMIN_PATH = "admin";
    @SuppressLint("StaticFieldLeak")
    public static LoadingDialogBar loadingDialogBar = null;
    public static boolean isAddressSelected = false;
    public static boolean ADMIN_MODE = false;
    public static HashMap<Integer, String> MONTH = new HashMap<Integer, String>() {{
       put(1, "JAN");
       put(2, "FEB");
       put(3, "MAR");
       put(4, "APR");
       put(5, "MAY");
       put(6, "JUN");
       put(7, "JUL");
       put(8, "AUG");
       put(9, "SEP");
       put(10, "OCT");
       put(11, "NOV");
       put(12, "DEC");
    }};
}
