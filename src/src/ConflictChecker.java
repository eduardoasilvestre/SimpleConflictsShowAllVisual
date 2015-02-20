package src;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class ConflictChecker {
	public boolean conflictChecker2Norms(Norm norm1, Norm norm2) {

		// returns true if there is a conflict: prohibition x obligation OR prohibition x permission
		boolean conflictConcept = deonticConceptChecker(norm1, norm2);
		if (!conflictConcept) {
			return false;
		}
		
		// returns true if the state of the norms are both NONE
		boolean normState = stateChecker(norm1, norm2);
		if (!normState) {
			return false;
		}

		// returns true if the context of the norms are the same
		boolean conflictContext = contextChecker(norm1, norm2);
		if (!conflictContext) {
			return false;
		}

		// returns true if the if the entities are the same OR one is ALL
		boolean conflictEntity = entityChecker(norm1, norm2);
		if (!conflictEntity) {
			return false;
		}

		// returns true if there is not conflict between activation and deactivation constraint
		boolean conflictConstraint = constraintChecker(norm1, norm2);
		if (!conflictConstraint) {
			return false;
		}

		// returns true if the action are the same
		boolean conflictAction = actionChecker(norm1, norm2);
		if (!conflictAction) {
			return false;
		}

		// at this moment all conditions are valid and the norms are in conflict
		return true;
	}

	public boolean stateChecker(Norm norm1, Norm norm2) {
		State s1 = norm1.getState();
		State s2 = norm2.getState();

		if (s1 == null) {
			norm1.setState(State.NONE);
		}

		if (s2 == null) {
			norm2.setState(State.NONE);
		}
		if (s1.equals(State.NONE) && s2.equals(State.NONE)) {
			return true;
		}

		return false;
	}

	public boolean deonticConceptChecker(Norm norm1, Norm norm2) {
		if ((norm1.getDeonticConcept().equals(DeonticConcept.PROHIBITION) && 
			norm2.getDeonticConcept().equals(DeonticConcept.OBLIGATION))
			|| (norm1.getDeonticConcept().equals(DeonticConcept.PROHIBITION) && 
			norm2.getDeonticConcept().equals(DeonticConcept.PERMISSION))) {
			return true;
		}
		if ((norm2.getDeonticConcept().equals(DeonticConcept.PROHIBITION) && 
			norm1.getDeonticConcept().equals(DeonticConcept.OBLIGATION))
			|| (norm2.getDeonticConcept().equals(DeonticConcept.PROHIBITION) && 
			norm1.getDeonticConcept().equals(DeonticConcept.PERMISSION))) {
			return true;
		}

		return false;
	}

	public boolean contextChecker(Norm norm1, Norm norm2) {
		Context c1 = norm1.getContext();
		Context c2 = norm2.getContext();

		if (c1 == null || c1.getName() == null || c1.getContextType() == null) {
			c1 = new Context("context1", ContextType.ORGANIZATION);
			norm1.setContext(c1);
		}

		if (c2 == null || c2.getName() == null || c2.getContextType() == null) {
			c2 = new Context("context2", ContextType.ORGANIZATION);
			norm2.setContext(c2);
		}

		if (norm1.getContext().equals(norm2.getContext())) {
			return true;
		}

		return false;
	}

	public boolean entityChecker(Norm norm1, Norm norm2) {

		Entity e1 = norm1.getEntity();
		Entity e2 = norm2.getEntity();

		if (e1 == null || e1.getName() == null || e1.getEntityType() == null) {
			return false;
		}
		if (e2 == null || e2.getName() == null || e2.getEntityType() == null) {
			return false;
		}

		// if the execution arrived here means that all fields are filled
		if (e1.getEntityType().equals(EntityType.ALL)) {
			e1.setEntityType(e2.getEntityType());
			norm1.setEntity(e1);
		}
		if (e2.getEntityType().equals(EntityType.ALL)) {
			e2.setEntityType(e1.getEntityType());
			norm2.setEntity(e2);
		}

		// if the entities are equal
		if (norm1.getEntity().equals(norm2.getEntity())) {
			return true;
		}

		return false;
	}

	public boolean actionChecker(Norm norm1, Norm norm2) {
		Behavior b1 = norm1.getBehavior();
		Behavior b2 = norm2.getBehavior();

		if (b1 == null || b2 == null) {
			return false;
		}

		
		if (b1 instanceof BehaviorAtomicAction && b2 instanceof BehaviorAtomicAction) {
			if (b1.equals(b2)) {
				return true;
			}
			return false;

		}
		//novos casos devem ser feitos posteriormente
			
		return false;
	}


	
	public boolean constraintChecker(Norm norm1, Norm norm2) {
		
		//for realize the comparisons all the fields must be filled, if one field is null then we don't have problem
		if (norm1.getActivationConstraint() == null || norm1.getDeactivationConstraint() == null ||
			norm2.getActivationConstraint() == null || norm2.getDeactivationConstraint() == null) {
			
			norm1.setActivationConstraint(null);
			norm2.setActivationConstraint(null);
			norm1.setDeactivationConstraint(null);
			norm2.setDeactivationConstraint(null);
			
			return true;
		}
		
		ConstraintType na1 = norm1.getActivationConstraint().getConstraintType();
		ConstraintType nd1 = norm1.getDeactivationConstraint().getConstraintType();
		
		ConstraintType na2 = norm2.getActivationConstraint().getConstraintType();
		ConstraintType nd2 = norm2.getDeactivationConstraint().getConstraintType();
		
		//it is necessary only 3 tests
		if (!na1.equals(nd1) || !na2.equals(nd2) || !na1.equals(na2)) {
			norm1.setActivationConstraint(null);
			norm2.setActivationConstraint(null);
			norm1.setDeactivationConstraint(null);
			norm2.setDeactivationConstraint(null);
			
			return true;
		}
		
		// If the activation conditions are actions
		if (norm1.getActivationConstraint().getConstraintType().equals(ConstraintType.ACTIONTYPE)
				&& norm1.getActivationConstraint().getConstraintType().equals(ConstraintType.ACTIONTYPE)) {

			//todo...o tratamento vai ser realizado no futuro, caso necessário
				
			return true;
		}
		
		//
		//at this moment the constrainttype are both DATETYPE, so it is not necessary more comparisons
		//
		
		DateTime d1Begin = ((ConstraintDate) norm1.getActivationConstraint()).getDate();
		DateTime d1End = ((ConstraintDate) norm1.getDeactivationConstraint()).getDate();
		DateTime d2Begin = ((ConstraintDate) norm2.getActivationConstraint()).getDate();
		DateTime d2End = ((ConstraintDate) norm2.getDeactivationConstraint()).getDate();
		
		boolean r = this.compareDateIntervals(d1Begin, d1End, d2Begin, d2End);
		return r;
	}
	
	public boolean compareDateIntervals(DateTime d1Begin, DateTime d1End, DateTime d2Begin, DateTime d2End){
		Interval i1 = new Interval(d1Begin,d1End);
		Interval i2 = new Interval(d2Begin,d2End);
		return i1.overlaps(i2);
		//http://stackoverflow.com/questions/17106670/how-to-check-a-timeperiod-is-overlapping-another-time-period-in-java
	}
}
