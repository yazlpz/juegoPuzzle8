package puzzle;

public class PilaEstatica<E> implements Pila<E> {
	
	private E items[];
	private int sp;
	private int size;
	private final int LIMITE=10;
	
	@SuppressWarnings("unchecked")
	public PilaEstatica() {
		this.items= (E[])new Object[LIMITE];
		this.sp=LIMITE;
		this.size=0;
	}
	
	@SuppressWarnings("unchecked")
	public PilaEstatica(int size) {
		this.items= (E[])new Object[size];
		this.sp=size;
		this.size=0;
	}

	@Override
	public void push(E item) throws ExcepcionDePilaLlena {
		// TODO Auto-generated method stub
        if(this.isFull())
        	throw new ExcepcionDePilaLlena("La pila esta Llena!!");
        this.items[--this.sp]=item;
        this.size++;
	}

	@Override
	public E pop() throws ExcepcionDePilaVacia {
		// TODO Auto-generated method stub
		if(this.isEmpty())
			throw new ExcepcionDePilaVacia("La pila esta Vacia!!");
		this.size--;
		return this.items[this.sp++];
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.size==0;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return this.size==this.items.length;
	}

	@Override
	public E top() throws ExcepcionDePilaVacia {
		// TODO Auto-generated method stub
		if(this.isEmpty())
			throw new ExcepcionDePilaVacia("La pila esta Vacia!!");
		return this.items[this.sp];
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.size;
	}

}
