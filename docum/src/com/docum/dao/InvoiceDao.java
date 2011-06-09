package com.docum.dao;

import java.util.List;

import com.docum.domain.po.common.Invoice;

public interface InvoiceDao extends BaseDao {

	public static final String GET_INVOICES_BY_VOYAGE_QUERY = "getInvoicesByVoyage";
	public static final String GET_INVOICES_BY_PURCHASE_ORDER_QUERY = "getInvoicesByPurchaseOrder";

	public List<Invoice> getInvoicesByVoyage(Long voyageId);
	public List<Invoice> getInvoicesByPurchaseOrder(Long orderId);
}