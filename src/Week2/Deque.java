
import java.util.Iterator;
import java.lang.NullPointerException;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

/**
 * Created by greg on 9/18/16.
 */
public class Deque<Item> implements Iterable<Item> {

    private int size;
    private DoubleNode<Item> first;
    private DoubleNode<Item> last;

    private class DoubleNode<Item>{

        private Item value;
        private DoubleNode<Item> previous;
        private DoubleNode<Item> next;

        public DoubleNode(Item i, DoubleNode<Item> prev, DoubleNode<Item> n){
            value = i;
            previous = prev;
            next = n;
        }

        public Item getValue(){
            return value;
        }

        public void setPrevious(DoubleNode<Item> node){
            previous = node;
        }

        public void setNext(DoubleNode<Item> node){
            next = node;
        }

        public DoubleNode<Item> getPrevious(){
            return previous;
        }

        public DoubleNode<Item> getNext(){
            return next;
        }
    }

    // Construct and empty deque
    public Deque(){
        size = 0;
        first = new DoubleNode<>(null, null, null);
        last = new DoubleNode<>(null, first, null);
        first.setNext(last);
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void addFirst(Item item) throws NullPointerException{
        if(item == null){
            throw new NullPointerException();
        }

        DoubleNode<Item> newNode = new DoubleNode<>(item, first, first.getNext());
        first.getNext().setPrevious(newNode);
        first.setNext(newNode);
        size++;
    }

    public void addLast(Item item) throws NullPointerException{
        if(item == null){
            throw new NullPointerException();
        }

        DoubleNode<Item> newNode = new DoubleNode<>(item, last.getPrevious(), last);
        last.getPrevious().setNext(newNode);
        last.setPrevious(newNode);
        size++;
    }

    public Item removeFirst() throws NoSuchElementException{
        if(size == 0){
            throw new NoSuchElementException();
        }
        Item temp = first.getNext().getValue();
        first.setNext(first.getNext().getNext());
        first.getNext().setPrevious(first);
        size--;
        return temp;
    }

    public Item removeLast() throws NoSuchElementException{
        if(size == 0){
            throw new NoSuchElementException();
        }
        Item temp = last.getPrevious().getValue();
        last.setPrevious(last.getPrevious().getPrevious());
        last.getPrevious().setNext(last);
        size--;
        return temp;
    }

    public Iterator<Item> iterator(){
        return new DequeIterator<Item>();
    }

    private class DequeIterator<Item> implements Iterator<Item>{

        private DoubleNode<Item> current = (DoubleNode<Item>) first.getNext();

        public boolean hasNext(){
            return current.getValue() != null;
        }
        public void remove() throws UnsupportedOperationException{
            throw new UnsupportedOperationException();
        }

        public Item next() throws NoSuchElementException{
            if(current.getValue() == null){
                throw new NoSuchElementException();
            }

            Item temp = current.getValue();
            current = current.getNext();
            return temp;
        }

    }

    public static void main(String[] args){

    }
}
