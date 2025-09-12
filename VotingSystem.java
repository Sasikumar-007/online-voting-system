import java.util.*;

// Candidate class
class Candidate {
    int id;
    String name;
    int votes = 0;

    Candidate(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

// Main Voting System class
public class VotingSystem {
    private static Map<Integer, Candidate> candidates = new HashMap<>();
    private static Set<String> votedVoters = new HashSet<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Step 1: Add candidates
        candidates.put(1, new Candidate(1, "Alice"));
        candidates.put(2, new Candidate(2, "Bob"));
        candidates.put(3, new Candidate(3, "Charlie"));

        System.out.println("=== Online Voting System ===");
        int choice;
        do {
            System.out.println("\n1. Vote");
            System.out.println("2. Show Results");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            
            while (!sc.hasNextInt()) { // Validate numeric input
                System.out.println("Invalid input! Please enter a number (1-3).");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> vote();
                case 2 -> showResults();
                case 3 -> System.out.println("Exiting... Thank you!");
                default -> System.out.println("Invalid choice, try again.");
            }
        } while (choice != 3);
    }

    // Voting process
    private static void vote() {
        System.out.print("Enter your Voter ID (unique): ");
        String voterId = sc.nextLine().trim();

        if (voterId.isEmpty()) {
            System.out.println("❌ Voter ID cannot be empty!");
            return;
        }

        if (votedVoters.contains(voterId)) {
            System.out.println("❌ You have already voted!");
            return;
        }

        System.out.println("\nCandidates:");
        for (Candidate c : candidates.values()) {
            System.out.println(c.id + ". " + c.name);
        }

        System.out.print("Enter candidate ID to vote: ");
        while (!sc.hasNextInt()) { // Validate numeric input
            System.out.println("Invalid input! Please enter a valid candidate ID.");
            sc.next();
        }
        int candidateId = sc.nextInt();
        sc.nextLine();

        Candidate selected = candidates.get(candidateId);
        if (selected != null) {
            selected.votes++;
            votedVoters.add(voterId);
            System.out.println("✅ Vote cast successfully for " + selected.name);
        } else {
            System.out.println("❌ Invalid candidate ID!");
        }
    }

    // Show election results
    private static void showResults() {
        System.out.println("\n=== Voting Results ===");
        for (Candidate c : candidates.values()) {
            System.out.println(c.name + " → " + c.votes + " votes");
        }

        // Find winner
        Candidate winner = null;
        for (Candidate c : candidates.values()) {
            if (winner == null || c.votes > winner.votes) {
                winner = c;
            }
        }

        if (winner != null) {
            System.out.println("\n🏆 Current Leading Candidate: " + winner.name + " with " + winner.votes + " votes.");
        }
    }
}
