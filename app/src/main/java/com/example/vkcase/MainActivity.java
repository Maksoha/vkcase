package com.example.vkcase;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.provider.Telephony;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.vkcase.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int videocamNum = 0, micNum = 0, panelNum = 0;
    private final int[] images_videocam = {R.drawable.baseline_videocam_off_24, R.drawable.baseline_videocam_24};
    private final int[] images_mic = {R.drawable.baseline_mic_off_24, R.drawable.baseline_mic_24};
    private final int[] images_circle = {R.drawable.circle, R.drawable.circle_grey};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding.btnVideocam.setOnClickListener(view -> {
                switchVideocam();
        });
        binding.btnMic.setOnClickListener(view -> {
            switchMic();
        });
        binding.btnWavingHand.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(getResources().getString(R.string.hello));
            builder.setPositiveButton("OK", (dialog, which) -> dialog.cancel());
            builder.show();
        });
        binding.btnCallEnd.setOnClickListener(view -> {
            finish();
        });
        binding.outlineSpaceDashboard.setOnClickListener(view -> {
            switchPanel();
        });
        binding.buttonMembers.setOnClickListener(view -> {
            Intent intent_contacts = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent_contacts, 1);
        });
        binding.buttonChat.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
                startActivity(intent);
            }
            catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "No SIM Found", Toast.LENGTH_LONG).show();
            };
        });
    }
    private void switchVideocam() {
        videocamNum = (videocamNum + 1) % 2;
        binding.iconVideocam.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), images_videocam[videocamNum]));
        binding.btnVideocam.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), images_circle[videocamNum]));
    }
    private void switchMic() {
        micNum = (micNum + 1) % 2;
        binding.iconMic.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), images_mic[micNum]));
        binding.btnMic.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), images_circle[micNum]));
        if (micNum % 2 == 0) binding.textYou.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_mic_off_16,0);
        else binding.textYou.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }
    private void switchPanel() {
        panelNum = (panelNum + 1) % 2;
        ConstraintLayout.LayoutParams banner_you = (ConstraintLayout.LayoutParams) binding.bannerYou.getLayoutParams();
        ConstraintLayout.LayoutParams banner_member = (ConstraintLayout.LayoutParams) binding.bannerMember.getLayoutParams();
        ConstraintLayout.LayoutParams top_panel = (ConstraintLayout.LayoutParams) binding.topPanel.getLayoutParams();
        ConstraintLayout.LayoutParams round_minimize = (ConstraintLayout.LayoutParams) binding.roundMinimize.getLayoutParams();
        if (panelNum % 2 != 0) {
            banner_member.topToBottom = binding.topPanel.getId();
            banner_member.bottomToTop = binding.bannerYou.getId();
            banner_you.topToBottom = binding.bannerMember.getId();
            banner_you.bottomToTop = binding.roundMinimize.getId();
            top_panel.bottomToTop = binding.bannerMember.getId();
            round_minimize.topToBottom = binding.bannerYou.getId();
        }
        else {
            banner_you.topToBottom = binding.topPanel.getId();
            banner_you.bottomToTop = binding.bannerMember.getId();
            banner_member.topToBottom = binding.bannerYou.getId();
            banner_member.bottomToTop = binding.roundMinimize.getId();
            top_panel.bottomToTop = binding.bannerYou.getId();
            round_minimize.topToBottom = binding.bannerMember.getId();
        }
        binding.bannerYou.setLayoutParams(banner_you);
        binding.bannerMember.setLayoutParams(banner_member);
        binding.topPanel.setLayoutParams(top_panel);
        binding.roundMinimize.setLayoutParams(round_minimize);
    }
}