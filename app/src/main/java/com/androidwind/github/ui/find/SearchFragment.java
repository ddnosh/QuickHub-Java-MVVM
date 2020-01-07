package com.androidwind.github.ui.find;

import com.androidwind.base.util.StringUtil;
import com.androidwind.github.common.Constant;
import com.androidwind.github.ui.base.BaseRepoFragment;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class SearchFragment extends BaseRepoFragment<SearchViewModel> {

    private String currentLanguage = "Java";
    private String keyword = "language:Java";

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void queryWithLanguage(String selectedLanguage) {
        if (currentLanguage.equals(selectedLanguage)) {
            return;
        }
        this.currentLanguage = selectedLanguage;
        mAdapter.getData().clear();
        page = 1;
        keyword = "language:" + currentLanguage;
        loadData();
    }

    public void query(String keyword) {
        mAdapter.getData().clear();
        page = 1;
        this.keyword = keyword;
        loadData();
    }

    @Override
    protected void loadData() {
        if (StringUtil.isEmpty(keyword)) {
            return;
        }
        mViewModel.search(keyword, page, Constant.PER_PAGE)
                .observe(this, observer);
    }

}
