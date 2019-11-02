package com.shl.command;


import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellCommand {
	public String executeCommand(String command) {
		System.out.println("Command executing : " + command);
		String output="OutPut : \n";
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader stdInput = new BufferedReader(new
					InputStreamReader(p.getInputStream()));

			BufferedReader stdError = new BufferedReader(new
					InputStreamReader(p.getErrorStream()));

// read the output from the command
			System.out.println("Here is the standard output of the command:\n");
			String s = null;
			while ((s = stdInput.readLine()) != null) {
				output=output.concat(s).concat("\n");
			}
			output=output.concat("\n And Error : \n");
// read any errors from the attempted command
			System.out.println("Here is the standard error of the command (if any):\n");
			while ((s = stdError.readLine()) != null) {
				output=output.concat(s).concat("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return output;

	}
}
