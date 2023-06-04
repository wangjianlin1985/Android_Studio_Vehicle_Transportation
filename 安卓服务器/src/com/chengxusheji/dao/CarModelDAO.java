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
import com.chengxusheji.domain.CarModel;

@Service @Transactional
public class CarModelDAO {

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
    public void AddCarModel(CarModel carModel) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(carModel);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<CarModel> QueryCarModelInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From CarModel carModel where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List carModelList = q.list();
    	return (ArrayList<CarModel>) carModelList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<CarModel> QueryCarModelInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From CarModel carModel where 1=1";
    	Query q = s.createQuery(hql);
    	List carModelList = q.list();
    	return (ArrayList<CarModel>) carModelList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<CarModel> QueryAllCarModelInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From CarModel";
        Query q = s.createQuery(hql);
        List carModelList = q.list();
        return (ArrayList<CarModel>) carModelList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From CarModel carModel where 1=1";
        Query q = s.createQuery(hql);
        List carModelList = q.list();
        recordNumber = carModelList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public CarModel GetCarModelByModelId(int modelId) {
        Session s = factory.getCurrentSession();
        CarModel carModel = (CarModel)s.get(CarModel.class, modelId);
        return carModel;
    }

    /*更新CarModel信息*/
    public void UpdateCarModel(CarModel carModel) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(carModel);
    }

    /*删除CarModel信息*/
    public void DeleteCarModel (int modelId) throws Exception {
        Session s = factory.getCurrentSession();
        Object carModel = s.load(CarModel.class, modelId);
        s.delete(carModel);
    }

}
