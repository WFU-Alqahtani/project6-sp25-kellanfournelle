import java.util.Scanner;

public class lab6 {

    public static LinkedList initialize_deck() {

        LinkedList deck = new LinkedList();

        // populate linked list with a single deck of cards
        for (Card.suites s : Card.suites.values()) {
            for(Card.ranks r : Card.ranks.values()) {
                if (r != Card.ranks.NULL && s != Card.suites.NULL) {
                    Card newCard = new Card(s, r);
                    //newCard.print_card();
                    deck.add_at_tail(newCard);
                }
            }
        }

        return deck;
    }

    //play game
    private static void play_blind_mans_bluff(LinkedList player1, LinkedList computer, LinkedList deck) {
        System.out.println("\nStarting Blind mans Bluff \n");

        Scanner sc = new Scanner(System.in);

        //count losses
        int lossStreak = 0;

        while (!player1.isEmpty() && !computer.isEmpty()) {
            //check rage quit
            if (lossStreak >= 3) {
                rage_quit(player1, computer, deck);
                //reset back to 0 after rage quit
                lossStreak = 0;
            }


            //player draws
            Card cardP1 = player1.remove_from_head();
            deck.add_at_tail(cardP1);

            //player draws
            Card cardC = computer.remove_from_head();
            deck.add_at_tail(cardC);

            //print computer card
            System.out.println("Computers card is: ");
            cardC.print_card();
            System.out.println();

            //guess
            int playerGuess = -1;
            while (playerGuess != 0 && playerGuess != 1) {
                System.out.println("Guess 0 (Higher) or 1 (Lower): ");
                playerGuess = sc.nextInt();
                if (playerGuess != 0 && playerGuess != 1) {
                    System.out.println("Invalid guess. Please guess again using 0 (Higher) or 1 (Lower): ");
                }
            }

            //print guess
            if (playerGuess == 0) {
                System.out.println("Player guesses that their card is higher.");
            }
            if (playerGuess == 1) {
                System.out.println("Player guesses that their card is lower.");
            }

            //compare
            int comparison = cardP1.compareTo(cardC);

            boolean correct = ((comparison > 0 && playerGuess == 0) || (comparison < 0 && playerGuess == 1));

            //print if correct
            if (correct) {
                System.out.println("Player's guess is correct.");
            } else {
                System.out.println("Player's guess is incorrect.");
                //update loss streak
                lossStreak++;

            }

            //display player card
            System.out.println("Player's card was:");
            cardP1.print_card();
            System.out.println();
            System.out.println("--------------------");

        }

        System.out.println("Game over.");
    }

    public static void rage_quit(LinkedList player1, LinkedList computer, LinkedList deck){

        System.out.println("Rage quitting... game is resetting!");

        //put player cards back in deck
        while (!player1.isEmpty()){
            deck.add_at_tail(player1.remove_from_head());
        }

        //put computer cards back in deck
        while (!computer.isEmpty()){
            deck.add_at_tail(computer.remove_from_head());
        }

        //shuffle
        deck.shuffle(512);

        //5 new cards dealt
        for (int i = 0; i < 5; i++) {
            // player removes a card from the deck and adds to their hand
            player1.add_at_tail(deck.remove_from_head());
            computer.add_at_tail(deck.remove_from_head());
        }

        System.out.println("The game has been reset. Time to start over!");

    }

    public static void main(String[] args) {

        // create a deck (in order)
        LinkedList deck = initialize_deck();
        deck.print();
        deck.sanity_check(); // because we can all use one

        // shuffle the deck (random order)
        deck.shuffle(512);
        deck.print();
        deck.sanity_check(); // because we can all use one

        // cards for player 1 (hand)
        LinkedList player1 = new LinkedList();
        // cards for player 2 (hand)
        LinkedList computer = new LinkedList();

        int num_cards_dealt = 5;
        for (int i = 0; i < num_cards_dealt; i++) {
            // player removes a card from the deck and adds to their hand
            player1.add_at_tail(deck.remove_from_head());
            computer.add_at_tail(deck.remove_from_head());
        }

        // let the games begin!
        play_blind_mans_bluff(player1, computer, deck);
    }
}
