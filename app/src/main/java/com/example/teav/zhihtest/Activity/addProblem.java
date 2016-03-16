package com.example.teav.zhihtest.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teav.zhihtest.R;
import com.example.teav.zhihtest.db.ZhihuDB;
import com.example.teav.zhihtest.model.Problem;

public class addProblem extends AppCompatActivity {

    private EditText problemTitle;
    private EditText problemText;
    private Button confirmProblemButton;
    private ZhihuDB zhihuDB;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);

        zhihuDB = ZhihuDB.getInstance(this);
        toolbar = (Toolbar) findViewById(R.id.ap_toolbar);
        problemTitle = (EditText) findViewById(R.id.problemTitle);
        problemText  = (EditText) findViewById(R.id.problemText);
        confirmProblemButton = (Button) findViewById(R.id.confirmProblemButton);

        toolbar.setTitle(R.string.app_name);//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);

        confirmProblemButton.setOnClickListener(new MyOnClickListener());
    }

    private class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if (problemText.getText().toString().equals("")
                    || problemTitle.getText().toString().equals("")) {
                Toast.makeText(addProblem.this, "请输入内容后发表", Toast.LENGTH_SHORT).show();
            } else {
                Problem problem = new Problem();
                problem.setProblemText(problemText.getText().toString());
                zhihuDB.saveProblem(problem);
                finish();
            }
        }
    }
}
