package com.docum.domain.po.common;

import javax.persistence.Entity;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class Measure extends IdentifiedEntity implements Comparable<Measure> {

	private static final long serialVersionUID = 34410299838532629L;

	private String name;

	private String englishName;
	
	public Measure() {

	}

	public Measure(String name, String englishName) {
		this.name = name;
		this.englishName = englishName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Measure)) {
			return false;
		}
		
		if(getId() == null || ((Measure) obj).getId() == null) {
			return false;
		}
		return EqualsHelper.equals(getId(), ((Measure) obj).getId());
	}

	public int hashCode() {
		if(getId() == null) {
			return super.hashCode();
		}
		return HashCodeHelper.hashCode(getId());
	}
	
	public String toString(){		
		return getName();
	}

	@Override
	public int compareTo(Measure o) {
		if (o == null) {
			return 1;
		} else if (this.getName() == null && o.getName() == null) {
			return 0;
		} else if (this.getName() != null && o.getName() == null) {
			return 1;
		} else if (this.getName() == null && o.getName() != null) {
			return -1;
		} else {
			return this.getName().compareToIgnoreCase(o.getName());
		}
		
	}

}
