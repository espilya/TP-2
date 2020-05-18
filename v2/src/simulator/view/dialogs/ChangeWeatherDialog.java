package simulator.view.dialogs;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.Weather;

public class ChangeWeatherDialog<T1 extends Road, T2> extends OptionsDialog<T1, T2> {
	private static final long serialVersionUID = 7325697385390860815L;


	public ChangeWeatherDialog(Frame windowAncestor, RoadMap map) {
		super(windowAncestor, true, map);
		this.initGUI();
	}

	@Override
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

	@Override
	protected JPanel createTextPanel() {
		JPanel panel = new JPanel();
		String text = 
				"<html>Schedule an event to change the weather of a road after a given number <br> of simulation ticks from now </html>";
		JLabel label = new JLabel("<html><div style='text-align: center;'>" + text + "</div></html>");
		panel.add(label);
		return panel;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected JPanel createSelectPanel() {
		JPanel panel = new JPanel();

		firstModel = new DefaultComboBoxModel<T1>();
		firstList = new JComboBox<T1>(firstModel);
		
		firstModel.removeAllElements();
		for (Road r : _map.getRoads())
			firstModel.addElement((T1) r);

		secondModel = new DefaultComboBoxModel<T2>();
		secondModel.addElement((T2) Weather.SUNNY);
		secondModel.addElement((T2)Weather.CLOUDY);
		secondModel.addElement((T2)Weather.RAINY);
		secondModel.addElement((T2)Weather.WINDY);
		secondModel.addElement((T2)Weather.STORM);
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

	@Override
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

	@Override
	public int open() {
		setLocation(250, 150);
		setVisible(true);
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T1 getFirst() {
		return (T1) firstModel.getSelectedItem();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T2 getSecond() {
		return (T2) secondModel.getSelectedItem();
	}

	@Override
	public int getTicks() {
		return (int) tickSpinner.getValue();

	}
}
