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
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明驾号TextView
	private TextView TV_jiahao;
	// 声明登录密码输入框
	private EditText ET_password;
	// 声明姓名输入框
	private EditText ET_name;
	// 声明性别输入框
	private EditText ET_sex;
	// 声明电话输入框
	private EditText ET_telephone;
	// 声明驾照类型下拉框
	private Spinner spinner_jzTypeObj;
	private ArrayAdapter<String> jzTypeObj_adapter;
	private static  String[] jzTypeObj_ShowText  = null;
	private List<JzType> jzTypeList = null;
	/*驾照类型管理业务逻辑层*/
	private JzTypeService jzTypeService = new JzTypeService();
	// 声明驾龄输入框
	private EditText ET_jialing;
	// 声明家庭地址输入框
	private EditText ET_address;
	protected String carmera_path;
	/*要保存的用户信息*/
	UserInfo userInfo = new UserInfo();
	/*用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();

	private String jiahao;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.userinfo_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑用户信息");
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
		// 获取所有的驾照类型
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
		// 将可选内容与ArrayAdapter连接起来
		jzTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, jzTypeObj_ShowText);
		// 设置图书类别下拉列表的风格
		jzTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_jzTypeObj.setAdapter(jzTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_jzTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				userInfo.setJzTypeObj(jzTypeList.get(arg2).getTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_jzTypeObj.setVisibility(View.VISIBLE);
		ET_jialing = (EditText) findViewById(R.id.ET_jialing);
		ET_address = (EditText) findViewById(R.id.ET_address);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		jiahao = extras.getString("jiahao");
		/*单击修改用户按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取登录密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					userInfo.setPassword(ET_password.getText().toString());
					/*验证获取姓名*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					userInfo.setName(ET_name.getText().toString());
					/*验证获取性别*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					userInfo.setSex(ET_sex.getText().toString());
					/*验证获取电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					userInfo.setTelephone(ET_telephone.getText().toString());
					/*验证获取驾龄*/ 
					if(ET_jialing.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "驾龄输入不能为空!", Toast.LENGTH_LONG).show();
						ET_jialing.setFocusable(true);
						ET_jialing.requestFocus();
						return;	
					}
					userInfo.setJialing(ET_jialing.getText().toString());
					/*验证获取家庭地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(UserInfoEditActivity.this, "家庭地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					userInfo.setAddress(ET_address.getText().toString());
					/*调用业务逻辑层上传用户信息*/
					UserInfoEditActivity.this.setTitle("正在更新用户信息，稍等...");
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

	/* 初始化显示编辑界面的数据 */
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
