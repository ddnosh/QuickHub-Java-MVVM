package com.androidwind.github.ui.open;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.androidwind.github.R;
import com.androidwind.github.ui.base.BaseFragment;
import com.androidwind.github.ui.find.SearchFragment;
import com.androidwind.github.view.WheelView;

import java.util.Arrays;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class OpenFragment extends BaseFragment {

    @BindView(R.id.btn_select)
    Button mSelect;
    private int currentSelect;
    private SearchFragment mSearchFragment;

    private static final String[] LANGUAGE = new String[]{"JS", "Java", "HTML", "Python", "PHP", "CSS", "C#", "C++", "Ruby", "C", "Kotlin", "Object-C"};

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_open;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        mSearchFragment = new SearchFragment();
        transaction.replace(R.id.fl_open, mSearchFragment);
        transaction.commit(); // 提交创建Fragment请求
    }

    @OnClick({R.id.btn_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_select:
                View outerView = LayoutInflater.from(getActivity()).inflate(R.layout.view_wheel, null);
                WheelView wv = outerView.findViewById(R.id.wheelview_open);
                wv.setOffset(1);
                wv.setItems(Arrays.asList(LANGUAGE));
                wv.setSeletion(currentSelect);
                wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        currentSelect = selectedIndex - 1;
                    }
                });

                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.select_language))
                        .setView(outerView)
                        .setPositiveButton(getResources().getString(R.string.confirm), (dialogInterface, i) -> {
                            mSelect.setText(LANGUAGE[currentSelect]);
                            mSearchFragment.queryWithLanguage(LANGUAGE[currentSelect]);
                        })
                        .show();
                break;
        }
    }
}
