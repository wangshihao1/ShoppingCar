package com.bawei.shoppingcar.ui.adapter;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import com.bawei.shoppingcar.R;
import com.bawei.shoppingcar.data.bean.ShoppingCarBean;
import com.bawei.shoppingcar.ui.custom.CustomView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

public class GoodsAdapter extends BaseQuickAdapter<ShoppingCarBean.DataBean.ListBean,BaseViewHolder> {

     private onGoodsItemListener goodsItemListener;
     public interface onGoodsItemListener{
          void onCallBack();
     }

    public void setGoodsItemListener(onGoodsItemListener goodsItemListener) {
        this.goodsItemListener = goodsItemListener;
    }

    public GoodsAdapter(int layoutResId, @Nullable List<ShoppingCarBean.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ShoppingCarBean.DataBean.ListBean item) {

             helper.setText(R.id.tv_goodsPrice,"￥："+item.getPrice());

             helper.setText(R.id.tv_goodsTitle,item.getTitle());

             ImageView iv_icon = helper.getView(R.id.iv_goodsIcon);

             String images = item.getImages();

             String[] sp = images.split("\\|");

             Glide.with(mContext).load(sp[0]).into(iv_icon);

             //避免抢占焦点
             final CheckBox check = helper.getView(R.id.cb_goods);

             check.setOnCheckedChangeListener(null);

             check.setChecked(item.getGoodsChecked());
             // 以接口的方式把状态回传给外层
             check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                 @Override
                 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                     //Bean对象状态进行更新完毕
                     item.setGoodsChecked(isChecked);

                     goodsItemListener.onCallBack();
                 }
             });
             // 加加减减实现
            CustomView customView = helper.getView(R.id.custom_view);

            customView.setCustomListener(new CustomView.onCustomListener() {

                @Override
                public void onCustomdel(int num) {
                    //对新增的字段进行改动
                    item.setDefalutNumber(num);

                    goodsItemListener.onCallBack();

                }

                @Override
                public void onCustomadd(int num) {

                    item.setDefalutNumber(num);

                    goodsItemListener.onCallBack();
                }
            });
    }
}
