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
import com.chengxusheji.domain.Car;

@Service @Transactional
public class CarDAO {

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
    public void AddCar(Car car) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(car);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Car> QueryCarInfo(String carNo,CarModel carModel,String pinpai,String youxing,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Car car where 1=1";
    	if(!carNo.equals("")) hql = hql + " and car.carNo like '%" + carNo + "%'";
    	if(null != carModel && carModel.getModelId()!=0) hql += " and car.carModel.modelId=" + carModel.getModelId();
    	if(!pinpai.equals("")) hql = hql + " and car.pinpai like '%" + pinpai + "%'";
    	if(!youxing.equals("")) hql = hql + " and car.youxing like '%" + youxing + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List carList = q.list();
    	return (ArrayList<Car>) carList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Car> QueryCarInfo(String carNo,CarModel carModel,String pinpai,String youxing) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Car car where 1=1";
    	if(!carNo.equals("")) hql = hql + " and car.carNo like '%" + carNo + "%'";
    	if(null != carModel && carModel.getModelId()!=0) hql += " and car.carModel.modelId=" + carModel.getModelId();
    	if(!pinpai.equals("")) hql = hql + " and car.pinpai like '%" + pinpai + "%'";
    	if(!youxing.equals("")) hql = hql + " and car.youxing like '%" + youxing + "%'";
    	Query q = s.createQuery(hql);
    	List carList = q.list();
    	return (ArrayList<Car>) carList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Car> QueryAllCarInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Car";
        Query q = s.createQuery(hql);
        List carList = q.list();
        return (ArrayList<Car>) carList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String carNo,CarModel carModel,String pinpai,String youxing) {
        Session s = factory.getCurrentSession();
        String hql = "From Car car where 1=1";
        if(!carNo.equals("")) hql = hql + " and car.carNo like '%" + carNo + "%'";
        if(null != carModel && carModel.getModelId()!=0) hql += " and car.carModel.modelId=" + carModel.getModelId();
        if(!pinpai.equals("")) hql = hql + " and car.pinpai like '%" + pinpai + "%'";
        if(!youxing.equals("")) hql = hql + " and car.youxing like '%" + youxing + "%'";
        Query q = s.createQuery(hql);
        List carList = q.list();
        recordNumber = carList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Car GetCarByCarId(int carId) {
        Session s = factory.getCurrentSession();
        Car car = (Car)s.get(Car.class, carId);
        return car;
    }

    /*更新Car信息*/
    public void UpdateCar(Car car) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(car);
    }

    /*删除Car信息*/
    public void DeleteCar (int carId) throws Exception {
        Session s = factory.getCurrentSession();
        Object car = s.load(Car.class, carId);
        s.delete(car);
    }

}
