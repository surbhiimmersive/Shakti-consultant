package com.shakticonsultant;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.SelectableAdapter;
import com.shakticonsultant.adapter.SelectableViewHolder;
import com.shakticonsultant.model.Item;
import com.shakticonsultant.model.SelectableItem;
import com.shakticonsultant.responsemodel.OrganizationDatumResponse;
import com.shakticonsultant.responsemodel.OrganizationResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrganizationDailog extends AppCompatActivity implements SelectableViewHolder.OnItemSelectedListener{
    RecyclerView recyclerView;
    SelectableAdapter adapter;
    List<Item> selectableItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizationlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) this.findViewById(R.id.selection_list);
        recyclerView.setLayoutManager(layoutManager);
        getOrganizationList();
        List<Item> selectableItems = generateItems();


    }

    public List<Item> generateItems(){

        selectableItems = new ArrayList<>();
       /* selectableItems.add(new Item("cem","karaca"));
        selectableItems.add(new Item("sezen","aksu"));
        selectableItems.add(new Item("baris","manco"));*/

        return selectableItems;
    }

    @Override
    public void onItemSelected(SelectableItem selectableItem) {

        List<Item> selectedItems = adapter.getSelectedItems();

        if (selectableItem.isSelected() == true) {



        List<String> name = new ArrayList<>();
        name.add(selectableItem.getName());
        String csv = String.join(",", name);
        Snackbar.make(recyclerView, "Selected item is " + selectedItems.size()
                , Snackbar.LENGTH_LONG).show();
    }else{


        }
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

                            selectableItems.add(new Item(response.body().getData().get(i).getOrganisation(),response.body().getData().get(i).getOrganisation()));
                        }

                        adapter = new SelectableAdapter(OrganizationDailog.this,selectableItems,true);
                        recyclerView.setAdapter(adapter);
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