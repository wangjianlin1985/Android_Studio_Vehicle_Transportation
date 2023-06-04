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
import com.chengxusheji.domain.JzType;

@Service @Transactional
public class JzTypeDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddJzType(JzType jzType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(jzType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<JzType> QueryJzTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From JzType jzType where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List jzTypeList = q.list();
    	return (ArrayList<JzType>) jzTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<JzType> QueryJzTypeInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From JzType jzType where 1=1";
    	Query q = s.createQuery(hql);
    	List jzTypeList = q.list();
    	return (ArrayList<JzType>) jzTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<JzType> QueryAllJzTypeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From JzType";
        Query q = s.createQuery(hql);
        List jzTypeList = q.list();
        return (ArrayList<JzType>) jzTypeList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From JzType jzType where 1=1";
        Query q = s.createQuery(hql);
        List jzTypeList = q.list();
        recordNumber = jzTypeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public JzType GetJzTypeByTypeId(int typeId) {
        Session s = factory.getCurrentSession();
        JzType jzType = (JzType)s.get(JzType.class, typeId);
        return jzType;
    }

    /*更新JzType信息*/
    public void UpdateJzType(JzType jzType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(jzType);
    }

    /*删除JzType信息*/
    public void DeleteJzType (int typeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object jzType = s.load(JzType.class, typeId);
        s.delete(jzType);
    }

}
