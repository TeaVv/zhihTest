package com.example.teav.zhihtest.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teav.zhihtest.R;
import com.example.teav.zhihtest.db.ZhihuDB;
import com.example.teav.zhihtest.model.User;


public class Login extends ActionBarActivity {

    private static int REQUEST = 1;

    private ZhihuDB zhihudb;
    private Toolbar toolbar;
    private EditText edittext_username;
    private EditText edittext_password;
    private Button button_login;
    private TextView textview_newuser;
    private User user;
    private boolean isLoginSuccess = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            Bundle b = data.getExtras();
            intent.putExtra("username", b.getString("username"));
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        zhihudb = ZhihuDB.getInstance(this);
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        edittext_username = (EditText) findViewById(R.id.user_login_name);
        edittext_password = (EditText) findViewById(R.id.user_login_password);
        button_login = (Button) findViewById(R.id.button_login);
        textview_newuser = (TextView) findViewById(R.id.textview_newuser);

        toolbar.setTitle(R.string.app_name);//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);

        textview_newuser.setOnClickListener(new MyOnClickListener());
        button_login.setOnClickListener(new MyOnClickListener());

    }

    public class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_login:
                    if (!edittext_username.getText().toString().equals("") &&
                            !edittext_password.getText().toString().equals("")) {
                        user = new User();
                        user.setUsername(edittext_username.getText().toString());
                        user.setPassword(edittext_password.getText().toString());
                    } else {
                        Toast.makeText(Login.this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    isLoginSuccess = zhihudb.checkUser(user);
                    if (isLoginSuccess) {
                        Intent intent = new Intent();
                        intent.putExtra("username", user.getUsername());
                        setResult(RESULT_OK, intent);
                        Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Login.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                       return;
                    }
                    break;
                case R.id.textview_newuser:
                    Intent intent = new Intent(Login.this, Register.class);
                    Login.this.startActivityForResult(intent, REQUEST);
                    break;
            }
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
