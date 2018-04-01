package com.wyd.zc.demo.core;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyd.zc.db.core.Mapper;

import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 * 
 * @author 王玉栋
 * @date 2018-03-24 23:00:02
 * @since 1.0
 */
public abstract class AbstractService<T> implements Service<T> {

	@Autowired
	protected Mapper<T> mapper;

	/**
	 * 当前泛型真实类型的Class
	 */
	private Class<T> modelClass;

	@SuppressWarnings("unchecked")
	public AbstractService() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		modelClass = (Class<T>) pt.getActualTypeArguments()[0];
	}

	@Override
	public void save(T model) {
		mapper.insertSelective(model);
	}

	@Override
	public void save(List<T> models) {
		mapper.insertList(models);
	}

	@Override
	public void deleteById(Object id) {
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByIds(String ids) {
		mapper.deleteByIds(ids);
	}

	@Override
	public void update(T model) {
		mapper.updateByPrimaryKeySelective(model);
	}

	@Override
	public T findById(Object id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public T findBy(String fieldName, Object value) throws TooManyResultsException {
		try {
			T model = modelClass.newInstance();
			Field field = modelClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(model, value);
			return mapper.selectOne(model);
		} catch (ReflectiveOperationException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<T> findByIds(String ids) {
		return mapper.selectByIds(ids);
	}

	@Override
	public List<T> findByCondition(Condition condition) {
		return mapper.selectByCondition(condition);
	}

	@Override
	public List<T> findAll() {
		return mapper.selectAll();
	}

}
