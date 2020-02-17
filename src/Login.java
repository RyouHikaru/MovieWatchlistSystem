import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Login extends JFrame implements ActionListener {
    //<editor-fold defaultstate="collapsed" desc="OBJECT AND VARIABLE DECLARATION">
    private final JLabel li;
    private final JLabel un;
    private final JLabel pw;
    private final JLabel logoLabel;
    private final JTextField user;
    private final JPasswordField passw;
    private final JButton loginButton;
    private final JButton cancelButton;
    
    private final JPanel loginPanel;

    private final Font font;
    //</editor-fold>
    public Login() {
        super("Login");
        this.setLayout(null);
        //<editor-fold defaultstate="collapsed" desc="OBJECT INITIIALIZATION">
        li = new JLabel("LOGIN FORM");
        un = new JLabel("Username");
        pw = new JLabel("Password");
        user = new JTextField();
        passw = new JPasswordField();
        loginButton = new JButton("LOGIN");
        cancelButton = new JButton("CANCEL");
        logoLabel = new JLabel(new ImageIcon("MovieDatabaseLogo2.png"));
        font = new Font("Product Sans", Font.PLAIN, 13);
        
        Color color = new Color(98, 189, 178);
        Container mainContainer = this.getContentPane();
        mainContainer.setLayout(null);
        mainContainer.setBackground(color);
        
        loginButton.addActionListener(this);
        cancelButton.addActionListener(this);
        
        li.setFont(new Font("Product Sans",Font.BOLD,15));
        un.setFont(font);
        pw.setFont(font);
        loginButton.setFont(font);
        cancelButton.setFont(font);
        
        li.setBounds(100, 10, 100, 20);
        logoLabel.setBounds(100, 45, 100, 95);

        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="THIS FRAME PROPERTIES">

        this.add(li);
        this.add(logoLabel);
        validate();
        
        loginPanel = new JPanel();
        loginPanel.setBorder(new LineBorder(Color.WHITE, 2));
        loginPanel.setLayout(null);
        loginPanel.setBounds(30, 120, 235, 200);
        
        un.setBounds(25, 20, 65, 15);
        loginPanel.add(un);
        
        user.setBounds(25, 40, 187, 25);
        loginPanel.add(user);
        
        pw.setBounds(25, 80, 65, 15);
        loginPanel.add(pw);
        
        passw.setBounds(25, 100, 187, 25);
        loginPanel.add(passw);
       
        loginButton.setBounds(33, 140, 80, 40);
        loginPanel.add(loginButton);
        
        cancelButton.setBounds(117, 140, 86, 40);
        loginPanel.add(cancelButton);
        
        mainContainer.add(loginPanel);
        
        this.setSize(300, 380);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        //</editor-fold>
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //<editor-fold defaultstate="collapsed" desc="CANCEL">
        if (e.getSource() == cancelButton) {
            JLabel q = new JLabel("Do you want to quit?");
            q.setFont(font);
            int exitOption = JOptionPane.showConfirmDialog(rootPane, q, "Confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (exitOption == 0) {
                this.dispose();
                JLabel a = new JLabel("Have a nice day!");
                a.setFont(font);
                JOptionPane.showMessageDialog(rootPane, a);
                System.exit(exitOption);
            }
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="LOGIN">
        if (e.getSource() == loginButton) {
            //<editor-fold defaultstate="collapsed" desc="try - catch - finally">
            try {
                String host = "jdbc:oracle:thin:@localhost:1521:orcl";
//                String host = "jdbc:derby://localhost:1527/sample";
                String username = user.getText();
                String password = passw.getText();
                Connection con = DriverManager.getConnection(host, username, password);
                this.setVisible(false);
                MainGUI j = new MainGUI(con);
//                SampleGUI j = new SampleGUI(con);
                JLabel a = new JLabel("Login sucess.");
                a.setFont(font);
                JOptionPane.showMessageDialog(rootPane, a);
            }
            catch(SQLException sql) {
                JLabel a = new JLabel(sql.getMessage());
                a.setFont(font);
                JOptionPane.showMessageDialog(rootPane, a, "Authentication Failed", JOptionPane.WARNING_MESSAGE);
            }
            finally {
                user.setText("");
                passw.setText("");
                System.out.println("Connection testing complete.");
            }          
            //</editor-fold>
        }
        //</editor-fold>
    }
}
