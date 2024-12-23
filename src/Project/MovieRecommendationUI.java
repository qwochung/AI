package Project;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MovieRecommendationUI extends JFrame {

    private static Map<String, Set<String>> uniqueValues = new LinkedHashMap<>();
    private static final List<String> movieImages = Arrays.asList(
            "AI/dataset/hinh-nen-7-vien-ngoc-rong-dep-nhat-17.jpg",
            "AI/dataset/image.jpg",
            "AI/dataset/Cac-yeu-to-giup-poster-phim-thanh-cong.jpg",
            "AI/dataset/89c9c2109283153.5fd0670528916.png",
            "AI/dataset/MV5BMmY5ZDc3OTgtYzlmNi00N2VmLWFjYTUtODk3ODFhNjIwNjY2XkEyXkFqcGdeQXVyMTExNzgzMDY3._V1_FMjpg_UX1000_.jpg",
            "AI/dataset/tac-gia-thiet-ke-poster-phim-_161695124133.jpg"
    );
    private static final Random random = new Random();

    public static void main(String[] args) {
        String filePath = "AI/dataset/small_data.csv"; // Đường dẫn file thực tế
        String[][] data = readDataAsArray(filePath);
        if (data != null) {
            calculateDecisionTree(data);
        }
        System.out.println("Dataset:");
        for (String[] row : data) {
            System.out.println(Arrays.toString(row));
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("🎥 Luxury Movie Recommendation System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 900);
            frame.setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(new Color(40, 40, 40)); // Nền tối

            // Header Panel
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setPreferredSize(new Dimension(1200, 80));
            headerPanel.setBackground(new Color(40, 40, 40)); // Nền tối
            headerPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(212, 212, 212))); // Viền sáng

            JLabel titleLabel = new JLabel("Luxury Movie Recommendation", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
            titleLabel.setForeground(Color.WHITE); // Chữ sáng
            headerPanel.add(titleLabel, BorderLayout.CENTER);

            mainPanel.add(headerPanel, BorderLayout.NORTH);

            // Panel bộ lọc
            JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
            filterPanel.setPreferredSize(new Dimension(1200, 120));
            filterPanel.setBackground(new Color(50, 50, 50)); // Nền tối cho bộ lọc
            filterPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(126, 122, 122), 2), // Độ dày viền là 2
                    "Filters",
                    0,
                    0,
                    new Font("Segoe UI", Font.BOLD, 18),
                    new Color(255, 255, 255)
            ));


            Map<String, JComboBox<String>> comboBoxes = new LinkedHashMap<>();
            for (Map.Entry<String, Set<String>> entry : uniqueValues.entrySet()) {
                String columnName = entry.getKey();
                if (!columnName.equalsIgnoreCase("Name") && !columnName.equalsIgnoreCase("Recommended")) {
                    JLabel label = new JLabel(columnName + ":");
                    label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                    label.setForeground(Color.WHITE); // Chữ sáng cho nhãn

                    JComboBox<String> comboBox = new JComboBox<>();
                    comboBox.addItem("All"); // Giá trị mặc định
                    for (String value : entry.getValue()) {
                        comboBox.addItem(value);
                    }
                    comboBoxes.put(columnName, comboBox);

                    filterPanel.add(label);
                    filterPanel.add(comboBox);
                }
            }

            JButton filterButton = new JButton("Apply Filters");
            filterButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
            filterButton.setPreferredSize(new Dimension(150, 40));
            filterButton.setBackground(new Color(255, 255, 255)); // Màu nền sáng cho nút
            filterButton.setForeground(Color.BLACK); // Màu chữ sáng cho nút
            filterButton.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2, true));
            filterButton.setFocusPainted(false);

            filterPanel.add(filterButton);

            // Panel hiển thị phim
            JPanel moviePanel = new JPanel();
            moviePanel.setLayout(new GridLayout(0, 4, 20, 20)); // Lưới với tối đa 4 cột
            moviePanel.setBackground(new Color(40, 40, 40)); // Nền tối

            JScrollPane scrollPane = new JScrollPane(moviePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
            scrollPane.setBorder(BorderFactory.createLineBorder(new Color(212, 212, 212), 0));
            scrollPane.setBorder(BorderFactory.createLineBorder(new Color(212, 212, 212), 2, true));

            filterButton.addActionListener(e -> {
                moviePanel.removeAll();

                if (data != null) {
                    calculateDecisionTree(data); // Áp dụng thuật toán cây quyết định
                    List<String[]> filteredMovies = filterMovies(comboBoxes);

                    if (filteredMovies.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Không tìm thấy phim phù hợp với bộ lọc.");
                    } else {
                        for (String[] movie : filteredMovies) {
                            addMovieToPanel(movie, moviePanel);
                        }
                    }
                }

                moviePanel.revalidate();
                moviePanel.repaint();
            });

            mainPanel.add(filterPanel, BorderLayout.NORTH);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }

    private static void addMovieToPanel(String[] movie, JPanel moviePanel) {
        JPanel movieItem = new JPanel();
        movieItem.setLayout(new BorderLayout());
        movieItem.setBorder(BorderFactory.createLineBorder(new Color(212, 212, 212), 1)); // Viền sáng
        movieItem.setBackground(new Color(50, 50, 50)); // Nền tối cho item phim
        movieItem.setPreferredSize(new Dimension(200, 300));

        JLabel movieImageLabel = new JLabel();
        movieImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        movieImageLabel.setIcon(new ImageIcon(movieImages.get(random.nextInt(movieImages.size())))); // Lựa chọn hình ảnh ngẫu nhiên

        JLabel movieNameLabel = new JLabel("Tên phim: " + movie[0], SwingConstants.CENTER);
        movieNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        movieNameLabel.setForeground(Color.WHITE); // Chữ sáng cho tên phim

        JLabel genreLabel = new JLabel("Thể loại: " + movie[1], SwingConstants.CENTER);
        genreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        genreLabel.setForeground(new Color(200, 200, 200)); // Chữ sáng nhạt cho thể loại

        movieItem.add(movieImageLabel, BorderLayout.CENTER);
        movieItem.add(movieNameLabel, BorderLayout.NORTH);
        movieItem.add(genreLabel, BorderLayout.SOUTH);

        moviePanel.add(movieItem);
    }

    private static String[][] readDataAsArray(String filePath) {
        uniqueValues.clear(); // Xóa các giá trị trước đó để tránh lỗi dữ liệu
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine();
            if (headerLine != null) {
                String[] headers = headerLine.split(",");
                rows.add(headers); // Thêm tiêu đề vào dữ liệu
                for (String header : headers) {
                    uniqueValues.put(header.trim(), new TreeSet<>()); // Khởi tạo tập giá trị duy nhất
                }

                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    rows.add(values);

                    for (int i = 0; i < headers.length; i++) {
                        uniqueValues.get(headers[i].trim()).add(values[i].trim());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!rows.isEmpty()) {
            String[][] data = new String[rows.size()][rows.get(0).length];
            for (int i = 0; i < rows.size(); i++) {
                data[i] = rows.get(i);
            }
            return data;
        }
        return null;
    }

    private static List<String[]> filterMovies(Map<String, JComboBox<String>> comboBoxes) {
        List<String[]> filteredMovies = new ArrayList<>();
        String filePath = "AI/dataset/small_data.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine();
            if (headerLine != null) {
                String[] headers = headerLine.split(",");

                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    boolean matches = true;

                    for (int i = 0; i < headers.length; i++) {
                        String header = headers[i].trim();
                        if (comboBoxes.containsKey(header)) {
                            String selectedValue = (String) comboBoxes.get(header).getSelectedItem();
                            if (!selectedValue.equalsIgnoreCase("All") && !values[i].trim().equalsIgnoreCase(selectedValue.trim())) {
                                matches = false;
                                break;
                            }
                        }
                    }

                    if (matches) {
                        filteredMovies.add(values);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filteredMovies;
    }

    private static void calculateDecisionTree(String[][] data) {
        String[][] dataCopy = deepCopy(data);

        if (data == null || data.length <= 1) {
            System.out.println("Dataset is empty or invalid.");
            return;
        }

        String[] decisionColumn = getColumn(data, data[0].length - 1);
        System.out.println("Full Dataset:");
        for (String[] row : data) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("Decision Column: " + Arrays.toString(decisionColumn));

        double datasetEntropy = Entropy.calculateEntropy(decisionColumn);
        System.out.println("Entropy of Dataset: " + datasetEntropy);

        int rootIndex = Gain.informationGain(data, datasetEntropy, decisionColumn);
        System.out.println("Calculated Root Index: " + rootIndex);
        if (rootIndex != -1 && rootIndex < data[0].length) {
            System.out.println("Root Attribute (Column Title): " + data[0][rootIndex]);
        } else {
            System.out.println("Invalid Root Index or No Valid Attribute Found.");
        }

    }

    private static String[][] deepCopy(String[][] original) {
        if (original == null) return null;
        String[][] copy = new String[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }

    private static String[] getColumn(String[][] data, int colIndex) {
        return ProcessingData.getCol(data, colIndex);
    }
}

class CustomScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = new Color(212, 212, 212); // Màu thumb sáng
        this.trackColor = new Color(50, 50, 50); // Màu track tối
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }
}
