package com.easyflower.testvideo.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easyflower.testvideo.R;
import com.easyflower.testvideo.utils.DensityUtil;

import java.util.List;

public class HomeItemFunListAdapter extends BaseAdapter {

    private Activity act;
    private List<String> list;

    private String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571376212027&di=d0b71bc5e7ebca762ae3213eabdf86c0&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F4610b912c8fcc3cef70d70409845d688d53f20f7.jpg";
    private RelativeLayout.LayoutParams paramsImg;

    public HomeItemFunListAdapter(Activity act, List<String> list) {
        this.act = act;
        this.list = list;

        int w = DensityUtil.getWidth(act) / 4 - DensityUtil.dip2px(act, 10);
        paramsImg = new RelativeLayout.LayoutParams(w, w);
        paramsImg.addRule(RelativeLayout.CENTER_IN_PARENT);
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.item_home_pager_fun_list_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.item_img.setLayoutParams(paramsImg);
        Glide.with(act).load(url).transform(new GlideCircleTransform(act)).into(holder.item_img);

        holder.item_text.setText("" + list.get(position));

        return convertView;
    }

    class ViewHolder {
        ImageView item_img;

        TextView item_text;

        public ViewHolder(View convertView) {
            item_img = convertView.findViewById(R.id.item_img);
            item_text = convertView.findViewById(R.id.item_text);
        }
    }
}
