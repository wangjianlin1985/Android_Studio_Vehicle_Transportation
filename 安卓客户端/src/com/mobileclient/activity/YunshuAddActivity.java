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
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �����ݺ�������
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*�ݺŹ���ҵ���߼���*/
	private UserInfoService userInfoService = new UserInfoService();
	// ��������������
	private Spinner spinner_carObj;
	private ArrayAdapter<String> carObj_adapter;
	private static  String[] carObj_ShowText  = null;
	private List<Car> carList = null;
	/*���ƹ���ҵ���߼���*/
	private CarService carService = new CarService();
	// ����������������
	private EditText ET_yshw;
	// ��������(��)�����
	private EditText ET_zl;
	// ������Ҫʱ�������
	private EditText ET_xysj;
	// ������ʼ�������
	private EditText ET_qsd;
	// ����Ŀ�ĵ������
	private EditText ET_mudidi;
	// ���������������
	private EditText ET_gonglishu;
	// �������䱸ע�����
	private EditText ET_yunshuMemo;
	protected String carmera_path;
	/*Ҫ��������䵥��Ϣ*/
	Yunshu yunshu = new Yunshu();
	/*���䵥����ҵ���߼���*/
	private YunshuService yunshuService = new YunshuService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.yunshu_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("������䵥");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// ��ȡ���еļݺ�
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
		// ����ѡ������ArrayAdapter��������
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// ���������б�ķ��
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_userObj.setAdapter(userObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				yunshu.setUserObj(userInfoList.get(arg2).getJiahao()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_userObj.setVisibility(View.VISIBLE);
		spinner_carObj = (Spinner) findViewById(R.id.Spinner_carObj);
		// ��ȡ���еĳ���
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
		// ����ѡ������ArrayAdapter��������
		carObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, carObj_ShowText);
		// ���������б�ķ��
		carObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_carObj.setAdapter(carObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_carObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				yunshu.setCarObj(carList.get(arg2).getCarId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_carObj.setVisibility(View.VISIBLE);
		ET_yshw = (EditText) findViewById(R.id.ET_yshw);
		ET_zl = (EditText) findViewById(R.id.ET_zl);
		ET_xysj = (EditText) findViewById(R.id.ET_xysj);
		ET_qsd = (EditText) findViewById(R.id.ET_qsd);
		ET_mudidi = (EditText) findViewById(R.id.ET_mudidi);
		ET_gonglishu = (EditText) findViewById(R.id.ET_gonglishu);
		ET_yunshuMemo = (EditText) findViewById(R.id.ET_yunshuMemo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*����������䵥��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�������*/ 
					if(ET_yshw.getText().toString().equals("")) {
						Toast.makeText(YunshuAddActivity.this, "����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_yshw.setFocusable(true);
						ET_yshw.requestFocus();
						return;	
					}
					yunshu.setYshw(ET_yshw.getText().toString());
					/*��֤��ȡ����(��)*/ 
					if(ET_zl.getText().toString().equals("")) {
						Toast.makeText(YunshuAddActivity.this, "����(��)���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_zl.setFocusable(true);
						ET_zl.requestFocus();
						return;	
					}
					yunshu.setZl(ET_zl.getText().toString());
					/*��֤��ȡ��Ҫʱ��*/ 
					if(ET_xysj.getText().toString().equals("")) {
						Toast.makeText(YunshuAddActivity.this, "��Ҫʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_xysj.setFocusable(true);
						ET_xysj.requestFocus();
						return;	
					}
					yunshu.setXysj(ET_xysj.getText().toString());
					/*��֤��ȡ��ʼ��*/ 
					if(ET_qsd.getText().toString().equals("")) {
						Toast.makeText(YunshuAddActivity.this, "��ʼ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_qsd.setFocusable(true);
						ET_qsd.requestFocus();
						return;	
					}
					yunshu.setQsd(ET_qsd.getText().toString());
					/*��֤��ȡĿ�ĵ�*/ 
					if(ET_mudidi.getText().toString().equals("")) {
						Toast.makeText(YunshuAddActivity.this, "Ŀ�ĵ����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_mudidi.setFocusable(true);
						ET_mudidi.requestFocus();
						return;	
					}
					yunshu.setMudidi(ET_mudidi.getText().toString());
					/*��֤��ȡ������*/ 
					if(ET_gonglishu.getText().toString().equals("")) {
						Toast.makeText(YunshuAddActivity.this, "���������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_gonglishu.setFocusable(true);
						ET_gonglishu.requestFocus();
						return;	
					}
					yunshu.setGonglishu(ET_gonglishu.getText().toString());
					/*��֤��ȡ���䱸ע*/ 
					if(ET_yunshuMemo.getText().toString().equals("")) {
						Toast.makeText(YunshuAddActivity.this, "���䱸ע���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_yunshuMemo.setFocusable(true);
						ET_yunshuMemo.requestFocus();
						return;	
					}
					yunshu.setYunshuMemo(ET_yunshuMemo.getText().toString());
					/*����ҵ���߼����ϴ����䵥��Ϣ*/
					YunshuAddActivity.this.setTitle("�����ϴ����䵥��Ϣ���Ե�...");
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
