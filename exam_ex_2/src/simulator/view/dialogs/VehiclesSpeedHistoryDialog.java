package simulator.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent; 
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import simulator.control.Controller;
import simulator.model.RoadMap;
import simulator.view.tables.VehiclesSpeedHistoryTableModel;

public class VehiclesSpeedHistoryDialog extends JDialog {
	private static final long serialVersionUID = -6829793650797373850L;

	private JPanel _mainPanel, _textPanel, _selectPanel, _buttonPanel;
	private JPanel _viewPanel;
	private VehiclesSpeedHistoryTableModel _viewPanel11;

	private JSpinner tickSpinner;
	private JButton submitButton, cancelButton;
	private Controller _ctrl;

	public VehiclesSpeedHistoryDialog(Frame windowAncestor, RoadMap map, Controller ctrl) {
		super(windowAncestor, true);
		_ctrl = ctrl;
		initGUI();
	}

	protected void initGUI( ) {
		_mainPanel = new JPanel();
		_mainPanel.setLayout(new BoxLayout(_mainPanel, BoxLayout.PAGE_AXIS));
		setContentPane(_mainPanel);

		_textPanel = createTextPanel();
		_textPanel.setAlignmentX(CENTER_ALIGNMENT);

		_selectPanel = createSelectPanel();
		_selectPanel.setAlignmentX(CENTER_ALIGNMENT);

		_buttonPanel = createButtonPanel();
		_buttonPanel.setAlignmentX(CENTER_ALIGNMENT);

		// table
		
		_viewPanel11 = new VehiclesSpeedHistoryTableModel(_ctrl);
		 _viewPanel = createViewPanel(new JTable(_viewPanel11), "");
		 _viewPanel.setPreferredSize(new Dimension(500, 200));
		// _viewPanel.setPreferredSize(new Dimension(500, 200));
		// _mainPanel.add(_viewPanel);


		_mainPanel.add(_textPanel);
		_mainPanel.add(_selectPanel);
		_mainPanel.add(_buttonPanel);
		_mainPanel.add(_viewPanel);

		setTitle("Vehicles Speed History");
		setPreferredSize(new Dimension(500, 200));
		pack();
		setResizable(false);
		setVisible(false);
	}
	
	
	private void updateTable(int limit) {
		_viewPanel11.updateTable(limit);
		 _viewPanel = createViewPanel(new JTable(_viewPanel11), "");
		 _viewPanel11.updateTable(limit);
		
	}

	protected JPanel createTextPanel() {
		JPanel panel = new JPanel();
		String text = "<html>Select a speed limit and press UPDATE to show bla-bla-bla </html>";
		JLabel label = new JLabel("<html><div style='text-align: center;'>" + text + "</div></html>");
		panel.add(label);
		return panel;
	}

	protected JPanel createSelectPanel() {
		JPanel panel = new JPanel();

		tickSpinner = new JSpinner(new SpinnerNumberModel(70, 0, Integer.MAX_VALUE, 1));
		tickSpinner.setToolTipText("Speed limit: 1-" + Integer.MAX_VALUE);
		tickSpinner.setMaximumSize(new Dimension(80, 40));
		tickSpinner.setMinimumSize(new Dimension(80, 40));
		tickSpinner.setPreferredSize(new Dimension(80, 40));

		panel.add(new JLabel("Speed Limit: "));
		panel.add(tickSpinner);
		return panel;
	}
	
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel(new BorderLayout());
		Border b = BorderFactory.createLineBorder(Color.black, 2);
		JScrollPane sp = new JScrollPane(c);
		sp.setBorder(BorderFactory.createTitledBorder(b, title));
		sp.setPreferredSize(new Dimension(80, 80));
		p.add(sp);
		return p;
	}

	protected JPanel createButtonPanel() {
		JPanel panel = new JPanel();

		cancelButton = new JButton("Close");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VehiclesSpeedHistoryDialog.this.setVisible(false);
			}
		});
		submitButton = new JButton("Update");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTable((Integer) tickSpinner.getValue());
			}
		});

		panel.add(cancelButton);
		panel.add(submitButton);
		return panel;
	}

	public void open() {
		setLocation(250, 150);
		setVisible(true);
	}

}
