package com.vincent.mytv;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.vincent.mytv.adapter.TvInfoAdapter;
import com.vincent.mytv.model.TvInfo;
import com.vincent.mytv.utils.MsgUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TvInfoAdapter.OnRecyclerViewListener {
    private static final String TAG = "MainActivity";
    ActionBarDrawerToggle mAbToggle;
    RecyclerView mRecyclerView;
    List<TvInfo> mTvInfos = new ArrayList<>();
    TvInfoAdapter mTvInfoAdapter;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        query(Constant.TYPE_MOVIE);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mAbToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.exit, R.string.exit);
        drawer.setDrawerListener(mAbToggle);
        mAbToggle.syncState();
        //设定NavigationView菜单的选择事件
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_tvlist);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mTvInfoAdapter = new TvInfoAdapter(mTvInfos, this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mTvInfoAdapter);
        mTvInfoAdapter.setOnRecyclerViewListener(this);
    }

    private void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void showContent() {
        mProgressBar.setVisibility(View.GONE);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void query(String type) {
        showLoading();
        BmobQuery<TvInfo> bmobQuery = new BmobQuery<TvInfo>();
        bmobQuery.addWhereEqualTo("type", type);
        bmobQuery.findObjects(new FindListener<TvInfo>() {

            @Override
            public void done(List<TvInfo> list, BmobException e) {
                if (e == null) {
                    mTvInfos.clear();
                    mTvInfos.addAll(list);
                    mTvInfoAdapter.setData(mTvInfos);
                    mTvInfoAdapter.notifyDataSetChanged();
                    showContent();
                } else {
                    MsgUtil.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    showContent();
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_movie) {
            query(Constant.TYPE_MOVIE);
        } else if (id == R.id.nav_local) {
            query(Constant.TYPE_LOCAL);
        } else if (id == R.id.nav_tv) {
            query(Constant.TYPE_TV);
        } else if (id == R.id.nav_hk) {
            query(Constant.TYPE_HK);
        } else if (id == R.id.nav_music) {
            query(Constant.TYPE_MUSIC);
        } else if (id == R.id.nav_cr) {
            query(Constant.TYPE_CR);
        } else if (id == R.id.nav_test) {
            query(Constant.TYPE_TEST);
        } else if (id == R.id.nav_foreign) {
            query(Constant.TYPE_FOREIGN);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        //关闭侧滑菜单
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mAbToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mAbToggle.syncState();
    }

    @Override
    public void onItemClick(int position) {
        Intent mIntent = new Intent(this, PlayActivity.class);
        mIntent.putExtra(Constant.TV_NAME, mTvInfos.get(position).getName());
        mIntent.putExtra(Constant.TV_URL, mTvInfos.get(position).getUrl());
        startActivity(mIntent);

    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }
}
