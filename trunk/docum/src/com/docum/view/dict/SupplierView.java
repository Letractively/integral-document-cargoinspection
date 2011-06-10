package com.docum.view.dict;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Company;
import com.docum.domain.po.common.Supplier;

@Controller("supplierBean")
@Scope("session")
public class SupplierView extends BaseView {
	private static final long serialVersionUID = -676095247499740650L;

	private static final String sign = "Поставщик";
	private Supplier supplier = new Supplier();	

	public List<Company> getCompanies() {		
		return getBaseService().getAll(Company.class, null);
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public Company getSelectedCompany() {
		return supplier == null ? null : supplier.getCompany();
	}

	public void setSelectedCompany(Company selectedCompany) {
		if (supplier != null) {
			supplier.setCompany(selectedCompany);
		}
	}

	@Override
	public void newObject() {
		super.newObject();
		supplier = new Supplier();
	}

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return getSelectedCompany().getName();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return supplier != null ? this.supplier : new Supplier();
	}
}
