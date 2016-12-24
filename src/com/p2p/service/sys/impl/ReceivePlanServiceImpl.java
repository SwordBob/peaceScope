package com.p2p.service.sys.impl;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.p2p.dao.sys.ReceivePlanDao;
import com.p2p.model.sys.ReceivePlan;
import com.p2p.model.sys.RepaymentState;

import com.p2p.service.sys.ReceivePlanService;

import core.service.BaseService;
import core.util.HtmlUtils;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class ReceivePlanServiceImpl extends BaseService<ReceivePlan> implements ReceivePlanService {

	private ReceivePlanDao receivePlanDao;

	@Resource
	public void setReceivePlanDao(ReceivePlanDao receivePlanDao) {
		this.receivePlanDao = receivePlanDao;
		this.dao=receivePlanDao;
	}
	
	
	 

	public ReceivePlanDao getReceivePlanDao() {
		return receivePlanDao;
	}




	public List<ReceivePlan> getReceivePlanList(List<ReceivePlan> resultList) {
		List<ReceivePlan> receivePlanList = new ArrayList<ReceivePlan>();
		for (ReceivePlan entity : resultList) {
			ReceivePlan receivePlan = new ReceivePlan();
			receivePlan.setReceivePlanId(entity.getReceivePlanId());
			receivePlan.setPlanDate(entity.getPlanDate());
			receivePlan.setPlanEstimate(entity.getPlanEstimate());
			receivePlan.setPlanDateEnd(entity.getPlanDateEnd());
			receivePlan.setReceiveState(entity.getReceiveState());
			receivePlan.setPlanPenalty(entity.getPlanPenalty());
			receivePlan.setPartStages(entity.getPartStages());
			receivePlan.setBorrowId(entity.getBorrow().getBorrowId());
			receivePlanList.add(receivePlan);
		}
		return receivePlanList;
	}


	

	
	


}
