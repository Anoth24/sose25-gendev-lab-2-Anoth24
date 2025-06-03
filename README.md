#### 💯Points: ![Points bar](../../blob/badges/.github/badges/points-bar.svg)

#### 📝 [Report](../../blob/badges/report.md)
---

# Reactive Synthesis with Spectra
## Activity: Learn Spectra, controller synthesis, and execution

Follow the Spectra tutorial available from [https://se-buw.github.io/gse/tutorials/spectra/](https://se-buw.github.io/gse/tutorials/spectra/0a.Reactive-synthesis-with-Spectra/). You may skip parts [D1] and [D2].

-   This includes the installation of the Spectra plug-ins from this
    Eclipse update site: `http://smlab.cs.tau.ac.il/syntech/spectra/tools/update/`

-   You may start watching the tutorial videos from here:
    <https://www.youtube.com/watch?v=smDcuR3NP44&list=PLGyeoukah9Nbx1QquUmZGdLulFZIsiRlZ&index=3>

-   The code for all tasks of the tutorial is available from GitHub:
    <https://github.com/jringert/spectra-tutorial>

-   Spectra specifications may also be edited in the Formal Methods Playground (does not support all features of the Spectra Eclipse plug-in):
    <https://play.formal-methods.net/?check=SPECTRA>

The tutorial consists of explanation videos with simple tasks. Please
try these yourself. Solution videos are provided, but just watching
these without trying the activities will likely not have the desired
learning effect.

---

## Homework task: Your Own Reactive System

Clone this repository and import it into your IDE (Eclipse, VS Code, or simply using Gradle). Edit the specification file either on the Formal Methods Playground or in your IDE.
Make sure to install the required Spectra plug-ins for the best editing experience in Eclipse.

To pass this part you need to:

-   Create a meaningful Spectra specification `Spec.spectra` of some
    reactive system (see
    [\[L1\]](https://www.youtube.com/watch?v=IVzfd3zz0jc&list=PLGyeoukah9Nbx1QquUmZGdLulFZIsiRlZ&index=3)-[\[L3\]](https://www.youtube.com/watch?v=9uTs4wD45JA&list=PLGyeoukah9Nbx1QquUmZGdLulFZIsiRlZ&index=15))
    with at least

    -   3 environment and 3 system variables

    -   2 initial (ini), 4 safety (alw), and 2 justice (alwEv)
        assumptions or guarantees (at least 2 x asm and 2 x gar)

-   Write a short comment about the meaning of each variable, each
    assumption, and each guarantee.

-   Ensure that the specification is realizable (see
    [\[A1\]](https://www.youtube.com/watch?v=0v85VNP2Ngw&list=PLGyeoukah9Nbx1QquUmZGdLulFZIsiRlZ&index=5)
    &
    [\[A2\]](https://www.youtube.com/watch?v=p9qB3NtsS48&list=PLGyeoukah9Nbx1QquUmZGdLulFZIsiRlZ&index=11))
    and well-separated (see
    [\[A3\]](https://www.youtube.com/watch?v=UlPtJUccy6E&list=PLGyeoukah9Nbx1QquUmZGdLulFZIsiRlZ&index=17)).

-   Create a variant of your specification that is unrealizable due to a
    missing justice assumption. Provide the assumption commented out in
    the specification `Spec_Unreal.spectra` and mark it with the comment
    `//FIX` preceding it.

-   Synthesize a Just-in-time symbolic controller for the realizable
    specification in file `Spec.spectra` and write code to execute it (see
    [\[E2\]](https://www.youtube.com/watch?v=Zu-EL3fSeIM&list=PLGyeoukah9Nbx1QquUmZGdLulFZIsiRlZ&index=13))
    in class `gendev.lab2.SpecSimulatorCmd`.

You may use the provided test cases to see your progress. All test cases
should pass for your submission. The test cases are not able to test the
simulation of the controller!

See this video when using Eclipse with the Spectra plug-ins installed: <https://youtu.be/SIDXccugsyY>

See this video when using VS Code or any other IDE supporting Gradle: <https://youtu.be/fNl9TVYBeow>

---

## Submission
Commit and push your solution to GitHub and submit the repository link in Moodle.
