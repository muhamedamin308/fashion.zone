package com.example.e_commerceapplication.ui.products;

import static com.example.e_commerceapplication.general.Constants.NEW_PRODUCTS;
import static com.example.e_commerceapplication.general.Constants.USERS;
import static com.example.e_commerceapplication.general.Constants.USER_PAYMENTS;

import android.annotation.SuppressLint;
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
import com.example.e_commerceapplication.database.DataLayer;
import com.example.e_commerceapplication.classes.product.MyCart;
import com.example.e_commerceapplication.classes.product.NewProduct;
import com.example.e_commerceapplication.classes.product.PopularProduct;
import com.example.e_commerceapplication.classes.product.Product;
import com.example.e_commerceapplication.classes.product.AllProducts;
import com.example.e_commerceapplication.ui.dialogs.RatingDialogBar;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PaymentActivity extends AppCompatActivity {

    private EditText cardNumber, cvvNumber, cardDate;
    private TextView cardHolderName;
    private DataLayer dataLayer;
    private Product mainProduct;
    private List<MyCart> myCartList = null;
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

        if (object instanceof NewProduct) {
            mainProduct = (NewProduct) object;
            totalAmountPrice = ((amount * quantity) + shippingValue) - discountValue;
        } else if (object instanceof PopularProduct) {
            mainProduct = (PopularProduct) object;
            totalAmountPrice = ((amount * quantity) + shippingValue) - discountValue;
        } else if (object instanceof AllProducts) {
            mainProduct = (AllProducts) object;
            totalAmountPrice = ((amount * quantity) + shippingValue) - discountValue;
        } else if (object != null) {
            myCartList = (List<MyCart>) object;
            totalAmountPrice = (amount + shippingValue) - discountValue;
        } else {
            totalAmountPrice = 0.0;
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
                if (myCartList == null) {
                    paymentMap.put("creditCardHolderName", cardHolderName.getText().toString());
                    paymentMap.put("productName", mainProduct.getName());
                    paymentMap.put("productTotalPrice", totalAmountPrice);
                    paymentMap.put("productRate", mainProduct.getRating());
                    paymentMap.put("productQuantity", quantity);
                    paymentMap.put("userID", Objects.requireNonNull(dataLayer.getAuth().getCurrentUser()).getUid());
                    dataLayer.customerDataActivation(paymentMap, PaymentActivity.this, USER_PAYMENTS);
                    if (mainProduct.getStock() - quantity == 0) {
                        dataLayer.newProductsRemoverDatabase((NewProduct) mainProduct);
                    } else {
                        HashMap<String, Object> stockUpdate = new HashMap<>();
                        stockUpdate.put("stock", mainProduct.getStock() - quantity);
                        dataLayer.getFireStore().collection(NEW_PRODUCTS).whereEqualTo("name", mainProduct.getName()).get().addOnCompleteListener(task -> {
                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                String id = documentSnapshot.getId();
                                dataLayer.getFireStore().collection(NEW_PRODUCTS).document(id).update(stockUpdate);
                            }
                        });
                    }
                } else {
                    myCartList.forEach(product -> {
                        paymentMap.put("creditCardHolderName", cardHolderName.getText().toString());
                        paymentMap.put("productName", product.getProductName());
                        paymentMap.put("productTotalPrice", product.getTotalPrice());
                        paymentMap.put("productRate", product.getProductRate());
                        paymentMap.put("productQuantity", product.getTotalQuantity());
                        paymentMap.put("userID", Objects.requireNonNull(dataLayer.getAuth().getCurrentUser()).getUid());
                        dataLayer.buyCartData(paymentMap, PaymentActivity.this);
                    });
                    dataLayer.clearCartAfterPayment(myCartList);
                }
                ratingDialogBar.showDialog(this);
            }
        });

        exit.setOnClickListener(v -> finish());
    }

    private boolean isCreditCardValid() {
        return (cardNumber.getText().toString().isEmpty() || cardDate.getText().toString().isEmpty() || cvvNumber.getText().toString().isEmpty() || cardNumber.getText().toString().length() < 16 || cvvNumber.getText().toString().length() < 3);
    }
}