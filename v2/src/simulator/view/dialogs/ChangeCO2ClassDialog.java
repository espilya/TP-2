package simulator.view.dialogs;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog {
	private static final long serialVersionUID = 4030401133340738220L;
	
	protected JPanel _mainPanel, _textPanel, _selectPanel, _buttonPanel;
	protected DefaultComboBoxModel<Vehicle> firstModel;
	protected DefaultComboBoxModel<Integer> secondModel;
	protected JComponent firstList;
	protected JComponent secondList;
	protected JSpinner tickSpinner;
	protected JButton submitButton, cancelButton;
	protected int status;
	protected RoadMap _map;

	public ChangeCO2ClassDialog(Frame windowAncestor, RoadMap map) {
		super(windowAncestor, true);
		_map = map;
		status = 0;
		initGUI();
	}

	
	protected void initGUI() {
		_mainPanel = new JPanel();
		_mainPanel.setLayout(new BoxLayout(_mainPanel, BoxLayout.PAGE_AXIS));
		setContentPane(_mainPanel);

		// creating textPanel
		_textPanel = createTextPanel();
//		textPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		_textPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		// creating selectPanel
		_selectPanel = createSelectPanel();
		_selectPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		// creating buttonPanel
		_buttonPanel = createButtonPanel();
		_buttonPanel.setAlignmentX(CENTER_ALIGNMENT);

		_mainPanel.add(_textPanel);
		_mainPanel.add(_selectPanel);
		_mainPanel.add(_buttonPanel);

		setLocation(250, 150);
		setTitle("Change CO2 class");
		setPreferredSize(new Dimension(500, 200));
		pack();
		setResizable(false);
		setVisible(false);
	}

	
	protected JPanel createTextPanel() {
		JPanel panel = new JPanel();
		String text = "<html>Schedule an event to change the CO2 class of a vehicle after a given <br> number of simulation ticks from now</html>";
		JLabel label = new JLabel("<html><div style='text-align: center;'>" + text + "</div></html>");
		panel.add(label);

		return panel;
	}

	
	protected JPanel createSelectPanel() {
		JPanel panel = new JPanel();

		firstModel = new DefaultComboBoxModel<Vehicle>();
		firstList = new JComboBox<Vehicle>(firstModel);
		
		firstModel.removeAllElements();
		for (Vehicle v : _map.getVehicles()) {
			firstModel.addElement(v);
		}

		secondModel = new DefaultComboBoxModel<Integer>();
		for (int i = 0; i <= 10; i++)
			secondModel.addElement(i);
		secondList = new JComboBox<Integer>(secondModel);

		tickSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 5000, 1));
		tickSpinner.setMaximumSize(new Dimension(80, 40));
		tickSpinner.setMinimumSize(new Dimension(80, 40));
		tickSpinner.setPreferredSize(new Dimension(80, 40));

		panel.add(new JLabel("Vehicle: "));
		panel.add(firstList);
		panel.add(new JLabel("CO2 Class: "));
		panel.add(secondList);
		panel.add(new JLabel("Ticks: "));
		panel.add(tickSpinner);

		return panel;
	}

	
	protected JPanel createButtonPanel() {
		JPanel panel = new JPanel();

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				status = 0;
				ChangeCO2ClassDialog.this.setVisible(false);
			}
		});

		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (firstModel.getSelectedItem() != null) {
					status = 1;
					ChangeCO2ClassDialog.this.setVisible(false);
				}
			}
		});

		panel.add(cancelButton);
		panel.add(submitButton);
		return panel;
	}

	public int open() {
		setLocation(250, 150);
		setVisible(true);
		return status;
	}

	public Vehicle getFirst() {
		return  (Vehicle) firstModel.getSelectedItem();
	}

	public Integer getSecond() {
		return  (Integer) secondModel.getSelectedItem();
	}

	public int getTicks() {
		return (int) tickSpinner.getValue();

	}
}
