package com.bawei.shoppingcar.di.presenter;

import com.bawei.shoppingcar.di.contract.IContract;
import com.bawei.shoppingcar.di.model.IShowDataModelImp;

import java.lang.ref.SoftReference;

public class IShowDataPresenterImp implements IContract.IShowPresenter<IContract.IShowView> {
     private IContract.IShowView showView;
     private IContract.IShowModel showModel;
             SoftReference<IContract.IShowView> reference;

    @Override
    public void atteachView(IContract.IShowView iShowView) {
        this.showView = iShowView;
        reference = new SoftReference<>(showView);
        showModel = new IShowDataModelImp();
    }

    @Override
    public void dectachView(IContract.IShowView iShowView) {
           reference.clear();
    }

    @Override
    public void responseData() {
          showModel.requestData(new IContract.IShowModel.onCallBack() {
              @Override
              public void callBack(String requestData) {
                  showView.showData(requestData);
              }
          });
    }
}
