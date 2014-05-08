import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * The FrequencyQueue implemented with a max heap and a map. TOCOMPLETE
 * 
 * @param <E>
 *            The type of elements in the queue
 * 
 * @author TOCOMPLETE
 */
public class FrequencyQueue<E> implements Cloneable {

	/**
	 *The ArrayList that stores the heap.
	 * 
	 * @invariant forall e:entries | e.frequency > 0
	 */
	private ArrayList<Entry<E>> entries;
	private Bag<E> bag;

	/**
	 * The Map that stores the positions in the heap of items in the queue.
	 * 
	 * @invariant forall e:map.keyset() | entries.get(map.get(e)).item.equals(e)
	 * @invariant forall 0 <= i < entries.size() | map(get(i).item)==i
	 */
	private HashMap<E, Integer> map;

	// TOCOMPLETE

	/**
	 * Build an empty frequency queue.
	 */
	public FrequencyQueue() {
		this.entries = new ArrayList<Entry<E>>();
		this.bag = new Bag();
		this.map = new HashMap<E, Integer>();
	}

	/**
	 * Is this queue empty?
	 * 
	 * @return <tt>True</tt> in case it is empty. <tt>False</tt> otherwise.
	 */
	public boolean isEmpty() {
		return entries.isEmpty();
	}

	/**
	 * @return The frequency of the highest frequency item in the queue.
	 * @requires !isEmpty();
	 */
	public int topFreq() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return The item with the highest frequency in the queue
	 * @requires !isEmpty()
	 */
	public E topItem() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Increase by one the frequency of item in the queue. If item is not in the
	 * queue, add it to the queue with frequency one.
	 * 
	 * @param item
	 *            The item.
	 */
	public void add(E item) {
		if(this.bag.contains(item)){
			Entry<E> entry = this.entries.get(this.map.get(item));
			entry.frequency++;
			reMap(item, swim(this.map.get(item));
		}else{
			bag.add(item);
			this.entries.add(new Entry<E>(item, 1));
			this.map.put(item, swim(entries.size()-1));
		}
		
	}		

	/**
	 * Decrease by one the frequency of item in the queue. If the frequency of
	 * item becomes zero, remove it from the queue
	 * 
	 * @param item
	 *            The item
	 */
	public void sub(E item) {
		int pos = map.get(item);
		int freq = this.entries.get(pos).frequency;
		if(freq == 1){
			this.entries.remove(pos);
			reheap();
		}else{
			this.entries.get(pos).frequency--;
		}	
	}

	/**
	 * reheap
	 */
	private void reheap(){
		
	}
	
	/**
	 * Comparator
	 */
	private int compare(Entry<E> first, Entry<E> second){
		if(first.frequency == second.frequency)
			return 0;
		if(first.frequency > second.frequency)
			return 1;
		else
			return -1;
	}
	
	/**
	 * A bag with the elements in the queue. The frequency of an item in the bag
	 * is the same than in the queue. IMPORTANT: this method implements
	 * auxiliary operation in specification FrequencyQueue not to be called by
	 * clients or internally by other methods!
	 */
	public Bag<E> elements() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Is a given node a leaf in the heap?
	 */
	private boolean isLeaf(int parent) {
		if((parent*2)+1 > this.entries.size()-1)
			return true;
		return false;
	}

	/**
	 * Sink a changed node of the heap inorder to restore the heap.
	 * 
	 * @param node
	 *            The changed node.
	 */
	private void sink(int node) {
		// TODO Auto-generated method stub
	}

	/**
	 * Make a changed node to swim in the direction of root inorder to restore
	 * the heap.
	 * 
	 * @param node
	 *            The changed node.
	 */
	private int swim(int node) {
		return 0;
	}

	/**
	 * actualiza map
	 */
	private void reMap(E item, int key){
		map.replace(item, key);
	}
	
	/**
	 * The index of the larger child of a node
	 * 
	 * @param node
	 *            The node
	 */
	private int maxChild(int parent) {
		//verifica se nao eh folha
		if (!isLeaf(parent)) {
			//parent com 2 filhos
			if ((parent * 2) + 2 <= this.entries.size() - 1) {
				boolean isEqual = this.entries.get((parent * 2) + 1).frequency == this.entries
						.get((parent * 2) + 2).frequency;
				//se tiverem a mesma frequency
				if (isEqual)
					return (parent * 2) + 1;
				
				//verifica se eh leftChild
				boolean leftChild = this.entries.get((parent * 2) + 1).frequency > this.entries
						.get((parent * 2) + 2).frequency;
				//se o leftChild eh tem mais frequency
				if (leftChild)
					return (parent * 2) + 1;
				
				//verifica se eh rightChild
				boolean rightChild = this.entries.get((parent * 2) + 1).frequency < this.entries
						.get((parent * 2) + 2).frequency;
				if (rightChild)
					return (parent * 2) + 2;
			} else {
				return (parent * 2) + 1;
			}
		}
		return -1;

	}

	/**
	 * Swap the entries in two positions of the supporting arraylist and update
	 * the map accordingly.
	 * 
	 * @param pos1
	 *            One position
	 * @param pos2
	 *            The other position
	 */
	private void swap(int child, int parent) {
		Entry<E> aux = this.entries.get(parent);
		this.entries.set(parent, this.entries.get(child));
		this.entries.set(child, aux);
		//toHash
	}

	/**
	 * @return The entry with the highest frequency in the heap
	 * @requires !isEmpty()
	 */
	private Entry<E> max() {
		return entries.get(0);
	}

	/**
	 * Removes from the frequency queue the max entry requires !isEmpty()
	 */
	private void delMax() {
		int last = this.entries.size() - 1;
		Entry<E> elem = this.entries.get(last);
		this.entries.remove(last);
		this.entries.set(0, elem);
		//re-hash
		// faz sink do elem de acordo com a prioridade
		sink(0);
	}

	/**
	 * A shallow clone of this queue.
	 * 
	 * @return a shallow copy of this FrequencyQueue instance: the values
	 *         themselves are not cloned.
	 */
	@SuppressWarnings("unchecked")
	public FrequencyQueue<E> clone() {
		try {
			FrequencyQueue<E> result = (FrequencyQueue<E>) super.clone();
			result.entries = new ArrayList<Entry<E>>();
			for (Entry<E> entry : entries)
				result.entries.add(entry.clone());
			result.map = (HashMap<E, Integer>) this.map.clone();
			return result;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError(e.toString());
		}
	}

	/**
	 * Is this frequency equal to a given Object?
	 */
	@SuppressWarnings("unchecked")
	public boolean equals(Object other) {
		return this == other || this.getClass() == other.getClass()
				&& equalQueues((FrequencyQueue<E>) other);
	}

	/**
	 * Is this frequency queue equal to another one?
	 */
	private boolean equalQueues(FrequencyQueue<E> other) {
		FrequencyQueue<E> first = this.clone();
		ArrayList<Entry<E>> elsOther = other.clone().entries;
		while (!first.isEmpty()) {
			if (!elsOther.contains(first.max()))
				return false;
			elsOther.remove(first.max());
			first.delMax();
		}
		return elsOther.isEmpty();
	}

	/**
	 * The textual representation of this frequency queue.
	 */
	public String toString() {
		// OPTION1 useful for debugging
		StringBuilder result = new StringBuilder("");
		FrequencyQueue<E> copy = this.clone();
		while (!copy.isEmpty()) {
			result.append(copy.max() + " ");
			copy.delMax();
		}
		return result.toString() + "  " + map.toString();
		// OPTION2 more succinct
		// return entries.toString();
	}

	/**
	 * A static nested class defining the entries of the heap
	 * 
	 * @param <E>
	 *            The type of items in entries
	 */
	private static class Entry<E> implements Cloneable {

		private E item;

		private int frequency;

		/**
		 * TOCOMPLETE
		 * 
		 * @param e
		 *            TOCOMPLETE
		 * @requires TOCOMPLETE
		 */
		private Entry(E e, int freq) {
			this.item = e;
			this.frequency = freq;
		}

		public String toString() {
			return "(" + item + ":" + frequency + ")";
		}

		@SuppressWarnings("unchecked")
		@Override
		public Entry<E> clone() {
			try {
				return (Entry<E>) super.clone();
			} catch (CloneNotSupportedException e) {
				// this shouldn't happen, since we are Cloneable
				throw new InternalError(e.toString());
			}
		}

		@Override
		@SuppressWarnings("rawtypes")
		public boolean equals(Object other) {
			return other == this
					|| (other instanceof Entry
							&& ((Entry) other).item.equals(item) && ((Entry) other).frequency == frequency);

		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + frequency;
			result = prime * result + ((item == null) ? 0 : item.hashCode());
			return result;
		}
	}
}
