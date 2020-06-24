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

import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog {
	private static final long serialVersionUID = 7325697385390860815L;

	protected JPanel _mainPanel, _textPanel, _selectPanel, _buttonPanel;
	protected DefaultComboBoxModel<Road> firstModel;
	protected DefaultComboBoxModel<Weather> secondModel;
	protected JComponent firstList;
	protected JComponent secondList;
	protected JSpinner tickSpinner;
	protected JButton submitButton, cancelButton;
	protected int status;
	protected RoadMap _map;
	
	public ChangeWeatherDialog(Frame windowAncestor, RoadMap map) {
		super(windowAncestor, true);
		_map = map;
		status = 0;
		initGUI();
	}

	protected void initGUI() {
		_mainPanel = new JPanel();
		_mainPanel.setLayout(new BoxLayout(_mainPanel, BoxLayout.PAGE_AXIS));
		setContentPane(_mainPanel);

		_textPanel = createTextPanel();
		_textPanel.setAlignmentX(CENTER_ALIGNMENT);

		_selectPanel = createSelectPanel();
		_selectPanel.setAlignmentX(CENTER_ALIGNMENT);

		_buttonPanel = createButtonPanel();
		_buttonPanel.setAlignmentX(CENTER_ALIGNMENT);

		_mainPanel.add(_textPanel);
		_mainPanel.add(_selectPanel);
		_mainPanel.add(_buttonPanel);

		setTitle("Change ROAD Weather");
		setPreferredSize(new Dimension(500, 200));
		pack();
		setResizable(false);
		setVisible(false);
	}

	protected JPanel createTextPanel() {
		JPanel panel = new JPanel();
		String text = 
				"<html>Schedule an event to change the weather of a road after a given number <br> of simulation ticks from now </html>";
		JLabel label = new JLabel("<html><div style='text-align: center;'>" + text + "</div></html>");
		panel.add(label);
		return panel;
	}

	protected JPanel createSelectPanel() {
		JPanel panel = new JPanel();

		firstModel = new DefaultComboBoxModel<Road>();
		firstList = new JComboBox<Road>(firstModel);
		
		firstModel.removeAllElements();
		for (Road r : _map.getRoads())
			firstModel.addElement(r);

		secondModel = new DefaultComboBoxModel<Weather>();
		secondModel.addElement(Weather.SUNNY);
		secondModel.addElement(Weather.CLOUDY);
		secondModel.addElement(Weather.RAINY);
		secondModel.addElement(Weather.WINDY);
		secondModel.addElement(Weather.STORM);
		secondList = new JComboBox<>(secondModel);

		tickSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 5000, 1));
		tickSpinner.setToolTipText("Simulation tick to run: 1-5000");
		tickSpinner.setMaximumSize(new Dimension(80, 40));
		tickSpinner.setMinimumSize(new Dimension(80, 40));
		tickSpinner.setPreferredSize(new Dimension(80, 40));

		panel.add(new JLabel("Road: "));
		panel.add(firstList);
		panel.add(new JLabel("Weather: "));
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
				ChangeWeatherDialog.this.setVisible(false);
			}
		});
		submitButton = new JButton("Ok");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (firstModel.getSelectedItem() != null) {
					status = 1;
					ChangeWeatherDialog.this.setVisible(false);
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

	public Road getFirst() {
		return (Road) firstModel.getSelectedItem();
	}

	public Weather getSecond() {
		return (Weather) secondModel.getSelectedItem();
	}

	public int getTicks() {
		return (int) tickSpinner.getValue();

	}
}
