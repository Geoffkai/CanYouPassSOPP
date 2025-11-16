package src;

import src.gui.GUIMain;
import src.logic.TerminalGame;

/**
 * Unified entry point for the Programming Paradigms Quiz Game.
 * 
 * Usage:
 *   java src.Main              - Launches GUI mode (default)
 *   java src.Main --gui        - Launches GUI mode
 *   java src.Main --terminal   - Launches terminal/console mode
 *   java src.Main -g           - Launches GUI mode (short)
 *   java src.Main -t           - Launches terminal mode (short)
 */
public class Main {
    public static void main(String[] args) {
        // Default to GUI mode if no arguments provided
        String mode = "gui";
        
        // Check command-line arguments
        if (args.length > 0) {
            String arg = args[0].toLowerCase();
            if (arg.equals("--terminal") || arg.equals("-t") || arg.equals("terminal")) {
                mode = "terminal";
            } else if (arg.equals("--gui") || arg.equals("-g") || arg.equals("gui")) {
                mode = "gui";
            } else {
                System.out.println("Unknown argument: " + args[0]);
                printUsage();
                return;
            }
        }
        
        // Launch the appropriate mode
        if (mode.equals("terminal")) {
            TerminalGame.main(args);
        } else {
            GUIMain.main(args);
        }
    }
    
    private static void printUsage() {
        System.out.println("\nUsage:");
        System.out.println("  java src.Main              - Launch GUI mode (default)");
        System.out.println("  java src.Main --gui        - Launch GUI mode");
        System.out.println("  java src.Main --terminal   - Launch terminal mode");
        System.out.println("  java src.Main -g           - Launch GUI mode (short)");
        System.out.println("  java src.Main -t           - Launch terminal mode (short)");
    }
}

