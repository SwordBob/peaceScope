package com.p2p.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.p2p.dao.sys.ContactsDao;
import com.p2p.model.sys.Contacts;

import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class ContactsDaoImpl extends BaseDao<Contacts> implements ContactsDao {

	public ContactsDaoImpl() {
		super(Contacts.class);
	}

}
