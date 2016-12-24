package com.p2p.service.sys;

import java.util.List;

import com.p2p.model.sys.OtherInfo;

import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface OtherInfoService extends Service<OtherInfo> {

	List<OtherInfo> getOtherInfoList(List<OtherInfo> resultList);

}
