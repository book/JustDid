# JustDid Roadmap

## Vision

Keep track of things one did, to be able to answer the question "when
did I last do X?".

## MVP

A text input and a submit that save entry to a log file.

Show the log in reverse chronological order in the main screen.

Format:

    # timestamp string
    1591616515 Laundry
    1591634542 Dishes

Next step: the text input is complemented by a dropdown of
existing tasks.

## Future Features

### Task Configuration

* periodicity: this task should performed x times per period
* recurrence: this task should be performed every Wednesday
* granularity (for UI): hours, days, etc
* format: show dates as absolute or relative and according to
  granularity
* create todos from tasks: e.g. if laundry is a weekly thing, add a todo
  item just after having done it, same for recurring tasks with a
  stricter deadline

### Sharing

Sharing the log, sharing the configuration.

Do we need a web service? Communicate with a calendar or todo
application/service?

Command-line tool to interact with the app/service?

### Grouping

Group tasks by context (e.g. home, work, association, etc), show each
context as a tab.

## Miscellaneous

* To-do list is ordered by the next thing to do.
* Marking a to-do item as done logs it automatically
