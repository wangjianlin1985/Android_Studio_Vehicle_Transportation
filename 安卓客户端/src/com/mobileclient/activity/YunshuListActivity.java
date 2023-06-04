package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Yunshu;
import com.mobileclient.service.YunshuService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.YunshuSimpleAdapter;
import com.mobileclient.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class YunshuListActivity extends Activity {
	YunshuSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int yunshuId;
	/* ���䵥����ҵ���߼������ */
	YunshuService yunshuService = new YunshuService();
	/*�����ѯ�������������䵥����*/
	private Yunshu queryConditionYunshu;

	private MyProgressDialog dialog; //������	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.yunshu_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//�������ؼ�
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(YunshuListActivity.this, YunshuQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//�˴���requestCodeӦ�������������е��õ�requestCodeһ��
			}
		});
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("���䵥��ѯ�б�");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(YunshuListActivity.this, YunshuAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		
		if(declare.getIdentify().equals("user")) add_btn.setVisibility(View.GONE);
		setViews();
	}

	//���������������secondActivity�з���ʱ���ô˺���
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionYunshu = (Yunshu)extras.getSerializable("queryConditionYunshu");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionYunshu = null;
        	setViews();
        }
    }

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		dialog.show();
		final Handler handler = new Handler();
		new Thread(){
			@Override
			public void run() {
				//�����߳��н����������ݲ���
				list = getDatas();
				//������ʧ��handler��֪ͨ���߳��������
				handler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						adapter = new YunshuSimpleAdapter(YunshuListActivity.this, list,
	        					R.layout.yunshu_list_item,
	        					new String[] { "userObj","carObj","yshw","zl","xysj","qsd","mudidi","gonglishu" },
	        					new int[] { R.id.tv_userObj,R.id.tv_carObj,R.id.tv_yshw,R.id.tv_zl,R.id.tv_xysj,R.id.tv_qsd,R.id.tv_mudidi,R.id.tv_gonglishu,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// ��ӳ������
		lv.setOnCreateContextMenuListener(yunshuListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int yunshuId = Integer.parseInt(list.get(arg2).get("yunshuId").toString());
            	Intent intent = new Intent();
            	intent.setClass(YunshuListActivity.this, YunshuDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("yunshuId", yunshuId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener yunshuListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			Declare declare = (Declare) YunshuListActivity.this.getApplication();
			if(declare.getIdentify().equals("admin")) {
				menu.add(0, 0, 0, "�༭���䵥��Ϣ"); 
				menu.add(0, 1, 0, "ɾ�����䵥��Ϣ");
			}
			
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭���䵥��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��¼id
			yunshuId = Integer.parseInt(list.get(position).get("yunshuId").toString());
			Intent intent = new Intent();
			intent.setClass(YunshuListActivity.this, YunshuEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("yunshuId", yunshuId);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// ɾ�����䵥��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ��¼id
			yunshuId = Integer.parseInt(list.get(position).get("yunshuId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(YunshuListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = yunshuService.DeleteYunshu(yunshuId);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* ��ѯ���䵥��Ϣ */
			List<Yunshu> yunshuList = yunshuService.QueryYunshu(queryConditionYunshu);
			for (int i = 0; i < yunshuList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("yunshuId",yunshuList.get(i).getYunshuId());
				map.put("userObj", yunshuList.get(i).getUserObj());
				map.put("carObj", yunshuList.get(i).getCarObj());
				map.put("yshw", yunshuList.get(i).getYshw());
				map.put("zl", yunshuList.get(i).getZl());
				map.put("xysj", yunshuList.get(i).getXysj());
				map.put("qsd", yunshuList.get(i).getQsd());
				map.put("mudidi", yunshuList.get(i).getMudidi());
				map.put("gonglishu", yunshuList.get(i).getGonglishu());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
