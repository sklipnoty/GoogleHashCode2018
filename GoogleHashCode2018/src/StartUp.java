
import domain.SelfDrivingRides;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import output.SolutionWriter;
import solver.Solver;
import solver.Solver2;
import solver.ThreadedSolver;

public class StartUp {

    private static final String INPUT_FILE1 = "resources/b_should_be_easy.in";
    private static final String INPUT_FILE2 = "resources/c_no_hurry.in";
    private static final String INPUT_FILE3 = "resources/d_metropolis.in";
    private static final String INPUT_FILE4 = "resources/e_high_bonus.in";

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

        SelfDrivingRides problem1 = new SelfDrivingRides(new Scanner(Paths.get(INPUT_FILE1).toAbsolutePath()));
        SelfDrivingRides problem2 = new SelfDrivingRides(new Scanner(Paths.get(INPUT_FILE2).toAbsolutePath()));
        SelfDrivingRides problem3 = new SelfDrivingRides(new Scanner(Paths.get(INPUT_FILE3).toAbsolutePath()));
        SelfDrivingRides problem4 = new SelfDrivingRides(new Scanner(Paths.get(INPUT_FILE4).toAbsolutePath()));
//
//        Solver2 solv1 = new Solver2(problem1, "b");
//        SolutionWriter solutionWriter = new SolutionWriter();
//        solutionWriter.writeSolution(solv1.solution, "b");
////
//        Solver2 solv2 = new Solver2(problem2, "c");
//        solutionWriter.writeSolution(solv2.solution, "c");
////        
//        Solver2 solv3 = new Solver2(problem3, "d");
//        solutionWriter.writeSolution(solv3.solution, "d");
//
//        Solver2 solv4 = new Solver2(problem4, "e");
//        solutionWriter.writeSolution(solv4.solution, "e");

        SolutionWriter solutionWriter = new SolutionWriter();
        ThreadedSolver threadedSolver = new ThreadedSolver(problem4);
        solutionWriter.writeSolution(threadedSolver.solve(), "e");
    }
}
