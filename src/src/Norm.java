package src;

public class Norm {

	private DeonticConcept deonticConcept; /* deontic concept (obligation, prohibition or permission)*/

	private Context context; /* context where the norm is defined*/
	
	private Entity entity; /* entity whose behaviour is being regulated*/
	
	private Behavior behavior; /* behavior (action or state) being regulated*/
	
	private Constraint activationConstraint; /* condition that activates the norm*/
	
	private Constraint deactivationConstraint; /* condition that deactivates the norm */
	
	private State state; /* indicates the state of the norm (fulfilled, violated, none). */ 
	
	//dani
		private boolean selecionado;
	
	public Norm(DeonticConcept deonticConcept, Context context, Entity entity,
			Behavior behavior, Constraint activationConstraint,
			Constraint deactivationConstraint, State state) {
		
		this.deonticConcept = deonticConcept;
		this.context = context;
		this.entity = entity;
		this.behavior = behavior;
		this.activationConstraint = activationConstraint;
		this.deactivationConstraint = deactivationConstraint;
		this.state = state;
	}

	public DeonticConcept getDeonticConcept() {
		return deonticConcept;
	}

	public void setDeonticConcept(DeonticConcept deonticConcept) {
		this.deonticConcept = deonticConcept;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Behavior getBehavior() {
		return behavior;
	}

	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}

	public Constraint getActivationConstraint() {
		return activationConstraint;
	}

	public void setActivationConstraint(Constraint activationConstraint) {
		this.activationConstraint = activationConstraint;
	}

	public Constraint getDeactivationConstraint() {
		return deactivationConstraint;
	}

	public void setDeactivationConstraint(Constraint deactivationConstraint) {
		this.deactivationConstraint = deactivationConstraint;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	//dani
		public boolean isSelecionado() {
			return selecionado;
		}

		//dani
		public void setSelecionado(boolean selecionado) {
			this.selecionado = selecionado;
		}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		String dc =  deonticConcept == null ? "DeonticConcept: NULL | " : "DeonticConcept: " + deonticConcept + " | ";
		builder.append(dc);
	
		String c = context == null ? "Context: NULL | " : "Context: " + context.getName() + " | ";
		builder.append(c);
		
		String e = entity == null ? "Entity: NULL | " : "Entity: " + entity.getName() + " | ";
		builder.append(e);
		
		String a = behavior == null ? "Behavior: NULL | " : "Behavior: " + behavior.getName() + " | ";
		builder.append(a);
		
		String ac = null;
		if (activationConstraint == null) {
			ac = "ActivationConstraint: NULL" + " | ";
		} else if (activationConstraint instanceof ConstraintDate) {
			ac = "ActivationConstraint Date: " + ((ConstraintDate) activationConstraint).getDate().toString("dd/MM/yyyy") + " | ";
		}
		builder.append(ac);
		
		String de = null;
		if (deactivationConstraint == null) {
			de = "DeactivationConstraint: NULL" + " | ";
		} else if (deactivationConstraint instanceof ConstraintDate) {
			de = "DeactivationConstraint Date: " + ((ConstraintDate) deactivationConstraint).getDate().toString("dd/MM/yyyy") + " | " + "\n";
		}
		builder.append(de);
		
		//String s = state == null ? "State: NULL | " : "State: " + state + ;
		//builder.append(s);
				
		return builder.toString();
	}
}
