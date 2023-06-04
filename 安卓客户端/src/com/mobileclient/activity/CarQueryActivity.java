package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Car;
import com.mobileclient.domain.CarModel;
import com.mobileclient.service.CarModelService;

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
public class CarQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明车牌输入框
	private EditText ET_carNo;
	// 声明车型下拉框
	private Spinner spinner_carModel;
	private ArrayAdapter<String> carModel_adapter;
	private static  String[] carModel_ShowText  = null;
	private List<CarModel> carModelList = null; 
	/*车型管理业务逻辑层*/
	private CarModelService carModelService = new CarModelService();
	// 声明品牌输入框
	private EditText ET_pinpai;
	// 声明油型输入框
	private EditText ET_youxing;
	/*查询过滤条件保存到这个对象中*/
	private Car queryConditionCar = new Car();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.car_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置车辆查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_carNo = (EditText) findViewById(R.id.ET_carNo);
		spinner_carModel = (Spinner) findViewById(R.id.Spinner_carModel);
		// 获取所有的车型
		try {
			carModelList = carModelService.QueryCarModel(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int carModelCount = carModelList.size();
		carModel_ShowText = new String[carModelCount+1];
		carModel_ShowText[0] = "不限制";
		for(int i=1;i<=carModelCount;i++) { 
			carModel_ShowText[i] = carModelList.get(i-1).getModelName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		carModel_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, carModel_ShowText);
		// 设置车型下拉列表的风格
		carModel_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_carModel.setAdapter(carModel_adapter);
		// 添加事件Spinner事件监听
		spinner_carModel.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionCar.setCarModel(carModelList.get(arg2-1).getModelId()); 
				else
					queryConditionCar.setCarModel(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_carModel.setVisibility(View.VISIBLE);
		ET_pinpai = (EditText) findViewById(R.id.ET_pinpai);
		ET_youxing = (EditText) findViewById(R.id.ET_youxing);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionCar.setCarNo(ET_carNo.getText().toString());
					queryConditionCar.setPinpai(ET_pinpai.getText().toString());
					queryConditionCar.setYouxing(ET_youxing.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionCar", queryConditionCar);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
