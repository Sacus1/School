import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame("SQL");
		JPanel panel = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		// sql
		SQL sql = new SQL("jdbc:mysql://localhost:3306/myband", "root", "");
		panel.setLayout(new GridBagLayout());
		// make a scrollbar on the right
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// add scrollpane to frame
		frame.add(scrollPane);
		// add components to panel
		ResultSet rs = sql.select("SELECT * FROM `setlist`");
		try {
			int i = 0;
			int[] columnWidth = {100, 100, 100, 100};
			while (rs.next()) {
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.BOTH;
				c.gridx = 0;
				c.gridy = i;
				// add column names to panel as labels
				if (i == 0) {
					for (int j = 1; j <= 4; j++) {
						String columnName = rs.getMetaData().getColumnName(j);
						c.gridx += columnWidth[j - 1];
						panel.add(new JLabel(columnName + " "), c);
					}
				}
        c.gridy = i + 1;
        c.gridx = 0;
				// add data to panel as labels
				for (int j = 1; j <= 4; j++) {
					String attribute = rs.getString(j);
					c.gridx += columnWidth[j - 1];
					panel.add(new JLabel(attribute + " "), c);
				}
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		frame.pack();
		frame.setVisible(true);

	}
}
