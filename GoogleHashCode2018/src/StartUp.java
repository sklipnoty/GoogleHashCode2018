
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

        SolutionWriter solutionWriter = new SolutionWriter();

      /*  ThreadedSolver threadedSolver1 = new ThreadedSolver(problem1);
        solutionWriter.writeSolution(threadedSolver1.solve(), "b");

        ThreadedSolver threadedSolver2 = new ThreadedSolver(problem2);
        solutionWriter.writeSolution(threadedSolver2.solve(), "c"); */

        ThreadedSolver threadedSolver3 = new ThreadedSolver(problem3);
        solutionWriter.writeSolution(threadedSolver3.solve(), "d");
        
        ThreadedSolver threadedSolver4 = new ThreadedSolver(problem4);
        solutionWriter.writeSolution(threadedSolver4.solve(), "e");

        System.exit(0);
    }
}
