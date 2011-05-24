package com.docum.dao;

import java.util.List;

import com.docum.domain.po.common.BillOfLading;

public interface BillOfLadingDao extends BaseDao {

	public static final String GET_BILLS_BY_VOYAGE_QUERY = "getBillsByVoyage";

	public List<BillOfLading> getBillsByVoyage(Long voyageId);

}