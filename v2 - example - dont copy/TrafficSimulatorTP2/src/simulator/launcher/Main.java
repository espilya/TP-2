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
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.control.Controller;
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
	private static Integer timeLimit = null;
	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;
	private static String mode;
	private static File fileName = null;
	
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
			parseTimeOption(line);
			parseaOpcionModo(line);

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

	private static void parseaOpcionModo(CommandLine line) {
		if(line.hasOption("m")) {
			switch(line.getOptionValue("m")) {
				case "batch": Main.mode = "BATCH"; break;
				case "BATCH": Main.mode = "BATCH"; break;
				case "gui":   Main.mode = "GUI"; break;
				case "GUI":   Main.mode = "GUI"; break;
			}			
		}
		else Main.mode = "GUI";
	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Pasos que ejecuta el simulador en su bucle principal (el valor por defecto es " + Main._timeLimitDefaultValue + ").")
				.build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Modo").build());
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
		if (_inFile == null && Main.mode == "BATCH") {
			throw new ParseException("An events file is missing");
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}
	private static void parseTimeOption(CommandLine line) throws ParseException {
			String t = line.getOptionValue("t", Main._timeLimitDefaultValue.toString());
			try {
				Main.timeLimit = Integer.parseInt(t);
			} catch (Exception e) {
				throw new ParseException("Valor invalido para el limite de tiempo: " + t);
			}
	}

	private static void initFactories() {

		// TODO complete this method to initialize _eventsFactory
		List<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add(new RoundRobinStrategyBuilder());
		lsbs.add(new MostCrowdedStrategyBuilder());
		Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);
		
		List<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add(new MoveFirstStrategyBuilder());
		dqbs.add(new MoveAllStrategyBuilder());
		Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);
		
		List<Builder<Event>> ebs = new ArrayList<>();
		ebs.add(new NewJunctionEventBuilder(lssFactory,dqsFactory) );
		ebs.add(new NewCityRoadEventBuilder());
		ebs.add(new NewInterCityRoadEventBuilder());
		ebs.add(new NewVehicleEventBuilder());
		ebs.add(new SetWeatherEventBuilder());
		ebs.add(new SetContClassEventBuilder());
		
		_eventsFactory = new BuilderBasedFactory<>(ebs);

	}
	private static String checkSemanticEquality(JSONObject jo1, JSONObject jo2) {
		return jo1.similar(jo2) ? "Yes" : "No";
	}
	private static void startBatchMode() throws IOException {
		InputStream in = new FileInputStream(new File(_inFile));
		OutputStream out =_outFile == null ? System.out : new FileOutputStream(new File(_outFile));
		TrafficSimulator simulator = new TrafficSimulator();
		Controller control = new Controller(simulator, _eventsFactory);
		control.loadEvents(in);
		//System.out.println(Main.timeLimit); "resources/tmp/ex3.out.json"
		control.run(Main.timeLimit, out);
		in.close();
		compararSiSonIguales();
		System.out.println("Fin del programa");
	}
	private static void startGUIMode() throws IOException {
		TrafficSimulator simulator = new TrafficSimulator();
		Controller control = new Controller(simulator, _eventsFactory);
		if(_inFile != null) {
		fileName = new File(_inFile);	
		InputStream in = new FileInputStream(fileName);
		control.loadEvents(in);
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow(control, fileName);
			}
		});

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
	

	System.out
			.println("Are ex1.expout.json and ex1.out.json equal? " + checkSemanticEquality(joFromFile1, joFromFile11));
	System.out
	    	.println("Are ex2.expout.json and ex2.out.json equal? " + checkSemanticEquality(joFromFile2, joFromFile22));
	System.out
			.println("Are ex3.expout.json and ex3.out.json equal? " + checkSemanticEquality(joFromFile3, joFromFile33));
		
	}

	private static void start(String[] args) throws IOException {
		initFactories();
		parseArgs(args);
		if(Main.mode == "BATCH") startBatchMode();
		else if(Main.mode == "GUI") startGUIMode();
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/examples/ex1.out.json
	// --help

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
