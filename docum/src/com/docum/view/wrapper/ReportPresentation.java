package com.docum.view.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Report;
import com.docum.util.ListHandler;

public class ReportPresentation implements Serializable {
	private static final long serialVersionUID = -7413568404878800684L;
	
	private Report report;
	private String reportedContainers;
	private List<Object> cargoes = new ArrayList<Object>();
	private List<Object> cargoSuppliers = new ArrayList<Object>();
	
	public ReportPresentation(Report report) {
		this.report = report;
		if (report != null) {
			this.reportedContainers = getReportedContainers(report.getContainers());
			for (Container container: report.getContainers()) {
				Set<Cargo> c = container.getDeclaredCondition().getCargoes(); 
				this.cargoes.add(ListHandler.join(new ArrayList<Cargo>(c), ", "));
				for (Cargo cargo: c) {
					cargoSuppliers.add(cargo.getSupplier());
				}
			}
		}
	}
	
	private String getReportedContainers(List<Container> containers) {
		StringBuffer result = new StringBuffer();
		for (Container container: containers) {
			result.append(", ").append(container.getNumber());
		}
		result.setCharAt(0, ' ');
		return result.toString();
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public String getCargo() {
		return ListHandler.getUniqueResult(this.cargoes);
	}

	public String getCargoSupplier() {
		return ListHandler.getUniqueResult(this.cargoSuppliers);
	}

	public String getReportedContainers() {
		return reportedContainers;
	}
	
	public String getNumber() {
		if (this.report != null) {
			return this.report.getNumber();
		} else {
			return null;
		}
			
	}
	
	public String getCustomer() {
		if (this.report != null && this.report.getCustomer() != null) {
			return this.report.getCustomer().toString();
		} else {
			return null;
		}
			
	}
	
	public Date getDate() {
		if (this.report != null) {
			return this.report.getDate();
		} else {
			return null;
		}
	}
	
	public Long getId() {
		if (this.report != null) {
			return this.report.getId();
		} else {
			return null;
		}
	}
}
