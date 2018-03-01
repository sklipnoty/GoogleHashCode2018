
import domain.SelfDrivingRides;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import output.SolutionWriter;
import solver.Solver;

public class StartUp {

    private static final String INPUT_FILE1 = "resources/b_should_be_easy.in";
    private static final String INPUT_FILE2 = "resources/c_no_hurry.in";
    private static final String INPUT_FILE3 = "resources/d_metropolis.in";
    private static final String INPUT_FILE4 = "resources/e_high_bonus.in";

    public static void main(String[] args) throws IOException {

        SelfDrivingRides problem1 = new SelfDrivingRides(new Scanner(Paths.get(INPUT_FILE1).toAbsolutePath()));
        SelfDrivingRides problem2 = new SelfDrivingRides(new Scanner(Paths.get(INPUT_FILE2).toAbsolutePath()));
        SelfDrivingRides problem3 = new SelfDrivingRides(new Scanner(Paths.get(INPUT_FILE3).toAbsolutePath()));
        SelfDrivingRides problem4 = new SelfDrivingRides(new Scanner(Paths.get(INPUT_FILE4).toAbsolutePath()));
//
//        Solver solv1 = new Solver(problem1,"b");
          SolutionWriter solutionWriter = new SolutionWriter();
//        solutionWriter.writeSolution(solv1.solution, "b");
//
//        Solver solv2 = new Solver(problem2, "c");
//        solutionWriter.writeSolution(solv2.solution, "c");
//        
//        Solver solv3 = new Solver(problem3, "d");
//        solutionWriter.writeSolution(solv3.solution, "d");
        
        Solver solv4 = new Solver(problem4, "e");
        solutionWriter.writeSolution(solv4.solution, "e");
    }
}
