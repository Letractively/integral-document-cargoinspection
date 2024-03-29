package com.docum.view.dict;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Company;

@Controller("companyBean")
@Scope("session")
public class CompanyView extends BaseView {

	private static final long serialVersionUID = -1126312045148623962L;
	private static final String sign = "Контрагент";
	private Company company = new Company();

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBriefInfo() {
		return company.getName();
	}

	@Override
	public void newObject() {
		super.newObject();
		this.company = new Company();		
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return company != null ? this.company : new Company();		
	}

	public void setCompany(Company mesure) {		
			this.company = mesure;		
	}

	public Company getCompany() {
		return company;
	}
	
	@Override
	public Map<String, SortOrderEnum> getDefaultSortFields(){
		HashMap<String, SortOrderEnum> sortFields = new HashMap<String, SortOrderEnum>();
		sortFields.put("name", SortOrderEnum.ASC);
		return sortFields;
	}

}
