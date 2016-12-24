package com.p2p.service.sys;

import java.util.List;

import com.p2p.model.sys.UserInfo;

import core.service.Service;
import core.support.QueryResult;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface UserInfoService extends Service<UserInfo> {

	List<UserInfo> getUserInfoList(List<UserInfo> resultList);

	

}
