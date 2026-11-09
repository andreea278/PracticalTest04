# PracticalTest02

# PracticalTest01Var04

**Student:** Andreea Pleșu
**Group:** 341A3

Practice test for *Elemente de Informatică Mobilă* (EIM), UPB — mock exam #4.

- Package: `ro.pub.cs.systems.eim.practicaltest01var04`
- Main activity: `PracticalTest01Var04MainActivity` (`activity_practical_test01_var04_main.xml`)
- Secondary activity: `PracticalTest01Var04SecondaryActivity` (`activity_practical_test01_var04_secondary.xml`)
- Service: `PracticalTest01Var04Service`

## How the app looks

### Main activity

```
┌─────────────────────────────────────────┐
│          [NAVIGATE TO SECONDARY ACTIVITY]│  ← aligned right
│ [ Top, Left, Left, Bottom              ]│  ← path (non-editable, full width)
│               [  TOP  ]                 │  ← centered
│ [       LEFT       ][      RIGHT       ]│  ← half / half
│               [ BOTTOM ]                │  ← centered
│ [                 4                    ]│  ← total presses (non-editable, full width)
└─────────────────────────────────────────┘
```

### Secondary activity

```
┌─────────────────────────────────────────┐
│ [ Top, Left, Left, Bottom              ]│  ← path received (non-editable)
│ [     REGISTER     ][      CANCEL      ]│  ← same line, half / half
└─────────────────────────────────────────┘
```

## Features

| Part | What it does |
| --- | --- |
| A.2 | Layout above: Navigate button (right), path field, cross-shaped direction buttons, press counter |
| B.1 | Each direction button appends its name to the path, separated by commas (no comma before the first), and increments the counter |
| B.2 | Automatic state saving is disabled (`saveEnabled="false"`); the path and the counter (as an `Int`) are saved in `onSaveInstanceState` and restored manually |
| C.1 | The secondary activity is started only through an intent (custom action + `DEFAULT` category); it shows the path; `Register` returns `RESULT_OK`, `Cancel` returns `RESULT_CANCELED` |
| C.2 | Back in the main activity, a `Toast` shows the result; the path and counter are reset (empty path, `0`) whatever the result |
| D.1 | When the total number of presses exceeds a threshold constant, `PracticalTest01Var04Service` starts with the path and the count; every 5 seconds it broadcasts the date/time, path and count, with one of three actions chosen at random; it stops when the app is destroyed |
| D.2 | A `BroadcastReceiver` registered in the main activity logs every message with a fixed tag |

## How to test

1. Open the project in Android Studio and run it on an emulator.
2. Press the direction buttons → the path grows (`Top, Left, ...`) and the counter increases.
3. Rotate the emulator (or enable *Developer options → Don't keep activities* and press Home) → the path and counter are restored.
4. Press **Navigate to secondary activity** → the path is shown → **Register** / **Cancel** → a Toast shows the result and the fields are reset.
5. Press the buttons until the counter passes the threshold → in Logcat (`package:mine`) a broadcast is sent and logged every 5 seconds, with a random action.
6. Press Back to close the app → the broadcasts stop.
