package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.JzType;
import com.mobileclient.service.JzTypeService;
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
public class UserInfoDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// �����ݺſؼ�
	private TextView TV_jiahao;
	// ������¼����ؼ�
	private TextView TV_password;
	// ���������ؼ�
	private TextView TV_name;
	// �����Ա�ؼ�
	private TextView TV_sex;
	// �����绰�ؼ�
	private TextView TV_telephone;
	// �����������Ϳؼ�
	private TextView TV_jzTypeObj;
	// ��������ؼ�
	private TextView TV_jialing;
	// ������ͥ��ַ�ؼ�
	private TextView TV_address;
	/* Ҫ������û���Ϣ */
	UserInfo userInfo = new UserInfo(); 
	/* �û�����ҵ���߼��� */
	private UserInfoService userInfoService = new UserInfoService();
	private JzTypeService jzTypeService = new JzTypeService();
	private String jiahao;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.userinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴�û�����");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_jiahao = (TextView) findViewById(R.id.TV_jiahao);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_name = (TextView) findViewById(R.id.TV_name);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_jzTypeObj = (TextView) findViewById(R.id.TV_jzTypeObj);
		TV_jialing = (TextView) findViewById(R.id.TV_jialing);
		TV_address = (TextView) findViewById(R.id.TV_address);
		Bundle extras = this.getIntent().getExtras();
		jiahao = extras.getString("jiahao");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				UserInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    userInfo = userInfoService.GetUserInfo(jiahao); 
		this.TV_jiahao.setText(userInfo.getJiahao());
		this.TV_password.setText(userInfo.getPassword());
		this.TV_name.setText(userInfo.getName());
		this.TV_sex.setText(userInfo.getSex());
		this.TV_telephone.setText(userInfo.getTelephone());
		JzType jzTypeObj = jzTypeService.GetJzType(userInfo.getJzTypeObj());
		this.TV_jzTypeObj.setText(jzTypeObj.getTypeName());
		this.TV_jialing.setText(userInfo.getJialing());
		this.TV_address.setText(userInfo.getAddress());
	} 
}
