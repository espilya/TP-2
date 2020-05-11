package simulator.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.exceptions.NonExistingObjectException;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.Factory;
import simulator.factories.MostCrowdedStrategyBuilder;
import simulator.factories.MoveAllStrategyBuilder;
import simulator.factories.MoveFirstStrategyBuilder;
import simulator.factories.NewCityRoadEventBuilder;
import simulator.factories.NewInterCityRoadEventBuilder;
import simulator.factories.NewJunctionEventBuilder;
import simulator.factories.NewVehicleEventBuilder;
import simulator.factories.RoundRobinStrategyBuilder;
import simulator.factories.SetContClassEventBuilder;
import simulator.factories.SetWeatherEventBuilder;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.TrafficSimulator;
import simulator.view.MainWindow;

public class Main {

	private final static Integer _timeLimitDefaultValue = 10;
	private static int _ticks = -1;
	private static int _mode = 1;
	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;

	private static void parseArgs(String[] args) {

		// define the valid command line options
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseTicksOption(line);
			parseModeOption(line);
			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}
		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		// input
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());

		// output
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		// help
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());

		// ticks
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg()
				.desc("Ticks to the simulator’s main loop (default value is " + _timeLimitDefaultValue + ").").build());

		// mode
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg()
				.desc("Application launch mode (default value is GUI).").build());

		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
//		if (_inFile == null) {
//			throw new ParseException("An events file is missing");
//		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		if (!line.hasOption("m"))
			_outFile = line.getOptionValue("o");
	}

	private static void parseTicksOption(CommandLine line) throws ParseException {
		try {
			_ticks = Integer.parseInt(line.getOptionValue("t", _timeLimitDefaultValue.toString()));
		} catch (NumberFormatException e) {
			throw new ParseException("Incorrect ticks value.");
		}
	}

	private static void parseModeOption(CommandLine line) throws ParseException {
		String input;
		if (line.hasOption("m")) {
			input = line.getOptionValue("m");
			if (input.equals("console"))
				_mode = 0;
			else if (!input.equals("gui"))
				throw new ParseException("Incorrect mode value.");
		}
	}

	private static void initFactories() {
		List<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		List<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		List<Builder<Event>> ebs = new ArrayList<>();
		Factory<DequeuingStrategy> dqsFactory = null;
		Factory<LightSwitchingStrategy> lssFactory = null;
		lsbs.add(new MostCrowdedStrategyBuilder());
		lsbs.add(new RoundRobinStrategyBuilder());
		dqbs.add(new MoveFirstStrategyBuilder());
		dqbs.add(new MoveAllStrategyBuilder());
		ebs.add(new NewCityRoadEventBuilder());
		ebs.add(new NewInterCityRoadEventBuilder());
		ebs.add(new NewVehicleEventBuilder());
		ebs.add(new SetWeatherEventBuilder());
		ebs.add(new SetContClassEventBuilder());
		dqsFactory = new BuilderBasedFactory<>(dqbs);
		lssFactory = new BuilderBasedFactory<>(lsbs);
		ebs.add(new NewJunctionEventBuilder(lssFactory, dqsFactory));
		_eventsFactory = new BuilderBasedFactory<>(ebs);
	}

	private static void startBatchMode() throws IOException, NonExistingObjectException {
		System.out.println("-Start");
		Controller control = null;
		TrafficSimulator simulator = new TrafficSimulator();
		InputStream in = new FileInputStream(new File(_inFile));
		OutputStream out = _outFile == null ? System.out : new FileOutputStream(new File(_outFile));
		control = new Controller(simulator, _eventsFactory);
		control.loadEvents(in);
		control.run(Main._ticks, out);
		in.close();
		System.out.println("-End");
	}

	private static void start(String[] args) throws IOException {
		initFactories();
		parseArgs(args);
		try {
			if (_mode == 0)
				startBatchMode();
			else
				startGUIMode();
		} catch (NonExistingObjectException e) {
			e.printStackTrace();
		}
	}

	private static void startGUIMode() throws IOException, NonExistingObjectException {
		TrafficSimulator simulator = new TrafficSimulator();
		InputStream in = null;
		Controller control = new Controller(simulator, _eventsFactory);

		if (_inFile != null) {
			in = new FileInputStream(new File(_inFile));
			control.loadEvents(in);
			in.close();
		}
//		control.run(Main._ticks);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow(control);
			}
		});
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help
	// -m // "-m gui", "-m console", default = gui mode

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
