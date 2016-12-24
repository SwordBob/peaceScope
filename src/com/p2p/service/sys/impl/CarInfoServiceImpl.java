package com.p2p.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.p2p.dao.sys.CarInfoDao;
import com.p2p.model.sys.CarInfo;
import com.p2p.service.sys.CarInfoService;

import core.service.BaseService;
import core.web.SystemCache;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class CarInfoServiceImpl extends BaseService<CarInfo> implements CarInfoService {

	private CarInfoDao carInfoDao;

	@Resource
	public void setCarInfoDao(CarInfoDao carInfoDao) {
		this.carInfoDao = carInfoDao;
		this.dao=carInfoDao;
	}

	
	public List<CarInfo> getCarInfoList(List<CarInfo> resultList) {
		List<CarInfo> carInfoList = new ArrayList<CarInfo>();
		for (CarInfo entity : resultList) {
			CarInfo carInfo = new CarInfo();
			carInfo.setCarInfoId(entity.getCarInfoId());
			carInfo.setCarBrand(entity.getCarBrand());
			carInfo.setCarId(entity.getCarId());
			carInfo.setMileage(entity.getMileage());
			carInfo.setOrgPrice(entity.getOrgPrice());
			carInfo.setEvaluate(entity.getEvaluate());
			carInfo.setAuditDate(entity.getAuditDate());
			carInfo.setAuditor(entity.getAuditor());
			//carInfo.setRoleName(SystemCache.DICTIONARY.get("SYSUSER_ROLE").getItems().get(String.valueOf(entity.getRole())).getValue());
			carInfoList.add(carInfo);
		}
		return carInfoList;
	}

	

}
