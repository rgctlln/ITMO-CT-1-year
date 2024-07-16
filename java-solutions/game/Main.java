package game;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    private static int getValidParameter(Scanner scanner, String parameterName) {
        int parameter = -1;
        while (parameter <= 0) {
            System.out.println("Enter " + parameterName.toUpperCase() + ": ");
            String input = scanner.nextLine();
            Scanner lineScanner = new Scanner(input);
            int countNumbers = 0;
            while (lineScanner.hasNext()) {
                if (lineScanner.hasNextInt()) {
                    parameter = lineScanner.nextInt();
                    countNumbers++;
                } else {
                    System.out.println("Invalid input. Please enter a valid integer for " + parameterName.toUpperCase() + ".");
                    parameter = -1;
                    break;
                }
                if (lineScanner.hasNext()) {
                    System.out.println("You've entered more than one number. Try again");
                    parameter = -1;
                    break;
                }
            }
            lineScanner.close();
            if (countNumbers > 1) {
                parameter = -1;
            } else if (parameter <= 0 && countNumbers == 1) {
                System.out.println("Invalid input. Try again.");
            }
        }
        return parameter;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("If you want to play a regular game, write 0.");
                System.out.println("If you want to play Olympic games, write 1.");
                if (!scanner.hasNext()) {
                    System.out.println("The input is closed.");
                    break;
                }
                String line = scanner.next();
                if (line.isBlank()) {
                    continue;
                }
                try {
                    Scanner in = new Scanner(line);
                    int choose = in.nextInt();
                    if (choose != 1 && choose != 0) {
                        System.out.println("There is no such game. Try again.");
                        while (true) {
                            choose = scanner.nextInt();
                            if (choose == 1) {
                                break;
                            } else if (choose == 0) {
                                break;
                            } else {
                                System.out.println("Try again.");
                                scanner.nextLine();
                            }
                        }
                    }
                    if (choose == 0) {
                        System.out.println("---REGULAR MATCH---");
                        scanner.nextLine();
                        System.out.println("Enter the MNK parameters:");
                        int m = getValidParameter(scanner, "m");
                        int n = getValidParameter(scanner, "n");
                        int k = getValidParameter(scanner, "k");
                        if (k <= 0 || k >= (m * n) / 2) {
                            while (true) {
                                System.out.println("You cant play this game. Enter your k-parameter again.");
                                k = getValidParameter(scanner, "k");
                                if (!(k <= 0 || k >= (m * n) / 2)) {
                                    break;
                                }
                            }
                        }
                        final Game game = new Game(false, new RandomMNKPlayer(m, n), new HumanPlayer());
                        int resultMNK;
                        do {
                            resultMNK = game.play(new MNKBoard(m, n, k));
                            System.out.println("Game result: " + resultMNK);
                        } while (resultMNK != 0);
                        break;

//                        while (true) {
//                            try {
//                                if (!scanner.hasNext()) {
//                                    System.out.println("The input is closed.");
//                                    break;
//                                }
//                                try {
//                                    String line1 = scanner.nextLine();
//                                    if (line1.isBlank()) {
//                                        continue;
//                                    }
//                                    Scanner in1 = new Scanner(line1);
//                                    while (true) {
//                                        int m = getValidParameter(scanner, "m");
//                                        int n = getValidParameter(scanner, "n");
//                                        int k = getValidParameter(scanner, "k");
//                                        if (k <= 0 || k > (m * n) / 2) {
//                                            System.out.println("You cant play this game. Enter your k-parameter again.");
//                                            k = getValidParameter(scanner, "k");
//                                        }
//                                        final Game game = new Game(false, new RandomMNKPlayer(m, n), new HumanPlayer());
//                                        int resultMNK;
//                                        do {
//                                            resultMNK = game.play(new MNKBoard(m, n, k));
//                                            System.out.println("Game result: " + resultMNK);
//                                        } while (resultMNK != 0);
//                                        break;
//                                    }
//                                    in1.close();
//                                } catch (NoSuchElementException e) {
//                                    System.out.println("Incorrect data.");
//                                    System.out.println("-------------------------------");
//                                    break;
//                                }
//                            } catch (InputMismatchException e) {
//                                System.out.println("You wrote the wrong MNK");
//                            }
//                            break;
//                        }
                    }
                    if (choose == 1) {
                        System.out.println("---CHAMPIONSHIP---");
                        scanner.nextLine();
                        System.out.println("Enter the MNK parameters:");
                        int m = getValidParameter(scanner, "m");
                        int n = getValidParameter(scanner, "n");
                        int k = getValidParameter(scanner, "k");
                        if (k <= 0 || k >= (m * n) / 2) {
                            while (true) {
                                System.out.println("You cant play this game. Enter your k-parameter again.");
                                k = getValidParameter(scanner, "k");
                                if (!(k <= 0 || k >= (m * n) / 2)) {
                                    break;
                                }
                            }
                        }
                        System.out.println("Write the number of participants");
                        int number = getValidParameter(scanner, "number");
                        if (number <= 1) {
                            System.out.println("You can't play the tournament with " + number + " participants. Try again");
                            number = getValidParameter(scanner, "number");
                        }
                        Player[] players = new Player[number];
                        System.out.println("________PLAY-OFF RESULTS_______");
                        for (int i = 0; i < players.length; i++) {
                            players[i] = new RandomMNKPlayer(m, n);
                        }
                        new OlympicSystem(players, m, n, k).makeTournament();
                        break;
//                        while (true) {
//                            try {
//                                if (!scanner.hasNext()) {
//                                    System.out.println("The input is closed.");
//                                    break;
//                                }
//                                String line2 = scanner.nextLine();
//                                if (line2.isBlank()) {
//                                    continue;
//                                }
//                                try {
//                                    Scanner in2 = new Scanner(line2);
//                                    int m = in2.nextInt();
//                                    int n = in2.nextInt();
//                                    int k = in2.nextInt();
//                                    if (in2.hasNext()) {
//                                        System.out.println("You wrote too much input data.");
//                                        System.out.println("------------------------------");
//                                        break;
//                                    }
//                                    if (m <= 0) {
////                                        System.out.println("Try again, you wrote incorrect data");
////                                        continue;
//                                        System.out.println("Try to write the M-parameter again.");
//                                        while (true) {
//                                            m = scanner.nextInt();
//                                            if (m > 0) {
//                                                break;
//                                            } else {
//                                                System.out.println("Try again");
//                                            }
//                                        }
//                                    }
//                                    if (n <= 0) {
//                                        System.out.println("Try to write the N-parameter again.");
//                                        while (true) {
//                                            n = Integer.parseInt(scanner.next());
//                                            if (n > 0) {
//                                                break;
//                                            } else {
//                                                System.out.println("Try again");
//                                            }
//                                        }
//                                    }
//                                    if (k < 0 || k > m * n / 2) {
//                                        try {
//                                            System.out.println("You cant play this game. Enter your k-parameter again.");
//                                            while (true) {
//                                                k = scanner.nextInt();
//                                                if (k > 0 && k < (m * n) / 2) {
//                                                    break;
//                                                } else {
//                                                    System.out.println("Try again");
//                                                }
//                                            }
//                                        } catch (NumberFormatException e) {
//                                            continue;
//                                        }
//                                    }
//                                    System.out.println("Write the number of participants");
//                                    while (true) {
//                                        try {
//                                            int num = scanner.nextInt();
//                                            if (num <= 0) {
//                                                System.out.println("You wrote the wrong data, try again");
//                                                while (true) {
//                                                    num = scanner.nextInt();
//                                                    if (num > 0) {
//                                                        break;
//                                                    } else {
//                                                        System.out.println("Try again");
//                                                        scanner.nextLine();
//                                                    }
//                                                }
//                                            }
//                                            Player[] players = new Player[num];
//                                            System.out.println("________PLAY-OFF RESULTS_______");
//                                            for (int i = 0; i < players.length; i++) {
//                                                players[i] = new RandomMNKPlayer(m, n);
//                                            }
//                                            new OlympicSystem(players, m, n, k).makeTournament();
//                                            break;
//                                        } catch (NumberFormatException e) {
//                                            System.out.println("You wrote the wrong number of participants.");
//                                            scanner.nextLine();
//                                        }
//                                    }
//                                    in2.close();
//                                } catch (NoSuchElementException e) {
//                                    System.out.println("Incorrect data.");
//                                    System.out.println("-------------------------------");
//                                    scanner.nextLine();
//                                    break;
//                                }
//                            } catch (NumberFormatException e) {
//                                System.out.println(e.getMessage());
//                            } catch (InputMismatchException e) {
//                                System.out.println("You wrote the wrong MNK format");
//                                scanner.nextLine();
//                            } catch (NoSuchElementException e) {
//                                break;
//                            }
//                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("The wrong input.");
                    scanner.nextLine();
                } catch (NoSuchElementException e) {
                    break;
                }
            } catch (NoSuchElementException e) {
                break;
            }
        }
        scanner.close();
    }
}
