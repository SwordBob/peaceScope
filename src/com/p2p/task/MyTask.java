package com.p2p.task;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.p2p.dao.sys.AccountDao;
import com.p2p.dao.sys.BorrowDao;
import com.p2p.dao.sys.ReceivePlanDao;
import com.p2p.dao.sys.RepaymentStateDao;
import com.p2p.dao.sys.UserInfoDao;
import com.p2p.model.sys.Account;
import com.p2p.model.sys.AccountRecord;
import com.p2p.model.sys.Borrow;
import com.p2p.model.sys.ReceivePlan;
import com.p2p.model.sys.RepaymentState;
import com.p2p.model.sys.UserInfo;
@Component
public class MyTask {
	
private RepaymentStateDao repaymentStateDao;
	
	private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");


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
	
	//@Scheduled(cron="0/5 * * * * ? ") //间隔60秒执行 		
	public void taskCycle(){  
        System.out.println("执行收款**********");  
    } 
	@Scheduled(cron="0/180 * * * * ? ") //间隔60秒执行 	
	@Transactional
	public void getSysMoney(){
		Date dt=new Date();
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
