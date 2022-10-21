# Clock

This is a Minecraft 1.16 plugin written using the Spigot API. It allows players to create, configure, and display timers and stopwatches in-game!

This project was created primarily as a learning experience for me. During its development, I had a chance to brush up on some OO principles such as polymorphism and to learn new intermediate Java concepts such as annotations and Reflections. Though most importantly of all, I used this project as a lesson for how to develop clean and reliable user experiences.

You can download the latest version of Clock [here](https://github.com/Luicel/Clock/releases/latest).

### Commands

```
Clock
  /clock info
  /clock reload
Timer
  /timer create <name> <seconds>
  /timer delete <name>
  /timer info <name>
  /timer list
  /timer start <name>
  /timer stop <name>
  /timer time <name> <add/remove/set> <seconds>
  /timer display <name> <none/actionbar>
  /timer format <name> <prefix/suffix> <text/clear>
Stopwatch
  /stopwatch create <name>
  /stopwatch delete <name>
  /stopwatch info <name>
  /stopwatch list
  /stopwatch start <name>
  /stopwatch stop <name>
  /stopwatch lap <name>
  /stopwatch reset <name>
  /stopwatch display <name> <none/actionbar>
  /stopwatch format <name> <prefix/suffix> <text/clear>
```

### Permissions

```
Permissions use the following format: clock.<command>.<subcommand>

Notes:
  - Wildcards (*) are supported!
  - clock.timer == clock.timer.* (for example)
  - OP == clock.*

Examples:
  - clock.timer.create (access to create a timer, but nothing else)
  - clock.timer.* (access to all /timer commands)
  - clock.* (access to everything)
```

### Config

```
config-version: 1.0.0

##############################
# MECHANICS
# Configure the functionality of the plugin.
##############################
mechanics:
  # Whether or not to announce a timer's completion.
  announce-timer-completion: false

  # Whether or not to announce a stopwatch lapping.
  announce-stopwatch-lapping: false

  # Whether or not '/clock reload' will use previously cached
  # seconds or seconds that are stored in the data files when
  # reloading timers.
  # true = previously cached seconds are used.
  # false = file stored seconds are used.
  use-cached-timer-seconds-on-reload: true

##############################
# FORMATTING
# Configure the look of the plugin.
##############################
formatting:
  # Whether or not to place a 0 before single digit numbers.
  # true = 04d 25h 02m 08s
  # false = 4d 25h 2m 8s
  use-leading-zero-for-single-digits: true

  # Message to announce once a timer reaches 0 seconds remaining.
  # Use '{timer-name}' for the timer's name.
  # These variables work in-game too! And color codes are supported!
  timer-completion-announcement: "&7Timer &f{timer-name} &7has reached 0 seconds remaining!"

  # Message to announce once a stopwatch laps.
  # Use '{stopwatch-name}' for the stopwatch's name.
  # Use '{lap-time}' for the lap timestamp.
  # These variables work in-game too! And color codes are supported!
  stopwatch-lap-announcement: "&7Stopwatch &f{stopwatch-name} &7has been lapped at &f{lap-time}&7!"

  # Default formats for timers.
  # Use '{timer-name}' for the timer's name.
  # These variables work in-game too! And color codes are supported!
  timer-default-format-prefix: "&b&lTimer: &f"
  timer-default-format-suffix: ""

  # Default formats for stopwatches.
  # Use '{stopwatch-name}' for the stopwatch's name.
  # Use '{currentLap-time}' for the current lap time.
  # These variables work in-game too! And color codes are supported!
  stopwatch-default-format-prefix: "&d&lStopwatch: &f"
  stopwatch-default-format-suffix: ""
```
