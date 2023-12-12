package com.example.e_commerceapplication.products;

import static com.example.e_commerceapplication.general.Constants.USERS;
import static com.example.e_commerceapplication.general.Constants.USER_PAYMENTS;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.general.data.DataLayer;
import com.example.e_commerceapplication.models.product.MyCartModel;
import com.example.e_commerceapplication.models.product.NewProductsModel;
import com.example.e_commerceapplication.models.product.PopularProductsModel;
import com.example.e_commerceapplication.models.product.Product;
import com.example.e_commerceapplication.models.product.ShowAllModel;
import com.example.e_commerceapplication.start.MainActivity;
import com.example.e_commerceapplication.start.RatingDialogBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PaymentActivity extends AppCompatActivity {

    private EditText cardNumber, cvvNumber, cardDate;
    private TextView cardHolderName;
    private DataLayer dataLayer;
    private Product mainProduct;
    private List<MyCartModel> myCartModelList = null;

    private RatingDialogBar ratingDialogBar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        cardNumber = findViewById(R.id.cardNumberTV);
        cvvNumber = findViewById(R.id.CvvTV);
        cardDate = findViewById(R.id.cardExpiryDateTV);
        cardHolderName = findViewById(R.id.cardHolderNameTV);
        TextView subTotal = findViewById(R.id.subtotal_price);
        TextView discount = findViewById(R.id.discount);
        TextView shipping = findViewById(R.id.shipping);
        TextView totalQuantity = findViewById(R.id.quantity);
        TextView totalPayment = findViewById(R.id.total_pay);
        AppCompatButton pay = findViewById(R.id.payButton);
        ImageView exit = findViewById(R.id.exit);

        dataLayer = new DataLayer(USERS);
        ratingDialogBar = new RatingDialogBar(this);

        double amount, discountValue, shippingValue, totalAmountPrice;
        int quantity;
        Object object = getIntent().getSerializableExtra("product");
        amount = getIntent().getDoubleExtra("amount", 0.0);
        discountValue = getIntent().getDoubleExtra("discount", 0.0);
        shippingValue = getIntent().getDoubleExtra("shipping", 0.0);
        quantity = getIntent().getIntExtra("quantity", 1);
        totalAmountPrice = ((amount * quantity) + shippingValue) - discountValue;

        if (object instanceof NewProductsModel) {
            mainProduct = (NewProductsModel) object;
        } else if (object instanceof PopularProductsModel) {
            mainProduct = (PopularProductsModel) object;
        } else if (object instanceof ShowAllModel) {
            mainProduct = (ShowAllModel) object;
        } else if (object != null) {
            myCartModelList = (List<MyCartModel>) object;
        }

        subTotal.setText("$" + amount);
        discount.setText("$" + discountValue);
        shipping.setText("$" + shippingValue);
        totalQuantity.setText(String.valueOf(quantity));
        totalPayment.setText("$" + totalAmountPrice);

        cardNumber.addTextChangedListener(new TextWatcher() {
            int temp = 4;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    if (s.toString().length() % temp == 0) {
                        temp += 5;
                        s.append("-");
                    }
                } else {
                    temp = 4;
                }
            }
        });
        cardDate.addTextChangedListener(new TextWatcher() {
            int temp = 2;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    if (s.toString().length() % temp == 0) {
                        temp += 3;
                        s.append("/");
                    }
                } else {
                    temp = 2;
                }
            }
        });

        dataLayer.getCustomerData(cardHolderName, null);

        pay.setOnClickListener(v -> {
            if (isCreditCardValid()) {
                Toast.makeText(PaymentActivity.this, "Please Enter all Credit Card Details", Toast.LENGTH_SHORT).show();
            } else {
                HashMap<String, Object> paymentMap = new HashMap<>();
                if (myCartModelList == null) {
                    paymentMap.put("creditCardHolderName", cardHolderName.getText().toString());
                    paymentMap.put("productName", mainProduct.getName());
                    paymentMap.put("productTotalPrice", totalAmountPrice);
                    paymentMap.put("productRate", mainProduct.getRating());
                    paymentMap.put("productQuantity", quantity);
                    dataLayer.customerDataActivation(paymentMap, PaymentActivity.this, USER_PAYMENTS);
                } else {
                    List<HashMap<String, Object>> paymentMapList = new ArrayList<>();
                    myCartModelList.forEach(product -> {
                        paymentMap.put("creditCardHolderName", cardHolderName.getText().toString());
                        paymentMap.put("productName", product.getProductName());
                        paymentMap.put("productTotalPrice", product.getTotalPrice());
                        paymentMap.put("productRate", product.getProductRate());
                        paymentMap.put("productQuantity", product.getTotalQuantity());
                        paymentMapList.add(paymentMap);
                    });
                    dataLayer.buyCartData(paymentMapList, PaymentActivity.this);
                    dataLayer.clearCartAfterPayment(myCartModelList);
                }
                ratingDialogBar.showDialog(this);
            }
        });

        exit.setOnClickListener(v -> finish());
    }
    private boolean isCreditCardValid() {
        return (cardNumber.getText().toString().isEmpty() ||
                cardDate.getText().toString().isEmpty() ||
                cvvNumber.getText().toString().isEmpty() ||
                cardNumber.getText().toString().length() < 16 ||
                cvvNumber.getText().toString().length() < 3);
    }
}