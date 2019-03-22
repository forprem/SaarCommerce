package com.pulsaar.saarcommerce;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pulsaar.saarcommerce.model.AdminOrders;

import org.w3c.dom.Text;


public class AdminCheckOrdersActivity extends AppCompatActivity {

    private RecyclerView orderList;
    private DatabaseReference orderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_orders);

        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");


        orderList = findViewById(R.id.order_list);
        orderList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options =
        new FirebaseRecyclerOptions.Builder<AdminOrders>()
        .setQuery(orderRef, AdminOrders.class)
        .build()  ;

        FirebaseRecyclerAdapter<AdminOrders, AdminCheckOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrders, AdminCheckOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminCheckOrdersViewHolder holder, final int position, @NonNull final AdminOrders model) {
                        holder.userName.setText("Name: "+model.getName());
                        holder.userPhoneNumber.setText("Phone: "+model.getPhone());
                        holder.userTotalPrice.setText("Total Amount: "+model.getTotalAmount());
                        holder.userDateTime.setText("Order at: "+model.getDate()+ " "+model.getTime());
                        holder.userShippingddress.setText("Shipping Address: "+model.getAddress() + ", "+model.getCity());

                        //Passing UID for Orde info in Admin Page
                        holder.showOrderBtn.setOnClickListener(new View.OnClickListener() {

                            String uID = getRef(position).getKey();
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(AdminCheckOrdersActivity.this, AdminUserProductActivity.class);
                                intent.putExtra("uid", uID);
                                startActivity(intent);
                            }
                        });
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]{
                                        "Yes",
                                        "No"
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminCheckOrdersActivity.this);
                                builder.setTitle("Have you shipped this Product ?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (which == 0) {
                                            String uID = getRef(position).getKey();
                                            removeOrder(uID);
                                        } else {
                                            finish();
                                        }
                                    }
                                });
                                builder.show();
                            }

                        });

                    }

                    @NonNull
                    @Override
                    public AdminCheckOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_layout, viewGroup, false);
                        return new AdminCheckOrdersViewHolder(view);
                    }
                };
        orderList.setAdapter(adapter);
        adapter.startListening();
    }

    private void removeOrder(String uID) {
        orderRef.child(uID).removeValue();
    }

    public static class AdminCheckOrdersViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, userPhoneNumber, userTotalPrice, userDateTime, userShippingddress;
        public Button showOrderBtn;
        public AdminCheckOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.order_user_name);
            userPhoneNumber = itemView.findViewById(R.id.order_phone_number);
            userTotalPrice = itemView.findViewById(R.id.order_total_price);
            userDateTime = itemView.findViewById(R.id.order_date_time);
            userShippingddress = itemView.findViewById(R.id.order_address_city);
            showOrderBtn = itemView.findViewById(R.id.show_all_products_btn);

        }
    }
}
