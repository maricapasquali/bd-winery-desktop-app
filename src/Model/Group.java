package Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import Utility.Utility;
import exception.JustInsertException;

public class Group {

	private static Map<PersonCompany, Optional<Integer>> workerAndHoursWork;
	private static PhaseProduction idPhase;
	private static Optional<Long> idHarvester;

	public Group() {
		workerAndHoursWork = new HashMap<>();
		idPhase = null;
		idHarvester = Optional.empty();
	}

	public Map<PersonCompany, Optional<Integer>> getWorkerAndHoursWork() {
		return workerAndHoursWork;
	}

	public void addWorkerAndHoursWork(final PersonCompany pp, final Integer hours) throws JustInsertException {
		Utility.check(pp==null || (pp.isPartTime() && hours==0));
		if(workerAndHoursWork.containsKey(pp)) {
			throw new JustInsertException("Operaio");
		}
		workerAndHoursWork.put(pp, hours.intValue() == 0 ? Optional.empty() : Optional.ofNullable(hours));
	}

	public PhaseProduction getPhaseProduction() {
		return idPhase;
	}

	public void setPhaseProduction(PhaseProduction phaseProd) {
		idPhase = phaseProd;
	}

	public Optional<Long> getIdHarvester() {
		return idHarvester;
	}

	public void setIdHarvester(Long idHarve) {
		idHarvester = Optional.ofNullable(idHarve);
	}
	
	public boolean isSetIdPhase() {
		return idPhase != null;
	}
	
	public void reset () {
		workerAndHoursWork = new HashMap<>();
		idPhase = null;
		idHarvester = Optional.empty();
	}
	
	public static void String() {
		Utility.log("Group ");
		workerAndHoursWork.entrySet().stream().forEach(e -> Utility.log(" Worker= ["+e.getKey().string() + "], Hours= "+ e.getValue() +
				", idPhase=" + idPhase + ", idHarvester=" + idHarvester + "]"));
	
	}
}
