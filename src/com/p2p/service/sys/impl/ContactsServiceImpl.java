package com.p2p.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.p2p.dao.sys.ContactsDao;
import com.p2p.model.sys.Contacts;
import com.p2p.service.sys.ContactsService;

import core.service.BaseService;
import core.web.SystemCache;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class ContactsServiceImpl extends BaseService<Contacts> implements ContactsService {

	private ContactsDao contactsDao;

	@Resource
	public void setContactsDao(ContactsDao contactsDao) {
		this.contactsDao = contactsDao;
		this.dao = contactsDao;
	}

	
	/*public List<Contacts> getContactsList(List<Contacts> resultList) {
		List<Contacts> contactsList = new ArrayList<Contacts>();
		for (Contacts entity : resultList) {
			Contacts contacts = new Contacts();
			contacts.setContactsId(entity.getContactsId());
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
