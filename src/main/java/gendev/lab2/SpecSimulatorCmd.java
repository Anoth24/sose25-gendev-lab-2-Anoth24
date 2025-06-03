package gendev.lab2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import tau.smlab.syntech.controller.executor.ControllerExecutor;
import tau.smlab.syntech.games.controller.jits.BasicJitController;


public class SpecSimulatorCmd {

	public static void main(String[] args) throws IOException {

		Map<String, String> inputs = new HashMap<>();

		// Instantiate a new controller executor
		ControllerExecutor executor = new ControllerExecutor(new BasicJitController(), "out/jit", "Spec");

		boolean iniState = true;
		while (true) {
			inputs.clear();

			// TODO read inputs here

			// TODO send inputs to controller

			// execute controller
			if (iniState) {
				executor.initState(inputs);
				iniState = false;
			} else {
				executor.updateState(inputs);
			}

			// TODO read outputs and decide how to proceed
		}
	}
}
