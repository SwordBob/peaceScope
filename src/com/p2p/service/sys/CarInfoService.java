package com.p2p.service.sys;

import java.util.List;

import com.p2p.model.sys.CarInfo;
import com.p2p.model.sys.SysUser;

import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface CarInfoService extends Service<CarInfo> {

	List<CarInfo> getCarInfoList(List<CarInfo> resultList);

}
