# Forth Interpreter
This project consists of a Forth interpreter for parsing and executing scripts written in a variant of the Forth postfix language.

# Usage: 

## Initialization:
Before using the interpeter a ScriptController must be created. This requires passing a listener that implements the ScriptListener interface that is required for handling all of the scripts I/O output.
```java
ScriptController controller = new ScriptController(listener);
```
## Building:
Once a script controller has been created a script can be built. Synax checking occurs when the script is built and an executable tree structure is produced if building succeeds. An optional user and script name may be provided when building a script. 

```java
String script =  
      ": destroy!                 \n" +               
      "    scan!                  \n" +      
      "    dup 0 > if             \n" +
      "        dup 1 -            \n" +
      "        0 do               \n" + 
      "            I identify!    \n" + 
      "            team <> if     \n" +
      "                shoot!     \n" +
      "                leave      \n" +
      "            else           \n" +
      "            then           \n" +
      "        loop               \n" +
      "    else                   \n" +
      "    then ;                 \n" +
      ": play ( -- )              \n" +      
      "    begin                  \n" +  
      "        destroy!           \n" +
      "        2 random 1 - turn! \n" +
      "        move!              \n" +
      "        movesLeft 0 =      \n" +  
      "    until                  \n" + 
      "    destroy! ;             \n";
    
ScriptData data = controller.build("user name", "script name", script);
```

## Executing:
Once a script has been built it can be executed. This requires calling execute on the controller. Execute requires an additional data source parameter. The data source is responsible for handling the input side of the interpreters I/O. The data source will be called whenever an instruction requires external input.
```java
ScriptData data = controller.build("user name", "script name", ...);
controller.execute(data, dataSource);
```
