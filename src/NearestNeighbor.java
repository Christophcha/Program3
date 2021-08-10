import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NearestNeighbor {

	public static void main(String[] args) throws FileNotFoundException {

		double[][] testingValues = new double[75][4];
		double[][] trainingValues = new double[75][4];
		String[] testingClass = new String[75];
		String[] trainingClass = new String[75];

		Scanner scan = new Scanner(System.in);

		// prints header
		System.out.println("Programming Fundamentals \nNAME: Christopher Chakchay \nPROGRAMMING ASSIGNMENT 3 \n");
		
		// prompts user to enter training file until they call the correct file.
		System.out.print("Enter the name of the training file: ");
		String training = scan.nextLine();
		
		while (!training.equals("iris-training-data.csv")) {
			System.out.print("There is no file under that name, please enter training file again: ");
			training = scan.nextLine();
		}
		
		// prompts user to enter testing file until they call the correct file.
		System.out.print("Enter the name of the testing file: ");
		String testing = scan.nextLine();
		
		while (!testing.equals("iris-testing-data.csv")) {
			System.out.print("There is no file under that name, please enter testing file again: ");
			testing = scan.nextLine();
		}

		System.out.println("");
		
		// imports training file
		File trainingFile = new File(
				"C:\\Users\\Christopher\\eclipse-workspace\\Program3\\src\\iris-training-data.csv");
		Scanner scanTrainingFile = new Scanner(trainingFile);

		String[] lineArrayTraining;
		int trainingIndex = 0;

		// reads in training file
		while (scanTrainingFile.hasNextLine()) {
			lineArrayTraining = scanTrainingFile.nextLine().split(",");
			for (int i = 0; i < 4; i++) {
				trainingValues[trainingIndex][i] = Double.parseDouble(lineArrayTraining[i]);
			}
			trainingClass[trainingIndex] = lineArrayTraining[4];
			trainingIndex++;

		}
		// imports testing file
		File testingFile = new File("C:\\Users\\Christopher\\eclipse-workspace\\Program3\\src\\iris-testing-data.csv");
		Scanner scanTestingFile = new Scanner(testingFile);

		String[] lineArrayTesting;
		int testingIndex = 0;

		// reads in testing file
		while (scanTestingFile.hasNextLine()) {
			lineArrayTesting = scanTestingFile.nextLine().split(",");
			for (int i = 0; i < 4; i++) {
				testingValues[testingIndex][i] = Double.parseDouble(lineArrayTesting[i]);
			}
			testingClass[testingIndex] = lineArrayTesting[4];
			testingIndex++;

		}
		scanTestingFile.close();
		scanTrainingFile.close();
		scan.close();

		// calls classification method
		classify(testingValues, trainingValues, trainingClass, testingClass);

	}

	public static double distance(double[] testing, double[] training) {
		
		// distance algorithm
		double distance = Math.sqrt((Math.pow((testing[0] - training[0]), 2) + Math.pow((testing[1] - training[1]), 2)
				+ Math.pow((testing[2] - training[2]), 2) + Math.pow((testing[3] - training[3]), 2)));

		return distance;

	}

	public static void accuracy(String[] testingClass, String[] predictedValues) {

		double correct = 0;
		double accurate = 0;

		// run through true value(testing class) and checks if its equal with predicted values
		for (int i = 0; i < 75; i++) {
			if (testingClass[i].equals(predictedValues[i])) {
				correct++;
			}
			accurate = (correct / 75); // divides correct with total tested to get accuracy

		}
		System.out.println("ACCURACY: " + accurate);

	}

	public static void classify(double[][] testingValues, double[][] trainingValues, String[] trainingClass,
			String[] testingClass) {

		String[] predictedValues = new String[75];
		double closestDistance = Double.MAX_VALUE;
		double currentDistance;
		int currentIndex = 0;
		int classificationIndex = 0;
		double[] testingRow = new double[4];
		double[] trainingRow = new double[4];

		for (int i = 0; i < testingValues.length; i++) {
			for (int j = 0; j < testingValues[0].length; j++) {
				testingRow[j] = testingValues[i][j];
			}

			for (int k = 0; k < trainingValues.length; k++) {
				for (int l = 0; l < trainingValues[0].length; l++) {
					trainingRow[l] = trainingValues[k][l];
				}

				currentDistance = distance(testingRow, trainingRow);
				if (currentDistance < closestDistance) {
					closestDistance = currentDistance;
					classificationIndex = k;
				}
				predictedValues[currentIndex] = trainingClass[classificationIndex];
			}
			currentDistance = 0.0;
			closestDistance = Double.MAX_VALUE;
			currentIndex++;

		}
		System.out.println("EX#: TRUE LABEL, PREDICTED LABEL");
		
		for (int i = 0; i < predictedValues.length; i++) {
			System.out.println((i + 1) + ": " + testingClass[i] + " " + predictedValues[i]);
		}
		accuracy(testingClass, predictedValues);
	}

}
