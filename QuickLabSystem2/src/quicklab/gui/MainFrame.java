package quicklab.gui;

import quicklab.QuickLabSystem;
import quicklab.model.*;
import quicklab.service.DialogNotification;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class MainFrame extends JFrame {

   
    static final Color BG       = new Color(0x0D0D14);
    static final Color SURFACE  = new Color(0x13131E);
    static final Color SURFACE2 = new Color(0x1A1A2A);
    static final Color BORDER   = new Color(0x2A2A42);
    static final Color ACCENT   = new Color(0x6C63FF);
    static final Color GREEN    = new Color(0x43E97B);
    static final Color RED      = new Color(0xFF6584);
    static final Color GOLD     = new Color(0xF7C948);
    static final Color TEXT     = new Color(0xE8E8F0);
    static final Color MUTED    = new Color(0x6B6B8A);

  
    final QuickLabSystem system = new QuickLabSystem();
    User currentUser;
    JPanel rootPanel;
    CardLayout cardLayout;
    JTabbedPane tabbedPane;

    public MainFrame() {
        setTitle("QuickLab Library System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1150, 750);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        seedData();
        cardLayout = new CardLayout();
        rootPanel  = new JPanel(cardLayout);
        rootPanel.setBackground(BG);
        rootPanel.add(buildLoginPanel(), "login");
        
        rootPanel.add(new JPanel(), "main");  
        add(rootPanel);
        cardLayout.show(rootPanel, "login");
        setVisible(true);
    }

  
    private void seedData() {
        system.addUser(new Librarian(1, "admin", "admin123", 1001));
        system.addUser(new Member(2, "emre", "emre123"));
        system.addUser(new Member(3, "semih",   "semih123"));
        system.addUser(new Member(4, "kaan", "kaan123"));

        Object[][] books = {
            {"Crime and Punishment",        "Fyodor Dostoevsky",   "Classic",
             "A psychological novel about a young man who commits murder and grapples with guilt and redemption.",
             new Color(0x8B1A1A), "[!]"},
            {"War and Peace",               "Leo Tolstoy",         "Historical",
             "An epic narrative of Russian society during the Napoleonic Wars, following five aristocratic families.",
             new Color(0x1A3A6B), "[W]"},
            {"The Little Prince",           "Antoine de Saint-Exupery","Fantasy",
             "A poetic tale about a young prince who visits various planets, exploring loneliness, friendship and love.",
             new Color(0x1A6B4A), "[P]"},
            {"1984",                        "George Orwell",       "Dystopia",
             "A chilling vision of a totalitarian society where Big Brother watches every move you make.",
             new Color(0x2A2A2A), "[E]"},
            {"The Great Gatsby",            "F. Scott Fitzgerald", "Classic",
             "A portrait of the Jazz Age in America, exploring themes of wealth, class, love and idealism.",
             new Color(0x6B5A1A), "[G]"},
            {"To Kill a Mockingbird",       "Harper Lee",          "Drama",
             "A story of racial injustice and childhood innocence in the American South during the 1930s.",
             new Color(0x3A5A1A), "[J]"},
            {"The Hitchhiker's Guide",      "Douglas Adams",       "Sci-Fi",
             "A comedic sci-fi adventure following Arthur Dent after Earth is demolished for a hyperspace bypass.",
             new Color(0x1A4A6B), "[S]"},
            {"Pride and Prejudice",         "Jane Austen",         "Romance",
             "A witty romantic novel exploring love, marriage and social class in 19th-century England.",
             new Color(0x6B1A5A), "[R]"},
            {"The Alchemist",               "Paulo Coelho",        "Philosophy",
             "A philosophical novel about a young Andalusian shepherd on a journey to find his personal legend.",
             new Color(0x6B3A1A), "[*]"},
            {"Brave New World",             "Aldous Huxley",       "Dystopia",
             "A dystopian novel set in a futuristic World State where citizens are engineered and conditioned.",
             new Color(0x1A5A6B), "[B]"},
            {"The Catcher in the Rye",      "J.D. Salinger",       "Coming-of-Age",
             "A novel about teenage alienation and loss of innocence told through the eyes of Holden Caulfield.",
             new Color(0x5A1A1A), "[T]"},
            {"One Hundred Years of Solitude","Gabriel Garcia Marquez","Magical Realism",
             "The Buendia family's multi-generational story in the fictional town of Macondo.",
             new Color(0x4A1A6B), "[M]"},
        };

        for (Object[] b : books) {
            system.addBook(new Book(system.nextBookID(),
                (String)b[0], (String)b[1], (String)b[2],
                (String)b[3], (Color)b[4], (String)b[5]));
        }

       
        Book b1 = system.getBooks().get(0);
        Review r = new Review(system.nextReviewID(), b1.getBookID(), 2, "alice",
                "A profound psychological masterpiece. The internal monologue is breathtaking.", 5, false);
        b1.addReview(r);
        system.addReview(r);
    }

   
    private JPanel buildLoginPanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG);

        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(SURFACE);
                g2.fill(new RoundRectangle2D.Float(0,0,getWidth(),getHeight(),20,20));
                g2.dispose();
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(44, 52, 44, 52));
        card.setPreferredSize(new Dimension(400, 420));

        JLabel ico = lbl("[LIB]", 48, Font.PLAIN, TEXT);
        ico.setAlignmentX(CENTER_ALIGNMENT);
        JLabel title = lbl("QuickLab", 30, Font.BOLD, ACCENT);
        title.setAlignmentX(CENTER_ALIGNMENT);
        JLabel sub = lbl("Library Management System", 13, Font.PLAIN, MUTED);
        sub.setAlignmentX(CENTER_ALIGNMENT);

        card.add(ico); card.add(vs(4));
        card.add(title); card.add(vs(4));
        card.add(sub); card.add(vs(32));

        JTextField userF = field();
        JPasswordField passF = new JPasswordField();
        styleTextField(passF);

        card.add(fmtLbl("USERNAME")); card.add(vs(6)); card.add(userF); card.add(vs(16));
        card.add(fmtLbl("PASSWORD")); card.add(vs(6)); card.add(passF); card.add(vs(28));

        JButton btn = accentBtn("Sign In ->");
        btn.setAlignmentX(CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        card.add(btn);

        card.add(vs(20));
        JLabel hint = lbl("admin/admin123 - alice/alice123 - bob/bob123", 11, Font.PLAIN, MUTED);
        hint.setAlignmentX(CENTER_ALIGNMENT);
        card.add(hint);

        ActionListener doLogin = e -> {
            String u = userF.getText().trim();
            String p = new String(passF.getPassword());
            system.findUser(u, p).ifPresentOrElse(user -> {
                currentUser = user;
                buildAndShowMain();
            }, () -> JOptionPane.showMessageDialog(this,
                    "Invalid credentials or account blocked.", "Login Failed", JOptionPane.ERROR_MESSAGE));
        };
        btn.addActionListener(doLogin);
        userF.addActionListener(doLogin);
        passF.addActionListener(doLogin);

        outer.add(card);
        return outer;
    }

    private void buildAndShowMain() {
        rootPanel.remove(rootPanel.getComponent(1));
        rootPanel.add(buildMainPanel(), "main");
        cardLayout.show(rootPanel, "main");
    }

    
    private JPanel buildMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);

        // Top bar
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(SURFACE);
        bar.setBorder(new CompoundBorder(new MatteBorder(0,0,1,0,BORDER), new EmptyBorder(13,22,13,22)));

        JLabel title = lbl("[LIB]  QuickLab Library System", 16, Font.BOLD, TEXT);
        bar.add(title, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
        JLabel userLbl = lbl("  " + currentUser.getName() + "  [" + currentUser.getRole() + "]  ", 13, Font.BOLD,
                             "Librarian".equals(currentUser.getRole()) ? ACCENT : GREEN);
        JButton logout = ghostBtn("Logout");
        logout.addActionListener(e -> { currentUser = null; cardLayout.show(rootPanel, "login"); });
        right.add(userLbl); right.add(logout);
        bar.add(right, BorderLayout.EAST);
        panel.add(bar, BorderLayout.NORTH);

        // Tabs ? differ by role
        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        tabbedPane.setBackground(SURFACE);
        tabbedPane.setForeground(MUTED);
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 13));

        tabbedPane.addTab("[LIB]  Books",   buildBooksTab());
        tabbedPane.addTab("[~]  Rentals", buildRentalsTab());
        tabbedPane.addTab("[R]  Reviews",  buildReviewsTab());

        if ("Librarian".equals(currentUser.getRole())) {
            tabbedPane.addTab("[U]  Users", buildUsersTab());
        }

        panel.add(tabbedPane, BorderLayout.CENTER);
        return panel;
    }

    void refreshAll() {
        tabbedPane.setComponentAt(0, buildBooksTab());
        tabbedPane.setComponentAt(1, buildRentalsTab());
        tabbedPane.setComponentAt(2, buildReviewsTab());
        if ("Librarian".equals(currentUser.getRole()) && tabbedPane.getTabCount() > 3)
            tabbedPane.setComponentAt(3, buildUsersTab());
    }

   
    private JPanel buildBooksTab() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(BG);

        JPanel header = headerPanel("Book Catalog");
        if ("Librarian".equals(currentUser.getRole())) {
            JButton add = accentBtn("+ Add Book");
            add.addActionListener(e -> showAddBookDialog());
            header.add(add, BorderLayout.EAST);
        }
        outer.add(header, BorderLayout.NORTH);

        // Grid
        JPanel grid = new JPanel(new GridLayout(0, 3, 16, 16));
        grid.setBackground(BG);
        grid.setBorder(new EmptyBorder(4, 24, 24, 24));

        for (Book b : system.getBooks()) {
            grid.add(buildBookCard(b));
        }

        JScrollPane sp = new JScrollPane(grid);
        sp.getViewport().setBackground(BG);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        outer.add(sp, BorderLayout.CENTER);
        return outer;
    }

    private JPanel buildBookCard(Book book) {
        JPanel card = new JPanel(new BorderLayout(0, 0)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(SURFACE);
                g2.fill(new RoundRectangle2D.Float(0,0,getWidth(),getHeight(),14,14));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(0,0,0,0));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Cover
        JPanel cover = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Background gradient
                Color c1 = book.getCoverColor();
                Color c2 = c1.darker().darker();
                GradientPaint gp = new GradientPaint(0,0,c1,getWidth(),getHeight(),c2);
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Float(0,0,getWidth(),getHeight(),14,14));
                // Spine line
                g2.setColor(new Color(0,0,0,60));
                g2.fillRect(0, 0, 8, getHeight());
                // Emoji
                g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 38));
                FontMetrics fm = g2.getFontMetrics();
                String emoji = book.getCoverEmoji();
                int ex = (getWidth()-fm.stringWidth(emoji))/2;
                g2.drawString(emoji, ex, getHeight()/2 - 10);
                // Status badge
                if (!book.isAvailable()) {
                    g2.setColor(new Color(0,0,0,160));
                    g2.fillRoundRect(getWidth()-68, 8, 60, 22, 10, 10);
                    g2.setColor(RED);
                    g2.setFont(new Font("SansSerif", Font.BOLD, 10));
                    g2.drawString("RENTED", getWidth()-62, 23);
                }
                g2.dispose();
            }
        };
        cover.setPreferredSize(new Dimension(0, 160));
        cover.setOpaque(false);
        card.add(cover, BorderLayout.NORTH);

        // Info
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        info.setBorder(new EmptyBorder(10, 12, 12, 12));

        JLabel titleLbl = lbl(book.getTitle(), 13, Font.BOLD, TEXT);
        titleLbl.setAlignmentX(LEFT_ALIGNMENT);
        JLabel authorLbl = lbl(book.getAuthor(), 11, Font.PLAIN, MUTED);
        authorLbl.setAlignmentX(LEFT_ALIGNMENT);

        // Stars
        double avg = book.getAvgRating();
        String stars = "*".repeat((int)Math.round(avg)) + "o".repeat(5-(int)Math.round(avg));
        JLabel starLbl = lbl(avg>0 ? stars + String.format(" %.1f", avg) : "No ratings yet",
                             11, Font.PLAIN, avg>0 ? GOLD : MUTED);
        starLbl.setAlignmentX(LEFT_ALIGNMENT);

        JLabel genreLbl = lbl(book.getGenre(), 10, Font.BOLD, ACCENT);
        genreLbl.setAlignmentX(LEFT_ALIGNMENT);

        info.add(titleLbl); info.add(vs(2));
        info.add(authorLbl); info.add(vs(4));
        info.add(genreLbl); info.add(vs(4));
        info.add(starLbl);
        card.add(info, BorderLayout.CENTER);

        // Click -> detail dialog
        MouseAdapter click = new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { showBookDetail(book); }
            @Override public void mouseEntered(MouseEvent e) { card.setBorder(new LineBorder(ACCENT, 2, true)); }
            @Override public void mouseExited(MouseEvent e)  { card.setBorder(new EmptyBorder(0,0,0,0)); }
        };
        card.addMouseListener(click);
        cover.addMouseListener(click);
        info.addMouseListener(click);
        return card;
    }

    
    void showBookDetail(Book book) {
        JDialog d = new JDialog(this, book.getTitle(), true);
        d.setSize(620, 580);
        d.setLocationRelativeTo(this);
        d.setBackground(SURFACE);
        d.getContentPane().setBackground(SURFACE);

        JPanel main = new JPanel(new BorderLayout(0,0));
        main.setBackground(SURFACE);

        
        JPanel top = new JPanel(new BorderLayout(16,0));
        top.setBackground(SURFACE);
        top.setBorder(new EmptyBorder(20,20,16,20));

        
        JPanel miniCover = new JPanel() {
            @Override public Dimension getPreferredSize() { return new Dimension(110, 160); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c1 = book.getCoverColor();
                GradientPaint gp = new GradientPaint(0,0,c1,getWidth(),getHeight(),c1.darker().darker());
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Float(0,0,getWidth(),getHeight(),10,10));
                g2.setColor(new Color(0,0,0,60)); g2.fillRect(0,0,6,getHeight());
                g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
                FontMetrics fm = g2.getFontMetrics();
                String em = book.getCoverEmoji();
                g2.drawString(em,(getWidth()-fm.stringWidth(em))/2, getHeight()/2);
                g2.dispose();
            }
        };
        miniCover.setOpaque(false);
        top.add(miniCover, BorderLayout.WEST);

        // Meta
        JPanel meta = new JPanel();
        meta.setLayout(new BoxLayout(meta, BoxLayout.Y_AXIS));
        meta.setOpaque(false);

        meta.add(lbl(book.getTitle(), 18, Font.BOLD, TEXT));
        meta.add(vs(4));
        meta.add(lbl("by " + book.getAuthor(), 13, Font.PLAIN, MUTED));
        meta.add(vs(8));
        meta.add(lbl("Genre: " + book.getGenre(), 12, Font.BOLD, ACCENT));
        meta.add(vs(12));

        double avg = book.getAvgRating();
        String stars = avg>0 ? "*".repeat((int)Math.round(avg)) + "o".repeat(5-(int)Math.round(avg))
                             + String.format("  %.1f / 5  (%d reviews)", avg, book.getReviews().size())
                             : "No reviews yet";
        meta.add(lbl(stars, 13, Font.PLAIN, avg>0 ? GOLD : MUTED));
        meta.add(vs(12));

        // Status + due date for this user
        Rental activeRental = null;
        if (currentUser instanceof Member m) {
            activeRental = system.getRentalByMemberAndBook(m.getUserID(), book.getBookID());
        }

        JLabel statusLbl = lbl(book.isAvailable() ? "OK  Available" : "X  Currently Rented",
                               13, Font.BOLD, book.isAvailable() ? GREEN : RED);
        meta.add(statusLbl);

        if (activeRental != null && "Approved".equals(activeRental.getStatus())) {
            meta.add(vs(6));
            meta.add(lbl("[DATE]  Your Due Date: " + activeRental.getDueDateStr(), 12, Font.BOLD,
                         activeRental.isOverdue() ? RED : GOLD));
            meta.add(vs(2));
            meta.add(lbl("Requested: " + activeRental.getRequestDateStr(), 11, Font.PLAIN, MUTED));
        }

        top.add(meta, BorderLayout.CENTER);

        
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.setOpaque(false);

        if (currentUser instanceof Member member) {
            Rental existRental = system.getRentalByMemberAndBook(member.getUserID(), book.getBookID());
            if (existRental == null && book.isAvailable()) {
                JButton rentBtn = accentBtn("[BOX] Rent This Book");
                rentBtn.addActionListener(e -> { d.dispose(); showRentalDialog(book, member); });
                btns.add(rentBtn);
            } else if (existRental != null && "Pending".equals(existRental.getStatus())) {
                btns.add(lbl("[WAIT] Rental Pending Approval", 12, Font.BOLD, GOLD));
            } else if (existRental != null && "Approved".equals(existRental.getStatus())) {
                JButton retBtn = new JButton("<- Return Book");
                styleReturnBtn(retBtn);
                Rental finalRental = existRental;
                retBtn.addActionListener(e -> { d.dispose(); showReturnAndReviewDialog(finalRental, member); });
                btns.add(retBtn);
            }
        }
        meta.add(vs(12));
        meta.add(btns);
        top.add(btns, BorderLayout.SOUTH);
        main.add(top, BorderLayout.NORTH);

        // Description
        JTextArea desc = new JTextArea(book.getDescription());
        desc.setBackground(SURFACE2);
        desc.setForeground(MUTED);
        desc.setFont(new Font("SansSerif", Font.PLAIN, 13));
        desc.setEditable(false);
        desc.setLineWrap(true); desc.setWrapStyleWord(true);
        desc.setBorder(new EmptyBorder(12,20,12,20));
        main.add(desc, BorderLayout.AFTER_LINE_ENDS);

        // Reviews panel
        JPanel reviewSection = new JPanel(new BorderLayout());
        reviewSection.setBackground(SURFACE);
        reviewSection.setBorder(new CompoundBorder(
            new MatteBorder(1,0,0,0,BORDER), new EmptyBorder(12,20,16,20)));

        JLabel revTitle = lbl("Reviews", 14, Font.BOLD, TEXT);
        reviewSection.add(revTitle, BorderLayout.NORTH);

        JPanel revList = new JPanel();
        revList.setLayout(new BoxLayout(revList, BoxLayout.Y_AXIS));
        revList.setBackground(SURFACE);

        List<Review> reviews = book.getReviews();
        if (reviews.isEmpty()) {
            revList.add(lbl("No reviews yet.", 12, Font.PLAIN, MUTED));
        } else {
            for (Review rv : reviews) {
                JPanel rc = buildReviewCard(rv);
                rc.setAlignmentX(LEFT_ALIGNMENT);
                revList.add(rc); revList.add(vs(8));
            }
        }
        JScrollPane rsp = new JScrollPane(revList);
        rsp.getViewport().setBackground(SURFACE);
        rsp.setBorder(null);
        rsp.setPreferredSize(new Dimension(0, 160));
        reviewSection.add(rsp, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(SURFACE);
        center.add(desc, BorderLayout.NORTH);
        center.add(reviewSection, BorderLayout.CENTER);
        main.add(center, BorderLayout.CENTER);

        d.add(main);
        d.setVisible(true);
    }

    private JPanel buildReviewCard(Review rv) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(SURFACE2);
        p.setBorder(new CompoundBorder(new LineBorder(BORDER,1,true), new EmptyBorder(8,12,8,12)));

        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        JLabel name = lbl(rv.getMemberName(), 12, Font.BOLD, TEXT);
        String stars = "*".repeat(rv.getRating()) + "o".repeat(5-rv.getRating());
        JLabel star = lbl(stars + (rv.isSpoiler() ? "  ? SPOILER" : ""), 12, Font.PLAIN, GOLD);
        row.add(name, BorderLayout.WEST);
        row.add(star, BorderLayout.EAST);
        p.add(row); p.add(vs(4));

        JLabel content = lbl("<html><body style='width:360px'>" + rv.getContent() + "</body></html>",
                             12, Font.PLAIN, MUTED);
        p.add(content);
        return p;
    }

    
    void showRentalDialog(Book book, Member member) {
        JDialog d = dialog("Rent: " + book.getTitle(), 420, 500);
        JPanel form = formPanel();
        JComboBox<String> typeBox = styledCombo(new String[]{"standard (10 TL)", "priority (15 TL)"});
        JLabel priceLbl = lbl("Price: 10 TL", 13, Font.BOLD, GREEN);
        typeBox.addActionListener(e -> priceLbl.setText(
            "Price: " + (typeBox.getSelectedIndex()==1 ? "15" : "10") + " TL"));

        form.add(fmtLbl("BOOK")); form.add(vs(5));
        form.add(lbl(book.getTitle() + " - " + book.getAuthor(), 13, Font.BOLD, TEXT));
        form.add(vs(14));
        form.add(fmtLbl("DELIVERY TYPE")); form.add(vs(5)); form.add(typeBox);
        form.add(vs(8)); form.add(priceLbl); form.add(vs(20));

        form.add(fmtLbl("CARDHOLDER NAME")); form.add(vs(5));
        JTextField cardNameF = styledField("Name on card");
        form.add(cardNameF); form.add(vs(12));

        form.add(fmtLbl("CARD NUMBER")); form.add(vs(5));
        JTextField cardNumF = styledField("XXXX XXXX XXXX XXXX");
        form.add(cardNumF); form.add(vs(12));

        JPanel expCvvRow = new JPanel(new java.awt.GridLayout(1, 2, 10, 0));
        expCvvRow.setOpaque(false);
        JPanel expPanel = new JPanel(new BorderLayout(0, 5));
        expPanel.setOpaque(false);
        expPanel.add(fmtLbl("EXP DATE (MM/YY)"), BorderLayout.NORTH);
        JTextField expF = styledField("MM/YY");
        expPanel.add(expF, BorderLayout.CENTER);
        JPanel cvvPanel = new JPanel(new BorderLayout(0, 5));
        cvvPanel.setOpaque(false);
        cvvPanel.add(fmtLbl("CVV"), BorderLayout.NORTH);
        JTextField cvvF = styledField("123");
        cvvPanel.add(cvvF, BorderLayout.CENTER);
        expCvvRow.add(expPanel);
        expCvvRow.add(cvvPanel);
        form.add(expCvvRow); form.add(vs(20));

        JButton ok = accentBtn("Send Rental Request");
        ok.addActionListener(e -> {
            String cardName = cardNameF.getText().trim();
            String cardNum  = cardNumF.getText().trim().replaceAll("\\s+", "");
            String exp      = expF.getText().trim();
            String cvv      = cvvF.getText().trim();

            if (cardName.isEmpty() || cardNum.isEmpty() || exp.isEmpty() || cvv.isEmpty()) {
                warn(d, "Please fill in all card fields."); return;
            }
            if (!cardNum.matches("\\d{16}")) {
                warn(d, "Card number must be 16 digits."); return;
            }
            if (!exp.matches("(0[1-9]|1[0-2])/\\d{2}")) {
                warn(d, "Expiry date must be in MM/YY format."); return;
            }
            if (!cvv.matches("\\d{3,4}")) {
                warn(d, "CVV must be 3 or 4 digits."); return;
            }

            String t = typeBox.getSelectedIndex()==1 ? "priority" : "standard";
            Rental rental = member.requestRental(book, t);
            if (rental == null) { warn(d, "Cannot create rental."); return; }
            system.addRental(rental);
            d.dispose();
            JOptionPane.showMessageDialog(null,
                "<html><center><b>Your card information has been saved.</b><br><br>" +
                "Please wait for approval.</center></html>",
                "Request Sent", JOptionPane.INFORMATION_MESSAGE);
            refreshAll();
        });
        form.add(ok);
        d.add(form); d.setVisible(true);
    }

 
    void showReturnAndReviewDialog(Rental rental, Member member) {
        JDialog d = dialog("Return Book & Write Review", 480, 420);
        JPanel form = formPanel();

        form.add(fmtLbl("BOOK"));
        form.add(lbl(rental.getBook().getTitle(), 13, Font.BOLD, TEXT));
        form.add(vs(4));
        form.add(lbl("Due Date: " + rental.getDueDateStr() +
                     (rental.isOverdue() ? "  ? OVERDUE" : ""), 12, Font.PLAIN,
                     rental.isOverdue() ? RED : MUTED));
        form.add(vs(16));

        form.add(fmtLbl("YOUR RATING"));
        int[] rating = {0};
        JPanel starRow = new JPanel(new FlowLayout(FlowLayout.LEFT,4,0));
        starRow.setOpaque(false);
        JButton[] starBtns = new JButton[5];
        for (int i=0; i<5; i++) {
            JButton sb = new JButton("o");
            sb.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
            sb.setForeground(MUTED); sb.setBackground(SURFACE2);
            sb.setBorderPainted(false); sb.setFocusPainted(false);
            sb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            final int val = i+1;
            sb.addActionListener(ev -> {
                rating[0] = val;
                for (int j=0; j<5; j++) {
                    starBtns[j].setText(j < val ? "*" : "o");
                    starBtns[j].setForeground(j < val ? GOLD : MUTED);
                }
            });
            starBtns[i] = sb;
            starRow.add(sb);
        }
        form.add(vs(5)); form.add(starRow); form.add(vs(14));

        form.add(fmtLbl("REVIEW (optional)"));
        JTextArea area = new JTextArea(4, 20);
        area.setBackground(SURFACE2); area.setForeground(TEXT);
        area.setCaretColor(TEXT); area.setLineWrap(true); area.setWrapStyleWord(true);
        area.setFont(new Font("SansSerif", Font.PLAIN, 13));
        area.setBorder(new CompoundBorder(new LineBorder(BORDER), new EmptyBorder(6,8,6,8)));
        form.add(vs(5)); form.add(new JScrollPane(area)); form.add(vs(6));

        JCheckBox spoiler = new JCheckBox("Contains spoiler");
        spoiler.setBackground(SURFACE); spoiler.setForeground(TEXT);
        spoiler.setFont(new Font("SansSerif", Font.PLAIN, 12));
        form.add(spoiler); form.add(vs(18));

        JButton returnBtn = new JButton("<-  Return Book");
        styleReturnBtn(returnBtn);
        returnBtn.setAlignmentX(LEFT_ALIGNMENT);
        returnBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        returnBtn.addActionListener(e -> {
            // Return book
            if (currentUser instanceof Librarian lib) {
                lib.returnRental(rental);
            } else {
                
                rental.returnBook();
                if (member != null) member.decrementRentals();
            }
            
            String content = area.getText().trim();
            if (rating[0] > 0) {
                Review rv = member.addReview(rental.getBook(), content.isEmpty() ?
                        "Returned after reading." : content,
                        rating[0], spoiler.isSelected(), system.nextReviewID());
                system.addReview(rv);
            }
            ok(d, "Book returned successfully!\nThank you for using QuickLab.");
            d.dispose();
            refreshAll();
        });
        form.add(returnBtn);
        d.add(form); d.setVisible(true);
    }

    // =====================================================================
    //  RENTALS TAB
    // =====================================================================
    private JPanel buildRentalsTab() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(BG);

        JPanel header = headerPanel("Rentals");
        if (currentUser instanceof Member) {
            JButton req = accentBtn("+ Request Rental");
            req.addActionListener(e -> {
                List<Book> avail = system.getBooks().stream().filter(Book::isAvailable).toList();
                if (avail.isEmpty()) { warn(this, "No books available."); return; }
                showRentalDialog(avail.get(0), (Member) currentUser);
            });
            header.add(req, BorderLayout.EAST);
        }
        outer.add(header, BorderLayout.NORTH);

        String[] cols = {"#", "Book", "Member", "Type", "Price", "Requested", "Due Date", "Status", "Collected"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };

        List<Rental> source;
        if (currentUser instanceof Member m)
            source = system.getRentals().stream()
                    .filter(r -> r.getMember().getUserID() == m.getUserID()).toList();
        else
            source = system.getRentals();

        for (Rental r : source) {
            String collected;
            if ("Returned".equals(r.getStatus())) {
                collected = r.isBookCollected() ? "Collected" : "Awaiting";
            } else {
                collected = "-";
            }
            model.addRow(new Object[]{
                "#" + r.getRentalID(),
                r.getBook().getTitle(),
                r.getMember().getName(),
                r.getType(),
                r.getPrice() + " TL",
                r.getRequestDateStr(),
                r.getDueDateStr(),
                r.getStatus(),
                collected
            });
        }

        JTable table = styledTable(model);
        table.getColumnModel().getColumn(7).setCellRenderer(new StatusRenderer());
        table.getColumnModel().getColumn(8).setCellRenderer(new StatusRenderer());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        if ("Librarian".equals(currentUser.getRole())) {
            JPanel actionBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            actionBar.setBackground(SURFACE);
            actionBar.setBorder(new MatteBorder(1, 0, 0, 0, BORDER));
            actionBar.add(lbl("  Select a row then use:", 12, Font.PLAIN, MUTED));

            JButton approveBtn = accentBtn("Approve");
            approveBtn.setBackground(new Color(0x1A4A2A));
            approveBtn.setForeground(GREEN);
            approveBtn.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row < 0) { warn(this, "Please select a rental row first."); return; }
                Rental rental = source.get(row);
                if (!"Pending".equals(rental.getStatus())) {
                    warn(this, "Only Pending rentals can be approved."); return;
                }
                ((Librarian) currentUser).approveRental(rental);
                new DialogNotification(rental.getMember().getName())
                    .send("Rental Approved: " + rental.getBook().getTitle() + "\nDue Date: " + rental.getDueDateStr());
                refreshAll();
            });

            JButton rejectBtn = accentBtn("Reject");
            rejectBtn.setBackground(new Color(0x4A1A1A));
            rejectBtn.setForeground(RED);
            rejectBtn.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row < 0) { warn(this, "Please select a rental row first."); return; }
                Rental rental = source.get(row);
                if (!"Pending".equals(rental.getStatus())) {
                    warn(this, "Only Pending rentals can be rejected."); return;
                }
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Reject rental for \"" + rental.getBook().getTitle() + "\"?",
                    "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    rental.setStatus("Rejected");
                    new DialogNotification(rental.getMember().getName())
                        .send("Rental Rejected: " + rental.getBook().getTitle());
                    refreshAll();
                }
            });

            JButton returnBtn = accentBtn("Mark Returned");
            returnBtn.setBackground(new Color(0x2A2A4A));
            returnBtn.setForeground(new Color(0xCCCCCC));
            returnBtn.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row < 0) { warn(this, "Please select a rental row first."); return; }
                Rental rental = source.get(row);
                if (!"Approved".equals(rental.getStatus())) {
                    warn(this, "Only Approved rentals can be marked returned."); return;
                }
                ((Librarian) currentUser).returnRental(rental);
                ok(this, "Book returned. Now available again.");
                refreshAll();
            });

            JButton collectBtn = accentBtn("Mark Collected");
            collectBtn.setBackground(new Color(0x1A3A4A));
            collectBtn.setForeground(new Color(0x66CCFF));
            collectBtn.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row < 0) { warn(this, "Please select a rental row first."); return; }
                Rental rental = source.get(row);
                if (!"Returned".equals(rental.getStatus())) {
                    warn(this, "Only Returned rentals can be marked as collected."); return;
                }
                if (rental.isBookCollected()) {
                    warn(this, "This book is already marked as collected."); return;
                }
                rental.setBookCollected(true);
                ok(this, "Book marked as collected.");
                refreshAll();
            });

            JButton uncollectBtn = accentBtn("Not Collected");
            uncollectBtn.setBackground(new Color(0x3A2A1A));
            uncollectBtn.setForeground(new Color(0xFFAA44));
            uncollectBtn.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row < 0) { warn(this, "Please select a rental row first."); return; }
                Rental rental = source.get(row);
                if (!"Returned".equals(rental.getStatus())) {
                    warn(this, "Only Returned rentals can be updated."); return;
                }
                rental.setBookCollected(false);
                ok(this, "Book marked as not collected.");
                refreshAll();
            });

            actionBar.add(approveBtn);
            actionBar.add(rejectBtn);
            actionBar.add(returnBtn);
            actionBar.add(collectBtn);
            actionBar.add(uncollectBtn);
            outer.add(actionBar, BorderLayout.SOUTH);
        }

        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(SURFACE);
        sp.setBorder(null);
        outer.add(sp, BorderLayout.CENTER);
        return outer;
    }


    private JPanel buildReviewsTab() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(BG);
        outer.add(headerPanel("Reviews"), BorderLayout.NORTH);

        String[] cols = {"#","Book","Member","Rating","Spoiler","Content","Action"};
        DefaultTableModel model = new DefaultTableModel(cols,0) {
            @Override public boolean isCellEditable(int row, int col) {
                return "Librarian".equals(currentUser.getRole()) && col == 6;
            }
        };

        List<Review> src = "Librarian".equals(currentUser.getRole()) ? system.getReviews() :
            system.getReviews().stream()
                .filter(r -> r.getMemberID()==currentUser.getUserID()).toList();

        for (Review rv : src) {
            String book = system.findBook(rv.getBookID()).map(Book::getTitle).orElse("?");
            model.addRow(new Object[]{
                "#"+rv.getReviewID(), book, rv.getMemberName(),
                "*".repeat(rv.getRating())+"o".repeat(5-rv.getRating()),
                rv.isSpoiler()?"YES":"No", rv.getContent(),
                "Librarian".equals(currentUser.getRole()) ? "Toggle Spoiler" : ""
            });
        }
        JTable table = styledTable(model);
        table.getColumnModel().getColumn(4).setCellRenderer(new StatusRenderer());
        if ("Librarian".equals(currentUser.getRole())) {
            table.getColumnModel().getColumn(6).setCellRenderer(new BtnRenderer());
            table.getColumnModel().getColumn(6).setCellEditor(new BtnEditor(row -> {
                String idStr = table.getValueAt(row,0).toString().replace("#","");
                int rid = Integer.parseInt(idStr);
                Review rv = system.getReviews().stream().filter(r->r.getReviewID()==rid).findFirst().orElse(null);
                if (rv == null) return;
                Librarian lib = (Librarian) currentUser;
                if (rv.isSpoiler()) lib.unmarkSpoiler(rv); else lib.markSpoiler(rv);
                refreshAll();
            }));
        }
        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(SURFACE); sp.setBorder(null);
        outer.add(sp, BorderLayout.CENTER);
        return outer;
    }

   
    private JPanel buildUsersTab() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(BG);
        JPanel header = headerPanel("User Management");
        JButton add = accentBtn("+ Add User");
        add.addActionListener(e -> showAddUserDialog());
        header.add(add, BorderLayout.EAST);
        outer.add(header, BorderLayout.NORTH);

        String[] cols = {"#","Name","Role","Status","Active Rentals","Action"};
        DefaultTableModel model = new DefaultTableModel(cols,0) {
            @Override public boolean isCellEditable(int row, int col) { return col == 5; }
        };
        for (User u : system.getUsers()) {
            int ar = (u instanceof Member m) ? m.getActiveRentals() : -1;
            model.addRow(new Object[]{
                "#"+u.getUserID(), u.getName(), u.getRole(), u.getStatus(),
                ar<0?"?":ar,
                u.getRole().equals("Member") ? (u.getStatus().equals("Blocked")?"Unblock":"Block") : ""
            });
        }
        JTable table = styledTable(model);
        table.getColumnModel().getColumn(3).setCellRenderer(new StatusRenderer());
        table.getColumnModel().getColumn(5).setCellRenderer(new BtnRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new BtnEditor(row -> {
            String idStr = table.getValueAt(row,0).toString().replace("#","");
            int uid = Integer.parseInt(idStr);
            User target = system.getUsers().stream().filter(u->u.getUserID()==uid).findFirst().orElse(null);
            if (target==null||target instanceof Librarian) return;
            Librarian lib = (Librarian)currentUser;
            if ("Blocked".equals(target.getStatus())) lib.unblockUser(target);
            else lib.blockUser(target);
            refreshAll();
        }));
        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(SURFACE); sp.setBorder(null);
        outer.add(sp, BorderLayout.CENTER);
        return outer;
    }

 
    void showAddBookDialog() {
        JDialog d = dialog("Add New Book", 460, 380);
        JPanel form = formPanel();
        JTextField titleF = field(), authorF = field();
        String[] genres = {"Classic","Historical","Fantasy","Sci-Fi","Dystopia","Romance",
                           "Philosophy","Drama","Coming-of-Age","Magical Realism","Thriller"};
        JComboBox<String> genreBox = styledCombo(genres);
        JTextArea descArea = new JTextArea(3,20);
        descArea.setBackground(SURFACE2); descArea.setForeground(TEXT);
        descArea.setFont(new Font("SansSerif",Font.PLAIN,13));
        descArea.setBorder(new CompoundBorder(new LineBorder(BORDER), new EmptyBorder(6,8,6,8)));
        descArea.setLineWrap(true); descArea.setWrapStyleWord(true);

        form.add(fmtLbl("TITLE"));  form.add(vs(5)); form.add(titleF);  form.add(vs(12));
        form.add(fmtLbl("AUTHOR")); form.add(vs(5)); form.add(authorF); form.add(vs(12));
        form.add(fmtLbl("GENRE"));  form.add(vs(5)); form.add(genreBox); form.add(vs(12));
        form.add(fmtLbl("DESCRIPTION")); form.add(vs(5));
        form.add(new JScrollPane(descArea)); form.add(vs(18));

        JButton ok = accentBtn("Add Book");
        ok.addActionListener(e -> {
            String t = titleF.getText().trim(), a = authorF.getText().trim();
            String g = (String)genreBox.getSelectedItem();
            String desc = descArea.getText().trim();
            if (t.isEmpty()||a.isEmpty()) { warn(d,"Fill in title and author."); return; }
            // Random cover color
            Color[] palette = {new Color(0x6B1A5A),new Color(0x1A5A6B),new Color(0x3A6B1A),
                               new Color(0x6B4A1A),new Color(0x1A1A6B)};
            String[] emojis = {"?","?","[T]","?","?","?","?"};
            Color c = palette[(int)(Math.random()*palette.length)];
            String em = emojis[(int)(Math.random()*emojis.length)];
            Book book = new Book(system.nextBookID(), t, a, g,
                    desc.isEmpty()?"No description provided.":desc, c, em);
            system.addBook(book);
            ok(d,"Book \""+t+"\" added!");
            d.dispose(); refreshAll();
        });
        form.add(ok);
        d.add(form); d.setVisible(true);
    }


    void showAddUserDialog() {
        JDialog d = dialog("Add New User", 400, 260);
        JPanel form = formPanel();
        JTextField nameF = field();
        JComboBox<String> roleBox = styledCombo(new String[]{"Member","Librarian"});
        form.add(fmtLbl("NAME")); form.add(vs(5)); form.add(nameF); form.add(vs(12));
        form.add(fmtLbl("ROLE")); form.add(vs(5)); form.add(roleBox); form.add(vs(20));
        JButton ok = accentBtn("Add User");
        ok.addActionListener(e -> {
            String n = nameF.getText().trim();
            if (n.isEmpty()) { warn(d,"Enter a name."); return; }
            int id = system.getUsers().size()+1;
            User u = roleBox.getSelectedItem().equals("Librarian")
                ? new Librarian(id,n,n+"123",1000+id)
                : new Member(id,n,n+"123");
            system.addUser(u);
            ok(d,u.getRole()+" \""+n+"\" added.\nPassword: "+n+"123");
            d.dispose(); refreshAll();
        });
        form.add(ok);
        d.add(form); d.setVisible(true);
    }


    JPanel darkPanel() {
        JPanel p = new JPanel(new BorderLayout()); p.setBackground(BG); return p;
    }
    JPanel headerPanel(String title) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(20,24,14,24));
        p.add(lbl(title, 18, Font.BOLD, TEXT), BorderLayout.WEST);
        return p;
    }
    JPanel formPanel() {
        JPanel p = new JPanel(); p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(SURFACE); p.setBorder(new EmptyBorder(24,28,24,28)); return p;
    }
    JLabel lbl(String t, int sz, int style, Color c) {
        JLabel l = new JLabel(t); l.setFont(new Font("SansSerif",style,sz));
        l.setForeground(c); return l;
    }
    JLabel fmtLbl(String t) {
        JLabel l = new JLabel(t); l.setFont(new Font("Monospaced",Font.BOLD,10));
        l.setForeground(MUTED); l.setAlignmentX(LEFT_ALIGNMENT); return l;
    }
    Component vs(int h) { return Box.createVerticalStrut(h); }
    JTextField field() { JTextField f = new JTextField(); styleTextField(f); return f; }
    JTextField styledField(String placeholder) {
        JTextField f = new JTextField() {
            @Override protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    g.setColor(new Color(0x666680));
                    g.setFont(getFont().deriveFont(Font.ITALIC));
                    java.awt.Insets ins = getInsets();
                    g.drawString(placeholder, ins.left + 2, getHeight() - ins.bottom - 5);
                }
            }
        };
        styleTextField(f);
        return f;
    }
    void styleTextField(JTextField f) {
        f.setBackground(SURFACE2); f.setForeground(TEXT); f.setCaretColor(TEXT);
        f.setFont(new Font("SansSerif",Font.PLAIN,13));
        f.setBorder(new CompoundBorder(new LineBorder(BORDER,1,true),new EmptyBorder(8,12,8,12)));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE,42));
        f.setAlignmentX(LEFT_ALIGNMENT);
    }
    <T> JComboBox<T> styledCombo(T[] items) {
        JComboBox<T> c = new JComboBox<>(items);
        c.setBackground(SURFACE2); c.setForeground(TEXT);
        c.setFont(new Font("SansSerif",Font.PLAIN,13));
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE,42));
        c.setAlignmentX(LEFT_ALIGNMENT); return c;
    }
    JButton accentBtn(String t) {
        JButton b = new JButton(t); b.setBackground(ACCENT); b.setForeground(Color.WHITE);
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setFont(new Font("SansSerif",Font.BOLD,13));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(10,20,10,20)); return b;
    }
    void styleReturnBtn(JButton b) {
        b.setBackground(new Color(0x2A4A3A)); b.setForeground(GREEN);
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setFont(new Font("SansSerif",Font.BOLD,13));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(10,20,10,20));
    }
    JButton ghostBtn(String t) {
        JButton b = new JButton(t); b.setBackground(SURFACE2); b.setForeground(MUTED);
        b.setFocusPainted(false);
        b.setBorder(new CompoundBorder(new LineBorder(BORDER),new EmptyBorder(7,14,7,14)));
        b.setFont(new Font("SansSerif",Font.BOLD,12));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); return b;
    }
    JDialog dialog(String title, int w, int h) {
        JDialog d = new JDialog(this, title, true);
        d.setSize(w,h); d.setLocationRelativeTo(this);
        d.setBackground(SURFACE); d.getContentPane().setBackground(SURFACE);
        d.setResizable(false); return d;
    }
    JTable styledTable(TableModel m) {
        JTable t = new JTable(m);
        t.setBackground(SURFACE); t.setForeground(TEXT);
        t.setSelectionBackground(ACCENT.darker()); t.setSelectionForeground(TEXT);
        t.setGridColor(BORDER); t.setRowHeight(36);
        t.setFont(new Font("SansSerif",Font.PLAIN,13));
        t.getTableHeader().setBackground(SURFACE2);
        t.getTableHeader().setForeground(MUTED);
        t.getTableHeader().setFont(new Font("SansSerif",Font.BOLD,11));
        t.getTableHeader().setBorder(new MatteBorder(0,0,1,0,BORDER));
        t.setFillsViewportHeight(true); return t;
    }
    void ok(Component p,String msg)  { JOptionPane.showMessageDialog(p,msg,"OK Success",JOptionPane.INFORMATION_MESSAGE); }
    void warn(Component p,String msg){ JOptionPane.showMessageDialog(p,msg,"? Warning",JOptionPane.WARNING_MESSAGE); }


    static class StatusRenderer extends DefaultTableCellRenderer {
        @Override public Component getTableCellRendererComponent(
                JTable t,Object v,boolean sel,boolean foc,int row,int col) {
            super.getTableCellRendererComponent(t,v,sel,foc,row,col);
            setBackground(sel?ACCENT.darker():SURFACE);
            String s = v==null?"":v.toString();
            switch(s) {
                case "Available","Active","Approved","No" -> setForeground(GREEN);
                case "Rented","Blocked","Returned","YES"  -> setForeground(RED);
                case "Awaiting"                              -> setForeground(GOLD);
                case "Collected"                             -> setForeground(new Color(0x66CCFF));
                case "Pending"                            -> setForeground(GOLD);
                default                                   -> setForeground(TEXT);
            }
            setBorder(new EmptyBorder(0,12,0,0)); return this;
        }
    }
    static class BtnRenderer extends JButton implements TableCellRenderer {
        BtnRenderer() { setOpaque(true); setBackground(new Color(0x2A2A42));
                        setForeground(TEXT); setBorderPainted(false);
                        setFont(new Font("SansSerif",Font.BOLD,11)); }
        @Override public Component getTableCellRendererComponent(
                JTable t,Object v,boolean s,boolean f,int r,int c) {
            setText(v==null?"":v.toString()); return this; }
    }
    static class BtnEditor extends AbstractCellEditor implements TableCellEditor {
        private final JButton btn;
        private int editingRow = -1;
        BtnEditor(java.util.function.IntConsumer onAction) {
            btn = new JButton(); btn.setBackground(ACCENT); btn.setForeground(Color.WHITE);
            btn.setBorderPainted(false); btn.setFont(new Font("SansSerif",Font.BOLD,11));
            btn.addActionListener(e -> {
                int row = editingRow;
                fireEditingStopped();
                if (row >= 0) onAction.accept(row);
            });
        }
        @Override public Object getCellEditorValue() { return btn.getText(); }
        @Override public Component getTableCellEditorComponent(
                JTable t,Object v,boolean s,int r,int c) {
            editingRow = r;
            btn.setText(v==null?"":v.toString()); return btn; }
    }


    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        UIManager.put("OptionPane.background",      SURFACE);
        UIManager.put("Panel.background",           SURFACE);
        UIManager.put("OptionPane.messageForeground",TEXT);
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
