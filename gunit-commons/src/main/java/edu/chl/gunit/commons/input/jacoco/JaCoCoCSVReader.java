package edu.chl.gunit.commons.input.jacoco;

import edu.chl.gunit.commons.api.ApiJaCoCoResult;
import edu.chl.gunit.commons.csv.CSV;
import edu.chl.gunit.commons.csv.CSVReader;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by davida on 5.2.2015.
 */
public class JaCoCoCSVReader {

    public List<ApiJaCoCoResult> read(File file) throws JaCoCoResultException {
        try {
            return read(FileUtils.fileRead(file));
        } catch (IOException e) {
            Logger.getLogger("JaCoCoCSVReader").log(Level.SEVERE, e.getMessage());
            throw new JaCoCoResultException("Unable to open jacoco csv file from path " + file.getAbsolutePath());
        }
    }

    public List<ApiJaCoCoResult> read(String data) {
        CSVReader reader = new CSVReader();
        CSV csv = reader.read(data, true);
        List<ApiJaCoCoResult> out = new ArrayList<ApiJaCoCoResult>();

        for (List<String> row : csv.getRows()) {
            String groupName = row.get(0);
            String packageName = row.get(1);
            String className = row.get(2);

            ApiJaCoCoResult result = new ApiJaCoCoResult(className, groupName, packageName);
            result.setDate(new Date());
            result.setInstructionMissed(Integer.parseInt(row.get(3)));
            result.setInstructionCovered(Integer.parseInt(row.get(4)));
            result.setBranchMissed(Integer.parseInt(row.get(5)));
            result.setBranchCovered(Integer.parseInt(row.get(6)));
            result.setLineMissed(Integer.parseInt(row.get(7)));
            result.setLineCovered(Integer.parseInt(row.get(8)));
            result.setComplexityMissed(Integer.parseInt(row.get(9)));
            result.setComplexityCovered(Integer.parseInt(row.get(10)));
            result.setMethodMissed(Integer.parseInt(row.get(11)));
            result.setMethodCovered(Integer.parseInt(row.get(12)));

            out.add(result);
        }

        return out;
    }
}
