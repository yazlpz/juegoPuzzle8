package puzzle;

public class NodoPila<E> {
	
	private E item;
	private NodoPila<E> next;
	
	public NodoPila(E item) {
		this.item = item;
		this.next=null;
	}

	public E getItem() {
		return item;
	}

	public void setItem(E item) {
		this.item = item;
	}

	public NodoPila<E> getNext() {
		return next;
	}

	public void setNext(NodoPila<E> next) {
		this.next = next;
	}
	
	

}
