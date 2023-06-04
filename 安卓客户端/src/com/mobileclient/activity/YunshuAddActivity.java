package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Yunshu;
import com.mobileclient.service.YunshuService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.Car;
import com.mobileclient.service.CarService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class YunshuAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明驾号下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*驾号管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明车牌下拉框
	private Spinner spinner_carObj;
	private ArrayAdapter<String> carObj_adapter;
	private static  String[] carObj_ShowText  = null;
	private List<Car> carList = null;
	/*车牌管理业务逻辑层*/
	private CarService carService = new CarService();
	// 声明运输货物输入框
	private EditText ET_yshw;
	// 声明重量(吨)输入框
	private EditText ET_zl;
	// 声明需要时间输入框
	private EditText ET_xysj;
	// 声明起始地输入框
	private EditText ET_qsd;
	// 声明目的地输入框
	private EditText ET_mudidi;
	// 声明公里数输入框
	private EditText ET_gonglishu;
	// 声明运输备注输入框
	private EditText ET_yunshuMemo;
	protected String carmera_path;
	/*要保存的运输单信息*/
	Yunshu yunshu = new Yunshu();
	/*运输单管理业务逻辑层*/
	private YunshuService yunshuService = new YunshuService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.yunshu_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加运输单");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的驾号
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getJiahao();
		}
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				yunshu.setUserObj(userInfoList.get(arg2).getJiahao()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		spinner_carObj = (Spinner) findViewById(R.id.Spinner_carObj);
		// 获取所有的车牌
		try {
			carList = carService.QueryCar(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int carCount = carList.size();
		carObj_ShowText = new String[carCount];
		for(int i=0;i<carCount;i++) { 
			carObj_ShowText[i] = carList.get(i).getCarNo();
		}
		// 将可选内容与ArrayAdapter连接起来
		carObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, carObj_ShowText);
		// 设置下拉列表的风格
		carObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_carObj.setAdapter(carObj_adapter);
		// 添加事件Spinner事件监听
		spinner_carObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				yunshu.setCarObj(carList.get(arg2).getCarId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_carObj.setVisibility(View.VISIBLE);
		ET_yshw = (EditText) findViewById(R.id.ET_yshw);
		ET_zl = (EditText) findViewById(R.id.ET_zl);
		ET_xysj = (EditText) findViewById(R.id.ET_xysj);
		ET_qsd = (EditText) findViewById(R.id.ET_qsd);
		ET_mudidi = (EditText) findViewById(R.id.ET_mudidi);
		ET_gonglishu = (EditText) findViewById(R.id.ET_gonglishu);
		ET_yunshuMemo = (EditText) findViewById(R.id.ET_yunshuMemo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加运输单按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取运输货物*/ 
					if(ET_yshw.getText().toString().equals("")) {
						Toast.makeText(YunshuAddActivity.this, "运输货物输入不能为空!", Toast.LENGTH_LONG).show();
						ET_yshw.setFocusable(true);
						ET_yshw.requestFocus();
						return;	
					}
					yunshu.setYshw(ET_yshw.getText().toString());
					/*验证获取重量(吨)*/ 
					if(ET_zl.getText().toString().equals("")) {
						Toast.makeText(YunshuAddActivity.this, "重量(吨)输入不能为空!", Toast.LENGTH_LONG).show();
						ET_zl.setFocusable(true);
						ET_zl.requestFocus();
						return;	
					}
					yunshu.setZl(ET_zl.getText().toString());
					/*验证获取需要时间*/ 
					if(ET_xysj.getText().toString().equals("")) {
						Toast.makeText(YunshuAddActivity.this, "需要时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_xysj.setFocusable(true);
						ET_xysj.requestFocus();
						return;	
					}
					yunshu.setXysj(ET_xysj.getText().toString());
					/*验证获取起始地*/ 
					if(ET_qsd.getText().toString().equals("")) {
						Toast.makeText(YunshuAddActivity.this, "起始地输入不能为空!", Toast.LENGTH_LONG).show();
						ET_qsd.setFocusable(true);
						ET_qsd.requestFocus();
						return;	
					}
					yunshu.setQsd(ET_qsd.getText().toString());
					/*验证获取目的地*/ 
					if(ET_mudidi.getText().toString().equals("")) {
						Toast.makeText(YunshuAddActivity.this, "目的地输入不能为空!", Toast.LENGTH_LONG).show();
						ET_mudidi.setFocusable(true);
						ET_mudidi.requestFocus();
						return;	
					}
					yunshu.setMudidi(ET_mudidi.getText().toString());
					/*验证获取公里数*/ 
					if(ET_gonglishu.getText().toString().equals("")) {
						Toast.makeText(YunshuAddActivity.this, "公里数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_gonglishu.setFocusable(true);
						ET_gonglishu.requestFocus();
						return;	
					}
					yunshu.setGonglishu(ET_gonglishu.getText().toString());
					/*验证获取运输备注*/ 
					if(ET_yunshuMemo.getText().toString().equals("")) {
						Toast.makeText(YunshuAddActivity.this, "运输备注输入不能为空!", Toast.LENGTH_LONG).show();
						ET_yunshuMemo.setFocusable(true);
						ET_yunshuMemo.requestFocus();
						return;	
					}
					yunshu.setYunshuMemo(ET_yunshuMemo.getText().toString());
					/*调用业务逻辑层上传运输单信息*/
					YunshuAddActivity.this.setTitle("正在上传运输单信息，稍等...");
					String result = yunshuService.AddYunshu(yunshu);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
