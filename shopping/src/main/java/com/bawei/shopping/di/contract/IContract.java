package com.bawei.shopping.di.contract;

public interface IContract {

    public interface IShowView{

        void showData(String datas);
    }

    public interface IShowPresenter<IShowView>{

        void atteachView(IShowView showView);
        void dettachView(IShowView showView);
        void responseData();
    }

    public interface IShowModel{

        public interface onCallBackListener{
            void onCallBack(String requestData);
        }

        void requestData(onCallBackListener callBackListener);
    }

}
