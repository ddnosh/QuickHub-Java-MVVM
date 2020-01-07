package com.androidwind.github.ui.find;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.androidwind.github.R;
import com.androidwind.github.ui.base.BaseFragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class FindFragment extends BaseFragment {

    @BindView(R.id.ev_find)
    EditText mEditTextSearch;
    private SearchFragment mSearchFragment;

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        mSearchFragment = new SearchFragment();
        transaction.replace(R.id.fl_find, mSearchFragment);
        transaction.commit(); // 提交创建Fragment请求

        mSearchFragment.setKeyword("");

        mEditTextSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mSearchFragment.query(mEditTextSearch.getText().toString().trim());
                return true;
            }
            return false;
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_find;
    }

}
