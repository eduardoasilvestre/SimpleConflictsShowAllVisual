package visual;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import src.Behavior;
import src.BehaviorAtomicAction;
import src.Constraint;
import src.ConstraintDate;
import src.ConstraintType;
import src.Context;
import src.ContextType;
import src.DeonticConcept;
import src.Entity;
import src.EntityType;
import src.Norm;
import src.State;

public class ImportNorm extends JDialog {

	private JPanel contentPane;
	private JTable table = null;
	List<Norm> normSet = new ArrayList<>();
	List<Norm> norm = new ArrayList<>();
	private NormTableModel modelo;
	private SimpleConflicts normas;

	private final Action action = new SwingAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImportNorm frame = new ImportNorm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ImportNorm() {
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 749, 293);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		Panel panel = new Panel();
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("NORMS");
		lblNewLabel.setBounds(336, 11, 60, 20);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 65, 703, 136);
		panel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JButton BT_import = new JButton("IMPORT");
		BT_import.setAction(action);
		BT_import.setBounds(318, 212, 89, 23);
		panel.add(BT_import);

		// Este método recebe uma data no formato String e retorna um DateTime
		// (JodaTime)

		// sList<Norm> normSet = new ArrayList<>();

		// n1 and n2 are in conflict: OBLIGATION X PROHIBITION
		/*
		 * Context context1 = new Context("university",
		 * ContextType.ORGANIZATION); // Context context1 = new
		 * Context("university", // ContextType.ORGANIZATION); Entity entity1 =
		 * new Entity("student", EntityType.ROLE); Behavior action1 = new
		 * BehaviorAtomicAction("study"); Constraint aConstraint1 = new
		 * ConstraintDate(ConstraintType.DATETYPE, buildDateTime("18/07/2014"));
		 * Constraint dConstraint1 = new ConstraintDate(ConstraintType.DATETYPE,
		 * buildDateTime("20/07/2014")); Norm norm20 = new
		 * Norm(DeonticConcept.OBLIGATION, context1, entity1, action1,
		 * aConstraint1, dConstraint1, State.NONE); normSet.add(norm20);
		 * 
		 * // n2 and n3 are in conflict: PROHIBITION X PERMISSION Context
		 * context2 = new Context("university", ContextType.ORGANIZATION);
		 * Entity entity2 = new Entity("student", EntityType.ROLE); Behavior
		 * action2 = new BehaviorAtomicAction("study"); Constraint aConstraint2
		 * = new ConstraintDate(ConstraintType.DATETYPE,
		 * buildDateTime("18/07/2014")); Constraint dConstraint2 = new
		 * ConstraintDate(ConstraintType.DATETYPE, buildDateTime("20/07/2014"));
		 * Norm norm21 = new Norm(DeonticConcept.PROHIBITION, context2, entity2,
		 * action2, aConstraint2, dConstraint2, State.NONE);
		 * normSet.add(norm21);
		 * 
		 * Context context3 = new Context("university",
		 * ContextType.ORGANIZATION); Entity entity3 = new Entity("student",
		 * EntityType.ROLE); Behavior action3 = new
		 * BehaviorAtomicAction("study"); Constraint aConstraint3 = new
		 * ConstraintDate(ConstraintType.DATETYPE, buildDateTime("19/07/2014"));
		 * Constraint dConstraint3 = new ConstraintDate(ConstraintType.DATETYPE,
		 * buildDateTime("20/07/2014")); Norm norm3 = new
		 * Norm(DeonticConcept.PERMISSION, context3, entity3, action3,
		 * aConstraint3, dConstraint3, State.NONE); normSet.add(norm3);
		 */

		// n4 is not in conflict (with n1, n2 or n3) because the norm is
		// FULFILLED (could be VIOLATED)
		/*
		 * Context context4 = new Context("university",
		 * ContextType.ORGANIZATION); Entity entity4 = new Entity("student",
		 * EntityType.ROLE); Behavior action4 = new
		 * BehaviorAtomicAction("study"); Constraint aConstraint4 = null;
		 * Constraint dConstraint4 = null; Norm norm4 = new
		 * Norm(DeonticConcept.PROHIBITION, context4, entity4, action4,
		 * aConstraint4, dConstraint4, State.NONE); normSet.add(norm4);
		 * 
		 * // n5 x n2: ther is not a conflict because the actions are
		 * differente. // How to solve ????????? Context context5 = new
		 * Context("university", ContextType.ORGANIZATION); Entity entity5 = new
		 * Entity("student", EntityType.ROLE); Behavior action5 = new
		 * BehaviorAtomicAction("have lunch"); Constraint aConstraint5 = null;
		 * Constraint dConstraint5 = null; Norm norm5 = new
		 * Norm(DeonticConcept.OBLIGATION, context5, entity5, action5,
		 * aConstraint5, dConstraint5, State.NONE); normSet.add(norm5);
		 * 
		 * // n6 x n7: there is no conflict. Different entities Context context6
		 * = new Context("team", ContextType.ORGANIZATION); Entity entity6 = new
		 * Entity("player", EntityType.ROLE); Behavior action6 = new
		 * BehaviorAtomicAction("play"); Constraint aConstraint6 = null;
		 * Constraint dConstraint6 = null; State state6 = null; Norm norm6 = new
		 * Norm(DeonticConcept.PROHIBITION, context6, entity6, action6,
		 * aConstraint6, dConstraint6, State.NONE); normSet.add(norm6);
		 * 
		 * Context context7 = new Context("team", ContextType.ORGANIZATION);
		 * Entity entity7 = new Entity("manager", EntityType.ROLE); Behavior
		 * action7 = new BehaviorAtomicAction("play"); Constraint aConstraint7 =
		 * null; Constraint dConstraint7 = null; Norm norm7 = new
		 * Norm(DeonticConcept.PROHIBITION, context7, entity7, action7,
		 * aConstraint7, dConstraint7, State.NONE); normSet.add(norm7);
		 * 
		 * // norm8 and norm9 different contexts Context context8 = new
		 * Context("team", ContextType.ENVIRONMENT); Entity entity8 = new
		 * Entity("manager", EntityType.ROLE); Behavior action8 = new
		 * BehaviorAtomicAction("manage"); Constraint aConstraint8 = null;
		 * Constraint dConstraint8 = null; Norm norm8 = new
		 * Norm(DeonticConcept.PERMISSION, context8, entity8, action8,
		 * aConstraint8, dConstraint8, State.NONE); normSet.add(norm8);
		 * 
		 * Context context9 = new Context("company", ContextType.ORGANIZATION);
		 * Entity entity9 = new Entity("manager", EntityType.ROLE); Behavior
		 * action9 = new BehaviorAtomicAction("manage"); Constraint aConstraint9
		 * = null; Constraint dConstraint9 = null; Norm norm9 = new
		 * Norm(DeonticConcept.PROHIBITION, context9, entity9, action9,
		 * aConstraint9, dConstraint9, State.NONE); normSet.add(norm9);
		 * 
		 * // n10 and n11: is not in conflict due the different action Context
		 * context10 = new Context("company", ContextType.ORGANIZATION); Entity
		 * entity10 = new Entity("manager", EntityType.ROLE); Behavior action10
		 * = new BehaviorAtomicAction("manage"); Constraint aConstraint10 =
		 * null; Constraint dConstraint10 = null; Norm norm10 = new
		 * Norm(DeonticConcept.PROHIBITION, context10, entity10, action10,
		 * aConstraint10, dConstraint10, State.NONE); normSet.add(norm10);
		 * 
		 * Context context11 = new Context("company", ContextType.ORGANIZATION);
		 * Entity entity11 = new Entity("manager", EntityType.ROLE); Behavior
		 * action11 = new BehaviorAtomicAction("manage"); Constraint
		 * aConstraint11 = null; Constraint dConstraint11 = null; Norm norm11 =
		 * new Norm(DeonticConcept.PROHIBITION, context11, entity11, action11,
		 * aConstraint11, dConstraint11, State.NONE); normSet.add(norm11);
		 * 
		 * // norm11 and norm12 are not in conflict because norm12 is violated
		 * Context context12 = new Context("company", ContextType.ORGANIZATION);
		 * Entity entity12 = new Entity("manager", EntityType.ROLE); Behavior
		 * action12 = new BehaviorAtomicAction("manage"); Constraint
		 * aConstraint12 = null; Constraint dConstraint12 = null; Norm norm12 =
		 * new Norm(DeonticConcept.PERMISSION, context12, entity12, action12,
		 * aConstraint12, dConstraint12, State.VIOLATED); normSet.add(norm12);
		 */

		// norm13, norm14 and norm 15 consider time
		// norm13 x norm14: there is not conflict
		// norm14 x norm15: there is conflict

		Context context13 = new Context("home", ContextType.ORGANIZATION);
		Entity entity13 = new Entity("mother", EntityType.ROLE);
		Behavior action13 = new BehaviorAtomicAction("cook");
		Constraint aConstraint13 = new ConstraintDate(ConstraintType.DATETYPE,
				buildDateTime("18/07/2014"));
		Constraint dConstraint13 = new ConstraintDate(ConstraintType.DATETYPE,
				buildDateTime("20/07/2014"));
		Norm norm13 = new Norm(DeonticConcept.OBLIGATION, context13, entity13,
				action13, aConstraint13, dConstraint13, State.NONE);
		normSet.add(norm13);

		Context context14 = new Context("home", ContextType.ORGANIZATION);
		Entity entity14 = new Entity("mother", EntityType.ROLE);
		Behavior action14 = new BehaviorAtomicAction("cook");
		Constraint aConstraint14 = new ConstraintDate(ConstraintType.DATETYPE,
				buildDateTime("15/07/2014"));
		Constraint dConstraint14 = new ConstraintDate(ConstraintType.DATETYPE,
				buildDateTime("17/07/2014"));
		Norm norm14 = new Norm(DeonticConcept.PROHIBITION, context14, entity14,
				action14, aConstraint14, dConstraint14, State.NONE);
		normSet.add(norm14);

		Context context15 = new Context("home", ContextType.ORGANIZATION);
		Entity entity15 = new Entity("mother", EntityType.ROLE);
		Behavior action15 = new BehaviorAtomicAction("cook");
		Constraint aConstraint15 = new ConstraintDate(ConstraintType.DATETYPE,
				buildDateTime("19/07/2014"));
		Constraint dConstraint15 = new ConstraintDate(ConstraintType.DATETYPE,
				buildDateTime("20/07/2014"));
		Norm norm15 = new Norm(DeonticConcept.PROHIBITION, context15, entity15,
				action15, aConstraint15, dConstraint15, State.NONE);
		normSet.add(norm15);

		preencherTabelaNormas(normSet);

		/*
		 * ArrayList<Object> objeto = new ArrayList<Object>(); for (int i = 0; i
		 * < normSet.size(); i++) { Object[] linhas =
		 * {normSet.get(i).getDeonticConcept(),
		 * normSet.get(i).getContext().getContextType(),
		 * normSet.get(i).getContext().getName(),
		 * normSet.get(i).getEntity().getEntityType(),
		 * normSet.get(i).getEntity().getName(),
		 * normSet.get(i).getBehavior().getName(), "NULL", "NULL", "NULL",
		 * normSet.get(i).getState()}; objeto.add(linhas);
		 * preencherTabelaNormas(objeto);
		 * 
		 * 
		 * }
		 */

		/*
		 * for (int i = 0; i < normSet.size(); i++) {
		 * 
		 * Object ac =
		 * buildConstraintToTable(normSet.get(i).getActivationConstraint());
		 * Object dc =
		 * buildConstraintToTable(normSet.get(i).getDeactivationConstraint());
		 * 
		 * 
		 * Object[] linhas = { normSet.get(i).getDeonticConcept(),
		 * normSet.get(i).getContext().getContextType(),
		 * normSet.get(i).getContext().getName(),
		 * normSet.get(i).getEntity().getEntityType(),
		 * normSet.get(i).getEntity().getName(),
		 * normSet.get(i).getBehavior().getName(),
		 * normSet.get(i).getActivationConstraint().getConstraintType(), ac, dc,
		 * normSet.get(i).getState() }; }
		 */

	}

	private static DateTime buildDateTime(String data) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime d = dtf.parseDateTime(data);
		return d;
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "IMPORT");
			putValue(SHORT_DESCRIPTION, "IMPORT");
		}

		public void actionPerformed(ActionEvent e) {

			setVisible(false);

		}
	}

	/*
	 * private Object buildConstraintToTable(Constraint constraint) {
	 * 
	 * Object ac = ""; if
	 * (constraint.getConstraintType().equals(ConstraintType.DATETYPE)) { return
	 * ((ConstraintDate) constraint).getDate().toString(
	 * DateTimeFormat.forPattern("dd/MM/yyyy")); } else if
	 * (constraint.getConstraintType().equals( ConstraintType.ACTIONTYPE)) { //
	 * todo...sera tratado no futuro } return ac; }
	 */

	public void preencherTabelaNormas(List<Norm> norm) {
		// ArrayList<Norm> dados = new ArrayList<Norm>();
		String[] colunas = new String[] { "Deontic", "ContextType", "Context",
				"EntityType", "Entity", "Behavior", "ConstraintType", "Activation", "Deactivation" };

		modelo = new NormTableModel(norm, colunas);
		table.setModel(modelo);
		table.setEnabled(true);
		/*
		 * table.getColumnModel().getColumn(0).setPreferredWidth(30);
		 * table.getColumnModel().getColumn(0).setResizable(false);
		 * table.getColumnModel().getColumn(1).setPreferredWidth(120);
		 * table.getColumnModel().getColumn(1).setResizable(false);
		 * table.getColumnModel().getColumn(2).setPreferredWidth(100);
		 * jTableFazendas.getColumnModel().getColumn(2).setResizable(false);
		 * jTableFazendas.getColumnModel().getColumn(3).setPreferredWidth(100);
		 * jTableFazendas.getColumnModel().getColumn(3).setResizable(false);
		 * jTableFazendas.getColumnModel().getColumn(4).setPreferredWidth(80);
		 * jTableFazendas.getColumnModel().getColumn(4).setResizable(false);
		 * jTableFazendas.getColumnModel().getColumn(5).setPreferredWidth(80);
		 * jTableFazendas.getColumnModel().getColumn(5).setResizable(false);
		 * jTableFazendas.getColumnModel().getColumn(6).setPreferredWidth(80);
		 * jTableFazendas.getColumnModel().getColumn(6).setResizable(false);
		 * jTableFazendas.getTableHeader().setReorderingAllowed(false);
		 * jTableFazendas.setAutoResizeMode(jTableFazendas.AUTO_RESIZE_OFF);
		 * jTableFazendas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 */
	}

	public List<Norm> retornar() {
		int[] lin = table.getSelectedRows();
		for (int i = 0; i < lin.length; i++) {
			norm.add(modelo.getNorm(i));
		}
		return norm;
	}
}
