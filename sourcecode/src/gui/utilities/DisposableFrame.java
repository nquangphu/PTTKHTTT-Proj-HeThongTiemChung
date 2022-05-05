package gui.utilities;

import javax.swing.*;

/**
 * gui
 * Created by NhatLinh - 19127652
 * Date 4/8/2022 - 12:47 AM
 * Description: ...
 */
public class DisposableFrame extends JFrame {

    protected final Runnable onDispose;

    public DisposableFrame(Runnable onDispose)
    {
        this.onDispose = onDispose;
    }
}
