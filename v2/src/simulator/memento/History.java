package simulator.memento;

import java.util.ArrayList;
import java.util.List;

public class History {
	private List<Pair> history = new ArrayList<Pair>();
	private int virtualSize = 0;

	private class Pair {
		int time;
		Memento memento;

		Pair(int c, Memento m) {
			time = c;
			memento = m;
		}

		private int getEvent() {
			return time;
		}

		private Memento getMemento() {
			return memento;
		}
	}

	public void push(int c, Memento m) {
		if (virtualSize != history.size() && virtualSize > 0) {
			history = history.subList(0, virtualSize - 1);
		}
		history.add(new Pair(c, m));
		virtualSize = history.size();
	}

	public boolean undo() {
		Pair pair = getUndo();
		if (pair == null) {
			return false;
		}
		System.out.println("Undoing: " + pair.getEvent());
		pair.getMemento().restore();
		return true;
	}

//	public boolean redo() {
//		Pair pair = getRedo();
//		if (pair == null) {
//			return false;
//		}
//		System.out.println("Redoing: " + pair.getEvent());
//		pair.getMemento().restore();
//		pair.getEvent().execute();
//		return true;
//	}

	private Pair getUndo() {
		if (virtualSize == 0) {
			return null;
		}
		virtualSize = Math.max(0, virtualSize - 1);
		return history.get(virtualSize);
	}

//	private Pair getRedo() {
//		if (virtualSize == history.size()) {
//			return null;
//		}
//		virtualSize = Math.min(history.size(), virtualSize + 1);
//		return history.get(virtualSize - 1);
//	}
}