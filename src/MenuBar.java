import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar {
    static void makeMenuBar(MainFrame frame){
        JMenuItem menuItem;
        JMenuBar menuBar;
        JMenu menu;

        menuBar = new JMenuBar();
        menu = new JMenu("File");

        menuItem = new JMenuItem("New");
        menuItem.addActionListener(new MenuItemListener(frame.getFile()));
        menuItem.setActionCommand("new");
        menu.add(menuItem);

        menuItem = new JMenuItem("Open");
        menuItem.addActionListener(new MenuItemListener(frame.getFile()));
        menuItem.setActionCommand("open");
        menu.add(menuItem);

        menuItem = new JMenuItem("Save");
        menuItem.addActionListener(new MenuItemListener(frame.getFile()));
        menuItem.setActionCommand("save");
        menu.add(menuItem);

        menuItem = new JMenuItem("Save as");
        menuItem.addActionListener(new MenuItemListener(frame.getFile()));
        menuItem.setActionCommand("save as");
        menu.add(menuItem);
        menu.addSeparator();

        menuItem = new JMenuItem("Close project");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.close();
            }
        });
        menuItem.setActionCommand("close");
        menu.add(menuItem);

        menuBar.add(menu);

        frame.setJMenuBar(menuBar);
    }
}
class MenuItemListener implements ActionListener {
    FileIO file;
    MenuItemListener(FileIO file){
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
                throw new RuntimeException("Error at menubar-switch!");
        }
    }
}

