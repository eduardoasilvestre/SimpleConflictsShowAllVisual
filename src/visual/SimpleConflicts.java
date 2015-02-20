package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import src.Behavior;
import src.BehaviorAtomicAction;
import src.ConflictChecker;
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

//import visual.SimpleConflicts.SwingAction;

public class SimpleConflicts extends JFrame {

	private JPanel contentPane;
	private JTextField TF_entity;
	private JTextField TF_behavior;
	private JTextField TF_context;

	private State state;
	private ContextType contextType;
	private EntityType entityType;
	private DeonticConcept dConcept;
	private ConstraintType constraintType;

	String nameContext, nameEntity, Behavior, Activation, Deactivation;

	private DeonticConcept deontic;
	private Entity entity;
	private Context context;
	private Behavior behavior;
	private Constraint activation, deactivation;
	private ConstraintDate date;
	private ImportNorm importNorm;

	private final Action action = new SwingAction();
	private JTextField TF_aConstraint;
	private JTextField TF_dConstraint;
	private JComboBox<ContextType> CB_contextType;
	private JComboBox<EntityType> CB_entityType;
	private JComboBox<ConstraintType> CB_constraintType;
	private JComboBox<DeonticConcept> CB_deonticC;

	List<Norm> normSet = new ArrayList<>();
	List<Norm> norm = new ArrayList<>();
	List<Norm> normImport;

	private JTable table = null;
	private NormTableModel modelo;

	// private NormTableModel tableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimpleConflicts frame = new SimpleConflicts();
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
	public SimpleConflicts() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setExtendedState(MAXIMIZED_BOTH);
		setBounds(100, 100, 686, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel LB_DeonticC = new JLabel("Deontic Concept:");
		LB_DeonticC.setBounds(10, 11, 118, 14);
		panel.add(LB_DeonticC);

		CB_deonticC = new JComboBox<>();
		// CB_deonticC.addItem("OBLIGATION");
		// CB_deonticC.addItem("PERMISSION");
		// CB_deonticC.addItem("PROHIBITION");
		CB_deonticC
				.setModel(new DefaultComboBoxModel<>(DeonticConcept.values()));
		// CB_deonticC.setToolTipText("");
		CB_deonticC.setBounds(10, 27, 175, 20);
		panel.add(CB_deonticC);

		JLabel LB_entity = new JLabel("Entity name:");
		LB_entity.setBounds(202, 104, 84, 14);
		panel.add(LB_entity);

		TF_entity = new JTextField();
		TF_entity.setBounds(202, 119, 175, 20);
		panel.add(TF_entity);
		TF_entity.setColumns(10);

		JLabel LB_behavior = new JLabel("Behavior:");
		LB_behavior.setBounds(202, 12, 84, 14);
		panel.add(LB_behavior);

		TF_behavior = new JTextField();
		TF_behavior.setBounds(202, 27, 175, 20);
		panel.add(TF_behavior);
		TF_behavior.setColumns(10);

		JLabel LB_activation = new JLabel("Activation Constraint:");
		LB_activation.setBounds(204, 150, 133, 14);
		panel.add(LB_activation);

		JLabel LB_deactivaion = new JLabel("Deactivation Constraint:");
		LB_deactivaion.setBounds(10, 194, 175, 14);
		panel.add(LB_deactivaion);

		JLabel LB_context = new JLabel("Context name:");
		LB_context.setBounds(202, 58, 126, 14);
		panel.add(LB_context);

		TF_context = new JTextField();
		TF_context.setBounds(202, 73, 175, 20);
		panel.add(TF_context);
		TF_context.setColumns(10);

		JButton BT_save = new JButton("Save");
		BT_save.setAction(action);
		BT_save.setBounds(202, 203, 175, 28);
		panel.add(BT_save);

		JButton BT_duasNormas = new JButton("Conflicts Between 2 Norms");
		BT_duasNormas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				/**
				 * Este método pega 2 normas simples e responde se existe
				 * conflito direto entre elas. Além disso, mostra todos os
				 * conflitos existentes entre um conjunto de normas
				 * 
				 * @author eduardo.silvestre
				 */

				// Add as normas importadas na List normSet para verificação de
				// conflito

				ConflictChecker conflictChecker = new ConflictChecker();
				for (int i = 0; i < normSet.size(); i++) {
					for (int j = i + 1; j < normSet.size(); j++) {
						Norm norm1 = normSet.get(i);
						Norm norm2 = normSet.get(j);

						boolean conflictCheckerReturn = conflictChecker
								.conflictChecker2Norms(norm1, norm2);

						if (conflictCheckerReturn) {

							JOptionPane.showMessageDialog(
									null,
									"O programa encontrou os seguintes pares de normas em conflito: \n"
											+ norm1.toString()
											+ norm2.toString());

						}
					}
				}

				//normSet.clear();
			}
		});

		BT_duasNormas.setBounds(440, 51, 219, 28);
		panel.add(BT_duasNormas);

		// Importa as normas
		final JButton BT_importar = new JButton("Import Norms");
		BT_importar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				importNorm = new ImportNorm();
				importNorm.setModal(true);
				importNorm.setLocationRelativeTo(SwingUtilities
						.windowForComponent(BT_importar));
				importNorm.setVisible(true);
				normImport = importNorm.retornar();				
				preencherTabelaNormasDois(normImport);
				//normSet.clear();
				importNorm.dispose();
			
				
				for (Norm i : normImport) {
					if (!normSet.contains(i))
						normSet.add(i);
				}
			}
		});

		BT_importar.setBounds(440, 14, 219, 28);
		panel.add(BT_importar);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 242, 659, 121);
		panel.add(scrollPane_1);

		// Cria a Tabela
		table = new JTable();
		// tableModel = new NormTableModel();
		// table.setModel(tableModel);
		scrollPane_1.setViewportView(table);

		JLabel lbl_contextType = new JLabel("Context type:");
		lbl_contextType.setBounds(10, 58, 84, 14);
		panel.add(lbl_contextType);

		JLabel LB_entityType = new JLabel("Entity type:");
		LB_entityType.setBounds(10, 104, 84, 14);
		panel.add(LB_entityType);

		TF_aConstraint = new JTextField();
		TF_aConstraint.setBounds(202, 164, 177, 20);
		panel.add(TF_aConstraint);
		TF_aConstraint.setColumns(10);

		TF_dConstraint = new JTextField();
		TF_dConstraint.setBounds(10, 207, 177, 24);
		panel.add(TF_dConstraint);
		TF_dConstraint.setColumns(10);

		CB_contextType = new JComboBox<>();
		// CB_contextType.addItem("ORGANIZATION");
		// CB_contextType.addItem("ENVIRONMENT");
		CB_contextType
				.setModel(new DefaultComboBoxModel<>(ContextType.values()));
		CB_contextType.setBounds(10, 73, 175, 20);
		panel.add(CB_contextType);

		CB_entityType = new JComboBox<>();
		// CB_entityType.addItem("AGENT");
		// CB_entityType.addItem("ROLE");
		// CB_entityType.addItem("ORGANIZATION");
		// CB_entityType.addItem( "ALL");
		CB_entityType.setModel(new DefaultComboBoxModel<>(EntityType.values()));
		CB_entityType.setBounds(10, 119, 175, 20);
		panel.add(CB_entityType);

		JLabel LB_constraintType = new JLabel("Constrint Type:");
		LB_constraintType.setBounds(10, 150, 126, 14);
		panel.add(LB_constraintType);

		// Cria o ComboBox com os itens
		CB_constraintType = new JComboBox<>();
		// CB_constraintType.addItem("DATETYPE");
		// CB_constraintType.addItem("ACTIONTYPE");
		CB_constraintType.setModel(new DefaultComboBoxModel<>(ConstraintType
				.values()));
		CB_constraintType.setBounds(10, 163, 175, 20);
		panel.add(CB_constraintType);
	}

	// Este método recebe uma data no formato String e retorna um DateTime
	// (JodaTime)
	private static DateTime buildDateTime(String data) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime d = dtf.parseDateTime(data);
		return d;
	}

	// Salva os dados na tabela
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Save");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {

			dConcept = ((DeonticConcept) CB_deonticC.getSelectedItem());
			contextType = ((ContextType) CB_contextType.getSelectedItem());
			nameContext = TF_context.getText();
			entityType = ((EntityType) CB_entityType.getSelectedItem());
			nameEntity = TF_entity.getText();
			Behavior = TF_behavior.getText();
			constraintType = ((ConstraintType) CB_constraintType
					.getSelectedItem());
			Activation = TF_aConstraint.getText();
			Deactivation = TF_dConstraint.getText();
			entity = new Entity(nameEntity, entityType);
			activation = new ConstraintDate(constraintType,
					buildDateTime(Activation));
			deactivation = new ConstraintDate(constraintType,
					buildDateTime(Deactivation));
			context = new Context(nameContext, contextType);
			behavior = new BehaviorAtomicAction(Behavior);
			Norm norm = new Norm(dConcept, context, entity, behavior,
					activation, deactivation, state);
			normSet.add(norm);
			preencherTabelaNormasDois(normSet);

			/*
			 * for (int i = 0; i < normSet.size(); i++) {
			 * 
			 * Object ac =
			 * buildConstraintToTable(normSet.get(i).getActivationConstraint());
			 * Object dc =
			 * buildConstraintToTable(normSet.get(i).getDeactivationConstraint
			 * ());
			 * 
			 * 
			 * Object[] linhas = { normSet.get(i).getDeonticConcept(),
			 * normSet.get(i).getContext().getContextType(),
			 * normSet.get(i).getContext().getName(),
			 * normSet.get(i).getEntity().getEntityType(),
			 * normSet.get(i).getEntity().getName(),
			 * normSet.get(i).getBehavior().getName(),
			 * normSet.get(i).getActivationConstraint().getConstraintType(), ac,
			 * dc, normSet.get(i).getState() }; model.addRow(linhas);
			 * 
			 * }
			 */

		}
	}

	private Object buildConstraintToTable(Constraint constraint) {

		Object ac = "";
		if (constraint.getConstraintType().equals(ConstraintType.DATETYPE)) {
			return ((ConstraintDate) constraint).getDate().toString(
					DateTimeFormat.forPattern("dd/MM/yyyy"));
		} else if (constraint.getConstraintType().equals(
				ConstraintType.ACTIONTYPE)) {
			// todo...sera tratado no futuro
		}
		return ac;
	}

	public void preencherTabelaNormasDois(List<Norm> norm) {
		norm = norm;
		String[] colunas = new String[] { "Deontic", "ContextType", "Context",
				"EntityType", "Entity", "Behavior", "ConstraintType",
				"Activation", "Deactivation" };

		modelo = new NormTableModel(norm, colunas);
		table.setModel(modelo);
		table.getColumnModel().getColumn(7)
				.setCellEditor(new DefaultCellEditor(new JCheckBox()));
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
}
