package com.docum.view.handbook;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.docum.persistence.common.Supplier;
import com.docum.service.SupplierService;

@ManagedBean(name = "supplier")
@ViewScoped
public class SupplierView implements Serializable {
	private static final long serialVersionUID = -676095247499740650L;

	@ManagedProperty(value = "#{supplierService}")
	private SupplierService supplierService;

	private List<Supplier> suppliers;
	
	public List<Supplier> getSuppliers() {
		if(suppliers == null) {
			suppliers = supplierService.getAllSuppliers();
		}
		return suppliers;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}
}
