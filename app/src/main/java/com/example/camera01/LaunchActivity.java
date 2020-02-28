package com.example.camera01;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class LaunchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // permission
            if (!hasPermissions(PERMISSIONS)) {
                //
                requestPermissions(PERMISSIONS, PERMISSION_REQUEST_CODE);
            } else {
                Intent mainIntent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }
    }

    //
    static final int PERMISSION_REQUEST_CODE = 1000;
    private static final String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private boolean hasPermissions(String[] permissions) {
        int result;

        // check permission state
        for (String perms : permissions){

            result = ContextCompat.checkSelfPermission(this,perms);

            if(result == PackageManager.PERMISSION_DENIED) {
                return false;
            }

        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch(requestCode){
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length>0) {
                    boolean cameraPermissionAccepted = grantResults[0]
                            == PackageManager.PERMISSION_GRANTED;
                    boolean diskPermissionAccepted = grantResults[1]
                            == PackageManager.PERMISSION_GRANTED;

                    if (!cameraPermissionAccepted || !diskPermissionAccepted) {
                        showDialogForPermission("앱을 실행할려면 퍼미션을 허가해야합니다.");
                    } else {
                        Intent mainIntent = new Intent(LaunchActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }

                }
                break;
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    private void showDialogForPermission(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LaunchActivity.this);
        builder.setTitle("Alert");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();
    }

}
