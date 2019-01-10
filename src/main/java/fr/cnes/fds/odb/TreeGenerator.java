package fr.cnes.fds.odb;

import fr.cnes.fds.odb.model.Branch;
import fr.cnes.fds.odb.model.Leaf;
import fr.cnes.fds.odb.model.Root;
import org.apache.commons.lang.math.RandomUtils;

import java.util.UUID;

public class TreeGenerator {

    private int maxVertices = 10000;
    private int maxDepth = 20;

    public TreeGenerator() {
    }

    public TreeGenerator(int maxVertices, int maxDepth) {
        this.maxVertices = maxVertices;
        this.maxDepth = maxDepth;
    }

    public Root generateTree() {

        // Init counters
        int vertexCount = 1;

        // Root
        Root root = new Root("generated_root");

        while (vertexCount < maxVertices) {
            vertexCount += generateRootBranches(root);
        }

        return root;
    }

    private int generateRootBranches(Root root) {

        // vertices generated
        int generated = 0;

        // current depth
        int currentDepth = 1;

        // branches : 1, 2 or 3
        int nb_branches = RandomUtils.nextInt(3) + 1;

        // looping over branches
        for (int i = 0; i < nb_branches; i++) {

            // Create branch
            Branch branch = new Branch("branch_" + UUID.randomUUID().toString());
            generated++;

            // Add sub-branch
            generated += generateBranches(branch, currentDepth);

            // Add leaves on branch
            generated += generateLeaves(branch);

            // Add to root
            root.getBranches().add(branch);
        }

        return generated;
    }

    private int generateBranches(Branch inputBranch, int currentDepth) {

        // generated vertices
        int generated = 0;

        // update current depth
        int depth = currentDepth + 1;

        // Checking depth
        if (depth <= maxDepth) {

            // branches : 1, 2 or 3
            int nb_branches = RandomUtils.nextInt(3) + 1;

            // looping over branches
            for (int i = 0; i < nb_branches; i++) {

                // Create branch
                Branch subbranch = new Branch("branch_" + UUID.randomUUID().toString());
                generated++;

                // Add sub-branches
                generated += generateBranches(subbranch, depth);

                // Add leaves on branch
                generated += generateLeaves(inputBranch);

                // Add to root
                inputBranch.getBranches().add(subbranch);
            }

        }

        return generated;

    }

    private int generateLeaves(Branch branch) {
        int nb_leaves = RandomUtils.nextInt(3) + 1;
        for (int j = 0; j < nb_leaves; j++) {
            Leaf leaf = new Leaf("leaf_" + UUID.randomUUID().toString(), RandomUtils.nextLong());
            branch.getLeaves().add(leaf);
        }
        return nb_leaves;
    }

    public void setMaxVertices(int maxVertices) {
        this.maxVertices = maxVertices;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

}
