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
    public void AddCarModel(CarModel carModel) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(carModel);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<CarModel> QueryCarModelInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From CarModel carModel where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public CarModel GetCarModelByModelId(int modelId) {
        Session s = factory.getCurrentSession();
        CarModel carModel = (CarModel)s.get(CarModel.class, modelId);
        return carModel;
    }

    /*����CarModel��Ϣ*/
    public void UpdateCarModel(CarModel carModel) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(carModel);
    }

    /*ɾ��CarModel��Ϣ*/
    public void DeleteCarModel (int modelId) throws Exception {
        Session s = factory.getCurrentSession();
        Object carModel = s.load(CarModel.class, modelId);
        s.delete(carModel);
    }

}
