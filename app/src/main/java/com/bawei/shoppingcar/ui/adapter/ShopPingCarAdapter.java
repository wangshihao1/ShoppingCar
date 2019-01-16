package com.bawei.shoppingcar.ui.adapter;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import com.bawei.shoppingcar.R;
import com.bawei.shoppingcar.data.bean.ShoppingCarBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

public class ShopPingCarAdapter extends BaseQuickAdapter<ShoppingCarBean.DataBean,BaseViewHolder> {

     private  onBusinessListener businessListener;

     public interface onBusinessListener{
         void onCallBack();
     }

    public void setBusinessListener(onBusinessListener businessListener) {
        this.businessListener = businessListener;
    }

    public ShopPingCarAdapter(int layoutResId, @Nullable List<ShoppingCarBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ShoppingCarBean.DataBean item) {
             helper.setText(R.id.tv_business_name,item.getSellerName());
             RecyclerView cycleview = helper.getView(R.id.rv_goods);
             final CheckBox cb_business = helper.getView(R.id.cb_business);
             //避免抢占焦点
             cb_business.setOnCheckedChangeListener(null);
            //获取商家条目是否选中状态
             cb_business.setChecked(item.getBusinessChecked());
             //子条目数据源
            List<ShoppingCarBean.DataBean.ListBean> goodslist = item.getList();
            LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
             //设置子条目适配器
            final GoodsAdapter goodsAdapter = new GoodsAdapter(R.layout.item_goods,goodslist);
            cycleview.setLayoutManager(manager);
            cycleview.setAdapter(goodsAdapter);
             //首先处理内部子条目控制外部条目
            goodsAdapter.setGoodsItemListener(new GoodsAdapter.onGoodsItemListener() {
                @Override
                public void onCallBack() {
                    //遍历获取所有子条目，只要有一个未勾选，商品类别也应该是未勾选
                    boolean result = true;

                    for (int i = 0; i <item.getList().size() ; i++) {
                        boolean goodsChecked = item.getList().get(i).getGoodsChecked();
                        result = result&goodsChecked;
                    }
                    cb_business.setChecked(result);
                    //刷新适配器
                    goodsAdapter.notifyDataSetChanged();
                    //把最后的状态进行回传
                    businessListener.onCallBack();
                }
            });
               //然后外层的商品类别条目需要控制里面的商品条目
            cb_business.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取商品类别勾选状态
                    //外层商品类别条目获取的关键：cb_business.isChecked()
                    for (int i = 0; i <item.getList().size() ; i++) {
                             item.getList().get(i).setGoodsChecked(cb_business.isChecked());
                    }
                     item.setBusinessChecked(cb_business.isChecked());
                     notifyDataSetChanged();
                    //把最后的状态进行回传
                     businessListener.onCallBack();
                }
            });
    }
}
