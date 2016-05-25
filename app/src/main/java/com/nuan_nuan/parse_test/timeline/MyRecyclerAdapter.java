package com.nuan_nuan.parse_test.timeline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nuan_nuan.parse_test.R;

import java.util.List;

/**
 * Created by kevin .
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
    List<ItemBean> lists;
    Context mContext;

    public MyRecyclerAdapter(List<ItemBean> lists, Context mContext) {
        super();
        this.lists = lists;
        this.mContext = mContext;
    }

    /*
     * 覆盖方法
     */
    @Override
    public int getItemCount() {

        return lists.size();
    }

    /*
     * 覆盖方法
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_view, arg0, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, time;
        View line;

        /**
         * @param itemView
         */
        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.show_title);
            time = (TextView) itemView.findViewById(R.id.show_time);
            line = itemView.findViewById(R.id.line_normal);
        }

    }

    /*
     * 覆盖方法
     */
    @Override
    public void onBindViewHolder(MyViewHolder arg0, int arg1) {

        arg0.title.setText(lists.get(arg1).getTitle());
        arg0.time.setText(lists.get(arg1).getTime());

        //最后一项时，竖线不再显示
        if (arg1 == lists.size() - 1) {
            arg0.line.setVisibility(View.GONE);
        }
    }

}
