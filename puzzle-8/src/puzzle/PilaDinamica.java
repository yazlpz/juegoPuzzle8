package puzzle;

public class PilaDinamica<E> implements Pila<E> {
	
	private NodoPila<E> sp;
	private int size;
	
	public PilaDinamica() {
		this.sp=null;
		this.size=0;
	}

	@Override
	public void push(E item) throws ExcepcionDePilaLlena {
		// TODO Auto-generated method stub
		NodoPila<E> n= new NodoPila<E>(item);
		n.setNext(this.sp);
		this.sp=n;
		this.size++;
	}

	@Override
	public E pop() throws ExcepcionDePilaVacia {
		// TODO Auto-generated method stub
		if(this.isEmpty())
			throw new ExcepcionDePilaVacia("La pila esta Vacia!!");
		E item=this.sp.getItem();
		this.sp=this.sp.getNext();
		this.size--;
		return item;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.sp==null;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E top() throws ExcepcionDePilaVacia {
		// TODO Auto-generated method stub
		if(this.isEmpty())
			throw new ExcepcionDePilaVacia("La pila esta Vacia!!");
		E item=this.sp.getItem();
		return item;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.size;
	}

}
