package gendev.lab2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import tau.smlab.syntech.controller.executor.ControllerExecutor;
import tau.smlab.syntech.games.controller.jits.BasicJitController;

public class SpecSimulatorCmd {

    public static void main(String[] args) throws IOException {
        Map<String, String> inputs = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        // Instantiate a new controller executor
        ControllerExecutor executor = new ControllerExecutor(new BasicJitController(), "out/jit", "Spec");

        boolean iniState = true;

        System.out.println("=*=*= Greenhouse Controller Simulation =*=*=");
        System.out.println("Available environment inputs: tempHigh, soilDry, lightLow");
        System.out.println("Enter true/false for each input (or just press Enter for false)");

        while (true) {
            inputs.clear();

            //READ INPUTS
            System.out.print("\nIs temperature high? (tempHigh) [true/false]: ");
            String tempHigh = scanner.nextLine().trim();
            if (tempHigh.equalsIgnoreCase("true")) inputs.put("tempHigh", "true");

            System.out.print("Is soil dry? (soilDry) [true/false]: ");
            String soilDry = scanner.nextLine().trim();
            if (soilDry.equalsIgnoreCase("true")) inputs.put("soilDry", "true");

            System.out.print("Is light low? (lightLow) [true/false]: ");
            String lightLow = scanner.nextLine().trim();
            if (lightLow.equalsIgnoreCase("true")) inputs.put("lightLow", "true");

            //SEND INPUTS TO CONTROLLER
            if (iniState) {
                executor.initState(inputs);
                iniState = false;
            } else {
                executor.updateState(inputs);
            }

            //READ OUTPUTS
            Map<String, String> outputs = executor.getCurrOutputs();

            //PRINT SYSTEM OUTPUTS
            System.out.println("\n--- System Actions ---");
            System.out.println("Fan On:         " + outputs.getOrDefault("fanOn", "false"));
            System.out.println("Water Pump On:  " + outputs.getOrDefault("waterPumpOn", "false"));
            System.out.println("Grow Light On:  " + outputs.getOrDefault("growLightOn", "false"));
        }
    }
}
