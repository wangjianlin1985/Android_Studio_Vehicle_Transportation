package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.Car;
import com.chengxusheji.domain.Yunshu;

@Service @Transactional
public class YunshuDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddYunshu(Yunshu yunshu) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(yunshu);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Yunshu> QueryYunshuInfo(UserInfo userObj,Car carObj,String yshw,String qsd,String mudidi,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Yunshu yunshu where 1=1";
    	if(null != userObj && !userObj.getJiahao().equals("")) hql += " and yunshu.userObj.jiahao='" + userObj.getJiahao() + "'";
    	if(null != carObj && carObj.getCarId()!=0) hql += " and yunshu.carObj.carId=" + carObj.getCarId();
    	if(!yshw.equals("")) hql = hql + " and yunshu.yshw like '%" + yshw + "%'";
    	if(!qsd.equals("")) hql = hql + " and yunshu.qsd like '%" + qsd + "%'";
    	if(!mudidi.equals("")) hql = hql + " and yunshu.mudidi like '%" + mudidi + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List yunshuList = q.list();
    	return (ArrayList<Yunshu>) yunshuList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Yunshu> QueryYunshuInfo(UserInfo userObj,Car carObj,String yshw,String qsd,String mudidi) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Yunshu yunshu where 1=1";
    	if(null != userObj && !userObj.getJiahao().equals("")) hql += " and yunshu.userObj.jiahao='" + userObj.getJiahao() + "'";
    	if(null != carObj && carObj.getCarId()!=0) hql += " and yunshu.carObj.carId=" + carObj.getCarId();
    	if(!yshw.equals("")) hql = hql + " and yunshu.yshw like '%" + yshw + "%'";
    	if(!qsd.equals("")) hql = hql + " and yunshu.qsd like '%" + qsd + "%'";
    	if(!mudidi.equals("")) hql = hql + " and yunshu.mudidi like '%" + mudidi + "%'";
    	Query q = s.createQuery(hql);
    	List yunshuList = q.list();
    	return (ArrayList<Yunshu>) yunshuList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Yunshu> QueryAllYunshuInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Yunshu";
        Query q = s.createQuery(hql);
        List yunshuList = q.list();
        return (ArrayList<Yunshu>) yunshuList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(UserInfo userObj,Car carObj,String yshw,String qsd,String mudidi) {
        Session s = factory.getCurrentSession();
        String hql = "From Yunshu yunshu where 1=1";
        if(null != userObj && !userObj.getJiahao().equals("")) hql += " and yunshu.userObj.jiahao='" + userObj.getJiahao() + "'";
        if(null != carObj && carObj.getCarId()!=0) hql += " and yunshu.carObj.carId=" + carObj.getCarId();
        if(!yshw.equals("")) hql = hql + " and yunshu.yshw like '%" + yshw + "%'";
        if(!qsd.equals("")) hql = hql + " and yunshu.qsd like '%" + qsd + "%'";
        if(!mudidi.equals("")) hql = hql + " and yunshu.mudidi like '%" + mudidi + "%'";
        Query q = s.createQuery(hql);
        List yunshuList = q.list();
        recordNumber = yunshuList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Yunshu GetYunshuByYunshuId(int yunshuId) {
        Session s = factory.getCurrentSession();
        Yunshu yunshu = (Yunshu)s.get(Yunshu.class, yunshuId);
        return yunshu;
    }

    /*����Yunshu��Ϣ*/
    public void UpdateYunshu(Yunshu yunshu) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(yunshu);
    }

    /*ɾ��Yunshu��Ϣ*/
    public void DeleteYunshu (int yunshuId) throws Exception {
        Session s = factory.getCurrentSession();
        Object yunshu = s.load(Yunshu.class, yunshuId);
        s.delete(yunshu);
    }

}
