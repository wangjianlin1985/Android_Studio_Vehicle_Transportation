package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Yunshu;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.Car;
import com.mobileclient.service.CarService;

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
public class YunshuQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// �����ݺ�������
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*�û�����ҵ���߼���*/
	private UserInfoService userInfoService = new UserInfoService();
	// ��������������
	private Spinner spinner_carObj;
	private ArrayAdapter<String> carObj_adapter;
	private static  String[] carObj_ShowText  = null;
	private List<Car> carList = null; 
	/*��������ҵ���߼���*/
	private CarService carService = new CarService();
	// ����������������
	private EditText ET_yshw;
	// ������ʼ�������
	private EditText ET_qsd;
	// ����Ŀ�ĵ������
	private EditText ET_mudidi;
	/*��ѯ�����������浽���������*/
	private Yunshu queryConditionYunshu = new Yunshu();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.yunshu_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�������䵥��ѯ����");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// ��ȡ���е��û�
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount+1];
		userObj_ShowText[0] = "������";
		for(int i=1;i<=userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i-1).getJiahao();
		} 
		// ����ѡ������ArrayAdapter��������
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// ���üݺ������б�ķ��
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_userObj.setAdapter(userObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionYunshu.setUserObj(userInfoList.get(arg2-1).getJiahao()); 
				else
					queryConditionYunshu.setUserObj("");
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
		carObj_ShowText = new String[carCount+1];
		carObj_ShowText[0] = "������";
		for(int i=1;i<=carCount;i++) { 
			carObj_ShowText[i] = carList.get(i-1).getCarNo();
		} 
		// ����ѡ������ArrayAdapter��������
		carObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, carObj_ShowText);
		// ���ó��������б�ķ��
		carObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_carObj.setAdapter(carObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_carObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionYunshu.setCarObj(carList.get(arg2-1).getCarId()); 
				else
					queryConditionYunshu.setCarObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_carObj.setVisibility(View.VISIBLE);
		ET_yshw = (EditText) findViewById(R.id.ET_yshw);
		ET_qsd = (EditText) findViewById(R.id.ET_qsd);
		ET_mudidi = (EditText) findViewById(R.id.ET_mudidi);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionYunshu.setYshw(ET_yshw.getText().toString());
					queryConditionYunshu.setQsd(ET_qsd.getText().toString());
					queryConditionYunshu.setMudidi(ET_mudidi.getText().toString());
					Intent intent = getIntent();
					//����ʹ��bundle��������������
					Bundle bundle =new Bundle();
					//�����������Ȼ�Ǽ�ֵ�Ե���ʽ
					bundle.putSerializable("queryConditionYunshu", queryConditionYunshu);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
