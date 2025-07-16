package com.winery.Model.enumeration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum PhaseProductionWine {
	
	RACCOLTA{
		@Override
		public String toString() {
			return "Raccolta";
		}
	}, PIGIATURA{
		@Override
		public String toString() {
			return "Pigiatura";
		}
	}, SVINATURA{
		@Override
		public String toString() {
			return "Svinatura";
		}
	},SFECCIATURA{
		@Override
		public String toString() {
			return "Sfecciatura";
		}
	};
	
	public static List<PhaseProductionWine> withProducts(){
		return Stream.of(PhaseProductionWine.values()).filter(p -> !p.equals(RACCOLTA)).collect(Collectors.toList());
	}
	
	public static PhaseProductionWine conversion(final String typePP) {
		if(typePP.equals(RACCOLTA.toString())) {
			return RACCOLTA;
		}else if(typePP.equals(PIGIATURA.toString())){
			return PIGIATURA;
		}else if(typePP.equals(SVINATURA.toString())){
			return SVINATURA;
		}else if(typePP.equals(SFECCIATURA.toString())){
			return SFECCIATURA;
		}
		return null;
	}
	
}
