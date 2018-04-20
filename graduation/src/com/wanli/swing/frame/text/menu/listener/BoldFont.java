package com.wanli.swing.frame.text.menu.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;

import com.wanli.utils.StaticVariable;

public class BoldFont implements SelectionListener {

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		Point point = StaticVariable.text.getSelectionRange();
		for (int i = point.x; i < point.x + point.y; i++) {
			StyleRange range = StaticVariable.text.getStyleRangeAtOffset(i);
			if (range != null) {
				StaticVariable.style = (StyleRange) range.clone();
				StaticVariable.style.start = i;
				StaticVariable.style.length = 1;
			} else {
				StaticVariable.style = new StyleRange(i, 1, null, null, SWT.NORMAL);
			}
			// ¼Ó´Ö×ÖÌå
			StaticVariable.style.fontStyle ^= SWT.BOLD;
			StaticVariable.text.setStyleRange(StaticVariable.style);
		}
	}

}
