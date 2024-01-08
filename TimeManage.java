import java.util.*;

public class TimeManage {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        int[] arr = lengthOfDay(console); //use this to tell user in next method how much time they have left to get a goal done
        String goal = Goal(console);
        int goalLength = GoalTime(console);
        goalTracker(console, goal, goalLength, arr);
    }

    // This code prompts the user to enter a specified interval in which they want to get something done
    // and does a calculation based on if the input is am-pm or to pm-pm, however, not am-am in the next day since this
    // is strictly for people with normal schedules
    public static int[] lengthOfDay (Scanner console) {
        // this array will be returned and contains the the hour of the beginning times hour and mins, then ending's. The fifth cubby will contain the total time 
        // of the beginning of the day to the end of the day
        int[] arr = new int[3];
        System.out.println("Hi Welcome to Time Manage! This is used to create goals and help manage time throughout a specified interval"); 
        System.out.println("This program assumes you are a sane person and wake up at the 'am' time or late 'pm' and sleep no later than 1:00 am");
        System.out.println("Please specify pm or am when asked for the time. Keep in mind this program is case sensitive"); 
        System.out.println("Format such as 'X:XX pm' or 'XX:XX am' is required");
        System.out.println("Finally, if you are working on your goal when asked, please say 'goal'");
        System.out.println();

        System.out.print("When would you like to start your day? ");
        String start = console.nextLine();

        while (!start.endsWith("am") && !start.endsWith("pm")) { 
            System.out.println("Please specify pm or am");
            System.out.print("When would you like to start your day? ");
            start = console.nextLine();
        }

        System.out.print("When would you like to end your day? "); 
        String end = console.nextLine();

        while (!end.endsWith("am") && !end.endsWith("pm")) {
            System.out.println("Please specify pm or am");
            System.out.print("When would you like to end your day? ");
            end = console.nextLine(); 
        }

        int twelvePM = 0;
        if (end.startsWith("12") && end.endsWith("am") && (start.endsWith("am") || start.startsWith("12") && start.endsWith("pm"))) { //asks user if they mean 12 am currently or the next day
                twelvePM = 12;
        }

        //this will be used to convert string of start into just the numbers need to break up the hours and minutes store in diff strings and use those for return
        String startHourNum = "";
        String colon = "";
        int startChar = 0;

        while (!colon.equals(":")) {
            startHourNum += "" + start.charAt(startChar); //gets number of hour
            startChar++;
            colon = "" + start.charAt(startChar); //identifys when there is a colon
        }
        int hourStart = Integer.parseInt(startHourNum);
        arr[0] = hourStart;

        String endHourNum = "";
        String colon2 = "";
        int endChar = 0;

        while (!colon2.equals(":")) {
            endHourNum += "" + end.charAt(endChar); //gets number of hour
            endChar++;
            colon2 = "" + end.charAt(endChar); //identifys when there is a colon
        }
        int hourEnd = Integer.parseInt(endHourNum); 

        int totalTime = Math.abs(hourStart - hourEnd); 
        if (start.startsWith("12") && !end.startsWith("12")) { //in the case of waking up at 12:00 pm
            totalTime = hourEnd;

        }else if (start.contains("am") && end.contains("pm")) {
            totalTime += 12;
        } 

        startChar++;
        String minStartStr = "";
        String minIdentifier1 = "";
        while(!minIdentifier1.equals(" ") && !minIdentifier1.equals("a") && !minIdentifier1.equals("p")) { // to get minutes of the time inputted
            minStartStr += "" + start.charAt(startChar);
            startChar++;
            minIdentifier1 = "" + start.charAt(startChar);
        }
        int minStart = Integer.parseInt(minStartStr);
        arr[1] = minStart;

        endChar++;
        String minEndStr = "";
        String minIdentifier2 = "";
        while(!minIdentifier2.equals(" ") && !minIdentifier2.equals("a") && !minIdentifier2.equals("p")) { // to get minutes of the time inputted
            minEndStr += "" + end.charAt(endChar);
            endChar++;
            minIdentifier2 = "" + end.charAt(endChar);
        }
        int minEnd = Integer.parseInt(minEndStr);
        
        // to convert hours into min, and test if start or end is greater than the other. This is the final calculation for total time
        int hoursIntoMin = totalTime + twelvePM;
        int minDiffEndAndStart = minEnd - minStart;
        if (minStart > minEnd) {
            hoursIntoMin--;
            minDiffEndAndStart = 60 - minStart + minEnd;               
        }
        hoursIntoMin = hoursIntoMin * 60;
        totalTime = hoursIntoMin + minDiffEndAndStart;
        arr[2] = totalTime;

        System.out.println();

        return arr;
    }

    // User goal for the day
    public static String Goal(Scanner console) {
        System.out.print("What is your goal for the day? ");
        String goal = console.nextLine();
        System.out.println();

        return goal;
    }

    // this method asks the user to input a goal for the day and how long it will take 
    // it will then return the number to be used in the method below as the user goes throughout their day
    // and tell the user how much remaining time they have left to complete their goal for the day
    public static int GoalTime(Scanner console) {
        System.out.print("How long will that take? Let's assume 1 extra hour to be safe! When typing please specify 'hours' or 'min' or both, format such as '5 min', '4 hours', or '4 hours 5 min' is required: ");
        String timeForGoal = console.nextLine();

        while (!timeForGoal.endsWith("min") && (!timeForGoal.endsWith("hours") && !timeForGoal.endsWith("hour"))) {
            System.out.println("Please specify min or hours");
            System.out.print("How long will that take? Let's assume 1 extra hour to be safe! When typing please specify 'hours' or 'min' or both, format such as '5 min', '4 hours', or '4 hours 5 min' is required: ");
            timeForGoal = console.nextLine();
        }
        System.out.println();

        String minOfGoal = "0";
        String hoursOfGoal = "0";
        String spaceIndicator = "";
        int charAtGoal = 0;

        if (timeForGoal.contains("min") && (!timeForGoal.contains("hours") && !timeForGoal.contains("hour"))) {
            while(!spaceIndicator.equals(" ")) {
                minOfGoal += "" + timeForGoal.charAt(charAtGoal);
                charAtGoal++;
                spaceIndicator = "" + timeForGoal.charAt(charAtGoal);
            }
        }else if ((timeForGoal.contains("hours") || timeForGoal.contains("hour")) && !timeForGoal.contains("min")) {
            while(!spaceIndicator.equals(" ")) {
                hoursOfGoal += "" + timeForGoal.charAt(charAtGoal);
                charAtGoal++;
                spaceIndicator = "" + timeForGoal.charAt(charAtGoal);
            }
        }else {
            while (!spaceIndicator.equals(" ")) {
                hoursOfGoal += "" + timeForGoal.charAt(charAtGoal);
                charAtGoal++;
                spaceIndicator = "" + timeForGoal.charAt(charAtGoal);
            }
            spaceIndicator = "";
            charAtGoal = charAtGoal + 6;
            if (timeForGoal.contains("hours")) {
                charAtGoal++;
            }
            while (!spaceIndicator.equals(" ")) {
                minOfGoal += "" + timeForGoal.charAt(charAtGoal);
                charAtGoal++;
                spaceIndicator = "" + timeForGoal.charAt(charAtGoal);
            }
        }
        int goalLength = Integer.parseInt(minOfGoal) + (Integer.parseInt(hoursOfGoal) * 60) + 60;

        return goalLength;
    }
    // prompts the user about the current time and what they are currently doing
    // then tells the user how much time they have left in the day to complete their goal.
    // when 'goal' is typed the program will stop stop stating how much time the user has left
    // until the goal must be completed, and the user will continue with their event log 
    // until the day is completed
    public static void goalTracker (Scanner console, String goal, int goalLength, int[] arr) {
        int goalTime = Integer.MAX_VALUE;
        boolean goalCompletion = false;

        while (goalTime > 0) { // fix this while loop
            System.out.print("What is the current time? ");
            String currentTime = console.nextLine();

            while(!currentTime.endsWith("am") && !currentTime.endsWith("pm")) {
                System.out.println("Please specify am or pm");
                System.out.print("What is the current time? ");
                currentTime = console.nextLine();
            }

            String colon = "";
            String hourTime = "";
            int charCurrentTime = 0;

            while (!colon.equals(":")) {
                hourTime += "" + currentTime.charAt(charCurrentTime);
                charCurrentTime++;
                colon = "" + currentTime.charAt(charCurrentTime);
                }

            int currentHour = Integer.parseInt(hourTime);

            String spaceOrPmOrAm = "";
            String minTime = "";
            charCurrentTime++;

            while (!spaceOrPmOrAm.equals(" ") && !spaceOrPmOrAm.equals("a") && !spaceOrPmOrAm.equals("p")) {
                minTime += "" + currentTime.charAt(charCurrentTime);
                charCurrentTime++;
                spaceOrPmOrAm = "" + currentTime.charAt(charCurrentTime);
                }

            int currentMin = Integer.parseInt(minTime);

            //this is for hours
            int hourDiffBegAndCurr = 0;
            if ((currentTime.endsWith("am") && !currentTime.contains("12")) || (currentTime.endsWith("pm") && currentHour == 12)) {
                hourDiffBegAndCurr = currentHour - arr[0];

            }else if (currentTime.endsWith("pm") || (currentTime.endsWith("am") && currentHour == 12)) {
                int noonMinBegTime = 12 - arr[0];
                hourDiffBegAndCurr = currentHour + noonMinBegTime;
            }

            //for min
            int minDiffBegAndCurr = currentMin - arr[1];

            if (minDiffBegAndCurr < 0) {
                minDiffBegAndCurr = 60 + minDiffBegAndCurr;
                hourDiffBegAndCurr--;
            }
            int totalTimeFromBeg = hourDiffBegAndCurr * 60 + minDiffBegAndCurr;
        
            System.out.print("What are you doing? ");
            String event = console.nextLine();

            if (event.equals("goal")) {
                goalCompletion = true;
            }
            String eventLength = "";

            if (!event.equals("goal")) {
            System.out.print("How long will that take? ");
            eventLength = console.nextLine();
            }

            while (!eventLength.endsWith("hour") && !eventLength.endsWith("hours") && !eventLength.endsWith("min") && !eventLength.equals("test") && !event.equals("goal")) {
                System.out.println("Please specify hours, min. or both");
                System.out.print("How long will that take? ");
                eventLength = console.nextLine();
                }

                String minOfEvent = "0";
                String hoursOfEvent = "0";
                String spaceIndicator = "";
                int charAtEvent = 0;

            if (eventLength.contains("min") && (!eventLength.contains("hours") && !eventLength.contains("hour"))) {
                while(!spaceIndicator.equals(" ")) {
                    minOfEvent += "" + eventLength.charAt(charAtEvent);
                    charAtEvent++;
                    spaceIndicator = "" + eventLength.charAt(charAtEvent);
                }
            }else if ((eventLength.contains("hours") || eventLength.contains("hour")) && !eventLength.contains("min")) {
                while(!spaceIndicator.equals(" ")) {
                    hoursOfEvent += "" + eventLength.charAt(charAtEvent);
                    charAtEvent++;
                    spaceIndicator = "" + eventLength.charAt(charAtEvent);
                }
            }else if (!event.equals("goal")) {
                while (!spaceIndicator.equals(" ")) {
                    hoursOfEvent += "" + eventLength.charAt(charAtEvent);
                    charAtEvent++;
                    spaceIndicator = "" + eventLength.charAt(charAtEvent);
                }
                spaceIndicator = "";
                charAtEvent = charAtEvent + 6;
                if (eventLength.contains("hours")) {
                     charAtEvent++;
                }
                while (!spaceIndicator.equals(" ")) {
                    minOfEvent += "" + eventLength.charAt(charAtEvent);
                    charAtEvent++;
                    spaceIndicator = "" + eventLength.charAt(charAtEvent);
                }
            }
            int timeForEvent = Integer.parseInt(minOfEvent) + (Integer.parseInt(hoursOfEvent) * 60);

            goalTime = arr[2] - (totalTimeFromBeg + timeForEvent + goalLength); //check if this equation is right

            int hoursLeft = goalTime / 60;
            int minsLeft = goalTime % 60;

            if (hoursLeft > 2 && !goalCompletion) {
                System.out.println("You have " + hoursLeft + " hours " + "& " + minsLeft + " min" + "to start " + goal); 

            }else if (!goalCompletion) {
                System.out.println("It is suggested that you " + goal);
                System.out.print("Will you? (yes or no) ");
                String answer = console.next();

                if (!answer.equals("yes") && !answer.equals("no") && !answer.equals("y") && !answer.equals("n")) {
                    System.out.println("Please say yes or no. ");
                    System.out.print("Will you? (yes or no): "); //change this up to match output, also change the code ending after typing yes or no this goes back to the while loop assigned at the beginning 
                    answer = console.next();
                }
                if (answer.contains("yes")) {
                    goalCompletion = true; //change this line of code later
                    }
            }
            System.out.println();
        }
        if (goalCompletion) {
            System.out.println("Congratulations you finished your goal of the day!"); //then add along with all the other things
        }else {
            System.out.println("Unfortunately you did not complete your assigned goal. :("); //then add along with all the other things
        }
    }
}