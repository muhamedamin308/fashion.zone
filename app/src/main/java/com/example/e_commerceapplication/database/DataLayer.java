package com.example.e_commerceapplication.database;

import static com.example.e_commerceapplication.general.Constants.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceapplication.R;
import com.example.e_commerceapplication.ui.fragments.CartFragment;
import com.example.e_commerceapplication.general.Constants;
import com.example.e_commerceapplication.classes.address.Address;
import com.example.e_commerceapplication.classes.product.Category;
import com.example.e_commerceapplication.classes.product.MyCart;
import com.example.e_commerceapplication.classes.product.NewProduct;
import com.example.e_commerceapplication.classes.product.PopularProduct;
import com.example.e_commerceapplication.classes.product.AllProducts;
import com.example.e_commerceapplication.classes.users.Admin;
import com.example.e_commerceapplication.classes.users.User;
import com.example.e_commerceapplication.ui.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@SuppressLint("NotifyDataSetChanged")
public class DataLayer {
    private final FirebaseAuth auth;
    private final FirebaseFirestore fireStore;
    private final DatabaseReference databaseReference;

    public DataLayer(String path) {
        auth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference(path);
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public FirebaseFirestore getFireStore() {
        return fireStore;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void registerUser(String userEmail, String userPassword, Activity activity, User object) {
        getAuth().createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(activity, task -> {
            if (task.isSuccessful()) {
                object.setUserID(Objects.requireNonNull(getAuth().getUid()));
                HashMap<String, Object> user = new HashMap<>();
                user.put("username", object.getUsername());
                user.put("email", object.getEmail());
                user.put("password", object.getPassword());
                user.put("paymentRate", object.getPaymentRate());
                user.put("userID", object.getUserID());
                getDatabaseReference().child(object.getUserID()).setValue(user);
                getDatabaseReference().child(ADMIN_PATH).child(object.getUserID()).setValue(user);
                Toast.makeText(activity, "Successfully Registered", Toast.LENGTH_SHORT).show();
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
            } else {
                Toast.makeText(activity, "Error: " + task.getException(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loginUser(String userEmail, String userPassword, Activity activity) {
        getAuth().signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(activity, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(activity, "Successfully Registered", Toast.LENGTH_LONG).show();
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
            } else {
                Toast.makeText(activity, "Registration Failed " + task.getException(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void categoryDatabase(List<Category> list, RecyclerView.Adapter adapter, Fragment fragment, LinearLayout linearLayout) {
        getFireStore().collection(Constants.CATEGORY).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document: task.getResult()) {
                    Category category = document.toObject(Category.class);
                    list.add(category);
                    adapter.notifyDataSetChanged();
                    Constants.loadingDialogBar.hideDialog();
                    linearLayout.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(fragment.getContext(), "ERROR!!! " + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void newProductsDatabase(List<NewProduct> list, RecyclerView.Adapter adapter, Fragment fragment) {
        getFireStore().collection(Constants.NEW_PRODUCTS).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document: task.getResult()) {
                    NewProduct newProducts = document.toObject(NewProduct.class);
                    list.add(newProducts);
                    adapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(fragment.getContext(), "ERROR!!! " + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void newProductsRemoverDatabase(NewProduct newProduct) {
        getFireStore().collection(Constants.NEW_PRODUCTS)
                .whereEqualTo("name", newProduct.getName())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        String id = documentSnapshot.getId();
                        getFireStore().collection(NEW_PRODUCTS).document(id).delete();
                    }
                });
    }

    public void popularProductsDatabase(List<PopularProduct> list, RecyclerView.Adapter adapter, Fragment fragment) {
        getFireStore().collection(Constants.POPULAR_PRODUCTS).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document: task.getResult()) {
                    PopularProduct popularProduct = document.toObject(PopularProduct.class);
                    list.add(popularProduct);
                    adapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(fragment.getContext(), "ERROR!!! " + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cartDatabase(String collection, String documentCollection, RecyclerView.Adapter adapter, List<MyCart> list, TextView price, View view, View view2) {
        getFireStore().collection(collection).document(Objects.requireNonNull(getAuth().getCurrentUser()).getUid()).collection(documentCollection).get().addOnCompleteListener(task -> {
            double totalCartPrice = 0.0;
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()) {
                    MyCart cartModel = documentSnapshot.toObject(MyCart.class);
                    list.add(cartModel);
                    assert cartModel != null;
                    totalCartPrice += cartModel.getTotalPrice();
                    adapter.notifyDataSetChanged();
                }
                if (!(list.isEmpty())) {
                    view.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.GONE);
                } else {
                    view2.setVisibility(View.VISIBLE);
                    view.setVisibility(View.GONE);
                }
                NumberFormat formatter = new DecimalFormat("#0.00");
                price.setText(String.format("$%s", formatter.format(totalCartPrice)));
            }
        });
    }

    public void removeCartDatabase(String collection, String documentCollection, List<MyCart> list, TextView price, @NonNull MyCart itemToRemove, Fragment fragment) {
        getFireStore().collection(collection).document(Objects.requireNonNull(getAuth().getCurrentUser()).getUid()).collection(documentCollection).whereEqualTo(PRODUCT_IMAGE, itemToRemove.getProductImage()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                String id = documentSnapshot.getId();
                getFireStore().collection(collection).document(getAuth().getCurrentUser().getUid()).collection(documentCollection).document(id).delete().addOnCompleteListener(task1 -> {
                    double totalPrice;
                    if (task1.isSuccessful()) {
                        list.remove(itemToRemove);
                        totalPrice = 0.0;
                        for (MyCart item: list) {
                            totalPrice += item.getTotalPrice();
                        }
                        NumberFormat formatter = new DecimalFormat("#0.00");
                        price.setText(String.format("$%s", formatter.format(totalPrice)));
                        FragmentTransaction transaction = fragment.requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_container, new CartFragment());
                        transaction.commit();
                    }
                });
            }
        });
    }

    public void getCustomerData(TextView userName, TextView userEmail) {
        if (ADMIN_MODE) {
            userName.setText(Admin.adminName);
            userEmail.setText(Admin.adminEmail);
        } else {
            getDatabaseReference().child(Objects.requireNonNull(getAuth().getUid())).get().addOnCompleteListener(task -> {
                DataSnapshot dataSnapshot = task.getResult();
                userName.setText(String.valueOf(dataSnapshot.child(USERNAME).getValue()));
                if (userEmail != null)
                    userEmail.setText(String.valueOf(dataSnapshot.child(EMAIL).getValue()));
            });
        }
    }

    public void customerDataActivation(HashMap<String, Object> cartMap, Activity activity, String collection) {
        getFireStore().collection(collection).document(Objects.requireNonNull(getAuth().getCurrentUser()).getUid()).collection(USER).add(cartMap).addOnCompleteListener(task -> {
            if (collection.equals(ADD_TO_CART)) {
                Toast.makeText(activity, ADD_TO_CART_SUCCESSFUL, Toast.LENGTH_SHORT).show();
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
            } else {
                Toast.makeText(activity, PAYMENT_SUCCESSFUL, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showAllDatabase(boolean specific, TextView categoryName, String type, List<AllProducts> allProductsList, RecyclerView.Adapter showAllAdapter) {
        if (specific) {
            categoryName.setText(Constants.categories.get(type));
            getFireStore().collection(SHOW_ALL).whereEqualTo("type", type).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()) {
                        AllProducts allProducts = documentSnapshot.toObject(AllProducts.class);
                        allProductsList.add(allProducts);
                        showAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        } else {
            categoryName.setText(R.string.all);
            getFireStore().collection(SHOW_ALL).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()) {
                        AllProducts allProducts = documentSnapshot.toObject(AllProducts.class);
                        allProductsList.add(allProducts);
                        showAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public void getAddressDatabase(List<Address> list, RecyclerView.Adapter adapter) {
        getFireStore().collection("Addresses").document(Objects.requireNonNull(getAuth().getCurrentUser()).getUid()).collection(USER).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot snapshot: task.getResult().getDocuments()) {
                    Address address = snapshot.toObject(Address.class);
                    list.add(address);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void addAddressDatabase(HashMap<String, Object> addressMap, Activity activity) {
        getFireStore().collection("Addresses").document(Objects.requireNonNull(getAuth().getCurrentUser()).getUid()).collection(USER).add(addressMap).addOnCompleteListener(task -> {
            Toast.makeText(activity, "Address Added", Toast.LENGTH_SHORT).show();
            activity.finish();
        });
    }

    public void removeAddressDatabase(Activity activity, List<Address> list, Address remove) {
        getFireStore().collection("Addresses").document(Objects.requireNonNull(getAuth().getCurrentUser()).getUid()).collection(USER).whereEqualTo("address", remove.getAddress()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                String id = documentSnapshot.getId();
                getFireStore().collection("Addresses").document(Objects.requireNonNull(getAuth().getCurrentUser()).getUid()).collection(USER).document(id).delete().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        list.remove(remove);
                        activity.finish();
                        activity.startActivity(activity.getIntent());
                    }
                });
            }
        });
    }

    public void buyCartData(HashMap<String, Object> paymentMapList, Activity activity) {
        getFireStore().collection(USER_PAYMENTS).document(Objects.requireNonNull(getAuth().getCurrentUser()).getUid()).collection(USER).add(paymentMapList);
        Toast.makeText(activity, PAYMENT_SUCCESSFUL, Toast.LENGTH_SHORT).show();
    }

    public void clearCartAfterPayment(List<MyCart> list) {
        getFireStore().collection(ADD_TO_CART).document(Objects.requireNonNull(getAuth().getCurrentUser()).getUid()).collection(USER).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()) {
                    String id = documentSnapshot.getId();
                    getFireStore().collection(ADD_TO_CART).document(Objects.requireNonNull(getAuth().getCurrentUser()).getUid()).collection(USER).document(id).delete().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            MyCart cartModel = documentSnapshot.toObject(MyCart.class);
                            list.remove(cartModel);
                        }
                    });
                }
            }
        });
    }


    // ----------- Admin Data Layer ----------- //
    public void getPopularProductsAdminData(List<PopularProduct> list, RecyclerView.Adapter adapter, Activity activity) {
        getFireStore().collection(Constants.POPULAR_PRODUCTS).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document: task.getResult()) {
                    PopularProduct popularProduct = document.toObject(PopularProduct.class);
                    list.add(popularProduct);
                    adapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(activity, "ERROR!!! " + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getNewProductsAdminData(List<NewProduct> list, RecyclerView.Adapter adapter, Activity activity) {
        getFireStore().collection(NEW_PRODUCTS).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document: task.getResult()) {
                    NewProduct newProduct = document.toObject(NewProduct.class);
                    list.add(newProduct);
                    adapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(activity, "ERROR!!! " + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAllProductsAdminData(List<AllProducts> list, RecyclerView.Adapter adapter, Activity activity) {
        getFireStore().collection("ShowAll").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document: task.getResult()) {
                    AllProducts allProducts = document.toObject(AllProducts.class);
                    list.add(allProducts);
                    adapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(activity, "ERROR!!! " + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void removeNewProductAdminData(Activity activity, List<NewProduct> list, NewProduct remove, String collection) {
        getFireStore().collection(collection).whereEqualTo("name", remove.getName()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                String id = documentSnapshot.getId();
                getFireStore().collection(collection).document(id).delete().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        list.remove(remove);
                        activity.finish();
                        activity.startActivity(activity.getIntent());
                    }
                });
            }
        });
    }


    public void removePopularProductAdminData(Activity activity, List<PopularProduct> list, PopularProduct remove, String collection) {
        getFireStore().collection(collection).whereEqualTo("name", remove.getName()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                String id = documentSnapshot.getId();
                getFireStore().collection(collection).document(id).delete().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        list.remove(remove);
                        activity.finish();
                        activity.startActivity(activity.getIntent());
                    }
                });
            }
        });
    }

    public void removeAllProductAdminData(Activity activity, List<AllProducts> list, AllProducts remove, String collection) {
        getFireStore().collection(collection).whereEqualTo("name", remove.getName()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                String id = documentSnapshot.getId();
                getFireStore().collection(collection).document(id).delete().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        list.remove(remove);
                        activity.finish();
                        activity.startActivity(activity.getIntent());
                    }
                });
            }
        });
    }
}
