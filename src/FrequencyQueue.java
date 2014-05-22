import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * The FrequencyQueue implemented with a max heap and a map. TOCOMPLETE
 * 
 * @param <E>
 *            The type of elements in the queue
 * 
 * @author TOCOMPLETE
 */
public class FrequencyQueue<E> implements Cloneable {

	public static void main(String[] args) {
		FrequencyQueue<String> queue = new FrequencyQueue<String>();
		queue.add("a");
		queue.add("b");
		queue.add("c");
		queue.add("d");
		queue.add("d");
		
		System.out.println(queue);
		
	}

	/**
	 * The ArrayList that stores the heap.
	 * 
	 * @invariant forall e:entries | e.frequency > 0
	 */
	private ArrayList<Entry<E>> entries;

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
		return this.max().frequency;
	}

	/**
	 * @return The item with the highest frequency in the queue
	 * @requires !isEmpty()
	 */
	public E topItem() {
		return this.max().item;
	}

	/**
	 * Increase by one the frequency of item in the queue. If item is not in the
	 * queue, add it to the queue with frequency one.
	 * 
	 * @param item
	 *            The item.
	 */
	public void add(E item) {
		if (this.map.containsKey(item)) {
			this.entries.get(this.map.get(item)).frequency++;
			reMap(item, swim(this.map.get(item)));
		} else {
			this.entries.add(new Entry<E>(item, 1));
			this.map.put(item, swim(entries.size() - 1));
		}
	}
	
	private void reHash() {
		this.map.clear();
		Iterator<Entry<E>> iter = entries.iterator();
		int i = 0;
		while (iter.hasNext()) {
			Entry<E> curr = iter.next();
			this.map.put(curr.item, i);
			i++;
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
		if (map.containsKey(item)) {
			int pos = map.get(item);
			int freq = this.entries.get(pos).frequency;
			if (freq == 1) {
				this.entries.remove(pos);
				// TESTE, ACTUALIZAR MAPA
				//this.map.remove(item);
				reHash();
				
			} else {
				this.entries.get(pos).frequency--;
				if (hasToSink(pos))
					sink(pos);
			}
		}
	}

	/**
	 * reheap
	 */
	private void reheap() {
		for (int i = entries.size() - 1; i > 0; i--) {
			swim(i);
		}
	}

	/**
	 * Comparator
	 */
	private int compare(int firstIndex, int secondIndex) {
		int result = -1;
		Entry<E> first = entries.get(firstIndex);
		Entry<E> second = entries.get(secondIndex);
		if (first.frequency == second.frequency)
			result = 0;
		else if (first.frequency > second.frequency)
			result = 1;
		return result;
	}

	/**
	 * A bag with the elements in the queue. The frequency of an item in the bag
	 * is the same than in the queue. IMPORTANT: this method implements
	 * auxiliary operation in specification FrequencyQueue not to be called by
	 * clients or internally by other methods!
	 */
	public Bag<E> els() {
		Bag<E> bagFinal = new Bag<E>();
		for (Entry<E> currEntry : entries) {
			int freq = currEntry.frequency;
			E itemCurr = currEntry.item;
			for (int i = 0; i < freq; i++) {
				bagFinal.add(itemCurr);
			}
		}
		return bagFinal;
	}

	/**
	 * Is a given node a leaf in the heap?
	 */
	private boolean isLeaf(int parent) {
		return ((parent * 2) + 1 > this.entries.size() - 1);
	}

	/**
	 * Sink a changed node of the heap inorder to restore the heap.
	 * 
	 * @param node
	 *            The changed node.
	 */
	private void sink(int parent) {
		int maxChild = maxChild(parent);
		boolean isLeaf = (maxChild == -1);
		while(compare(parent, maxChild) < 0 && !isLeaf){
			swap(parent, maxChild);
			parent = maxChild;
			maxChild = maxChild(parent);
			if (maxChild == -1)
				break;
			isLeaf = isLeaf(parent);
		}
	}


	/**
	 * Make a changed node to swim in the direction of root inorder to restore
	 * the heap.
	 * 
	 * @param node
	 *            The changed node.
	 */
	private int swim(int child) {
		int parent = (child - 1) / 2;
		while (compare(child, parent) > 0) {
			swap(parent, child);
			child = parent;
			parent = (child - 1) / 2;
		}
		return child;
	}

	/**
	 * actualiza map
	 */
	private void reMap(E item, int key) {
		map.put(item, key);
	}

	/**
	 * The index of the larger child of a node
	 * 
	 * @param node
	 *            The node
	 */
	private int maxChild(int parent) {
		// caso nao tenha filhos, devolve -1
		int maxChild = -1;
		
		// verifica se nao eh folha
		if (!isLeaf(parent)) {
			int childLeft = (parent * 2) + 1;
			int childRight = childLeft + 1;
			// parent com 2 filhos
			if (childRight <= this.entries.size() - 1) {
				// se o max eh o da esquerda ou se tiverem a mesma frequency
				// retorna o da esquerda, caso contrario retorna o da direita
				if (compare(childLeft, childRight) >= 0)
					maxChild = childLeft;
				else
					maxChild = childRight;
			}
			// parent so tem um filho, retorna o da esquerda
			else {
				maxChild = childLeft;
			}
		}
		return maxChild;
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
		Entry<E> auxChild = this.entries.get(child);
		// entries swap
		entries.set(parent, auxChild);
		entries.set(child, aux);
		// map swap
		map.put(aux.item, child);
		map.put(auxChild.item, parent);
	}

	/**
	 * Verifica se a entry deve sinkar
	 * 
	 * @param parent
	 * @return
	 */
	private boolean hasToSink(int parent) {
		boolean hasToSink = false;
		// verifica se nao eh folha
		if (!isLeaf(parent))
			hasToSink = (compare(parent, maxChild(parent)) < 0);
		return hasToSink;
	}

	/**
	 * @return The entry with the highest frequency in the heap
	 * @requires !isEmpty()
	 */
	private Entry<E> max() {
		return entries.get(0);
	}
	
	// APAGAR NO FIM, so para testes
	private Entry<E> get(int i) {
		return entries.get(i);
	}

	/**
	 * Removes from the frequency queue the max entry requires !isEmpty()
	 */
	private void delMax() {
		E curMax = this.entries.get(0).item;
		map.remove(curMax);
		if (this.entries.size() > 1) {
			int last = this.entries.size() - 1;
			Entry<E> min = this.entries.get(last);
			this.entries.set(0, min);
			this.entries.remove(last);
			if(!isLeaf(0))
				sink(0);
		} else {
			this.entries.remove(0);
		}
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
//		FrequencyQueue<E> copy = this.clone();
//		while (!copy.isEmpty()) {
//			result.append(copy.max() + " ");
//			copy.delMax();
//		}
		return "FQ: " + entries.toString() + "\nMap: " + map.toString();
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
