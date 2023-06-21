package com.shakticonsultant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Arrays;
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

    ListView listview;
    Context context;
    Button btnSubmit, button3;
    Button button4;
    String data;
    ImageView imageView13;
    SparseBooleanArray sparseBooleanArray;
    List<String> org_name_list = new ArrayList<>();
    List<String> datalist = new ArrayList<>();
    List<String> selectedOrganization = new ArrayList<>();
    String working_organization_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizationlist);

        listview = (ListView) findViewById(R.id.listView);
        button4 = (Button) findViewById(R.id.button4);
        button3 = (Button) findViewById(R.id.button3);
        button3.setBackground(getResources().getDrawable(R.drawable.custom_item_bg));

        if (getIntent().getExtras() != null) {
            working_organization_name = getIntent().getStringExtra("working_organization_name");
            if (working_organization_name != null)
                selectedOrganization = new ArrayList<String>(Arrays.asList(working_organization_name.split("#")));
        }

        /*selectedOrganization.add("Allen");
        selectedOrganization.add("Byujus");
        selectedOrganization.add("Shakti Consultant");*/
        imageView13 = (ImageView) findViewById(R.id.imageView13);
        context = OrganizationDailog.this;
        getOrganizationList();
        imageView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent();
                intent.putExtra("Organization", "no");
                setResult(2, intent);
                finish();//finishing activity
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);

            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button4.setTextColor(getResources().getColor(R.color.main_text_color));
                button4.setBackground(getResources().getDrawable(R.drawable.custom_item_bg));
                button3.setBackground(getResources().getDrawable(R.drawable.custom_edittext_bg));
                button3.setTextColor(getResources().getColor(R.color.black));

                button4.setTextSize(15);

                Intent intent = new Intent();
                intent.putExtra("Organization", "no");
                setResult(2, intent);
                finish();//finishing activity
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                button3.setTextColor(getResources().getColor(R.color.main_text_color));
                button3.setBackground(getResources().getDrawable(R.drawable.custom_item_bg));
                button4.setBackground(getResources().getDrawable(R.drawable.custom_edittext_bg));
                button4.setTextColor(getResources().getColor(R.color.black));

                button3.setTextSize(15);

                Intent intent = new Intent();
                intent.putExtra("Organization", "no");
                setResult(2, intent);
                finish();//finishing activity


            }
        });
        btnSubmit = findViewById(R.id.submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(context, "Button"+ValueHolder, Toast.LENGTH_SHORT).show();
               /* if(context instanceof OrganizationInterface){

                    ((OrganizationInterface)context).apilist(ValueHolder);
                }

*/
                Intent intent = new Intent();
                intent.putExtra("Organization", data);
                setResult(2, intent);
                finish();//finishing activity

            }
        });

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("Organization", "no");
        setResult(2, intent);
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
                    if (response.body().isSuccess() == true) {
                        List<OrganizationDatumResponse> orglist = response.body().getData();


                        for (int i = 0; i < orglist.size(); i++) {

                            org_name_list.add(orglist.get(i).getOrganisation());
                            datalist.addAll(org_name_list);
                            Log.e("VALUE", String.valueOf(org_name_list.containsAll(selectedOrganization)));
                            //  System.out.println(org_name_list.containsAll(selectedOrganization));


                            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                    (OrganizationDailog.this,
                                            android.R.layout.simple_list_item_multiple_choice,
                                            android.R.id.text1, org_name_list);

                            listview.setAdapter(adapter);
                            for (int k = 0; k < org_name_list.size(); k++) {
                                for (int j = 0; j < selectedOrganization.size(); j++) {

                                    if (!(selectedOrganization.get(j).equals(org_name_list.get(k)))) {

                                        //listview.setItemChecked(k, false);

                                    } else {
                                        listview.setItemChecked(k, true);

                                    }
                                }

                            }

                          /*  for (int k = 0; k < org_name_list.size(); k++) {
                                if (org_name_list.containsAll(selectedOrganization)) {

                                    listview.setItemChecked(k, true);

                                } else {

                                    listview.setItemChecked(k, false);

                                }
                            }
*/


/*

                            int correctCount=0, incorrectCount = 0;
                            List<String> list1 = new ArrayList<String>(org_name_list);

                            List<String> list2 = new ArrayList<String>(selectedOrganization);

                            for(String tmp1: list1) {
                                for(String tmp2: list2) {
                                    if(tmp1.compareTo(tmp2) == 0) {
                                        correctCount++;

                                         listview.setItemChecked(correctCount,true);

                                    } else {
                                        incorrectCount++;
                                        listview.setItemChecked(incorrectCount, false);

                                }

                                }
                            }
*/


                            listview.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    // TODO Auto-generated method stub

                                    sparseBooleanArray = listview.getCheckedItemPositions();
                                    String ValueHolder = "";

                                    int i = 0;

                                    while (i < sparseBooleanArray.size()) {

                                        if (sparseBooleanArray.valueAt(i)) {

                                            ValueHolder += org_name_list.get(sparseBooleanArray.keyAt(i)) + "#";
                                            data = ValueHolder;
                                        }

                                        i++;
                                    }

                                    ValueHolder = ValueHolder.replaceAll("(#)*$", "");
                                    data = ValueHolder;
                                    // Toast.makeText(OrganizationDailog.this, "ListView Selected Values = " + ValueHolder, Toast.LENGTH_LONG).show();


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