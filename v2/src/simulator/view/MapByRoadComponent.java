package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {
	private static final long serialVersionUID = 1L;

	private static final int _JRADIUS = 10;

	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);

	private RoadMap _map;

	private Image _car, _cont0, _cont1, _cont2, _cont3, _cont4, _cont5, _wRain, _wSun, _wStorm, _wWind, _wCloud;

	MapByRoadComponent(Controller ctrl) {
		initGUI();
		setPreferredSize(new Dimension (300, 200));
		ctrl.addObserver(this);
	}

	private void initGUI() {
		_car = loadImage("car.png");
		_cont0 = loadImage("cont_0.png");
		_cont1 = loadImage("cont_1.png");
		_cont2 = loadImage("cont_2.png");
		_cont3 = loadImage("cont_3.png");
		_cont4 = loadImage("cont_4.png");
		_cont5 = loadImage("cont_5.png");
		_wRain = loadImage("rain.png");
		_wSun = loadImage("sun.png");
		_wStorm = loadImage("storm.png");
		_wWind = loadImage("wind.png");
		_wCloud = loadImage("cloud.png");
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
			drawMap(g);
		}
	}

	private void drawMap(Graphics g) {
		drawRoads(g);
		
		drawJunctions(g);
	}

	private void drawRoads(Graphics g) {
		int i = 0;
		for (Road r : _map.getRoads()) {

			// the road goes from (x1,y1) to (x2,y2)
			int x1 = 50;
			int x2 = getWidth() - 100;
			int y = (i + 1) * 50;


			// choose a color for the road depending on the total contamination, the darker
			// the
			// more contaminated (wrt its co2 limit)
			int roadColorValue = 200
					- (int) (200.0 * Math.min(1.0, (double) r.getContTotal() / (1.0 + (double) r.getContLimit())));
			Color roadColor = new Color(roadColorValue, roadColorValue, roadColorValue);

			drawLine(g, x1, y, x2, y, 15, 5, roadColor);
			g.setColor(Color.BLACK);
			g.drawString(r.getId(), x1 - 30, y + 2);

			drawIcons(g, r, x1, x2, y);
			drawVehicles(g, r, y);
			i++;
		}

	}

	private void drawVehicles(Graphics g, Road r, int y) {
		for (Vehicle v : r.getVehicles()) {
			if (v.getStatus() != VehicleStatus.ARRIVED) {

				// The calculation below compute the coordinate (vX,vY) of the vehicle on the
				// corresponding road. It is calculated relativly to the length of the road, and
				// the location on the vehicle.
				// x = x1 + ( int ) ((x2 - x1) * (( double ) A / ( double ) B))
				int x1 = 50;
				int x2 = getWidth() - 100;
				int x = x1 + (int) ((x2 - x1) * ((double) v.getLocation() / (double) r.getLength()));

				// Choose a color for the vehcile's label and background, depending on its
				// contamination class
				int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
				g.setColor(new Color(0, vLabelColor, 0));

				// draw an image of a car and it identifier
				g.drawImage(_car, x - 4, y- 10, 16, 16, this);
				g.drawString(v.getId(), x, y - 6);


			}
		}
	}

	private void drawJunctions(Graphics g) {
		int i = 0;
		for (Road r : _map.getRoads()) {
			Junction src = r.getSrcJunc();
			Junction dst = r.getDestJunc();
			int x1 = 50;
			int x2 = getWidth() - 100;
			int y = (i + 1) * 50;

			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(src.getId(), x1, y - 10);

			g.setColor((dst.getInRoads().get(dst.getGreenLightIndex()).equals(r) ? Color.GREEN : Color.RED));
			g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(dst.getId(), x2, y- 10);
			i++;
		}
	}

	private void drawIcons(Graphics g, Road r, int x1, int x2, int y) {
		Image cont = null;
		int c = (int) Math.floor(Math.min((double) r.getContTotal() / (1.0 + (double) r.getContLimit()), 1.0) / 0.19);
		switch (c) {
		case 0:
			cont = _cont0;
			break;
		case 1:
			cont = _cont1;
			break;
		case 2:
			cont = _cont2;
			break;
		case 3:
			cont = _cont3;
			break;
		case 4:
			cont = _cont4;
			break;
		case 5:
			cont = _cont5;
			break;
		}
		g.drawImage(cont, x2 + 60, y - 17, 32, 32, this);
		switch (r.getWeather().ordinal()) { // {SUNNY, CLOUDY, RAINY, WINDY, STORM}
		case 0:
			cont = _wSun;
			break;
		case 1:
			cont = _wCloud;
			break;
		case 2:
			cont = _wRain;
			break;
		case 3:
			cont = _wWind;
		case 4:
			cont = _wStorm;
			break;
		}
		g.drawImage(cont, x2 + 20, y - 17, 32, 32, this);

	}

	private void drawLine(Graphics g, int x1, int y1, int x2, int y2, int w, int h, Color lineColor) {
		g.setColor(lineColor);
		g.drawLine(x1, y1, x2, y2);
	}

	// this method is used to update the preffered and actual size of the component,
	// so when we draw outside the visible area the scrollbars show up
	private void updatePrefferedSize() {
		int maxW = 300;
		int maxH = 200;
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		maxW += 20;
		maxH += 20;
		setPreferredSize(new Dimension(maxW, maxH));
		setSize(new Dimension(maxW, maxH));
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
	}

	@Override
	public void onLoad(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUndo(RoadMap map, List<Event> events, int time) {
		update(map);
		
	}

}
