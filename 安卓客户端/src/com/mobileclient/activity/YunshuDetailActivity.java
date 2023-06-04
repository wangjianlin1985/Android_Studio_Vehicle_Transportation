package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Yunshu;
import com.mobileclient.service.YunshuService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.Car;
import com.mobileclient.service.CarService;
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
public class YunshuDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ������¼id�ؼ�
	private TextView TV_yunshuId;
	// �����ݺſؼ�
	private TextView TV_userObj;
	// �������ƿؼ�
	private TextView TV_carObj;
	// �����������ؼ�
	private TextView TV_yshw;
	// ��������(��)�ؼ�
	private TextView TV_zl;
	// ������Ҫʱ��ؼ�
	private TextView TV_xysj;
	// ������ʼ�ؿؼ�
	private TextView TV_qsd;
	// ����Ŀ�ĵؿؼ�
	private TextView TV_mudidi;
	// �����������ؼ�
	private TextView TV_gonglishu;
	// �������䱸ע�ؼ�
	private TextView TV_yunshuMemo;
	/* Ҫ��������䵥��Ϣ */
	Yunshu yunshu = new Yunshu(); 
	/* ���䵥����ҵ���߼��� */
	private YunshuService yunshuService = new YunshuService();
	private UserInfoService userInfoService = new UserInfoService();
	private CarService carService = new CarService();
	private int yunshuId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.yunshu_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴���䵥����");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_yunshuId = (TextView) findViewById(R.id.TV_yunshuId);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_carObj = (TextView) findViewById(R.id.TV_carObj);
		TV_yshw = (TextView) findViewById(R.id.TV_yshw);
		TV_zl = (TextView) findViewById(R.id.TV_zl);
		TV_xysj = (TextView) findViewById(R.id.TV_xysj);
		TV_qsd = (TextView) findViewById(R.id.TV_qsd);
		TV_mudidi = (TextView) findViewById(R.id.TV_mudidi);
		TV_gonglishu = (TextView) findViewById(R.id.TV_gonglishu);
		TV_yunshuMemo = (TextView) findViewById(R.id.TV_yunshuMemo);
		Bundle extras = this.getIntent().getExtras();
		yunshuId = extras.getInt("yunshuId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				YunshuDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    yunshu = yunshuService.GetYunshu(yunshuId); 
		this.TV_yunshuId.setText(yunshu.getYunshuId() + "");
		UserInfo userObj = userInfoService.GetUserInfo(yunshu.getUserObj());
		this.TV_userObj.setText(userObj.getJiahao());
		Car carObj = carService.GetCar(yunshu.getCarObj());
		this.TV_carObj.setText(carObj.getCarNo());
		this.TV_yshw.setText(yunshu.getYshw());
		this.TV_zl.setText(yunshu.getZl());
		this.TV_xysj.setText(yunshu.getXysj());
		this.TV_qsd.setText(yunshu.getQsd());
		this.TV_mudidi.setText(yunshu.getMudidi());
		this.TV_gonglishu.setText(yunshu.getGonglishu());
		this.TV_yunshuMemo.setText(yunshu.getYunshuMemo());
	} 
}
