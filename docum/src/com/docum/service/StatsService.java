package com.docum.service;

import java.util.List;

import com.docum.domain.Stats;

public interface StatsService {
	public static final String SERVICE_NAME = "statsService";
	//Получить информацицю по всем партиям груза в рамках инвойса.
	public List<Stats.CargoParty> getCargoParties(Long invoiceId);
	public List<Stats.CargoDefects> calcAverageDefects(Long containerId);
}
