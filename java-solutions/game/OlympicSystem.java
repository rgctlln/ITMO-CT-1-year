package game;

import java.util.*;

public class OlympicSystem {
    protected final Map<Player, Integer> allPlayers;
    private Player[] players;
    private List<Player> thisPlayers;
    private List<Integer> indices;
    private List<Player> winners;
    private final Random random = new Random();
    private final int m;
    private final int n;
    private final int k;


    public OlympicSystem(Player[] players, int m, int n, int k) {
        this.allPlayers = new HashMap<>();
        this.players = players;
        this.m = m;
        this.n = n;
        this.k = k;
        for (int i = 0; i < players.length; i++) {
            this.allPlayers.put(players[i], i + 1);
        }
        this.thisPlayers = new ArrayList<>();
        this.indices = new ArrayList<>();
        this.winners = new ArrayList<>();
        this.thisPlayers.addAll(Arrays.asList(players));
    }

    private int chooseRandom() {
        int num1 = random.nextInt(thisPlayers.size());
        if (!indices.contains(num1)) {
            indices.add(num1);
            return num1;
        } else {
            while (indices.contains(num1)) {
                num1 = random.nextInt(thisPlayers.size());
                if (!indices.contains(num1)) {
                    indices.add(num1);
                    break;
                }
            }
        }
        return num1;
    }

    private void announceResult(Player player1, Player player2, int len) {
        Integer indexWinner = allPlayers.get(player1);
        Integer indexLoser = allPlayers.get(player2);
        if (thisPlayers.size() == 2) {
            System.out.println("Player" + indexLoser + " gets the 2 place!");
            System.out.println("Player" + indexWinner + " won the tournament!!! Grats!");
            System.out.println("--------------------------------------------------");
            System.out.println("Our Olympic Championship in TikTakToe has ended!");
            System.out.println("--------------------------------------------------");
        } else {
            if (len % 2 != 0) {
                //System.out.println("Player" + indexWinner + " goes to the next stage!");
                System.out.println("Player" + indexLoser + " leaves the tournament with the " +
                        (len - len / 2 + 1) + "-" + len + " place");
            } else {
                System.out.println("Player" + indexLoser + " leaves the tournament with the " +
                        (len - len / 2 + 1) + "-" + (len) + " place");
            }
        }
    }

    public void makeTournament() {
        while (thisPlayers.size() > 1) {
            int len = 0;
            int size = thisPlayers.size();
            while (len < thisPlayers.size() / 2) {
                if (thisPlayers.size() % 2 != 0 && thisPlayers.size() != 3) {
                    int randNum = random.nextInt(thisPlayers.size());
                    winners.add(thisPlayers.get(randNum));
                    //indices.add(randNum);
                    thisPlayers.remove(randNum);
                }
                if (thisPlayers.size() == 3) {
                    int randNum = random.nextInt(thisPlayers.size());
                    winners.add(thisPlayers.get(randNum));
                    len++;
                    indices.add(randNum);
                    int num1 = chooseRandom();
                    Player player1 = thisPlayers.get(num1);
                    int num2 = chooseRandom();
                    Player player2 = thisPlayers.get(num2);
                    Game game = new Game(true, player1, player2);
                    int coin = random.nextInt(2);
                    if (coin == 0) {
                        game.setPlayers(player1, player2);
                    } else {
                        game.setPlayers(player2, player1);
                    }
                    int resultGame = game.play(new MNKBoard(m, n, k));
                    if (resultGame == 1) {
                        Integer indexLoser = allPlayers.get(player2);
                        System.out.println("Player" + indexLoser + " gets the 3 place!");
                        winners.add(player1);
                        len++;
                    } else if (resultGame == 2) {
                        Integer indexLoser = allPlayers.get(player1);
                        System.out.println("Player" + indexLoser + " gets the 3 place!");
                        winners.add(player2);
                        len++;
                    } else if (resultGame == 0) {
                        while (resultGame == 0) {
                            coin = random.nextInt(2);
                            if (coin == 0) {
                                game.setPlayers(player1, player2);
                            } else {
                                game.setPlayers(player2, player1);
                            }
                            resultGame = game.play(new MNKBoard(m, n, k));
                            if (resultGame == 1) {
                                Integer indexLoser = allPlayers.get(player2);
                                System.out.println("Player" + indexLoser + " gets the 3 place!");
                                winners.add(player1);
                                len++;
                            } else if (resultGame == 2) {
                                Integer indexLoser = allPlayers.get(player1);
                                System.out.println("Player" + indexLoser + " gets the 3 place!");
                                winners.add(player2);
                                len++;
                            }
                        }
                    }
//                    thisPlayers = new ArrayList<>(winners);
//                    winners.clear();
//                    players = thisPlayers.toArray(new Player[0]);
//                    indices.clear();
//                    continue;
                }
                if (thisPlayers.size() % 2 == 0) {
                    while (len < thisPlayers.size() / 2) {
                        int num1 = chooseRandom();
                        Player player1 = thisPlayers.get(num1);
                        int num2 = chooseRandom();
                        Player player2 = thisPlayers.get(num2);
                        int coin = random.nextInt(2);
                        Game game = new Game(true, player1, player2);
                        if (coin == 0) {
                            game.setPlayers(player1, player2);
                        } else {
                            game.setPlayers(player2, player1);
                        }
                        int resultGame = game.play(new MNKBoard(m, n, k));
                        if (resultGame == 1) {
                            announceResult(player1, player2, size);
                            winners.add(player1);
                            len++;
                        } else if (resultGame == 2) {
                            announceResult(player2, player1, size);
                            winners.add(player2);
                            len++;
                        } else if (resultGame == 0) {
                            while (resultGame == 0) {
                                coin = random.nextInt(2);
                                if (coin == 0) {
                                    game.setPlayers(player1, player2);
                                } else {
                                    game.setPlayers(player2, player1);
                                }
                                resultGame = game.play(new MNKBoard(m, n, k));
                                if (resultGame == 1) {
                                    announceResult(player1, player2, size);
                                    winners.add(player1);
                                    len++;
                                } else if (resultGame == 2) {
                                    announceResult(player2, player1, size);
                                    winners.add(player2);
                                    len++;
                                }
                            }
                        }
                    }
                }
            }
            thisPlayers = new ArrayList<>(winners);
            winners.clear();
            players = thisPlayers.toArray(new Player[0]);
            indices.clear();
        }
        thisPlayers = winners;
        players = thisPlayers.toArray(new Player[0]);
    }
}