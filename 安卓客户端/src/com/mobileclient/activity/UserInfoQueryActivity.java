package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.domain.JzType;
import com.mobileclient.service.JzTypeService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class UserInfoQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// �����ݺ������
	private EditText ET_jiahao;
	// �������������
	private EditText ET_name;
	// �����绰�����
	private EditText ET_telephone;
	// ������������������
	private Spinner spinner_jzTypeObj;
	private ArrayAdapter<String> jzTypeObj_adapter;
	private static  String[] jzTypeObj_ShowText  = null;
	private List<JzType> jzTypeList = null; 
	/*�������͹���ҵ���߼���*/
	private JzTypeService jzTypeService = new JzTypeService();
	/*��ѯ�����������浽���������*/
	private UserInfo queryConditionUserInfo = new UserInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.userinfo_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�����û���ѯ����");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_jiahao = (EditText) findViewById(R.id.ET_jiahao);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		spinner_jzTypeObj = (Spinner) findViewById(R.id.Spinner_jzTypeObj);
		// ��ȡ���еļ�������
		try {
			jzTypeList = jzTypeService.QueryJzType(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int jzTypeCount = jzTypeList.size();
		jzTypeObj_ShowText = new String[jzTypeCount+1];
		jzTypeObj_ShowText[0] = "������";
		for(int i=1;i<=jzTypeCount;i++) { 
			jzTypeObj_ShowText[i] = jzTypeList.get(i-1).getTypeName();
		} 
		// ����ѡ������ArrayAdapter��������
		jzTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, jzTypeObj_ShowText);
		// ���ü������������б�ķ��
		jzTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_jzTypeObj.setAdapter(jzTypeObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_jzTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionUserInfo.setJzTypeObj(jzTypeList.get(arg2-1).getTypeId()); 
				else
					queryConditionUserInfo.setJzTypeObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_jzTypeObj.setVisibility(View.VISIBLE);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionUserInfo.setJiahao(ET_jiahao.getText().toString());
					queryConditionUserInfo.setName(ET_name.getText().toString());
					queryConditionUserInfo.setTelephone(ET_telephone.getText().toString());
					Intent intent = getIntent();
					//����ʹ��bundle��������������
					Bundle bundle =new Bundle();
					//�����������Ȼ�Ǽ�ֵ�Ե���ʽ
					bundle.putSerializable("queryConditionUserInfo", queryConditionUserInfo);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
