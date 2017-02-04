
import java.util.Iterator;
import java.lang.NullPointerException;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by greg on 9/18/16.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] data;
    private int size;
    private int capacity = 1;
    private int position;

    private void resize(int i){
        capacity = i;
        Item[] temp = (Item[]) new Object[capacity];
        for(int a=0; a<size; a++){
            temp[a] = data[a];
        }
        data = temp;

    }

    // construct an empty randomized queue
    public RandomizedQueue(){
        data = (Item[]) new Object[capacity];
        size = 0;
    }

    // return true if empty
    public boolean isEmpty(){
        return size == 0;
    }

    // return number of items in array
    public int size(){
        return size;
    }

    // add the item
    // check if need to resize the array
    public void enqueue(Item item) throws NullPointerException{
        if(item == null){
            throw new NullPointerException();
        }

        // Check if array is full
        if(size == capacity){
            resize(2*capacity);
        }

        data[size] = item;
        size++;
    }

    // remove and return a random item. Take an item from the front and put it into the now empty spot
    // Check if need to resize the array
    public Item dequeue() throws NoSuchElementException{
        if(size == 0){
            throw new NoSuchElementException();
        }

        // Get a random number
        position = StdRandom.uniform(0,size);

        // Check if need to resize
        if(size == capacity/4){
            resize(capacity/4);
        }

        Item temp = data[position];
        size--;
        if(position != size) {
            data[position] = data[size];
            data[size] = null;
        }
        return temp;
    }

    // return a random item
    public Item sample() throws NoSuchElementException{
        if(size == 0){
            throw new NoSuchElementException();
        }

        position = StdRandom.uniform(0,size);
        return data[position];
    }

    // return an independent iterator of items in random order
    public Iterator<Item> iterator(){
        return new RandomIterator<Item>();
    }

    private class RandomIterator<Item> implements Iterator<Item> {

        int amount = size;
        int pos;
        Item[] MyArray = (Item[]) new Object[size];

        public RandomIterator(){
            super();
            MyArray = (Item[]) data.clone();
        }

        public boolean hasNext() {
            return amount != 0;
        }

        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

        public Item next() throws NoSuchElementException {
            if(amount == 0){
                throw new NoSuchElementException();
            }

            // Get a random number
            pos = StdRandom.uniform(0,amount);

            Item temp = MyArray[pos];
            amount--;
            if(position != amount) {
                MyArray[position] = MyArray[amount];
                MyArray[amount] = null;
            }
            return temp;

        }
    }

    public static void main(String[] args) {
    }
}
