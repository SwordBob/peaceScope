package com.p2p.service.sys.impl;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.p2p.dao.sys.AccountRecordDao;
import com.p2p.model.sys.AccountRecord;
import com.p2p.model.sys.RepaymentState;

import com.p2p.service.sys.AccountRecordService;

import core.service.BaseService;
import core.util.HtmlUtils;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class AccountRecordServiceImpl extends BaseService<AccountRecord> implements AccountRecordService {

	private AccountRecordDao accountRecordDao;

	@Resource
	public void setAccountRecordDao(AccountRecordDao accountRecordDao) {
		this.accountRecordDao = accountRecordDao;
		this.dao=accountRecordDao;
	}
	
	
	 

	public AccountRecordDao getAccountRecordDao() {
		return accountRecordDao;
	}




	public List<AccountRecord> getAccountRecordList(List<AccountRecord> resultList) {
		List<AccountRecord> accountRecordList = new ArrayList<AccountRecord>();
		for (AccountRecord entity : resultList) {
			AccountRecord accountRecord = new AccountRecord();
			accountRecord.setAccountRecordId(entity.getAccountRecordId());
			accountRecord.setAccountId(entity.getAccountId());
			accountRecord.setAccountType(entity.getAccountType());
			accountRecord.setTradeProcesses(entity.getTradeProcesses());
			accountRecord.setAmount(entity.getAmount());
			accountRecord.setTradeTime(entity.getTradeTime());
			accountRecord.setTradeTraget(entity.getTradeTraget());
			accountRecordList.add(accountRecord);
		}
		return accountRecordList;
	}


	

	
	


}
