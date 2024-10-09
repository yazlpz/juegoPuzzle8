package puzzle;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Stack;

public class Puzzle8 extends JFrame {
    private PilaDinamica<Integer[]> estadoActualDeLaPila = new PilaDinamica<>();
    private JButton[] buttons = new JButton[9];
    private int indexVacio;
    private Stack<Integer[]> undoStack = new Stack<>();
    private Stack<Integer[]> redoStack = new Stack<>();

    public Puzzle8() throws ExcepcionDePilaLlena, ExcepcionDePilaVacia {
        Integer[] estadoInicial = mezclarTablero();
        indexVacio = buscarNulo(estadoInicial);
        estadoActualDeLaPila.push(estadoInicial);

        setTitle("Puzzle 8");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 3));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 35));
            buttons[i].setFocusPainted(false);
            int index = i;
            buttons[i].addActionListener(e -> {
                try {
                    moverFicha(index);
                } catch (ExcepcionDePilaVacia | ExcepcionDePilaLlena ex) {
                    throw new RuntimeException(ex);
                }
            });
            add(buttons[i]);
        }

        JButton undoButton = new JButton("Deshacer");
        undoButton.addActionListener(e -> {
            try {
                undo();
            } catch (ExcepcionDePilaVacia | ExcepcionDePilaLlena ex) {
                JOptionPane.showMessageDialog(this, "Ya no se puede deshacer");
            }
        });
        add(undoButton);

        JButton redoButton = new JButton("Rehacer");
        redoButton.addActionListener(e -> {
            try {
                redo();
            } catch (ExcepcionDePilaLlena | ExcepcionDePilaVacia ex) {
                JOptionPane.showMessageDialog(this, "Ya no se puede rehacer");
            }
        });
        add(redoButton);

        actualizarTablero();
    }

    private int buscarNulo(Integer[] tablero) {
        for(int i=0; i<tablero.length; i++) {
            if(tablero[i] == null) {
                return i;
            }

        }
        return 0;
    }

    private Integer[] mezclarTablero() {
        Integer[] fichas =  new Integer[9];
        ArrayList<Integer> numeros = new ArrayList<>();

        for(int i=0; i<=8; i++) {
            numeros.add(i);
        }

        Collections.shuffle(numeros);
        for(int i=0; i<=8; i++) {
            fichas[i] = (numeros.get(i) != 0 ? numeros.get(i) : null);
            if(numeros.get(i) == 0) {
                fichas[i] = null;
            }
            fichas[i] = numeros.get(i);
        }
        return fichas;
    }

    private void actualizarTablero() throws ExcepcionDePilaVacia {
        Integer[] estadoActual = estadoActualDeLaPila.top();
        for (int i = 0; i < 9; i++) {
            buttons[i].setText(estadoActual[i] != null ? estadoActual[i].toString() : "");
        }
    }

    private void moverFicha(int index) throws ExcepcionDePilaVacia, ExcepcionDePilaLlena {
        Integer[] estadoActual = estadoActualDeLaPila.top().clone();
        System.out.println("Intentando mover desde " + index + " a " + indexVacio);
        if (esAdyacente(index, indexVacio)) {
            undoStack.push(estadoActual.clone());  // Clonamos el estado antes de hacer push
            redoStack.clear();  // Limpia la pila de rehacer después de un nuevo movimiento
            estadoActual[indexVacio] = estadoActual[index];
            estadoActual[index] = null;
            indexVacio = index;
            estadoActualDeLaPila.push(estadoActual.clone());  // Clonamos el estado de nuevo antes de hacer push
            actualizarTablero();

            if (verificarVictoria()) {
                JOptionPane.showMessageDialog(this, "Has ganado");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Este movimiento no es posible");
        }
    }

    private boolean esAdyacente(int index1, int index2) {
        return (index1 == index2 - 1 && index1 % 3 != 2) || // izquierda
                (index1 == index2 + 1 && index2 % 3 != 2) || // derecha
                (index1 == index2 - 3) || // arriba
                (index1 == index2 + 3);   // abajo
    }

    private boolean verificarVictoria() throws ExcepcionDePilaVacia {
        Integer[] estadoGanador = {1, 2, 3, 4, 5, 6, 7, 8, null};
        Integer[] estadoActual = estadoActualDeLaPila.top();
        for (int i = 0; i < estadoActual.length; i++) {
            if (!Objects.equals(estadoActual[i], estadoGanador[i])) {
                return false;
            }
        }
        return true;
    }

    private void undo() throws ExcepcionDePilaVacia, ExcepcionDePilaLlena {
        if (!undoStack.isEmpty()) {
            Integer[] currentState = estadoActualDeLaPila.top();
            redoStack.push(currentState.clone());  // Guarda el estado actual en la pila de rehacer antes de deshacer
            estadoActualDeLaPila.pop();  // Quita el estado actual de la pila
            Integer[] previousState = undoStack.pop();  // Recupera el estado anterior
            estadoActualDeLaPila.push(previousState.clone());
            indexVacio = buscarVacio();
            actualizarTablero();
        } else {
            JOptionPane.showMessageDialog(this, "No hay más movimientos para deshacer");
        }
    }

    private void redo() throws ExcepcionDePilaLlena, ExcepcionDePilaVacia {
        if (!redoStack.isEmpty()) {
            Integer[] currentState = estadoActualDeLaPila.top();
            undoStack.push(currentState.clone());  // Guarda el estado actual en la pila de deshacer antes de rehacer
            Integer[] nextState = redoStack.pop();  // Recupera el estado para rehacer
            estadoActualDeLaPila.push(nextState.clone());
            indexVacio = buscarVacio();
            actualizarTablero();
        } else {
            JOptionPane.showMessageDialog(this, "No hay más movimientos para rehacer");
        }
    }

    private int buscarVacio() throws ExcepcionDePilaVacia {
        Integer[] currentState = estadoActualDeLaPila.top();
        for (int i = 0; i < currentState.length; i++) {
            if (currentState[i] == null) {
                return i;
            }
        }
        return -1;
    }

}