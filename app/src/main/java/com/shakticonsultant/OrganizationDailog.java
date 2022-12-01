package com.shakticonsultant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.util.SparseBooleanArray;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.SelectableAdapter;
import com.shakticonsultant.adapter.SelectableViewHolder;
import com.shakticonsultant.model.Item;
import com.shakticonsultant.model.OrganizationInterface;
import com.shakticonsultant.model.SelectableItem;
import com.shakticonsultant.responsemodel.OrganizationDatumResponse;
import com.shakticonsultant.responsemodel.OrganizationResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class OrganizationDailog extends AppCompatActivity {
    List<String>org_name_list=new ArrayList<>();
    ListView listview ;
Context context;
Button btnSubmit;
Button button4;
String data;
ImageView imageView13;
    SparseBooleanArray sparseBooleanArray ;
    List<String> selectedOrganization=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizationlist);

        listview = (ListView)findViewById(R.id.listView);
        button4 = (Button)findViewById(R.id.button4);
        imageView13 = (ImageView) findViewById(R.id.imageView13);
        context=OrganizationDailog.this;
      getOrganizationList();
        imageView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent();
                intent.putExtra("Organization","no");
                setResult(2,intent);
                finish();//finishing activity

            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent();
                intent.putExtra("Organization","no");
                setResult(2,intent);
                finish();//finishing activity


            }
        });
      btnSubmit=findViewById(R.id.submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(context, "Button"+ValueHolder, Toast.LENGTH_SHORT).show();
               /* if(context instanceof OrganizationInterface){

                    ((OrganizationInterface)context).apilist(ValueHolder);
                }

*/
                Intent intent=new Intent();
                intent.putExtra("Organization",data);
                setResult(2,intent);
                finish();//finishing activity

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent();
        intent.putExtra("Organization","no");
        setResult(2,intent);
        finish();//finishing activity

    }

    public void getOrganizationList() {
       // pd_loading.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
       // map.put("user_id", AppPrefrences.getUserID(NotificationActivity.this));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<OrganizationResponse> resultCall = apiInterface.callOrganizationList();

        resultCall.enqueue(new Callback<OrganizationResponse>() {
            @Override
            public void onResponse(Call<OrganizationResponse> call, Response<OrganizationResponse> response) {

                if (response.isSuccessful()) {
                   // pd_loading.setVisibility(View.GONE);
                   // lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        List<OrganizationDatumResponse> orglist=response.body().getData();


                        for(int i=0;i<orglist.size();i++){

                            org_name_list.add(orglist.get(i).getOrganisation());

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                    (OrganizationDailog.this,
                                            android.R.layout.simple_list_item_multiple_choice,
                                            android.R.id.text1, org_name_list );

                            listview.setAdapter(adapter);

                            listview.setOnItemClickListener(new OnItemClickListener()
                            {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    // TODO Auto-generated method stub

                                    sparseBooleanArray = listview.getCheckedItemPositions();
                                    String ValueHolder = "" ;

                                    int i = 0 ;

                                    while (i < sparseBooleanArray.size()) {

                                        if (sparseBooleanArray.valueAt(i)) {

                                            ValueHolder += org_name_list.get(sparseBooleanArray.keyAt(i)) + "#";
                                            data=ValueHolder;
                                        }

                                        i++ ;
                                    }

                                    ValueHolder = ValueHolder.replaceAll("(#)*$", "");
                                    data=ValueHolder;
                                   // Toast.makeText(OrganizationDailog.this, "ListView Selected Values = " + ValueHolder, Toast.LENGTH_LONG).show();

                                    /*    int i = 0;

                                        while (i < sparseBooleanArray.size()) {

                                            if (sparseBooleanArray.valueAt(i)) {

                                                ValueHolder += org_name_list.get(sparseBooleanArray.keyAt(i)) + ",";

                                                selectedOrganization.add(ValueHolder);
                                            }

                                            i++;
                                        }

                                        ValueHolder = ValueHolder.replaceAll("(,)*$", "");
*/
                                      //  Toast.makeText(OrganizationDailog.this, "ListView Selected Values = " + ValueHolder, Toast.LENGTH_LONG).show();



                                }
                            });
                           // selectableItems.add(new Item(response.body().getData().get(i).getOrganisation(),response.body().getData().get(i).getOrganisation()));
                        }

                       // adapter = new SelectableAdapter(OrganizationDailog.this,selectableItems,true);
                       // recyclerView.setAdapter(adapter);
                    } else {

                      //  lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<OrganizationResponse> call, Throwable t) {

                //lemprtNotification.setVisibility(View.VISIBLE);
              //  pd_loading.setVisibility(View.GONE);
                //Utils.showFailureDialog(NotificationActivity.this, "Something went wrong!");
            }
        });
    }
}