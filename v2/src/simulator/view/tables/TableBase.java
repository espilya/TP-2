package simulator.view.tables;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.TrafficSimObserver;

public abstract class TableBase<T> extends AbstractTableModel implements TrafficSimObserver {
	private static final long serialVersionUID = -993463091959701722L;

	protected List<T> _list;

	protected String[] _colNames;

	public TableBase(String[] colnames, Controller ctrl) {
		ctrl.addObserver(this);
		_list = new ArrayList<>();
		_colNames = colnames;
		fireTableStructureChanged();
	}

	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	public int getRowCount() {
		return (_list == null ? 0 : _list.size());
	}

	public void setEventsList(List<T> events) {
		_list = events;
		fireTableStructureChanged();
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	}

	@Override
	public abstract Object getValueAt(int rowIndex, int columnIndex);

}
