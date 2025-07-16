package com.winery.desktop;

import com.winery.desktop.View.Personalmenu.AbstractFrameDefault;
import com.winery.desktop.View.Personalmenu.MenuLogin;

import javax.swing.*;

public class Main {
    public static void main(final String[] arg) {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
            final AbstractFrameDefault f = new MenuLogin();
            f.display();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }
    }
}
