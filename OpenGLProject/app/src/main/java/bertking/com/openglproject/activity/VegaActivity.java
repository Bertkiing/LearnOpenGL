package bertking.com.openglproject.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bertking.com.openglproject.R;
import bertking.com.openglproject.bean.StockBean;
import bertking.com.openglproject.util.ScreenUtil;
import bertking.com.openglproject.util.vega.VegaLayoutManager;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 参考链接：https://github.com/xmuSistone/VegaLayoutManager
 */

public class VegaActivity extends AppCompatActivity {

    @BindView(R.id.main_position_view)
    View         mMainPositionView;
    @BindView(R.id.main_toolbar)
    Toolbar      mMainToolbar;
    @BindView(R.id.main_recycler_view)
    RecyclerView mMainRecyclerView;
    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;
    @BindColor(R.color.red)
    int          redColor;
    @BindColor(R.color.green)
    int          greenColor;

    private List<StockBean> dataList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vega);
        ButterKnife.bind(this);


        // 1. 沉浸式状态栏 + dark模式
        View positionView = findViewById(R.id.main_position_view);
        boolean immerse = ScreenUtil.immerseStatusBar(this);
        boolean darkMode = ScreenUtil.setDarkMode(this);
        if (immerse) {
            ViewGroup.LayoutParams lp = positionView.getLayoutParams();
            lp.height = ScreenUtil.getStatusBarHeight(this);
            positionView.setLayoutParams(lp);
            if (!darkMode) {
                positionView.setBackgroundColor(Color.BLACK);
            }
        } else {
            positionView.setVisibility(View.GONE);
        }

        // 2. toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 3. recyclerView数据填充
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new VegaLayoutManager());
        RecyclerView.Adapter adapter = getAdapter();
        recyclerView.setAdapter(adapter);

        redColor = getResources().getColor(R.color.red);
        greenColor = getResources().getColor(R.color.green);

        prepareDataList();
        adapter.notifyDataSetChanged();
    }


    private void prepareDataList() {
        for (int i = 0; i < 5; i++) {
            dataList.add(new StockBean("Google Inc.", 921.59f, 1, "+6.59 (+0.72%)"));
            dataList.add(new StockBean("Apple Inc.", 158.73f, 1, "+0.06 (+0.04%)"));
            dataList.add(new StockBean("Vmware Inc.", 109.74f, -1, "-0.24 (-0.22%)"));
            dataList.add(new StockBean("Microsoft Inc.", 75.44f, 1, "+0.28 (+0.37%)"));
            dataList.add(new StockBean("Facebook Inc.", 172.52f, 1, "+2.51 (+1.48%)"));
            dataList.add(new StockBean("IBM Inc.", 144.40f, -1, "-0.15 (-0.10%)"));
            dataList.add(new StockBean("Alibaba Inc.", 180.04f, 1, "+0.06 (+0.03%)"));
            dataList.add(new StockBean("Tencent Inc.", 346.400f, 1, "+2.200 (+0.64%)"));
            dataList.add(new StockBean("Baidu Inc.", 237.92f, -1, "-1.15 (-0.48%)"));
            dataList.add(new StockBean("Amazon Inc.", 969.47f, -1, "-4.72 (-0.48%)"));
            dataList.add(new StockBean("Oracle Inc.", 48.03f, -1, "-0.30 (-0.62%)"));
            dataList.add(new StockBean("Intel Inc.", 37.22f, 1, "+0.22 (+0.61%)"));
            dataList.add(new StockBean("Cisco Systems Inc.", 32.49f, -1, "-0.03 (-0.08%)"));
            dataList.add(new StockBean("Qualcomm Inc.", 52.30f, 1, "+0.05 (+0.10%)"));
            dataList.add(new StockBean("Sony Inc.", 37.65f, -1, "-0.74 (-1.93%)"));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private RecyclerView.Adapter getAdapter() {
        final LayoutInflater inflater = LayoutInflater.from(this);
        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = inflater.inflate(R.layout.item_rega, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                MyViewHolder myHolder = (MyViewHolder) holder;
                myHolder.bindData(dataList.get(position));
            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }
        };
        return adapter;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  nameTv;
        TextView  currentPriceTv;
        ImageView trendFlagIv;
        TextView  grossTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.nameTv = (TextView) itemView.findViewById(R.id.item_name_tv);
            this.currentPriceTv = (TextView) itemView.findViewById(R.id.item_current_price);
            this.trendFlagIv = (ImageView) itemView.findViewById(R.id.item_trend_flag);
            this.grossTv = (TextView) itemView.findViewById(R.id.item_gross);
        }

        public void bindData(StockBean StockBean) {
            nameTv.setText(StockBean.getName());
            currentPriceTv.setText("$" + StockBean.getPrice());
            trendFlagIv.setImageResource(StockBean.getFlag() > 0 ? R.mipmap.up_red : R.mipmap.down_green);
            grossTv.setText(StockBean.getGross());
            grossTv.setTextColor(StockBean.getFlag() > 0 ? redColor : greenColor);
        }
    }
}
