package com.docum.view.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;

import com.docum.domain.ContainerStateEnum;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Voyage;
import com.docum.util.AlgoUtil;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

public class VoyagePresentation implements Serializable {
	private static final long serialVersionUID = -3545320838886096320L;
	private Voyage voyage;
	private List<ContainerPresentation> containers = new ArrayList<ContainerPresentation>();
	private EnumMap<ContainerStateEnum, Integer> containerStateMap = new EnumMap<ContainerStateEnum, Integer>(
			ContainerStateEnum.class);
	
	public VoyagePresentation() {
		for (ContainerStateEnum e : ContainerStateEnum.values()) {
			containerStateMap.put(e, 0);
		}
	}

	public VoyagePresentation(Voyage voyage) {
		this(voyage, true);
	}

	public VoyagePresentation(Voyage voyage, boolean deep) {
		this();
		this.voyage = voyage;
		if(deep) {
			for (Container container : voyage.getContainers()) {
				Integer count = containerStateMap.get(container.getState());
				if(count != null) {
					count++;
					containerStateMap.put(container.getState(), count);
				}
				this.containers.add(new ContainerPresentation(container));
			}
		}
	}
	
	public String getVessel() {
		return voyage.getVessel().getName();
	}

	public String getNumber() {
		return voyage.getNumber();
	}

	public Date getArrivalDate() {
		return voyage.getArrivalDate();
	}

	public List<ContainerPresentation> getContainers() {
		return containers;
	}

	public List<ContainerPresentation> getUnfinishedContainers() {
		List<ContainerPresentation> result = new ArrayList<ContainerPresentation>();
		AlgoUtil.collect(result, containers, new AlgoUtil.FindPredicate<ContainerPresentation>() {
			@Override
			public boolean isIt(ContainerPresentation container) {
				ContainerStateEnum state = container.getContainer().getState();
				return (ContainerStateEnum.NOT_HANDLED.equals(state) ||
						ContainerStateEnum.HANDLED.equals(state));
			}
		});
		return result;
	}
	
	public Integer getTotalContainerCount() {
		return containers.size();
	}

	public Integer getNotHandledContainerCount() {
		return containerStateMap.get(ContainerStateEnum.NOT_HANDLED);
	}

	public Integer getHandledContainerCount() {
		return containerStateMap.get(ContainerStateEnum.HANDLED);
	}

	public Voyage getVoyage() {
		return voyage;
	}

	public void setVoyage(Voyage voyage) {
		this.voyage = voyage;
	}
	
	public boolean isFinished() {
		return voyage.isFinished();
	}

	public void setFinished(boolean finished) {
		voyage.setFinished(finished);
	}
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof VoyagePresentation)) {
			return false;
		}

		return EqualsHelper.equals(getVoyage(), ((VoyagePresentation) obj).getVoyage());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getVoyage());
	}
	
	public static String joinVoyageInfo(Voyage voyage){
		if (voyage != null && voyage.getVessel() != null) {
			return String.format("%1$s, %2$s,  %3$td.%3$tm.%3$tY", voyage
					.getVessel().getName(), voyage.getNumber(), voyage
					.getArrivalDate());
		} else {
			return "Судозаход не выбран";
		}
	}
	
	public String getVoyageInfo(){
		return joinVoyageInfo(voyage);
	}
}
