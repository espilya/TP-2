package simulator.view.dialogs;

import java.awt.Frame;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import simulator.model.RoadMap;

/**
 * Class used earlier to implement inheritance for dialogs: CO2Class, Weather.
 *
 */
public abstract class OptionsDialog<T1, T2> extends JDialog {
	private static final long serialVersionUID = -6344194842352655281L;

	protected JPanel _mainPanel, _textPanel, _selectPanel, _buttonPanel;
	protected DefaultComboBoxModel<T1> firstModel;
	protected DefaultComboBoxModel<T2> secondModel;
	protected JComponent firstList;
	protected JComponent secondList;
	protected JSpinner tickSpinner;
	protected JButton submitButton, cancelButton;
	protected int status;
	protected RoadMap _map;

	protected OptionsDialog(Frame windowAncestor, boolean b, RoadMap map) {
		super(windowAncestor, true);
		_map = map;
		status = 0;
		this.initGUI();
	}

	protected abstract void initGUI();

	protected abstract JPanel createTextPanel();

	protected abstract JPanel createSelectPanel();

	protected abstract JPanel createButtonPanel();

	public abstract int open();

	public abstract T1 getFirst();

	public abstract T2 getSecond();

	public abstract int getTicks();

}
