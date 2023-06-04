package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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

public class CarModelEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ��������idTextView
	private TextView TV_modelId;
	// �����������������
	private EditText ET_modelName;
	protected String carmera_path;
	/*Ҫ����ĳ�����Ϣ*/
	CarModel carModel = new CarModel();
	/*���͹���ҵ���߼���*/
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
		setContentView(R.layout.carmodel_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�༭������Ϣ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_modelId = (TextView) findViewById(R.id.TV_modelId);
		ET_modelName = (EditText) findViewById(R.id.ET_modelName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		modelId = extras.getInt("modelId");
		/*�����޸ĳ��Ͱ�ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��������*/ 
					if(ET_modelName.getText().toString().equals("")) {
						Toast.makeText(CarModelEditActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_modelName.setFocusable(true);
						ET_modelName.requestFocus();
						return;	
					}
					carModel.setModelName(ET_modelName.getText().toString());
					/*����ҵ���߼����ϴ�������Ϣ*/
					CarModelEditActivity.this.setTitle("���ڸ��³�����Ϣ���Ե�...");
					String result = carModelService.UpdateCarModel(carModel);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    carModel = carModelService.GetCarModel(modelId);
		this.TV_modelId.setText(modelId+"");
		this.ET_modelName.setText(carModel.getModelName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
