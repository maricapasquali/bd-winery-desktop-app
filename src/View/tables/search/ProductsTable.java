package View.tables.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang3.Pair;

import Model.Product;
import Model.enumeration.PhaseProductionWine;
import Model.enumeration.TypeProduct;
import View.tables.Table;

public class ProductsTable extends Table {

	private int colum;
	private Map<Long, Pair<Product, Product>> p;
	private PhaseProductionWine phase;

	public ProductsTable(final PhaseProductionWine ph, final Map<Long, Pair<Product, Product>> prod) {
		super();
		this.p = prod;
		phase = ph;
	}

	@Override
	protected AbstractTableModel getModelTable() {
		switch (phase) {
		case RACCOLTA:
			colum = 2;
			break;
		case SFECCIATURA:
			colum = 5;
			break;
		default:
			colum = 4;
			break;
		}
		return new AbstractTableModel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6690835242520498225L;
			private static final String KG = " (kg) ";
			private static final String LT = " (Lt) ";

			@Override
			public final int getRowCount() {
				return p.size();
			}

			@Override
			public final int getColumnCount() {
				return colum;
			}

			@Override
			public final Object getValueAt(final int rowIndex, final int columnIndex) {
				final List<Pair<Product, Product>> lP = new ArrayList<>(p.values());
				final boolean thereArefirstProd = lP.get(rowIndex).left != null;
				final boolean thereAreSecProd = lP.get(rowIndex).right != null;
				switch (columnIndex) {
				case 0:
					return thereArefirstProd ? lP.get(rowIndex).left.getPhaseProduction().getGrape().getName()
							: thereAreSecProd ? lP.get(rowIndex).right.getPhaseProduction().getGrape().getName() : null;
				case 1:
					return thereArefirstProd ? lP.get(rowIndex).left.getQuantity() : null;

				case 2:
					switch (phase) {
					case SFECCIATURA:
						return thereArefirstProd ? lP.get(rowIndex).left.getCask().get() : null;
					default:
						return thereAreSecProd ? lP.get(rowIndex).right.getQuantity() : null;
					}
				case 3:
					switch (phase) {
					case SFECCIATURA:
						return thereAreSecProd ? lP.get(rowIndex).right.getQuantity() : null;
					default:
						return thereAreSecProd ? lP.get(rowIndex).right.getCask().get() : null;
					}
				case 4:
					return thereAreSecProd ? lP.get(rowIndex).right.getCask().get() : null;
				}
				return "";
			}

			@Override
			public final String getColumnName(final int column) {
				switch (column) {
				case 0:
					return "Uva";
				case 1:
					if (PhaseProductionWine.withProducts().contains(phase)) {
						switch (phase) {
						case PIGIATURA:
							return TypeProduct.RASPI.toString() + KG;
						case SVINATURA:
							return TypeProduct.VINACCIA.toString() + KG;
						case SFECCIATURA:
							return TypeProduct.FECCIA.toString() + KG;
						default:
							break;
						}
					} else {
						return "Quantita" + KG;
					}
				case 2:
					switch (phase) {
					case PIGIATURA:
						return TypeProduct.MOSTO.toString() + KG;
					case SVINATURA:
						return TypeProduct.VNF.toString() + LT;
					default:
						return "Botte";
					}
				case 3:
					switch (phase) {
					case SFECCIATURA:
						return TypeProduct.VINO.toString() + LT;
					default:
						return "Botte";
					}
				case 4:
					return "Botte";
				default:
				}
				return "";
			}

			@Override
			public final Class<?> getColumnClass(final int column) {

				switch (column) {
				case 0:
					return String.class;
				case 1:
					return Double.class;
				case 2:
					return Object.class;
				case 3:
					return Object.class;
				case 4:
					return Integer.class;

				default:
				}
				return Object.class;
			}

		};
	}

}
