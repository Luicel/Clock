# Clock

This is a Minecraft 1.16 plugin written using the Spigot API. It allows players to create, configure, and display timers and stopwatches in-game!

This project was entirely a learning experience for me and an excuse to familize myself with some new concepts and to brush up my skills on others. I plan to update this project periodically to gain experience with supporting long-term projects.

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
  # Use '{timerName}' for the timer's name.
  # These variables work in-game too! And color codes are supported!
  timer-completion-announcement: "&7Timer &f{timer-name} &7has reached 0 seconds remaining!"

  # Message to announce once a stopwatch laps.
  # Use '{stopwatchName}' for the stopwatch's name.
  # Use '{lapTime}' for the lap timestamp.
  # These variables work in-game too! And color codes are supported!
  stopwatch-lap-announcement: "&7Stopwatch &f{stopwatch-name} &7has been lapped at &f{lap-time}&7!"

  # Default formats for timers.
  # Use '{timerName}' for the timer's name.
  # These variables work in-game too! And color codes are supported!
  timer-default-format-prefix: "&b&lTimer: &f"
  timer-default-format-suffix: ""

  # Default formats for stopwatches.
  # Use '{stopwatchName}' for the stopwatch's name.
  # Use '{currentLapTime}' for the current lap time.
  # These variables work in-game too! And color codes are supported!
  stopwatch-default-format-prefix: "&d&lStopwatch: &f"
  stopwatch-default-format-suffix: ""
```

### (Potential) Future Ideas, Changes, and Additions

```
- Add a configurable command executable action for when a timer hits 0 seconds remaining.
- Implement a renaming command for timers and stopwatches.
- Implement regex support for days, hours, minutes and seconds for time arguments (instead of just seconds/milliseconds).
- Make timer and stopwatch ticking dependent on Timestamps instead of Minecraft server ticks.
- Cleanup the way in which displays are stored, tracked, and displayed (it’s a bit messy). Perhaps a data/display.yml file would be nice.
- Allow timers and stopwatches to display in titles and subtitles.
- Add MVdWPlaceholderAPI support for timer and stopwatch displays. 
- Reduce Reflections calls on startup.
- Add aliases for commands (i.e. /clock information executes /clock info)
- Add permissions for all commands.
- Formatting config options for core messages (configurable command prefixes, headers, and primary and secondary colors).
- Implement an automatic config.yml version updater.
- Potentially make display formats all one string (with variables such as {time} included) instead of prefix + time + suffix.
- Potentially make ClockObject own all variables that are consistent between models (i.e. state, name and formats)
- Make the stopwatch lap history size (the List<> size) configurable in the config.
```
