package com.p2p.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.p2p.dao.sys.OtherInfoDao;
import com.p2p.model.sys.OtherInfo;
import com.p2p.service.sys.OtherInfoService;

import core.service.BaseService;
import core.web.SystemCache;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class OtherInfoServiceImpl extends BaseService<OtherInfo> implements OtherInfoService {

	private OtherInfoDao otherInfoDao;

	@Resource
	public void setOtherInfoDao(OtherInfoDao otherInfoDao) {
		this.otherInfoDao = otherInfoDao;
		this.dao = otherInfoDao;
	}

	
	public List<OtherInfo> getOtherInfoList(List<OtherInfo> resultList) {
		List<OtherInfo> otherInfoList = new ArrayList<OtherInfo>();
		for (OtherInfo entity : resultList) {
			OtherInfo otherInfo = new OtherInfo();
			otherInfo.setOtherInfoId(entity.getOtherInfoId());
			otherInfo.setOtherInfoName(entity.getOtherInfoName());
			otherInfo.setAuditState(entity.getAuditState());			
			otherInfo.setAuditor(entity.getAuditor());
			otherInfo.setBorrowId(entity.getBorrow().getBorrowId());
			otherInfo.setTitle(entity.getBorrow().getTitle());
			otherInfo.setAmount(entity.getBorrow().getAmount());			
			otherInfo.setInterest(entity.getBorrow().getInterest());
			otherInfo.setStages(entity.getBorrow().getStages());
			otherInfo.setBidType(entity.getBorrow().getBidType());
			otherInfo.setType(entity.getBorrow().getType());
			otherInfo.setBorrowDate(entity.getBorrow().getBorrowDate());
			otherInfo.setState(entity.getBorrow().getState());
			otherInfo.setBidName(entity.getBorrow().getBidName());
			otherInfo.setGender(entity.getBorrow().getGender());
			otherInfo.setMarriage(entity.getBorrow().getMarriage());
			otherInfo.setAddress(entity.getBorrow().getAddress());				
			otherInfo.setOtherInfoUrl(entity.getOtherInfoUrl());
			otherInfoList.add(otherInfo);
		}
		return otherInfoList;
	}

}
