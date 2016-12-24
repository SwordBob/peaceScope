package com.p2p.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.p2p.dao.sys.AccountDao;
import com.p2p.model.sys.Account;
import com.p2p.service.sys.AccountService;

import core.service.BaseService;
import core.web.SystemCache;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class AccountServiceImpl extends BaseService<Account> implements AccountService {

	private AccountDao accountDao;

	@Resource
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
		this.dao = accountDao;
	}

	
	public List<Account> getAccountList(List<Account> resultList) {
		List<Account> accountList = new ArrayList<Account>();
		for (Account entity : resultList) {
			Account account = new Account();
			account.setAccountId(entity.getAccountId());
			account.setAccountRecord(entity.getAccountRecord());
			account.setBalance(entity.getBalance());
			account.setFrozen(entity.getFrozen());
			account.setRechargeInfo(entity.getRechargeInfo());
			account.setUserInfo(entity.getUserInfo());
			account.setWithdrawInfo(entity.getWithdrawInfo());
			accountList.add(account);
		}
		return accountList;
	}

}
