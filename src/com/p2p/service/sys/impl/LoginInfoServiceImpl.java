package com.p2p.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.p2p.dao.sys.LoginInfoDao;
import com.p2p.model.sys.LoginInfo;
import com.p2p.service.sys.LoginInfoService;

import core.service.BaseService;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class LoginInfoServiceImpl extends BaseService<LoginInfo> implements LoginInfoService {

	private LoginInfoDao loginInfoDao;

	@Resource
	public void setLoginInfoDao(LoginInfoDao loginInfoDao) {
		this.loginInfoDao = loginInfoDao;
		this.dao = loginInfoDao;
	}

	
	public List<Object[]> doGetLoginInfoStatistics() {
		return loginInfoDao.doGetLoginInfoStatistics();
	}


	public List<Object[]> doGetEnhanceLoginInfoStatistics(List<Object[]> list) {
		List<Object[]> enhanceList = new ArrayList<Object[]>();
		for (Object[] objectArray : list) {
			//String sted = String.valueOf(objectArray[0])+"时";
			Object[] object = new Object[] { objectArray[0], objectArray[1], objectArray[2]};
			//String.valueOf(sted[0]), String.valueOf(sted[sted.length - 1]) 
			
			enhanceList.add(object);
		}
		return enhanceList;
	}

}
