package com.p2p.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.p2p.dao.sys.AccountDao;
import com.p2p.model.sys.Account;

import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class AccountDaoImpl extends BaseDao<Account> implements AccountDao {

	public AccountDaoImpl() {
		super(Account.class);
	}

}
