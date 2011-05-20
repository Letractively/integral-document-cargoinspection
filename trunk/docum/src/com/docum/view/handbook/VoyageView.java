package com.docum.view.handbook;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Vessel;
import com.docum.domain.po.common.Voyage;

@ManagedBean(name = "voyageBean")
@SessionScoped
public class VoyageView extends BaseView implements Serializable {
	private static final String sign = "Судозаход";
	private static final long serialVersionUID = 5855731783922631397L;

	private List<Vessel> vessels;
	private Voyage voyage = new Voyage();
	
	
	@Override
	public void saveObject() {
		if (this.voyage.getId() != null) {
			getBaseService().updateObject(this.voyage);
		} else {
			getBaseService().saveObject(this.voyage);
		}
		refreshObjects();
	}
	
	@Override
	public void newObject() {
		super.newObject();
		this.voyage = new Voyage();
	}

	public Voyage getVoyage() {
		return voyage;
	}

	public void setVoyage(Voyage voyage) {
		this.voyage = voyage;
	}

	public List<Vessel> getVessels() {
		if(vessels == null) {
			vessels = getBaseService().getAll(Vessel.class, null);
		}
		return vessels;
	}

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return voyage.getNumber();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return this.voyage != null ? this.voyage : new Voyage();
	}

	public Vessel getSelectedVessel() {
		return voyage == null ? null : voyage.getVessel();
	}

	public void setSelectedVessel(Vessel selectedVessel) {
		if(voyage != null) {
			voyage.setVessel(selectedVessel);
		}
	}
}