package com.bawei.shopping.di.presenter;

import com.bawei.shopping.di.contract.IContract;
import com.bawei.shopping.di.model.IShowViewModelImp;

import java.lang.ref.SoftReference;

public class IShowViewPresenterImp implements IContract.IShowPresenter<IContract.IShowView> {

    private IContract.IShowView iShowView;
    private IContract.IShowModel showModel;
    SoftReference<IContract.IShowView> reference;

    @Override
    public void atteachView(IContract.IShowView iShowView) {
        this.iShowView = iShowView;
        reference = new SoftReference<>(iShowView);
        showModel = new IShowViewModelImp();
    }

    @Override
    public void dettachView(IContract.IShowView iShowView) {
             reference.clear();
    }

    @Override
    public void responseData() {
        //TODO
         //showModel.requestData();
    }
}
