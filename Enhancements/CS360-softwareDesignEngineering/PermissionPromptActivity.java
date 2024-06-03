package com.example.cs360projecttwo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionPromptActivity extends AppCompatActivity {
    private static final int SMS_PERMISSION_REQUEST_CODE = 100;

    private TextView explanationTextView;
    private Button grantPermissionButton;
    private Button denyPermissionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        explanationTextView = findViewById(R.id.textViewExplanation);
        grantPermissionButton = findViewById(R.id.buttonRequestPermission);
        denyPermissionButton = findViewById(R.id.buttonDenyPermission);

        grantPermissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSMSPermission();
            }
        });

        denyPermissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle deny permission button click
                Toast.makeText(PermissionPromptActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                // You can add further actions here if needed
            }
        });
    }

    private void requestSMSPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    SMS_PERMISSION_REQUEST_CODE);
        } else {
            // Permission is already granted
            Toast.makeText(this, "SMS permission is already granted", Toast.LENGTH_SHORT).show();
            // Send SMS alert for low inventory only if permission is granted
            sendLowInventorySMSAlert();
        }
    }


    private void sendLowInventorySMSAlert() {
        String message = "Low inventory alert: Please check your inventory.";
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("recipientPhoneNumber", null, message, null, null);
            Toast.makeText(this, "Low inventory SMS alert sent", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cannot send SMS. Permission not granted.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Toast.makeText(this, "SMS permission granted", Toast.LENGTH_SHORT).show();
                // Send SMS alert for low inventory only if permission is granted
                sendLowInventorySMSAlert();
            } else {
                // Permission denied
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
                // Handle the case when the user denies permission (e.g., disable SMS feature)
            }
        }
    }
}
