package com.wyd.zc.demo.service.impl;

import com.wyd.zc.db.mapper.${modelNameUpperCamel}Mapper;
import com.wyd.zc.db.model.${modelNameUpperCamel};
import com.wyd.zc.demo.service.${modelNameUpperCamel}Service;
import com.wyd.zc.demo.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 *
 * @author ${author}
 * @date ${date}
 * @since ${since}
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ${modelNameUpperCamel}ServiceImpl extends AbstractService<${modelNameUpperCamel}> implements ${modelNameUpperCamel}Service {

    @Resource
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

}
