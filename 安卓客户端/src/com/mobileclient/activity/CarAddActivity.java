package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class CarAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �������������
	private EditText ET_carNo;
	// ��������������
	private Spinner spinner_carModel;
	private ArrayAdapter<String> carModel_adapter;
	private static  String[] carModel_ShowText  = null;
	private List<CarModel> carModelList = null;
	/*���͹���ҵ���߼���*/
	private CarModelService carModelService = new CarModelService();
	// ����Ʒ�������
	private EditText ET_pinpai;
	// �������������
	private EditText ET_youxing;
	// ���������������
	private EditText ET_haoyouliang;
	// �������������
	private EditText ET_chexian;
	// ���������(����)�����
	private EditText ET_zonglicheng;
	// ����ά�޴��������
	private EditText ET_wxcs;
	// ����������ע�����
	private EditText ET_carMemo;
	protected String carmera_path;
	/*Ҫ����ĳ�����Ϣ*/
	Car car = new Car();
	/*��������ҵ���߼���*/
	private CarService carService = new CarService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.car_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("��ӳ���");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_carNo = (EditText) findViewById(R.id.ET_carNo);
		spinner_carModel = (Spinner) findViewById(R.id.Spinner_carModel);
		// ��ȡ���еĳ���
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
		// ����ѡ������ArrayAdapter��������
		carModel_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, carModel_ShowText);
		// ���������б�ķ��
		carModel_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_carModel.setAdapter(carModel_adapter);
		// ����¼�Spinner�¼�����
		spinner_carModel.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				car.setCarModel(carModelList.get(arg2).getModelId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_carModel.setVisibility(View.VISIBLE);
		ET_pinpai = (EditText) findViewById(R.id.ET_pinpai);
		ET_youxing = (EditText) findViewById(R.id.ET_youxing);
		ET_haoyouliang = (EditText) findViewById(R.id.ET_haoyouliang);
		ET_chexian = (EditText) findViewById(R.id.ET_chexian);
		ET_zonglicheng = (EditText) findViewById(R.id.ET_zonglicheng);
		ET_wxcs = (EditText) findViewById(R.id.ET_wxcs);
		ET_carMemo = (EditText) findViewById(R.id.ET_carMemo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*������ӳ�����ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ����*/ 
					if(ET_carNo.getText().toString().equals("")) {
						Toast.makeText(CarAddActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_carNo.setFocusable(true);
						ET_carNo.requestFocus();
						return;	
					}
					car.setCarNo(ET_carNo.getText().toString());
					/*��֤��ȡƷ��*/ 
					if(ET_pinpai.getText().toString().equals("")) {
						Toast.makeText(CarAddActivity.this, "Ʒ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_pinpai.setFocusable(true);
						ET_pinpai.requestFocus();
						return;	
					}
					car.setPinpai(ET_pinpai.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_youxing.getText().toString().equals("")) {
						Toast.makeText(CarAddActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_youxing.setFocusable(true);
						ET_youxing.requestFocus();
						return;	
					}
					car.setYouxing(ET_youxing.getText().toString());
					/*��֤��ȡ������*/ 
					if(ET_haoyouliang.getText().toString().equals("")) {
						Toast.makeText(CarAddActivity.this, "���������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_haoyouliang.setFocusable(true);
						ET_haoyouliang.requestFocus();
						return;	
					}
					car.setHaoyouliang(ET_haoyouliang.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_chexian.getText().toString().equals("")) {
						Toast.makeText(CarAddActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_chexian.setFocusable(true);
						ET_chexian.requestFocus();
						return;	
					}
					car.setChexian(ET_chexian.getText().toString());
					/*��֤��ȡ�����(����)*/ 
					if(ET_zonglicheng.getText().toString().equals("")) {
						Toast.makeText(CarAddActivity.this, "�����(����)���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_zonglicheng.setFocusable(true);
						ET_zonglicheng.requestFocus();
						return;	
					}
					car.setZonglicheng(ET_zonglicheng.getText().toString());
					/*��֤��ȡά�޴���*/ 
					if(ET_wxcs.getText().toString().equals("")) {
						Toast.makeText(CarAddActivity.this, "ά�޴������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_wxcs.setFocusable(true);
						ET_wxcs.requestFocus();
						return;	
					}
					car.setWxcs(ET_wxcs.getText().toString());
					/*��֤��ȡ������ע*/ 
					if(ET_carMemo.getText().toString().equals("")) {
						Toast.makeText(CarAddActivity.this, "������ע���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_carMemo.setFocusable(true);
						ET_carMemo.requestFocus();
						return;	
					}
					car.setCarMemo(ET_carMemo.getText().toString());
					/*����ҵ���߼����ϴ�������Ϣ*/
					CarAddActivity.this.setTitle("�����ϴ�������Ϣ���Ե�...");
					String result = carService.AddCar(car);
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
