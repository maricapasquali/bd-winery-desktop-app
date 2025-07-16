package com.winery.desktop.Model.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TypeProduct {
	RASPI{
		@Override
		public String toString() {
			return "Raspi";
		}
	}, MOSTO{
		@Override
		public String toString() {
			return "Mosto";
		}
	}, VINACCIA{
		@Override
		public String toString() {
			return "Vinaccia";
		}
	}, VNF{
		@Override
		public String toString() {
			return "VNF";
		}
	}, FECCIA{
		@Override
		public String toString() {
			return "Feccia";
		}
	}, VINO{
		@Override
		public String toString() {
			return "Vino";
		}
	};
	
	public static List<TypeProduct> productOf(PhaseProductionWine pp) {
		final List<TypeProduct> array = new ArrayList<>();
		switch (pp) {
		case PIGIATURA:
			array.add(RASPI);
			array.add(MOSTO);
			break;
		case SVINATURA:
			array.add(TypeProduct.VINACCIA);
			array.add(TypeProduct.VNF);
			break;
		case SFECCIATURA:
			array.add(TypeProduct.FECCIA);
			array.add(TypeProduct.VINO);
			break;
		default:
			break;
		}
		return array;
	}
		
	public static List<TypeProduct> withCask() {
		return Stream.of(TypeProduct.values()).filter(p -> !p.equals(VINACCIA) && !p.equals(RASPI)).collect(Collectors.toList());
	}
	
	public static TypeProduct conversion(final String typeP) {
		if(typeP.equals(RASPI.toString())) {
			return RASPI;
		}else if(typeP.equals(MOSTO.toString())){
			return MOSTO;
		}else if(typeP.equals(VINACCIA.toString())){
			return VINACCIA;
		}else if(typeP.equals(VNF.toString())){
			return VNF;
		}else if(typeP.equals(FECCIA.toString())){
			return FECCIA;
		}else if(typeP.equals(VINO.toString())){
			return VINO;
		}
		return null;
	}
	
	public static List<TypeProduct> firstProductForEachPhase(){
		return Arrays.asList(RASPI, VINACCIA, FECCIA);
		
	}
}
