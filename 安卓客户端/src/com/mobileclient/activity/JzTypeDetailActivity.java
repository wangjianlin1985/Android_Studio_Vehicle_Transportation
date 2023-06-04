package com.mobileclient.activity;

import java.util.Date;
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
public class JzTypeDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ��������id�ؼ�
	private TextView TV_typeId;
	// �����������ƿؼ�
	private TextView TV_typeName;
	/* Ҫ����ļ���������Ϣ */
	JzType jzType = new JzType(); 
	/* �������͹���ҵ���߼��� */
	private JzTypeService jzTypeService = new JzTypeService();
	private int typeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.jztype_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴������������");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_typeId = (TextView) findViewById(R.id.TV_typeId);
		TV_typeName = (TextView) findViewById(R.id.TV_typeName);
		Bundle extras = this.getIntent().getExtras();
		typeId = extras.getInt("typeId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				JzTypeDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    jzType = jzTypeService.GetJzType(typeId); 
		this.TV_typeId.setText(jzType.getTypeId() + "");
		this.TV_typeName.setText(jzType.getTypeName());
	} 
}
