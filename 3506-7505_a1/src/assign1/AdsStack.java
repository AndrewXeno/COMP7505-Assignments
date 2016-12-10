package assign1;

/*
 * This class implement the Stack ATD using the AdsList type.
 */

public class AdsStack<E> {
	
	private AdsList<E> stack;
	
	public AdsStack(){
//		this.stack = new AdsList<Integer>();		Error1: Should use parameterised type <E> instead of Integer type
		this.stack = new AdsList<E>();
	}
	
	public void push(E element){
//		stack.add(element);			Error2: As defined in AdsList, add() does not modify variable itself. It should be used with assignment.
		stack = stack.add(element);
	}
	
	public E pop(){
//		return stack.get(0);		Error3: pop() should remove the first element and return the removed element. Using stack.get() will not remove the element. headChop() should be used here.
		return stack.headChop();
	}
	
	// count the element in the stack: move all the elements to another storage stack (incrementing a counter), then put them back in the stack and output the counter.
	public int count(){
		AdsStack<E> storage = new AdsStack<E>();
		int count = 0;
		while(!stack.isEmpty()){
			storage.push(this.pop());
			count++;
		}
		for(int i = 0; i < count; i++)
			this.push(storage.pop());
		return count;
	}
}
