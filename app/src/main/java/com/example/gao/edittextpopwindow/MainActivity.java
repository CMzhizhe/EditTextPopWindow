package com.example.gao.edittextpopwindow;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt = (Button) this.findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction mFragTransaction = getFragmentManager().beginTransaction();
                Fragment fragment = getFragmentManager().findFragmentByTag("dialogFragment");
                if (fragment != null) {
                    //为了不重复显示dialog，在显示对话框之前移除正在显示的对话框
                    mFragTransaction.remove(fragment);
                }
                ConfigurationSchemeDialogFragment2 dialogFragment = ConfigurationSchemeDialogFragment2.newInstance("1","1");
                dialogFragment.show(mFragTransaction, "dialogFragment");
            }
        });
    }
}
