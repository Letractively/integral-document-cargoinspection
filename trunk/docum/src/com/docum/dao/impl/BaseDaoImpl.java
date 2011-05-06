package com.docum.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.docum.dao.BaseDao;
import com.docum.persistence.DocumEntity;

public class BaseDaoImpl implements BaseDao {
	
	@PersistenceContext(name="docum")
	protected EntityManager entityManager;

	protected BaseDaoImpl() {
	}
	
	@Transactional
	public <T extends DocumEntity> Long saveObject(T object) {
		Long id = null;
		entityManager.persist(object);
		id = object.getId();
		return id;
	}

	@Transactional
	public <T extends DocumEntity> void deleteObject(T object) {
		entityManager.remove(object);
	}

}