
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.File;
import java.awt.event.KeyEvent;

public class GeneratorRaportow extends JFrame {

  JTextArea tekst = new JTextArea();
  JButton przycisk = new JButton("Generuj Raport w Tle");
  JProgressBar pasek = new JProgressBar(0, 100);

  public GeneratorRaportow() {
    setTitle("INF.04 - Generator Raportów");
    setSize(500, 400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    add(new JScrollPane(tekst), BorderLayout.CENTER);

    JPanel dol = new JPanel(new BorderLayout());
    dol.add(przycisk, BorderLayout.NORTH);
    dol.add(pasek, BorderLayout.SOUTH);
    add(dol, BorderLayout.SOUTH);

    JMenuBar menuBar = new JMenuBar();
    JMenu plik = new JMenu("Plik");

    JMenuItem zapisz = new JMenuItem("Zapisz Raport");
    zapisz.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_S, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMaskEx()));

    JMenuItem wyjscie = new JMenuItem("Wyjście");

    plik.add(zapisz);
    plik.add(wyjscie);
    menuBar.add(plik);
    setJMenuBar(menuBar);

    zapisz.addActionListener(e -> zapiszRaport());
    wyjscie.addActionListener(e -> System.exit(0));
    przycisk.addActionListener(e -> generuj());

    setLocationRelativeTo(null);
    setVisible(true);
  }

  void generuj() {
    przycisk.setEnabled(false);

    SwingWorker<Void, Integer> worker = new SwingWorker<>() {

      protected Void doInBackground() throws Exception {
        for (int i = 1; i <= 10; i++) {
          Thread.sleep(300);
          publish(i * 10);
        }
        return null;
      }

      protected void process(java.util.List<Integer> dane) {
        pasek.setValue(dane.get(dane.size() - 1));
      }

      protected void done() {
        tekst.setText("RAPORT\n");

        for (int i = 1; i <= 10; i++) {
          tekst.append("Wiersz raportu " + i + "\n");
        }

        przycisk.setEnabled(true);
        JOptionPane.showMessageDialog(null,
                "Raport wygenerowany!");
      }
    };

    worker.execute();
  }

  void zapiszRaport() {
    if (tekst.getText().isEmpty()) {
      JOptionPane.showMessageDialog(null, "Brak raportu!");
      return;
    }

    JFileChooser wybor = new JFileChooser();

    if (wybor.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
      File plik = wybor.getSelectedFile();

      try {
        FileWriter zapis = new FileWriter(plik + ".txt");
        zapis.write(tekst.getText());
        zapis.close();

        JOptionPane.showMessageDialog(null, "Zapisano raport!");
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Błąd zapisu!");
      }
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new GeneratorRaportow());
  }
}
