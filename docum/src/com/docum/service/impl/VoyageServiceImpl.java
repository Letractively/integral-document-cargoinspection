package com.docum.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.dao.VoyageDao;
import com.docum.domain.po.common.Voyage;
import com.docum.service.VoyageService;

@Service("voyageService")
@Transactional
public class VoyageServiceImpl extends BaseServiceImpl implements VoyageService {
	private static final long serialVersionUID = -5052727843476156745L;
	
	@Autowired
	private VoyageDao voyageDao;
	
	@Override
	public List<Voyage> getAllVoyages() {
		return voyageDao.getAll(Voyage.class, null);
	}

	@Override
	public Collection<Voyage> getFinishedVoyages() {
		return voyageDao.getVoyagesByFinishStatus(true);
	}

	@Override
	public Collection<Voyage> getUnfinishedVoyages() {
		return voyageDao.getVoyagesByFinishStatus(false);
	}

	@Override
	public Voyage getVoyage(Long voyageId) {
		return voyageDao.getObject(Voyage.class, voyageId);
	}

	@Override
	public Voyage saveVoyage(Voyage voyage) {
		return voyageDao.saveObject(voyage);
	}

	@Override
	public void deleteVoyage(Voyage voyage) {
		voyageDao.deleteObject(voyage);
	}

	@Override
	public void deleteVoyage(Long voyageId) {
		voyageDao.deleteObject(Voyage.class, voyageId);
	}

	@Override
	public List<Voyage> getVoyagesByInvoice(Long invoiceId) {
		return voyageDao.getVoyagesByInvoice(invoiceId);
	}

	@Override
	public List<Voyage> getVoyagesByPurchaseOrder(Long orderId) {
		return voyageDao.getVoyagesByPurchaseOrder(orderId);
	}

}
