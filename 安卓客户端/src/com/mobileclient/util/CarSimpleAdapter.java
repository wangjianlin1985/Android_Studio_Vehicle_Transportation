package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.CarModelService;
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

public class CarSimpleAdapter extends SimpleAdapter { 
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

    public CarSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.car_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_carNo = (TextView)convertView.findViewById(R.id.tv_carNo);
	  holder.tv_carModel = (TextView)convertView.findViewById(R.id.tv_carModel);
	  holder.tv_pinpai = (TextView)convertView.findViewById(R.id.tv_pinpai);
	  holder.tv_youxing = (TextView)convertView.findViewById(R.id.tv_youxing);
	  holder.tv_haoyouliang = (TextView)convertView.findViewById(R.id.tv_haoyouliang);
	  holder.tv_chexian = (TextView)convertView.findViewById(R.id.tv_chexian);
	  holder.tv_zonglicheng = (TextView)convertView.findViewById(R.id.tv_zonglicheng);
	  holder.tv_wxcs = (TextView)convertView.findViewById(R.id.tv_wxcs);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_carNo.setText("���ƣ�" + mData.get(position).get("carNo").toString());
	  holder.tv_carModel.setText("���ͣ�" + (new CarModelService()).GetCarModel(Integer.parseInt(mData.get(position).get("carModel").toString())).getModelName());
	  holder.tv_pinpai.setText("Ʒ�ƣ�" + mData.get(position).get("pinpai").toString());
	  holder.tv_youxing.setText("���ͣ�" + mData.get(position).get("youxing").toString());
	  holder.tv_haoyouliang.setText("��������" + mData.get(position).get("haoyouliang").toString());
	  holder.tv_chexian.setText("���գ�" + mData.get(position).get("chexian").toString());
	  holder.tv_zonglicheng.setText("�����(����)��" + mData.get(position).get("zonglicheng").toString());
	  holder.tv_wxcs.setText("ά�޴�����" + mData.get(position).get("wxcs").toString());
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_carNo;
    	TextView tv_carModel;
    	TextView tv_pinpai;
    	TextView tv_youxing;
    	TextView tv_haoyouliang;
    	TextView tv_chexian;
    	TextView tv_zonglicheng;
    	TextView tv_wxcs;
    }
} 
