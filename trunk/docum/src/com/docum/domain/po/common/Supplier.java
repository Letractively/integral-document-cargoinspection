package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class Supplier extends IdentifiedEntity {

	private static final long serialVersionUID = 7918790765968857071L;

	@ManyToOne
	private Company company;

	public Supplier() {
	}

	public Supplier(Company company) {
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}