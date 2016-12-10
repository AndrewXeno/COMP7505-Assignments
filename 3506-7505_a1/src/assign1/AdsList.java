package assign1;

/*
 * This class implements the List ADT.
 * A list is represented by a head (the first element of the list) and a tail (the list of the rest of the elements).
 */

public class AdsList<E> {
	
	private E head;
	private AdsList<E> tail;
	
	// create an empty list
	public AdsList(){
		this.head = null;
		this.tail = null;
	}
	
	private AdsList(E head, AdsList<E> tail){
		this.head = head;
		this.tail = tail;
	}
	
	// return the list where the element was added
	public AdsList<E> add(E element){
//		return new AdsList<E>(element, this.tail);		Error1: the returned list's tail should include the whole original list, not only it's tail. Or an element is missing.
		return new AdsList<E>(element, this);
	}
	
	public boolean isEmpty(){
		return (head == null && tail == null);
	}
	
	// remove and return the head (first element)
	public E headChop(){
		E headTemp = this.head;
		this.head = this.tail.headPeek();
		this.tail = this.tail.getTail();
//		return this.head;		Error2£ºThis will not remove the head. A temporary head should be stored and reassign the head to its tail's head before reassign the tail and return the temporary head.
		return headTemp;
	}
	
	// return the first element without removing it from the list
	public E headPeek() {
		return head;
	}

	public AdsList<E> getTail() {
		return tail;
	}

	// return the element in position i in the list without removing it
	public E get(int i) {
		if(i==0)
			return head;
		return tail.get(i-1);
	}
	
}
