# Parking
Parking lots are an urban necessity. They provide places for you to leave your car without having to worry about it being stolen or towed. Modern car parking systems are automated and are capable of self-management. In this project, you will create a similar system, but in a simplified form. 
 
This project is created for get practice in Kotlin language

The project was created in 3 stages. It is just console application.

Kotlin 1.4 required

## Stage 1
Two spots are not enough for a parking lot, so let's increase the number of parking spaces. We'll jump straight to 20 spaces, numbered from 1 to 20. Initially, all the spots are vacant.

When the user wants to park the car, the program should find an available parking spot with the lowest number.

The user can write commands park and leave repeatedly and type exit to end the program.

If the parking lot is full and there's no room, the program should type Sorry, the parking lot is full..

If there are several available spots for a car, the program should always assign the spot with the lowest number.

### Examples in and out data

The symbol > represents the user input. Note that it's not part of the input.

```
> park KA-01-HH-9999 White
White car parked in spot 1.
> park KA-01-HH-3672 Green
Green car parked in spot 2.
...

> park Rs-P-N-21 Red
Sorry, the parking lot is full.
> leave 1
Spot 1 is free.
> park Rs-P-N-21 Red
Red car parked in spot 1.
> exit
```

## Stage 2
In real life, parking lots vary in size. At this stage, we will get better at making art imitate life. To do this, we will make our program customizable by adding a create command that allows the user to specify the number of spots. For example, the command create 3 makes a parking lot with three spots. The number of spots should be positive. The program output should be the following: Created a parking lot with 3 spots.

Other commands like park or leave should return an error Sorry, a parking lot has not been created. until the user enters the create command. If the user calls create again, the previous parking state should be reset.

It is also important to keep track of which spaces are occupied by which cars. For this, add a status command that prints all occupied spots in ascending order. For each spot, it should print the spot number, the car’s plate registration number, and the color of the car, all separated by spaces like the example below. If there are no occupied spots, the program should print: Parking lot is empty.

### Examples in and out data
The symbol > represents the user input. Note that it's not part of the input.
```
> park KA-01-HH-9999 White
Sorry, a parking lot has not been created.
> create 3
Created a parking lot with 3 spots.
> status
Parking lot is empty.
> park KA-01-HH-9999 White
White car parked in spot 1.
> park KA-01-HH-3672 Green
Green car parked in spot 2.
> park Rs-P-N-21 Red
Red car parked in spot 3.
> leave 2
Spot 2 is free.
> status
1 KA-01-HH-9999 White
3 Rs-P-N-21 Red
> exit
```

### Stage 3
The command reg_by_color prints all registration numbers of cars of a particular color, taking color as a parameter. The color may be written in uppercase or lowercase letters. For example, reg_by_color BLACK. The answer should contain registration numbers separated by commas. The order should be the same as in the status command. If there are no cars of this color, the output should be like this: No cars with color BLACK were found..

The command spot_by_color is similar to the previous one, but it prints the parking space numbers of all the cars of a particular color.

The command spot_by_reg returns you the number of the spot where a car is located based on its registration number, for example, spot_by_reg KA-01. This command will also return an error message if your car was not found: No cars with registration number KA-01 were found. For convenience, let's suppose that all car registration numbers are unique.

### Examples in and out data
```
> spot_by_color yellow
Sorry, a parking lot has not been created.
> create 4
Created a parking lot with 4 spots.
> park KA-01-HH-9999 White
White car parked in spot 1.
> park KA-01-HH-3672 White
White car parked in spot 2.
> park Rs-P-N-21 Red
Red car parked in spot 3.
> park Rs-P-N-22 Red
Red car parked in spot 4.
> reg_by_color GREEN
No cars with color GREEN were found.
> reg_by_color WHITE
KA-01-HH-9999, KA-01-HH-3672
> spot_by_color GREEN
No cars with color GREEN were found.
> spot_by_color red
3, 4
> spot_by_reg ABC
No cars with registration number ABC were found.
> spot_by_reg KA-01-HH-3672
2
> spot_by_reg Rs-P-N-21
3
> exit
```