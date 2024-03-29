package com.docum.view.dict;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.service.BaseService;

public abstract class BaseView implements Serializable{
	private static final long serialVersionUID = -1072752910650707550L;
	private String title;

	@Autowired
	private BaseService baseService;

	private List<? extends IdentifiedEntity> objects;

	public Map<String, SortOrderEnum> getDefaultSortFields(){
		HashMap<String, SortOrderEnum> sortFields = new HashMap<String, SortOrderEnum>();
		sortFields.put("id", SortOrderEnum.ASC);
		return sortFields;
	}
	
	public List<? extends IdentifiedEntity> getAllObjects() {
		if (this.objects == null) {
			refreshObjects();
		}
		return this.objects;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public abstract String getSign();

	public abstract String getBriefInfo();

	abstract public IdentifiedEntity getBeanObject();

	public void refreshObjects() {
		this.objects = baseService.getAll(getBeanObject().getClass(), getDefaultSortFields());
	}

	public void saveObject() {
		baseService.save(getBeanObject());
		refreshObjects();
	}

	public void deleteObject() {
		baseService.deleteObject(getBeanObject().getClass(), getBeanObject().getId());
		refreshObjects();
	}

	public void newObject() {
		setTitle("Новый " + getSign().toLowerCase());
	}

	public void editObject(ActionEvent actionEvent) {
		if (getBeanObject().getId() != null) {
			setTitle("Правка: " + getBriefInfo());
		} else {
			String message = String.format(
					"%1$s для редактирования не выбран!", getSign());
			showErrorMessage(message);
			addCallbackParam("dontShow", true);
		}
	}

	public void beforeDeleteObject(ActionEvent actionEvent) {
		if (getBeanObject().getId() == null) {
			String message = String.format("%1$s для удаления не выбран!",
					getSign());
			showErrorMessage(message);
			addCallbackParam("dontShow", true);
		}
	}

	protected void showErrorMessage(String message) {
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Ошибочка вышла...", message));
	}
	
	protected void showMessage(String message) {
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Ошибочка вышла...", message));
	}

	public List<? extends IdentifiedEntity> getObjects() {
		return objects;
	}

	public void setObjects(List<? extends IdentifiedEntity> objects) {
		this.objects = objects;
	}

	protected BaseService getBaseService() {
		return baseService;
	}
	
	public void addCallbackParam(String paramName, Object paramValue) {
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam(paramName, paramValue);
	}
}
