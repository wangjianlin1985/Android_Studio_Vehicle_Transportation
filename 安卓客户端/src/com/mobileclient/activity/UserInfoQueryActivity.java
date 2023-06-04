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
	// 声明查询按钮
	private Button btnQuery;
	// 声明驾号输入框
	private EditText ET_jiahao;
	// 声明姓名输入框
	private EditText ET_name;
	// 声明电话输入框
	private EditText ET_telephone;
	// 声明驾照类型下拉框
	private Spinner spinner_jzTypeObj;
	private ArrayAdapter<String> jzTypeObj_adapter;
	private static  String[] jzTypeObj_ShowText  = null;
	private List<JzType> jzTypeList = null; 
	/*驾照类型管理业务逻辑层*/
	private JzTypeService jzTypeService = new JzTypeService();
	/*查询过滤条件保存到这个对象中*/
	private UserInfo queryConditionUserInfo = new UserInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.userinfo_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置用户查询条件");
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
		// 获取所有的驾照类型
		try {
			jzTypeList = jzTypeService.QueryJzType(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int jzTypeCount = jzTypeList.size();
		jzTypeObj_ShowText = new String[jzTypeCount+1];
		jzTypeObj_ShowText[0] = "不限制";
		for(int i=1;i<=jzTypeCount;i++) { 
			jzTypeObj_ShowText[i] = jzTypeList.get(i-1).getTypeName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		jzTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, jzTypeObj_ShowText);
		// 设置驾照类型下拉列表的风格
		jzTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_jzTypeObj.setAdapter(jzTypeObj_adapter);
		// 添加事件Spinner事件监听
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
		// 设置默认值
		spinner_jzTypeObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionUserInfo.setJiahao(ET_jiahao.getText().toString());
					queryConditionUserInfo.setName(ET_name.getText().toString());
					queryConditionUserInfo.setTelephone(ET_telephone.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionUserInfo", queryConditionUserInfo);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
