package quicklab.service;
import javax.swing.*;
public class DialogNotification implements Notification {
    private final String recipient;
    public DialogNotification(String recipient) { this.recipient = recipient; }
    @Override
    public void send(String msg) {
        SwingUtilities.invokeLater(() ->
            JOptionPane.showMessageDialog(null, msg,
                "📬 Notification → " + recipient, JOptionPane.INFORMATION_MESSAGE));
    }
}
