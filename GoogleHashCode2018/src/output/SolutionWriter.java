/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package output;

import domain.Ride;
import domain.SelfDrivingRides;
import domain.Vehicle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Bart
 */
public class SolutionWriter
{
    public final String OutputFile = "output.txt";
    
    public void writeSolution (Map<Vehicle, List<Ride>> carRides) {
        writeSolution(carRides, OutputFile);
    }
    
    public void writeSolution (Map<Vehicle, List<Ride>> carRides,String outputFileName) {
        PrintWriter pw = null;
        try {
            System.out.println("OUTPUT FILE");
            pw = new PrintWriter(new File(outputFileName));
            for (List<Ride> rides : carRides.values())
            {
                pw.write(String.valueOf(rides.size())); // string value else it converts int ot char
                for (Ride ride : rides)
                {
                    pw.write(" " + ride.id);
                }
                pw.println();
            }
            
            pw.flush();
            
            System.out.println("OUTPUT FILE WRITTEN");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SolutionWriter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pw.close();
        }
    }
}
