package puzzle;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Stack;

public class Puzzle8 extends JFrame {
    private PilaEstatica<Integer[]> estadoActualDeLaPila = new PilaEstatica<>(9);
    private JButton[] buttons = new JButton[9];
    private int indexVacio;
    private Stack<Integer[]> undoStack = new Stack<>();
    private Stack<Integer[]> redoStack = new Stack<>();

    public Puzzle8() throws ExcepcionDePilaLlena, ExcepcionDePilaVacia {
        // Se tiene que cambiar para que sea random el estado inicial
        Integer[] estadoInicial = {2, 1, 4, 3, 7, 6, 5, null, 8};
        for(int i=0; i<estadoInicial.length; i++) {
            if(estadoInicial[i] == null) {
                indexVacio = i;
            }
        }
        // Integer[] estadoInicial = {1, 2, 3, 4, 5, 6, 7, 8, null};
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
                } catch (ExcepcionDePilaVacia ex) {
                    throw new RuntimeException(ex);
                }
            });
            add(buttons[i]);
        }

        JButton undoButton = new JButton("Deshacer");
        undoButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,"Deshacer");
        });
        add(undoButton);

        JButton redoButton = new JButton("Rehacer");
        redoButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,"Rehacer");
        });
        add(redoButton);

        actualizarTablero();
    }

    private void actualizarTablero() throws ExcepcionDePilaVacia {
        Integer[] estadoActual = estadoActualDeLaPila.top();
        for (int i = 0; i < 9; i++) {
            buttons[i].setText(estadoActual[i] != null ? estadoActual[i].toString() : "");
        }
    }

    private void moverFicha(int index) throws ExcepcionDePilaVacia {
        Integer[] estadoActual = estadoActualDeLaPila.top();

        System.out.println("Intentando mover desde " + index + " a " + indexVacio);
        if (esAdyacente(index, indexVacio)) {
            estadoActual[indexVacio] = estadoActual[index];
            estadoActual[index] = null;
            indexVacio = index;
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

    private void undo() throws ExcepcionDePilaVacia {
        if (!undoStack.isEmpty()) {
            Integer[] currentState = estadoActualDeLaPila.top();
            redoStack.push(currentState.clone());
            estadoActualDeLaPila.pop();
            indexVacio = buscarVacio();
            actualizarTablero();
        }
    }

    private void redo() throws ExcepcionDePilaLlena, ExcepcionDePilaVacia {
        if (!redoStack.isEmpty()) {
            Integer[] currentState = estadoActualDeLaPila.top();
            undoStack.push(currentState.clone());
            estadoActualDeLaPila.push(redoStack.pop());
            indexVacio = buscarVacio();
            actualizarTablero();
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