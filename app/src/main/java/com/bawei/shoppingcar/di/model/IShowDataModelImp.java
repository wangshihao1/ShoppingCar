package com.bawei.shoppingcar.di.model;

import com.bawei.shoppingcar.data.api.Apis;
import com.bawei.shoppingcar.di.contract.IContract;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class IShowDataModelImp implements IContract.IShowModel {
    @Override
    public void requestData(final onCallBack onCallBack) {
        OkGo.<String>get(Apis.SHOPPINGCART_URL).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                String result = response.body().toString();
                onCallBack.callBack(result);
            }
        });
    }
}
