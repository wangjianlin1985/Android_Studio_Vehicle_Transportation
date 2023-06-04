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
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
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
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.yunshu_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
	  holder.tv_carObj = (TextView)convertView.findViewById(R.id.tv_carObj);
	  holder.tv_yshw = (TextView)convertView.findViewById(R.id.tv_yshw);
	  holder.tv_zl = (TextView)convertView.findViewById(R.id.tv_zl);
	  holder.tv_xysj = (TextView)convertView.findViewById(R.id.tv_xysj);
	  holder.tv_qsd = (TextView)convertView.findViewById(R.id.tv_qsd);
	  holder.tv_mudidi = (TextView)convertView.findViewById(R.id.tv_mudidi);
	  holder.tv_gonglishu = (TextView)convertView.findViewById(R.id.tv_gonglishu);
	  /*设置各个控件的展示内容*/
	  holder.tv_userObj.setText("驾号：" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getJiahao());
	  holder.tv_carObj.setText("车牌：" + (new CarService()).GetCar(Integer.parseInt(mData.get(position).get("carObj").toString())).getCarNo());
	  holder.tv_yshw.setText("运输货物：" + mData.get(position).get("yshw").toString());
	  holder.tv_zl.setText("重量(吨)：" + mData.get(position).get("zl").toString());
	  holder.tv_xysj.setText("需要时间：" + mData.get(position).get("xysj").toString());
	  holder.tv_qsd.setText("起始地：" + mData.get(position).get("qsd").toString());
	  holder.tv_mudidi.setText("目的地：" + mData.get(position).get("mudidi").toString());
	  holder.tv_gonglishu.setText("公里数：" + mData.get(position).get("gonglishu").toString());
	  /*返回修改好的view*/
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
