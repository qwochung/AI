package Project;

import javax.swing.*;
import java.awt.*;

public class GraphTree {


    public static class TreePanel extends JPanel {
        Node root; // Gốc của cây

        public TreePanel(Node root) {
            this.root = root;
            setBackground(Color.WHITE); // Màu nền trắng
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Sử dụng Graphics2D để cải thiện chất lượng vẽ
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (root != null) {
                drawNode(g2d, root, getWidth() / 2, 50, getWidth() / 4);
            }
        }

        private void drawNode(Graphics2D g2d, Node node, int x, int y, int xOffset) {
            int nodeRadius = 20; // Bán kính của node

            // Cài đặt màu sắc cho nút
            Color nodeColor = node.isSuggest ? new Color(255, 153, 51) : new Color(102, 204, 255);
            Color borderColor = new Color(0, 102, 204);

            // Vẽ nút (hình tròn với hiệu ứng đổ bóng)
            g2d.setColor(new Color(0, 0, 0, 50)); // Đổ bóng
            g2d.fillOval(x - nodeRadius - 3, y - nodeRadius - 3, 2 * nodeRadius + 6, 2 * nodeRadius + 6);

            g2d.setColor(nodeColor); // Màu chính của nút
            g2d.fillOval(x - nodeRadius, y - nodeRadius, 2 * nodeRadius, 2 * nodeRadius);

            g2d.setColor(borderColor); // Viền nút
            g2d.drawOval(x - nodeRadius, y - nodeRadius, 2 * nodeRadius, 2 * nodeRadius);

            // Hiển thị giá trị trong nút
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(node.value);
            g2d.drawString(node.value, x - textWidth / 2, y + 5);

            // Vẽ liên kết tới nút kế tiếp
            if (node.next != null) {
                int childX = x - xOffset;
                int childY = y + 100;

                // Tính toán điểm bắt đầu và kết thúc cạnh nối
                int startX = x + (childX > x ? nodeRadius : -nodeRadius); // Lùi ra ngoài viền nút
                int startY = y + nodeRadius;

                int endX = childX + (childX > x ? -nodeRadius : nodeRadius);
                int endY = childY - nodeRadius;

                // Vẽ đường thẳng giữa các nút
                g2d.setColor(Color.GRAY);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(startX, startY, endX, endY);

                // Vẽ nút kế tiếp
                drawNode(g2d, node.next, childX, childY, xOffset / 2);
            }
        }
    }

    public static void main(String[] args) {
        // Tạo cây mẫu
        Node node3 = new Node(3, "Node 3", null, true);
        Node node2 = new Node(2, "Node 2", node3, false);
        Node node1 = new Node(1, "Node 1", node2, false);
        Node root = new Node(0, "Root", node1, false);

        // Hiển thị cây
        JFrame frame = new JFrame("Graph Tree Recursive");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new TreePanel(root));
        frame.setVisible(true);
    }
}
