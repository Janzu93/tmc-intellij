package fi.helsinki.cs.tmc.intellij.ui.courseselection;

import com.intellij.ui.components.JBScrollPane;
import fi.helsinki.cs.tmc.core.domain.Course;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class CourseCard extends javax.swing.JPanel {

    private Course course;

    /** Creates new form CourseCard */
    public CourseCard(Course course) {
        initComponents();

        this.course = course;

        DefaultCaret caret = (DefaultCaret) this.informationLabel.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

        this.titleLabel.setText(course.getTitle());
        String information = course.getDescription();
        if (information.length() > 100) {
            information = information.substring(0, 99) + "...";
        }
        this.informationLabel.setText(information);
        this.informationLabel.setRows(2);
        final String text = this.informationLabel.getText();
        if (text.length() > 3) {
            this.informationLabel.replaceRange("...", text.length() - 3, text.length());
        }
        this.nameLabel.setText("/" + course.getName());
    }

    public Course getCourse() {
        return this.course;
    }

    public void setColors(Color foreground, Color background) {
        this.setBackground(background);
        this.titleLabel.setForeground(foreground);
        this.nameLabel.setForeground(foreground);
        this.infoScrollPane.setBackground(background);
        this.informationLabel.setForeground(foreground);
        this.informationLabel.setBackground(background);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new JLabel();
        nameLabel = new JLabel();
        infoScrollPane = new JBScrollPane();
        informationLabel = new JTextArea();

        setBackground(new Color(255, 255, 255));
        setMaximumSize(new Dimension(332, 73));
        setMinimumSize(new Dimension(332, 73));
        setPreferredSize(new Dimension(346, 107));

        titleLabel.setFont(new Font("Ubuntu", 1, 18)); // NOI18N

        nameLabel.setForeground(new Color(150, 150, 150));

        infoScrollPane.setBackground(new Color(255, 255, 255));
        infoScrollPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        infoScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        infoScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        infoScrollPane.setViewportBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        infoScrollPane.setHorizontalScrollBar(null);
        infoScrollPane.setPreferredSize(new Dimension(106, 30));

        informationLabel.setEditable(false);
        informationLabel.setBackground(new Color(255, 255, 255));
        informationLabel.setLineWrap(true);
        informationLabel.setWrapStyleWord(true);
        informationLabel.setAutoscrolls(false);
        informationLabel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        informationLabel.setMaximumSize(null);
        infoScrollPane.setViewportView(informationLabel);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(
                                                layout.createParallelGroup(
                                                                GroupLayout.Alignment.LEADING)
                                                        .addGroup(
                                                                layout.createSequentialGroup()
                                                                        .addComponent(titleLabel)
                                                                        .addPreferredGap(
                                                                                LayoutStyle
                                                                                        .ComponentPlacement
                                                                                        .RELATED,
                                                                                161,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(nameLabel))
                                                        .addComponent(
                                                                infoScrollPane,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE))
                                        .addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(
                                                layout.createParallelGroup(
                                                                GroupLayout.Alignment.BASELINE)
                                                        .addComponent(titleLabel)
                                                        .addComponent(nameLabel))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(
                                                infoScrollPane,
                                                GroupLayout.PREFERRED_SIZE,
                                                40,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(
                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    } // </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane infoScrollPane;
    private javax.swing.JTextArea informationLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
