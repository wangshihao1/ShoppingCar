package com.bawei.shoppingcar.di.contract;

public interface IContract {

    public interface IShowView{
        void showData(String data);
    }

    public interface IShowPresenter< T extends IShowView>{

        void atteachView(T t);
        void dectachView(T t);
        void responseData();

    }

    public interface IShowModel{

        public interface onCallBack{
            void callBack(String requestData);
        }

        void requestData(onCallBack onCallBack);
    }
}
