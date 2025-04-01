// Standard French-style cards
public class Card {

    // Suites
    public enum suites {
        NULL, SPADES, CLUBS, DIAMONDS, HEARTS
    }

    // Ranks
    public enum ranks {
        NULL, two, three, four, five, six, seven, eight, nine, ten, jack, king, queen, ace
    }

    private suites suit;
    private ranks rank;

    Card(){
        suit = suites.NULL;
        rank = ranks.NULL;
    }

    Card(suites s, ranks r){
        suit = s;
        rank = r;
    }

    public void print_card(){
        System.out.print(suit + ": " + rank);
    }

    //override method to compare cards
    public int compareTo(Card c){
       //if both are null
        if (this.rank == ranks.NULL && c.rank == ranks.NULL){
            return 0;
        }

        //if this is null
        else if(this.rank == ranks.NULL){
            return -1;
        }

        //if that is null
        else if(c.rank == ranks.NULL){
            return 1;
        }

        else {
            return this.rank.ordinal() - c.rank.ordinal();
        }

    }

}
