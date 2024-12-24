package Project;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

import static Project.Gain.informationGain;

public class GraphTree {

    // Định nghĩa lớp TreePanel để vẽ cây quyết định
    public static class TreePanel extends JPanel {
        Node root; // Gốc của cây
        int nodeRadius = 20; // Kích thước của một node
        int verticalSpacing = 80; // Khoảng cách dọc giữa các tầng của cây
        int horizontalSpacing = 62; // Khoảng cách ngang giữa các node cùng tầng
        int treeWidth = 0; // Chiều rộng ước tính của cây

        public TreePanel(Node root) {
            this.root = root;
            setBackground(Color.WHITE); // Màu nền trắng
            treeWidth = calculateTreeWidth(root); // Tính chiều rộng của cây
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Sử dụng Graphics2D để cải thiện chất lượng vẽ
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (root != null) {
                int initialX = Math.max(treeWidth / 2, getWidth() / 2); // Căn giữa gốc cây
                drawNode(g2d, root, initialX, 50, treeWidth / 4);
            }
        }

        private void drawNode(Graphics2D g2d, Node node, int x, int y, int xOffset) {
            // Màu sắc và đường viền của nút
            Color nodeColor = node.isSuggest ? (node.value.contains("Yes") ? new Color(76, 175, 80) : new Color(244, 67, 54)) : new Color(100, 181, 246);
            Color borderColor = new Color(33, 150, 243);

            // Vẽ bóng
            g2d.setColor(new Color(0, 0, 0, 50));
            g2d.fillOval(x - nodeRadius - 3, y - nodeRadius - 3, 2 * nodeRadius + 6, 2 * nodeRadius + 6);

            // Vẽ nút
            g2d.setColor(nodeColor);
            g2d.fillOval(x - nodeRadius, y - nodeRadius, 2 * nodeRadius, 2 * nodeRadius);

            // Vẽ đường viền nút
            g2d.setColor(borderColor);
            g2d.drawOval(x - nodeRadius, y - nodeRadius, 2 * nodeRadius, 2 * nodeRadius);

            // Vẽ nội dung nút
            g2d.setColor(Color.black);
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(node.value);
            g2d.drawString(node.value, x - textWidth / 2, y + 5);

            // Vẽ các nhánh con
            if (!node.next.isEmpty()) {
                int childY = y + verticalSpacing;
                int totalWidth = xOffset * (node.next.size() - 1);
                int startX = x - totalWidth / 2;

                for (Node child : node.next) {
                    int childX = startX;

                    // Vẽ đường nối
                    g2d.setColor(Color.GRAY);
                    g2d.setStroke(new BasicStroke(1.5f));
                    g2d.drawLine(x, y + nodeRadius, childX, childY - nodeRadius);

                    // Vẽ nhánh con
                    drawNode(g2d, child, childX, childY, xOffset / 2);
                    startX += xOffset;
                }
            }
        }

        private int calculateTreeWidth(Node node) {
            if (node.next.isEmpty()) {
                return horizontalSpacing;
            }
            int width = 0;
            for (Node child : node.next) {
                width += calculateTreeWidth(child);
            }
            return width;
        }

        @Override
        public Dimension getPreferredSize() {
            int depth = calculateTreeDepth(root);
            int width = Math.max(getWidth(), treeWidth);
            int height = depth * verticalSpacing + 100; // Cộng thêm khoảng cách để chứa toàn bộ cây
            return new Dimension(width, height);
        }

        private int calculateTreeDepth(Node node) {
            if (node.next.isEmpty()) {
                return 1;
            }
            int maxDepth = 0;
            for (Node child : node.next) {
                maxDepth = Math.max(maxDepth, calculateTreeDepth(child));
            }
            return maxDepth + 1;
        }
    }

    public static Node buildTree(String[][] data) {

        String[] decision = new String[data.length - 1];
        for (int i = 1; i < data.length; i++) {
            decision[i - 1] = data[i][data[0].length - 1];
        }
        double entropy = Entropy.calculateEntropy(decision);

        // Chọn root dựa trên Information Gain
        int rootIndex = Gain.informationGain(data, entropy, decision);
        if (rootIndex == -1) {
            return new Node(-1, "Unknown", true);
        }

        Node root = new Node(rootIndex, data[0][rootIndex], false);

        // Chia dữ liệu theo giá trị trong cột rootIndex
        Map<String, List<String[]>> branches = new HashMap<>();
        for (int i = 1; i < data.length; i++) {
            String key = data[i][rootIndex];
            branches.computeIfAbsent(key, k -> new ArrayList<>()).add(data[i]);
        }

        for (Map.Entry<String, List<String[]>> entry : branches.entrySet()) {
            String key = entry.getKey();
            List<String[]> subData = entry.getValue();

            if (isPure(subData)) {
                root.addChild(new Node(-1, data[0][rootIndex] + "=" + key + subData.get(0)[data[0].length - 1], true));
            } else {
                String[][] subArray = new String[subData.size() + 1][data[0].length];
                subArray[0] = data[0];
                for (int i = 0; i < subData.size(); i++) {
                    subArray[i + 1] = subData.get(i);
                }

                Node child = buildTree(subArray);
                child.value = data[0][rootIndex] + "=" + key;
                root.addChild(child);
            }
        }

        return root;
    }

    // Kiểm tra nếu tất cả các giá trị quyết định trong nhánh con giống nhau (Yes hoặc No)
    private static boolean isPure(List<String[]> data) {
        String firstValue = data.get(0)[data.get(0).length - 1];
        for (String[] row : data) {
            if (!row[row.length - 1].equals(firstValue)) {
                return false;
            }
        }
        return true;
    }

    // Hàm chính
    public static void main(String[] args) throws FileNotFoundException {
        String[][] data = ProcessingData.processingData("AI/dataset/small_data.csv");
        Node treeRoot = buildTree(data);

        JFrame frame = new JFrame("Decision Tree Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TreePanel treePanel = new TreePanel(treeRoot);

        JScrollPane scrollPane = new JScrollPane(treePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane);

        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
