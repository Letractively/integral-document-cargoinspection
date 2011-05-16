package com.docum.view.handbook;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import com.docum.persistence.common.Supplier;
import com.docum.persistence.common.Voyage;
import com.docum.service.SupplierService;
import com.docum.service.VoyageService;
import com.docum.view.handbook.dialog.BaseDialog;

@ManagedBean(name = "voyageView")
@ViewScoped
public class VoyageView extends BaseDialog implements Serializable {
	private static final long serialVersionUID = 5855731783922631397L;

	@ManagedProperty(value = "#{voyageService}")
	private VoyageService voyageService;

	private List<Voyage> voyages;
	private Voyage voyage = new Voyage();
	
	public List<Voyage> getAllVoyages() {
		if(voyages == null) {
			voyages = voyageService.getAllVoyages();
		}
		return voyages;
	}
	
	public void editSupplier(ActionEvent actionEvent) {
		if (voyage != null) {
			setTitle("Правка: " + voyage.getNumber());
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ошибочка вышла...",
					"Поставщик для редактирования не выбран!"));
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.addCallbackParam("isValid", false);

		}
	}
	
	public void deleteVoyage() {
		//TODO implement
	}
	
	public void refreshVoyages() {
		this.voyages = voyageService.getAllVoyages();
	}


	public void newVoyage() {
		this.voyage = new Voyage();
		setTitle("Новый рейс");		
	}

	public void saveVoyageAction() {
		if (this.voyage.getId() != null) {
			Voyage voyage = voyageService.getVoyage(this.voyage.getId());
			voyage.copy(this.voyage);
			this.voyage = voyage;
		}
		this.voyage =  voyageService.saveVoyage(this.voyage);

		refreshVoyages();

	}

	public void setVoyageService(VoyageService voyageService) {
		this.voyageService = voyageService;
	}

	public Voyage getVoyage() {
		return voyage;
	}

	public void setVoyage(Voyage voyage) {
		this.voyage = voyage;
	}
}
