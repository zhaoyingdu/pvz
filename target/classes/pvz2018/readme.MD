To test the file, run App in a java IDE.

Before you run, put template.xml to your Present Working Directory;

(line 8 and line 9 in App.java are compulsaryto run the program,
because they will create temp/ and save/folder in proper place)

after you open the view, you can enter the following command to test the program.
notably, print is a good command to visualize command effect.

print
  -calls printStatus() on garden object, to visualize the current statuses of the game.
  
getData
  - calls format() on garden object to visualize the current creatures and their locations
  
save [filename]
  - call stack: save(String filename) in Garden Object->save(String filename) in GardenDOM  
  - save current game as name indicated by [filename], NOTE should be in *.XML format

load [filename]
  - call stack: load(String filename) in Garden Object->load(String filename) in GardenDOM
  - load game by file indicated by [filename]

undo
  - call stack: undo() in Garden Object
  - undo plant placement/removal within the current round;

redo
  - call stack: redo() in garden Object
  - redo plant placement/removal within the current round;

plant <plantname> <row> <col>
  - example: plant sunflower 1 1
            plant peaShooter 2 2
            plant walnut 3 3

q/a:
reset map size? 
  set variables "lotWith" and "lotHeight" in Garden.java
how to use the output from getData?
  its a long string with small unit like this: "z,1,1-", 
  it simply means zombie at x=1, y=1
how do I configure game parameters? like item speed and life?
  change the number within the string starting from line 58 in
  garden.java

todo:
  1.pass on gamestatus by "getData". 
  2.convert zombie col value and moving speed to pixel.