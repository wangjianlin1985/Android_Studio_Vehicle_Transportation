package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Car;
import com.mobileclient.service.CarService;
import com.mobileclient.domain.CarModel;
import com.mobileclient.service.CarModelService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class CarDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明车辆id控件
	private TextView TV_carId;
	// 声明车牌控件
	private TextView TV_carNo;
	// 声明车型控件
	private TextView TV_carModel;
	// 声明品牌控件
	private TextView TV_pinpai;
	// 声明油型控件
	private TextView TV_youxing;
	// 声明耗油量控件
	private TextView TV_haoyouliang;
	// 声明车险控件
	private TextView TV_chexian;
	// 声明总里程(公里)控件
	private TextView TV_zonglicheng;
	// 声明维修次数控件
	private TextView TV_wxcs;
	// 声明车辆备注控件
	private TextView TV_carMemo;
	/* 要保存的车辆信息 */
	Car car = new Car(); 
	/* 车辆管理业务逻辑层 */
	private CarService carService = new CarService();
	private CarModelService carModelService = new CarModelService();
	private int carId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.car_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看车辆详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_carId = (TextView) findViewById(R.id.TV_carId);
		TV_carNo = (TextView) findViewById(R.id.TV_carNo);
		TV_carModel = (TextView) findViewById(R.id.TV_carModel);
		TV_pinpai = (TextView) findViewById(R.id.TV_pinpai);
		TV_youxing = (TextView) findViewById(R.id.TV_youxing);
		TV_haoyouliang = (TextView) findViewById(R.id.TV_haoyouliang);
		TV_chexian = (TextView) findViewById(R.id.TV_chexian);
		TV_zonglicheng = (TextView) findViewById(R.id.TV_zonglicheng);
		TV_wxcs = (TextView) findViewById(R.id.TV_wxcs);
		TV_carMemo = (TextView) findViewById(R.id.TV_carMemo);
		Bundle extras = this.getIntent().getExtras();
		carId = extras.getInt("carId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CarDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    car = carService.GetCar(carId); 
		this.TV_carId.setText(car.getCarId() + "");
		this.TV_carNo.setText(car.getCarNo());
		CarModel carModel = carModelService.GetCarModel(car.getCarModel());
		this.TV_carModel.setText(carModel.getModelName());
		this.TV_pinpai.setText(car.getPinpai());
		this.TV_youxing.setText(car.getYouxing());
		this.TV_haoyouliang.setText(car.getHaoyouliang());
		this.TV_chexian.setText(car.getChexian());
		this.TV_zonglicheng.setText(car.getZonglicheng());
		this.TV_wxcs.setText(car.getWxcs());
		this.TV_carMemo.setText(car.getCarMemo());
	} 
}
