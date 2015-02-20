package src;

public class BehaviorCompositeAction extends Behavior {
	private BehaviorAtomicAction[] actions;
	
	//m�todo equals dever� ser implementado no futuro

	public BehaviorCompositeAction(String name, BehaviorAtomicAction[] actions) {
		this.name = name;
		this.actions = actions;
	}

	public BehaviorAtomicAction[] getActions() {
		return actions;
	}

	public void setActions(BehaviorAtomicAction[] actions) {
		this.actions = actions;
	}
}
