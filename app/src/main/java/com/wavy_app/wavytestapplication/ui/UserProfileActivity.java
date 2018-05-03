package com.wavy_app.wavytestapplication.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wavy_app.wavytestapplication.R;
import com.wavy_app.wavytestapplication.networks.NetworkManager;
import com.wavy_app.wavytestapplication.networks.RequestResponseListener;
import com.wavy_app.wavytestapplication.pojo.UserProfileResponse;
import com.wavy_app.wavytestapplication.utils.AppUtil;
import com.wavy_app.wavytestapplication.utils.Connectivity;
import com.wavy_app.wavytestapplication.utils.Constants;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    CircleImageView civDisplayPicture;
    ContentLoadingProgressBar clpbLoading;
    TextView tvEmail, tvFirstName, tvLastName, tvPhoneNo;
    Button bDelete;

    String user_id;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        init();
    }

    private void init() {
        civDisplayPicture = findViewById(R.id.civDisplayPicture);

        clpbLoading = findViewById(R.id.clpbLoading);

        tvEmail = findViewById(R.id.tvEmail);
        tvFirstName = findViewById(R.id.tvFirstName);
        tvLastName = findViewById(R.id.tvLastName);
        tvPhoneNo = findViewById(R.id.tvPhoneNo);

        bDelete = findViewById(R.id.bDelete);
        bDelete.setOnClickListener(this);

        if (Connectivity.isConnected(this)) {
            callProfileService();
        } else {
            AppUtil.showToast(this, getString(R.string.no_internet));
            clpbLoading.hide();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bDelete:
                if (Connectivity.isConnected(this)) {
                    showProgress();
                    callDeleteService();
                } else {
                    AppUtil.showToast(this, getString(R.string.no_internet));
                    clpbLoading.hide();
                }
                break;
        }
    }

    private void callProfileService() {
        showProgress();
        NetworkManager.getInstance().get("all", new RequestResponseListener<Object>() {
            @Override
            public void onResult(Integer response, Object object) {
                dismissProgress();
                switch (response) {
                    case Constants.WebApi.Response.NO_INTERNET:
                        AppUtil.showToast(UserProfileActivity.this, getString(R.string.no_internet));
                        break;
                    case Constants.WebApi.Response.SUCCESS:
                        Gson gson = new Gson();
                        UserProfileResponse userProfile = gson.fromJson((String) object, UserProfileResponse.class);
                        setupValues(userProfile);
                        break;
                    case Constants.WebApi.Response.TIMEOUT:
                        AppUtil.showToast(UserProfileActivity.this, getString(R.string.no_internet));
                        break;
                }
            }
        });
    }

    private void callDeleteService() {
        NetworkManager.getInstance().delete(user_id, new RequestResponseListener<Object>() {
            @Override
            public void onResult(Integer response, Object object) {
                dismissProgress();
                switch (response) {
                    case Constants.WebApi.Response.NO_INTERNET:
                        AppUtil.showToast(UserProfileActivity.this, getString(R.string.no_internet));
                        break;
                    case Constants.WebApi.Response.SUCCESS:
                        AppUtil.showToast(getApplicationContext(), getString(R.string.deleted_successfully));
                        removeValues();
                        break;
                    case Constants.WebApi.Response.TIMEOUT:
                        AppUtil.showToast(UserProfileActivity.this, getString(R.string.no_internet));
                        break;
                }
            }
        });
    }

    private void setupValues(UserProfileResponse userProfile) {
        tvEmail.setText(userProfile.getEmail());
        tvFirstName.setText(userProfile.getFirstName());
        tvLastName.setText(userProfile.getLastName());
        tvPhoneNo.setText(userProfile.getPhoneNumber());
        user_id = userProfile.getId();

        if (!userProfile.getProfilePicture().equals("") && userProfile.getProfilePicture() != null) {
            Picasso.with(this).load(userProfile.getProfilePicture())
                    .into(civDisplayPicture, new Callback() {
                        @Override
                        public void onSuccess() {
                            clpbLoading.hide();
                        }

                        @Override
                        public void onError() {

                        }
                    });
        } else {
            clpbLoading.hide();
        }
    }

    private void removeValues() {
        tvPhoneNo.setText("");
    }

    public void showProgress() {
        dismissProgress();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}
