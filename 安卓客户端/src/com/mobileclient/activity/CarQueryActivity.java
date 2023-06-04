package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Car;
import com.mobileclient.domain.CarModel;
import com.mobileclient.service.CarModelService;

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
public class CarQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
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
	/*��ѯ�����������浽���������*/
	private Car queryConditionCar = new Car();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.car_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("���ó�����ѯ����");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_carNo = (EditText) findViewById(R.id.ET_carNo);
		spinner_carModel = (Spinner) findViewById(R.id.Spinner_carModel);
		// ��ȡ���еĳ���
		try {
			carModelList = carModelService.QueryCarModel(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int carModelCount = carModelList.size();
		carModel_ShowText = new String[carModelCount+1];
		carModel_ShowText[0] = "������";
		for(int i=1;i<=carModelCount;i++) { 
			carModel_ShowText[i] = carModelList.get(i-1).getModelName();
		} 
		// ����ѡ������ArrayAdapter��������
		carModel_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, carModel_ShowText);
		// ���ó��������б�ķ��
		carModel_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_carModel.setAdapter(carModel_adapter);
		// ����¼�Spinner�¼�����
		spinner_carModel.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionCar.setCarModel(carModelList.get(arg2-1).getModelId()); 
				else
					queryConditionCar.setCarModel(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_carModel.setVisibility(View.VISIBLE);
		ET_pinpai = (EditText) findViewById(R.id.ET_pinpai);
		ET_youxing = (EditText) findViewById(R.id.ET_youxing);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionCar.setCarNo(ET_carNo.getText().toString());
					queryConditionCar.setPinpai(ET_pinpai.getText().toString());
					queryConditionCar.setYouxing(ET_youxing.getText().toString());
					Intent intent = getIntent();
					//����ʹ��bundle��������������
					Bundle bundle =new Bundle();
					//�����������Ȼ�Ǽ�ֵ�Ե���ʽ
					bundle.putSerializable("queryConditionCar", queryConditionCar);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
