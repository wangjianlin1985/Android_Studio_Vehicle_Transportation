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
	// �������ذ�ť
	private Button btnReturn;
	// ��������id�ؼ�
	private TextView TV_modelId;
	// �����������ƿؼ�
	private TextView TV_modelName;
	/* Ҫ����ĳ�����Ϣ */
	CarModel carModel = new CarModel(); 
	/* ���͹���ҵ���߼��� */
	private CarModelService carModelService = new CarModelService();
	private int modelId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.carmodel_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴��������");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
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
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    carModel = carModelService.GetCarModel(modelId); 
		this.TV_modelId.setText(carModel.getModelId() + "");
		this.TV_modelName.setText(carModel.getModelName());
	} 
}
