package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.JzTypeService;
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

public class UserInfoSimpleAdapter extends SimpleAdapter { 
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

    public UserInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.userinfo_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_jiahao = (TextView)convertView.findViewById(R.id.tv_jiahao);
	  holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
	  holder.tv_sex = (TextView)convertView.findViewById(R.id.tv_sex);
	  holder.tv_telephone = (TextView)convertView.findViewById(R.id.tv_telephone);
	  holder.tv_jzTypeObj = (TextView)convertView.findViewById(R.id.tv_jzTypeObj);
	  holder.tv_jialing = (TextView)convertView.findViewById(R.id.tv_jialing);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_jiahao.setText("�ݺţ�" + mData.get(position).get("jiahao").toString());
	  holder.tv_name.setText("������" + mData.get(position).get("name").toString());
	  holder.tv_sex.setText("�Ա�" + mData.get(position).get("sex").toString());
	  holder.tv_telephone.setText("�绰��" + mData.get(position).get("telephone").toString());
	  holder.tv_jzTypeObj.setText("�������ͣ�" + (new JzTypeService()).GetJzType(Integer.parseInt(mData.get(position).get("jzTypeObj").toString())).getTypeName());
	  holder.tv_jialing.setText("���䣺" + mData.get(position).get("jialing").toString());
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_jiahao;
    	TextView tv_name;
    	TextView tv_sex;
    	TextView tv_telephone;
    	TextView tv_jzTypeObj;
    	TextView tv_jialing;
    }
} 
