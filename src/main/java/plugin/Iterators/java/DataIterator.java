package plugin.Iterators.java;

/**
 * This interface is used to implement the Iterator 
 * design pattern. It defines specific iterator behaviour for all implementing
 * classes.
 */
public interface DataIterator {
    /**
     * The method gets the next entry in the TreeMap
     * @return The next entry in the TreeMap casted as a generic Object type
     */
    public Object next();
    
    /**
     * The method determines if there is another entry in the TreeMap 
     * @return True  or false depending on if there is another entry in the TreeMap
     */
    public boolean hasNext();
}
