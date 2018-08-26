package notfastjustfurious.epam.smartparking.Activity;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import notfastjustfurious.epam.smartparking.R;

public class DedicatedUserHome extends AppCompatActivity {
    private TextView slotIDTV, ratingTV, loginTimeTV, logoutTimeTV;
    private Button updateChangesButton, cancelChangesButton, lateButton, dayCancelledButton;
    private ImageButton editLoginButton, editLogoutButton;
    LinearLayout updateButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dedicated_user_home);
        init();
        this.editLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayChangeButton();
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(DedicatedUserHome.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        loginTimeTV.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);
                timePicker.setTitle("Update Login Time");
                timePicker.show();

            }
        });

        this.editLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayChangeButton();
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(DedicatedUserHome.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        logoutTimeTV.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);
                timePicker.setTitle("Update Logout Time");
                timePicker.show();

            }
        });

        this.cancelChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    hideChangeButton();
            }
        });
    }

    private void init() {
        this.slotIDTV = findViewById(R.id.slotIDTextView);
        this.ratingTV = findViewById(R.id.empRatingTextView);
        this.loginTimeTV = findViewById(R.id.loginTimeTextView);
        this.logoutTimeTV = findViewById(R.id.logoutTimeTextView);

        this.editLoginButton = findViewById(R.id.editLoginButton);
        this.editLogoutButton = findViewById(R.id.editLogoutButton);

        this.updateButtons = findViewById(R.id.updateButtons);

        this.updateChangesButton = findViewById(R.id.updateChanges);
        this.cancelChangesButton = findViewById(R.id.cancelChanges);
        this.lateButton = findViewById(R.id.lateButton);
        this.dayCancelledButton = findViewById(R.id.dayCanceled);
    }

    private void displayChangeButton() {
        Log.d("Display Change Button", "In the function");
        updateButtons.setVisibility(View.VISIBLE);
    }

    private void hideChangeButton() {
        Log.d("Hiding Change Button", "In the function");
        updateButtons.setVisibility(View.GONE);
    }
}
