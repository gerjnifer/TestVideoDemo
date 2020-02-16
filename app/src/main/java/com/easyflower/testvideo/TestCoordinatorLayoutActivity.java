package com.easyflower.testvideo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TestCoordinatorLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinatorlayout_view);

        testList();
    }

    private void testList() {
        List<List<String>> pagerList = new ArrayList<>();

        List<String> categorys = new ArrayList<>();
        categorys.add("1");
        categorys.add("2");
        categorys.add("3");
        categorys.add("4");
        categorys.add("5");
        categorys.add("6");
        categorys.add("7");
        categorys.add("8");
        categorys.add("9");
        categorys.add("10");
        categorys.add("11");
        categorys.add("12");
        categorys.add("13");
        categorys.add("14");
        categorys.add("15");
        categorys.add("16");
        categorys.add("17");
        categorys.add("18");


        for (int i = 0; i < categorys.size(); i++) {
            LogUtil.show(" ------------------------- categorys = " + categorys.get(i));
        }


        int n = 4;
        int size = categorys.size();
        int remainder = categorys.size() % n;  //(先计算出余数)
        int number = size / n;

        List<String> value = null;
        int offset = 0;
        for (int i = 0; i < number; i++) {
            offset = (i + 1) * n;
            value = categorys.subList(i * n, offset);
            pagerList.add(value);
        }

        if (remainder > 0) {
            value = categorys.subList(offset, size);
            pagerList.add(value);
        }

        for (int i = 0; i < pagerList.size(); i++) {
            List<String> strings = pagerList.get(i);
            for (int j = 0; j < strings.size(); j++) {
                LogUtil.show(" --------------- " + "  i = " + i + "  j = " + strings.get(j));
            }
        }


    }


//    @Override
//    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//        Log.d(TAG, "verticalOffset="+verticalOffset);
//        int offset = Math.abs(verticalOffset);
//        int total = appBarLayout.getTotalScrollRange();
//        int alphaIn = offset;
//        int alphaOut = (200-offset)<0?0:200-offset;
//        int maskColorIn = Color.argb(alphaIn, Color.red(mMaskColor),
//                Color.green(mMaskColor), Color.blue(mMaskColor));
//        int maskColorInDouble = Color.argb(alphaIn*2, Color.red(mMaskColor),
//                Color.green(mMaskColor), Color.blue(mMaskColor));
//        int maskColorOut = Color.argb(alphaOut*2, Color.red(mMaskColor),
//                Color.green(mMaskColor), Color.blue(mMaskColor));
//        if (offset <= total / 2) {
//            tl_expand.setVisibility(View.VISIBLE);
//            tl_collapse.setVisibility(View.GONE);
//            v_expand_mask.setBackgroundColor(maskColorInDouble);
//        } else {
//            tl_expand.setVisibility(View.GONE);
//            tl_collapse.setVisibility(View.VISIBLE);
//            v_collapse_mask.setBackgroundColor(maskColorOut);
//        }
//        v_pay_mask.setBackgroundColor(maskColorIn);
//    }
}
