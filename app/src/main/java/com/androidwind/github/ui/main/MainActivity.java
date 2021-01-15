package com.androidwind.github.ui.main;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidwind.base.common.Const;
import com.androidwind.base.module.EventCenter;
import com.androidwind.github.R;
import com.androidwind.github.common.App;
import com.androidwind.github.common.Constant;
import com.androidwind.github.module.QuickModule;
import com.androidwind.github.ui.about.AboutActivity;
import com.androidwind.github.ui.base.MVVMActivity;
import com.androidwind.github.ui.find.FindFragment;
import com.androidwind.github.ui.history.HistoryFragment;
import com.androidwind.github.ui.open.OpenFragment;
import com.androidwind.github.ui.profile.ProfileActivity;
import com.androidwind.github.ui.starred.StarredFragment;
import com.androidwind.github.ui.trends.TrendsFragment;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class MainActivity extends MVVMActivity<MainViewModel> implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.vp_main)
    ViewPager mViewPager;

    @BindView(R.id.tl_main)
    TabLayout mTabLayout;

    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;

    @BindView(R.id.nv_main)
    NavigationView mNavigationView;

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    ImageView mHeader;
    TextView mName, mSignature;

    private FmPagerAdapter pagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] tabs;
    private boolean isRetry;

    @Override
    protected boolean isBindEventBus() {
        return true;
    }

    @Override
    protected boolean isLoadDefaultTitleBar() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        tabs = getResources().getStringArray(R.array.tab_name);
        //init views
        View view = mNavigationView.getHeaderView(0);
        mHeader = view.findViewById(R.id.iv_nav_header_avatar);
        mName = view.findViewById(R.id.iv_nav_header_name);
        mSignature = view.findViewById(R.id.iv_nav_header_signature);
        //init drawer and toolbar
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
        //init tablayout
        for (int i = 0; i < tabs.length; i++) {
            switch (i) {
                case 0:
                    fragments.add(new TrendsFragment());
                    break;
                case 1:
                    fragments.add(new OpenFragment());
                    break;
                case 2:
                    fragments.add(new FindFragment());
                    break;
            }
            mTabLayout.addTab(mTabLayout.newTab());
        }

        mTabLayout.setupWithViewPager(mViewPager, false);
        pagerAdapter = new FmPagerAdapter(fragments, getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(tabs.length);
        mViewPager.setAdapter(pagerAdapter);
        for (int i = 0; i < tabs.length; i++) {
            mTabLayout.getTabAt(i).setText(tabs[i]);
        }
        //init login data
        if (App.sLastLoginUser != null) {
            getUserInfo();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_history) {
            readyGo(HistoryFragment.class);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_account:
                readyGo(ProfileActivity.class);
                break;
            case R.id.nav_starred:
                readyGo(StarredFragment.class);
                break;
            case R.id.nav_about:
                readyGo(AboutActivity.class);
        }
        return true;
    }

    class FmPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        public FmPagerAdapter(List<Fragment> fragmentList, FragmentManager fm) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.fragmentList = fragmentList;
        }

        @Override
        public int getCount() {
            return fragmentList != null && !fragmentList.isEmpty() ? fragmentList.size() : 0;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {
        if (eventCenter.getEventCode() == Const.RECEIVER_NETWORK_CONNECTED) {
//            if (App.sGithubAuth == null) {
//                isRetry = true;
//                getGitAuth();
//            }
        }
    }

    //需要登录后查询
    private void getUserInfo() {
        mViewModel.getGithubUser().observe(this, result -> {
            if (result.showSuccess()) {
                if (result.data != null) {
                    App.sGithubUser = result.data;
                    mSignature.setText(result.data.getBio());

                    QuickModule.imageProcessor().loadNet(App.sGithubUser.getAvatar_url(), mHeader);
                    mName.setText(App.sGithubUser.getName());

                    EventBus.getDefault().post(new EventCenter<>(Constant.EVENTBUS_EVENTCODE, App.sGithubUser.getName()));
                }
            }
            if (result.showError()) {
                dismissLoadingDialog();
                ToastUtils.showShort(result.msg);
            }
        });
    }

    private long DOUBLE_CLICK_TIME = 0L;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - DOUBLE_CLICK_TIME) > 2000) {
                ToastUtils.showShort(getResources().getString(R.string.exit_hint));
                DOUBLE_CLICK_TIME = currentTime;
            } else {
                System.gc();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
