package simulator.view.tables;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.model.TrafficSimObserver;

public abstract class GenericTableModel<T> extends AbstractTableModel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;

	protected List<T> _elements;
	protected String[] _colNames;
	
	public GenericTableModel(String[] colNames){
		this._colNames = colNames;
	}
	
	public void update() {
		fireTableDataChanged();		
	}
	
	public String getColumnName(int col) {
		return _colNames[col];
	}
	
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	public int getRowCount() {
		return _elements == null ? 0 : _elements.size();
	}
	
	protected void update(List<T> elements) {
		this._elements = elements;
		fireTableStructureChanged();
	}

}