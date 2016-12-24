package com.p2p.service.sys.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.p2p.dao.sys.AccountDao;
import com.p2p.dao.sys.BorrowDao;
import com.p2p.dao.sys.ReceivePlanDao;
import com.p2p.dao.sys.RepaymentStateDao;
import com.p2p.dao.sys.SysUserDao;
import com.p2p.dao.sys.UserInfoDao;
import com.p2p.model.sys.Account;
import com.p2p.model.sys.AccountRecord;
import com.p2p.model.sys.Borrow;
import com.p2p.model.sys.ReceivePlan;
import com.p2p.model.sys.RepaymentState;
import com.p2p.model.sys.SysUser;
import com.p2p.model.sys.UserInfo;
import com.p2p.service.sys.SysUserService;


import core.service.BaseService;
import core.support.QueryResult;
import core.web.SystemCache;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class SysUserServiceImpl extends BaseService<SysUser> implements SysUserService {

	private SysUserDao sysUserDao;

	@Resource
	public void setSysUserDao(SysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
		this.dao = sysUserDao;
	}
	
	
	private RepaymentStateDao repaymentStateDao;
	
	private ReceivePlanDao receivePlanDao;
	
	private BorrowDao borrowDao;
	
	private UserInfoDao userInfoDao;
	
	private AccountDao accountDao;
	
	@Resource
	public void setRepaymentStateDao(RepaymentStateDao repaymentStateDao) {
		this.repaymentStateDao = repaymentStateDao;
	}
	@Resource
	public void setReceivePlanDao(ReceivePlanDao receivePlanDao) {
		this.receivePlanDao = receivePlanDao;
	}
	@Resource
	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}
	@Resource
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}
	@Resource
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	
	private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
	
	public List<SysUser> getSysUserList(List<SysUser> resultList) {
		List<SysUser> sysUserList = new ArrayList<SysUser>();
		for (SysUser entity : resultList) {
			SysUser sysUser = new SysUser();
			sysUser.setId(entity.getId());
			sysUser.setUserName(entity.getUserName());
			sysUser.setPassword(entity.getPassword());
			sysUser.setRealName(entity.getRealName());
			sysUser.setTel(entity.getTel());
			sysUser.setEmail(entity.getEmail());
			sysUser.setLastLoginTime(entity.getLastLoginTime());
			sysUser.setRole(entity.getRole());
			sysUser.setRoleName(SystemCache.DICTIONARY.get("SYSUSER_ROLE").getItems().get(String.valueOf(entity.getRole())).getValue());
			sysUserList.add(sysUser);
		}
		return sysUserList;
	}
	@Transactional
	public void getSysMoney(){
		Date dt=new Date(116,2,29);
		ReceivePlan receivePlan=new ReceivePlan();
		Calendar c = Calendar.getInstance();
		Set<AccountRecord> accountRecords= new HashSet<AccountRecord>();
		AccountRecord accountRecord=new AccountRecord();
		List<ReceivePlan> receivePlans=receivePlanDao.queryByLikeProerties("$like_planDate",sdfDate.format(dt));
		for (int i = 0; i < receivePlans.size(); i++) {		
			receivePlan=receivePlans.get(i);
			//String receiveState=receivePlans.get(i).getReceiveState();
			if ("未还款".equals(receivePlan.getReceiveState())){
				Borrow borrow=borrowDao.get(receivePlan.getBorrowId());
				//生成还款计划时在repaymentstate表中加入空表，只写该表主键id为borrowId
				RepaymentState repaymentState=borrow.getRepaymentState();
				UserInfo userInfo=userInfoDao.get(borrow.getUserId());		
				Account account=accountDao.get(userInfo.getAccountId());
				
				Float balance=account.getBalance();
				Float money=receivePlan.getPlanEstimate()+receivePlan.getPlanPenalty();
				Float subBalance=balance-money;
				Float exceedPenalty=receivePlan.getPlanPenalty();
				Date exceedPlanDate=receivePlan.getPlanDate();
				//Integer stagesCome=receivePlan.getPartStages();
				if (subBalance>=0) {									
				account.setBalance(subBalance);
				accountRecord.setTradeProcesses("还款");
				accountRecord.setTradeTime(new Date());
				accountRecord.setTradeTraget("微贷网平台");
				accountRecord.setAccountType("账户余额");
				accountRecord.setAmount(money);
				accountRecords.add(accountRecord);
				account.setAccountRecord(accountRecords);
				receivePlan.setReceiveState("已还款");
				
				//repaymentState.setRepaymentStateId(borrow.getBorrowId());
				//在plandate中可考虑加一天
				repaymentState.setRepaymentDate(receivePlan.getPlanDate());
				repaymentState.setPayet(receivePlan.getPartStages());
				repaymentState.setRepaymentEnd(receivePlan.getPlanDateEnd());
				repaymentState.setEstimate(receivePlan.getPlanEstimate());
				repaymentState.setActual(receivePlan.getPlanEstimate());
				repaymentState.setPenalty(exceedPenalty);
				repaymentState.setBorrow(borrow);
				
				
				accountDao.updateMy(account);
				repaymentStateDao.updateMy(repaymentState);
				receivePlanDao.updateMy(receivePlan);
				}
				else {					
					exceedPenalty=exceedPenalty+50;					
					c.setTime(exceedPlanDate);
					c.add(Calendar.DAY_OF_MONTH,1);
					receivePlan.setPlanDate(c.getTime());
					receivePlan.setPlanPenalty(exceedPenalty);
					receivePlanDao.updateMy(receivePlan);
					repaymentState.setRepaymentDate(c.getTime());
					repaymentState.setRepaymentEnd(receivePlan.getPlanDateEnd());
					repaymentState.setEstimate(receivePlan.getPlanEstimate());
					repaymentState.setPenalty(exceedPenalty);
					repaymentStateDao.updateMy(repaymentState);
					
				}
			}
			
		}
		
	}
	

}
