package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class CargoPackage extends IdentifiedEntity implements Comparable<CargoPackage> {
	private static final long serialVersionUID = 1238283870788720201L;

	@ManyToOne(optional = false)
	private Cargo cargo;

	@ManyToOne(optional = false)
	private Measure measure;

	private double count;

	@OneToMany(mappedBy = "cargoPackage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<CargoPackageCalibre> calibres = new ArrayList<CargoPackageCalibre>();

	@OneToMany(mappedBy = "cargoPackage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<CargoPackageWeight> weights = new ArrayList<CargoPackageWeight>();

	public CargoPackage() {
		super();
	}

	public CargoPackage(Cargo cargo) {
		this();
		this.setCargo(cargo);
	}

	public CargoPackage(Cargo cargo, Measure measure, Double count) {
		this();
		this.setCargo(cargo);
		this.measure = measure;
		this.count = count;
	}

	public void copy(CargoPackage from) {
		this.cargo = from.cargo;
		this.measure = from.measure;
		this.count = from.count;
		setId(from.getId());
		if (from.getCargo().getCondition().isSurveyable()) {
			weights = new ArrayList<CargoPackageWeight>(from.weights.size());
			for (CargoPackageWeight w : from.weights) {
				this.weights.add(new CargoPackageWeight(w));
			}
		}
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Measure getMeasure() {
		return measure;
	}

	public void setMeasure(Measure measure) {
		this.measure = measure;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	public List<CargoPackageCalibre> getCalibres() {
		return calibres;
	}

	public void setCalibres(List<CargoPackageCalibre> calibres) {
		this.calibres = calibres;
	}

	public void addCalibre(CargoPackageCalibre calibre) {
		calibres.add(calibre);
		calibre.setCargoPackage(this);
	}

	public void removeCalibre(CargoPackageCalibre calibre) {
		if (calibres.remove(calibre)) {
			calibre.setCargoPackage(null);
		}
	}

	public List<CargoPackageWeight> getWeights() {
		return weights;
	}

	public void setWeights(List<CargoPackageWeight> weights) {
		this.weights = weights;
	}

	@Override
	public String toString() {
		return getMeasure() != null ? getMeasure().getName() : "Не указано измерение";
	}

	@Override
	public int compareTo(CargoPackage o) {
		if (o == null) {
			return 1;
		} else if (o.getMeasure() == null && this.measure == null ) {
			return 0;
		} else if (o.getMeasure() != null && this.measure == null) {
			return -1;
		} else if (o.getMeasure() == null && this.measure != null) {
			return 1;
		} else {
			return this.getMeasure().compareTo(o.getMeasure());
		}
		
	}
}
