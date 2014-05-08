import java.util.ArrayList;

/**
 * A class that implements the Bag adt.
 * 
 * @author Gloss
 * @version $Id: Bag.java 1767 2013-06-11 16:38:49 asantos $
 * @param <E> parameter of the elements of the bag
 */
public class Bag<E> implements Cloneable {

	/** OLA TESTE
	 * Our bag.
	 */
	private ArrayList<E> bag;

	/**
	 * Constructor.
	 */
	public Bag() {
		bag = new ArrayList<E>();
	}

	/**
	 * Adds an element to the bag.
	 * 
	 * @param elem Element to be added.
	 */
	public void add(E elem) {
		((ArrayList<E>) bag).add(elem);
	}

	/**
	 * Removes an element from the bag.
	 * 
	 * @param elem Element to be removed from the bag.
	 */
	public void remove(E elem) {
		bag.remove(elem);
	}

	/**
	 * Checks how many elements equals to target there is inside the bag.
	 * 
	 * @param target Element to be compared to.
	 * @return Number of elements equals to target.
	 */
	public int howMany(E target) {
		int result = 0;
		for (E element : bag) {
			if (element.equals(target)) {
				result++;
			}
		}
		return result;
	}

	/**
	 * Verifies if the bag is empty.
	 * 
	 * @return <tt>True</tt> in case it is empty. <tt>False</tt> otherwise.
	 */
	public boolean isEmpty() {
		return bag.isEmpty();
	}

	/**
	 * Verifies if the bag contains the specified element.
	 * 
	 * @param elem Element to be verified.
	 * @return <tt>True</tt> in case the bag contains the element.
	 *         <tt>False</tt> otherwise.
	 */
	public boolean contains(E elem) {
		return howMany(elem) != 0;
	}

	/**
	 * A shallow clone of this bag. 
	 * 
	 * @return  a shallow copy of this Bag instance: 
	 * the values themselves are not cloned.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Bag<E> clone() {
		Bag<E> result = null;
		try {
			result = (Bag<E>) super.clone();
			result.bag = (ArrayList<E>) (bag.clone());
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.getMessage());
		}
		return result;
	}

	/**
	 * Hashcode for the queue
	 * 
	 * @return hashcode for the queue
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bag == null) ? 0 : bag.hashCode());
		return result;
	}

	/**
	 * Is this bag equal to other bag?
	 * 
	 * @param other object to be compared
	 * @return true if the object is equal to the bag
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object other) {
		return this == other || (other instanceof Bag<?>)
				&& equalsBags((Bag<E>) other);
	}

	@SuppressWarnings("unchecked")
	private boolean equalsBags(Bag<E> other){
		ArrayList<E> first = (ArrayList<E>)this.bag.clone();
		ArrayList<E> second = (ArrayList<E>)other.bag.clone();
		if (first.size() != second.size())
			return false;
		else
			while( !first.isEmpty() ){
				if ( !second.contains(first.get(0)) )
					return false;
				second.remove(first.get(0));
				first.remove(0);
			}
		return second.isEmpty();
	}

	/**
	 * String representation of the bag
	 * 
	 * @return String representation of the bag
	 */
	@Override
	public String toString() {
		return bag.toString();
	}
}
