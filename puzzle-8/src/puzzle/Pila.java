package puzzle;

public interface Pila<E> {
	
     public void push(E item) throws ExcepcionDePilaLlena;
     public E pop() throws ExcepcionDePilaVacia;
     public boolean isEmpty();
     public boolean isFull();
     public E top() throws ExcepcionDePilaVacia;
     public int size();
}
