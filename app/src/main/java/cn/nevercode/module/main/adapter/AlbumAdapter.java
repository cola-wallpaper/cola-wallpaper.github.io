package cn.nevercode.module.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.nevercode.cola.R;
import cn.nevercode.module.main.activity.AlbumActivity;
import cn.nevercode.module.main.model.PapersModel;


public class AlbumAdapter extends BaseAdapter {

    List<PapersModel.DataBean> list;
    Context context;
    private LayoutInflater mInflater;


    public AlbumAdapter(Context context, List<PapersModel.DataBean> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        final PapersModel.DataBean item = list.get(i);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = mInflater.inflate(R.layout.item_album, null);
            viewHolder.img = convertView.findViewById(R.id.img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(item.getUrl()).into(viewHolder.img);
        View finalConvertView = convertView;
        viewHolder.img.setOnClickListener(v -> {
            finalConvertView.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK);
            Intent i1 = new Intent(context, AlbumActivity.class);
            i1.putExtra("img", item.getUrl());
            context.startActivity(i1);
        });
        return convertView;

    }

    static class ViewHolder {
        public ImageView img;
    }

}
