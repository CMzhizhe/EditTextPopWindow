package com.example.gao.edittextpopwindow;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * 创建时间: 2017/7/7
 * Gaoxx
 * 注释描述:配置方案的dialog
 */

public class ConfigurationSchemeDialogFragment2 extends DialogFragment implements View.OnClickListener {
    private static String TAG = ConfigurationSchemeDialogFragment2.class.getSimpleName();
    TextView tvStQty;
    TextView tvStPie;
    EditText etQty;
    EditText etPie;
    TextView etPickDate;
    EditText etCarShipNo;
    EditText etDriverName;
    EditText etDriverPhone;
    EditText etDriverId;
    Button btNegativeButton;
    Button btPositiveButton;
    LinearLayout linearLayout;

    private Activity mActivity;
    private View showView;


    PopupWindow carShipNoPopWindow;


    private ConfigurationFilterPopWindAdapter carAdapter;

    private ImageView bt;
    private List<String> carStringList = new ArrayList<>();

    List<String> newCarString  = new ArrayList<>();

    private String stQty;
    private String stPie;

    View view;

    private int width;

    private ListView listView;
    Handler myCarhandler = new Handler();


    public static ConfigurationSchemeDialogFragment2 newInstance(String StQty, String StPie) {
        //创建一个带有参数的Fragment实例
        ConfigurationSchemeDialogFragment2 fragment = new ConfigurationSchemeDialogFragment2();
        Bundle bundle = new Bundle();
        bundle.putString("StQty", StQty);
        bundle.putString("StPie", StQty);
        fragment.setArguments(bundle);//把参数传递给该DialogFragment
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
    }

    /**
     *作者：GaoXiaoXiong
     *创建时间:2017/7/10
     *注释描述:车牌号Runnable，异步刷新界面
     *
     * 关键代码
     */
    Runnable carChangeRunnable = new Runnable() {
        @Override
        public void run() {
            String data = etCarShipNo.getText().toString();
            if (etCarShipNo.getText().length()==0)
                return;
            if (carShipNoPopWindow!=null&&!carShipNoPopWindow.isShowing()){
                carShipNoPopWindow.showAsDropDown(etCarShipNo,0,-3);
            }

            if (carShipNoPopWindow==null){
                width = etCarShipNo.getWidth();
                initPopuWindow();
                carShipNoPopWindow.showAsDropDown(etCarShipNo,0,-3);
            }

            carStringList.clear();//先要清空，不然会叠加
            getNewData("10", data,newCarString);//获取更新数据
            carAdapter.notifyDataSetChanged();//更新
        }
    };

    private void getNewData(String status,String value,List<String> newString){
        if (status.equals("10")){
            for (String s : newString) {
                if (s.contains(value)){
                    carStringList.add(s);
                }
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.popup_configuration_scheme, null);
        view.setBackgroundDrawable(new ColorDrawable(0xb0000000));//背景色,半透明
        this.stQty = getArguments().getString("StQty");
        this.stPie = getArguments().getString("StPie");
        mActivity = getActivity();
        initView();
        return view;
    }

    /**
     * 作者：GaoXiaoXiong
     * 创建时间:2017/7/6
     * 注释描述:初始化视图
     */
    private void initView() {
        tvStQty = (TextView) view.findViewById(R.id.tv_popup_configuration_scheme_stQty);
        tvStPie = (TextView) view.findViewById(R.id.tv_popup_configuration_scheme_stPie);
        etQty = (EditText) view.findViewById(R.id.et_popup_configuration_scheme_Qty);
        etPie = (EditText) view.findViewById(R.id.et_popup_configuration_scheme_pie);
        etPickDate = (TextView) view.findViewById(R.id.et_popup_configuration_scheme_pickDate);
        etCarShipNo = (EditText) view.findViewById(R.id.et_popup_configuration_scheme_carShipNo);
        etDriverName = (EditText) view.findViewById(R.id.et_popup_configuration_scheme_driverName);
        etDriverPhone = (EditText) view.findViewById(R.id.et_popup_configuration_scheme_driverPhone);
        etDriverId = (EditText) view.findViewById(R.id.et_popup_configuration_scheme_driverId);
        btNegativeButton = (Button) view.findViewById(R.id.bt_popup_configuration_scheme_negativeButton);
        btPositiveButton = (Button) view.findViewById(R.id.bt_popup_configuration_scheme_positiveButton);
        linearLayout = (LinearLayout) view.findViewById(R.id.ll_popuwin_configuration_scheme_parent);
        bt = (ImageView) view.findViewById(R.id.iv_popup_configuration_scheme_carShipNo);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                width = etCarShipNo.getWidth();
                initPopuWindow();
                popupWindwShowing();
            }
        });

        tvStQty.setText(stQty);
        tvStPie.setText(stPie);
        btNegativeButton.setOnClickListener(this);
        btPositiveButton.setOnClickListener(this);


        for (int i=0;i<30;i++){
            carStringList.add("粤A"+i);
        }



        newCarString.addAll(carStringList);

        etCarShipNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                myCarhandler.post(carChangeRunnable);
            }
        });
    }

    private boolean init = false;

    /**
     * 初始化PopupWindow  关键代码
     */
    private void initPopuWindow(){
        if (!init){
            View view1 = mActivity.getLayoutInflater().inflate(R.layout.popup_options, null);
            listView =  (ListView) view1.findViewById(R.id.list);
            carAdapter = new ConfigurationFilterPopWindAdapter(mActivity, carStringList);
            listView.setAdapter(carAdapter);
            carShipNoPopWindow = new PopupWindow(view1, width, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            carShipNoPopWindow.setBackgroundDrawable(new ColorDrawable(0xb0000000));//背景色,半透明
            carShipNoPopWindow.setFocusable(false);
            carShipNoPopWindow.setOutsideTouchable(false);
            carShipNoPopWindow.setAnimationStyle(R.style.animation_push_from_bottom);
            carShipNoPopWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            carShipNoPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            init = true;

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mActivity, carStringList.get(position), Toast.LENGTH_SHORT).show();
                    carShipNoPopWindow.dismiss();
                }
            });
        }
    }

    /**
     * 显示PopupWindow窗口
     */
    public void popupWindwShowing() {
        carShipNoPopWindow.showAsDropDown(etCarShipNo,0,-3);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_popup_configuration_scheme_negativeButton: {//取消
                if (carShipNoPopWindow!=null&&carShipNoPopWindow.isShowing()){
                    carShipNoPopWindow.dismiss();
                }
                ConfigurationSchemeDialogFragment2.this.dismiss();
            }
            break;

            case R.id.bt_popup_configuration_scheme_positiveButton: {//确认
                if (carShipNoPopWindow!=null&&carShipNoPopWindow.isShowing()){
                    carShipNoPopWindow.dismiss();
                }
                ConfigurationSchemeDialogFragment2.this.dismiss();
            }
            break;
        }
    }


}
