package gendev.lab2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

import tau.smlab.syntech.bddgenerator.BDDGenerator;
import tau.smlab.syntech.bddgenerator.BDDGenerator.TraceInfo;
import tau.smlab.syntech.gameinput.model.GameInput;
import tau.smlab.syntech.gameinputtrans.TranslationException;
import tau.smlab.syntech.gameinputtrans.TranslationProvider;
import tau.smlab.syntech.gameinputtrans.translator.DefaultTranslators;
import tau.smlab.syntech.gameinputtrans.translator.Translator;
import tau.smlab.syntech.gamemodel.GameModel;
import tau.smlab.syntech.gamemodel.PlayerModule.TransFuncType;
import tau.smlab.syntech.games.gr1.GR1Game;
import tau.smlab.syntech.games.gr1.GR1GameExperiments;
import tau.smlab.syntech.games.gr1.GR1GameImplC;
import tau.smlab.syntech.games.gr1.GR1GameMemoryless;
import tau.smlab.syntech.games.gr1.wellseparation.WellSeparationChecker;
import tau.smlab.syntech.games.gr1.wellseparation.WellSeparationChecker.Systems;
import tau.smlab.syntech.jtlv.BDDPackage;
import tau.smlab.syntech.spectragameinput.ErrorsInSpectraException;
import tau.smlab.syntech.spectragameinput.SpectraInputProviderNoIDE;
import tau.smlab.syntech.spectragameinput.SpectraTranslationException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class T1YourOwnReactiveSystemTest {
	static final String specFile = "Spec.spectra";
	static final String specFileUnreal = "Spec_Unreal.spectra";

	@Test
	@Order(1)
	void testModelExists() {
		File f = new File(specFile);
		assertTrue(f.exists(), "Make sure that the original file " + specFile + " has not been deleted or renamed.");
	}

	@Test
	@Order(2)
	void testUnrealizableVersionExists() {
		File f = new File(specFileUnreal);
		assertTrue(f.exists(), "Make sure that the file " + specFileUnreal + " has been created.");
	}

	
	@Test
	@Order(3)
	void testUnrealizableVersionUnrealizable() throws SpectraTranslationException {
		GameModel model = getModel(specFileUnreal);
		    GR1Game gr1 = getGR1Game(model, BDDPackage.getCurrPackage(), true);
    boolean realizable = gr1.checkRealizability();
		assertFalse(realizable, "The specification in " + specFileUnreal + " should be unrealizable, but it is realizable.");
	}
	
	@Test
	@Order(4)
	void testVarsEnv() throws ErrorsInSpectraException, SpectraTranslationException {
		// get the Xtext-based input parser
		SpectraInputProviderNoIDE sip = new SpectraInputProviderNoIDE();
		// parse (via Xtext) and translate to abstract syntax (Xtext independent)
		GameInput gi = sip.getGameInput(specFile);
		assertTrue(gi.getEnv().getVars().size() >= 3, "The specification must contain at least 3 environment variables.");
	}

	@Test
	@Order(5)
	void testVarsSys() throws ErrorsInSpectraException, SpectraTranslationException {
		// get the Xtext-based input parser
		SpectraInputProviderNoIDE sip = new SpectraInputProviderNoIDE();
		// parse (via Xtext) and translate to abstract syntax (Xtext independent)
		GameInput gi = sip.getGameInput(specFile);
		assertTrue(gi.getSys().getVars().size() >= 3, "The specification must contain at least 3 system variables.");
	}

	@Test
	@Order(6)
	void testGars() throws ErrorsInSpectraException, SpectraTranslationException {
		// get the Xtext-based input parser
		SpectraInputProviderNoIDE sip = new SpectraInputProviderNoIDE();
		// parse (via Xtext) and translate to abstract syntax (Xtext independent)
		GameInput gi = sip.getGameInput(specFile);
		assertTrue(gi.getSys().getConstraints().size() >= 2, "The specification must contain at least 2 guarantees.");
	}

	@Test
	@Order(7)
	void testAsms() throws ErrorsInSpectraException, SpectraTranslationException {
		// get the Xtext-based input parser
		SpectraInputProviderNoIDE sip = new SpectraInputProviderNoIDE();
		// parse (via Xtext) and translate to abstract syntax (Xtext independent)
		GameInput gi = sip.getGameInput(specFile);
		assertTrue(gi.getEnv().getConstraints().size() >= 2, "The specification must contain at least 2 assumptions.");
	}

		
	@Test
	@Order(8)
	void testRealizable() throws SpectraTranslationException {
		GameModel model = getModel(specFile);
		    GR1Game gr1 = getGR1Game(model, BDDPackage.getCurrPackage(), true);
    boolean realizable = gr1.checkRealizability();
		assertTrue(realizable, "The specification in " + specFile + " should be realizable, but it is unrealizable.");
	}

	@Test
	@Order(9)
	void testWellSeparated() throws ErrorsInSpectraException, SpectraTranslationException {
		GameModel model = getModel(specFile);
		
		WellSeparationChecker checker = new WellSeparationChecker();
    
    List<String> res = null;
    try {
      res = checker.diagnose(model, Systems.SPEC);
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
      return;
    }
		assertNotNull(res, "The result of the well-separation check should not be null.");
		assertTrue(res.isEmpty(), "The specification should be well-separated, but the result indicates otherwise.");
	}

	private GameModel getModel(String file) throws SpectraTranslationException {
		// get the Xtext-based input parser
		SpectraInputProviderNoIDE sip = new SpectraInputProviderNoIDE();
		// parse (via Xtext) and translate to abstract syntax (Xtext independent)
		GameInput gi = sip.getGameInput(file);

		try {
			List<Translator> transList = DefaultTranslators.getDefaultTranslators();
			TranslationProvider.translate(gi, transList);
		} catch (TranslationException e) {
			System.out.println("Error: Could not execute translators on Spectra file.");
			throw e;
		}

		
		
    GameModel model = BDDGenerator.generateGameModel(gi, TraceInfo.ALL, false, TransFuncType.SINGLE_FUNC, true);
		return model;
	}

	private GR1Game getGR1Game(GameModel gameModel, BDDPackage pkg, boolean optimize) {
		
		GR1Game gr1;
		
		if (BDDPackage.CUDD.equals(pkg)) {
			gr1 = new GR1GameImplC(gameModel);
		} else if (optimize) {
			gr1 = new GR1GameExperiments(gameModel);
		} else {
			gr1 = new GR1GameMemoryless(gameModel);
		}
		
		return gr1;
  }
}
