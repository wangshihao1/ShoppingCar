package com.bawei.shopping.di.model;

import com.bawei.shopping.data.api.Apis;
import com.bawei.shopping.di.contract.IContract;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class IShowViewModelImp implements IContract.IShowModel {
    @Override
    public void requestData(final onCallBackListener callBackListener) {
        OkGo.<String>get(Apis.TEST_URL).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                String result = response.body().toString();
                callBackListener.onCallBack(result);
            }
        });
    }
}
