package Model.builder;

import Model.Cask;

public class CaskBuilderImpl implements CaskBuilder {
	
	private Cask cask;
	
	public CaskBuilderImpl() {
		cask = new Cask();
	}

	@Override
	public Cask build() {
		return cask;
	}

	@Override
	public CaskBuilder setIDBotte(int iDBotte) {
		cask.setIDBotte(iDBotte);
		return this;
	}

	@Override
	public CaskBuilder setCapacity(double capacity) {
		cask.setCapacity(capacity);
		return this;
	}

	@Override
	public CaskBuilder setWinery(int winery) {
		cask.setWinery(winery);
		return this;
	}

}
