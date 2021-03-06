package cdio.strucs;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.ArrayList;
import java.util.List;

public class BnBTree {

    // Data members:
    public Graph<RegionVertex, DefaultEdge> tree;
    public int nodeCount;
    public RegionVertex root;


    public BnBTree() {
        this.tree = new SimpleDirectedGraph<>(DefaultEdge.class);
        this.nodeCount = 0;
        this.root = null;
    }

    // Member methods:
    public boolean isEmpty() { return (this.nodeCount == 0); }

    public int getNodeCount() { return nodeCount; }

    public RegionVertex getRoot() { return root; }

    public void setRoot(RegionVertex root) {
        this.root = root;
        if(!isEmpty()) {
            this.tree.addEdge(root, this.root);
        }
        this.nodeCount++;
    }

    public List<RegionVertex> getChildren(RegionVertex node) {
        return Graphs.successorListOf(this.tree, node);
    }

    public RegionVertex getParent(RegionVertex node) {
        List<RegionVertex> parents = Graphs.predecessorListOf(this.tree, node);
        if(parents.size()>1)
            throw new IllegalArgumentException("Not a tree - multiple parents found.");
        if(parents.isEmpty())
            return null;
        return parents.get(0);
    }

    public void branch(RegionVertex branchRoot, int maxBranchDepth) {

        if(isEmpty())   // Raise error if tree is empty
            throw new IllegalCallerException("Cannot branch an empty tree.");

        // Ensure that branching root should not have any branches already
        if(tree.outgoingEdgesOf(branchRoot).size() != 0)
            throw new IllegalCallerException("Branch root already has branches.");

        // All okay, branch now till depth maxBranchDepth:
        int currentDepth = 0;
        RegionVertex localRoot = branchRoot;
        List<RegionVertex> currentNodesToBranch = new ArrayList<RegionVertex>();
        List<RegionVertex> newNodesToBranch = new ArrayList<RegionVertex>();
        currentNodesToBranch.add(branchRoot);

        while(currentDepth <= maxBranchDepth) {
            for(RegionVertex node:currentNodesToBranch) {
                // For each node to be branched, branch node:
                // Get the list of children of node:
                List<RegionVertex> children = RegionVertex.ConvertToRegionVertices(
                        node.regionR3.branchEven(RegionR3.GetBranchSize(), RegionR3.GetBranchSize(),
                        RegionR3.GetBranchSize()));
                // Add edge from node to its children:
                for(RegionVertex child:children) {
                    this.tree.addEdge(node, child);
                    nodeCount++;
                }
                // Add children node to new nodes to be branched:
                newNodesToBranch.addAll(children);
            }
            // Increase node depth and set current nodes to branch to new nodes:
            currentDepth++;
            currentNodesToBranch = newNodesToBranch;
            newNodesToBranch = new ArrayList<RegionVertex>();
        }
    }

    // Static methods:

}
