package com.example.caokun.fellowsearch.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.caokun.fellowsearch.R;
import com.example.caokun.fellowsearch.common.Presenter;
import com.example.caokun.fellowsearch.common.ui.PActivity;
import com.example.caokun.fellowsearch.model.FellowApiClient;
import com.example.caokun.fellowsearch.model.FellowfindController;
import com.example.caokun.fellowsearch.model.Institute;
import com.example.caokun.fellowsearch.model.Major;
import com.example.caokun.fellowsearch.model.Province;
import com.example.caokun.fellowsearch.model.RefreshEvent;
import com.example.caokun.fellowsearch.model.Senior;
import com.example.caokun.fellowsearch.presenter.FellowPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends PActivity<FellowPresenter> implements FellowfindController{
    @InjectView(R.id.textprovince)
    AutoCompleteTextView editProvince;
    @InjectView(R.id.textInstitute)
    AutoCompleteTextView editInstitute;
    @InjectView(R.id.textMajor)
    AutoCompleteTextView editMajor;
    @InjectView(R.id.textSenior)
    AutoCompleteTextView editSenior;

    @InjectView(R.id.main_toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.finder)
    Button button;
    @InjectView(R.id.fellow_icon)
    ImageView fellow_icon;
    @InjectView(R.id.provinceView)
    ImageView provinceview;
    @InjectView(R.id.instituteView)
            ImageView instituteview;
    @InjectView(R.id.majorView)
            ImageView majorview;
    @InjectView(R.id.seniorView)
            ImageView seniorview;

    String  user_province;
    String user_institute;
    String  user_major;
    String  user_senior;
    List<String> provinces=new ArrayList<>();
    List<String> seniors=new ArrayList<>();
    List<String> institutes=new ArrayList<>();
    List<String> majors=new ArrayList<>();
    @Override
    protected int getLayout(){
        return R.layout.activity_main;
    }
    @Override
    protected void actionStart(Context context){
    }
    @Override
    protected int getStatusbarColor() {
        return R.color.bus_primary_color;
    }

    @Override
    protected Toolbar getToolbar() {
        mToolbar.setTitle("老乡查询");

        return mToolbar;
    }

    @Override
    protected void preInitView(){
        fellow_icon.setImageResource(R.drawable.fellow_icon);
         provinceview.setImageResource(R.drawable.province);
        instituteview.setImageResource(R.drawable.institute);
        majorview.setImageResource(R.drawable.major);
        seniorview.setImageResource(R.drawable.senior);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onEvent(RefreshEvent event){
        Log.d("event", "onEvent: ok.......");
    }

    @Override
    protected void initView() {
        mPresenter.getProvince();
        editProvince.addTextChangedListener(provincewatcher);
        editInstitute.addTextChangedListener(institutewatcher);
        editMajor.addTextChangedListener(majorwatcher);
        editSenior.addTextChangedListener(seniorwatcher);
        setautoadapter();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,FellowFindActivity.class);
                intent.putExtra("province",user_province);
                intent.putExtra("institute",user_institute);
                intent.putExtra("major",user_major);
                intent.putExtra("senior",user_senior);
                startActivity(intent);
            }
        });

    }
    public void setautoadapter(){
        ArrayAdapter<String> provinceadapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,provinces);
        editProvince.setAdapter(provinceadapter);
        ArrayAdapter<String> senioeradapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,seniors);
        editSenior.setAdapter(senioeradapter);
        ArrayAdapter<String> instituteadapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,institutes);
        editInstitute.setAdapter(instituteadapter);
        ArrayAdapter<String> majoradapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,majors);
        editMajor.setAdapter(majoradapter);
    }

    public  void onActionStart(Context context){
    }
    @Override
    protected FellowPresenter getPresenter() {
        return new FellowPresenter(this, this);
    }

    private TextWatcher provincewatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
                   user_province=editable.toString();
            seniors.clear();
            institutes.clear();
            mPresenter.getSenior(user_province);
            mPresenter.getInstitute(user_province);
        }
    };
    private TextWatcher institutewatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            user_institute=editable.toString();
            majors.clear();
            mPresenter.getMajor(user_province,user_institute);

        }
    };
    private TextWatcher majorwatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            user_major=editable.toString();

        }
    }; private TextWatcher seniorwatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            user_senior=editable.toString();

        }
    };
    @Override
    public void bindAllProvince(List<Province> provinces) {
        for(int i=0;i<provinces.size();i++) {
            this.provinces.add(provinces.get(i).provincename);
        }
    }

    @Override
    public void bindAllMajor(List<Major> majors) {
        for(int i=0;i<majors.size();i++) {
            this.majors.add(majors.get(i).majorname);
        }
    }

    @Override
    public void bindAllInstitute(List<Institute> institutes) {
        for(int i=0;i<institutes.size();i++) {
            this.institutes.add(institutes.get(i).collegename);
        }
    }

    @Override
    public void bindAllSenior(List<Senior> seniors) {
        for(int i=0;i<seniors.size();i++) {
             this.seniors.add(seniors.get(i).seniorhigh);
        }
    }

}
