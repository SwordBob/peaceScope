package com.p2p.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.p2p.dao.sys.AccountDao;
import com.p2p.dao.sys.ContactsDao;
import com.p2p.dao.sys.RepaymentStateDao;
import com.p2p.dao.sys.UserInfoDao;
import com.p2p.model.sys.UserInfo;
import com.p2p.service.sys.UserInfoService;

import core.service.BaseService;
import core.support.QueryResult;
import core.web.SystemCache;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class UserInfoServiceImpl extends BaseService<UserInfo> implements UserInfoService {

	private UserInfoDao userInfoDao;

	@Resource
	private AccountDao accountDao;
	
	@Resource
	private ContactsDao contactsDao;
	
	@Resource
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
		this.dao = userInfoDao;
	}

	
	public List<UserInfo> getUserInfoList(List<UserInfo> resultList) {
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		for (UserInfo entity : resultList) {
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(entity.getUserId());
			userInfo.setPhone(entity.getPhone());
			userInfo.setPassword(entity.getPassword());
			//userInfo.setRealName(entity.getRealName());
			userInfo.setPhone(entity.getPhone());
			userInfo.setEmail(entity.getEmail());
			if(entity.getAccountId()!=null){
			userInfo.setAccountId(entity.getAccount().getAccountId());
			userInfo.setCreateDate(entity.getCreateDate());
			userInfo.setBalance(accountDao.get(entity.getAccount().getAccountId()).getBalance());
			userInfo.setFrozen(accountDao.get(entity.getAccount().getAccountId()).getFrozen());
			}
			if(entity.getContactId()!=null){
			userInfo.setContactId(entity.getContacts().getContactId());
			userInfo.setContactName(contactsDao.get(entity.getContacts().getContactId()).getContactName());
			userInfo.setContactPhone(contactsDao.get(entity.getContacts().getContactId()).getContactPhone());
			}
			if(entity.getAuthzId()!=null){
			userInfo.setAuthzId(entity.getAuthz().getAuthzId());
			userInfo.setRealName(entity.getAuthz().getRealName());
			userInfo.setIdCardNO(entity.getAuthz().getIdCardNO());
			}
			userInfoList.add(userInfo);
		}
		return userInfoList;
	}


	public QueryResult<UserInfo> doQueryIncludeDate(UserInfo userInfo) {
		return userInfoDao.doPaginationQueryDate(userInfo, true);
	}

}
