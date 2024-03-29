package com.docum.view.dict;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Vessel;

@Controller("vesselBean")
@Scope("session")
public class VesselView extends BaseView {
	private static final long serialVersionUID = -7018249724051865904L;

	private static final String sign = "Судно";
	private Vessel vessel = new Vessel();
	
	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBriefInfo() {
		return this.vessel.getName();
	}
	
	@Override
	public void newObject() {
		super.newObject();
		this.vessel = new Vessel();
		setTitle("Новое " + getSign().toLowerCase());
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return this.vessel != null ? this.vessel : new Vessel();
	}

	public Vessel getVessel() {
		return vessel;
	}

	public void setVessel(Vessel vessel) {
		this.vessel = vessel;
	}
	
	@Override
	public Map<String, SortOrderEnum> getDefaultSortFields(){
		HashMap<String, SortOrderEnum> sortFields = new HashMap<String, SortOrderEnum>();
		sortFields.put("name", SortOrderEnum.ASC);
		return sortFields;
	}

}
