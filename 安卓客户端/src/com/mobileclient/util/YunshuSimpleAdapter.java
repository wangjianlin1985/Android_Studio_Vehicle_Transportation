package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.UserInfoService;
import com.mobileclient.service.CarService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class YunshuSimpleAdapter extends SimpleAdapter { 
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //ͼƬ�첽���������,���ڴ滺����ļ�����
    private SyncImageLoader syncImageLoader;

    public YunshuSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*��һ��װ�����viewʱ=null,���½�һ������inflate��Ⱦһ��view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.yunshu_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
	  holder.tv_carObj = (TextView)convertView.findViewById(R.id.tv_carObj);
	  holder.tv_yshw = (TextView)convertView.findViewById(R.id.tv_yshw);
	  holder.tv_zl = (TextView)convertView.findViewById(R.id.tv_zl);
	  holder.tv_xysj = (TextView)convertView.findViewById(R.id.tv_xysj);
	  holder.tv_qsd = (TextView)convertView.findViewById(R.id.tv_qsd);
	  holder.tv_mudidi = (TextView)convertView.findViewById(R.id.tv_mudidi);
	  holder.tv_gonglishu = (TextView)convertView.findViewById(R.id.tv_gonglishu);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_userObj.setText("�ݺţ�" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getJiahao());
	  holder.tv_carObj.setText("���ƣ�" + (new CarService()).GetCar(Integer.parseInt(mData.get(position).get("carObj").toString())).getCarNo());
	  holder.tv_yshw.setText("������" + mData.get(position).get("yshw").toString());
	  holder.tv_zl.setText("����(��)��" + mData.get(position).get("zl").toString());
	  holder.tv_xysj.setText("��Ҫʱ�䣺" + mData.get(position).get("xysj").toString());
	  holder.tv_qsd.setText("��ʼ�أ�" + mData.get(position).get("qsd").toString());
	  holder.tv_mudidi.setText("Ŀ�ĵأ�" + mData.get(position).get("mudidi").toString());
	  holder.tv_gonglishu.setText("��������" + mData.get(position).get("gonglishu").toString());
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_userObj;
    	TextView tv_carObj;
    	TextView tv_yshw;
    	TextView tv_zl;
    	TextView tv_xysj;
    	TextView tv_qsd;
    	TextView tv_mudidi;
    	TextView tv_gonglishu;
    }
} 
