package com.example.aquabeing;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.aquabeing.models.productlist;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class product_details extends AppCompatActivity {
    private FloatingActionButton addtocartbtn;
    private ImageView productimg;
    private ElegantNumberButton numberButton;
    private TextView productPrice, productType, productname, dealername;
    private String productID = "";
    private NumberPicker inputnum;
    private String[] pickerVals;

    private FirebaseAuth fAuthen;
    String dealerID = "";
    private FirebaseFirestore fs;
    String p;
    int p4;
    String p2,p3,custname;
    Integer totprice,num,nums,p1,number;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_items_layout);
        fs = FirebaseFirestore.getInstance();
        fAuthen = FirebaseAuth.getInstance();

        final String userId = (fAuthen.getCurrentUser()).getUid();

//
        productID = getIntent().getStringExtra("products");
        dealerID = getIntent().getStringExtra("dealers");

        //  dealerID = "GEAqykAGaOg9ktuqdWu1exj0H6q2";
        // productID = fs.collection("dealers").document(dealerID).collection("products").document().getId();

        Log.d("tag", dealerID + productID);


        addtocartbtn = (FloatingActionButton) findViewById(R.id.addtocart);
        inputnum = (NumberPicker)findViewById(R.id.addthenumber);
//        numberButton = (ElegantNumberButton) findViewById(R.id.counter);
//        productimg = (ImageView) findViewById(R.id.cart_image_view);
        dealername = (TextView) findViewById(R.id.cart_dealer_name);
        productname = (TextView) findViewById(R.id.cart_prod_name);
        productType = (TextView) findViewById(R.id.cart_prod_type);
        productPrice = (TextView) findViewById(R.id.prod_price);

//        numberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String nums = numberButton.getNumber();
//                  num = Integer.valueOf(numberButton.getNumber());
//                Log.d("Counter", nums);
//            }
//        });

        DocumentReference productref = fs.collection("dealers").document(dealerID).collection("products").document(productID);
        productref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
//               dealername.setText(documentSnapshot.getString("Name"));
                productname.setText(documentSnapshot.getString("brand"));
                productType.setText(documentSnapshot.getString("price"));
                productPrice.setText(documentSnapshot.getString("type"));
                p = documentSnapshot.getString("brand");
                p1 = 30;
                try {
                    p1 = Integer.parseInt(Objects.requireNonNull(documentSnapshot.getString("price")));

                }catch (NumberFormatException nfe){
                    nfe.printStackTrace();
                }
               // p4 = Integer.parseInt(extractInt(documentSnapshot.getString("price")));
             //   totprice = p4 * num;
              //  Log.d("totalprice", p4*num+ " "+p1);
                pickerVals = new Integer[]{1,2,3};
                inputnum.setDisplayedValues(pickerVals);
                inputnum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        int valuePicker1 = inputnum.getValue();
                        p4 = Integer.parseInt(pickerVals[valuePicker1]);
                        Log.d("picker value", pickerVals[valuePicker1]);
                    }
                });
            //  p4= inputnum.getValue();
//                 number = 1; // or any appllication default value
//                try {
//                    number = Integer.parseInt(String.valueOf(p4));
//                } catch (NumberFormatException nfe) {
//                    nfe.printStackTrace();
//                }
             // totprice = Integer.valueOf(String.valueOf(inputnum.getText()));
              num = p4* p1;
                p2 = documentSnapshot.getString("type");
          //      p3 = documentSnapshot.getString("Name");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("tag", "onEvent: Document do not exists");
            }
        });


        DocumentReference dealername1 = fs.collection("dealers").document(dealerID);
        dealername1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                dealername.setText(documentSnapshot.getString("name"));
                p3 = documentSnapshot.getString("name");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("tag", "onEvent: Document do not exists");

            }
        });


        DocumentReference customerref = fs.collection("customers").document(userId);
        customerref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                custname = documentSnapshot.getString("name");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("tag", "onEvent: Document do not exists");

            }
        });

        addtocartbtn.setOnClickListener(new View.OnClickListener() {


            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                DocumentReference documentReference = fs.collection("customers").document(userId).collection("orders").document();
                final Map<String, Object> dealer = new HashMap<>();
                dealer.put("amount", 3);
                dealer.put("brand", p);
                dealer.put("customer_id", userId);
                dealer.put("dealer_name", p3);
                dealer.put("customer_name", custname);
                dealer.put("quantity", String.valueOf(p4));
                dealer.put("total_price",num);
                documentReference.set(dealer)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "Dealer ID: " + userId);
                                Toast.makeText(product_details.this, "added to cart", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "Dealer ID not added: " + userId);

                    }
                });
                Intent intent = new Intent(product_details.this, customer_orders.class);
                startActivity(intent);


            }


        });


        }

        public String extractInt(String str)
        {
            // Replacing every non-digit number
            // with a space(" ")
            str = str.replaceAll("[^\\d]", " ");

            // Remove extra spaces from the beginning
            // and the ending of the string
            str = str.trim();

            // Replace all the consecutive white
            // spaces with a single space
            str = str.replaceAll(" +", " ");

            if (str.equals(""))
                return "-1";

            return str;
        }
}


