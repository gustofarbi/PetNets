import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MenuBar {
    static void makeMenuBar(MainFrame frame){
        JMenuItem menuItem;
        JMenuBar menuBar;
        JMenu menu;

        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.getAccessibleContext().setAccessibleDescription("FileIO menu");

        menuItem = new JMenuItem("New");
        menuItem.addActionListener(new FileItemListener(frame.getFile()));
        menuItem.setActionCommand("new");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Open");
        menuItem.addActionListener(new FileItemListener(frame.getFile()));
        menuItem.setActionCommand("open");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Save");
        menuItem.addActionListener(new FileItemListener(frame.getFile()));
        menuItem.setActionCommand("save");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Save as");
        menuItem.addActionListener(new FileItemListener(frame.getFile()));
        menuItem.setActionCommand("save as");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK + InputEvent.ALT_DOWN_MASK));
        menu.add(menuItem);
        menu.addSeparator();

        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.close();
            }
        });
        menuItem.setActionCommand("close");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));

        menu.add(menuItem);
        menuBar.add(menu);

        menu = new JMenu("Run");

        menuItem = new JMenuItem("Animate next step");
        menuItem.setActionCommand("step");
        menuItem.addActionListener(new RunItemListener(frame));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, InputEvent.SHIFT_DOWN_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Play animation");
        menuItem.setActionCommand("play");
        menuItem.addActionListener(new RunItemListener(frame));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, InputEvent.SHIFT_DOWN_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Stop animation");
        menuItem.setActionCommand("stop");
        menuItem.addActionListener(new RunItemListener(frame));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, InputEvent.SHIFT_DOWN_MASK));
        menu.add(menuItem);

        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }
}
class FileItemListener implements ActionListener {
    private FileIO file;
    FileItemListener(FileIO file){
        this.file = file;
    }
    @Override
    public void actionPerformed(@NotNull ActionEvent e) throws RuntimeException {
        String str = e.getActionCommand();
        switch (str) {
            case "new":
                file.clear();
                break;
            case "open":
                file.openFile();
                break;
            case "save":
                file.saveFile();
                break;
            case "save as":
                file.saveAs();
                break;
            default:
                throw new RuntimeException("Error at FileItemListener-switch!");
        }
    }
}
class RunItemListener implements ActionListener{
    private MainFrame frame;
    RunItemListener(MainFrame frame){this.frame = frame;}
    @Override
    public void actionPerformed(ActionEvent e) throws RuntimeException {
        switch(e.getActionCommand()){
            case "step":
                frame.animateStep();
                break;
            case "play":
                frame.play();
                break;
            case "stop":
                frame.stop();
            default:
                throw new RuntimeException("Error at RunItemListener");
        }
    }
}
