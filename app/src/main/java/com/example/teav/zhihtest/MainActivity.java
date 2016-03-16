package com.example.teav.zhihtest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teav.zhihtest.Activity.Login;
import com.example.teav.zhihtest.Activity.RefreshableView;
import com.example.teav.zhihtest.Activity.addProblem;
import com.example.teav.zhihtest.db.ZhihuDB;
import com.example.teav.zhihtest.model.Problem;
import com.example.teav.zhihtest.tool.DividerItemDecoration;

import java.util.List;
import java.util.*;
import java.util.Map;



public class MainActivity extends ActionBarActivity{

    private static int REQUEST = 1;

    private ZhihuDB zhihudb;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView lvLeftMenu;
    private RecyclerView Main_Menu;
    private SimpleAdapter left_menu_adapter;
    //private SimpleAdapter main_menu_adapter;
    private MyRecyclerViewAdapter main_menu_adapter;
    private List<Map<String, Object>> main_menu_list;
    private List<Map<String, Object>> left_menu_list;
    private List<Problem> problem_list;
    private ImageView user_show_image;
    private TextView user_show_name;
    private boolean isLogin = false;
    private FloatingActionButton addProblemButton;
   // private RefreshableView refreshableView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//登陆成功
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.REQUEST && resultCode == RESULT_OK) {
            Bundle b = data.getExtras();
            user_show_image.setImageResource(R.drawable.head);
            user_show_name.setText(b.getString("username"));
            isLogin = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zhihudb = ZhihuDB.getInstance(this);
        Main_Menu = (RecyclerView) findViewById(R.id.RecyclerView);
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        lvLeftMenu = (ListView) findViewById(R.id.lv_left_menu);
        user_show_image = (ImageView) findViewById(R.id.user_show_icon);
        user_show_name = (TextView) findViewById(R.id.user_show_name);
        addProblemButton = (FloatingActionButton) findViewById(R.id.addProblemButton);
       // refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        initData();

        toolbar.setTitle(R.string.app_name);//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //设置菜单列表
        left_menu_adapter = new SimpleAdapter(this, left_menu_list, R.layout.leftmenu_list_item,
                new String[] {"text", "img"}, new int[] {R.id.leftmenu_text, R.id.leftmenu_image});
        lvLeftMenu.setAdapter(left_menu_adapter);//设置侧边栏菜单
        //lvLeftMenu.setDivider(null);

       /* main_menu_adapter = new SimpleAdapter(this, main_menu_list, R.layout.main_list_item,
                new String[] {"text", "img"}, new int[] {R.id.mainlist_item_text, R.id.mainlist_item_image});*/

        //Main_Menu.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        Main_Menu.setLayoutManager(layoutManager);
        //Main_Menu.setAdapter(main_menu_adapter = new MyRecyclerViewAdapter());//设置问题页面菜单
        Main_Menu.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));

        user_show_image.setOnClickListener(new MyClickListener());
        user_show_name.setOnClickListener(new MyClickListener());
        addProblemButton.setOnClickListener(new MyClickListener());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                Log.d("Swipe", "Refreshing Number");
                ( new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        initData();
                    }
                }, 1000);
            }
        });
    }

    private class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.user_show_icon:
                case R.id.user_show_name:
                    if (!isLogin) {
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        MainActivity.this.startActivityForResult(intent, REQUEST);
                    } else {
                        //用户页面
                    }
                    break;
                case R.id.addProblemButton:
                    if (!isLogin) {
                        Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();

                    } else {
                        Intent intent = new Intent(MainActivity.this, addProblem.class);
                        MainActivity.this.startActivity(intent);
                    }
                    break;
            }
        }
    }

    public void initData() {

        problem_list = zhihudb.getAllproblem();
        /*Problem problem = new Problem(1, "第一个问题");
        zhihudb.saveProblem(problem);
        problem.setProblemText("第二个问题");
        zhihudb.saveProblem(problem);
        problem.setProblemText("第三个问题");
        zhihudb.saveProblem(problem);*/

        main_menu_list = new ArrayList<Map<String, Object>>();
        for (Problem p : problem_list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", p.getProblemText());
            map.put("img", R.drawable.ic_launcher);
            main_menu_list.add(map);
        }
        Main_Menu.setAdapter(main_menu_adapter = new MyRecyclerViewAdapter());//设置问题页面菜单

        left_menu_list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("text", "知否广场");
        map.put("img", R.drawable.ic_launcher);
        left_menu_list.add(map);
        map = new HashMap<String, Object>();
        map.put("text", "知否广场");
        map.put("img", R.drawable.ic_launcher);
        left_menu_list.add(map);
        map = new HashMap<String, Object>();
        map.put("text", "知否广场");
        map.put("img", R.drawable.ic_launcher);
        left_menu_list.add(map);
    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    MainActivity.this).inflate(R.layout.main_list_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            Problem problem = problem_list.get(position);
            holder.tv.setText(problem.getProblemText());
            holder.im.setImageResource(R.drawable.ic_launcher);
        }

        @Override
        public int getItemCount()
        {
            return problem_list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            ImageView im;
            TextView tv;

            public MyViewHolder(View view)
            {
                super(view);
                im = (ImageView) view.findViewById(R.id.mainlist_item_image);
                tv = (TextView)  view.findViewById(R.id.mainlist_item_text);
            }


    }

}

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
