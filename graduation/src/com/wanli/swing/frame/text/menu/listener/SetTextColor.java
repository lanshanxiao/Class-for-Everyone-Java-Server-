package com.wanli.swing.frame.text.menu.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;

import com.wanli.utils.StaticVariable;

public class SetTextColor implements SelectionListener {

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		// 定义颜色选择对话框
		ColorDialog dlg = new ColorDialog(StaticVariable.parent.getShell());
		// 打开对话框
		RGB rgb = dlg.open();
		if (rgb != null) {
			// 定义 color 对象
			StaticVariable.color = new Color(StaticVariable.parent.getShell().getDisplay(), rgb);
			// 定义 point 对象，获取选择范围。
			Point point = StaticVariable.text.getSelectionRange();
			for (int i = point.x; i < point.x + point.y; i++) {
				// 获得选中的字体样式和范围
				StaticVariable.range = StaticVariable.text.getStyleRangeAtOffset(i);
				// 如果字体设置了其他样式( 如加粗、斜体、加下划线)
				if (StaticVariable.range != null) {
					/**
					 * 设置一个与原来 StyleRange 的值相同的 StyleRange
					 */
					StaticVariable.style = (StyleRange) StaticVariable.range.clone();
					StaticVariable.style.start = i;
					StaticVariable.style.length = 1;
					// 设置前景颜色
					StaticVariable.style.foreground = StaticVariable.color;
				} else {

					StaticVariable.style = new StyleRange(i, 1, StaticVariable.color, null, SWT.NORMAL);
				}
				StaticVariable.text.setStyleRange(StaticVariable.style);
			}
		}
	}

}
