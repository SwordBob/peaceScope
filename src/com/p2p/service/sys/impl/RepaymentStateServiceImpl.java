package com.p2p.service.sys.impl;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.p2p.dao.sys.RepaymentStateDao;
import com.p2p.model.sys.RepaymentState;

import com.p2p.service.sys.RepaymentStateService;

import core.service.BaseService;
import core.util.HtmlUtils;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class RepaymentStateServiceImpl extends BaseService<RepaymentState> implements RepaymentStateService {

	private RepaymentStateDao repaymentStateDao;

	@Resource
	public void setRepaymentStateDao(RepaymentStateDao repaymentStateDao) {
		this.repaymentStateDao = repaymentStateDao;
		this.dao=repaymentStateDao;
	}

	
	public List<RepaymentState> getRepaymentStateList(List<RepaymentState> resultList) {
		List<RepaymentState> repaymentStateList = new ArrayList<RepaymentState>();
		for (RepaymentState entity : resultList) {
			RepaymentState repaymentState = new RepaymentState();
			repaymentState.setRepaymentStateId(entity.getRepaymentStateId());
			repaymentState.setRepaymentDate(entity.getRepaymentDate());
			repaymentState.setEstimate(entity.getEstimate());
			repaymentState.setPayet(entity.getPayet());
			repaymentState.setPenalty(entity.getPenalty());		
			repaymentStateList.add(repaymentState);
		}
		return repaymentStateList;
	}
	
	 

	public RepaymentStateDao getRepaymentStateDao() {
		return repaymentStateDao;
	}


	

	
	


}
