package visual;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import src.Behavior;
import src.Constraint;
import src.ConstraintDate;
import src.ConstraintType;
import src.Context;
import src.ContextType;
import src.DeonticConcept;
import src.Entity;
import src.EntityType;
import src.Norm;

public class NormTableModel extends AbstractTableModel {

	// lista de linhas a serem exibidos na tela
	private List<Norm> linhas = null;

	// Array com os nomes das colunas
	private String[] colunas = null;

	// Constantes representando o índice das colunas
	private static final int deontic = 0;
	private static final int contextType = 1;
	private static final int context = 2;
	private static final int entityType = 3;
	private static final int entity = 4;
	private static final int behavior = 5;
	private static final int constraintType = 6;
	private static final int activation = 7;
	private static final int deactivation = 8;

	public NormTableModel(List<Norm> lin, String[] col) {
		setLinhas(lin);
		setColunas(col);
	}

	public List<Norm> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<Norm> linhas) {
		this.linhas = linhas;
	}

	public String[] getColunas() {
		return colunas;
	}

	public void setColunas(String[] colunas) {
		this.colunas = colunas;
	}

	// Cria a tabela sem nenhuma linha
	/*public NormTableModel() {
		linhas = new ArrayList<Object>;
	}*/

	// Cria a tabela contendo uma lista recebida por parametro
	/*public NormTableModel(List<Norm>listNorms) {
		linhas = new List<Norm>(listNorms);
	}*/

	// Pelo array criado com o nome colunas pode pegar os numeros de colunas da
	// tabela
	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	// Números de linhas da tabela definido pelo arrayList linhas
	@Override
	public int getRowCount() {
		return linhas.size();
	}

	// Retorna os nomes das colunas referente ao indice passado por parametro
	@Override
	public String getColumnName(int columnIndex) {
		return colunas[columnIndex];
	};

	// Verifica o índice recebido por parâmetro e retorna o tipo de classe
	// correspondente à coluna
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case deontic:
			return String.class;
		case contextType:
			return String.class;
		case context:
			return String.class;
		case entityType:
			return String.class;
		case entity:
			return String.class;
		case behavior:
			return String.class;
		case constraintType:
			return String.class;
		case activation:
			return DateTime.class;
		case deactivation:
			return DateTime.class;
		default:
			throw new IndexOutOfBoundsException("columnIndex out of bounds");
		}
	}

	// Nenhuma coluna poderá ser editada
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// Pega a norma referente a linha especificada.
		
		Norm norm = linhas.get(rowIndex);
		
		Object ac = buildConstraintToTable(norm.getActivationConstraint());
		Object dc = buildConstraintToTable(norm.getDeactivationConstraint());
				

		switch (columnIndex) {
		case deontic:
			return norm.getDeonticConcept();
		case contextType:
			return norm.getContext().getContextType();
		case context:
			return norm.getContext().getName();
		case entityType:
			return norm.getEntity().getEntityType();
		case entity:
			return norm.getEntity().getName();
		case behavior:
			return norm.getBehavior().getName();
		case constraintType:
			return norm.getActivationConstraint().getConstraintType();
		case activation:
			return ac;
		case deactivation:
			return dc;
		default:
			throw new IndexOutOfBoundsException("columnIndex out of bounds");
		}
	}

	// Retorna a norma referente a linha especificada
	public Norm getNorm(int indiceLinha) {
		return (Norm) linhas.get(indiceLinha);
	}
	
	  public Norm getValue(int indiceLinha){
	        return linhas.get(indiceLinha);
	    }
	

    // Você pode criar um método para recuperar quais os normas que foram selecionadas
    public List<Norm> recuperarNormasSelecionadas() {
        List<Norm> normasSelecionadas = new ArrayList<Norm>();

        for (Norm norm : linhas) {
            if (norm.isSelecionado()) {
                normasSelecionadas.add(norm);
            }
        }

        return normasSelecionadas;
    }
    

	
    public void setValueAt(Object value, int linha, int col) {    
       Norm norm = linhas.get(linha);    
 
        switch (col){    
        case 0: norm.setDeonticConcept((DeonticConcept) value);
        case 1: norm.getContext().setContextType((ContextType) value);;
        case 2: norm.setContext((Context) value);
        case 3: norm.getEntity().setEntityType((EntityType) value);;
        case 4: norm.setEntity((Entity) value);
        case 5: norm.setBehavior((Behavior) value);
        case 6: norm.getDeactivationConstraint().setConstraintType((ConstraintType) value);
        case 7:  norm.setSelecionado((Boolean)value); break;  
        //esse fica assim, não sei os outros  
          
        }    
   
      fireTableDataChanged();    
    } 

    
    public Norm get(int row) {  
        return linhas.get(row);  
    } 
    
    public List<Norm> getNorms(int[] rows) {  
    	  
        List<Norm> normas = new ArrayList<Norm>();  
        for (int row : rows) {  
            normas.add(linhas.get(row));  
        }  
        return normas;  
    }  
    
	// Adiciona a norma especificado ao modelo
	/*public void addNorm(Norm norm) {
		// Adiciona o registro.
		linhas.add(norm);

		// Pega a quantidade de registros e subtrai 1 para
		// achar o último índice. A subtração é necessária
		// porque os índices começam em zero.
		int ultimoIndice = getRowCount() - 1;

		// Notifica a mudança.
		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}*/

	//Remove a norma da linha especificada.
	public void removeNorm(int indiceLinha) {
		// Remove o registro.
		linhas.remove(indiceLinha);

		// Notifica a mudança.
		fireTableRowsDeleted(indiceLinha, indiceLinha);
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
	/* Remove todos os registros.
	public void limpar() {
		// Remove todos os elementos da lista de norm.
		linhas.clear();

		// Notifica a mudança.
		fireTableDataChanged();
	}*/

}
