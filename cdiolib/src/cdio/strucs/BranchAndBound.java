package cdio.strucs;

import cdio.math.distributions.BinghamDistribution;
import cdio.math.misc.BinghamNorm1F1;
import cdio.math.space.SaupeTensorVector;
import libprotnmr.math.Quaternion;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BranchAndBound {

    // Data members:
    public SaupeTensorVector saupeAligned1;
    public SaupeTensorVector saupeUnaligned1;
    public SaupeTensorVector saupeAligned2;
    public SaupeTensorVector saupeUnaligned2;
    public ArrayList<RegionR3> candidates;
    public int R3BranchDepth;


    public BranchAndBound(SaupeTensorVector saupeAligned1, SaupeTensorVector saupeAligned2, SaupeTensorVector saupeUnaligned1,
                          SaupeTensorVector saupeUnaligned2) {
        this.saupeAligned1 = saupeAligned1;
        this.saupeUnaligned1 = saupeUnaligned1;
        this.saupeAligned2 = saupeAligned2;
        this.saupeUnaligned2 = saupeUnaligned2;
        R3BranchDepth = 4;
    }
    // Member methods:
    public void setR3BranchDepth(int i) { R3BranchDepth = i; }

    // Run the algorithm to find candidates:
    public void runBnB() throws  IOException {
        step1();
        step2();
        step3();
    }

    // Step 1 pruning
    public void step1() throws IOException {
        // Setup total space in R3:
        RegionR3 totalSpace = new RegionR3(-4, -4, -4, 4, 4, 4);

        // define arrays we'll need later:
        ArrayList<RegionR3> currentRegions = new ArrayList<>();

        // add total space to list of current regions
        currentRegions.add(totalSpace);

        // Loop for all levels of branching:
        for(int i = 1; i <= R3BranchDepth; i++) {

            // list to save new regions after branching!
            ArrayList<RegionR3> newRegions = new ArrayList<>();

            // For all current regions:
            for(RegionR3 r: currentRegions) {

                // branch out every current region
                ArrayList<RegionR3> childrenRegions = r.branchEven(RegionR3.GetBranchSize(), RegionR3.GetBranchSize(),
                        RegionR3.GetBranchSize());
                for(RegionR3 cr: childrenRegions) {
                    // add each child branch that is not prunable into new regions
                    if(!cr.isPrunable1(saupeUnaligned1, saupeAligned1)) {
                        newRegions.add(cr);
                    }
                }
            }
            currentRegions = newRegions;
        }

        candidates = currentRegions;
    }

    // Step 2 pruning
    public void step2() {
        for(RegionR3 r: candidates) {
            r.pruneStep2(saupeUnaligned1, saupeAligned1);
        }

        ArrayList<RegionR3> rn = new ArrayList<>();
        for(RegionR3 r: candidates) {
            if(r.allowedLS3 != null)
                rn.add(r);
        }
        candidates = rn;
    }

    // TODO: Step 3 pruning
    public void step3() {
        ArrayList<BinghamDistribution> solutions = generateSolutions();
        for(BinghamDistribution b: solutions) {

        }
    }

    // TODO: Optimize squared error:
    public ArrayList<BinghamDistribution> sortSE(ArrayList<BinghamDistribution> unsorted) {
        return null;
    }

    // Generate all solutions from the candidates:
    public ArrayList<BinghamDistribution> generateSolutions() {
        ArrayList<BinghamDistribution> solutions = new ArrayList<>();

        for(RegionR3 r: candidates) {
            for(RegionS3 s: r.allowedLS3) {
                double lambda1 = (r.getXLower() + r.getXUpper())/2;
                double lambda2 = (r.getYLower() + r.getYUpper())/2;
                double lambda3 = (r.getZLower() + r.getZUpper())/2;
                double q0 = Math.cos((s.psi_lower + s.psi_upper)/2);
                double q1 = Math.sin((s.psi_lower + s.psi_upper)/2) * Math.cos((s.theta_lower + s.theta_upper)/2);
                double q2 = Math.sin((s.psi_lower + s.psi_upper)/2) * Math.sin((s.theta_lower + s.theta_upper)/2) *
                        Math.cos((s.phi_lower+s.phi_upper)/2);
                double q3 = Math.sin((s.psi_lower + s.psi_upper)/2) * Math.sin((s.theta_lower + s.theta_upper)/2) *
                        Math.sin((s.phi_lower+s.phi_upper)/2);
                Quaternion qL = new Quaternion(q0, q1, q2, q3);

                solutions.add(new BinghamDistribution(qL, lambda1, lambda2, lambda3, null));
            }
        }

        return solutions;
    }

}
