import java.util.ArrayList;
import java.util.HashMap;

/**
 * The FrequencyQueue implemented with a max heap and a map.
 * Trabalho 2 - AED 2013/2014 - LEI - FCUL
 * 
 * @param <E>
 *            The type of elements in the queue
 * 
 * @author Joao Rodrigues - fc45582
 * @author Simao Neves - fc45681
 * @author Grupo AED - aed041
 */
public class FrequencyQueue<E> implements Cloneable {

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

	/**
	 * Build an empty frequency queue.
	 */
	public FrequencyQueue() {
		entries = new ArrayList<Entry<E>>();
		map = new HashMap<E, Integer>();
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
		return max().frequency;
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
		if (map.containsKey(item)) {
			entries.get(map.get(item)).frequency++;
			map.put(item, swim(map.get(item)));
		} else {
			entries.add(new Entry<E>(item, 1));
			map.put(item, swim(entries.size() - 1));
		}
	}

	/**
	 * Reorganiza os valores do map de acordo com heap
	 * @param pos
	 * 			posicao a partir da qual a reorganizacao
	 * 			eh feita
	 */
	private void reHash(int pos) {
		//para cada posicao seguinte a pos decrementa no map
		for(int i = pos; i<entries.size(); i++){
			map.put(entries.get(i).item, i);
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
			int freq = entries.get(pos).frequency;
			//se o elemento ja so tem frequencia 1
			//elimina
			if (freq == 1) {
				map.remove(entries.get(pos).item);
				entries.remove(pos);
				if(entries.size() > 0)
					if(pos > 0)
						reHash(pos-1);
					else
						reHash(0);
			} 
			//se o elemento tem frequencia > 1
			//subtrai uma unidade ah frequencia
			else {
				this.entries.get(pos).frequency--;
				if (hasToSink(pos))
					sink(pos);
			}
		}
	}

	/**
	 * Comparador de posicoes de Entrys, com base na relação de ordem de frequencias
	 * 
	 * @param firstIndex Primeira posicao
	 * @param secondIndex Segunda posicao
	 * @return Devolve -1 se firstIndex for menor, 1 se for maior e 0 se as posicoes tiverem a mesma frequencia
	 */
	private int compare(int firstIndex, int secondIndex) {
		Entry<E> first = entries.get(firstIndex);
		Entry<E> second = entries.get(secondIndex);
		if (first.frequency == second.frequency)
			return 0;
		else if (first.frequency > second.frequency)
			return 1;
		return -1;
	}

	/**
	 * A bag with the elements in the queue. The frequency of an item in the bag
	 * is the same than in the queue. IMPORTANT: this method implements
	 * auxiliary operation in specification FrequencyQueue not to be called by
	 * clients or internally by other methods!
	 * 
	 * @param frequencyQueue FrequencyQueue de onde se vao retirar os elementos para colocar no Bag retornado
	 * @return Bag com os elementos todos da frequencyQueue, com a frequencia correspondente
	 * @requires frequencyQueue != null
	 */
	public Bag<E> elements() {
		Bag<E> bagFinal = new Bag<E>();
		for (Entry<E> currEntry : entries) {
			int freq = currEntry.frequency;
			E itemCurr = currEntry.item;
			//para cada freq de cur.item adiciona a bag
			for (int i = 0; i < freq; i++) {
				bagFinal.add(itemCurr);
			}
		}
		return bagFinal;
	}

	/**
	 * Is a given node a leaf in the heap?
	 * 
	 * @param parent Index da posicao que vai ser avaliada
	 * @return True se o node for leaf (nao tiver filhos), false caso contrario
	 */
	private boolean isLeaf(int parent) {
		return ((parent * 2) + 1 > entries.size() - 1);
	}

	/**
	 * Sink a changed node of the heap inorder to restore the heap.
	 * 
	 * @param parent
	 *            Index da posicao que vai ter de fazer sink no Amontoado
	 */
	private void sink(int parent) {
		int maxChild = maxChild(parent);
		boolean sinked = false;

		if (maxChild != -1)
			//enquanto deve sinkar
			while (compare(parent, maxChild) < 0) {
				sinked = true;
				swap(parent, maxChild);
				parent = maxChild;
				maxChild = maxChild(parent);
				//se jah nao tem filhos
				if (maxChild < 0)
					break;
			}
		//se todos os elementos tem a mesma freq
		if (!sinked) {
			if (maxChild(parent) != -1) {
				reHash(parent);
			}
		}
	}

	/**
	 * Make a changed node to swim in the direction of root inorder to restore
	 * the heap.
	 * 
	 * @param child
	 *            Index da posicao que vai ter de fazer swim no Amontoado
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
	 * The index of the larger child of a node
	 * 
	 * @param parent
	 *            Index da posicao na ArrayList de Entrys que vai ser usada para determinar o maior filho
	 * @return Devolve a posicao do maior filho da posicao recebida, se nao tiver filhos devlve -1
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

		Entry<E> auxParent = this.entries.get(parent);
		Entry<E> auxChild = this.entries.get(child);
		// entries swap
		entries.set(parent, auxChild);
		entries.set(child, auxParent);
		// map swap
		map.put(auxParent.item, child);
		map.put(auxChild.item, parent);
	}

	/**
	 * Verifica se a entry deve sinkar
	 * 
	 * @param parent Index da posicao na ArrayList de Entrys que vai ser avaliada
	 * @return True se tiver que sinkar, false caso contrário
	 */
	private boolean hasToSink(int parent) {
		boolean hasToSink = false;
		// verifica se nao eh folha
		if (!isLeaf(parent))
			hasToSink = (compare(parent, maxChild(parent)) < 0);
		return hasToSink;
	}

	/**
	 * Retorna a Entry com maior frequencia no Amontoado
	 * 
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
		//remove do mapa
		E curMax = this.entries.get(0).item;
		map.remove(curMax);
		
		//se o tamanho da freq queue eh maior que 1
		if (this.entries.size() > 1) {
			int last = this.entries.size() - 1;
			Entry<E> min = this.entries.get(last);
			
			//passa o elemento mais pequeno para o topo
			this.entries.set(0, min);
			this.entries.remove(last);
			
			//se tem filhos faz sink
			if (!isLeaf(0))
				sink(0);
		}
		//se tem apenas um elemento
		else {
			map.remove(entries.get(0).item);
			entries.remove(0);
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
		return "FQ: " + entries.toString() + "\nMap: " + map.toString();

	}

	/**
	 * A static nested class defining the entries of the heap
	 * 
	 * @param <E>
	 *            The type of items in entries
	 */
	private static class Entry<E> implements Cloneable {

		/**
		 * O elemento a ser guardado
		 */
		private E item;

		/**
		 * A frequencia do elemento
		 */
		private int frequency;

		/**
		 * Constructor de uma Entry do elemento e com frequencia freq
		 * 
		 * @param e
		 *            Elemento para ser guardado
		 * @param freq
		 *            Frequencia do elemento guardado
		 * @requires freq >= 0 && e != null
		 */
		private Entry(E e, int freq) {
			this.item = e;
			this.frequency = freq;
		}

		/**
		 * Representacao textual de uma Entry
		 * 
		 * @return Representacao textual de uma Entry
		 */
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
