package com.example.teav.zhihtest.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teav.zhihtest.R;
import com.example.teav.zhihtest.db.ZhihuDB;
import com.example.teav.zhihtest.model.User;

public class Register extends ActionBarActivity {

    private ZhihuDB zhihudb;
    private Toolbar toolbar;
    private EditText edittext_username;
    private EditText edittext_password;
    private Button button_register;
    private User user;
    private boolean isRegisterSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        zhihudb = ZhihuDB.getInstance(this);
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        edittext_username = (EditText) findViewById(R.id.user_register_name);
        edittext_password = (EditText) findViewById(R.id.user_register_password);
        button_register = (Button) findViewById(R.id.button_register);

        toolbar.setTitle(R.string.app_name);//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);

        button_register.setOnClickListener(new RegisterOnClickListener());
    }

    public class RegisterOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!edittext_username.getText().toString().equals("") &&
                    !edittext_password.getText().toString().equals("")) {
                /*Toast.makeText(Register.this, edittext_username.getText().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(Register.this, edittext_password.getText().toString(), Toast.LENGTH_SHORT).show();*/
                user = new User();
                user.setUsername(edittext_username.getText().toString());
                user.setPassword(edittext_password.getText().toString());
            } else {
                Toast.makeText(Register.this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
                return;
        }
            isRegisterSuccess = ! zhihudb.checkUser(user);
            if (isRegisterSuccess) {
                zhihudb.saveUser(user);
                Intent intent = new Intent();
                intent.putExtra("username", user.getUsername());
                setResult(RESULT_OK, intent);
                Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(Register.this, "已存在该用户", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
