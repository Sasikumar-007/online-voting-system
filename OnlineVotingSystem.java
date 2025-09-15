import java.io.*;
import java.util.*;

/**
 * Simple console-based "online" voting system (single-machine simulation).
 * Candidates are listed with a SYMBOL and users vote by typing that symbol.
 * Votes persist to a local text file so the program can be restarted.
 *
 * Compile: javac OnlineVotingSystem.java
 * Run:     java OnlineVotingSystem
 *
 * Notes:
 * - This is a simple educational example. For a real online system you'd need
 *   authentication, secure transport (HTTPS), a backend DB, and protections
 *   against double-voting and tampering.
 */
public class OnlineVotingSystem {

    static final String DATA_FILE = "votes.txt"; // simple persistence

    static class Candidate {
        String id;       // internal id
        String name;
        String symbol;   // unique symbol user types to vote, e.g. "★"
        int votes;

        Candidate(String id, String name, String symbol) {
            this.id = id;
            this.name = name;
            this.symbol = symbol;
            this.votes = 0;
        }

        @Override
        public String toString() {
            return String.format("%s  %s  (symbol: %s)  votes: %d", id, name, symbol, votes);
        }
    }

    Map<String, Candidate> candidates = new LinkedHashMap<>(); // keep insertion order

    public OnlineVotingSystem() {
        // initialize some candidates with symbols. You can add/change these.
        addCandidate(new Candidate("C1", "Alice Johnson", "A"));
        addCandidate(new Candidate("C2", "Bob Singh", "B"));
        addCandidate(new Candidate("C3", "Catherine Rao", "C"));
        addCandidate(new Candidate("C4", "Deepak Patel", "D"));

        // load persisted votes if available
        loadVotes();
    }

    private void addCandidate(Candidate c) {
        candidates.put(c.symbol, c);
    }

    private void loadVotes() {
        File f = new File(DATA_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                // each line: symbol=votes
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("=");
                if (parts.length != 2) continue;
                String sym = parts[0];
                int v = Integer.parseInt(parts[1]);
                Candidate c = candidates.get(sym);
                if (c != null) c.votes = v;
            }
        } catch (Exception e) {
            System.err.println("Failed to load votes: " + e.getMessage());
        }
    }

    private void saveVotes() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Candidate c : candidates.values()) {
                pw.println(c.symbol + "=" + c.votes);
            }
        } catch (IOException e) {
            System.err.println("Failed to save votes: " + e.getMessage());
        }
    }

    private void showMenu() {
        System.out.println("\n===== ONLINE VOTING SYSTEM =====");
        System.out.println("Candidates (vote by entering SYMBOL):");
        for (Candidate c : candidates.values()) {
            System.out.printf("  %s  - %s  (symbol: %s)\n", c.id, c.name, c.symbol);
        }
        System.out.println("\nCommands:");
        System.out.println("  vote <symbol>   - cast a vote for candidate with that symbol");
        System.out.println("  results         - show current vote counts");
        System.out.println("  help            - show this menu");
        System.out.println("  exit            - save and quit");
        System.out.println("\nExample: vote A\n");
    }

    private void castVote(String symbol) {
        Candidate c = candidates.get(symbol);
        if (c == null) {
            System.out.println("No candidate with symbol '" + symbol + "'. Try again.");
            return;
        }
        c.votes++;
        saveVotes();
        System.out.println("Thanks — your vote for " + c.name + " (" + c.symbol + ") has been recorded.");
    }

    private void showResults() {
        System.out.println("\n--- Current Results ---");
        for (Candidate c : candidates.values()) {
            System.out.printf("%s (%s) : %d\n", c.name, c.symbol, c.votes);
        }
        System.out.println("------------------------\n");
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        showMenu();
        while (true) {
            System.out.print("> ");
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split("\\s+");
            String cmd = parts[0].toLowerCase();
            if (cmd.equals("vote")) {
                if (parts.length < 2) {
                    System.out.println("Usage: vote <symbol>");
                    continue;
                }
                String symbol = parts[1];
                castVote(symbol);
            } else if (cmd.equals("results")) {
                showResults();
            } else if (cmd.equals("help")) {
                showMenu();
            } else if (cmd.equals("exit") || cmd.equals("quit")) {
                System.out.println("Saving votes and exiting...");
                saveVotes();
                break;
            } else {
                System.out.println("Unknown command. Type 'help' to see available commands.");
            }
        }
        sc.close();
    }

    public static void main(String[] args) {
        OnlineVotingSystem app = new OnlineVotingSystem();
        app.run();
    }
}
