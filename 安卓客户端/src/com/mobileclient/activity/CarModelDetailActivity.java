package com.mobileclient.activity;

import java.util.Date;
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
public class CarModelDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明车型id控件
	private TextView TV_modelId;
	// 声明车型名称控件
	private TextView TV_modelName;
	/* 要保存的车型信息 */
	CarModel carModel = new CarModel(); 
	/* 车型管理业务逻辑层 */
	private CarModelService carModelService = new CarModelService();
	private int modelId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.carmodel_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看车型详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_modelId = (TextView) findViewById(R.id.TV_modelId);
		TV_modelName = (TextView) findViewById(R.id.TV_modelName);
		Bundle extras = this.getIntent().getExtras();
		modelId = extras.getInt("modelId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CarModelDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    carModel = carModelService.GetCarModel(modelId); 
		this.TV_modelId.setText(carModel.getModelId() + "");
		this.TV_modelName.setText(carModel.getModelName());
	} 
}
