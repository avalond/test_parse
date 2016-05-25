package com.nuan_nuan.parse_test.timeline;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nuan_nuan.parse_test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin .
 */
public class TestTimeLine extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyRecyclerAdapter adapter;
    List<ItemBean> datas = new ArrayList<>();
    private Context mContext=TestTimeLine.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.time_line);
        InitDatas();


        recyclerView = (RecyclerView) findViewById(R.id.reccycle);
        adapter = new MyRecyclerAdapter(datas, mContext);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 这里用虚拟数据实现，仅供参考
     */
    private void InitDatas() {
        // TODO Auto-generated method stub
        ItemBean item1 = new ItemBean();
        item1.setTitle("提交订单");
        item1.setTime("03-14 08:13");
        item1.setStatu(1);
        ItemBean item2 = new ItemBean();
        item2.setTitle("已支付");
        item2.setTime("03-14 22:32");
        item2.setStatu(1);
        ItemBean item3 = new ItemBean();
        item3.setTitle("商品出库");
        item3.setTime("03-15 00:33");
        item3.setStatu(0);
        ItemBean item4 = new ItemBean();
        item4.setTitle("已签收");
        item4.setTime("03-15 15:55");
        item4.setStatu(0);

        datas.add(item1);
        datas.add(item2);
        datas.add(item3);
        datas.add(item4);
    }
}
