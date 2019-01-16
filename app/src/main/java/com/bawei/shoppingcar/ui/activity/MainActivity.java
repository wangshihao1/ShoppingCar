package com.bawei.shoppingcar.ui.activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.bawei.shoppingcar.R;
import com.bawei.shoppingcar.data.bean.ShoppingCarBean;
import com.bawei.shoppingcar.di.contract.IContract;
import com.bawei.shoppingcar.di.presenter.IShowDataPresenterImp;
import com.bawei.shoppingcar.ui.adapter.ShopPingCarAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IContract.IShowView,View.OnClickListener {


    @BindView(R.id.cycle)
    RecyclerView cycle;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    @BindView(R.id.ck_select)
    CheckBox ckSelect;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.btn_js)
    Button btnJs;
    private ShopPingCarAdapter shopPingCarAdapter;
    private IShowDataPresenterImp showDataPresenterImp;
    private Context mContext;
    private List<ShoppingCarBean.DataBean> dataBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        showDataPresenterImp = new IShowDataPresenterImp();
        showDataPresenterImp.atteachView(this);
        showDataPresenterImp.responseData();
    }




    @Override
    public void showData(String data) {
        ckSelect.setOnCheckedChangeListener(null);
        //全选反选的处理
        ckSelect.setOnClickListener(this);
        //数据解析展示
        ShoppingCarBean carBean = new Gson().fromJson(data, ShoppingCarBean.class);
        //外层商家条目的数据源
        dataBeans = carBean.getData();
        //TODO 不建议这样处理，手动移除第一个条目
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        cycle.setLayoutManager(layoutManager);
        //设置适配器
        shopPingCarAdapter = new ShopPingCarAdapter(R.layout.item_title, dataBeans);
        cycle.setAdapter(shopPingCarAdapter);
        shopPingCarAdapter.setBusinessListener(new ShopPingCarAdapter.onBusinessListener() {
            @Override
            public void onCallBack() {
                boolean result = true;
                for (int i = 0; i <dataBeans.size() ; i++) {
                    //外层选中状态
                    boolean checked = dataBeans.get(i).getBusinessChecked();
                    result = result&checked;
                    for (int j = 0; j <dataBeans.get(i).getList().size(); j++) {
                        //里层选中状态
                        boolean goodsChecked = dataBeans.get(i).getList().get(j).getGoodsChecked();
                        result = result&goodsChecked;
                    }
                }
                ckSelect.setChecked(result);
                //计算总价
                totalCountPrice();
            }
        });
    }

    private void totalCountPrice() {
        //对总价进行计算
        double totalCount = 0 ;
        //外层条目
        for (int i = 0; i <dataBeans.size() ; i++) {
           //里层条目
            for (int j = 0; j <dataBeans.get(i).getList().size() ; j++) {
                //判断内层条目是否勾选
                if (dataBeans.get(i).getList().get(j).getGoodsChecked()== true){
                       //获取商品价格
                    double price = dataBeans.get(i).getList().get(j).getPrice();
                    int number = dataBeans.get(i).getList().get(j).getDefalutNumber();
                    double goodsprice = price * number;
                    totalCount = totalCount+goodsprice;
                }
            }
        }
                  tvTotal.setText("总价："+String.valueOf(totalCount));
    }

    @Override
    public void onClick(View v) {
        //全选逻辑的处理
        for (int i = 0; i <dataBeans.size() ; i++) {
            //首先让外层的商家条目全部选中
             dataBeans.get(i).setBusinessChecked(ckSelect.isChecked());
            for (int j = 0; j <dataBeans.get(i).getList().size(); j++) {
                //然后让里层的商品条目全部选中
                dataBeans.get(i).getList().get(j).setGoodsChecked(ckSelect.isChecked());
            }
        }
                   // 全选计算总价 刷新
                  shopPingCarAdapter.notifyDataSetChanged();
                     totalCountPrice();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showDataPresenterImp.dectachView(this);
    }


}
