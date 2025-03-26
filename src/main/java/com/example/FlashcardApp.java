package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;                   // Шууд ажиллуулах: mvn clean compile exec:java
                                      // flashcard <cards-file> [options] гэсэн утгаа pom.xml дээр өөрчил
                                      // оруулах утга жишээ: cards.txt --repetitions 2 --order random --invertCards

public class FlashcardApp {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        // if (args.length == 0 || args[0].equals("--help")) {
        //     printHelp();
        //     return;
        // }

        System.out.println("flashcard ");
        String command = scanner.nextLine(); 

        String[] commands = command.split("\\s+");

        if (commands.length == 0) {
            printHelp();
            scanner.close();
            return;
        }

        // String cardsFile = args[0];
        String cardsFile = commands[0];
        String order = "random";
        int repetitions = 1;
        boolean invertCards = false;

        
        try {
            for (int i = 1; i < commands.length; i++) {
                switch (commands[i]) {
                    case "--order":
                        if (i + 1 >= commands.length) {
                            System.out.println("--order requires an argument");
                            printHelp();
                            return;
                        }
                        order = commands[++i];
                        break;
                    case "--repetitions":
                        if (i + 1 >= commands.length) {
                            System.out.println("--repetitions requires an argument");
                            printHelp();
                            return;
                        }
                        repetitions = Integer.parseInt(commands[++i]);
                        break;
                    case "--invertCards":
                        invertCards = true;
                        break;
                    case "--help":
                        printHelp();
                        return;
                    default:
                        System.out.println("Wrong argument inserted! " + commands[i]);
                        printHelp();
                        return;
                }
            }
    
            List<Flashcard> cards = loadCards(cardsFile, repetitions);
            CardOrganizer organizer = getOrganizer(order);
    
            if (organizer == null) {
                System.out.println("Invalid organizer type: " + order);
                scanner.close();
                return;
            }
    
            FlashcardSession session = new FlashcardSession(cards, organizer, repetitions, invertCards);
            session.start();
        } catch (NumberFormatException e) {
            System.out.println("Wrong number format for repetitions");
            printHelp();
        }

        // for (int i = 1; i < args.length; i++) {
        //     switch (args[i]) {
        //         case "--order":
        //             order = args[++i];
        //             break;
        //         case "--repetitions":
        //             repetitions = Integer.parseInt(args[++i]);
        //             break;
        //         case "--invertCards":
        //             invertCards = true;
        //             break;
        //         default:
        //             System.out.println("Wrong argument inserted! " + args[i]);
        //             printHelp();
        //             return;
        //     }
        // }

        // List<Flashcard> cards = loadCards(cardsFile, repetitions);
        // CardOrganizer organizer = getOrganizer(order);

        // if (organizer == null) {
        //     System.out.println("        Wrong organize type!: " + order);
        //     return;
        // }

        // FlashcardSession session = new FlashcardSession(cards, organizer, repetitions, invertCards);
        // session.start();

        scanner.close();
    }

    private static void printHelp() {
        System.out.println("Help:");
        System.out.println("flashcard <cards-file> [options]");
        System.out.println("Options:");
        System.out.println(" --help Show help information");
        System.out.println(" --order <order> Organize type, default: 'random'");
        System.out.println(" [choice: 'random', 'worst-first', 'recent-mistakes-first']");
        System.out.println(" --repetitions <num> Set the number of times a card must be answered correctly.");
        System.out.println(" --invertCards Card's questions and answers will be switched.");
    }

    private static List<Flashcard> loadCards(String filename, int repetitions) {
        List<Flashcard> cards = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                	for (int i = 0; i < repetitions; i++) {
                        cards.add(new Flashcard(parts[0].trim(), parts[1].trim()));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error to load file: " + e.getMessage());
        }
        return cards;
    }

    private static CardOrganizer getOrganizer(String order) {
        switch (order) {
            case "random":
                return cards -> {
                    Collections.shuffle(cards);
                    return cards;
                };
            case "worst-first":
                return cards -> {
                    cards.sort((c1, c2) -> Boolean.compare(c1.wasCorrect(), c2.wasCorrect()));
                    return cards;
                };
            case "recent-mistakes-first":
                return new RecentMistakesFirstSorter();
            default:
                return null;
        }
    }
}
