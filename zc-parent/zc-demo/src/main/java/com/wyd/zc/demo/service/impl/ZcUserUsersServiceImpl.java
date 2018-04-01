package com.wyd.zc.demo.service.impl;

import com.wyd.zc.db.mapper.ZcUserUsersMapper;
import com.wyd.zc.db.model.ZcUserUsers;
import com.wyd.zc.demo.service.ZcUserUsersService;
import com.wyd.zc.demo.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 *
 * @author 王玉栋
 * @date 2018-04-01 21:22:33
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ZcUserUsersServiceImpl extends AbstractService<ZcUserUsers> implements ZcUserUsersService {

    @Resource
    private ZcUserUsersMapper zcUserUsersMapper;

}
