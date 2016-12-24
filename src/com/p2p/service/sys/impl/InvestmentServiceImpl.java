package com.p2p.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.p2p.dao.sys.IncomeInfoDao;
import com.p2p.dao.sys.InvestmentDao;
import com.p2p.dao.sys.UserInfoDao;
import com.p2p.model.sys.IncomeInfo;
import com.p2p.model.sys.Investment;
import com.p2p.service.sys.InvestmentService;

import core.service.BaseService;
import core.web.SystemCache;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class InvestmentServiceImpl extends BaseService<Investment> implements InvestmentService {

	private InvestmentDao investmentDao;
	@Resource
	private UserInfoDao userInfoDao;
	@Resource
	private IncomeInfoDao incomeInfoDao;

	@Resource
	public void setInvestmentDao(InvestmentDao investmentDao) {
		this.investmentDao = investmentDao;
		this.dao = investmentDao;
	}

	
	public List<Investment> getInvestmentList(List<Investment> resultList) {
		List<Investment> investmentList = new ArrayList<Investment>();
		for (Investment entity : resultList) {
			Investment investment = new Investment();
			investment.setInvestmentId(entity.getInvestmentId());
			investment.setAmount(entity.getAmount());
			investment.setExpect(entity.getExpect());			
			investment.setInvestDate(entity.getInvestDate());
			investment.setBorrowId(entity.getBorrow().getBorrowId());
			investment.setTitle(entity.getBorrow().getTitle());
			investment.setBorrowAmount(entity.getBorrow().getAmount());			
			investment.setInterest(entity.getBorrow().getInterest());
			investment.setStages(entity.getBorrow().getStages());
			investment.setBidType(entity.getBorrow().getBidType());
			investment.setType(entity.getBorrow().getType());
			investment.setBorrowDate(entity.getBorrow().getBorrowDate());
			investment.setState(entity.getBorrow().getState());
			investment.setBidName(entity.getBorrow().getBidName());
			investment.setGender(entity.getBorrow().getGender());
			investment.setMarriage(entity.getBorrow().getMarriage());
			investment.setAddress(entity.getBorrow().getAddress());
			investment.setUserId(entity.getUserInfo().getUserId());
			investment.setEmail(userInfoDao.get(entity.getUserInfo().getUserId()).getEmail());
			investment.setPhone(userInfoDao.get(entity.getUserInfo().getUserId()).getPhone());
			investment.setIncomeInfoId(entity.getIncomeInfo().getIncomeInfoId());
			investment.setPrincipal(incomeInfoDao.get(entity.getIncomeInfo().getIncomeInfoId()).getPrincipal());
			investment.setExceptInterest(incomeInfoDao.get(entity.getIncomeInfo().getIncomeInfoId()).getExceptInterest());
			investment.setPaidIn(incomeInfoDao.get(entity.getIncomeInfo().getIncomeInfoId()).getPaidIn());
			investment.setIncomeInterest(incomeInfoDao.get(entity.getIncomeInfo().getIncomeInfoId()).getInterest());
			investmentList.add(investment);
		}
		return investmentList;
	}

}
