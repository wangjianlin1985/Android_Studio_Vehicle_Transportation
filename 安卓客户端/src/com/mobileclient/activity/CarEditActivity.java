package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Car;
import com.mobileclient.service.CarService;
import com.mobileclient.domain.CarModel;
import com.mobileclient.service.CarModelService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class CarEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明车辆idTextView
	private TextView TV_carId;
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
	// 声明耗油量输入框
	private EditText ET_haoyouliang;
	// 声明车险输入框
	private EditText ET_chexian;
	// 声明总里程(公里)输入框
	private EditText ET_zonglicheng;
	// 声明维修次数输入框
	private EditText ET_wxcs;
	// 声明车辆备注输入框
	private EditText ET_carMemo;
	protected String carmera_path;
	/*要保存的车辆信息*/
	Car car = new Car();
	/*车辆管理业务逻辑层*/
	private CarService carService = new CarService();

	private int carId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.car_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑车辆信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_carId = (TextView) findViewById(R.id.TV_carId);
		ET_carNo = (EditText) findViewById(R.id.ET_carNo);
		spinner_carModel = (Spinner) findViewById(R.id.Spinner_carModel);
		// 获取所有的车型
		try {
			carModelList = carModelService.QueryCarModel(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int carModelCount = carModelList.size();
		carModel_ShowText = new String[carModelCount];
		for(int i=0;i<carModelCount;i++) { 
			carModel_ShowText[i] = carModelList.get(i).getModelName();
		}
		// 将可选内容与ArrayAdapter连接起来
		carModel_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, carModel_ShowText);
		// 设置图书类别下拉列表的风格
		carModel_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_carModel.setAdapter(carModel_adapter);
		// 添加事件Spinner事件监听
		spinner_carModel.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				car.setCarModel(carModelList.get(arg2).getModelId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_carModel.setVisibility(View.VISIBLE);
		ET_pinpai = (EditText) findViewById(R.id.ET_pinpai);
		ET_youxing = (EditText) findViewById(R.id.ET_youxing);
		ET_haoyouliang = (EditText) findViewById(R.id.ET_haoyouliang);
		ET_chexian = (EditText) findViewById(R.id.ET_chexian);
		ET_zonglicheng = (EditText) findViewById(R.id.ET_zonglicheng);
		ET_wxcs = (EditText) findViewById(R.id.ET_wxcs);
		ET_carMemo = (EditText) findViewById(R.id.ET_carMemo);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		carId = extras.getInt("carId");
		/*单击修改车辆按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取车牌*/ 
					if(ET_carNo.getText().toString().equals("")) {
						Toast.makeText(CarEditActivity.this, "车牌输入不能为空!", Toast.LENGTH_LONG).show();
						ET_carNo.setFocusable(true);
						ET_carNo.requestFocus();
						return;	
					}
					car.setCarNo(ET_carNo.getText().toString());
					/*验证获取品牌*/ 
					if(ET_pinpai.getText().toString().equals("")) {
						Toast.makeText(CarEditActivity.this, "品牌输入不能为空!", Toast.LENGTH_LONG).show();
						ET_pinpai.setFocusable(true);
						ET_pinpai.requestFocus();
						return;	
					}
					car.setPinpai(ET_pinpai.getText().toString());
					/*验证获取油型*/ 
					if(ET_youxing.getText().toString().equals("")) {
						Toast.makeText(CarEditActivity.this, "油型输入不能为空!", Toast.LENGTH_LONG).show();
						ET_youxing.setFocusable(true);
						ET_youxing.requestFocus();
						return;	
					}
					car.setYouxing(ET_youxing.getText().toString());
					/*验证获取耗油量*/ 
					if(ET_haoyouliang.getText().toString().equals("")) {
						Toast.makeText(CarEditActivity.this, "耗油量输入不能为空!", Toast.LENGTH_LONG).show();
						ET_haoyouliang.setFocusable(true);
						ET_haoyouliang.requestFocus();
						return;	
					}
					car.setHaoyouliang(ET_haoyouliang.getText().toString());
					/*验证获取车险*/ 
					if(ET_chexian.getText().toString().equals("")) {
						Toast.makeText(CarEditActivity.this, "车险输入不能为空!", Toast.LENGTH_LONG).show();
						ET_chexian.setFocusable(true);
						ET_chexian.requestFocus();
						return;	
					}
					car.setChexian(ET_chexian.getText().toString());
					/*验证获取总里程(公里)*/ 
					if(ET_zonglicheng.getText().toString().equals("")) {
						Toast.makeText(CarEditActivity.this, "总里程(公里)输入不能为空!", Toast.LENGTH_LONG).show();
						ET_zonglicheng.setFocusable(true);
						ET_zonglicheng.requestFocus();
						return;	
					}
					car.setZonglicheng(ET_zonglicheng.getText().toString());
					/*验证获取维修次数*/ 
					if(ET_wxcs.getText().toString().equals("")) {
						Toast.makeText(CarEditActivity.this, "维修次数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_wxcs.setFocusable(true);
						ET_wxcs.requestFocus();
						return;	
					}
					car.setWxcs(ET_wxcs.getText().toString());
					/*验证获取车辆备注*/ 
					if(ET_carMemo.getText().toString().equals("")) {
						Toast.makeText(CarEditActivity.this, "车辆备注输入不能为空!", Toast.LENGTH_LONG).show();
						ET_carMemo.setFocusable(true);
						ET_carMemo.requestFocus();
						return;	
					}
					car.setCarMemo(ET_carMemo.getText().toString());
					/*调用业务逻辑层上传车辆信息*/
					CarEditActivity.this.setTitle("正在更新车辆信息，稍等...");
					String result = carService.UpdateCar(car);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    car = carService.GetCar(carId);
		this.TV_carId.setText(carId+"");
		this.ET_carNo.setText(car.getCarNo());
		for (int i = 0; i < carModelList.size(); i++) {
			if (car.getCarModel() == carModelList.get(i).getModelId()) {
				this.spinner_carModel.setSelection(i);
				break;
			}
		}
		this.ET_pinpai.setText(car.getPinpai());
		this.ET_youxing.setText(car.getYouxing());
		this.ET_haoyouliang.setText(car.getHaoyouliang());
		this.ET_chexian.setText(car.getChexian());
		this.ET_zonglicheng.setText(car.getZonglicheng());
		this.ET_wxcs.setText(car.getWxcs());
		this.ET_carMemo.setText(car.getCarMemo());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
