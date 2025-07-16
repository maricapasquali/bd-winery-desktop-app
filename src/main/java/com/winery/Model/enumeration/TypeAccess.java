package com.winery.Model.enumeration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TypeAccess {
	ADMIN {
		@Override
		public String toString() {
			return "Admin";
		}
	},
	DIPENDENTE {
		@Override
		public String toString() {
			return "Dipendente";
		}
	},
	PART_TIME {
		@Override
		public String toString() {
			return "Part-time";
		}
	};

	public static List<TypeAccess> workers(){
		return Stream.of(TypeAccess.values()).filter(p -> !p.equals(ADMIN)).collect(Collectors.toList());
	}
}
