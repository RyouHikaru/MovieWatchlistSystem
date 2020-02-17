import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MainGUI extends JFrame implements ActionListener, MouseListener, InputValidation, Settings{
    //<editor-fold defaultstate="collapsed" desc="OBJECTS AND VARIABLES DECLARATION">
    private final Connection c; // connection object
    private JTable table; // table
    private DefaultTableModel model;  // table model
    private final DefaultTableCellRenderer render;
    private final JScrollPane pane; 
    private Font font;    
    private final JLabel label1;
    private final JLabel label2;
    private final JLabel label3;
    private final JLabel label4;
    private final JLabel label5;
    private final JLabel tip;
    private JLabel l;   // jlabel used for indicating selected row
    private JLabel empty;
    private JLabel clk;
    private final JLabel imgLabel;    // just a labeler for later image
    private JLabel imgLabel2;
    private final JTextField tf1;
    private final JTextField tf2;
    private final JTextField tf3;
    private final JComboBox ratings;
    private final JComboBox status;
    private final JButton add;
    private final JButton delete;
    private final JButton update;
    private final JButton clear;
    private final JButton sort;
    private final JButton reset;
    private final JButton defmode;
    private final JButton edtmode;
    private final JButton logout;
    private final JButton exit;
    private final JButton search;
    private int movieid;    // used to auto increment the movieid in the database
    private int rows;  // used for monitoring the number of rows
    private Object[] data;  // object used to set data in the database
    private Object[][] dataVal; // object used to retrieve the content of database everytime the app starts 
    private boolean emptyList;
    private JMenuBar mb;
    private JMenu settings;
    private JMenu subChangeFont;
    private JMenuItem m1, m2, m3;
    private final Color color;
    private Container mainContainer;
    //</editor-fold>
    public MainGUI(Connection con) {    // Here lies the constructor
        //<editor-fold defaultstate="collapsed" desc="Initialization starts here">
        super("My Movie Watchlist");
        this.setLayout(null);
        c = con;
        l = new JLabel();
        l.setBounds(600, 300, 120, 120);
        data = new Object[6];
        setCurrentMovieId(c);
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="try - catch">
        try {
            String sql = "select * from movie";
            Statement stmt = c.createStatement();
            setDataArray(stmt, sql);
        }
        catch(SQLException s) {
//            System.out.println("Catched");
            System.out.println(s.getMessage());
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="JTABLE PROPERTIES">
        String[] col = {"Movie ID", "Movie Title", "Released Year", "Director", "Personal Ratings", "Status"};
        table = new JTable();
        model = new DefaultTableModel(dataVal, col) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        model.setColumnIdentifiers(col);
        render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(JLabel.CENTER);
        table.addMouseListener(this);
        table.setModel(model);
        table.add(l);   // selected row label
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);    // allows single row selection only
        table.getTableHeader().setReorderingAllowed(false); // disables column reordering
        table.setBackground(Color.LIGHT_GRAY);
        table.setForeground(Color.black);
        font = new Font("", 0, 13);
        table.setFont(font);
        table.setRowHeight(30); 
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="JLABEL PROPERTIES">
        tip = new JLabel("Tip: Double click to view the movie title.");
        label1 = new JLabel("Movie name: ");
        label2 = new JLabel("Released year: ");
        label3 = new JLabel("Director: ");
        label4 = new JLabel("Ratings: ");
        label5 = new JLabel("Status: ");
        tip.setBounds(20, 425, 300, 30);
        label1.setBounds(40, 340, 120, 25);
        label2.setBounds(40, 370, 120, 25);
        label3.setBounds(40, 400, 120, 25);
        label4.setBounds(40, 430, 120, 25);
        label5.setBounds(40, 460, 120, 25);
        label1.setFont(font);
        label2.setFont(font);
        label3.setFont(font);
        label4.setFont(font);
        label5.setFont(font);
        tip.setFont(font);
        setDefaultColumnSettings();
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="JTEXTFIELD PROPERTIES">
        tf1 = new JTextField();
        tf2 = new JTextField();
        tf3 = new JTextField();
        tf1.setBounds(130, 340, 150, 25);
        tf2.setBounds(130, 370, 150, 25);
        tf3.setBounds(130, 400, 150, 25);
        String[] st = {"Watched", "Watching", "Not yet watched"};
        String[] rt = {"","1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        status = new JComboBox(st);
        status.setBounds(130, 460, 150, 25);
        status.setFont(font);
        ratings = new JComboBox(rt);
        ratings.setBounds(130, 430, 150, 25);
        ratings.setFont(font);
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="JBUTTON PROPERTIES">
        add = new JButton("ADD");
        delete = new JButton("DELETE");
        update = new JButton("UPDATE");
        reset = new JButton("RESET");
        clear = new JButton("CLEAR LIST");
        sort = new JButton("SORT");
        edtmode = new JButton("EDIT MODE");
        defmode = new JButton("VIEW MODE");
        logout = new JButton("LOGOUT");
        exit = new JButton("EXIT");
        search = new JButton("FIND MOVIE");
        add.setBounds(300, 350, 100, 25);
        update.setBounds(300, 380, 100, 25);
        delete.setBounds(300, 410, 100, 25);
        reset.setBounds(300, 440, 100, 25);
        clear.setBounds(450, 440, 110, 25);
        sort.setBounds(450, 410, 110, 25);
        edtmode.setBounds(590, 420, 110, 25);
        defmode.setBounds(770, 340, 130, 25);
        logout.setBounds(700, 420, 100, 25);
        exit.setBounds(800, 420, 100, 25);
        search.setBounds(400, 420, 110, 25);
        add.addActionListener(this);
        delete.addActionListener(this);
        update.addActionListener(this);
        reset.addActionListener(this);
        clear.addActionListener(this);
        sort.addActionListener(this);
        edtmode.addActionListener(this);
        defmode.addActionListener(this);
        logout.addActionListener(this);
        exit.addActionListener(this);
        search.addActionListener(this);
        add.setFont(font);
        delete.setFont(font);
        update.setFont(font);
        reset.setFont(font);
        clear.setFont(font);
        sort.setFont(font);
        edtmode.setFont(font);
        defmode.setFont(font);
        logout.setFont(font);
        exit.setFont(font);
        search.setFont(font);
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="JMENU PROPERTIES">
        mb = new JMenuBar();
        settings = new JMenu("Settings");
        subChangeFont = new JMenu("Change font style");
        m1 = new JMenuItem("Times New Roman");
        m2 = new JMenuItem("Calibri");
        m3 = new JMenuItem("Tahoma");
        subChangeFont.add(m1);
        subChangeFont.add(m2);
        subChangeFont.add(m3);
        settings.add(subChangeFont);
        mb.add(settings);
        m1.addActionListener(this);
        m2.addActionListener(this);
        m3.addActionListener(this);
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="OTHER PROPERTIES">
        pane = new JScrollPane(table);
        pane.setBounds(20, 20, 880, 400); // table visible dimensions
        if (emptyList == true) {
            setEmptyMode();
        }
        else {
            empty = null;
            clk = null;
            imgLabel2 = null;
        }
        imgLabel = new JLabel(new ImageIcon("MovieDatabaseLogo2.png"));
        imgLabel.setBounds(790, 365, 100, 105);
        color = new Color(98, 189, 178);
        mainContainer = this.getContentPane();
        mainContainer.setLayout(null);
        mainContainer.setBackground(color);
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="THIS FRAME PROPERTIES">
        this.add(imgLabel);
        this.add(pane);
        this.add(tip);
        this.add(label1);
        this.add(label2);
        this.add(label3);
        this.add(label4);
        this.add(label5);
        this.add(tf1);
        this.add(tf2);
        this.add(tf3);
        this.add(ratings);
        this.add(status);
        this.add(add);
        this.add(delete);
        this.add(update);
        this.add(reset);
        this.add(clear);
        this.add(sort);
        this.add(edtmode);
        this.add(defmode);
        this.add(logout);
        this.add(exit);
        this.add(search);
        this.add(l);
        this.setJMenuBar(mb);
        setDefaultMode();
        this.setLocationRelativeTo(this);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //</editor-fold>
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //<editor-fold defaultstate="collapsed" desc="EXIT">
        if (e.getSource() == exit) {
            JLabel a = new JLabel("Do you want to quit?");
            a.setFont(font);
            int exitOption = JOptionPane.showConfirmDialog(rootPane, a, "Exit confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (exitOption == 0) {
                try {
                    c.close();
                    this.dispose();
                    JLabel b = new JLabel("Have a nice day!");
                    b.setFont(font);
                    JOptionPane.showMessageDialog(rootPane, b);
                    System.exit(exitOption);
                }
                catch(SQLException s) {
                    System.out.println(s.getMessage());
                }
            }
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="LOGOUT">
        else if (e.getSource() == logout) {
            JLabel a = new JLabel("Do you want to logout?");
            a.setFont(font);
            int logoutOption = JOptionPane.showConfirmDialog(rootPane, a, "Logout confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (logoutOption == 0) {
                try {
                    c.close();
                    this.dispose();
                    new Login();
                    JLabel b = new JLabel("Logout success.");
                    b.setFont(font);
                    JOptionPane.showMessageDialog(edtmode, b);
                }
                catch(SQLException s) {
                    JLabel b = new JLabel(s.getMessage());
                    b.setFont(font);
                    JOptionPane.showMessageDialog(edtmode, b);
                }
            }
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="SEARCH">
        else if (e.getSource() == search) {
            String s = null;
            JLabel b = new JLabel("Please enter Movie Title");
            b.setFont(font);
            s = JOptionPane.showInputDialog(rootPane, b);
            if (s == null) {
                return;
            }
            else if (checkDuplicateInList(s) == true) {
                String details = Integer.toString(getMovieID(s));
                JLabel a = new JLabel("Movie ID is: " + (details));
                a.setFont(font);
                JOptionPane.showMessageDialog(rootPane, a, "Displaying details", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JLabel a = new JLabel("Movie not found.");
                a.setFont(font);
                JOptionPane.showMessageDialog(rootPane, a, "Displaying details", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="EDIT MODE">
        else if (e.getSource() == edtmode) {
            JLabel a = new JLabel("Switch to edit mode?");
            a.setFont(font);
            int option = JOptionPane.showConfirmDialog(rootPane, a, "Edit mode confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == 0) {
                setEditMode();
                JLabel b = new JLabel("Switched successfully.");
                b.setFont(font);
                JOptionPane.showMessageDialog(rootPane, b);
            }
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="VIEW MODE">
        else if (e.getSource() == defmode) {
            JLabel a = new JLabel("Switch to view mode?");
            a.setFont(font);
            int option = JOptionPane.showConfirmDialog(rootPane, a, "View mode confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == 0) {
                setDefaultMode();
                if (emptyList == true) {
                    setEmptyMode();
                }
                JLabel b = new JLabel("Switched successfully.");
                b.setFont(font);
                JOptionPane.showMessageDialog(rootPane, b);
            }
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="ADD">
        else if (e.getSource() == add) { // ADD
            //<editor-fold defaultstate="collapsed" desc="try - catch">
            try {
                if (tf1.getText().isEmpty()) {  // checking if movie name is empty
                    JLabel a = new JLabel("Movie name can't be empty.");
                    a.setFont(font);
                    JOptionPane.showMessageDialog(rootPane, a, "Invalid Movie Name", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (isValidMovieName(tf1.getText()) == false) {  // checking movie name if having apostrophe
                    throw new InvalidMovieNameException();
                }
                if (isValidYear(tf2.getText()) == false) {  // checking year
                    throw new InvalidYearException();
                }
                if (isValidDirName(tf3.getText()) == false) {
                    throw new InvalidDirectorNameException();
                }
                data[1] = tf1.getText();
                data[2] = tf2.getText();
                data[3] = tf3.getText();
                data[4] = ratings.getSelectedItem();
                data[5] = status.getSelectedItem();
                setCurrentMovieId(c);
                data[0] = movieid;
            }
            catch(InvalidMovieNameException in) {
                JLabel a = new JLabel(in.getMessage());
                a.setFont(font);
                JOptionPane.showMessageDialog(rootPane, a, "Invalid Movie Name", JOptionPane.ERROR_MESSAGE);
                return;
            }
            catch(InvalidYearException iy) {
                JLabel a = new JLabel(iy.getMessage());
                a.setFont(font);
                JOptionPane.showMessageDialog(rootPane, a, "Invalid Year", JOptionPane.ERROR_MESSAGE);
                return;
            }
            catch(InvalidDirectorNameException id) {
                JLabel a = new JLabel(id.getMessage());
                a.setFont(font);
                JOptionPane.showMessageDialog(rootPane, a, "Invalid Director Name", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //</editor-fold>
            JLabel a = new JLabel("Confirm to add these values?");
            a.setFont(font);
            int confirmation = JOptionPane.showConfirmDialog(rootPane, a, "Add data confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            //<editor-fold defaultstate="collapsed" desc="if confirmed">
            if (confirmation == 0) { // if ok is pressed
                try {
                    if (checkDuplicateInList(tf1.getText()) == true) {
                        JLabel abc = new JLabel("Movie already exists. You sure to add again?");
                        abc.setFont(font);
                        int dupConfirmation = JOptionPane.showConfirmDialog(rootPane, abc, "Existing Movie", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                        System.out.println(dupConfirmation);
                        if (dupConfirmation == 2) 
                            return;
                    }
                    model.addRow(data);
                    Statement stmt = c.createStatement();
                    String sql = addStatement(tf1.getText(), tf2.getText(), tf3.getText(), (String) ratings.getSelectedItem(), (String) status.getSelectedItem());
                    ResultSet rs = stmt.executeQuery(sql);
                    JLabel b = new JLabel("1 row added.");
                    b.setFont(font);
                    JOptionPane.showMessageDialog(rootPane, b);
                    emptyList = false;
                }
                catch(SQLException s) {
                    System.out.println(s.getMessage());
                }
            }
            //</editor-fold>
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="DELETE">
        else if (e.getSource() == delete) { // DELETE
            int i = table.getSelectedRow();
            //<editor-fold defaultstate="collapsed" desc="if - else">
            if (i >= 0) {   // if there is selected row
                int selectedid = Integer.parseInt(table.getValueAt(i, 0).toString());
                System.out.println("Selected id: " + selectedid);
                JLabel a = new JLabel("Are you sure to delete the selected row?");
                a.setFont(font);
                int confirmation = JOptionPane.showConfirmDialog(rootPane, a, "Delete data confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirmation == 0) {
                    try {
                        model.removeRow(i);
                        Statement stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery(delStatement(selectedid));
                        JLabel b = new JLabel("1 row deleted.");
                        b.setFont(font);
                        JOptionPane.showMessageDialog(rootPane, b);
                        if (model.getRowCount() == 0) {
                            emptyList = true;
                        }
                    }
                    catch(SQLException s) {
                        System.out.println(s.getMessage());        
                    }
                }
            }
            else {  // if there is no row selected
                if (emptyList == true) {
                    JLabel a = new JLabel("Watchlist is empty. Nothing to delete.");
                    a.setFont(font);
                    JOptionPane.showMessageDialog(rootPane, a);
                    return;
                }
                JLabel a = new JLabel("Please select row to be deleted.");
                a.setFont(font);
                JOptionPane.showMessageDialog(rootPane, a);       
            }
            //</editor-fold>
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="UPDATE">
        else if (e.getSource() == update) {
            int i = table.getSelectedRow();
            //<editor-fold defaultstate="collapsed" desc="if - else">
            if (i >= 0) {   // if there is selected row
                int selectedid = Integer.parseInt(table.getValueAt(i, 0).toString());
//                System.out.println("Selected id: " + selectedid);
                JLabel ab = new JLabel("Confirm to update selected row?");
                ab.setFont(font);
                int confirmation = JOptionPane.showConfirmDialog(rootPane, ab, "Update data confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                //<editor-fold defaultstate="collapsed" desc="if confirmed">
                if (confirmation == 0) {
                    //<editor-fold defaultstate="collapsed" desc="try - catch">
                    try {
                        if (tf1.getText().isEmpty()) {  // checking if movie name is empty
                            JLabel b = new JLabel("Movie name can't be empty.");
                            b.setFont(font);
                            JOptionPane.showMessageDialog(rootPane, b, "Invalid Movie Name", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (isValidMovieName(tf1.getText()) == false) {  // checking movie name if having apostrophe
                            throw new InvalidMovieNameException();
                        }
                        if (isValidYear(tf2.getText()) == false) {  // checking released year
                            throw new InvalidYearException();
                        }
                        if (isValidDirName(tf3.getText()) == false) {
                            throw new InvalidDirectorNameException();
                        }
                        model.setValueAt(tf1.getText(), i, 1);
                        model.setValueAt(tf2.getText(), i, 2);
                        model.setValueAt(tf3.getText(), i, 3);
                        model.setValueAt(ratings.getSelectedItem(), i, 4);
                        model.setValueAt(status.getSelectedItem(), i, 5);
                        Statement stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery(upStatement(selectedid, tf1.getText(), tf2.getText(), tf3.getText(), (String) ratings.getSelectedItem(), (String) status.getSelectedItem()));
                        JLabel cd = new JLabel("1 row updated.");
                        cd.setFont(font);
                        JOptionPane.showMessageDialog(rootPane, cd);
                    }
                    catch(SQLException s) {
                        System.out.println("SQLException at Update: " + s.getMessage());
                    }
                    catch(InvalidMovieNameException in) {
                        JLabel a = new JLabel(in.getMessage());
                        a.setFont(font);
                        JOptionPane.showMessageDialog(rootPane, a, "Invalid Movie Name", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    catch(InvalidYearException iy) {
                        JLabel a = new JLabel(iy.getMessage());
                        a.setFont(font);
                        JOptionPane.showMessageDialog(rootPane, a, "Invalid Year", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    catch(InvalidDirectorNameException id) {
                        JLabel a = new JLabel(id.getMessage());
                        a.setFont(font);
                        JOptionPane.showMessageDialog(rootPane, a, "Invalid Year", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    //</editor-fold>
                }
                //</editor-fold>
            }
            else{
                if (emptyList == true) {
                    JLabel a = new JLabel("Watchlist is empty. Nothing to update.");
                    a.setFont(font);
                    JOptionPane.showMessageDialog(rootPane, a);
                    return;
                }
                JLabel a = new JLabel("Please select row to be updated.");
                a.setFont(font);
                JOptionPane.showMessageDialog(rootPane, a);
            }
            //</editor-fold>
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="RESET FIELD">
        else if (e.getSource() == reset) {
            tf1.setText("");
            tf2.setText("");
            tf3.setText("");
            JLabel a = new JLabel("Text field cleared.");
            a.setFont(font);
            JOptionPane.showMessageDialog(rootPane, a);
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="CLEAR LIST">
        else if (e.getSource() == clear) {
            JLabel a = new JLabel("Are you sure to delete all?");
            a.setFont(font);
            int confirmation = JOptionPane.showConfirmDialog(rootPane, a, "Clear list confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirmation == 0) {
                //<editor-fold defaultstate="collapsed" desc="try - catch">
                String finalConfirmation = null;
                JLabel cd = new JLabel("Please type the word \"DELETE\" to confirm");
                cd.setFont(font);
                finalConfirmation = JOptionPane.showInputDialog(rootPane, cd, "Clear list final confirmation", JOptionPane.WARNING_MESSAGE);
                //<editor-fold defaultstate="collapsed" desc="if final confirmed">
                if (finalConfirmation == null) {
                    JLabel d = new JLabel("Deletion cancelled.");
                    d.setFont(font);
                    JOptionPane.showMessageDialog(rootPane, d);
                }
                else if (finalConfirmation.equals("DELETE")) {
                    try {
                        Statement stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery(delStatement());
                        int i = table.getRowCount() - 1;
                        if (i == -1) {
                            JLabel d = new JLabel("List is already empty.");
                            d.setFont(font);
                            JOptionPane.showMessageDialog(rootPane, d);
                            return;
                        }
                        while(i >= 0) {
                            model.removeRow(i);
                            i--;
                        }
                        emptyList = true;
                        JLabel el = new JLabel("Watchlist is cleared.");
                        el.setFont(font);
                        JOptionPane.showMessageDialog(rootPane, el);
                    }
                    catch(SQLException s) {
                        System.out.println(s.getMessage());
                    }
                }
                else {
                    JLabel ab = new JLabel("Invalid confirmation.");
                    JLabel bc = new JLabel("Deletion cancelled.");
                    ab.setFont(font);
                    bc.setFont(font);
                    JOptionPane.showMessageDialog(rootPane, ab, "Confirmation", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(rootPane, bc);
                }
                //</editor-fold>
                //</editor-fold>
            }
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="SORT LIST">
        else if (e.getSource() == sort) {
            JLabel a = new JLabel("Sort the list?");
            a.setFont(font);
            int sortConfirmation = JOptionPane.showConfirmDialog(rootPane, a, "Sort list confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            //<editor-fold defaultstate="collapsed" desc="if confirmed">
            if (sortConfirmation == 0) {
                //<editor-fold defaultstate="collapsed" desc="try - catch">
                if (emptyList == true) {
                    JLabel b = new JLabel("Watchlist is empty. Nothing to sort.");
                    b.setFont(font);
                    JOptionPane.showMessageDialog(rootPane, b);
                    return;
                }
                Object[][] curData = null;
                try {
                    Statement stmt = c.createStatement();
                    curData = setDataArray(stmt, sortStatement(), curData);
                }
                catch(SQLException s) {
                    System.out.println("Catched");
                    System.out.println(s.getMessage());
                    System.out.println(s.getCause());
                }
                //</editor-fold>
                String[] col = {"Movie ID", "Movie Title", "Released Year", "Director", "Personal Ratings", "Status"};
                DefaultTableModel sortedModel;
                sortedModel = new DefaultTableModel(curData, col) {
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                };
                sortedModel.setColumnIdentifiers(col);
                model = sortedModel;
                table.setModel(model);
                setDefaultColumnSettings(table);
                JLabel b = new JLabel("Watchlist sorted.");
                b.setFont(font);
                JOptionPane.showMessageDialog(rootPane, b);
            }
            //</editor-fold>
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="CHANGE FONT">
        else if (e.getSource() == m1) {
            font = new Font("Times New Roman", 0, 13);
            setFont();
        }
        else if (e.getSource() == m2) {
            font = new Font("Calibri", 0, 13);
            setFont();
        }
        else if (e.getSource() == m3) {
            font = new Font("Tahoma", 0, 13);
            setFont();
        }
        //</editor-fold>
    }
    public String addStatement(String mn, String ry, String dir, String pr, String s) { // this method is used to generate the insert statement
        String sql = "insert into movie values (" + (movieid) + 
                ", '" + (mn) + "', '" + (ry) + 
                "', '" + (dir) + "', '" + (pr) + 
                "', '" + (s) + "')";
        System.out.println(sql);
        return sql;
    }
    public String delStatement(int selectedrow) {   // this method is used to generate the delete statement
        String sql = "delete from movie where movieid = " + (selectedrow);
        System.out.println(sql);
        return sql;
    }
    public String delStatement() {  // this method generates the delete all statement
        return "delete from movie";
    }
    public String upStatement(int selectedid, String mn, String ry, String dir, String pr, String s) {  // this method is used to generate the update statement
        String sql = "update movie set moviename = '" + (mn) + "', releasedyear = '" + (ry) + 
                "', director = '" + (dir) + "', personalratings = '" + (pr) + 
                "', status = '" + (s) + "' where movieid = " + (selectedid);
        System.out.println(sql);
        return sql;
    }
    public String sortStatement() { // this method is used to generate the order by moviename statement
        return "select * from movie order by moviename";
    }
    public void setDataArray(Statement stmt, String sql) {  // this method sets the Object[][] data for the initial viewing of the table
        //<editor-fold defaultstate="collapsed" desc="try - catch">
        try {
            ResultSet getRows = stmt.executeQuery(sql);
            while(getRows.next()) {
                rows++;
            }
//            System.out.println("rows: " + rows);
            if (rows == 0)
                emptyList = true;
            ResultSet rs = stmt.executeQuery(sql);
            dataVal = new Object[rows][6];
            
            for (int i = 0; i < rows; i++) {
                rs.next();
                for (int j = 0; j < 6; j++) {
                    dataVal[i][j] = rs.getString(j + 1);
                }
            }
//                         CHECKER loop
//            for (int i = 0; i < rows; i++) {
//                for (int j = 0; j < 6; j++) {
//                    System.out.print(dataVal[i][j] + "  ");
//                }
//                System.out.println("");
//            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        //</editor-fold>
    }  
    public Object[][] setDataArray(Statement stmt, String sql, Object[][] curData) {    // this is an overload of the setDataArray method. It is used for retrieveing  sorted data.
        //<editor-fold defaultstate="collapsed" desc="try - catch">
        try {
            int rows = 0;
            ResultSet getRows = stmt.executeQuery(sql);
            while(getRows.next()) {
                rows++;
            }
//            System.out.println("rows: " + rows);
            ResultSet rs = stmt.executeQuery(sql);
            curData = new Object[rows][6];
            
            for (int i = 0; i < rows; i++) {
                rs.next();
                for (int j = 0; j < 6; j++) {
                    curData[i][j] = rs.getString(j + 1);
                }
            }
//                         CHECKER loop
//            for (int i = 0; i < rows; i++) {
//                for (int j = 0; j < 6; j++) {
//                    System.out.print(curData[i][j] + "  ");
//                }
//                System.out.println("");
//            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        } 
        //</editor-fold>
         return curData;
    }
    public final void setCurrentMovieId(Connection x) {   // this method increments the movieid accordingly
        //<editor-fold defaultstate="collapsed" desc="try - catch">
        try {
            Statement stmt = x.createStatement();
            String sql = "select * from movie order by movieid";
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                movieid = rs.getInt("movieid") + 1;
            }
            if (movieid == 0) {
                movieid++;
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
//</editor-fold>
    }
    public boolean checkDuplicateInList(String mn) {    // this method checks if there is an existing movie title in the database
        //<editor-fold defaultstate="collapsed" desc="try - catch">
        try {
            ArrayList<String> titles = new ArrayList();
            Statement stmt = c.createStatement();
            String sql = "select moviename from movie";
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {  // adding list of movienames at the arraylist titles
                titles.add(rs.getString("moviename"));
            }
            
            System.out.println(titles);
            
            for (String title : titles) {   // checking if it is existing already
                if (mn.equals(title)) {
                    return true;
                }
            }
        }
        catch(SQLException s) {
            System.out.println(s.getMessage());
        }
//</editor-fold>
        return false;
    }
    public int getMovieID(String mn) {  // this method returns the movie id of the movie title searched
        try {
            String sql = "select movieid from movie where moviename = " + "'" + (mn) + "'";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                return(rs.getInt("movieid"));
            }
        }
        catch(SQLException s) {
            System.out.println(s.getMessage());
        }
        return 0;
    }
    //<editor-fold defaultstate="collapsed" desc="SETTINGS">
    @Override
    public void setDefaultColumnSettings() {    // this method sets the column settings of the table
        for (int i = 0; i < 6; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(render);
            if (i == 0) {
                table.getColumnModel().getColumn(i).setPreferredWidth(20);
            }
            else if (i == 1) {
                table.getColumnModel().getColumn(i).setPreferredWidth(170);
            }
            else if (i == 3) {
                table.getColumnModel().getColumn(i).setPreferredWidth(140);
            }
        }
    }
    public void setDefaultColumnSettings(JTable sortedTable) {  // this is an overload of the setDefaultColumnSettings method. This is used for the sorted table.
        for (int i = 0; i < 6; i++) {
            sortedTable.getColumnModel().getColumn(i).setCellRenderer(render);
            if (i == 0) {
                sortedTable.getColumnModel().getColumn(i).setPreferredWidth(20);
            }
            else if (i == 1) {
                sortedTable.getColumnModel().getColumn(i).setPreferredWidth(170);
            }
            else if (i == 3) {
                 sortedTable.getColumnModel().getColumn(i).setPreferredWidth(140);
            }
        }
    }
    @Override
    public void setEditMode() { // sets the current screen into edit mode
        if (empty != null && clk != null && imgLabel2 != null) {
            empty.setVisible(false);
            clk.setVisible(false);
            imgLabel2.setVisible(false);
        }
        search.setBounds(450, 380, 110, 25);
        search.setVisible(true);
        tip.setVisible(false);
        imgLabel.setVisible(true);
        pane.setVisible(true);
        pane.setBounds(20, 20, 880, 300);
        label1.setVisible(true);
        label2.setVisible(true);
        label3.setVisible(true);
        label4.setVisible(true);
        label5.setVisible(true);
        tf1.setVisible(true);
        tf2.setVisible(true);
        tf3.setVisible(true);
        ratings.setVisible(true);
        status.setVisible(true);
        add.setVisible(true);
        delete.setVisible(true);
        update.setVisible(true);
        reset.setVisible(true);
        clear.setVisible(true);
        sort.setVisible(true);
        defmode.setVisible(true);
        edtmode.setVisible(false);
        logout.setBounds(700, 475, 100, 25);
        exit.setBounds(800, 475, 100, 25);
        l.setVisible(true);
        this.setSize(932, 580);
    }
    @Override
    public void setDefaultMode() {  // sets the current screen into view mode. This is the default settings.
        if (imgLabel2 != null && emptyList == false) {
            imgLabel2.setVisible(false);
        }
        if (emptyList == true) {
            tip.setVisible(false);
            search.setVisible(false);
        }
        else {
            tip.setVisible(true);
            search.setVisible(true);
        }
        search.setBounds(400, 420, 110, 25);
        imgLabel.setVisible(false);
        pane.setBounds(20, 20, 880, 380);
        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);
        label5.setVisible(false);
        tf1.setVisible(false);
        tf2.setVisible(false);
        tf3.setVisible(false);
        ratings.setVisible(false);
        status.setVisible(false);
        add.setVisible(false);
        delete.setVisible(false);
        update.setVisible(false);
        reset.setVisible(false);
        clear.setVisible(false);
        sort.setVisible(false);
        defmode.setVisible(false);
        edtmode.setVisible(true);
        logout.setBounds(700, 420, 100, 25);
        exit.setBounds(800, 420, 100, 25);
        l.setVisible(false);
        this.setSize(932, 550);
    }
    @Override
    public void setEmptyMode() {    // sets the screen into empty mode if list is empty
        pane.setVisible(false);
        tip.setVisible(false);
        empty = new JLabel("List is empty. Start adding movies!");
        clk = new JLabel("Go to edit mode");
        imgLabel2 = new JLabel(new ImageIcon("MDL.png"));
        empty.setBounds(370, 180, 500, 80);
        clk.setBounds(520, 220, 500, 80);
        imgLabel2.setBounds(145, 115, 200, 210);
        empty.setFont(new Font("Times New Roman", Font.ITALIC, 30));
        clk.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        this.add(imgLabel2);
        this.add(empty);
        this.add(clk);
    }
    public void setFont() {
        table.setFont(font);
        label1.setFont(font);
        label2.setFont(font);
        label3.setFont(font);
        label4.setFont(font);
        label5.setFont(font);
        tip.setFont(font);
        ratings.setFont(font);
        status.setFont(font);
        settings.setFont(font);
        subChangeFont.setFont(font);
        m1.setFont(font);
        m2.setFont(font);
        m3.setFont(font);
        add.setFont(font);
        delete.setFont(font);
        update.setFont(font);
        reset.setFont(font);
        clear.setFont(font);
        sort.setFont(font);
        edtmode.setFont(font);
        defmode.setFont(font);
        logout.setFont(font);
        exit.setFont(font);
        search.setFont(font);
        JLabel a = new JLabel("Font changed successfully!");
        a.setFont(font);
        JOptionPane.showMessageDialog(rootPane, a, "Font settings", JOptionPane.INFORMATION_MESSAGE);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="INPUTVALIDATION METHODS">
    @Override
    public boolean isValidYear(String yr) { // this method checks the validity of released year input
        //<editor-fold defaultstate="collapsed" desc="method algorithm">
        boolean valid = true;
        if (yr.isEmpty())
            return true;
        if (yr.length() == 4) {
//            System.out.println("receieved yr length: " + yr.length());
            for (int i = 0; i < yr.length(); i++) {
                if (yr.charAt(i) >= 48 && yr.charAt(i) <= 57) {
                }
                else {
                    valid = false;
                    return valid;
                }
            }
            int year = Integer.parseInt(yr);
            
            if (year >= 1900 && year <= 2100) {
            }
            else {
                valid = false;
            }
        }
        else {
            valid = false;
        }
//        System.out.println("Year is valid: " + valid);
        return valid;
        //</editor-fold>
    }
    @Override
    public boolean isValidMovieName(String mn) {
        int whitespaceCount = 0;
        for (int i = 0; i < mn.length(); i++) { // counting number of singlequotes
            if (mn.charAt(i) == 39) {
//                System.out.println("Returning false cause ' detected");
                return false;
            }
            else if (mn.charAt(i) == 32) {
//                System.out.println("whitespace");
                whitespaceCount++;
            }
        }
//        System.out.println("Returning true");
        return !(whitespaceCount == mn.length());
    }
    @Override
    public boolean isValidDirName(String dir) {
        int whitespaceCount = 0;
        for (int i = 0; i < dir.length(); i++) { // counting number of singlequotes
            if (dir.charAt(i) == 32) {
//                System.out.println("whitespace");
                whitespaceCount++;
            }
            else if (dir.charAt(i) == 39)
                return false;
        }
//        System.out.println(whitespaceCount == dir.length() || dir.isEmpty());
        return (!(whitespaceCount == dir.length()) || dir.isEmpty());
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="MOUSELISTENER METHODS">
    @Override
    public void mouseClicked(MouseEvent e) {    // used to monitor clicked space. It is used in the table.
        int i = table.getSelectedRow();
        l.setText("Selected row: " + (i + 1));
        l.setFont(font);
        if (e.getClickCount() == 2) {   // view movie title upon double clicking
            //<editor-fold defaultstate="collapsed" desc="try - catch">
            try {
                int selectedid = Integer.parseInt(table.getValueAt(i, 0).toString());
//                System.out.println("Selected id: " + selectedid);
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("select * from movie where movieid = " + selectedid);
                
                JLabel movielabel;
                
                while(rs.next()) {
                    movielabel = new JLabel(rs.getString("moviename"));
                    movielabel.setFont(font);
                    JOptionPane.showMessageDialog(rootPane, movielabel, "Movie title", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            catch(SQLException s) {
                System.out.println(s.getMessage());
            }
            catch(ArrayIndexOutOfBoundsException a) {
                System.out.println(a.getMessage());
            }
            //</editor-fold>
        }
    }
    @Override
    public void mouseExited(MouseEvent e) { // unused
    }
    @Override
    public void mousePressed(MouseEvent e) {  // unused
    }
    @Override
    public void mouseReleased(MouseEvent e) {  // unused
    }
    @Override
    public void mouseEntered(MouseEvent e) {  // unused
    }
    //</editor-fold>
}