package com.easyflower.testvideo.bannber;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.easyflower.testvideo.LogUtil;
import com.easyflower.testvideo.R;
import com.easyflower.testvideo.adapter.HomeItemFunListAdapter;
import com.easyflower.testvideo.view.MyGridView;

import java.util.List;

public class HomeFunPagerView {
    View view;

    private Activity act;

    private MyGridView home_pager_fun_list;

    private List<String> testList;

    public HomeFunPagerView(Activity act, List<String> testList) {
        this.act = act;
        this.testList = testList;
        initView();

        initData();
    }

    private void initData() {
        HomeItemFunListAdapter adapter = new HomeItemFunListAdapter(act, testList);
        home_pager_fun_list.setAdapter(adapter);
    }

    public void initView() {
        view = View.inflate(act, R.layout.home_pager_view_fun, null);
        home_pager_fun_list = view.findViewById(R.id.home_pager_fun_list);

        home_pager_fun_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                LogUtil.show(" ---------------------- position = " + position);

                if (itemClick != null) {
                    itemClick.onFunItemClick(testList.get(position));
                }

            }
        });
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }


    public onFunViewItemClick itemClick;

    public void setOnFunViewItemClick(onFunViewItemClick l) {
        this.itemClick = l;
    }

    public interface onFunViewItemClick {
        public void onFunItemClick(String s);
    }
}
