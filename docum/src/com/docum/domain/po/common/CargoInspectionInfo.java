package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;

import com.docum.dao.CargoDao;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.OrderedEntityUtil;

@Entity
@NamedQueries({
	@NamedQuery(
			name = CargoDao.GET_CARGO_INSPECTION_INFO,
			query = "FROM CargoInspectionInfo c WHERE c.cargo.id=:cargoId"
	)
})
public class CargoInspectionInfo extends IdentifiedEntity {
	private static final long serialVersionUID = -109254110803956456L;

	@ManyToOne(optional = false)
	private Cargo cargo;

	@OneToMany(mappedBy = "inspectionInfo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderColumn(name="ord")
	private List<CargoDefectGroup> defectGroups = new ArrayList<CargoDefectGroup>();

	@ManyToOne
	private NormativeDocument normativeDocument;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private FileUrl sticker;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private FileUrl stickerEng;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private FileUrl shippingMark;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private FileUrl shippingMarkEng;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderColumn(name="ord")
	private List<FileUrl> images = new ArrayList<FileUrl>();
	
	@OneToMany(mappedBy = "inspectionInfo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<CargoInspectionOption> inspectionOptions = new HashSet<CargoInspectionOption>();

	public CargoInspectionInfo() {
	}

	public CargoInspectionInfo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public List<CargoDefectGroup> getDefectGroups() {
		return defectGroups;
	}

	public void setDefectGroups(List<CargoDefectGroup> defectGroups) {
		this.defectGroups = defectGroups;
	}

	public void addDefectGroup(CargoDefectGroup defectGroup){
		if(defectGroup != null) {
			defectGroups.add(defectGroup);
			defectGroup.setInspectionInfo(this);
		}
	}

	public void removeDefectGroup(CargoDefectGroup defectGroup){
		if(defectGroup != null && defectGroups.remove(defectGroup)) {
			defectGroup.setInspectionInfo(null);
		}
	}
	
	public void resetArticleCategory(ArticleCategory articleCategory) {
		defectGroups.clear();
		//Получаем все категории, начиная с этой и ниже, и генерим по ним дефекты.
		ListIterator<ArticleCategory> it =
			articleCategory.getArticle().getCategories().listIterator(articleCategory.getOrd());
		while(it.hasNext()) {
			defectGroups.add(new CargoDefectGroup(this, it.next(), defectGroups.size()));
		}
	}

	public NormativeDocument getNormativeDocument() {
		return normativeDocument;
	}

	public void setNormativeDocument(NormativeDocument normativeDocument) {
		this.normativeDocument = normativeDocument;
	}

	public FileUrl getSticker() {
		return sticker;
	}

	public void setSticker(FileUrl sticker) {
		this.sticker = sticker;
	}

	public FileUrl getStickerEng() {
		return stickerEng;
	}

	public void setStickerEng(FileUrl stickerEng) {
		this.stickerEng = stickerEng;
	}

	public FileUrl getShippingMark() {
		return shippingMark;
	}

	public void setShippingMark(FileUrl shippingMark) {
		this.shippingMark = shippingMark;
	}
	
	public FileUrl getShippingMarkEng() {
		return shippingMarkEng;
	}

	public void setShippingMarkEng(FileUrl shippingMarkUrlEng) {
		this.shippingMarkEng = shippingMarkUrlEng;
	}

	public List<FileUrl> getImages() {
		return images;
	}

	public void setImages(List<FileUrl> images) {
		this.images = images;
	}
	
	public void addImage(FileUrl url) {
		images.add(url);
	}
	
	public void removeImage(FileUrl url) {
		images.remove(url);
	}
	
	public void moveImageUp(FileUrl url) {
		OrderedEntityUtil.moveUp(url, images);
	}

	public void moveImageDown(FileUrl url) {
		OrderedEntityUtil.moveDown(url, images);
	}

	public Set<CargoInspectionOption> getInspectionOptions() {
		return inspectionOptions;
	}

	public void setInspectionOptions(Set<CargoInspectionOption> inspectionOptions) {
		this.inspectionOptions = inspectionOptions;
	}

}
