package notfastjustfurious.epam.smartparking.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import notfastjustfurious.epam.smartparking.R;
import notfastjustfurious.epam.smartparking.services.UserLogin;


public class Login extends AppCompatActivity {

    Button registerButton;
    Button loginButton;
    EditText loginIdText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean loginStatus = Boolean.parseBoolean(sharedPreferences.getString("loginStatus", "false"));
        if (loginStatus) {
            SharedPreferences preferences = getSharedPreferences("userinfo",Context.MODE_PRIVATE);
            String role = preferences.getString("role","Normal");
            if(role.equals("Normal"))
                startActivity(new Intent(Login.this,NormalUserHome.class));
            else
                startActivity(new Intent(Login.this,DedicatedUserHome.class));
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });
    }

    public void init() {
        registerButton = (Button) findViewById(R.id.regiser);
        loginButton = (Button) findViewById(R.id.login);
        loginIdText = (EditText) findViewById(R.id.loginId);
        passwordText = (EditText) findViewById(R.id.password);
    }

    public void performLogin() {
        String userName = loginIdText.getText().toString();
        String password = passwordText.getText().toString();
        if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password))
            new UserLogin().validateUser(userName, password, Login.this);
        else
            Toast.makeText(this, "Please fill all required Details", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
