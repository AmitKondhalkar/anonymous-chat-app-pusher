package talentica.com.chatapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import talentica.com.androidstarterkit.R;

/**
 * Created by amitk on 19/01/17.
 */

public class LoginActivity extends AppCompatActivity {
    EditText username;
    Button login;
    String userNameTxt;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle(R.string.login_title);

        username = (EditText) findViewById(R.id.username);
        login = (Button) findViewById(R.id.loginbtn);
        pref = getSharedPreferences("AppPref", MODE_PRIVATE);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                userNameTxt = username.getText().toString();

                SharedPref.saveUserInSharedPref(getBaseContext(), userNameTxt);
                Intent chatActivity = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(chatActivity);
                finish();
            }
        });
    }
}

