package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import simulator.control.Controller;
import simulator.misc.SortedArrayList;
import simulator.model.TrafficSimObserver;

public abstract class TableModel<T> extends AbstractTableModel  implements TrafficSimObserver{
	protected String[] columnIds;
	protected List<T> lista;
	
	public TableModel(String[] _columnId, Controller ctrl) {
		this.lista = new SortedArrayList<>();
		this.setColumnNames(_columnId);
		ctrl.addObserver(this);
	}
	public void setColumnNames(String[] columnNames) {
		 this.columnIds = columnNames;
		 this.fireTableStructureChanged();
	}
	public String getColumnName(int col) {
		return this.columnIds[col];
	}
	public int getColumnCount() {
		return this.columnIds.length;
	}
	public int getRowCount() {
		return this.lista == null ? 0 : this.lista.size();
	}
	
}