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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import exceptions.ValueParseException;
import simulator.control.Controller;
import simulator.factories.*;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.TrafficSimulator;

public class Main {

	private final static Integer _timeLimitDefaultValue = 10;
	private static int ticks = -1;
	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseTicksOption(line);
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

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg()
				.desc("Ticks to the simulator’s main loop (default value is " 
		+ _timeLimitDefaultValue + ").").build());

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
		if (_inFile == null) {
			throw new ParseException("An events file is missing");
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}

	private static void parseTicksOption(CommandLine line) throws ParseException {
		ticks = Integer.parseInt(line.getOptionValue("t", _timeLimitDefaultValue.toString()));
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

		dqsFactory = new BuilderBasedFactory<>(dqbs);
		lssFactory = new BuilderBasedFactory<>(lsbs);

		ebs.add(new NewCityRoadEventBuilder());
		ebs.add(new NewInterCityRoadEventBuilder());
		ebs.add(new NewVehicleEventBuilder());
		ebs.add(new SetWeatherEventBuilder());
		ebs.add(new SetContClassEventBuilder());

		ebs.add(new NewJunctionEventBuilder(lssFactory, dqsFactory));

		_eventsFactory = new BuilderBasedFactory<>(ebs);
	}

	private static void startBatchMode() throws IOException, ValueParseException {
		InputStream in = new FileInputStream(new File(_inFile));
		OutputStream out = _outFile == null ? System.out : new FileOutputStream(new File(_outFile));
		TrafficSimulator simulator = new TrafficSimulator();
		Controller control = new Controller(simulator, _eventsFactory);
		control.loadEvents(in);
		// System.out.println(Main.timeLimit); "resources/tmp/ex3.out.json"
		control.run(Main.ticks, out);
		in.close();
		compararSiSonIguales();
		System.out.println("Fin del programa");
	} 

	private static void compararSiSonIguales() throws JSONException, FileNotFoundException {
		JSONObject joFromFile1 = new JSONObject(
				new JSONTokener(new FileInputStream(new File("resources/examples/ex1.expout.json"))));
		JSONObject joFromFile11 = new JSONObject(
				new JSONTokener(new FileInputStream(new File("resources/examples/ex1.out.json"))));
		JSONObject joFromFile2 = new JSONObject(
				new JSONTokener(new FileInputStream(new File("resources/examples/ex2.expout.json"))));
		JSONObject joFromFile22 = new JSONObject(
				new JSONTokener(new FileInputStream(new File("resources/examples/ex2.out.json"))));
		JSONObject joFromFile3 = new JSONObject(
				new JSONTokener(new FileInputStream(new File("resources/examples/ex3.expout.json"))));
		JSONObject joFromFile33 = new JSONObject(
				new JSONTokener(new FileInputStream(new File("resources/examples/ex3.out.json"))));
		System.out.println("Compare JSON structures");

		System.out.println(
				"Are ex1.expout.json and ex1.out.json equal? " + checkSemanticEquality(joFromFile1, joFromFile11));
		System.out.println(
				"Are ex2.expout.json and ex2.out.json equal? " + checkSemanticEquality(joFromFile2, joFromFile22));
		System.out.println(
				"Are ex3.expout.json and ex3.out.json equal? " + checkSemanticEquality(joFromFile3, joFromFile33));

	}

	private static String checkSemanticEquality(JSONObject jo1, JSONObject jo2) {
		return jo1.similar(jo2) ? "Yes" : "No";
	}

	private static void start(String[] args) throws IOException, ValueParseException {
		initFactories();
		parseArgs(args);
		startBatchMode();
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
