package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver{
	
	private static final long serialVersionUID = 1L;

	private static final int _JRADIUS = 10;

	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR_SRC = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;

	private RoadMap _map;

	private Image _car, sun, cloud, rain, wind, storm;
	private Image cont_0, cont_1, cont_2, cont_3, cont_4, cont_5;
	
	
	MapByRoadComponent(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
		setPreferredSize(new Dimension (300, 200));
	}
	
	private void initGUI() {
		_car = loadImage("car.png"); 
		sun = loadImage("sun.png");
		cloud = loadImage("cloud.png");
		rain = loadImage("rain.png");
		wind = loadImage("wind.png");
		storm = loadImage("storm.png");
		cont_0 = loadImage("cont_0.png");
		cont_1 = loadImage("cont_1.png");
		cont_2 = loadImage("cont_2.png");
		cont_3 = loadImage("cont_3.png");
		cont_4 = loadImage("cont_4.png");
		cont_5 = loadImage("cont_5.png");
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawRoads(g);
		}
	}

	private void drawRoads(Graphics g) {
		for (int i=0; i<_map.getRoads().size(); i++) {
			int x1 = 50;
			int x2 = getWidth()-100;
			int y = (i + 1) * 50;
			//draw a id of road
			g.setColor(Color.black);
			g.drawString(_map.getRoads().get(i).getId() , 10 , y+5);
			// draw line from (x1,y) to (x2,y)
			g.drawLine(x1, y, x2, y);
			//draw a junction id and circle
			drawJunction(g, x1, x2, y, i);
			//draw the vehicles of road
			drawVehicles(g, x1, x2, y, i);
			//draw weather image and contamination class image
			drawImagen(g, x2, y, i);

		}
	}

	private void drawImagen(Graphics g, int x2, int y, int i) {
		Image weatherIm = null, contIm = null;
		switch(_map.getRoads().get(i).getWeather()) {
		case SUNNY:	 weatherIm = this.sun;
			break;
		case CLOUDY: weatherIm = this.cloud;
			break;
		case RAINY:  weatherIm = this.rain;
			break;
		case WINDY:	 weatherIm = this.wind;
			break;
		case STORM:	 weatherIm = this.storm;
			break;
		}
		g.drawImage(weatherIm, x2+9, y-17, 32, 32, this);
		int A = _map.getRoads().get(i).getTotalCO2();
		int B = _map.getRoads().get(i).getAlarContExcesiva();
		int C = (int) Math.floor(Math.min((double) A/(1.0 + (double) B),1.0) / 0.19);
		
		switch(C) {
		case 0:	 contIm = this.cont_0;
			break;
		case 1:  contIm = this.cont_1;
			break;
		case 2:  contIm = this.cont_2;
			break;
		case 3:	 contIm = this.cont_3;
			break;
		case 4:	 contIm = this.cont_4;
			break;
		case 5:	 contIm = this.cont_5;
			break;
		}
		
		g.drawImage(contIm, x2+43, y-17, 32, 32, this);
		
	}

	private void drawVehicles(Graphics g, int x1, int x2, int y, int i) {
		for (Vehicle v : _map.getVehicles()) {
			if (v.getEstado() != VehicleStatus.ARRIVED) {
				if(v.getCarretera() == _map.getRoads().get(i)) {
					int A = v.getLocalizacion();
					int B = v.getCarretera().getLongitud(),
					x = x1 + (int) ((x2 - x1) * ((double) A / (double) B));
				// draw an image of a car and it identifier
				g.drawImage(_car, x, y, 16, 16, this);
				g.drawString(v.getId(), x, y);
				}
			}
		}
	}

	private void drawJunction(Graphics g, int x1, int x2, int y, int i) {
		// draw circle with center at (x1,y) with radius _JRADIUS
		g.setColor(_JUNCTION_COLOR_SRC);
		g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
		Color circleColor = _RED_LIGHT_COLOR;
		int idx = _map.getRoads().get(i).getCruceDest().getGreenLightIndex();
		if (idx != -1) {
			circleColor = _GREEN_LIGHT_COLOR;
		}
		g.setColor(circleColor);
		g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
		// draw the junction's identifier at (x,y)
		g.setColor(_JUNCTION_LABEL_COLOR);
		g.drawString(_map.getRoads().get(i).getCruceOrigen().getId(), x1-4, y-7);
		g.drawString(_map.getRoads().get(i).getCruceDest().getId(), x2-4, y-7);
}

	// this method is used to update the preffered and actual size of the component,
	// so when we draw outside the visible area the scrollbars show up
	private void updatePrefferedSize() {
		int maxW = 200;
		int maxH = 200;
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		maxW += 20;
		maxH += 20;
		if(maxW > getWidth() || maxH > getHeight()) {
		setPreferredSize(new Dimension(maxW, maxH));
		setSize(new Dimension(maxW, maxH));
		}
	}
	// loads an image from a file
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}

	public void update(RoadMap map) {
		_map = map;
		repaint();
	}
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
		
	}

	@Override
	public void onError(String err) {
		JOptionPane.showMessageDialog((Frame) SwingUtilities.getWindowAncestor(this), err, "WARNING", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		update(map);
		
	}

}
