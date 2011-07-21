package com.docum.view.dict;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.SurveyPlace;

@Controller("surveyPlaceBean")
@Scope("session")
public class SurveyPlaceView extends BaseView {
	
	private static final long serialVersionUID = -4003915795099731055L;
	private static final String sign = "Место проведения инспекции";
	
	private SurveyPlace sp = new SurveyPlace();
	
	public SurveyPlace getSurveyPlace() {
		return sp;
	}

	public void setSurveyPlace(SurveyPlace sp) {
		this.sp = sp;
	}

	@Override
	public void newObject() {
		super.newObject();
		this.sp = new SurveyPlace();
	}
	
	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return sp.getEnglishName();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return sp != null ? this.sp : new SurveyPlace();
	}

}
