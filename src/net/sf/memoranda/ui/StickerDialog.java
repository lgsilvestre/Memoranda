package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.text.DateFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.Local;

/*$Id: StickerDialog.java,v 1.5 2004/10/07 21:31:33 ivanrise Exp $*/
public class StickerDialog extends JDialog {
	public boolean CANCELLED = true;
	JPanel panel1 = new JPanel();
	BorderLayout borderLayout1 = new BorderLayout();
	BorderLayout borderLayout2 = new BorderLayout();
	JButton cancelButton = new JButton();
	JButton okButton = new JButton();
	JPanel bottomPanel = new JPanel();
	JPanel topPanel = new JPanel();
	JLabel header = new JLabel();
	JScrollPane jScrollPane1 = new JScrollPane();
	JPanel jPanelcolor = new JPanel();
	JPanel PanelPriority = new JPanel();
	JPanel ContPanel = new JPanel();
	JTextArea stickerText = new JTextArea();
	JLabel jLabelcolor = new JLabel();
	JLabel jLabelpriority = new JLabel();
	BorderLayout borderLayout3 = new BorderLayout();

	Border border1;
	Border border2;
	Color[] colors =
		{
			Color.YELLOW,
			Color.ORANGE,
			Color.RED,
			Color.BLUE,
			Color.GREEN,
			Color.CYAN,
			Color.MAGENTA,
			Color.PINK };
	String[] priorities = {"Muy Alta","Alta","Media","Baja","Muy Baja"};
	String[] colorLabels =
		{
			Local.getString("Yellow"),
			Local.getString("Orange"),
			Local.getString("Red"),
			Local.getString("Blue"),
			Local.getString("Green"),
			Local.getString("Cyan"),
			Local.getString("Magenta"),
			Local.getString("Pink"),
			Local.getString("Custom")+"..."};
	JComboBox<String> stickerColor = new JComboBox<String>(colorLabels);
	JComboBox<String> priorityList = new JComboBox<String>(priorities);

	public StickerDialog(Frame frame) {
		super(frame, Local.getString("Sticker"), true);
		try {
			jbInit();
			pack();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}

	public StickerDialog() {
		this(null);
	}
	void jbInit() throws Exception {
		stickerColor.setRenderer(new ComboBoxRenderer());
		stickerColor.setMaximumRowCount(9);
		priorityList.setSelectedIndex(2);
		border1 =
			BorderFactory.createCompoundBorder(
				BorderFactory.createEtchedBorder(
					Color.white,
					new Color(156, 156, 158)),
				BorderFactory.createEmptyBorder(5, 5, 5, 5));
		border2 = BorderFactory.createEmptyBorder(5, 0, 5, 0);
		panel1.setLayout(borderLayout1);
		this.getContentPane().setLayout(borderLayout2);
		cancelButton.setMaximumSize(new Dimension(100, 25));
		cancelButton.setMinimumSize(new Dimension(100, 25));
		cancelButton.setPreferredSize(new Dimension(100, 25));
		cancelButton.setText(Local.getString("Cancel"));
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelButton_actionPerformed(e);
			}
		});
		okButton.setMaximumSize(new Dimension(100, 25));
		okButton.setMinimumSize(new Dimension(100, 25));
		okButton.setPreferredSize(new Dimension(100, 25));
		okButton.setText(Local.getString("Ok"));
		okButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okButton_actionPerformed(e);
			}
		});
		this.getRootPane().setDefaultButton(okButton);
		
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.setBorder(new EmptyBorder(new Insets(0, 5, 0, 5)));
		topPanel.setBackground(Color.WHITE);
		//topPanel.setBackground(new Color(215, 225, 250));
		header.setFont(new java.awt.Font("Dialog", 0, 20));
		header.setForeground(new Color(0, 0, 124));
		header.setText(Local.getString("Sticker"));
		header.setIcon(new ImageIcon(net.sf.memoranda.ui.StickerDialog.class.getResource(
            "resources/icons/sticker48.png")));
		//header.setHorizontalAlignment(SwingConstants.LEFT);

		jLabelcolor.setText(Local.getString("Sticker color")+": ");
		jLabelpriority.setText("Prioridad :");
		jPanelcolor.setLayout(borderLayout3);
		ContPanel.setLayout(new GridLayout(2,1));
		PanelPriority.setLayout(new BorderLayout());
		
		panel1.setBorder(border1);
		jPanelcolor.setBorder(border2);
		PanelPriority.setBorder(border2);
		
		PanelPriority.add(jLabelpriority,BorderLayout.WEST);
		PanelPriority.add(priorityList, BorderLayout.CENTER);
		
		
		jPanelcolor.add(jLabelcolor, BorderLayout.WEST);
		jPanelcolor.add(stickerColor, BorderLayout.CENTER);
		
		ContPanel.add(PanelPriority);
		ContPanel.add(jPanelcolor);
		
		getContentPane().add(panel1, BorderLayout.CENTER);
		panel1.add(jScrollPane1, BorderLayout.CENTER);
		jScrollPane1.getViewport().add(stickerText, null);
		
		
		panel1.add(ContPanel, BorderLayout.SOUTH);
		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.add(okButton);
		bottomPanel.add(cancelButton);
		this.getContentPane().add(topPanel, BorderLayout.NORTH);
		topPanel.add(header);
		if (Context.get("STICKER_COLOR") != null) {
			Color c = new Color(new Integer(Context.get("STICKER_COLOR").toString()).intValue());
			stickerText.setBackground(c);
			int i = findColorIndex(c);
			if (i > -1)
				stickerColor.setSelectedIndex(i);
			else
				stickerColor.setSelectedIndex(8);
		}
		else
			stickerText.setBackground(Color.YELLOW);
		stickerText.setWrapStyleWord(true);
		stickerText.setText(
			CalendarDate.today().getLongDateString()
				+ " "
				+ DateFormat.getTimeInstance(
					DateFormat.SHORT,
					Local.getCurrentLocale()).format(
					new java.util.Date())+"\n");
		stickerColor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stickerColor_actionPerformed(e);
			}
		});
		
	}
	
	int getPriority(){
		return priorityList.getSelectedIndex();
	}
	
	int findColorIndex(Color c) {		
		for (int i = 0; i < colors.length; i++)
			if (c.equals(colors[i]))
				return i;
		return -1;
	}

	public String getStickerText() {
		return stickerText.getText();
	}

	public String getStickerColor() {
		return "#"
			+ Integer
				.toHexString(stickerText.getBackground().getRGB() - 0xFF000000)
				.toUpperCase();
	}

	void cancelButton_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	void okButton_actionPerformed(ActionEvent e) {
		CANCELLED = false;
		this.dispose();
	}

	void stickerColor_actionPerformed(ActionEvent e) {
		if (stickerColor.getSelectedIndex() < colors.length)
			stickerText.setBackground(colors[stickerColor.getSelectedIndex()]);
		else {
			Color c =
				JColorChooser.showDialog(
					this,
					Local.getString("Sticker color"),
					stickerText.getBackground());
			if (c != null)
				stickerText.setBackground(c);
		}
		Context.put("STICKER_COLOR", new Integer(stickerText.getBackground().getRGB()));
	}

	class ComboBoxRenderer extends JLabel implements ListCellRenderer {
		public ComboBoxRenderer() {
			setOpaque(true);

		}
		public Component getListCellRendererComponent(
			JList list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {
			/*
			 * if (isSelected) { setBackground(list.getSelectionBackground());
			 * setForeground(list.getSelectionForeground());
			 */
			if ((index > -1) && (index < colors.length))
				setBackground(colors[index]);
			else
				setBackground(list.getBackground());
			setForeground(list.getForeground());
			//}
			setText(value.toString());
			return this;
		}
	}

}