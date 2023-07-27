package com.shakticonsultant;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.OrganizationAdapter;
import com.shakticonsultant.databinding.ActivitySignUpBinding;
import com.shakticonsultant.databinding.ActivityUpdateProfileBinding;
import com.shakticonsultant.responsemodel.CommonResponse;
import com.shakticonsultant.responsemodel.SignupResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.FileHelper;
import com.shakticonsultant.utils.PathUtil;
import com.shakticonsultant.utils.PermissionManager;
import com.shakticonsultant.utils.Utils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    String MobilePattern = "[0-9]{10}";
    String idprooffilePath = null;
    ActivityUpdateProfileBinding binding;
    ApiInterface apiInterface;
    ConnectionDetector cd;
    String name, email, mobile, img;
    PermissionManager permission;
    private static final int PICK_IMAGE_REQUEST = 9544;

    //   String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String profilefilepath = null;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cd = new ConnectionDetector(EditProfileActivity.this);
        if (checkStoragePermission()) {
        } else {
            requestPermissions();
        }

        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                },
                1
        );
     /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {

                // If you don't have access, launch a new activity to show the user the system's dialog
                // to allow access to the external storage
            } else {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }*/
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        mobile = getIntent().getStringExtra("mobile");
        img = getIntent().getStringExtra("profile_img");

        binding.edtName.setText(name);
        binding.edtEmail.setText(email);
        binding.edtMobile.setText(mobile);

        Picasso.get()
                .load(ApiClient.Photourl + img)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .fit()
                /* .resize(400,300)
                 .centerCrop(Gravity.TOP)*/
                .into(binding.imageView6);

        binding.imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                pick(view);
            }
        });


        /*binding.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                pick(view);
            }
        });*/
        binding.imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            }
        });


        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!cd.isConnectingToInternet()) {
                    Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                } else if (binding.edtName.getText().toString().trim().equals("")) {

                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "The name field is required.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();

                } else if (binding.edtName.getText().toString().trim().toString().length() < 3) {

                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "The name must be minimum 3 character limit.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();

                } else if (binding.edtMobile.getText().toString().trim().equals("")) {

                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "The mobile field is required", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();

                } else if (!binding.edtMobile.getText().toString().matches(MobilePattern)) {

                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter valid 10 digit phone number.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();

                } else {
                    //signupApi(binding.edtName.getText().toString().trim(),binding.edtMobile.getText().toString().trim(),binding.edtEmail.getText().toString().trim(),binding.edtPassword.getText().toString().trim(),binding.edtRePassword.getText().toString().trim());
                    // startActivity(new Intent(SignUpActivity.this, OTPActivity.class));

                    getUpdateProfile();
                }
            }
        });

    }


    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void pick(View view) {
        verifyStoragePermissions(EditProfileActivity.this);
       /* Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Open Gallery"), PICK_IMAGE_REQUEST);*/


        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/jpeg");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.app_name)), PICK_IMAGE_REQUEST);
        } else {
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, PICK_IMAGE_REQUEST);
        }
    }

    public void getUpdateProfile() {
        //   binding.progressBar2.setVisibility(View.VISIBLE);
        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(this);
        progress_spinner.show();

        Map<String, RequestBody> map = new HashMap<>();

        map.put("user_id", Utils.getRequestBodyParameter(AppPrefrences.getUserid(EditProfileActivity.this)));
        map.put("name", Utils.getRequestBodyParameter(binding.edtName.getText().toString().trim()));
        map.put("mobile", Utils.getRequestBodyParameter(binding.edtMobile.getText().toString().trim()));

        MultipartBody.Part strprofileimg = null;
        if (profilefilepath != null) {
            File serviceImageUri = new File(profilefilepath);
            RequestBody fbody = RequestBody.create(serviceImageUri, MediaType.parse("image*/"));
            strprofileimg = MultipartBody.Part.createFormData("profile_image", profilefilepath, fbody);
        }
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        //Call<SignUpResponse> resultCall = apiInterface.callSignupApi(map,body);
        Call<CommonResponse> resultCall = apiInterface.callUpdateProfile(map, strprofileimg);
//Log.e("MAP DTA",map.toString());
        resultCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful()) {
                    progress_spinner.dismiss();
                    // binding.progressBar2.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {

                        AlertDialog.Builder logoutDialog = new AlertDialog.Builder(EditProfileActivity.this, R.style.AlertDialogTheme)
                                .setTitle(R.string.app_name)

                                .setMessage("Your Profile has been update successfully.")
                                .setIcon(R.drawable.shakti_consultant_logo)
                                .setPositiveButton(Html.fromHtml("<font color='#BB274D'>Ok</font>"), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        finish();


                                    }
                                });
                        logoutDialog.show();

                    } else {
                        progress_spinner.dismiss();

                        //   pd_loading.setVisibility(View.GONE);
                        //binding.progressBar2.setVisibility(View.GONE);

                    }
                } else {
                    progress_spinner.dismiss();

                    // binding.progressBar2.setVisibility(View.GONE);

                    //
                }

            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                // pd_loading.setVisibility(View.GONE);
                //  binding.progressBar2.setVisibility(View.GONE);

                Toast.makeText(EditProfileActivity.this, "ERROR" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {


                Uri uri = data.getData();
                //  File file = new File(uri.getPath());//create path from uri
                //  final String[] split = file.getPath().split(":");//split the path.

                try {
                    //  profilefilepath = PathUtil.getPath(PersonalInfoActivity.this, uri);

                    File finalFile = new File(PathUtil.getPath(EditProfileActivity.this, uri));
                    profilefilepath = finalFile.getAbsolutePath();


                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                binding.imageView6.setImageBitmap(bitmap);

                binding.imgEdit.setVisibility(View.VISIBLE);

            }

        }


    }

    private boolean checkStoragePermission() {
        return (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermissions() {
        permission = new PermissionManager() {
            @Override
            public List<String> setPermission() {
                List<String> permssions = new ArrayList<>();
                permssions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                permssions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                permssions.add(Manifest.permission.CAMERA);
                return permssions;
            }
        };

        permission.checkAndRequestPermissions((Activity) EditProfileActivity.this);
    }

}
