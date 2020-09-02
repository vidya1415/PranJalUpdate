package com.example.aquabeing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aquabeing.models.productlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class dealer_products_list extends AppCompatActivity {

    private RecyclerView recyclerViewlist;
    private FirebaseAuth fAuthen;
    String dealerID,productID;
    private FirebaseFirestore fs;
    FirestoreRecyclerAdapter adapter;
    String documentId = " ",pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_products_list);

        fAuthen = FirebaseAuth.getInstance();
        fs = FirebaseFirestore.getInstance();


//        dealerID = fAuthen.getCurrentUser().getUid();
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
//                new IntentFilter("custom-message"));
        recyclerViewlist = findViewById(R.id.recyclerView);

//        dealerID = "GEAqykAGaOg9ktuqdWu1exj0H6q2";
//
//        productID = fs.collection("dealers").document(dealerID).collection("products").getId();

        Intent i = getIntent();
//The second parameter below is the default string returned if the value is not there.
        dealerID = i.getExtras().getString("dealers");
       // productID = fs.collection("dealers").document(dealerID).collection("products").document().getId();


        Log.d("TAG", dealerID );


        //Query
        Query query = fs.collection("dealers").document(dealerID).collection("products");     //RecyclerOptions
        FirestoreRecyclerOptions<productlist> options = new FirestoreRecyclerOptions.Builder<productlist>()
                .setQuery(query,productlist.class)
                .build();

         adapter = new FirestoreRecyclerAdapter<productlist, productlistViewHolder>(options) {
            @NonNull
            @Override
            public productlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
              View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productlistitem,parent,false);


              return new productlistViewHolder(view);


            }



            @Override
            protected void onBindViewHolder(@NonNull final productlistViewHolder holder, int position, @NonNull final productlist model) {

                holder.brand_name.setText(model.getBrand());
                holder.pricing.setText(model.getPrice());
                holder.quantity_needed.setText(model.getQuantity());
                holder.type_inlit.setText(model.getType());



                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                        documentId=snapshot.getId();
                        onItemClickListner.onClick(documentId);

                    }
                });

            }
        };

        recyclerViewlist.setHasFixedSize(true);
        recyclerViewlist.setLayoutManager(new LinearLayoutManager(this));

        setOnItemClickListner(new onItemClickListner() {
            @Override
            public void onClick(String str) {
                Bundle bundle = new Bundle();
                Intent activityBstartIntent = new Intent(getApplicationContext(), product_details.class);
                activityBstartIntent.putExtra("products",str);
                activityBstartIntent.putExtra("dealers",dealerID);
                startActivity(activityBstartIntent);


                Log.d("TAG", str );

            }
        });

        recyclerViewlist.setAdapter(adapter);


        //View Holder


    }

    private  class productlistViewHolder extends RecyclerView.ViewHolder{

        private TextView brand_name;
      private TextView pricing;
          private TextView quantity_needed;
        private TextView type_inlit;


        public productlistViewHolder(@NonNull View itemView) {
            super(itemView);
            brand_name = itemView.findViewById(R.id.brand);
            pricing= itemView.findViewById(R.id.price);
            quantity_needed =itemView.findViewById(R.id.quantity);
            type_inlit = itemView.findViewById(R.id.type);


    }

    }
//    BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String name= intent.getStringExtra("key");
//            Toast.makeText(dealer_products_list.this, name, Toast.LENGTH_SHORT).show();
//        }
//    };


    onItemClickListner onItemClickListner;

    public void setOnItemClickListner(onItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface onItemClickListner{
        void onClick(String str);//pass your object types.
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}