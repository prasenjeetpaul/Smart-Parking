package notfastjustfurious.epam.smartparking.Activity;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import notfastjustfurious.epam.smartparking.R;
import notfastjustfurious.epam.smartparking.entity.DedicatedUserAttribute;
import notfastjustfurious.epam.smartparking.webio.JSONHelper;
import notfastjustfurious.epam.smartparking.webio.URLHelper;


public class DedicatedUserHome extends AppCompatActivity {
    private TextView slotIDTV, ratingTV, loginTimeTV, logoutTimeTV;
    private Button updateChangesButton, cancelChangesButton, lateButton, dayCancelledButton;
    private ImageButton editLoginButton, editLogoutButton;
    private LinearLayout updateButtons;
    private ProgressDialog progress;
    private DedicatedUserAttribute userAttribute;
    private String empID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dedicated_user_home);
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        empID = sharedPreferences.getString("empID", "100002");
        init();
        setOnClickListeners();

        progress= new ProgressDialog(this);
        progress.setTitle("Refreshing User");
        progress.setMessage("Contacting server for data...");
        progress.setCancelable(false);

        loadUserData();

    }

    private void loadUserData() {
        progress.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLHelper.GET_DEDICATED_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(DedicatedUserHome.this, "User Data Recieved", Toast.LENGTH_SHORT).show();
                userAttribute = JSONHelper.getDedicatedUserAttribute(response);
                Log.d("userAttribute", userAttribute.toString());
                displayData(userAttribute);


                progress.dismiss();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(DedicatedUserHome.this, "Some error occured", Toast.LENGTH_SHORT).show();
                        Log.d("Error Response", error.getMessage());
                        progress.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("empID", empID);
                //params.put("domain", "http://itsalif.info");
                return params;
            }
        };
        queue.add(postRequest);
    }

    private void displayData(DedicatedUserAttribute userAttribute) {
        this.slotIDTV.setText(userAttribute.getSlotID());
        this.ratingTV.setText(userAttribute.getNoOfStars());
        loginTime = userAttribute.getLoginTime();
        logoutTime = userAttribute.getLogoutTime();
        this.loginTimeTV.setText(loginTime);
        this.logoutTimeTV.setText(logoutTime);
        Log.d("failedInst", userAttribute.getFailedInstanceNumber());
        if(userAttribute.getFailedInstanceNumber().equals("0")){
            findViewById(R.id.failedInstanceMessage).setVisibility(View.GONE);
        } else {
            TextView failedInstanceTV = findViewById(R.id.failedInstanceMessage);
            failedInstanceTV.setVisibility(View.VISIBLE);
            int failCount = Integer.parseInt(userAttribute.getFailedInstanceNumber());
            int refillCount = failCount*5;
            failedInstanceTV.setText("You have "+failCount+" failed apperences \n" +
                    "Your star refill limit is "+refillCount+" consequtive sucessful apperences");
        }
        if(userAttribute.isDelay()==null){
            Log.d("isDelay", "is null");
        } else {
            lateButton.setVisibility(View.VISIBLE);
            lateButton.setText("Applied to go late!");
            lateButton.setEnabled(false);

            dayCancelledButton.setVisibility(View.GONE);
            dayCancelledButton.setEnabled(true);
            Log.d("isDelay", userAttribute.isDelay());
        }

        if(userAttribute.getIsFloat()==null){
            Log.d("isFloat", "is null");
        } else {
            dayCancelledButton.setVisibility(View.VISIBLE);
            dayCancelledButton.setText("Applied for leave today!");
            dayCancelledButton.setEnabled(false);
            lateButton.setVisibility(View.GONE);
            lateButton.setEnabled(true);
            Log.d("isDelay", userAttribute.getIsFloat());
        }
        /*Log.d("isDelay", userAttribute.isDelay());
        Log.d("isFlotter", userAttribute.getIsFloat());*/

    }

    private void init() {
        this.slotIDTV = findViewById(R.id.dedEmpIDTextView);
        if(slotIDTV == null){
            Log.d("slotIDTV", "Is Null :(");
        }

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

    private void setOnClickListeners() {
        this.editLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTimeForLoginTime();
            }
        });

        this.editLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTimeForLogoutTime();
            }
        });

        this.cancelChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideChangeButton();
                loadUserData();
            }
        });

        this.updateChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideChangeButton();
                progress.show();
                RequestQueue queue = Volley.newRequestQueue(DedicatedUserHome.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST, URLHelper.EDIT_DEDICATED_USER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(DedicatedUserHome.this, "User Data Recieved", Toast.LENGTH_SHORT).show();
                        if (!response.equals("[]")) {
                            Toast.makeText(DedicatedUserHome.this, "Slot Details Modified Successfully!", Toast.LENGTH_SHORT).show();
                            loadUserData();
                        }

                        progress.dismiss();
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(DedicatedUserHome.this, "Some error occured", Toast.LENGTH_SHORT).show();
                                Log.d("Error Response", error.getMessage());
                                progress.dismiss();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("empID", empID);
                        if(loginTime!=null){
                            params.put("loginTime", loginTime);
                        }
                        if(logoutTime!=null){
                            params.put("logoutTime", logoutTime);
                        }

                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });

        this.lateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.show();
                RequestQueue queue = Volley.newRequestQueue(DedicatedUserHome.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST, URLHelper.DELAY_DEDICATED_SLOT, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(DedicatedUserHome.this, "User Data Recieved", Toast.LENGTH_SHORT).show();
                        if (response.equals("Success")) {
                            Toast.makeText(DedicatedUserHome.this, "Slot Delayed for the day", Toast.LENGTH_SHORT).show();
                            loadUserData();
                        }

                        progress.dismiss();
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(DedicatedUserHome.this, "Some error occured", Toast.LENGTH_SHORT).show();
                                Log.d("Error Response", error.getMessage());
                                progress.dismiss();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        /*SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                        String empID = sharedPreferences.getString("empID", "100001");*/
                        params.put("empID", empID);
                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
        this.dayCancelledButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progress.show();
                RequestQueue queue = Volley.newRequestQueue(DedicatedUserHome.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST, URLHelper.CANCEL_DEDICATED_SLOT, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(DedicatedUserHome.this, "User Data Recieved", Toast.LENGTH_SHORT).show();
                        if(response.equals("Success")) {
                            Toast.makeText(DedicatedUserHome.this, "Slot Canceled for the day", Toast.LENGTH_SHORT).show();
                            loadUserData();
                        }

                        progress.dismiss();
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(DedicatedUserHome.this, "Some error occured", Toast.LENGTH_SHORT).show();
                                Log.d("Error Response", error.getMessage());
                                progress.dismiss();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        /*SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                        String empID = sharedPreferences.getString("empID", "100001");*/
                        params.put("empID", empID);
                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
    }

    private void displayChangeButton() {
        Log.d("Display Change Button", "In the function");
        updateButtons.setVisibility(View.VISIBLE);
    }

    private void hideChangeButton() {
        Log.d("Hiding Change Button", "In the function");
        updateButtons.setVisibility(View.GONE);
    }

    private String loginTime;
    private String logoutTime;
    private void getTimeForLoginTime() {
        displayChangeButton();
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog timePicker;
        timePicker = new TimePickerDialog(DedicatedUserHome.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                loginTime = hourOfDay + ":" + minute + ":00";
                loginTimeTV.setText(loginTime);

            }
        }, hour, minute, true);
        timePicker.setTitle("Update Login Time");
        timePicker.show();
    }

    private void getTimeForLogoutTime() {
        displayChangeButton();
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog timePicker;
        timePicker = new TimePickerDialog(DedicatedUserHome.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                logoutTime = hourOfDay + ":" + minute + ":00";
                logoutTimeTV.setText(logoutTime);
            }
        }, hour, minute, true);
        timePicker.setTitle("Update Logout Time");
        timePicker.show();
    }
}
