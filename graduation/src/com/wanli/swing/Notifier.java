package com.wanli.swing;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * 具有淡入淡出效果且不需要用户点击关闭的消息提示框。
 * @author ggfan@amarsoft
 *
 */
public class Notifier {
    private static final int DISPLAY_TIME = 2000;
    
    private static final int FADE_TIMER = 50;
    
    private static final int FADE_IN_STEP = 30;
    
    private static final int FADE_OUT_STEP = 8;

    private static final int FINAL_ALPHA = 225;

    public static int DEFAULT_WIDTH = 150;
    
    public static  int DEFAULT_HEIGHT = 60;

    private static Color _titleFgColor = SWTResourceManager.getColor(40, 73, 97);

    private static Color _fgColor = _titleFgColor;

    private static Color _bgFgGradient = SWTResourceManager.getColor(226, 239, 249);

    private static Color _bgBgGradient = SWTResourceManager.getColor(177, 211, 243);

    private static Color _borderColor = SWTResourceManager.getColor(40, 73, 97);

    private static Image _oldImage;
    // TODO Scrollable可能不合适
    public static void notify(Scrollable scrollable, final String msg) {

        final Shell parentShell = scrollable.getShell();
        final Shell newShell = new Shell(parentShell, SWT.NO_FOCUS | SWT.NO_TRIM);
        newShell.setLayout(new FillLayout());
        newShell.setForeground(_fgColor);
        newShell.setBackground(_bgBgGradient);
        newShell.setBackgroundMode(SWT.INHERIT_FORCE);
        scrollable.addDisposeListener(new DisposeListener(){
	    public void widgetDisposed(DisposeEvent e) {
		newShell.dispose();
	    }
	});
        final Composite inner = new Composite(newShell, SWT.NONE);
        FillLayout layout = new FillLayout();
        layout.marginWidth = 20;
        layout.marginHeight = 20;
        inner.setLayout(layout);

        newShell.addListener(SWT.Resize, new Listener() {
            public void handleEvent(Event event) {
                try {
                    Rectangle rect = newShell.getClientArea();
                    Image newImage = new Image(Display.getDefault(), Math.max(1, rect.width), rect.height);
                    GC gc = new GC(newImage);
                    // 背景
                    gc.setForeground(_bgFgGradient);
                    gc.setBackground(_bgBgGradient);
                    gc.fillGradientRectangle(rect.x, rect.y, rect.width, rect.height, true);
                    // 边框
                    gc.setLineWidth(2);
                    gc.setForeground(_borderColor);
                    gc.drawRectangle(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2);

                    gc.dispose();
                    newShell.setBackgroundImage(newImage);
                    if (_oldImage != null) {
                        _oldImage.dispose();
                    }
                    _oldImage = newImage;
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });

        Label text = new Label(inner, SWT.WRAP | SWT.CENTER);
        Font tf = text.getFont();
        FontData tfd = tf.getFontData()[0];
        tfd.setStyle(SWT.BOLD);
        tfd.height = 8;
        text.setFont(SWTResourceManager.getFont(tfd.getName(), 8, SWT.NORMAL));
        text.setForeground(_fgColor);
        text.setText(msg);

        newShell.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        if (Display.getDefault().getActiveShell() == null || Display.getDefault().getActiveShell().getMonitor() == null) { 
            return; 
        }
        newShell.setLocation(computePoint(scrollable));
        newShell.setAlpha(0);
        newShell.setVisible(true);

        fadeIn(newShell);
    }
    
    // TODO 当有滚动条出现的时候是否能够居中？
    private static Point computePoint(Scrollable scrollable) {
        Point p = scrollable.toDisplay(scrollable.getClientArea().x, scrollable.getClientArea().y);
        int w = scrollable.getClientArea().width;
        int h = scrollable.getClientArea().height;
        p.x += w / 2 - DEFAULT_WIDTH / 2 ;
        p.y += h / 2 - DEFAULT_HEIGHT / 2; 
        return p;
    }

    private static void fadeIn(final Shell _shell) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    if (_shell == null || _shell.isDisposed()) {
                        return;
                    }

                    int cur = _shell.getAlpha();
                    cur += FADE_IN_STEP;

                    if (cur > FINAL_ALPHA) {
                        _shell.setAlpha(FINAL_ALPHA);
                        startTimer(_shell);
                        return;
                    }

                    _shell.setAlpha(cur);
                    Display.getDefault().timerExec(FADE_TIMER, this);
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        };
        Display.getDefault().timerExec(FADE_TIMER, run);
    }

    private static void startTimer(final Shell _shell) {
        Runnable run = new Runnable() {

            @Override
            public void run() {
                try {
                    if (_shell == null || _shell.isDisposed()) {
                        return;
                    }

                    fadeOut(_shell);
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }

        };
        Display.getDefault().timerExec(DISPLAY_TIME, run);

    }

    private static void fadeOut(final Shell _shell) {
        final Runnable run = new Runnable() {

            @Override
            public void run() {
                try {
                    if (_shell == null || _shell.isDisposed()) {
                        return;
                    }

                    int cur = _shell.getAlpha();
                    cur -= FADE_OUT_STEP;

                    if (cur <= 0) {
                        _shell.setAlpha(0);
                         if (_oldImage != null) {
                             _oldImage.dispose();
                         }
                        _shell.dispose();
                        return;
                    }

                    _shell.setAlpha(cur);

                    Display.getDefault().timerExec(FADE_TIMER, this);

                } catch (Exception err) {
                    err.printStackTrace();
                }
            }

        };
        Display.getDefault().timerExec(FADE_TIMER, run);

    }
    
}