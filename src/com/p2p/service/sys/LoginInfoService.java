package com.p2p.service.sys;

import java.util.List;

import com.p2p.model.sys.LoginInfo;

import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface LoginInfoService extends Service<LoginInfo> {

	List<Object[]> doGetLoginInfoStatistics();

	List<Object[]> doGetEnhanceLoginInfoStatistics(List<Object[]> list);

}
