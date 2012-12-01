package edu.brunel.freerun;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;


/**
 * A simple HelloWorld demo showing a simple speech application built using Sphinx-4. This application uses the Sphinx-4
 * end-pointer, which automatically segments incoming audio into utterances and silences.
 */
public class SphinxInput {
 
    public SphinxInput() 
    {
      	ConfigurationManager cm;
      	cm = new ConfigurationManager(SphinxInput.class.getResource("helloworld.config.xml"));
        
 
        Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
        recognizer.allocate();
 
        // start the microphone or exit if the program if this is not possible
        Microphone microphone = (Microphone) cm.lookup("microphone");
        if (!microphone.startRecording()) {
            System.out.println("Cannot start microphone.");
            recognizer.deallocate();
            System.exit(1);
        }
 
        System.out.println("Say: (Good morning | Hello | Fuck off) ( Bhiksha | Evandro | Paul | Philip | Rita | Will | Asuna )");
 
        // loop the recognition until the program exits.
        
        while (true) 
        {
            System.out.println("Start speaking. Press Ctrl-C to quit.\n");
 
            Result result = recognizer.recognize();
 
            if (result != null) 
            {
                String resultText = result.getBestFinalResultNoFiller();
                
                //List<Token> resultTokens = result.getActiveTokens().getTokens();
                //String resultTokenString = resultTokens.get(0).getWordPath();
                //for (int i = 1; i < resultTokens.size(); i++)
                //{
                //	resultTokenString += " " + resultTokens.get(i).getWordPath();
                //}
                
                System.out.println("You said: " + resultText + '\n');
                
                if (resultText.equals("time for bed asuna"))
                {
                	break;
                }
            } 
            else 
            {
                System.out.println("I can't hear what you said.\n");
            }
        }
        System.out.println("Program End...");
        System.exit(0);
    }
}