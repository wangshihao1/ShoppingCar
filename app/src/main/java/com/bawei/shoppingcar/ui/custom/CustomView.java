package com.bawei.shoppingcar.ui.custom;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.bawei.shoppingcar.R;
public class CustomView extends LinearLayout implements View.OnClickListener {

    private Button add;
    private Button del;
    private EditText number;
    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View rootview = LayoutInflater.from(context).inflate(R.layout.custom_view, this);
         del = rootview.findViewById(R.id.btn_del);
         add = rootview.findViewById(R.id.btn_add);
         number = rootview.findViewById(R.id.et_number);
         del.setOnClickListener(this);
         add.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String numString = number.getText().toString();
        int num = Integer.parseInt(numString);
        switch (v.getId()){
            case R.id.btn_del:
                num = num - 1;
                if (num<0){
                    num = 0;
                    // 数量最小为0
                    number.setText(String.valueOf(num));
                }
                    number.setText(String.valueOf(num));
                //接口回调回传数字
                customListener.onCustomdel(num);
                break;
            case R.id.btn_add:
                num = num+1;
                number.setText(String.valueOf(num));
                //接口回调回传数字
                customListener.onCustomadd(num);
                break;
        }
    }

    private onCustomListener customListener;
    public interface onCustomListener{

        void onCustomdel(int num);

        void onCustomadd(int num);
    }

    public void setCustomListener(onCustomListener customListener) {
        this.customListener = customListener;
    }
}
