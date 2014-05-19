import java.util.Random;

/**
 * A class to test a Frequency Queue with Strings.
 *
 * @author antonialopes
 */
public class FrequencyQueueRandomTest {

	/**
	 * Our random objects to generate operations and strings.
	 */
	private Random rand, randStrings;

	/** the max length of generated strings */
	private static final int MAX_LENGTH = 2;

	/** chars to use in generated strings */
	private static final String CHARS = "abcdef";


	public static void main(String[] args) {
		new FrequencyQueueRandomTest( new FrequencyQueue<String>(), 3, 1010, true).run();
	}

	private FrequencyQueue<String> queue;
	private int howManyTests;

	public FrequencyQueueRandomTest (FrequencyQueue<String> queue, int howManyInitially,
			int howManyTests, boolean checkPreConditions) {
		this.queue = queue;
		this.rand  = new Random ();
		this.randStrings  = new Random ();
		this.howManyTests = howManyTests;
		fill (howManyInitially);
	}

	public void run () {
		for (int i = 0; i < howManyTests; i++) {
			switch (rand.nextInt(6)) {
			case 0:
				String s = newString();
				System.out.print ("add "+ s+ " ");
				queue.add(s);
				break;
				
			case 1:
				if (!queue.isEmpty()) 
					System.out.print ("topFrequency "+queue.topFreq()+"  ");
				break;
				
			case 2:
				if (!queue.isEmpty()) 
					System.out.print ("topItem "+ queue.topItem()+"  ");
				break;

			case 3:
				if (!queue.isEmpty()) {
					s = newString();
					System.out.print ("sub "+ s +"  ");
					queue.sub(s);
				}
				break;
			
			case 4:
				System.out.print ("is empty? "+ queue.isEmpty() + " ");
				break;
			
			case 5:
				System.out.print ("els "+ queue.els() + " ");
				break;
			}
			System.out.println(queue);
		}
	}

	private void fill (int howMany) {
		for (int i = 0; i < howMany; i++) {
			queue.add(newString());
		}
	}

	/**
	 * Creates a new String.
	 * @return The newly created object.
	 */
	private String newString() {
		return generateString(randStrings.nextInt(MAX_LENGTH)+1);
	}

	private String generateString(int length){
		char[] text = new char[length];
		for (int i = 0; i < length; i++){
			text[i] = CHARS.charAt(randStrings.nextInt(CHARS.length()));
		}
		return new String(text);
	}

}
