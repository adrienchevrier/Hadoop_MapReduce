import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Pivot {

	public static void main(String[] args) throws Exception
	{

		/*Initialisation*/
		BufferedReader br = new BufferedReader(new FileReader("out/production/pivot_project/test.csv"));
		String ligne = null;
		/*table containing csv file*/
		ArrayList<ArrayList<String>>table = new ArrayList<ArrayList<String>>();



		/*Read each line in csv file*/
		while ((ligne = br.readLine()) != null)
		{


			/*Read next line stored in buffer*/
			String[] row =  ligne.split(",");
			/*add row to table*/
			table.add(new ArrayList<>(Arrays.asList(row)));
		}
		br.close();

		/*Print table*/
		System.out.println("table :"+table);
		System.out.println("Size = "+ table.size());
		System.out.println("Size = "+ table.get(0).size());
		for (int i=0; i<table.size();i++)
			System.out.println(table.get(i));

		/*create pivot table*/
		ArrayList<ArrayList<String>>pivotTable = new ArrayList<ArrayList<String>>();
		ArrayList<String> pivotStrange = new ArrayList<>();

		/*loop for each row*/
		for (int i = 0; i < table.get(0).size(); i++) {
			ArrayList<String> row = new ArrayList<>();
			StringBuilder text= new StringBuilder();
			/*Copy column value in array*/
			for (int j = 0; j < table.size(); j++){
				row.add(table.get(j).get(i));
				text.append(",").append(table.get(j).get(i));
			}
			/*put column array in pivot table*/
			pivotTable.add(row);
			pivotStrange.add(text.toString());
		}

		/*Print table*/
		System.out.println("pivotTable:");
		for (int i=0; i<pivotTable.size();i++)
			System.out.println(pivotStrange.get(i));


	}
}
