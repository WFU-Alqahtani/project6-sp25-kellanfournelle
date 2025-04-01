import java.util.Random;

// linked list class for a deck of cards
public class LinkedList {

    public Node head;
    public Node tail;
    public int size = 0;

    LinkedList(){
        head = null;
        tail = null;
        size = 0;
    }

    public void shuffle(int shuffle_count) {

        Random rand = new Random();
        for(int i = 0; i < shuffle_count; i++) {
            // pick two random integers
            int r1 = rand.nextInt(52);
            int r2 = rand.nextInt(52);

            swap(r1,r2); // swap nodes at these indices
        }
    }

    // remove a card from a specific index
    public Card remove_from_index(int index) {
        //check for invalid entry
        if (index < 0 || index >= size){
           throw new IndexOutOfBoundsException ("Invalid index: " + index);
        }

        Node curr = head;

        //loop to move curr until at index
        for (int i = 0; i < index; i++){
            curr = curr.next;
        }


        //make sure its not the head
        if (curr.prev != null){
            curr.prev.next = curr.next;
        }
        else{
            head = curr.next;
        }



        //make sure its not the tail
        if (curr.next != null){
            curr.next.prev = curr.prev;
        }
        else{
            tail = curr.prev;
        }

        //adjust size for removed node
        size = size - 1;
        return curr.data;

    }

    // insert a card at a specific index
    public void insert_at_index(Card x, int index) {
        //check for invalid entry
        if (index < 0 || index > size){
           throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        Node n = new Node(x);

        //insert at head
        if (index == 0){
            n.next = head;
            if (head != null){
                head.prev = n;
            }
            head = n;
            if (size == 0){
                tail = n;
            }
        }
        //insert at tail
        else if(index == size){
            n.prev = tail;
            if (tail != null){
                tail.next = n;
            }
            tail = n;
        }

        //insert in the middle
        else{
            Node curr = head;
            for (int i = 0; i < index; i++){
                curr = curr.next;
            }
            n.prev = curr.prev;
            n.next = curr;
            curr.prev.next = n;
            curr.prev = n;
        }

        //update size for added node
        size = size + 1;

    }

    // swap two cards in the deck at the specific indices
    public void swap(int index1, int index2) {
        //check for invalid entry
        if (index1 < 0 || index1 >= size || index2 < 0 || index2 >= size){
            throw new IndexOutOfBoundsException("Invalid index");
        }

        //make sure not same
        if (index1 == index2){
            return;
        }

        //make index1 always less/appear first
        if (index1 > index2){
            int temp = index1;
            index1 = index2;
            index2 = temp;
        }

        //remove cards from list
        Card c1 = remove_from_index(index1);
        Card c2 = remove_from_index(index2-1);

        //re-add cards in opposite order
        insert_at_index(c1, index2-1);
        insert_at_index(c2, index1);


    }

    // add card at the end of the list
    public void add_at_tail(Card data) {
        Node n = new Node(data);
        n.next = null;

        //if list is empty
        if (tail == null){
            n.prev = null;
            head = n;
        }

        //is list is not empty
        else{
            n.prev = tail;
            tail.next = n;
        }

        //update tail and size
        tail = n;
        size = size + 1;

    }

    // remove a card from the beginning of the list
    public Card remove_from_head() {
        //if list is empty
        if (head == null){
            return null;
        }

        Node n = head;
        head = head.next;

        //if list is now empty
        if (head == null){
            tail = null;
        }

        //if list is still not empty
        else{
            head.prev = null;
        }

        //update size
        size = size - 1;

        return n.data;

    }

    public boolean isEmpty(){
        if (head == null){
            return true;
        }
        return false;
    }

    public int getSize(){
        return this.size;
    }

    // check to make sure the linked list is implemented correctly by iterating forwards and backwards
    // and verifying that the size of the list is the same when counted both ways.
    // 1) if a node is incorrectly removed
    // 2) and head and tail are correctly updated
    // 3) each node's prev and next elements are correctly updated
    public void sanity_check() {
        // count nodes, counting forward
        Node curr = head;
        int count_forward = 0;
        while (curr != null) {
            curr = curr.next;
            count_forward++;
        }

        // count nodes, counting backward
        curr = tail;
        int count_backward = 0;
        while (curr != null) {
            curr = curr.prev;
            count_backward++;
        }

        // check that forward count, backward count, and internal size of the list match
        if (count_backward == count_forward && count_backward == size) {
            System.out.println("Basic sanity Checks passed");
        }
        else {
            // there was an error, here are the stats
            System.out.println("Count forward:  " + count_forward);
            System.out.println("Count backward: " + count_backward);
            System.out.println("Size of LL:     " + size);
            System.out.println("Sanity checks failed");
            System.exit(-1);
        }
    }

    // print the deck
    public void print() {
        Node curr = head;
        int i = 1;
        while(curr != null) {
            curr.data.print_card();
            if(curr.next != null)
                System.out.print(" -->  ");
            else
                System.out.println(" X");

            if (i % 7 == 0) System.out.println("");
            i = i + 1;
            curr = curr.next;
        }
        System.out.println("");
    }
}