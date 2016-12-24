package com.p2p.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.p2p.dao.sys.IncomeInfoDao;
import com.p2p.model.sys.IncomeInfo;
import com.p2p.service.sys.IncomeInfoService;

import core.service.BaseService;
import core.web.SystemCache;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class IncomeInfoServiceImpl extends BaseService<IncomeInfo> implements IncomeInfoService {

	private IncomeInfoDao incomeInfoDao;

	@Resource
	public void setIncomeInfoDao(IncomeInfoDao incomeInfoDao) {
		this.incomeInfoDao = incomeInfoDao;
		this.dao = incomeInfoDao;
	}

	
	public List<IncomeInfo> getIncomeInfoList(List<IncomeInfo> resultList) {
		List<IncomeInfo> incomeInfoList = new ArrayList<IncomeInfo>();
		for (IncomeInfo entity : resultList) {
			IncomeInfo incomeInfo = new IncomeInfo();
			incomeInfo.setIncomeInfoId(entity.getIncomeInfoId());
			incomeInfo.setPrincipal(entity.getPrincipal());
			incomeInfo.setExceptInterest(entity.getExceptInterest());			
			incomeInfo.setPaidIn(entity.getPaidIn());					
			incomeInfo.setInterest(entity.getInterest());
			incomeInfoList.add(incomeInfo);
		}
		return incomeInfoList;
	}

}
