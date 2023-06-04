package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.JzType;
import com.mobileclient.service.JzTypeService;
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

public class UserInfoEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// �����ݺ�TextView
	private TextView TV_jiahao;
	// ������¼���������
	private EditText ET_password;
	// �������������
	private EditText ET_name;
	// �����Ա������
	private EditText ET_sex;
	// �����绰�����
	private EditText ET_telephone;
	// ������������������
	private Spinner spinner_jzTypeObj;
	private ArrayAdapter<String> jzTypeObj_adapter;
	private static  String[] jzTypeObj_ShowText  = null;
	private List<JzType> jzTypeList = null;
	/*�������͹���ҵ���߼���*/
	private JzTypeService jzTypeService = new JzTypeService();
	// �������������
	private EditText ET_jialing;
	// ������ͥ��ַ�����
	private EditText ET_address;
	protected String carmera_path;
	/*Ҫ������û���Ϣ*/
	UserInfo userInfo = new UserInfo();
	/*�û�����ҵ���߼���*/
	private UserInfoService userInfoService = new UserInfoService();

	private String jiahao;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.userinfo_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�༭�û���Ϣ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_jiahao = (TextView) findViewById(R.id.TV_jiahao);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		spinner_jzTypeObj = (Spinner) findViewById(R.id.Spinner_jzTypeObj);
		// ��ȡ���еļ�������
		try {
			jzTypeList = jzTypeService.QueryJzType(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int jzTypeCount = jzTypeList.size();
		jzTypeObj_ShowText = new String[jzTypeCount];
		for(int i=0;i<jzTypeCount;i++) { 
			jzTypeObj_ShowText[i] = jzTypeList.get(i).getTypeName();
		}
		// ����ѡ������ArrayAdapter��������
		jzTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, jzTypeObj_ShowText);
		// ����ͼ����������б�ķ��
		jzTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_jzTypeObj.setAdapter(jzTypeObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_jzTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				userInfo.setJzTypeObj(jzTypeList.get(arg2).getTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_jzTypeObj.setVisibility(View.VISIBLE);
		ET_jialing = (EditText) findViewById(R.id.ET_jialing);
		ET_address = (EditText) findViewById(R.id.ET_address);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		jiahao = extras.getString("jiahao");
		/*�����޸��û���ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��¼����*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "��¼�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					userInfo.setPassword(ET_password.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					userInfo.setName(ET_name.getText().toString());
					/*��֤��ȡ�Ա�*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "�Ա����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					userInfo.setSex(ET_sex.getText().toString());
					/*��֤��ȡ�绰*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "�绰���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					userInfo.setTelephone(ET_telephone.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_jialing.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_jialing.setFocusable(true);
						ET_jialing.requestFocus();
						return;	
					}
					userInfo.setJialing(ET_jialing.getText().toString());
					/*��֤��ȡ��ͥ��ַ*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "��ͥ��ַ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					userInfo.setAddress(ET_address.getText().toString());
					/*����ҵ���߼����ϴ��û���Ϣ*/
					UserInfoEditActivity.this.setTitle("���ڸ����û���Ϣ���Ե�...");
					String result = userInfoService.UpdateUserInfo(userInfo);
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
	    userInfo = userInfoService.GetUserInfo(jiahao);
		this.TV_jiahao.setText(jiahao);
		this.ET_password.setText(userInfo.getPassword());
		this.ET_name.setText(userInfo.getName());
		this.ET_sex.setText(userInfo.getSex());
		this.ET_telephone.setText(userInfo.getTelephone());
		for (int i = 0; i < jzTypeList.size(); i++) {
			if (userInfo.getJzTypeObj() == jzTypeList.get(i).getTypeId()) {
				this.spinner_jzTypeObj.setSelection(i);
				break;
			}
		}
		this.ET_jialing.setText(userInfo.getJialing());
		this.ET_address.setText(userInfo.getAddress());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
