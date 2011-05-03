package com.docum.persistence.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.docum.persistence.DocumEntity;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "vessel_seq")
public class Vessel extends DocumEntity implements Serializable {

	private static final long serialVersionUID = -8435521372184842224L;

	@Column(nullable = false)
	private String vesselName;

	@Column(nullable = false)
	private Date vesselTripDate;

	public Vessel() {
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public Date getVesselTripDate() {
		return vesselTripDate;
	}

	public void setVesselTripDate(Date vesselTripDate) {
		this.vesselTripDate = vesselTripDate;
	}

}