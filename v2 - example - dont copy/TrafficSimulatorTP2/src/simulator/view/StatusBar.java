package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver{
	private Controller ctrl;
	
	private JPanel mainPanel;
	private JTextField steps;
	private JTextField addedEvent;
	
	public StatusBar(Controller _ctrl) {
		this.ctrl = _ctrl;
		intGUI();
		this.ctrl.addObserver(this);
	}

	private void intGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		mainPanel = new JPanel();
		add(mainPanel, BorderLayout.PAGE_END);
		
		steps = new JTextField();
		this.add(new JLabel(" Time: "));
		this.steps = new JTextField("0", 5);
		this.steps.setBackground(new Color(182, 211, 240));
		this.steps.setHorizontalAlignment(JTextField.RIGHT);
		this.steps.setEditable(false);
		this.add(this.steps);
		this.steps.setVisible(true);
		
		this.addedEvent = new JTextField("Welcone!!!", 78);
		this.addedEvent.setHorizontalAlignment(JTextField.CENTER);
		this.addedEvent.setBackground(new Color(160, 197, 233));
		this.addedEvent.setEditable(false);
		this.add(this.addedEvent);
		this.addedEvent.setVisible(true);
		
		
		this.setVisible(true);
	}
	
	private JPanel createPanel(Color color, int x, int y) {
		JPanel panel;
		panel = new JPanel();
		panel.setBackground(color);
		panel.setAlignmentY(Component.LEFT_ALIGNMENT);
		panel.setPreferredSize(new Dimension(x, y));
		panel.setMaximumSize(new Dimension(x, y));
		panel.setMinimumSize(new Dimension(x, y));
		return panel;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		steps.setText(""+ time);
		addedEvent.setText(" ");
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		steps.setText(""+ time);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		steps.setText(""+ time);
		addedEvent.setText(" Event added (" + e.toString() + ")");
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		steps.setText(""+ 0);
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		if(!events.isEmpty())
		addedEvent.setText(" Event added (" + events.get(events.size()-1).toString() + ")");
	}

	@Override
	public void onError(String err) {
		JOptionPane.showMessageDialog((Frame) SwingUtilities.getWindowAncestor(this), err, "WARNING", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		
		
	}

}
