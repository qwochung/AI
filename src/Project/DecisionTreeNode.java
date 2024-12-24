package Project;

import java.util.HashMap;
import java.util.Map;

class DecisionTreeNode {
    private String attribute;
    private String label;
    private Map<String, DecisionTreeNode> children;

    public DecisionTreeNode(String value) {
        if (value != null && value.contains(":")) {
            this.attribute = null;
            this.label = value;
        } else {
            this.attribute = value;
            this.label = null;
        }
        this.children = new HashMap<>();
    }

    public boolean isLeaf() {
        return label != null;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getLabel() {
        return label;
    }

    public void addChild(String value, DecisionTreeNode child) {
        children.put(value, child);
    }

    public DecisionTreeNode getChild(String value) {
        return children.get(value);
    }
}
