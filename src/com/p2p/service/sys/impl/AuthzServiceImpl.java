package com.p2p.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.p2p.dao.sys.AuthzDao;
import com.p2p.model.sys.Authz;
import com.p2p.service.sys.AuthzService;

import core.service.BaseService;
import core.web.SystemCache;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class AuthzServiceImpl extends BaseService<Authz> implements AuthzService {

	private AuthzDao contactsDao;

	@Resource
	public void setAuthzDao(AuthzDao contactsDao) {
		this.contactsDao = contactsDao;
		this.dao = contactsDao;
	}

	
	/*public List<Authz> getAuthzList(List<Authz> resultList) {
		List<Authz> contactsList = new ArrayList<Authz>();
		for (Authz entity : resultList) {
			Authz contacts = new Authz();
			contacts.setAuthzId(entity.getAuthzId());
			contacts.setcontactsRecord(entity.getcontactsRecord());
			contacts.setBalance(entity.getBalance());
			contacts.setFrozen(entity.getFrozen());
			contacts.setRechargeInfo(entity.getRechargeInfo());
			contacts.setUserInfo(entity.getUserInfo());
			contacts.setWithdrawInfo(entity.getWithdrawInfo());
			contactsList.add(contacts);
		}
		return contactsList;
	}
*/
}
