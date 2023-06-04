package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Yunshu;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.Car;
import com.mobileclient.service.CarService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class YunshuQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明驾号下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明车牌下拉框
	private Spinner spinner_carObj;
	private ArrayAdapter<String> carObj_adapter;
	private static  String[] carObj_ShowText  = null;
	private List<Car> carList = null; 
	/*车辆管理业务逻辑层*/
	private CarService carService = new CarService();
	// 声明运输货物输入框
	private EditText ET_yshw;
	// 声明起始地输入框
	private EditText ET_qsd;
	// 声明目的地输入框
	private EditText ET_mudidi;
	/*查询过滤条件保存到这个对象中*/
	private Yunshu queryConditionYunshu = new Yunshu();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.yunshu_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置运输单查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的用户
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount+1];
		userObj_ShowText[0] = "不限制";
		for(int i=1;i<=userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i-1).getJiahao();
		} 
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置驾号下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionYunshu.setUserObj(userInfoList.get(arg2-1).getJiahao()); 
				else
					queryConditionYunshu.setUserObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		spinner_carObj = (Spinner) findViewById(R.id.Spinner_carObj);
		// 获取所有的车辆
		try {
			carList = carService.QueryCar(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int carCount = carList.size();
		carObj_ShowText = new String[carCount+1];
		carObj_ShowText[0] = "不限制";
		for(int i=1;i<=carCount;i++) { 
			carObj_ShowText[i] = carList.get(i-1).getCarNo();
		} 
		// 将可选内容与ArrayAdapter连接起来
		carObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, carObj_ShowText);
		// 设置车牌下拉列表的风格
		carObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_carObj.setAdapter(carObj_adapter);
		// 添加事件Spinner事件监听
		spinner_carObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionYunshu.setCarObj(carList.get(arg2-1).getCarId()); 
				else
					queryConditionYunshu.setCarObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_carObj.setVisibility(View.VISIBLE);
		ET_yshw = (EditText) findViewById(R.id.ET_yshw);
		ET_qsd = (EditText) findViewById(R.id.ET_qsd);
		ET_mudidi = (EditText) findViewById(R.id.ET_mudidi);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionYunshu.setYshw(ET_yshw.getText().toString());
					queryConditionYunshu.setQsd(ET_qsd.getText().toString());
					queryConditionYunshu.setMudidi(ET_mudidi.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionYunshu", queryConditionYunshu);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
